package com.emulator.f9.model.ftr;

import com.emulator.f9.model.market.mobility.sea.mdm.F9_MDM_LOCATION;
import com.emulator.f9.model.market.mobility.sea.mdm.F9_MDM_LOCATION_ReactiveMongoRepository;
import com.emulator.f9.model.market.mobility.sea.mdm.F9_MDM_VSL;
import com.emulator.f9.model.market.mobility.sea.miner.Converter_maerskSchedule;
import com.emulator.f9.model.market.mobility.sea.miner.maerskSchedule.F9E_MSK_PORTMDM;
import com.emulator.f9.model.market.mobility.sea.miner.maerskSchedule.F9E_MSK_VSLMDM;

import java.util.Random;

public class MdmVesselCloneService {

    Converter_maerskSchedule converter = new Converter_maerskSchedule();

    public void cloneMdmVessel(F9E_MSK_VSLMDM f9eMskVslMdm, MDM_T_VSL_MySqlRepository mdmTVslMySqlRepo) {
        MDM_T_VSL mdmTVsl = new MDM_T_VSL();
        F9_MDM_VSL f9MdmVsl = converter.convertVslMdm(f9eMskVslMdm);
        mdmTVsl.setAllData(f9MdmVsl);
        try {
            mdmTVslMySqlRepo.save(mdmTVsl);
        } catch (Exception f) {
            System.out.println(f.toString());
        }

    }

    public void cloneMdmPort(F9E_MSK_PORTMDM f9eMskPortmdm, F9_MDM_LOCATION_ReactiveMongoRepository f9MdmLocationRepo, MDM_T_PORT_MySqlRepository mdmTPortRepo, String locationName) {
        if (locationName.contains(" - ")) {
            MDM_T_PORT mdmTPort = new MDM_T_PORT();
            mdmTPort.setCountryCode("UNDEFINED");
            mdmTPort.setCountryName("UNDEFINED");
            Random rand = new Random();
            mdmTPort.setF9LocationId(locationName+rand.nextInt(9999999));
            mdmTPort.setLocationCode("UNDEFINED");
            mdmTPort.setLocationDate("UNDEFINED");
            mdmTPort.setLocationFunction("UNDEFINED");
            mdmTPort.setLocationLatitude("UNDEFINED");
            mdmTPort.setLocationLongitude("UNDEFINED");
            mdmTPort.setLocationName(locationName);
            mdmTPort.setLocationNameWithDiacritics("UNDEFINED");
            mdmTPort.setLocationStatus("UNDEFINED");
            mdmTPort.setMdmOwnerCode("UNDEFINED");
            mdmTPort.setMdmOwnerLocationId("UNDEFINED");
            mdmTPort.setRegionCode("UNDEFINED");
            mdmTPort.setRegionName("UNDEFINED");
            mdmTPort.setSubCountryCode("UNDEFINED");
            mdmTPort.setSubCountryName("UNDEFINED");
            mdmTPort.setSubRegionCode("UNDEFINED");
            mdmTPort.setSubRegionName("UNDEFINED");
            mdmTPortRepo.save(mdmTPort);
        } else {
            MDM_T_PORT mdmTPort = new MDM_T_PORT();
            F9_MDM_LOCATION f9MdmLocation = converter.convertPortMdm(f9eMskPortmdm, f9MdmLocationRepo);
            mdmTPort.setAllData(f9MdmLocation);
            mdmTPortRepo.save(mdmTPort);
        }

    }
}