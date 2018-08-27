package com.haokuo.wenyanoa.eventbus;

import lombok.Data;

/**
 * Created by zjf on 2018-08-07.
 */
@Data
public class WeekdaySelectedEvent {
    private int weekday;

    public WeekdaySelectedEvent(int weekday) {
        this.weekday = weekday;
    }
}
