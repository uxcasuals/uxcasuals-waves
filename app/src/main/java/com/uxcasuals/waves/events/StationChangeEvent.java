package com.uxcasuals.waves.events;

import com.uxcasuals.waves.models.Station;

/**
 * Created by ivy4804 on 9/15/2015.
 */
public class StationChangeEvent {
    private Station station;

    public StationChangeEvent(Station station) {
        this.station = station;
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }
}
