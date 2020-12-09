package com.emulator.f9.model.bot.ftr.mastercontract;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Data
public class MasterContractCargoType {
    @Getter
    @Setter
    String cargoTypeCode;

    @Getter
    @Setter
    String deleteYn;

    public void setAllData(String cargoType){
        setCargoTypeCode(cargoType);
        setDeleteYn("1");
    }
}
