package com.haokuo.wenyanoa.eventbus;

import lombok.Data;

/**
 * Created by zjf on 2018-08-07.
 */
@Data
public class QueryTextChangeEvent {
    private String newText;

    public QueryTextChangeEvent(String newText) {
        this.newText = newText;
    }
}
