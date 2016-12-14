package com.example.christoforos.myapplication.backend.servlets;

import com.example.christoforos.myapplication.backend.model.Message;
import com.example.christoforos.myapplication.backend.model.MessageDatasource;
import com.example.christoforos.myapplication.backend.model.MessageMapper;
import com.google.appengine.api.datastore.DatastoreServiceFactory;

import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by christoforos on 14/12/2016.
 */

public class PostDeleteMessageServlet extends BaseServlet {

    private String result;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject jsonObject = readRequest(req);
        MessageMapper mapper = new MessageMapper();
        Message message = mapper.fromJson(jsonObject);
        MessageDatasource datasource = new MessageDatasource();
        List<Message> messages = datasource.searchExactMessage(message.getText(), DatastoreServiceFactory.getDatastoreService());
        datasource.deleteMessages(messages, DatastoreServiceFactory.getDatastoreService());
        result = getJsonResultOk();
        sendResponse(result, resp);
    }

}