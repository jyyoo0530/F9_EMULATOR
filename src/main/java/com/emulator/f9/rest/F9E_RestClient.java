package com.emulator.f9.rest;

import com.emulator.f9.model.Membership;
import com.emulator.f9.model.market.mobility.sea.F9_SEA_SKD;
import com.emulator.f9.model.market.mobility.sea.F9_SEA_SKD_ReactiveMongoRepository;
import com.emulator.f9.model.market.mobility.sea.mdm.*;
import com.emulator.f9.model.market.mobility.sea.miner.maerskSchedule.*;
import com.emulator.f9.model.tmp.MDM_T_PORT_MySqlRepository;
import com.emulator.f9.model.tmp.MDM_T_VSL_MySqlRepository;
import com.emulator.f9.model.tmp.SCH_SRC;
import com.emulator.f9.model.tmp.SCH_SRC_MySqlRepository;
import com.emulator.f9.service.MaerskMiningService;
import com.emulator.f9.service.UnlocodeMDM;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController()
public class F9E_RestClient {

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    F9_MDM_VSL_ReactiveMongoRepository f9MdmVslRepo;

    @Autowired
    F9_MDM_LOCATION_ReactiveMongoRepository f9MdmLocationRepo;

    @Autowired
    F9_MDM_CARGO_ReactiveMongoRepository f9MdmCargoRepo;

    @Autowired
    F9_MDM_DATAOWNER_ReactiveMongoRepository f9MdmDataownerRepo;

    @Autowired
    F9_SEA_SKD_ReactiveMongoRepository f9SeaSkdRepo;

    @Autowired
    SCH_SRC_MySqlRepository schSrcMySqlRepo;

    @Autowired
    MDM_T_PORT_MySqlRepository mdmTPortMySqlRepo;

    @Autowired
    MDM_T_VSL_MySqlRepository mdmTVslMySqlRepo;

    Membership membership = new Membership();

    @RequestMapping(value = "mobility/sea/schedule/{codeOwner}", method = RequestMethod.GET)
    public void digMaerskSchedule(@PathVariable("codeOwner") String codeOwner) {
        MaerskMiningService miningMaersk = new MaerskMiningService();
        // --) get active port list
        ArrayList<F9E_MSK_ACTIVEPORT> activePorts = miningMaersk.getActivePorts();
        ArrayList<F9E_MSK_ACTIVEVESSEL> activeVessels = miningMaersk.getActiveVessels();

        // 1) get active vessel list
        ArrayList<String> vesselList = miningMaersk.getVesselList();

        // --) get into loop (parallel computing으로 가야함..)
        vesselList.forEach(x -> {

            // 2) check if F9_VSL_MDM exists
            boolean chkVsl = miningMaersk.checkF9eMdmVsl(x, f9MdmVslRepo);
            String activeVesselName = activeVessels.stream().filter(b -> b.getCode().equals(x)).collect(Collectors.toList()).get(0).getName();

            // 3) get Vessel Detail
            F9E_MSK_VSLMDM mskVslMdm = miningMaersk.getVesselDetail(x, activeVesselName);

            // --) update Vessel MDM
            if (!chkVsl) {
                miningMaersk.updateVesselMdm(mskVslMdm, f9MdmVslRepo);
            }

            // --) get into loop (parallel computing으로 가야함..)
            ArrayList<String> portList = new ArrayList<>(); // port mdm 업데이트용
            ArrayList<F9E_MSK_SKED_VSL> listF9eSeaSkd = new ArrayList<>(); // F9E_MSK_SKED_VSL 업데이트용

            String extensionUri = "vesselCode=" + x + "&vesselName=" + mskVslMdm.getName().replace(" ", "+");
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String fromDate = "&fromDate=" + formatter.format(calendar.getTime());
            calendar.add(Calendar.DATE, 29);
            String endDate = "&toDate=" + formatter.format(calendar.getTime());
            ArrayList<F9E_MSK_SKED_VSL> extendedList = miningMaersk.getVesselSked(x, extensionUri + fromDate + endDate);
            listF9eSeaSkd = new ArrayList<>(Stream.concat(listF9eSeaSkd.stream(), extendedList.stream()).collect(Collectors.toList()));
            while (extendedList.size() != 0) {

                // 4) add Lists
                calendar.add(Calendar.DATE, 1);
                fromDate = "&fromDate=" + formatter.format(calendar.getTime());
                calendar.add(Calendar.DATE, 29);
                endDate = "&toDate=" + formatter.format(calendar.getTime());
                extendedList = miningMaersk.getVesselSked(x, extensionUri + fromDate + endDate);
                listF9eSeaSkd = new ArrayList<>(Stream.concat(listF9eSeaSkd.stream(), extendedList.stream()).collect(Collectors.toList()));

                // 4-1) generate port lists
                listF9eSeaSkd.forEach(z -> {
                    portList.add(z.getSrcPortCode());
                });
                List<String> distinctPortList = portList.stream().distinct().collect(Collectors.toList());

                // 4-2) update port lists
                distinctPortList.forEach(a -> {
                    // 4-2-1) check if port code is exists in the collection
                    boolean chkResult = miningMaersk.checkF9eMdmLocation(a, f9MdmLocationRepo);
                    // 4-2-1) get and update port code if not exists
                    String locationName = activePorts.stream().filter(b -> b.getGeoId().equals(a)).collect(Collectors.toList()).get(0).getLocationName();
                    if (!chkResult) {
                        F9E_MSK_PORTMDM response = miningMaersk.getPortMdm(locationName, a);
                        miningMaersk.updateF9MdmLocation(response, f9MdmLocationRepo);
                    }
                });
            }

            // 5) convert to f9s vessel schedule
            List<F9_SEA_SKD> f9SeaSkds = miningMaersk.parseIntoF9SeaSkd(listF9eSeaSkd, f9MdmLocationRepo, f9MdmVslRepo, x);

            // 6) update F9E_SEA_SKED(MongoDB and MySQL)
            f9SeaSkds.forEach(t -> {
                SCH_SRC u = new SCH_SRC();
                u.setAllData(t);
                miningMaersk.uploadF9SeaSkd(t, f9SeaSkdRepo);
                miningMaersk.uploadF9SeaSkdMySQL(u, schSrcMySqlRepo);
            });


        });

    }

