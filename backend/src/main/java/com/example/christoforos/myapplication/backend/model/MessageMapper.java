package com.example.christoforos.myapplication.backend.model;

import com.example.christoforos.myapplication.backend.utils.JsonParser;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.KeyFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by christoforos on 12/12/2016.
 */

public class MessageMapper {

    public static final String MESSAGE = "message";

    public Message fromJson(JSONObject jsonObject) {
        String msg = JsonParser.getString(jsonObject, MESSAGE);
        Message message = new Message(msg);
        return message;
    }

    public Message fromEntity(Entity entity) {
        Message message = new Message((String) entity.getProperty(MessageDatasource.PROPERTY_TEXT));
        message.setId(KeyFactory.keyToString(entity.getKey()));
        return message;
    }

    public JSONObject toJson(Message message){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(MESSAGE, message.getText());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public JSONArray toJsonArray(List<Message> messageList){
        JSONArray jsonArray = new JSONArray();
        for (Message message :
                messageList) {
            jsonArray.put(toJson(message));
        }
        return jsonArray;
    }
}