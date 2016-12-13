package com.example.christoforos.myapplication.backend.servlets;

import com.example.christoforos.myapplication.backend.model.Message;
import com.example.christoforos.myapplication.backend.model.MessageDatasource;
import com.example.christoforos.myapplication.backend.model.MessageMapper;
import com.google.appengine.api.datastore.DatastoreServiceFactory;

import org.json.JSONObject;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by christoforos on 12/12/2016.
 */

public class CreateMessageServlet extends BaseServlet {

    private String result;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject jsonObject = readRequest(req);
        MessageMapper mapper = new MessageMapper();
        Message message = mapper.fromJson(jsonObject);
        MessageDatasource datasource = new MessageDatasource();
        String msgKey = datasource.saveMessageToDatastore(message, DatastoreServiceFactory.getDatastoreService());
        message.setId(msgKey);
        result = getJsonResultOk();
        sendResponse(result, resp);
    }

}
