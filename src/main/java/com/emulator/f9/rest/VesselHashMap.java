package com.emulator.f9.rest;

import lombok.Synchronized;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@UtilityClass
public class VesselHashMap {
    static List<String> vesselCode = new ArrayList<>();
    static List<String> vesselName = new ArrayList<>();

    @Synchronized
    public void setVesselCodes(String code) {
        vesselCode.add(code);
    }

    @Synchronized
    public void setVesselNames(String name){
        vesselName.add(name);
    }

    @Synchronized
    public List<String> getVesselCodes() {
        return vesselCode;
    }

    @Synchronized
    public List<String> getVesselnames(){
        return vesselName;
    }

}
