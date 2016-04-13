package me.kamadi.memorize.event;

import com.squareup.otto.Bus;

/**
 * Created by Madiyar on 13.04.2016.
 */
public class BusProvider {
    private static final Bus BUS = new Bus();

    public static Bus getInstance() {
        return BUS;
    }

    private BusProvider() {
        // No instances.
    }
}
