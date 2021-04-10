package com.example.roushan.railwayenquiry.Models;

/**
 * Created by Roushan on 05-05-2017.
 */

public class SeatDetails {

    private int serialNumber;
    private String date;
    private String status;

    public SeatDetails(int serialNumber, String date, String status) {
        this.serialNumber = serialNumber;
        this.date = date;
        this.status = status;
    }

    public int getSerialNumber() {
        return serialNumber;
    }

    public String getDate() {
        return date;
    }

    public String getStatus() {
        return status;
    }
}
