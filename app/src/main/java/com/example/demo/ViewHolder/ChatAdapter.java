package com.example.demo.ViewHolder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo.R;
import com.example.demo.model.Chat;

import java.util.ArrayList;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    String text, time;
    private final int FROM_ME = 100;
    private final int FROM_YOU = 200;
    private String userId;
    boolean isRead;
    private List<Chat> chats = new ArrayList<>();

    public ChatAdapter(List<Chat> chats, String userId){
        this.chats = chats;
        this.userId = userId;
    }


    @NonNull
    @Override
    public ChatAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        if(viewType == FROM_ME){
            view = inflater.inflate(R.layout.item_chat_me, parent, false);
        }
        else {
            view = inflater.inflate(R.layout.item_chat_you, parent, false);
        }
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public int getItemViewType(int position) {
        if(chats.get(position).getSender().equals(userId)){
            return FROM_ME;
        }
        return FROM_YOU;
    }

    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.ViewHolder holder, int position) {
        holder.timeField.setText(time);
        holder.messageField.setText(text);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView timeField, messageField;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            timeField = (TextView) itemView.findViewById(R.id.text_time_you);
            messageField = (TextView) itemView.findViewById(R.id.text_content_you);

            timeField.setText(time);
            messageField.setText(text);
        }
    }
}
