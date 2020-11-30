package com.emulator.f9.model.market.mobility.sea.miner;

import com.emulator.f9.model.market.mobility.sea.F9_SEA_SKD;
import com.emulator.f9.model.market.mobility.sea.F9_SEA_SKD_ReactiveMongoRepository;
import com.emulator.f9.model.market.mobility.sea.mdm.F9_MDM_LOCATION;
import com.emulator.f9.model.market.mobility.sea.mdm.F9_MDM_LOCATION_ReactiveMongoRepository;
import com.emulator.f9.model.market.mobility.sea.mdm.F9_MDM_VSL;
import com.emulator.f9.model.market.mobility.sea.mdm.F9_MDM_VSL_ReactiveMongoRepository;
import com.emulator.f9.model.market.mobility.sea.miner.maerskSchedule.F9E_MSK_PORTMDM;
import com.emulator.f9.model.market.mobility.sea.miner.maerskSchedule.F9E_MSK_SKED_VSL;
import com.emulator.f9.model.market.mobility.sea.miner.maerskSchedule.F9E_MSK_VSLMDM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class Converter_maerskSchedule {
//ftradm

    public F9_MDM_VSL convertVslMdm(F9E_MSK_VSLMDM mskVslMdm) {
        F9_MDM_VSL f9eMdmVsl = new F9_MDM_VSL();
        f9eMdmVsl.setBuildYear(mskVslMdm.getBuildYear());
        f9eMdmVsl.setCallSign(mskVslMdm.getCallSign());
        f9eMdmVsl.setCapacityTeu(mskVslMdm.getCapacityTeu());
        f9eMdmVsl.setVesselClass(mskVslMdm.getVesselClass());
        f9eMdmVsl.setFlagCode(mskVslMdm.getFlagCode());
        f9eMdmVsl.setFlagName(mskVslMdm.getFlagName());
        f9eMdmVsl.setImoNumber(mskVslMdm.getImoNumber());
        f9eMdmVsl.setF9eVslCode(mskVslMdm.getMaerskId());
        f9eMdmVsl.setVesselName(mskVslMdm.getName());
        f9eMdmVsl.setVesselCode(mskVslMdm.getMaerskId());
        f9eMdmVsl.setDataOwner("MSK"); ////////////// Carrier MDM에서 가져와야됨.

        return f9eMdmVsl;
    }

    public F9_MDM_LOCATION convertPortMdm(
            F9E_MSK_PORTMDM mskPortMdm,
            F9_MDM_LOCATION_ReactiveMongoRepository f9MdmLocationRepo
    ) {
        F9_MDM_LOCATION f9MdmLocation = new F9_MDM_LOCATION();
        F9_MDM_LOCATION sample;
        try {
            sample = f9MdmLocationRepo.findByLocationCode(mskPortMdm.getUnLocCode()).block();
        } catch (Exception e) {
            System.out.println("Log             :.............There is no matching f9LocationId in F9E_MDM_LOCATION");
            System.out.println("Exception!!     :com.f9e.emulator.service.Converter_maerskSchedule.convertPortMdm()");
            f9MdmLocation.setF9LocationId("MSK_" + mskPortMdm.getMaerskGeoLocationId());
            f9MdmLocation.setRegionCode("UNDEFINED");
            f9MdmLocation.setRegionName("UNDEFINED");
            f9MdmLocation.setSubRegionCode("UNDEFINED");
            f9MdmLocation.setSubRegionName("UNDEFINED");
            f9MdmLocation.setCountryCode(mskPortMdm.getCountryCode());
            f9MdmLocation.setCountryName(mskPortMdm.getCountryName());
            f9MdmLocation.setSubCountryCode("UNDEFINED");
            f9MdmLocation.setSubCountryName("UNDEFINED");
            f9MdmLocation.setLocationCode(mskPortMdm.getMaerskRkstCode());
            f9MdmLocation.setLocationName(mskPortMdm.getCityName());
            f9MdmLocation.setLocationNameWithDiacritics(mskPortMdm.getCityName());
            f9MdmLocation.setLocationStatus("UNDEFINED");
            f9MdmLocation.setLocationFunction("UNDEFINED");
            f9MdmLocation.setLocationDate("UNDEFINED");
            f9MdmLocation.setLocationLatitude("UNDEFINED");
            f9MdmLocation.setLocationLongitude("UNDEFINED");
            f9MdmLocation.setMdmOwnerCode("MSK");
            f9MdmLocation.setMdmOwnerLocationId(mskPortMdm.getMaerskGeoLocationId());
            return f9MdmLocation;
        }
        try {
            f9MdmLocation.setF9LocationId("MSK_" + mskPortMdm.getMaerskGeoLocationId());
            f9MdmLocation.setRegionCode(sample.getRegionCode());
            f9MdmLocation.setRegionName(sample.getRegionName());
            f9MdmLocation.setSubRegionCode(sample.getSubRegionCode());
            f9MdmLocation.setSubRegionName(sample.getSubRegionName());
            f9MdmLocation.setCountryCode(mskPortMdm.getCountryCode());
            f9MdmLocation.setCountryName(mskPortMdm.getCountryName());
            f9MdmLocation.setSubCountryCode(sample.getSubCountryCode());
            f9MdmLocation.setSubCountryName(sample.getSubCountryName());
            f9MdmLocation.setLocationCode(mskPortMdm.getMaerskRkstCode());
            f9MdmLocation.setLocationName(mskPortMdm.getCityName());
            f9MdmLocation.setLocationNameWithDiacritics(mskPortMdm.getCityName());
            f9MdmLocation.setLocationStatus(sample.getLocationStatus());
            f9MdmLocation.setLocationFunction(sample.getLocationFunction());
            f9MdmLocation.setLocationDate(sample.getLocationDate());
            f9MdmLocation.setLocationLatitude(sample.getLocationLatitude());
            f9MdmLocation.setLocationLongitude(sample.getLocationLongitude());
            f9MdmLocation.setMdmOwnerCode("MSK");
            f9MdmLocation.setMdmOwnerLocationId(mskPortMdm.getMaerskGeoLocationId());
            return f9MdmLocation;
        } catch (Exception e) {
            System.out.println("Exception!!     :com.f9e.emulator.service.Converter_maerskSchedule.convertPortMdm()");
            return f9MdmLocation;
        }
    }

    public List<F9_SEA_SKD> convertMskSked(
            List<F9E_MSK_SKED_VSL> f9eMskSkedVsls,
            F9_MDM_LOCATION_ReactiveMongoRepository f9MdmLocationRepo,
            F9_MDM_VSL_ReactiveMongoRepository f9MdmVslRepo,
            String vesselCode) {
        List<F9_SEA_SKD> f9SeaSkds = new ArrayList<>();
        List<F9E_MSK_SKED_VSL> updatedSrcList = new ArrayList<>();
        List<String> tempServiceList = new ArrayList<>();
        List<String> serviceList = new ArrayList<>();
        String serviceCode = "";
        // 1) filter : serviceArr == serviceDep (exclude false value.)
        try {
            updatedSrcList = f9eMskSkedVsls.stream().filter(a -> a.getServiceArr().equals(a.getServiceDep())).collect(Collectors.toList());
            updatedSrcList.forEach(b -> tempServiceList.add(b.getServiceArr()));
            updatedSrcList.forEach(c -> tempServiceList.add(c.getServiceDep()));
            serviceList = tempServiceList.stream().distinct().collect(Collectors.toList());
        } catch (Exception e) {
            System.out.println("Exception in....:1) filter : serviceArr == serviceDep (exclude false value.)       ");
            System.out.println("Exception!!     :com.f9e.emulator.service.Converter_maerskSchedule.convertMskSked()");
        }

        // --1) check
        try {
            if (serviceList.size() == 1) {
                serviceCode = serviceList.get(0);
            }
        } catch (Exception e) {
            System.out.println("Exception in....:--1) ServiceList is more than 1                                   ");
            System.out.println("Exception!!     :com.f9e.emulator.service.Converter_maerskSchedule.convertMskSked()");
        }

        // 2) filter : voyageArr != voyageDep (exclude false value.)
        try {
            updatedSrcList = f9eMskSkedVsls.stream().filter(a -> !a.getVoyageArrival().equals(a.getVoyageDeparture())).collect(Collectors.toList());
        } catch (Exception e) {
            System.out.println("Exception in....:2) filter : voyageArr != voyageDep (exclude false value.)         ");
            System.out.println("Exception!!     :com.f9e.emulator.service.Converter_maerskSchedule.convertMskSked()");
        }

        // 3) makeSeqList : voyageLinkList
        HashMap<String, String> voyageLink = new HashMap<>();
        try {
            updatedSrcList.forEach(a -> {
                voyageLink.put(a.getVoyageArrival(), a.getVoyageDeparture());
            });
        } catch (Exception e) {
            System.out.println("Exception in....:3) makeSeqList : voyageLinkList                                   ");
            System.out.println("Exception!!     :com.f9e.emulator.service.Converter_maerskSchedule.convertMskSked()");
        }

        // 4) schedule convert main job
        try {
            Set<String> keys = voyageLink.keySet();
            String finalServiceCode = serviceCode;
            keys.forEach(a -> {
                List<F9E_MSK_SKED_VSL> fromScheduleList = f9eMskSkedVsls.stream().filter(b -> b.getVoyageArrival().equals(a)).collect(Collectors.toList());
                List<F9E_MSK_SKED_VSL> toScheduleList = f9eMskSkedVsls.stream().filter(b -> b.getVoyageArrival().equals(voyageLink.get(a))).collect(Collectors.toList());
                List<String> fromSeq = new ArrayList<>();
                fromSeq.add("start");
                fromScheduleList.forEach(c -> {

                    List<String> toSeq = new ArrayList<>();
                    toSeq.add("start");
                    toScheduleList.forEach(d -> {

                        // 5) core process start(grouping) : 1) 선박, 2) 서비스, 3) 항차, 4) from, 5) to
                        F9_SEA_SKD scheduleRow = new F9_SEA_SKD();
                        String fromTarget = c.getSrcPortCode();
                        F9_MDM_LOCATION fromMDM = f9MdmLocationRepo.findByMdmOwnerLocationId(fromTarget).block();
                        String fromKey = fromMDM.getLocationCode();
                        String toTarget = d.getSrcPortCode();
                        F9_MDM_LOCATION toMDM = f9MdmLocationRepo.findByMdmOwnerLocationId(toTarget).block();
                        String toKey = toMDM.getLocationCode();
                        F9_MDM_VSL vslMdm = f9MdmVslRepo.findByVesselCode(vesselCode).block();
                        String vesselName = vslMdm.getVesselName();

                        // 5-1) Master 항목 설정 (선박, 서비스, 항차, from(loc+skd), to(loc+skd))
                        scheduleRow.setVesselKey(vesselCode);
                        scheduleRow.setServiceLaneKey(finalServiceCode);
                        scheduleRow.setVoyageNumber(d.getVoyageArrival());
                        scheduleRow.setFromKey(fromKey);
                        scheduleRow.setFromScheduleReferenceKey(c.getScheduleKey());
                        scheduleRow.setToKey(toKey);
                        scheduleRow.setToScheduleReferenceKey(d.getScheduleKey());

                        // 5-2) 선박 디테일 설정
                        scheduleRow.setVesselName(vesselName);

                        // 5-3) 서비스 디테일 설정
                        scheduleRow.setServiceLaneName(finalServiceCode + "_NotYetDefined");

                        // 5-4) from 디테일 설정(loc + skd)
                        String fromLocationStatus = "";
                        String test1 = fromMDM.getLocationFunction();
                        if (test1.contains("4")) {
                            fromLocationStatus = "SeaPort";
                        } else {
                            fromLocationStatus = "Inland";
                        }
                        Calendar calendar = Calendar.getInstance();
                        int year = Integer.parseInt(c.getArrival().substring(0, 4));
                        int month = Integer.parseInt(c.getArrival().substring(4, 6));
                        int dayOfMonth = Integer.parseInt(c.getArrival().substring(6, 7));
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, month);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        String productWeek = String.valueOf((year * 100 + calendar.get(Calendar.WEEK_OF_YEAR)));
                        String fromETA = c.getArrival().replaceAll("[^\\d.]", "");
                        String fromETB = c.getArrival().replaceAll("[^\\d.]", "");
                        String fromETD = c.getDeparture().replaceAll("[^\\d.]", "");

                        scheduleRow.setFromSeq(fromSeq.size());
                        scheduleRow.setFromLocationStatus(fromLocationStatus);
                        scheduleRow.setServiceProductWeek(productWeek);
                        scheduleRow.setFromETA(fromETA + "000000");
                        scheduleRow.setFromETB(fromETB + "000000");
                        scheduleRow.setFromETD(fromETD + "000000");

                        // 5-5) to 디테일 설정(loc + skd)
                        String toLocationStatus = "";
                        String test2 = toMDM.getLocationFunction();
                        if (test2.contains("4")) {
                            toLocationStatus = "SeaPort";
                        } else {
                            toLocationStatus = "Inland";
                        }
                        String toETA = d.getArrival().replaceAll("[^\\d.]", "");
                        String toETB = d.getArrival().replaceAll("[^\\d.]", "");
                        String toETD = d.getDeparture().replaceAll("[^\\d.]", "");

                        scheduleRow.setToSeq(toSeq.size());
                        scheduleRow.setToLocationStatus(toLocationStatus);
                        scheduleRow.setToETA(toETA + "000000");
                        scheduleRow.setToETB(toETB + "000000");
                        scheduleRow.setToETD(toETD + "000000");

                        // 5-6) 마무으리 (아이디 // 시퀀스 // 생성일자 // owner/provider 부여
                        Calendar now = Calendar.getInstance();
                        scheduleRow.setScheduleId("S" + vesselCode + finalServiceCode + fromETA + toETA + "MSK");
                        scheduleRow.setScheduleSeq(0);
                        scheduleRow.setScheduleOwnerCode("MSK" + "_NotYetDefined");
                        scheduleRow.setDataCreationDate(now.getTime().toString());
                        scheduleRow.setScheduleProvider("MSK");

                        scheduleRow.setFromAdditionalInfoKey("UNDEFINED");
                        scheduleRow.setFromAlliedNodeScheduleId("UNDEFINED");
                        scheduleRow.setToAdditionalInfoKey("UNDEFINED");
                        scheduleRow.setToAlliedNodeScheduleId("UNDEFINED");

                        f9SeaSkds.add(scheduleRow);
                        toSeq.add("Success");
                    });
                    fromSeq.add("Success");
                });
            });
        } catch (Exception e) {
            System.out.println("Exception in....:4) schedule convert main job                                      ");
            System.out.println("Exception!!     :com.f9e.emulator.service.Converter_maerskSchedule.convertMskSked()");
        }

        List<F9_SEA_SKD> finalF9SeaSkds = f9SeaSkds.stream().distinct().collect(Collectors.toList());
        return finalF9SeaSkds;
    }

}
