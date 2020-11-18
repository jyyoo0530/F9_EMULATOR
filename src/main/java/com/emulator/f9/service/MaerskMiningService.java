package com.emulator.f9.service;

import com.emulator.f9.model.market.mobility.sea.mdm.F9_MDM_LOCATION;
import com.emulator.f9.model.market.mobility.sea.mdm.F9_MDM_LOCATION_ReactiveMongoRepository;
import com.emulator.f9.model.market.mobility.sea.mdm.F9_MDM_VSL;
import com.emulator.f9.model.market.mobility.sea.mdm.F9_MDM_VSL_ReactiveMongoRepository;
import com.emulator.f9.model.market.mobility.sea.miner.Converter_maerskSchedule;
import com.emulator.f9.model.market.mobility.sea.miner.maerskSchedule.*;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static com.emulator.f9.rest.UriStore.*;

@Service
public class MaerskMiningService {
    String maerskVesselActiveUri = getUriMarketMobilityVessel_MaerskActive();
    String maerskVesselDetailUri = getUriMarketMobilityVessel_MaerskDetail();
    String maerskPortDetailUri = getUriMarketMobilityPort_MaerskDetail();
    String maerskSeaSked = getUriMarketMobilitySeaSked();
    String maerskPortActiveUri = getUriMarketMobilityPort_MaerskActive();

    RestTemplate restTemplate = new RestTemplate();
    Converter_maerskSchedule mskConverter = new Converter_maerskSchedule();

    public ArrayList<F9E_MSK_ACTIVEPORT> getActivePorts() {
        ArrayList<F9E_MSK_ACTIVEPORT> activePorts = new ArrayList<>();

        try {
            String response = restTemplate.getForObject(maerskPortActiveUri, String.class);
            JsonArray jsonArray = new JsonParser().parse(response).getAsJsonObject().get("ports").getAsJsonArray();
            jsonArray.forEach(x -> {
                F9E_MSK_ACTIVEPORT object = new F9E_MSK_ACTIVEPORT();
                object.setAllData(x.getAsJsonObject());
                activePorts.add(object);
            });
        } catch (Exception e) {
            System.out.println("Exception!!     :com.f9e.emulator.service.MaerskMiningService.getActivePorts()");
        }
        return activePorts;
    }

    public ArrayList<F9E_MSK_ACTIVEVESSEL> getActiveVessels() {
        ArrayList<F9E_MSK_ACTIVEVESSEL> activeVessels = new ArrayList<>();

        try {
            String response = restTemplate.getForObject(maerskVesselActiveUri, String.class);
            JsonArray jsonArray = new JsonParser().parse(response).getAsJsonObject().get("vessels").getAsJsonArray();
            jsonArray.forEach(x -> {
                F9E_MSK_ACTIVEVESSEL object = new F9E_MSK_ACTIVEVESSEL();
                object.setAllData(x.getAsJsonObject());
                activeVessels.add(object);
            });
        } catch (Exception e) {
            System.out.println("Exception!!     :com.f9e.emulator.service.MaerskMiningService.getActiveVessels()");
        }
        return activeVessels;
    }

    public ArrayList<String> getVesselList() {
        ArrayList<String> vesselList = new ArrayList<>();

        try {
            String response = restTemplate.getForObject(maerskVesselActiveUri, String.class);

            JsonArray jsonArray = new JsonParser().parse(response).getAsJsonObject().get("vessels").getAsJsonArray();
            jsonArray.forEach(x -> {
                vesselList.add(x.getAsJsonObject().get("code").toString().replace("\"", ""));
            });
        } catch (Exception e) {
            System.out.println("Exception!!     :com.f9e.emulator.service.MaerskMiningService.getVesselList()");
        }

        return vesselList;
    }

    public F9E_MSK_VSLMDM getVesselDetail(String vesselCode, String dummyName) {
        F9E_MSK_VSLMDM mskVslMdm = new F9E_MSK_VSLMDM();

        try {
            String url = maerskVesselDetailUri + "maerskCode=" + vesselCode;
            String response = restTemplate.getForObject(url, String.class).replace("\"\"", "\"");
            mskVslMdm.setAllData(response);
        } catch (Exception e) {
            System.out.println("Exception!!     :com.f9e.emulator.service.MaerskMiningService.getVesselDetail()");
            mskVslMdm.setDummyData(vesselCode, dummyName);
        }

        return mskVslMdm;
    }

    public boolean checkF9eMdmVsl(String vesselCode, F9_MDM_VSL_ReactiveMongoRepository f9eMdmVslRepo) {
        boolean result = false;
        try {
            result = f9eMdmVslRepo.findById(vesselCode).block().getSize() != 0;
            return result;
        } catch (Exception e) {
            System.out.println("Exception!!     :com.f9e.emulator.service.MaerskMiningService.checkF9eMdmVsl()");
            return result;
        }

    }

