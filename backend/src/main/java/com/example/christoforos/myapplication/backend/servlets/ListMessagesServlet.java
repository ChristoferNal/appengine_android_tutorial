package com.example.christoforos.myapplication.backend.servlets;

import com.example.christoforos.myapplication.backend.model.Message;
import com.example.christoforos.myapplication.backend.model.MessageDatasource;
import com.example.christoforos.myapplication.backend.model.MessageMapper;
import com.google.appengine.api.datastore.DatastoreServiceFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by christoforos on 12/12/2016.
 */

public class ListMessagesServlet extends BaseServlet {

    protected static final String RESULT_NO_MESSAGES = "There are no messages";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        MessageDatasource datasource = new MessageDatasource();
        List<Message> messageList = queryMessages(datasource);
        buildAndSendResponse(resp, messageList);
    }

    protected void buildAndSendResponse(HttpServletResponse resp, List<Message> messageList) {
        JSONObject jsonObjectResponse = new JSONObject();
        MessageMapper mapper = new MessageMapper();
        JSONArray jsonArray = mapper.toJsonArray(messageList);
        if(jsonArray.length() == 0){
            sendResponse(createJsonResultNoMessages(), resp);
            return;
        }
        try {
            jsonObjectResponse.put(KEY_RESULT, jsonArray);
        } catch (JSONException e) {
            sendResponse(createJsonResultNoMessages(), resp);
        }
        sendResponse(jsonObjectResponse.toString(), resp);
    }

    protected List<Message> queryMessages(MessageDatasource datasource) {
        return datasource
                    .getAllMessages(DatastoreServiceFactory.getDatastoreService());
    }

    protected String createJsonResultNoMessages() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(KEY_RESULT, RESULT_NO_MESSAGES);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

}
