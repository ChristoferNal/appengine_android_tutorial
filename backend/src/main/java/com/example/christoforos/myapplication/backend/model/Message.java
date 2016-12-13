package com.example.christoforos.myapplication.backend.model;

/**
 * Created by christoforos on 12/12/2016.
 */

public class Message {

    private String text;
    private String id;

    public Message(String msg) {
        this.text = msg;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}