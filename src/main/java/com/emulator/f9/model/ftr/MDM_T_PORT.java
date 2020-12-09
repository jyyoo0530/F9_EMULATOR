package com.emulator.f9.model.ftr;


import com.emulator.f9.model.market.mobility.sea.mdm.F9_MDM_LOCATION;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@Data
@Entity(name = "MDM_T_PORT")
public class MDM_T_PORT {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    @Column(name = "MDM_RGN_CD")
    @Getter
    @Setter
    String regionCode;

    @Column(name = "MDM_RGN_NM")
    @Getter
    @Setter
    String regionName;

    @Column(name = "MDM_SUBRGN_CD")
    @Getter
    @Setter
    String subRegionCode;

    @Column(name = "MDM_SUBRGN_NM")
    @Getter
    @Setter
    String subRegionName;

    @Column(name = "MDM_CNTRY_CD")
    @Getter
    @Setter
    String countryCode;

    @Column(name = "MDM_CNTRY_NM")
    @Getter
    @Setter
    String countryName;

    @Column(name = "MDM_SUBCNTRY_CD")
    @Getter
    @Setter
    String subCountryCode;

    @Column(name = "MDM_SUBCNTRY_NM")
    @Getter
    @Setter
    String subCountryName;

    @Column(name = "MDM_LOC_CD")
    @Getter
    @Setter
    String locationCode;

    @Column(name = "MDM_LOC_NM")
    @Getter
    @Setter
    String locationName;

    @Column(name = "MDM_LOC_NM_DC")
    @Getter
    @Setter
    String locationNameWithDiacritics;

    @Column(name = "MDM_LOC_STS")
    @Getter
    @Setter
    String locationStatus;

    @Column(name = "MDM_LOC_FUNC")
    @Getter
    @Setter
    String locationFunction;

    @Column(name = "MDM_LOC_DT")
    @Getter
    @Setter
    String locationDate;

    @Column(name = "MDM_LOC_LAT")
    @Getter
    @Setter
    String locationLatitude;

    @Column(name = "MDM_LOC_LONG")
    @Getter
    @Setter
    String locationLongitude;

    @Column(name = "MDM_OWNR_CD")
    @Getter
    @Setter
    String mdmOwnerCode;

    @Column(name = "MDM_OWNR_LOC_ID")
    @Getter
    @Setter
    String mdmOwnerLocationId;

    @Column(name = "MDM_F9_LOC_ID", unique = true)
    @Getter
    @Setter
    String f9LocationId; // dataowner + mdmownerlocationid

    @Column(name = "CRE_USR_ID")
    @Getter
    @Setter
    String creUsrId = "SYS"; // dataowner + mdmownerlocationid

    @Column(name = "UPD_USR_ID")
    @Getter
    @Setter
    String updUsrId = ""; // dataowner + mdmownerlocationid

    @Column(name = "CRE_DT")
    @Getter
    @Setter
    String creDt = getDate()+"000000"; // dataowner + mdmownerlocationid

    @Column(name = "UPD_DT")
    @Getter
    @Setter
    String updDt = getDate()+"000000"; // dataowner + mdmownerlocationid

    public void setAllData(F9_MDM_LOCATION f9MdmLocation) {
        setCountryCode(f9MdmLocation.getCountryCode());
        setCountryName(f9MdmLocation.getCountryName());
        setF9LocationId(f9MdmLocation.getF9LocationId());
        setLocationCode(f9MdmLocation.getLocationCode());
        setLocationDate(f9MdmLocation.getLocationDate());
        setLocationFunction(f9MdmLocation.getLocationFunction());
        setLocationLatitude(f9MdmLocation.getLocationLatitude());
        setLocationLongitude(f9MdmLocation.getLocationLongitude());
        setLocationName(f9MdmLocation.getLocationName());
        setLocationNameWithDiacritics(f9MdmLocation.getLocationNameWithDiacritics());
        setLocationStatus(f9MdmLocation.getLocationStatus());
        setMdmOwnerCode(f9MdmLocation.getMdmOwnerCode());
        setMdmOwnerLocationId(f9MdmLocation.getMdmOwnerLocationId());
        setRegionCode(f9MdmLocation.getRegionCode());
        setRegionName(f9MdmLocation.getRegionName());
        setSubCountryCode(f9MdmLocation.getSubCountryCode());
        setSubCountryName(f9MdmLocation.getSubCountryName());
        setSubRegionCode(f9MdmLocation.getSubRegionCode());
        setSubRegionName(f9MdmLocation.getSubRegionName());
    }

    public String getDate(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.");
        return formatter.format(calendar.getTime());
    }
}
