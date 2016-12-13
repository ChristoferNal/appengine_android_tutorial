package com.example.christoforos.myapplication.backend.servlets;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by christoforos on 12/12/2016.
 */

public abstract class BaseServlet extends HttpServlet {

    protected static final String KEY_RESULT = "result";
    protected static final String RESULT_OK = "ok";
    protected static final String UTF_8 = "UTF-8";

    public JSONObject readRequest(HttpServletRequest request) {
        BufferedReader bufferedReader = getBufferedReader(request);
        StringBuilder stringBuilder = getResult(bufferedReader);
        JSONObject jsonObject = convertResultToJson(stringBuilder);
        return jsonObject;
    }

    public void sendResponse(String result, HttpServletResponse response) {
        try {
            response.setCharacterEncoding(UTF_8);
            PrintWriter printWriter = response.getWriter();
            printWriter.print(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private JSONObject convertResultToJson(StringBuilder stringBuilder) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(stringBuilder.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    private StringBuilder getResult(BufferedReader bufferedReader) {
        if (bufferedReader == null) {
            return null;
        }
        String message;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            while ((message = bufferedReader.readLine()) != null) {
                stringBuilder.append(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder;
    }

    private BufferedReader getBufferedReader(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = request.getReader();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bufferedReader;
    }

    protected String getJsonResultOk() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(KEY_RESULT, RESULT_OK);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

}