package com.emulator.f9.model.market.mobility.sea.mdm;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.Data;
import lombok.Synchronized;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

@Data
public class F9E_VSL_SKEDSample {

    static String serviceLaneName = "";
    static ArrayList<String> polCode = new ArrayList<>();
    static ArrayList<String> podCode = new ArrayList<>();
    static ArrayList<String> carrierCode = new ArrayList<>();
    static String startWeek = "";
    static String endWeek = "";
    static int qtyTeu = 0;
    static ArrayList<String> cargoTypes = new ArrayList<>();


    @Synchronized
    public void setPolCode(String locationCode) {
        polCode.add(locationCode);
    }

    @Synchronized
    public void setPodCode(String locationCode) {
        podCode.add(locationCode);
    }

    @Synchronized
    public void setCarrierCode(String companyCode) {
        carrierCode.add(companyCode);
    }

    @Synchronized
    public void setStartEndWeek(String start, String end) {
        startWeek = start;
        endWeek = end;
    }

    @Synchronized
    public void setQtyTeu(int spaces) {
        qtyTeu = spaces;
    }

    @Synchronized
    public void setCargoTypes(String types) {
        cargoTypes.add(types);
    }

    @Synchronized
    public void setServiceLaneName(String laneName) {
        serviceLaneName = laneName;
    }

    @Synchronized
    public JsonArray makeRouteList(String caseSelector) {
        JsonArray result = new JsonArray();

        if (polCode.size() == 0 || podCode.size() == 0) {
            throw new NullPointerException("Exception: polCode or podCode has no attribute!!");
        } else {
            switch (caseSelector) {
                case "masterContract": {
                    ArrayList<String> i = new ArrayList<>(Arrays.asList("i"));
                    polCode.forEach(x -> {
                        podCode.forEach(y -> {
                            JsonObject a = new JsonObject();
                            a.addProperty("regSeq", i.size());
                            a.addProperty("locationCode", x);
                            a.addProperty("locationName", "test");
                            a.addProperty("locationTypeCode", "01");
                            a.addProperty("deleteYn", "0");
                            JsonObject b = new JsonObject();
                            b.addProperty("regSeq", i.size());
                            b.addProperty("locationCode", x);
                            b.addProperty("locationName", "test");
                            b.addProperty("locationTypeCode", "02");
                            b.addProperty("deleteYn", "0");
                            JsonObject c = new JsonObject();
                            c.addProperty("regSeq", i.size());
                            c.addProperty("locationCode", y);
                            c.addProperty("locationName", "test");
                            c.addProperty("locationTypeCode", "03");
                            c.addProperty("deleteYn", "0");
                            JsonObject d = new JsonObject();
                            d.addProperty("regSeq", i.size());
                            d.addProperty("locationCode", y);
                            d.addProperty("locationName", "test");
                            d.addProperty("locationTypeCode", "03");
                            d.addProperty("deleteYn", "0");
                            result.add(a);
                            result.add(b);
                            result.add(c);
                            result.add(d);
                            i.add("i");
                        });
                    });
                }
                case "offer": {
                    ArrayList<String> i = new ArrayList<>(Arrays.asList("i"));
                    polCode.forEach(x -> {
                        podCode.forEach(y -> {
                            JsonObject a = new JsonObject();
                            a.addProperty("regSeq", i.size());
                            a.addProperty("locationCode", x);
                            a.addProperty("locationName", "test");
                            a.addProperty("locationTypeCode", "01");
                            a.addProperty("deleteYn", "0");
                            JsonObject b = new JsonObject();
                            b.addProperty("regSeq", i.size());
                            b.addProperty("locationCode", x);
                            b.addProperty("locationName", "test");
                            b.addProperty("locationTypeCode", "02");
                            b.addProperty("deleteYn", "0");
                            JsonObject c = new JsonObject();
                            c.addProperty("regSeq", i.size());
                            c.addProperty("locationCode", y);
                            c.addProperty("locationName", "test");
                            c.addProperty("locationTypeCode", "03");
                            c.addProperty("deleteYn", "0");
                            JsonObject d = new JsonObject();
                            d.addProperty("regSeq", i.size());
                            d.addProperty("locationCode", y);
                            d.addProperty("locationName", "test");
                            d.addProperty("locationTypeCode", "03");
                            d.addProperty("deleteYn", "0");
                            result.add(a);
                            result.add(b);
                            result.add(c);
                            result.add(d);
                            i.add("i");
                        });
                    });
                }
            }
        }
        return result;
    }

    @Autowired
    MongoTemplate mongoTemplate;

