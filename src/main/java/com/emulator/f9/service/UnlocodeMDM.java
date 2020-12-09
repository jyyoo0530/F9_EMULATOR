package com.emulator.f9.service;


import com.emulator.f9.model.market.mobility.sea.mdm.F9_MDM_LOCATION;
import com.emulator.f9.model.market.mobility.sea.mdm.F9_MDM_LOCATION_ReactiveMongoRepository;
import com.emulator.f9.model.ftr.MDM_T_PORT;
import com.emulator.f9.model.ftr.MDM_T_PORT_MySqlRepository;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class UnlocodeMDM {

    public void generateInitialMDM(String targetSource, F9_MDM_LOCATION_ReactiveMongoRepository f9MdmLocationRepo, MDM_T_PORT_MySqlRepository mdmTPortMySqlRepo) throws IOException {


        switch (targetSource) {
            case "f9s":
                // --) file을 읽어서 Java Object로 저장
                InputStream csvFile = getClass().getClassLoader().getResourceAsStream("locationMDM.csv");
                MappingIterator<F9_MDM_LOCATION> f9MdmLocationIter = new CsvMapper().readerWithTypedSchemaFor(F9_MDM_LOCATION.class)
                        .readValues(csvFile);
                List<F9_MDM_LOCATION> locationMDM = f9MdmLocationIter.readAll();

                // 1) File읽어서 MongoDB에 기록
                f9MdmLocationRepo.saveAll(locationMDM).blockLast();
            case "ftr":
                // --) file을 읽어서 MySQL에 기록
//                InputStream csvFile2 = getClass().getClassLoader().getResourceAsStream("locationMDM2.csv");
//                MappingIterator<MDM_T_PORT> mdmTPortIter = new CsvMapper().readerWithTypedSchemaFor(MDM_T_PORT.class)
//                        .readValues(csvFile2);
//                List<MDM_T_PORT> locationMDM2 = mdmTPortIter.readAll();

                InputStream csvFile2 = getClass().getClassLoader().getResourceAsStream("locationMDM2.csv");
                MappingIterator<F9_MDM_LOCATION> f9MdmLocationIter2 = new CsvMapper().readerWithTypedSchemaFor(F9_MDM_LOCATION.class)
                        .readValues(csvFile2);
                List<F9_MDM_LOCATION> locationMDM2 = f9MdmLocationIter2.readAll();
                List<MDM_T_PORT> locationMDM3 = new ArrayList<>();

                locationMDM2.forEach(a -> {
                    MDM_T_PORT b = new MDM_T_PORT();
                    b.setAllData(a);
                    try {
                        mdmTPortMySqlRepo.save(b);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

                // 2) MySQL에 기록
                mdmTPortMySqlRepo.saveAll(locationMDM3);
        }

    }
}
