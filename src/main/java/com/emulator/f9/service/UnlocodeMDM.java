package com.emulator.f9.service;


import com.emulator.f9.model.market.mobility.sea.mdm.F9_MDM_LOCATION;
import com.emulator.f9.model.market.mobility.sea.mdm.F9_MDM_LOCATION_ReactiveMongoRepository;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class UnlocodeMDM {

    public void generateInitialMDM(F9_MDM_LOCATION_ReactiveMongoRepository f9MdmLocationRepo) throws IOException {
        InputStream csvFile = getClass().getClassLoader().getResourceAsStream("locationMDM.csv");

        MappingIterator<F9_MDM_LOCATION> personIter = new CsvMapper().readerWithTypedSchemaFor(F9_MDM_LOCATION.class)
                .readValues(csvFile);
        List<F9_MDM_LOCATION> locationMDM = personIter.readAll();

        f9MdmLocationRepo.saveAll(locationMDM).blockLast();
    }
}
