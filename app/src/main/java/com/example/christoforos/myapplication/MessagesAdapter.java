package com.example.christoforos.myapplication;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by christoforos on 12/12/2016.
 */

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MessageItemView> {

    private List<Message> messages;

    public MessagesAdapter(List<Message> messages){
        this.messages = messages;
    }

    @Override
    public MessageItemView onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message, parent, false);
        return new MessageItemView(view);
    }

    @Override
    public void onBindViewHolder(MessageItemView holder, int position) {
        final Message message = messages.get(position);
        holder.messageView.setText(message.getText());
        holder.messageView.setTag(message);
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
        notifyDataSetChanged();
    }

    public static class MessageItemView extends RecyclerView.ViewHolder{

        private final TextView messageView;

        public MessageItemView(View itemView) {
            super(itemView);
            messageView = (TextView) itemView.findViewById(R.id.item_message);
        }
    }
}
