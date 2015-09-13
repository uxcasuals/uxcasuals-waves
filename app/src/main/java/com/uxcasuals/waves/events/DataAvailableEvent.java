package com.uxcasuals.waves.events;

import com.uxcasuals.waves.models.Station;

import java.util.List;

/**
 * Created by Dhakchianandan on 14/09/15.
 */
public class DataAvailableEvent {
    private List<Station> stations;

    public DataAvailableEvent(List<Station> stations) {
        this.stations = stations;
    }

    public List<Station> getStations() {
        return stations;
    }

    public void setStations(List<Station> stations) {
        this.stations = stations;
    }
}