    @Synchronized
    public JsonArray makeCarrierList(String caseSelector) {
        JsonArray result = new JsonArray();

        if (carrierCode.size() == 0) {
            throw new NullPointerException("Exception: carrierCode has no attribute!!");
        } else {
            switch (caseSelector) {
                case "masterContract": {
                    ArrayList<String> i = new ArrayList<>(Arrays.asList("i"));
                    carrierCode.forEach(x -> {
                        JsonObject a = new JsonObject();
                        a.addProperty("carrierCode", x);
                        a.addProperty("deleteYn", "0");
                        result.add(a);
                    });
                }
                case "offer": {
                    ArrayList<String> i = new ArrayList<>(Arrays.asList("i"));
                    carrierCode.forEach(x -> {
                        JsonObject a = new JsonObject();
                        a.addProperty("offerCarrierCode", x);
                        a.addProperty("isChecked", true);
                        result.add(a);
                    });
                }
            }

        }

        return result;
    }


    @Synchronized
    public JsonArray makeLineItem(String caseSelector) {
        JsonArray result = new JsonArray();

        if (startWeek == null || endWeek == null || qtyTeu == 0) {
            throw new NullPointerException("Exception: startWeek or endWeek or qtyTeu has no attribute!!");
        } else {
            switch (caseSelector) {
                case "masterContract": {
                    ArrayList<String> i = new ArrayList<>(Arrays.asList("i"));
                    ArrayList<String> baseYearWeek = new ArrayList<>();
                    int weekPointer = Integer.parseInt(startWeek);
                    do {
                        JsonObject a = new JsonObject();
                        a.addProperty("baseYearWeek", String.valueOf(weekPointer));
                        a.addProperty("qty", qtyTeu);
                        a.addProperty("deleteYn", "0");
                        a.add("masterContractPrices", makePrices("masterContract"));
                        result.add(a);
                        if (Integer.parseInt(String.valueOf(weekPointer).substring(4, 6)) == 53) {
                            weekPointer = weekPointer + 100 - Integer.parseInt(String.valueOf(weekPointer).substring(4, 6)) + 1;
                        } else {
                            weekPointer = weekPointer + 1;
                        }
                    }
                    while (weekPointer != Integer.parseInt(endWeek) + 1);
                    break;

                }
                case "offer": {
                    ArrayList<String> i = new ArrayList<>(Arrays.asList("i"));
                    polCode.forEach(x -> {
                        podCode.forEach(y -> {
                            JsonObject a = new JsonObject();
                            a.addProperty("regSeq", i.size());
                            a.addProperty("locationCode", x);
                            a.addProperty("locationName", "test");
                            a.addProperty("locationTypeCode", "01");
                            a.addProperty("deleteYn", "0");
                            JsonObject b = new JsonObject();
                            b.addProperty("regSeq", i.size());
                            b.addProperty("locationCode", x);
                            b.addProperty("locationName", "test");
                            b.addProperty("locationTypeCode", "02");
                            b.addProperty("deleteYn", "0");
                            JsonObject c = new JsonObject();
                            c.addProperty("regSeq", i.size());
                            c.addProperty("locationCode", y);
                            c.addProperty("locationName", "test");
                            c.addProperty("locationTypeCode", "03");
                            c.addProperty("deleteYn", "0");
                            JsonObject d = new JsonObject();
                            d.addProperty("regSeq", i.size());
                            d.addProperty("locationCode", y);
                            d.addProperty("locationName", "test");
                            d.addProperty("locationTypeCode", "03");
                            d.addProperty("deleteYn", "0");
                            result.add(a);
                            result.add(b);
                            result.add(c);
                            result.add(d);
                            i.add("i");
                        });
                    });
                }
            }
        }
        return result;
    }

    @Synchronized
    public JsonArray makePrices(String caseSelector) {
        JsonArray result = new JsonArray();
        JsonObject a = new JsonObject();
        JsonObject b = new JsonObject();
        JsonObject c = new JsonObject();
        JsonObject d = new JsonObject();

        Random rand = new Random();

        a.addProperty("containerTypeCode", "01");
        a.addProperty("containerSizeCode", "01");
        a.addProperty("price", (int) rand.nextGaussian() * 1000 + 1000);
        a.addProperty("deleteYn", "0");
        b.addProperty("containerTypeCode", "01");
        b.addProperty("containerSizeCode", "02");
        b.addProperty("price", (int) rand.nextGaussian() * 1000 + 1000);
        b.addProperty("deleteYn", "0");
        c.addProperty("containerTypeCode", "01");
        c.addProperty("containerSizeCode", "03");
        c.addProperty("price", (int) rand.nextGaussian() * 1000 + 1000);
        c.addProperty("deleteYn", "0");
        d.addProperty("containerTypeCode", "01");
        d.addProperty("containerSizeCode", "04");
        d.addProperty("price", (int) rand.nextGaussian() * 1000 + 1000);
        d.addProperty("deleteYn", "0");

        result.add(a);
        result.add(b);
        result.add(c);
        result.add(d);
        return result;
    }

