package com.example.christoforos.myapplication;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private TextView messageTextView;
    private Button submitBtn;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private MessagesAdapter adapter;
    private Button fetchBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        setupRecyclerView();
        setupListeners();
    }

    private void setupListeners() {
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
            }
        });
        fetchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchMessages();
            }
        });
    }

    private void fetchMessages() {
        Call<GetMessagesResponse> call =
                RestClient.get().getAllMessages();
        call.enqueue(new Callback<GetMessagesResponse>() {
            @Override
            public void onResponse(Call<GetMessagesResponse> call, Response<GetMessagesResponse> response) {
                GetMessagesResponse messagesResponse = response.body();
                if(messagesResponse.getMessages() != null) {
                    adapter.setMessages(messagesResponse.getMessages());
                }
            }

            @Override
            public void onFailure(Call<GetMessagesResponse> call, Throwable t) {
                Snackbar.make(submitBtn, "Failed to get messages.", Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void sendMessage() {
        Message message = new Message(messageTextView.getText().toString());
        Call<Message> call =
                RestClient.get().createMessage(message);
        call.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                Snackbar.make(submitBtn, "Message Submitted!", Snackbar.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {
                Snackbar.make(submitBtn, "Message Failed!", Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void setupRecyclerView() {
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        adapter = new MessagesAdapter(new ArrayList<Message>());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void findViews() {
        messageTextView = (TextView) findViewById(R.id.message);
        submitBtn = (Button) findViewById(R.id.submit_btn);
        fetchBtn = (Button) findViewById(R.id.fetch_btn);
        recyclerView = (RecyclerView) findViewById(R.id.messages_list);
    }
}
