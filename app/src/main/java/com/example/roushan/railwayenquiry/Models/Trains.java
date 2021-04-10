package com.example.roushan.railwayenquiry.Models;

/**
 * Created by Roushan on 08-05-2017.
 */

public class Trains {

    private String name;
    private String number;
    private String srcDeparture;
    private String destinationArrival;
    private String fromStationName;
    private String toStationName;

    public Trains(String name, String number, String srcDeparture, String destinationArrival, String fromStationName,
                  String toStationName) {
        this.name = name;
        this.number = number;
        this.srcDeparture = srcDeparture;
        this.destinationArrival = destinationArrival;
        this.fromStationName = fromStationName;
        this.toStationName = toStationName;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public String getSrcDeparture() {
        return srcDeparture;
    }

    public String getDestinationArrival() {
        return destinationArrival;
    }

    public String getFromStationName() {
        return fromStationName;
    }

    public String getToStationName() {
        return toStationName;
    }
}


