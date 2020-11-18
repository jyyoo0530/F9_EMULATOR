package com.emulator.f9.model.market.mobility.sea.miner.maerskSchedule;

import com.google.gson.JsonObject;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class F9E_MSK_ACTIVEVESSEL {
    @Getter
    @Setter
    String code;

    @Getter
    @Setter
    String name;

    public void setAllData(JsonObject response) {
        setCode(response.get("code").toString().replace("\"", ""));
        setName(response.get("name").toString().replace("\"", ""));
    }
}
