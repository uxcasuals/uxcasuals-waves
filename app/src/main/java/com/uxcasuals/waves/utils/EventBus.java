package com.uxcasuals.waves.utils;

import com.squareup.otto.Bus;

/**
 * Created by Dhakchianandan on 14/09/15.
 */
public class EventBus extends Bus {
    private static final EventBus bus = new EventBus();

    private EventBus(){

    }

    public static Bus getInstance() {
        return bus;
    }
}
