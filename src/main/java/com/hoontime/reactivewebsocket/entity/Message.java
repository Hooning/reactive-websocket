package com.hoontime.reactivewebsocket.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Message {

    private String eventId;
    private String eventDt;
    private String data;

}
