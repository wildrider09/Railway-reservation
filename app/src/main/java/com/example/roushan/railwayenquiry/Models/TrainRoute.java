package com.example.roushan.railwayenquiry.Models;

/**
 * Created by Roushan on 01-05-2017.
 */

public class TrainRoute {

    private int number;
    private int distance;
    private int day;
    private int halt;
    private String stationName;
    private String scheduledArrival;
    private String scheduledDeparture;

    public TrainRoute(int number, int distance, int day, int halt, String stationName, String scheduledArrival,
                      String scheduledDeparture) {
        this.number = number;
        this.distance = distance;
        this.day = day;
        this.halt = halt;
        this.stationName = stationName;
        this.scheduledArrival = scheduledArrival;
        this.scheduledDeparture = scheduledDeparture;
    }

    public int getNumber() { return number; }

    public int getDistance() {
        return distance;
    }

    public int getDay() {
        return day;
    }

    public int getHalt() {
        return halt;
    }

    public String getScheduledArrival() {
        return scheduledArrival;
    }

    public String getScheduledDeparture() {
        return scheduledDeparture;
    }

    public String getStationName() { return stationName; }
}
