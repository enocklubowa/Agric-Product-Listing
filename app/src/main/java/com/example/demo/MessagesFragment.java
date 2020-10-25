package com.example.demo;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.demo.ViewHolder.MessageAdapter;
import com.example.demo.model.MessageList;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class MessagesFragment extends Fragment {
    private RecyclerView messagesRecyclerView;
    private Query messagesQuery;
    private String userId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_messages, container, false);
        messagesRecyclerView = rootView.findViewById(R.id.messages_recycler_view);
        messagesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        messagesRecyclerView.setHasFixedSize(true);
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        messagesQuery = FirebaseDatabase.getInstance().getReference().child("chats").child(userId);
        retrieveMessages();
        return rootView;
    }

    private void retrieveMessages(){

        FirebaseRecyclerOptions<MessageList> options = new FirebaseRecyclerOptions.Builder<MessageList>()
                .setQuery(messagesQuery, MessageList.class)
                .build();

        FirebaseRecyclerAdapter<MessageList, MessageAdapter> firebaseRecyclerAdapter
                = new FirebaseRecyclerAdapter<MessageList, MessageAdapter>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MessageAdapter holder, int position, @NonNull MessageList model) {
                holder.setDetails(model.getSender(), model.getRecentMessage().getText());
            }

            @NonNull
            @Override
            public MessageAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_message, parent, false);
                return new MessageAdapter(view);
            }
        };

        messagesRecyclerView.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();
    }
}










