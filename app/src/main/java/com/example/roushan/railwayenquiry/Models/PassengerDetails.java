package com.example.roushan.railwayenquiry.Models;

/**
 * Created by Roushan on 27-04-2017.
 */

public class PassengerDetails {

    private int passengerNo;
    private String bookingStatus;
    private String currrentStatus;
    private int coachPosition;

    public PassengerDetails(int passengerNo, String bookingStatus, String currrentStatus, int coachPosition) {
        this.passengerNo = passengerNo;
        this.bookingStatus = bookingStatus;
        this.currrentStatus = currrentStatus;
        this.coachPosition = coachPosition;
    }

    public int getPassengerNo() {
        return passengerNo;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public String getCurrrentStatus() {
        return currrentStatus;
    }

    public int getCoachPosition() {
        return coachPosition;
    }
}