    public void updateVesselMdm(F9E_MSK_VSLMDM mskVesselMdm, F9_MDM_VSL_ReactiveMongoRepository f9eMdmVslRepo) {
        F9_MDM_VSL f9eVesselMdm;
        try {
            f9eVesselMdm = mskConverter.convertVslMdm(mskVesselMdm);
            f9eMdmVslRepo.save(f9eVesselMdm).block();
            ; ////find by ID
        } catch (Exception e) {
            System.out.println("Exception!!     :com.f9e.emulator.service.MaerskMiningService.updateVesselMdm()");
        }
    }

    public ArrayList<F9E_MSK_SKED_VSL> getVesselSked(String vesselCode, String extendedUri) {
        ArrayList<F9E_MSK_SKED_VSL> listF9eMskSkedVsl = new ArrayList<>();

        try {
            String response = restTemplate.getForObject(maerskSeaSked + extendedUri, String.class);
            JsonArray listReponse = new JsonParser().parse(response.replace("\"\"", "\"")).getAsJsonObject().get("ports").getAsJsonArray();
            listReponse.forEach(x -> {
                F9E_MSK_SKED_VSL f9eMskSkedVsl = new F9E_MSK_SKED_VSL();
                f9eMskSkedVsl.setAllData(vesselCode, x.getAsJsonObject());
                listF9eMskSkedVsl.add(f9eMskSkedVsl);
            });
        } catch (Exception e) {
            System.out.println("Exception!!     :com.f9e.emulator.service.MaerskMiningService.getVesselSked()");
        }

        return listF9eMskSkedVsl;
    }

    public F9E_MSK_PORTMDM getPortMdm(String locationName, String locationCode) {
        F9E_MSK_PORTMDM f9eMskPortmdm = new F9E_MSK_PORTMDM();
        List<F9E_MSK_PORTMDM> f9eMskPortmdms = new ArrayList<>();
        String url = maerskPortDetailUri + locationName.replace(" ", "+");
        try {
            String response = restTemplate.getForObject(url, String.class).replace("\"\"", "\"");
            f9eMskPortmdm.setAllData(response);
        } catch (Exception e) {
            try {
                String response = restTemplate.getForObject(url, String.class).replace("\"\"", "\"");
                JsonArray response2 = new JsonParser().parse(response).getAsJsonArray();
                response2.forEach(z -> {
                    F9E_MSK_PORTMDM test = new F9E_MSK_PORTMDM();
                    test.setAllData(z.toString());
                    if (test.getMaerskGeoLocationId() == locationCode) {
                        f9eMskPortmdm.setAllData(z.toString());
                    }
                });
                System.out.println("Warning!!     :..................response is not object but list(object)");
                System.out.println("Warning!!     :com.f9e.emulator.service.MaerskMiningService.getPortMdm()");

            } catch (Exception f) {
                System.out.println("Exception!!   :com.f9e.emulator.service.MaerskMiningService.getPortMdm()");
            }
        }
        return f9eMskPortmdm;
    }

    public boolean checkF9eMdmLocation(String srcCode, F9_MDM_LOCATION_ReactiveMongoRepository f9MdmLocationRepo) {
        try {
            int result = 0 / f9MdmLocationRepo.findById("MSK_" + srcCode).block().getLocationCode().length();
            System.out.println("ByPass!!        :Port MDM \"" + srcCode + "\"  exists in the Database");
            return true;
        } catch (Exception e) {
            System.out.println("                :Port MDM \"" + srcCode + "\"  does not exist");
            System.out.println("Warning!!       :com.f9e.emulator.service.MaerskMiningService.checkF9eMdmLocation()");
            return false;
        }
    }

    public F9_MDM_LOCATION updateF9MdmLocation(F9E_MSK_PORTMDM f9eMskPortmdm, F9_MDM_LOCATION_ReactiveMongoRepository f9MdmLocationRepo) {
        F9_MDM_LOCATION f9MdmLocation = new F9_MDM_LOCATION();
        try {
            f9MdmLocation = mskConverter.convertPortMdm(f9eMskPortmdm, f9MdmLocationRepo);
            f9MdmLocationRepo.save(f9MdmLocation).block();
        } catch (Exception e) {
            System.out.println("Exception!!     :com.f9e.emulator.service.MaerskMiningService.updateF9MdmLocation()");
        }
        return f9MdmLocation;
    }

    public ArrayList<F9E_MSK_SKED_VSL> distinctAndSortF9eMskSkedVsls(ArrayList<F9E_MSK_SKED_VSL> f9eMskSkedVsls) {
        ArrayList<F9E_MSK_SKED_VSL> result = new ArrayList<>();

        try {

        } catch (Exception e) {

        }


        return result;
    }
}


