package com.littlefog.evenbusdemo;

/**
 * Created by user10 on 2017/10/16.
 */

public class MessageEvent {
    public  String message;

    public MessageEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {

        this.message = message;
    }
}
