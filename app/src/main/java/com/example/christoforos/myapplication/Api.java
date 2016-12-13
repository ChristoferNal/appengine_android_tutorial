package com.example.christoforos.myapplication;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by christoforos on 12/12/2016.
 */

public interface Api {

    public static final String ROOT_URL = "http://aristotle-152313.appspot.com/";
    public static final String POST_CREATE_MESSAGE ="createmessage";
    public static final String GET_ALL_MESSAGES = "fetchmessages";

    @GET(GET_ALL_MESSAGES)
    Call<GetMessagesResponse> getAllMessages();

    @POST(POST_CREATE_MESSAGE)
    Call<Message> createMessage(@Body Message message);
}
