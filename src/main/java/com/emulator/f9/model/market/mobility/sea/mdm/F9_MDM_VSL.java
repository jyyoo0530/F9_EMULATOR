package com.emulator.f9.model.market.mobility.sea.mdm;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "F9_MDM_VSL")
public class F9_MDM_VSL {

    @Getter
    @Setter
    String vesselCode;

    @Getter
    @Setter
    String vesselName;

    @Getter
    @Setter
    String callSign;

    @Getter
    @Setter
    String imoNumber;

    @Getter
    @Setter
    String flagCode;

    @Getter
    @Setter
    String flagName;

    @Getter
    @Setter
    String capacityTeu;

    @Getter
    @Setter
    String buildYear;

    @Getter
    @Setter
    String vesselClass;

    @Getter
    @Setter
    String dataOwner;

    @Id
    @Getter
    @Setter
    String f9eVslCode;

    public int getSize() {
        return getF9eVslCode().length();
    }
}
