package com.univtop.univtop.events;

import android.os.Bundle;

/**
 * Created by dungpham on 11/1/15.
 */
public class NotificationEvent {
    public Bundle payload;

    public NotificationEvent(Bundle extras) {
        this.payload = extras;
    }
}
