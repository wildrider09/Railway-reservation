package com.example.roushan.railwayenquiry.Models;

/**
 * Created by Roushan on 29-04-2017.
 */

public class TrainStatus {

    private int stationNumber;
    private String stationName;
    private String scheduledArrival;
    private String scheduledDeparture;
    private String actualArrival;
    private String actualDeparture;
    private int distance;

    public TrainStatus(int stationNumber, String stationName, String scheduledArrival, String scheduledDeparture,
                       String actualArrival, String actualDeparture, int distance) {
        this.stationNumber = stationNumber;
        this.stationName = stationName;
        this.scheduledArrival = scheduledArrival;
        this.scheduledDeparture = scheduledDeparture;
        this.actualArrival = actualArrival;
        this.actualDeparture = actualDeparture;
        this.distance = distance;
    }

    public int getStationNumber() { return stationNumber; }

    public String getStationName() {
        return stationName;
    }

    public String getScheduledArrival() {
        return scheduledArrival;
    }

    public String getScheduledDeparture() {
        return scheduledDeparture;
    }

    public String getActualArrival() {
        return actualArrival;
    }

    public String getActualDeparture() {
        return actualDeparture;
    }

    public int getDistance() {
        return distance;
    }
}
