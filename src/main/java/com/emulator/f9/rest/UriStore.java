package com.emulator.f9.rest;


import lombok.Data;
import lombok.Getter;
import lombok.experimental.UtilityClass;

@UtilityClass
@Data
public class UriStore {
    @Getter
    String UriMarketMobilityVessel_MaerskActive = "https://api.maerskline.com/maeu/schedules/vessel/active";
    @Getter
    String UriMarketMobilityPort_MaerskActive = "https://api.maerskline.com/maeu/schedules/port/active";
    @Getter
    String UriMarketMobilitySeaSked = "https://api.maerskline.com/maeu/schedules/vessel?"; // vesselCode=128&vesselName=ANNA+MAERSK&fromDate=2021-01-01&toDate=2021-02-01
    @Getter
    String UriTradeMakeNewOffer = "http://api.freight9.com:8080/api/v1/product/offer/new";
    @Getter
    String UriMarketMobilityPort_MaerskDetail = "https://api.maerskline.com/locations?type=city&cityName="; //type=city & cityName=durres
    @Getter
    String UriMarketMobilityPort_MaerskDetail2 = "https://api.maerskline.com/locations/"; //type=city & cityName=durres
    @Getter
    String UriMarketMobilityVessel_MaerskDetail = "https://api.maerskline.com/vessels?"; //maerskCode=766
}
