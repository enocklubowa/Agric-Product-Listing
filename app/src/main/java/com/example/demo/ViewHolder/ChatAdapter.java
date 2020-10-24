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
    private final int FROM_ME = 100;
    private final int FROM_YOU = 200;
    private String userId;
    boolean isRead;
    private ArrayList<Chat> chats = new ArrayList<>();

    public ChatAdapter(ArrayList<Chat> chats, String userId){
        this.chats = chats;
        this.userId = userId;
    }


    @NonNull
    @Override
    public ChatAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ChatAdapter.ViewHolder viewHolder;
        if(viewType == FROM_ME){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_me, parent, false);
            viewHolder = new ViewHolder(view);
        }
        else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_you, parent, false);
            viewHolder = new ViewHolder(view);
        }
        return viewHolder;
    }

    @Override
    public int getItemViewType(int position) {
        if(this.chats.get(position).getSender().equals(userId)){
            return FROM_ME;
        }
        return FROM_YOU;
    }

    @Override
    public int getItemCount() {
        return chats.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.ViewHolder holder, int position) {
        Chat chat = chats.get(position);
        holder.timeField.setText(chat.getTime());
        holder.messageField.setText(chat.getText());

    }



    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView timeField, messageField;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            timeField = (TextView) itemView.findViewById(R.id.text_time);
            messageField = (TextView) itemView.findViewById(R.id.text_content);

        }
    }

    public void insertItem(Chat item) {
        this.chats.add(item);
        notifyItemInserted(getItemCount());
    }

}
