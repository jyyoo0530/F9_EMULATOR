package com.emulator.f9.model.market.mobility.sea.miner;

import com.emulator.f9.model.market.mobility.sea.F9_SEA_SKD;
import com.emulator.f9.model.market.mobility.sea.F9_SEA_SKD_ReactiveMongoRepository;
import com.emulator.f9.model.market.mobility.sea.mdm.F9_MDM_LOCATION;
import com.emulator.f9.model.market.mobility.sea.mdm.F9_MDM_LOCATION_ReactiveMongoRepository;
import com.emulator.f9.model.market.mobility.sea.mdm.F9_MDM_VSL;
import com.emulator.f9.model.market.mobility.sea.miner.maerskSchedule.F9E_MSK_PORTMDM;
import com.emulator.f9.model.market.mobility.sea.miner.maerskSchedule.F9E_MSK_SKED_VSL;
import com.emulator.f9.model.market.mobility.sea.miner.maerskSchedule.F9E_MSK_VSLMDM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class Converter_maerskSchedule {

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

    public F9_MDM_LOCATION convertPortMdm(F9E_MSK_PORTMDM mskPortMdm, F9_MDM_LOCATION_ReactiveMongoRepository f9MdmLocationRepo) {
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

    public void convertMskSked(ArrayList<F9E_MSK_SKED_VSL> f9eMskSkedVsls, F9_SEA_SKD_ReactiveMongoRepository f9SeaSkdRepo) {
        f9eMskSkedVsls.forEach(x->{


        });
    }

}
