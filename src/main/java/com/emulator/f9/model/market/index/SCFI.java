package com.emulator.f9.model.market.index;

import com.emulator.f9.model.market.mobility.sea.mdm.F9_MDM_LOCATION;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Data
public class SCFI {
    @Getter
    @Setter
    String baseYearWeek;

    @Getter
    @Setter
    int priceIndex;
}
