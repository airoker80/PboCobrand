package com.paybyonline.KantipurPay.Adapter.Model;

/**
 * Created by Sameer on 5/8/2017.
 */

public class FlightSectorModel {
    String SectorName,SectorCode;

    public FlightSectorModel(String sectorName, String sectorCode) {
        SectorName = sectorName;
        SectorCode = sectorCode;
    }

    public String getSectorName() {
        return SectorName;
    }

    public void setSectorName(String sectorName) {
        SectorName = sectorName;
    }

    public String getSectorCode() {
        return SectorCode;
    }

    public void setSectorCode(String sectorCode) {
        SectorCode = sectorCode;
    }
}