    @RequestMapping(value = "generateInitialMDM/{targetSource}", method = RequestMethod.GET)
    public void generateMDM(@PathVariable("targetSource") String targetSource) throws IOException {
        UnlocodeMDM mdm = new UnlocodeMDM();
        mdm.generateInitialMDM(targetSource, f9MdmLocationRepo, mdmTPortMySqlRepo);
    }

    @RequestMapping(path = "login", method = RequestMethod.GET)
    public void getJWTToken() {

        // 0) pre-declaration
        ResponseEntity<String> response = null;
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        // tmp) set membership -> Client입력으로 받아야함.
        membership.setUsrId("USR00");
        membership.setPassword("USR00");

        // 1) oauth String
        String targetUrl = "http://api.freight9.com:8080/oauth/token";
        targetUrl += "?username=";
        targetUrl += membership.getUsrId();
        targetUrl += "&password=";
        targetUrl += membership.getPassword();
        targetUrl += "&grant_type=password";

        // 2) set Headers with credential Code and http request
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add("Authorization", "Basic " + membership.getCredential());
        HttpEntity<String> request = new HttpEntity<String>(headers);

        // 3) get response
        response = restTemplate.exchange(targetUrl, HttpMethod.POST, request, String.class);

        // 4) save as token file
        JsonParser parser = new JsonParser();
        JsonObject token = new JsonParser().parse(response.getBody()).getAsJsonObject();
        membership.setAccess_token(token.get("access_token").toString().replace("\"", ""));
        System.out.println(membership.getAccess_token());


    }

//    @RequestMapping(path = "test/create/mastercontract", method = RequestMethod.GET)
//    public void postMasterContract() {
//
//        JsonObject source = masterContractNE;
//        System.out.println(source);
//        String targetUrl = "${get.uri.f9s.submit.mastercontract}";
//        RestTemplate restTemplate = new RestTemplate();
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        HttpEntity<String> request =
//                new HttpEntity<String>(source.toString(), headers);
//        restTemplate.postForObject(targetUrl, request, String.class);
//
//    }

//    @RequestMapping(value = "test/create/sellerbot", method = RequestMethod.GET)
//    public void postSellOffer() throws InterruptedException {
//        try {
//            JsonObject source;
//            while (true) {
//                source = createSellOffer();
//                System.out.println(source);
//                String targetUrl = getUriTradeMakeNewOffer();
//                RestTemplate restTemplate = new RestTemplate();
//                HttpHeaders headers = new HttpHeaders();
//                headers.setContentType(MediaType.APPLICATION_JSON);
//                headers.setBasicAuth(membership.getUsrId(), membership.getPassword());
//                headers.setBearerAuth(membership.getAccess_token());
//                HttpEntity<String> request =
//                        new HttpEntity<String>(source.toString(), headers);
//                restTemplate.postForObject(targetUrl, request, String.class);
//                Random rand = new Random();
//                Long stop = (long) (3 * (10 * rand.nextGaussian() + 3));
//                if (stop < 0) {
//                    stop = (long) 1;
//                }
//
//                Thread.sleep(stop);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }

}
