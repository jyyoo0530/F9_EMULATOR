package com.emulator.f9.service;

import com.emulator.f9.model.ftr.MDM_T_PORT;
import com.emulator.f9.model.ftr.MDM_T_PORT_MySqlRepository;
import com.emulator.f9.model.market.mobility.sea.F9_SEA_SKD;
import com.emulator.f9.model.market.mobility.sea.F9_SEA_SKD_ReactiveMongoRepository;
import com.emulator.f9.model.market.mobility.sea.mdm.F9_MDM_LOCATION;
import com.emulator.f9.model.market.mobility.sea.mdm.F9_MDM_LOCATION_ReactiveMongoRepository;
import com.emulator.f9.model.market.mobility.sea.mdm.F9_MDM_VSL;
import com.emulator.f9.model.market.mobility.sea.mdm.F9_MDM_VSL_ReactiveMongoRepository;
import com.emulator.f9.model.market.mobility.sea.miner.Converter_maerskSchedule;
import com.emulator.f9.model.market.mobility.sea.miner.maerskSchedule.*;
import com.emulator.f9.model.ftr.SCH_SRC;
import com.emulator.f9.model.ftr.SCH_SRC_MySqlRepository;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import static com.emulator.f9.rest.UriStore.*;

@Service
public class MaerskMiningService {
    String maerskVesselActiveUri = getUriMarketMobilityVessel_MaerskActive();
    String maerskVesselDetailUri = getUriMarketMobilityVessel_MaerskDetail();
    String maerskPortDetailUri = getUriMarketMobilityPort_MaerskDetail();
    String maerskPortDetailUri2 = getUriMarketMobilityPort_MaerskDetail2();
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
            e.printStackTrace();
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
            e.printStackTrace();
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
            e.printStackTrace();
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
            e.printStackTrace();
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
            e.printStackTrace();
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
            e.printStackTrace();
        }
    }

    public ArrayList<F9E_MSK_SKED_VSL> getVesselSked(String vesselCode, String extendedUri, F9E_MSK_SKED_VSL_ReactiveMongoRepository f9eMskSkedVslRepo) {
        ArrayList<F9E_MSK_SKED_VSL> listF9eMskSkedVsl = new ArrayList<>();
        try {
            String response = restTemplate.getForObject(maerskSeaSked + extendedUri, String.class);
            JsonArray listReponse = new JsonParser().parse(response.replace("\"\"", "\"")).getAsJsonObject().get("ports").getAsJsonArray();
            listReponse.forEach(x -> {
                F9E_MSK_SKED_VSL f9eMskSkedVsl = new F9E_MSK_SKED_VSL();
                f9eMskSkedVsl.setAllData(vesselCode, x.getAsJsonObject(), f9eMskSkedVslRepo);
                if (!f9eMskSkedVsl.getTerminal().contains("Canal") || !f9eMskSkedVsl.getTerminal().contains("Bunker")) {
                    f9eMskSkedVslRepo.save(f9eMskSkedVsl).block();
                    listF9eMskSkedVsl.add(f9eMskSkedVsl);
                }
            });
        } catch (Exception e) {
            System.out.println("Exception!!     :com.f9e.emulator.service.MaerskMiningService.getVesselSked()");
            e.printStackTrace();
        }
        return listF9eMskSkedVsl;
    }

    public F9E_MSK_PORTMDM getPortMdm(String locationName, String locationCode) {
        F9E_MSK_PORTMDM f9eMskPortmdm = new F9E_MSK_PORTMDM();
        List<F9E_MSK_PORTMDM> f9eMskPortmdms = new ArrayList<>();
        String url = maerskPortDetailUri2 + locationCode;
        try {
            String response = Objects.requireNonNull(restTemplate.getForObject(url, String.class)).replace("\"\"", "\"");
            f9eMskPortmdm.setAllData(response);
        } catch (Exception e) {
            System.out.println("Exception!!   :com.f9e.emulator.service.MaerskMiningService.getPortMdm()");
            e.printStackTrace();
        }
        return f9eMskPortmdm;
    }

    public F9E_MSK_PORTMDM getPortMdm2(String locationName, String geoLocationId) {
        F9E_MSK_PORTMDM f9eMskPortmdm = new F9E_MSK_PORTMDM();
        System.out.println(locationName);
        String url = maerskPortDetailUri + locationName.replace(" ", "+");
        if (!locationName.contains(" - ")) {
            String response = restTemplate.getForObject(url, String.class).replace("\"\"", "\"");

            int length;
            try {
                length = new JsonParser().parse(response).getAsJsonArray().size();
            } catch (Exception e) {
                length = 0;
                System.out.println("MSK Port Detail List is not 'List'");
                e.printStackTrace();
            }
            if (length == 0) {
                try {
                    JsonObject test = new JsonParser().parse(response).getAsJsonObject();
                    System.out.println(test);
                    length = -1;
                } catch (Exception e) {
                    System.out.println("Exception!!   :com.f9e.emulator.service.MaerskMiningService.getPortMdm()");
                    e.printStackTrace();
                }
            }

            switch (length) {
                case -1:
                    f9eMskPortmdm.setAllData(response);
                case 0:
                    System.out.println("Exception!!   :com.f9e.emulator.service.MaerskMiningService.getPortMdm()");
                case 1:
                    String response2 = "";
                    try {
                        JsonArray test = new JsonParser().parse(response.replace("\"\"", "")).getAsJsonArray();
                        response2 = test.get(0).toString().replace("\"\"", "");
                    } catch (Exception e) {
                        response2 = response;
                        System.out.println("Warning!!     :....................response length is 1 and not an array");
                        System.out.println("Warning!!     :com.f9e.emulator.service.MaerskMiningService.getPortMdm()");
                    }
                    f9eMskPortmdm.setAllData(response2);
                default:
                    JsonArray response3 = new JsonParser().parse(response).getAsJsonArray();
                    try {
                        response3.forEach(z -> {
                            F9E_MSK_PORTMDM test = new F9E_MSK_PORTMDM();
                            test.setAllData(z.toString().replace("\"\"", ""));
                            if (test.getMaerskGeoLocationId().equals(geoLocationId)) {
                                f9eMskPortmdm.setAllData(z.toString().replace("\"\"", ""));
                            }
                        });
                    } catch (Exception e) {
                        System.out.println("Exception!!   :...........................There is no matching result !!");
                        System.out.println("Exception!!   :com.f9e.emulator.service.MaerskMiningService.getPortMdm()");
                        e.printStackTrace();
                    }
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

    public void updateF9MdmLocation(F9E_MSK_PORTMDM f9eMskPortmdm, F9_MDM_LOCATION_ReactiveMongoRepository f9MdmLocationRepo, String locationName) {
        try {
            if (locationName.contains(" - ")) {
                F9_MDM_LOCATION f9MdmLocation = new F9_MDM_LOCATION();
                Random rand = new Random();
                f9MdmLocation.setF9LocationId(locationName + rand.nextInt(99999999));
                f9MdmLocation.setLocationCode("UNDEFINED");
                f9MdmLocation.setLocationName(locationName);
                f9MdmLocation.setCountryCode("UNDEFINED");
                f9MdmLocation.setCountryName("UNDEFINED");
                f9MdmLocation.setSubCountryCode("UNDEFINED");
                f9MdmLocation.setSubCountryName("UNDEFINED");
                f9MdmLocation.setSubRegionCode("UNDEFINED");
                f9MdmLocation.setSubRegionName("UNDEFINED");
                f9MdmLocation.setRegionCode("UNDEFINED");
                f9MdmLocation.setRegionName("UNDEFINED");
                f9MdmLocation.setLocationDate("UNDEFINED");
                f9MdmLocation.setLocationLatitude("UNDEFINED");
                f9MdmLocation.setLocationLongitude("UNDEFINED");
                f9MdmLocation.setMdmOwnerLocationId("UNDEFINED");
                f9MdmLocation.setLocationStatus("UNDEFINED");
                f9MdmLocation.setLocationFunction("UNDEFINED");
                f9MdmLocation.setLocationNameWithDiacritics("UNDEFINED");
                f9MdmLocation.setMdmOwnerCode("UNDEFINED");
                f9MdmLocationRepo.save(f9MdmLocation).block();
            } else {
                F9_MDM_LOCATION f9MdmLocation = mskConverter.convertPortMdm(f9eMskPortmdm, f9MdmLocationRepo);
                f9MdmLocationRepo.save(f9MdmLocation).block();
            }
        } catch (Exception e) {
            System.out.println("Exception!!     :com.f9e.emulator.service.MaerskMiningService.updateF9MdmLocation()");
            e.printStackTrace();
        }
    }

    public int checkF9eMdmLocation2(F9E_MSK_PORTMDM f9eMskPortmdm, MDM_T_PORT_MySqlRepository mdmTPortMySqlRepo) {
        int process = 4;
        String result = "fail";
        // 1) Unlocode가 있는지?

        try {
            System.out.println(f9eMskPortmdm.getUnLocCode());
            process = 1;
            result = "success";
        } catch (Exception e) {
            System.out.println("There is no Unlocode in F9E_MSK_PORTMDM");
        }


        // 2) 1)이 실패면, 도시명 + 국가명이 일치하는 것이 있는지
        if (result.equals("fail")) {
            try {
                System.out.println(mdmTPortMySqlRepo.findByMdmOwnerCodeAndCountryCodeAndLocationName("F9M", f9eMskPortmdm.getCountryCode(), f9eMskPortmdm.getCityName()));
                process = 2;
                result = "success";
            } catch (Exception e) {
                System.out.println("There is no matching result with locationName+CountryName");
            }
        }


        // 3) 2)가 실패면, msrkrqstCode아 locationCode가 일치하는 것이 있는지
        if (result.equals("fail")) {
            try {
                System.out.println(mdmTPortMySqlRepo.findByMdmOwnerCodeAndLocationCode("F9M", f9eMskPortmdm.getMaerskRkstCode()));
                process = 3;
            } catch (Exception e) {
                System.out.println("There is no matching result with geoLocationId");
            }
        }


        return process;
    }

    public void updateF9MdmLocation2(F9E_MSK_PORTMDM f9eMskPortmdm, MDM_T_PORT_MySqlRepository mdmTPortMySqlRepo, int process) {
        try {
            MDM_T_PORT mdmTPort = mskConverter.convertPortMdm2(f9eMskPortmdm, mdmTPortMySqlRepo, process);
            mdmTPortMySqlRepo.save(mdmTPort);
        } catch (Exception e) {
            System.out.println("Exception!!     :com.f9e.emulator.service.MaerskMiningService.updateF9MdmLocation()");
        }
    }


    public List<F9_SEA_SKD> parseIntoF9SeaSkd(
            List<F9E_MSK_SKED_VSL> f9eMskSkedVsls,
            F9_MDM_LOCATION_ReactiveMongoRepository f9MdmLocationRepo,
            F9_MDM_VSL_ReactiveMongoRepository f9MdmVslRepo,
            String vesselCode) {
        List<F9_SEA_SKD> result = new ArrayList<>();
        try {
            result = mskConverter.convertMskSked(f9eMskSkedVsls, f9MdmLocationRepo, f9MdmVslRepo, vesselCode);
        } catch (Exception e) {
            System.out.println("Exception!!     :com.f9e.emulator.service.MaerskMiningService.parseIntoF9SeaSkd()");
        }
        return result;
    }

    public void uploadF9SeaSkd(F9_SEA_SKD f9SeaSkd, F9_SEA_SKD_ReactiveMongoRepository f9SeaSkdRepo) {
        try { // 찾아서 같으면 pass, 다르면 seq 더해서 업로드 한다.
            f9SeaSkdRepo.save(f9SeaSkd).block();
        } catch (Exception e) {
            System.out.println("Exception!!     :com.f9e.emulator.service.MaerskMiningService.uploadF9SeaSkd()");
        }
    }

    public void uploadF9SeaSkdMySQL(SCH_SRC schSrc, SCH_SRC_MySqlRepository schSrcMySqlRepo) {
        try { // 찾아서 같으면 pass, 다르면 seq 더해서 업로드 한다.
            schSrcMySqlRepo.save(schSrc);
        } catch (Exception e) {
            System.out.println("Exception!!     :com.f9e.emulator.service.MaerskMiningService.uploadF9SeaSkdMySQL()");
        }
    }

}


