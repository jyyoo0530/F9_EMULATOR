package com.emulator.f9.rest;

import com.emulator.f9.model.Membership;
import com.emulator.f9.model.bot.ftr.mastercontract.*;
import com.emulator.f9.model.bot.ftr.offer.*;
import com.emulator.f9.model.market.index.SCFI;
import com.emulator.f9.model.market.mobility.sea.F9_SEA_SKD;
import com.emulator.f9.model.market.mobility.sea.F9_SEA_SKD_ReactiveMongoRepository;
import com.emulator.f9.model.market.mobility.sea.mdm.*;
import com.emulator.f9.model.market.mobility.sea.miner.maerskSchedule.*;
import com.emulator.f9.model.ftr.*;
import com.emulator.f9.service.MaerskMiningService;
import com.emulator.f9.service.OfferMarketEsitmator;
import com.emulator.f9.service.UnlocodeMDM;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.runner.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.lang.reflect.Array;
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

    @Autowired
    F9_SEA_SKD_GRP_ReactiveMongoRepository scheduleMasterRepo;

    @Autowired
    SCH_GRP_MySqlRepository schGrpMySqlRepo;

    @Autowired
    SCH_GRP_LINE_ITEM_MySqlRepository schGrpLineItemMySqlRepo;

    @Autowired
    SCH_GRP_DETAILS_MySqlRepository schGrpDetailMySqlRepo;

    @Autowired
    SCH_GRP_RTE_MySqlRepository schGrpRteMySqlRepo;

    @Autowired
    MDM_T_SVCE_LANE_MySqlRepository mdmTSvceLaneMySqlRepo;

    @Autowired
    F9_MDM_SVCGRP_ReactiveMongoRepository f9MdmSvcgrpRepo;

    @Autowired
    FMC_MSTR_CTRK_MySqlRepository fmcMstrCtrkMySqlRepo;

    @Autowired
    FMC_MSTR_CTRK_LINE_ITEM_MySqlRepository fmcMstrCtrkLineItemMySqlRepo;

    @Autowired
    FMC_MSTR_CTRK_CGO_TP_MySqlRepository fmcMstrCtrkCgoTpMySqlRepo;

    @Autowired
    FMC_MSTR_CTRK_CRYR_MySqlRepository fmcMstrCtrkCryrMySqlRepo;

    @Autowired
    FMC_MSTR_CTRK_RTE_MySqlRepository fmcMstrCtrkRteMySqlRepo;

    @Autowired
    FMC_MSTR_CTRK_PRCE_LST_MySqlRepository fmcMstrCtrkPrceLstMySqlRepo;


    Membership membership = new Membership();

    @RequestMapping(value = "mobility/sea/schedule/msk/{idx}", method = RequestMethod.GET)
    public void digMaerskSchedule(@PathVariable("idx") int idx) {
        MaerskMiningService miningMaersk = new MaerskMiningService();
        MdmVesselCloneService mdmVesselCloneService = new MdmVesselCloneService();

        // --) get active port list
        ArrayList<F9E_MSK_ACTIVEPORT> activePorts = miningMaersk.getActivePorts();
        ArrayList<F9E_MSK_ACTIVEVESSEL> activeVessels = miningMaersk.getActiveVessels();

        // 1) get active vessel list
        ArrayList<String> vesselList = miningMaersk.getVesselList();

        // --) get into loop (parallel computing으로 가야함..)
        for (int z = idx; z < vesselList.size(); z++) {
            String x = vesselList.get(z);

            // 2) check if F9_VSL_MDM exists
            boolean chkVsl = miningMaersk.checkF9eMdmVsl(x, f9MdmVslRepo);
            String activeVesselName = activeVessels.stream()
                    .filter(b -> b.getCode().equals(x)).collect(Collectors.toList()).get(0).getName();

            // 3) get Vessel Detail
            F9E_MSK_VSLMDM mskVslMdm = miningMaersk.getVesselDetail(x, activeVesselName);

            // --) update Vessel MDM
            if (!chkVsl) {
                miningMaersk.updateVesselMdm(mskVslMdm, f9MdmVslRepo);
                mdmVesselCloneService.cloneMdmVessel(mskVslMdm, mdmTVslMySqlRepo); /// Temporary
            }

            // --) check if vesselCapacityTeu exists
            if (mskVslMdm.getCapacityTeu().replaceAll("[^0-9]", "").length() == 0) {
                System.out.println("///////////////// " + "This vessel mdm does not define any supply Teus..Skipping" + " /////////////////");
                System.out.println("///////////////// " + vesselList.indexOf(x) + " out of " + vesselList.size() + " completed..!!" + " /////////////////");
                continue;
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
//            listF9eSeaSkd = Stream.concat(listF9eSeaSkd.stream(), extendedList.stream()).collect(Collectors.toCollection(ArrayList::new));
            while (extendedList.size() != 0) {

                // 4) add Lists
                calendar.add(Calendar.DATE, 1);
                fromDate = "&fromDate=" + formatter.format(calendar.getTime());
                calendar.add(Calendar.DATE, 29);
                endDate = "&toDate=" + formatter.format(calendar.getTime());
                extendedList = miningMaersk.getVesselSked(x, extensionUri + fromDate + endDate);
                listF9eSeaSkd = Stream.concat(listF9eSeaSkd.stream(), extendedList.stream()).collect(Collectors.toCollection(ArrayList::new));

                // 4-1) generate port lists
                listF9eSeaSkd.forEach(y -> {
                    portList.add(y.getSrcPortCode());
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
                        mdmVesselCloneService.cloneMdmPort(response, f9MdmLocationRepo, mdmTPortMySqlRepo); // TEMPORARY
                    }
                });
            }

            // 5) convert to f9s vessel schedule
            List<F9_SEA_SKD> f9SeaSkds = miningMaersk.parseIntoF9SeaSkd(listF9eSeaSkd, f9MdmLocationRepo, f9MdmVslRepo, x);

            // 6) update F9E_SEA_SKED(MongoDB and MySQL)
            f9SeaSkds.forEach(t -> {
                SCH_SRC u = new SCH_SRC();
                u.setAllData(t);
                List<F9_SEA_SKD> chkSrcList = f9SeaSkdRepo.findByServiceLaneKeyAndVoyageNumberAndVesselKeyAndFromKeyAndToKey(
                        t.getServiceLaneKey(), t.getVoyageNumber(), t.getVesselKey(), t.getFromKey(), t.getToKey()
                ).collect(Collectors.toList()).block();
                F9_SEA_SKD chkSrc = Collections.max(chkSrcList, Comparator.comparing(F9_SEA_SKD::getScheduleSeq));
                if (chkSrc == null
                ) {
                    miningMaersk.uploadF9SeaSkd(t, f9SeaSkdRepo);
                    miningMaersk.uploadF9SeaSkdMySQL(u, schSrcMySqlRepo); // TEMPORARY
                } else if (
                        !chkSrc.getFromETA().equals(t.getFromETA()) ||
                                !chkSrc.getFromETB().equals(t.getFromETB()) ||
                                !chkSrc.getFromETD().equals(t.getFromETD()) ||
                                !chkSrc.getToETA().equals(t.getToETA()) ||
                                !chkSrc.getToETB().equals(t.getToETB()) ||
                                !chkSrc.getToETD().equals(t.getToETD())
                ) {
                    t.setScheduleSeq(chkSrc.getScheduleSeq() + 1);
                    miningMaersk.uploadF9SeaSkd(t, f9SeaSkdRepo);
                    miningMaersk.uploadF9SeaSkdMySQL(u, schSrcMySqlRepo); // TEMPO
                } else if (
                        chkSrc.getFromETA().equals(t.getFromETA()) &&
                                chkSrc.getFromETB().equals(t.getFromETB()) &&
                                chkSrc.getFromETD().equals(t.getFromETD()) &&
                                chkSrc.getToETA().equals(t.getToETA()) &&
                                chkSrc.getToETB().equals(t.getToETB()) &&
                                chkSrc.getToETD().equals(t.getToETD())
                ) {
                    System.out.println("Already Exists!!");
                } else {
                    miningMaersk.uploadF9SeaSkd(t, f9SeaSkdRepo);
                    miningMaersk.uploadF9SeaSkdMySQL(u, schSrcMySqlRepo);
                }
            });
            System.out.println("///////////////// " + vesselList.indexOf(x) + " out of " + vesselList.size() + " completed..!!" + " /////////////////");
        }
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
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("Authorization", "Basic " + membership.getCredential());
        HttpEntity<String> request = new HttpEntity<String>(headers);

        // 3) get response
        response = restTemplate.exchange(targetUrl, HttpMethod.POST, request, String.class);

        // 4) save as token file
        JsonParser parser = new JsonParser();
        JsonObject token = new JsonParser().parse(Objects.requireNonNull(response.getBody())).getAsJsonObject();
        membership.setAccess_token(token.get("access_token").toString().replace("\"", ""));
        System.out.println(membership.getAccess_token());
    }

    @RequestMapping(value = "generateScheduleGroup", method = RequestMethod.GET)
    public void generateScheduleGroup() {
        // --) scheduleMasters 생
        List<F9_SEA_SKD_GRP> scheduleMasters = new ArrayList<>();
        // 1) Service Lane List 생성
        List<String> serviceLaneNames =
                mongoTemplate.query(F9_SEA_SKD.class).distinct("serviceLaneName").as(String.class).all();
        List<String> finalServiceLaneNames = serviceLaneNames.stream().distinct().collect(Collectors.toList());
        System.out.println("Logging      :" + " There are " + serviceLaneNames.size() + " serviceLanes to be arranged");

        // 2) ServiceLane.foreach()
        finalServiceLaneNames.forEach(a -> {
            List<F9_SEA_SKD> f9SeaSkds = f9SeaSkdRepo.findByServiceLaneName(a).collect(Collectors.toList()).block();
            Query query = new Query();
            query.addCriteria(Criteria.where("serviceLaneName").is(a));

            // 3) Iterator -> by direction & vesselCode & voyageNumber
            List<String> voyageDirection = new ArrayList<>();
            assert f9SeaSkds != null;
            f9SeaSkds.forEach(a1 -> {
                String chk = a1.getVoyageNumber().replaceAll("[0-9]", "");
                voyageDirection.add(chk);
            });
            List<String> finalVoyageDirection = voyageDirection.stream().distinct().collect(Collectors.toList());

            // 3-1) direction forEach
            finalVoyageDirection.forEach(b -> {
                // ---) filtered F9_SEA_SKD 생성
                List<F9_SEA_SKD> voyDirFilteredF9SeaSkds = f9SeaSkds.stream().filter(t -> t.getVoyageNumber().replaceAll("[0-9]", "").equals(b)).collect(Collectors.toList());
                // ---) 다음 filter target 생성(vesselCode)
                List<String> vesselCodes = new ArrayList<>();
                voyDirFilteredF9SeaSkds.forEach(b1 -> vesselCodes.add(b1.getVesselKey()));
                List<String> finalVesselCodes = vesselCodes.stream().distinct().collect(Collectors.toList());
                // ---) Generate ScheduleLinesGroup
                F9_SEA_SKD_GRP scheduleMaster = new F9_SEA_SKD_GRP();
                List<ScheduleLineGroup> scheduleLineGroups = new ArrayList<>();
                List<ScheduleRouteGroup> scheduleRouteGroups = new ArrayList<>();


                // ---) generate RouteGroup
                List<String> polCodes = new ArrayList<>();
                List<String> podCodes = new ArrayList<>();
                voyDirFilteredF9SeaSkds.forEach(b2 -> {
                    polCodes.add(b2.getFromKey());
                    podCodes.add(b2.getToKey());
                });
                List<String> finalPolCodes = polCodes.stream().distinct().collect(Collectors.toList());
                List<String> finalPodCodes = podCodes.stream().distinct().collect(Collectors.toList());
                List<String> sizes = new ArrayList<>();
                sizes.add("Start");
                finalPolCodes.forEach(b3 -> {
                    finalPodCodes.forEach(b4 -> {
                        for (int i = 1; i < 5; i++) {
                            ScheduleRouteGroup scheduleRouteGroup = new ScheduleRouteGroup();
                            switch (i) {
                                case 1:
                                    scheduleRouteGroup.setLocationCode(b3);
                                    break;
                                case 2:
                                    scheduleRouteGroup.setLocationCode(b3);
                                    break;
                                case 3:
                                    scheduleRouteGroup.setLocationCode(b4);
                                    break;
                                case 4:
                                    scheduleRouteGroup.setLocationCode(b4);
                                    break;
                            }
                            scheduleRouteGroup.setLocationTypeCode("0" + i);
                            scheduleRouteGroup.setRegSeq(sizes.size());
                            scheduleRouteGroups.add(scheduleRouteGroup);
                        }
                        sizes.add("and");
                    });
                });

                // 3-2) vesselCode forEach
                finalVesselCodes.forEach(c -> {
                    // ---) filtered F9_SEA_SKD 생성
                    List<F9_SEA_SKD> vslFilteredF9SeaSkds = voyDirFilteredF9SeaSkds.stream().filter(t -> t.getVesselKey().equals(c)).collect(Collectors.toList());
                    // ---) 다음 filter target 생성(voyageNumber)
                    List<String> voyageNumbers = new ArrayList<>();
                    vslFilteredF9SeaSkds.forEach(c1 -> voyageNumbers.add(c1.getVoyageNumber()));
                    List<String> finalVoyageNumbers = voyageNumbers.stream().distinct().collect(Collectors.toList());

                    // 3-3) voyageNumber forEach
                    finalVoyageNumbers.forEach(d -> {
                        // ---) filtered F9_SEA_SKD 생성
                        List<F9_SEA_SKD> voyNrFilteredF9SeaSkds = vslFilteredF9SeaSkds.stream().filter(t -> t.getVoyageNumber().equals(d)).collect(Collectors.toList());
                        ScheduleLineGroup scheduleLineGroup = new ScheduleLineGroup();
                        List<ScheduleAbstractDetail> scheduleAbstractDetails = new ArrayList<>();
                        // 4) GenerationStart!
                        //////////////////////
                        // 4-1) generate Abstract Schedule
                        voyNrFilteredF9SeaSkds.forEach(d1 -> {
                            ScheduleAbstractDetail scheduleAbstractDetail = new ScheduleAbstractDetail();
                            scheduleAbstractDetail.setInitialScheduleSeq(d1.getScheduleSeq());
                            scheduleAbstractDetail.setScheduleId(d1.getScheduleId());
                            scheduleAbstractDetails.add(scheduleAbstractDetail);
                        });
                        // 4-2) Add Abstract Schedule into ScheduleGroup
                        IntSummaryStatistics intSummaryStatistics = voyNrFilteredF9SeaSkds.stream()
                                .map(F9_SEA_SKD::getServiceProductWeek)
                                .mapToInt(Integer::parseInt)
                                .summaryStatistics();
                        scheduleLineGroup.setProductWeek(String.valueOf(intSummaryStatistics.getMin()));
                        scheduleLineGroup.setScheduleAbstractDetails(scheduleAbstractDetails);
                        scheduleLineGroup.setScheduleGroupId(a + intSummaryStatistics.getMin() + "");
                        scheduleLineGroup.setVesselCode(c);
                        scheduleLineGroup.setVesselCapacityTeu(voyNrFilteredF9SeaSkds.get(0).getVesselCapacityTeu());
                        scheduleLineGroups.add(scheduleLineGroup);
                    });
                });
                // 4-3) Put Service Group
                scheduleMaster.setScheduleLineGroup(scheduleLineGroups);
                scheduleMaster.setScheduleRouteGroup(scheduleRouteGroups);
                scheduleMaster.setServiceCode(
                        Objects.requireNonNull(f9SeaSkdRepo.findByServiceLaneName(a).blockFirst()).getServiceLaneKey()
                );
                scheduleMaster.setServiceName(a);
                scheduleMaster.setServiceDirection(b);
                IntSummaryStatistics intSummaryStatistics2 = scheduleLineGroups.stream()
                        .map(ScheduleLineGroup::getVesselCapacityTeu)
                        .mapToInt(Integer::intValue)
                        .summaryStatistics();
                scheduleMaster.setWeeklySupplyTeu((int) intSummaryStatistics2.getAverage());
                scheduleMasters.add(scheduleMaster);
                //////////////////////
            });
        });
        // 5) set data 1(주차) : N(scheduleId)
        // --> 현재로써는: 1. 웹 ui구현해서 디비수정, 2) 디비 직접 수정.

        // 6) SVC + SVC Weeks + SVC Routes
        scheduleMasterRepo.saveAll(scheduleMasters).blockLast();
        System.out.println("/////////////////" + scheduleMasters.size() + " are updated" + "///////////////////");
    }

    @RequestMapping(value = "transferScheduleGroup/ftr", method = RequestMethod.GET)
    public void transferScheduleGroup() {
        // --) find All group from F9S
        List<F9_SEA_SKD_GRP> scheduleMasters = scheduleMasterRepo.findAll().collect(Collectors.toList()).block();
        // 1) into the loop
        scheduleMasters.forEach(a -> {
            // 2) update Master -> ftr.SCH_GRP
            SCH_GRP schGrp = new SCH_GRP();
            schGrp.setSchGrpId(a.getServiceCode() + a.getServiceDirection());
            schGrp.setSchGrpSvcCd(a.getServiceCode());
            schGrp.setSchGrpSvcNm(a.getServiceName());
            schGrp.setSchGrpSvcDir(a.getServiceDirection());
            schGrp.setSchGrpVol(a.getWeeklySupplyTeu());
            schGrpMySqlRepo.save(schGrp);
            // 3) update LineGroup -> ftr.SCH_GRP_LINE_ITEM-----> unique 하게 만들어야됨!!!
            a.getScheduleLineGroup().forEach(b -> {
                SCH_GRP_LINE_ITEM schGrpLineItem = new SCH_GRP_LINE_ITEM();
                schGrpLineItem.setSchGrpLnId(b.getScheduleGroupId());
                schGrpLineItem.setSchGrpLnPrdWk(b.getProductWeek());
                schGrpLineItem.setSchGrpLnVslCd(b.getVesselCode());
                schGrpLineItem.setSchGrpLnVslCap(b.getVesselCapacityTeu());
                schGrpLineItem.setSchGrpId(a.getServiceCode() + a.getServiceDirection());
                schGrpLineItemMySqlRepo.save(schGrpLineItem);
                // 4) update Details -> ftr.SCH_GRP_DETAILS
                b.getScheduleAbstractDetails().forEach(c -> {
                    SCH_GRP_DETAILS schGrpDetails = new SCH_GRP_DETAILS();
                    schGrpDetails.setSchId(c.getScheduleId());
                    schGrpDetails.setSchSeq(c.getInitialScheduleSeq());
                    schGrpDetails.setSchSeqLineId(b.getScheduleGroupId());
                    schGrpDetailMySqlRepo.save(schGrpDetails);
                });
            });
            // 5) update details -> ftr.SCH_GRP_RTE
            a.getScheduleRouteGroup().forEach(b1 -> {
                SCH_GRP_RTE schGrpRte = new SCH_GRP_RTE();
                schGrpRte.setSchRteSeq(b1.getRegSeq());
                schGrpRte.setSchRteLocCd(b1.getLocationCode());
                schGrpRte.setSchRteLocTpCd(b1.getLocationTypeCode());
                schGrpRte.setSchGrpId(a.getServiceCode() + a.getServiceDirection());
                schGrpRteMySqlRepo.save(schGrpRte);
            });
        });

    }

    @RequestMapping(value = "scheduleGroupList/{targetSource}/get", method = RequestMethod.GET)
    public ResponseEntity<List<String>> getScheduleGroupList(@PathVariable("targetSource") String targetSource) {
        List<String> scheduleList = new ArrayList<>();
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("MyResponseHeader", "MyValue");
        switch (targetSource) {
            case "ftr":
                schGrpMySqlRepo.findAll().forEach(a -> scheduleList.add("serviceCode: " + a.getSchGrpSvcCd() + "  ," + "serviceDirection:" + a.getSchGrpSvcDir() + "  ," + "weeklyCapacity:" + a.getSchGrpVol()));
            case "f9s":
                List<F9_SEA_SKD_GRP> scheduleMasters = scheduleMasterRepo.findAll().collect(Collectors.toList()).block();
                scheduleMasters.forEach(a -> scheduleList.add("serviceCode: " + a.getServiceCode() + "  ," + "serviceDirection:" + a.getServiceDirection() + "  ," + "weeklyCapacity:" + a.getWeeklySupplyTeu()));
        }
        return new ResponseEntity<List<String>>(scheduleList, responseHeaders, HttpStatus.CREATED);

    }

    @RequestMapping(value = "createMasterContract/{serviceCode}/{serviceDirection}", method = RequestMethod.GET)
    public void createMasterContract(@PathVariable("serviceCode") String serviceCode,
                                     @PathVariable("serviceDirection") String serviceDirection
    ) {
        MasterContract masterContract = new MasterContract();
        List<MasterContractRoutes> masterContractRoutes = new ArrayList<>();
        List<MasterContractCargoType> masterContractCargoTypes = new ArrayList<>();
        List<MasterContractCarriers> masterContractCarriers = new ArrayList<>();
        List<MasterContractLineItem> masterContractLineItems = new ArrayList<>();

        F9_SEA_SKD_GRP selectedMaster = scheduleMasterRepo.findByServiceCodeAndServiceDirection(serviceCode, serviceDirection).block();

        masterContract.setCarrierCode("USR00");
        masterContract.setServiceLaneCode(serviceCode);
        masterContract.setDeleteYn("0");
        masterContract.setPaymentPlanCode("01PS");
        masterContract.setPaymentTermCode("01");
        masterContract.setRdTermCode("01");
        assert selectedMaster != null;
        masterContract.setServiceLaneCode(selectedMaster.getServiceCode());

        // MasterContractRoute
        selectedMaster.getScheduleRouteGroup().forEach(a -> {
            MasterContractRoutes masterContractRoute = new MasterContractRoutes();
            masterContractRoute.setDeleteYn("0");
            masterContractRoute.setLocationCode(a.getLocationCode());
            masterContractRoute.setLocationTypeCode(a.getLocationTypeCode());
            masterContractRoute.setRegSeq(a.getRegSeq());
            masterContractRoutes.add(masterContractRoute);
        });
        masterContract.setMasterContractRoutes(masterContractRoutes);
        MasterContractCargoType masterContractCargoType = new MasterContractCargoType();
        masterContractCargoType.setAllData("01");
        masterContractCargoTypes.add(masterContractCargoType);
        masterContract.setMasterContractCargoTypes(masterContractCargoTypes);

        // MasterContractCarriers
        MasterContractCarriers masterContractCarrier = new MasterContractCarriers();
        masterContractCarrier.setCarrierCode("MSK");
        masterContractCarrier.setDeleteYn("0");
        masterContractCarriers.add(masterContractCarrier);
        masterContract.setMasterContractCarriers(masterContractCarriers);

        // MasterContractLineItem
        selectedMaster.getScheduleLineGroup().forEach(a1 -> {
            List<MasterContractPrices> masterContractPrices = new ArrayList<>();
            MasterContractLineItem masterContractLineItem = new MasterContractLineItem();
            if (masterContractLineItems.stream().noneMatch(b -> b.getBaseYearWeek().equals(a1.getProductWeek()))) {
                masterContractLineItem.setBaseYearWeek(a1.getProductWeek());
                masterContractLineItem.setDeleteYn("0");
                masterContractLineItem.setQty(a1.getVesselCapacityTeu());
                masterContractLineItem.setScheduleGroupLineId(a1.getScheduleGroupId());

                for (int i = 1; i < 5; i++) {
                    for (int j = 1; j < 5; j++) {
                        MasterContractPrices masterContractPrice = new MasterContractPrices();
                        masterContractPrice.setContainerSizeCode("0" + i);
                        masterContractPrice.setContainerTypeCode("0" + j);
                        masterContractPrice.setDeleteYn("0");
                        masterContractPrice.setPrice(1000);
                        masterContractPrices.add(masterContractPrice);
                    }
                }

                masterContractLineItem.setMasterContractPrices(masterContractPrices);
                masterContractLineItems.add(masterContractLineItem);
            } else {
                System.out.println("Already Exists!");
            }
        });
        masterContract.setMasterContractLineItems(masterContractLineItems);

        // parse
        ObjectMapper mapper = new ObjectMapper();
        try {
            String result = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(masterContract).replaceAll("\\r", "").replaceAll("\\n", "");
            System.out.println(result);
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> request = new HttpEntity<String>(result, headers);
            restTemplate.postForObject("http://api.freight9.com:8080/api/v1/contract/temp/new", request, String.class);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "bot/makeSellOffer", method = RequestMethod.GET)
    public void makeSellOffer() {
    }

    @RequestMapping(value = "generateServiceLane", method = RequestMethod.GET)
    public void updateSvcLane() {
        List<F9_MDM_SVCGRP> f9MdmSvcgrps = new ArrayList<>();
        String baseUrl = "https://api.maersk.com/oceanProducts/maeu/futureschedules";
        String from = "?from="; // ADD ORIGIN GEOID
        String to = "&to="; // ADD DEST ORIGIN GEOID
        String cargoType = "&cargoType=DRY";
        String containerTypeName = "&containerTypeName=40%27+Dry+Standard";
        String containerType = "&containerType=DRY";
        String containerLength = "&containerLength=40";
        String containerIsoCode = "&containerIsoCode=42G1";
        String containerTempControl = "&containerTempControl=false";
        String fromServiceMode = "&fromServiceMode=CY";
        String toServiceMode = "&toServiceMode=CY";
        String numberOfWeeks = "&numberOfWeeks=4";
        String dateType = "&dataType=D";
        String date = "&date="; // ADD TODAY
        String vesselFlag = "&vesselFlag=";
        String vesselFlagName = "&vesselFlagName=";
        String originLocation = "&originLocation="; // ADD ORIGIN NAME
        String destinationLocation = "&destinationLocation="; // ADD DESTINATION NAME
        String originServiceMode = "&originServiceMode=CY";
        String destinationServiceMode = "&destinationServiceMode=CY";

        // 1) get list of items to be udpated
        List<F9_SEA_SKD_GRP> f9SeaSkdGrps = scheduleMasterRepo.findAll().collect(Collectors.toList()).block();
        f9SeaSkdGrps.forEach(a -> {
            F9_MDM_SVCGRP f9MdmSvcgrp = new F9_MDM_SVCGRP();
            ScheduleRouteGroup scheduleRouteGroup = new ScheduleRouteGroup();
            scheduleRouteGroup.setLocationCode(a.getScheduleRouteGroup().get(1).getLocationCode());

            // 1-1) set serviceCode
            f9MdmSvcgrp.setServiceCode("MSK" + a.getServiceCode());

            // 1-2) set serviceName
            String originLocationCode = a.getScheduleRouteGroup().get(1).getLocationCode();
            String destinationLocationCode = a.getScheduleRouteGroup().get(2).getLocationCode();
            String fromGeoId = f9MdmLocationRepo.findByLocationCodeAndMdmOwnerCode(originLocationCode, "MSK").blockFirst().getMdmOwnerLocationId();
            String toGeoId = f9MdmLocationRepo.findByLocationCodeAndMdmOwnerCode(destinationLocationCode, "MSK").blockFirst().getMdmOwnerLocationId();
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String now = formatter.format(calendar.getTime());

            RestTemplate restTemplate = new RestTemplate();
            String response = restTemplate.getForObject(
                    baseUrl + from + fromGeoId + to + toGeoId + cargoType + containerTypeName + containerType + containerLength + containerIsoCode + containerTempControl + fromServiceMode
                            + toServiceMode + numberOfWeeks + dateType + date + now + vesselFlag + vesselFlagName + originLocation + originLocationCode + destinationLocation + destinationLocationCode + originServiceMode + destinationServiceMode,
                    String.class);
            JsonArray products = new JsonParser().parse(response).getAsJsonObject().get("products").getAsJsonArray();
            System.out.println("test");
            for (int i = 0; i < products.size(); i++) {
                try {
                    JsonArray step1 = products.get(i).getAsJsonObject().get("schedules").getAsJsonArray();
                    JsonObject step2 = step1.get(0).getAsJsonObject();
                    JsonArray step3 = step2.get("scheduleDetails").getAsJsonArray();
                    JsonObject step4 = step3.get(0).getAsJsonObject();
                    String serviceName = step4.get("serviceName").toString().replace("\"", "");
                    String serviceCode = products.get(i).getAsJsonObject().get("schedules").getAsJsonArray().get(0).getAsJsonObject().get("scheduleDetails").getAsJsonArray().get(0).getAsJsonObject()
                            .get("serviceCode").toString().replace("\"", "");
                    if (serviceCode.equals(a.getServiceCode())) {
                        f9MdmSvcgrp.setServiceName(serviceName);
                        break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    f9MdmSvcgrp.setServiceName(a.getServiceCode() + "_NotYetDefined");
                }
            }

            // 1-3) set mdmOwnerCode
            f9MdmSvcgrp.setMdmOwnerCode("MSK");

            // 1-4) set mdmOwnerSvcgrpId
            f9MdmSvcgrp.setMdmOwnerSvcCd(a.getServiceCode());
            MDM_T_SVCE_LANE mdmTSvceLane = new MDM_T_SVCE_LANE();
            mdmTSvceLane.setAllData(f9MdmSvcgrp);

            // 1-5) update
            if (f9MdmSvcgrp.getServiceName() != null) {
                if (mdmTSvceLaneMySqlRepo.findByMdmSvcCd(f9MdmSvcgrp.getServiceCode()).size() == 0) {
                    mdmTSvceLaneMySqlRepo.save(mdmTSvceLane);
                }
                if (f9MdmSvcgrpRepo.findByServiceCode(f9MdmSvcgrp.getServiceCode()).collect(Collectors.toList()).block().size() == 0) {
                    f9MdmSvcgrpRepo.save(f9MdmSvcgrp).block();
                }
                System.out.println("DONE!!");
            }
        });

    }

    @RequestMapping(value = "market/createSellOffer/{masterContractNumber}/{iterator}", method = RequestMethod.GET)
    public void initializeMarket(@PathVariable("masterContractNumber") String masterContractNumber,
                                 @PathVariable("iterator") int iterator

    ) throws IOException {
        OfferMarketEsitmator estimator = new OfferMarketEsitmator();
        List<SCFI> priceNE = estimator.initialize("priceNE.csv");
//        List<SCFI> priceME = estimator.initialize("priceME.csv");
        for (int i = 0; i < iterator; i++) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                Offer offer = new Offer();
                offer.setAllYn("0");
                offer.setExpireYn("0");
                offer.setMasterContractNumber(masterContractNumber);
                offer.setOfferPaymentPlanCode("01PS");
                offer.setOfferPaymentTermCode("01");
                offer.setOfferRdTermCode("01");
                offer.setOfferTransactionTypeCode("01");
                offer.setOfferTypeCode("S");
                offer.setTimeInForceCode("0");
                offer.setTradeCompanyCode("USR00");
                offer.setTradeMarketTypeCode("01");
                offer.setTradeRoleCode("001");
                offer.setVirtualGroupAgreementYn("0");
                offer.setVirtualGroupOfferYn("0");
                offer.setWhatIfAgreementYn("0");

                List<OfferLineItems> offerLineItems = new ArrayList<>();
                List<FMC_MSTR_CTRK_LINE_ITEM> fmcMstrCtrkLineItems = fmcMstrCtrkLineItemMySqlRepo.findBymstrCtrkNr(masterContractNumber);
                fmcMstrCtrkLineItems.forEach(a -> {
                    Random rand = new Random();
                    List<OfferPrices> offerPrices = new ArrayList<>();
                    List<FMC_MSTR_CTRK_PRCE_LST> fmcMstrCtrkPrceLst = fmcMstrCtrkPrceLstMySqlRepo.findBymstrCtrkNrAndBseYw(masterContractNumber, a.getBseYw());
                    OfferLineItems offerLineItem = new OfferLineItems();
                    offerLineItem.setBaseYearWeek(a.getBseYw());
                    offerLineItem.setBalancedPaymentRatio(0.1);
                    offerLineItem.setFirstPaymentRatio(0.3);
                    offerLineItem.setMiddlePaymentRatio(0.6);
                    int basePrice = priceNE.stream().filter(a1 -> a1.getBaseYearWeek().equals(a.getBseYw())).collect(Collectors.toList()).get(0).getPriceIndex();
                    int simResult = (int) (rand.nextGaussian() * (basePrice * 0.1) + basePrice);
                    offerLineItem.setOfferPrice(simResult);
                    offerLineItem.setOfferQty(30);
                    fmcMstrCtrkPrceLst.forEach(a2 -> {
                        OfferPrices offerPrice = new OfferPrices();
                        offerPrice.setContainerSizeCode(a2.getTrdeContSizeCd());
                        offerPrice.setContainerTypeCode(a2.getTrdeContTpCd());
                        offerPrice.setOfferPrice(a2.getPrice());
                        offerPrices.add(offerPrice);
                    });
                    offerLineItem.setOfferPrices(offerPrices);
                    offerLineItem.setTradeContainerSizeCode("01");
                    offerLineItem.setTradeContainerTypeCode("01");
                    offerLineItems.add(offerLineItem);
                });
                offer.setOfferLineItems(offerLineItems);

                List<OfferCarriers> offerCarriers = new ArrayList<>();
                OfferCarriers offerCarrier = new OfferCarriers();
                offerCarrier.setOfferCarrierCode("MSK");
                offerCarriers.add(offerCarrier);
                offer.setOfferCarriers(offerCarriers);

                List<OfferRoutes> offerRoutes = new ArrayList<>();
                List<FMC_MSTR_CTRK_RTE> fmcMstrCtrkRtes = fmcMstrCtrkRteMySqlRepo.findBymstrCtrkNr(masterContractNumber);
                fmcMstrCtrkRtes.forEach(b -> {
                    OfferRoutes offerRoute = new OfferRoutes();
                    offerRoute.setLocationCode(b.getLocCd());
                    String locationName;

                    if (mdmTPortMySqlRepo.findByMdmOwnerCodeAndLocationCode("MSK", b.getLocCd()) != null) {
                        locationName = mdmTPortMySqlRepo.findByMdmOwnerCodeAndLocationCode("MSK", b.getLocCd()).getLocationName();
                    } else {
                        locationName = mdmTPortMySqlRepo.findByMdmOwnerCodeAndLocationCode("F9M", b.getLocCd()).getLocationName();
                    }

                    offerRoute.setLocationName(locationName);
                    offerRoute.setLocationTypeCode(b.getLocTpCd());
                    offerRoute.setOfferRegSeq(b.getRegSeq());
                    offerRoutes.add(offerRoute);
                });
                offer.setOfferRoutes(offerRoutes);

                getJWTToken();
                String result = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(offer).replaceAll("\\r", "").replaceAll("\\n", "");
                System.out.println(result);
                RestTemplate restTemplate = new RestTemplate();
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                headers.setBearerAuth(membership.getAccess_token());
                HttpEntity<String> request = new HttpEntity<String>(result, headers);
                System.out.println("....sending");
                System.out.println(".....sending");

                restTemplate.postForObject(UriStore.getUriTradeMakeNewOffer(), request, String.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @RequestMapping(value = "mdm/createMskMdm", method = RequestMethod.GET)
    public void createMskMdm() {
        MaerskMiningService miningService = new MaerskMiningService();
        // 1) Active Port List를 모두 가져온다.
        List<F9E_MSK_ACTIVEPORT> f9EMskActiveports = miningService.getActivePorts();
        f9EMskActiveports.forEach(a -> {
            // 2) port-detail 정보 가져온다. (list or not list)
            F9E_MSK_PORTMDM f9EMskPortmdm = miningService.getPortMdm2(a.getLocationName(), a.getGeoId());
            if (mdmTPortMySqlRepo.findByMdmOwnerCodeAndLocationCode("MSK", f9EMskPortmdm.getMaerskRkstCode()) == null) {
                // 3) check
                int process = miningService.checkF9eMdmLocation2(f9EMskPortmdm, mdmTPortMySqlRepo);
                // 4) update
                miningService.updateF9MdmLocation2(f9EMskPortmdm, mdmTPortMySqlRepo, process);
            }
        });


    }

    //temporaray
    @RequestMapping(value = "testtesttest", method = RequestMethod.GET)
    public void updateUndefined() {
        mdmTPortMySqlRepo.findByLocationStatus("UNDEFINED").forEach(
                a -> {
                    String mdmOwnerCode = "F9M";
                    String locationCode = a.getLocationCode();

                    MDM_T_PORT mdmTPort = mdmTPortMySqlRepo.findByMdmOwnerCodeAndLocationCode(mdmOwnerCode, locationCode);
                    try {
                        if (mdmTPort == null) {
                            String countryCode = a.getCountryCode();
                            String locationName = a.getLocationName();
                            mdmTPort = mdmTPortMySqlRepo.findByMdmOwnerCodeAndCountryCodeAndLocationName(mdmOwnerCode, countryCode, locationName);
                        }
                        a.setRegionCode(mdmTPort.getRegionCode());
                        a.setRegionName(mdmTPort.getRegionName());
                        a.setSubRegionCode(mdmTPort.getSubRegionCode());
                        a.setSubRegionName(mdmTPort.getSubRegionName());
                        a.setSubCountryCode(mdmTPort.getSubCountryCode());
                        a.setSubCountryName(mdmTPort.getSubCountryName());
                        a.setLocationStatus(mdmTPort.getLocationStatus());
                        a.setLocationFunction(mdmTPort.getLocationFunction());
                        a.setLocationLatitude(mdmTPort.getLocationLatitude());
                        a.setLocationLongitude(mdmTPort.getLocationLongitude());
                        mdmTPortMySqlRepo.save(a);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
        );
    }

}
