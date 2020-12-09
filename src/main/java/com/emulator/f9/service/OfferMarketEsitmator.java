package com.emulator.f9.service;

import com.emulator.f9.model.market.index.F9Index;
import com.emulator.f9.model.market.index.SCFI;
import com.emulator.f9.model.market.mobility.sea.mdm.F9_MDM_LOCATION;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class OfferMarketEsitmator {

    public List<SCFI> initialize(String target) throws IOException {
        List<SCFI> scfis = new ArrayList<>();

        InputStream streamBaseYearWeek = getClass().getClassLoader().getResourceAsStream("baseYearWeek.csv");
        InputStream streamPrices = getClass().getClassLoader().getResourceAsStream(target);

        MappingIterator<String> baseYearWeekIterator = new CsvMapper().readerWithTypedSchemaFor(String.class)
                .readValues(streamBaseYearWeek);
        MappingIterator<String> prices = new CsvMapper().readerWithTypedSchemaFor(String.class)
                .readValues(streamPrices);

        List<String> baseYearWeekList = baseYearWeekIterator.readAll();
        List<String> priceList = prices.readAll();


        for(int i=0; i<baseYearWeekList.size();i++){
            SCFI scfi = new SCFI();
            scfi.setBaseYearWeek(baseYearWeekList.get(i));
            scfi.setPriceIndex(Integer.parseInt(priceList.get(i)));
            scfis.add(scfi);
        }

        return scfis;
    }

}
