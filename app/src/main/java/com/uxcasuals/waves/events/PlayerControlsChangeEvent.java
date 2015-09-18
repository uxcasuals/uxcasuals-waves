package com.uxcasuals.waves.events;

import com.uxcasuals.waves.models.Station;

/**
 * Created by ivy4804 on 9/18/2015.
 */
public class PlayerControlsChangeEvent {
    public static final int SHOW_PLAY_ICON = 0;
    public static final int SHOW_PAUSE_ICON = 1;
    public static final int SHOW_STATION_ICON = 3;

    public int state = 0;
    private Station station;

    public PlayerControlsChangeEvent(int state) {
        this.state = state;
    }

    public PlayerControlsChangeEvent(int state, Station station) {
        this.state = state;
        this.station = station;
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
