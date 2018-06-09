package com.example.edvardsen.wastelessclient.services;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Epico-u-01 on 6/9/2018.
 */

public class NotificationID {
    private final static AtomicInteger c = new AtomicInteger(0);
    public static int getID() {
        return c.incrementAndGet();
    }
}
