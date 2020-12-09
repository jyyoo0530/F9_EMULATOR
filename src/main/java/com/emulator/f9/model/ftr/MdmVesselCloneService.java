package com.emulator.f9.model.ftr;

import com.emulator.f9.model.market.mobility.sea.mdm.F9_MDM_LOCATION;
import com.emulator.f9.model.market.mobility.sea.mdm.F9_MDM_LOCATION_ReactiveMongoRepository;
import com.emulator.f9.model.market.mobility.sea.mdm.F9_MDM_VSL;
import com.emulator.f9.model.market.mobility.sea.miner.Converter_maerskSchedule;
import com.emulator.f9.model.market.mobility.sea.miner.maerskSchedule.F9E_MSK_PORTMDM;
import com.emulator.f9.model.market.mobility.sea.miner.maerskSchedule.F9E_MSK_VSLMDM;

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

    public void cloneMdmPort(F9E_MSK_PORTMDM f9eMskPortmdm, F9_MDM_LOCATION_ReactiveMongoRepository f9MdmLocationRepo, MDM_T_PORT_MySqlRepository mdmTPortRepo) {
        MDM_T_PORT mdmTPort = new MDM_T_PORT();
        F9_MDM_LOCATION f9MdmLocation = converter.convertPortMdm(f9eMskPortmdm, f9MdmLocationRepo);
        mdmTPort.setAllData(f9MdmLocation);
        mdmTPortRepo.save(mdmTPort);
    }
}