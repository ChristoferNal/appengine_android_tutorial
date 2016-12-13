package com.example.christoforos.myapplication;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by christoforos on 13/12/2016.
 */
public class GetMessagesResponse {

    @SerializedName("result")
    private List<Message> messages;

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

}