    @Synchronized
    public JsonArray makeCargoTypes() {
        JsonArray result = new JsonArray();
        JsonObject a = new JsonObject();
        a.addProperty("cargoTypeCode", "001");
        a.addProperty("deleteYn", "0");
        result.add(a);
        return result;
    }

    @Synchronized
    public JsonObject makeMainBody(String caseSelector) {
        JsonObject result = new JsonObject();

        switch (caseSelector) {
            case "masterContract": {
                result.addProperty("carrierCode", carrierCode.get(0));
                result.addProperty("paymentPlanCode", "01PS");
                result.addProperty("deleteYn", "0");
                result.addProperty("rdTermCode", "01");
                result.addProperty("paymentTermCode", "01");
                result.addProperty("serviceLaneCode", serviceLaneName);
                result.add("masterContractRoutes", makeRouteList("masterContract"));
                result.add("masterContractLineItems", makeLineItem("masterContract"));
                result.add("masterContractCargoTypes", makeCargoTypes());
            }
            case "offer": {
                ArrayList<String> i = new ArrayList<>(Arrays.asList("i"));
            }

        }
        return result;
    }

    @Synchronized
    public ArrayList<Document> makeSchedule(JsonArray portMDM) {
        ArrayList<Document> plannedScheduleList = new ArrayList<>();
        ArrayList<String> locationCode = new ArrayList<String>();
        ArrayList<String> locationName = new ArrayList<String>();
        ArrayList<String> countryCode = new ArrayList<String>();
        ArrayList<String> countryName = new ArrayList<String>();
        ArrayList<String> subRegionCode = new ArrayList<String>();
        ArrayList<String> subRegionName = new ArrayList<String>();
        ArrayList<String> regionCode = new ArrayList<String>();
        ArrayList<String> regionName = new ArrayList<String>();
        portMDM.forEach(x -> {
                    locationCode.add(x.getAsJsonObject().get("locCd").toString().replace("\\\"", "").replace("\"",""));
                    locationName.add(x.getAsJsonObject().get("locNm").toString().replace("\\\"", "").replace("\"",""));
                    countryCode.add(x.getAsJsonObject().get("cntCd").toString().replace("\\\"", "").replace("\"",""));
                    countryName.add(x.getAsJsonObject().get("cntNm").toString().replace("\\\"", "").replace("\"",""));
                    subRegionCode.add(x.getAsJsonObject().get("sbCntiCd").toString().replace("\\\"", "").replace("\"",""));
                    subRegionName.add(x.getAsJsonObject().get("sbCntiNm").toString().replace("\\\"", "").replace("\"",""));
                    regionCode.add(x.getAsJsonObject().get("regnCd").toString().replace("\\\"", "").replace("\"",""));
                    regionName.add(x.getAsJsonObject().get("regnNm").toString().replace("\\\"", "").replace("\"",""));
                }
        );

        polCode.forEach(x -> {
            podCode.forEach(y -> {
                makeLineItem("masterContract").forEach(z -> {
                    JsonObject a = new JsonObject();
                    a.addProperty("serviceLaneName", serviceLaneName);
                    a.addProperty("polCode", x);
                    a.addProperty("polName", locationName.get(locationCode.indexOf(x)));
                    a.addProperty("polCountryCode", countryCode.get(locationCode.indexOf(x)));
                    a.addProperty("polCountryName", countryName.get(locationCode.indexOf(x)));
                    a.addProperty("polSubRegionCode", subRegionCode.get(locationCode.indexOf(x)));
                    a.addProperty("polSubRegionName", subRegionName.get(locationCode.indexOf(x)));
                    a.addProperty("polRegionCode", regionCode.get(locationCode.indexOf(x)));
                    a.addProperty("polRegionName", regionName.get(locationCode.indexOf(x)));
                    a.addProperty("podCode", y);
                    a.addProperty("podName", locationName.get(locationCode.indexOf(y)));
                    a.addProperty("podCountryCode", countryCode.get(locationCode.indexOf(y)));
                    a.addProperty("podCountryName", countryName.get(locationCode.indexOf(y)));
                    a.addProperty("podSubRegionCode", subRegionCode.get(locationCode.indexOf(y)));
                    a.addProperty("podSubRegionName", subRegionName.get(locationCode.indexOf(y)));
                    a.addProperty("podRegionCode", regionCode.get(locationCode.indexOf(y)));
                    a.addProperty("podRegionName", regionName.get(locationCode.indexOf(y)));
                    a.addProperty("carrierCode", carrierCode.get(0));
                    String zz = z.getAsJsonObject().get("baseYearWeek").toString().replace("\\\"", "").replace("\"","");
                    a.addProperty("polDepartureWeek", zz);
                    a.addProperty("vesselSize", qtyTeu);
                    Document doc = Document.parse(a.toString());
                    plannedScheduleList.add(doc);
                });
            });
        });
        System.out.println(plannedScheduleList);
        return plannedScheduleList;
    }
}
