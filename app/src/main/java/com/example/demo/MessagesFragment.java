package com.example.demo;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

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
    private FirebaseRecyclerAdapter<MessageList, MessageAdapter> firebaseRecyclerAdapter;
    private ProgressBar loading_messages;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_messages, container, false);
        loading_messages = rootView.findViewById(R.id.loading_messages);
        messagesRecyclerView = rootView.findViewById(R.id.messages_recycler_view);
        messagesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        messagesRecyclerView.setHasFixedSize(true);
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        messagesQuery = FirebaseDatabase.getInstance().getReference().child("chats").child(userId).orderByValue();
        retrieveMessages();
        return rootView;
    }

    private void retrieveMessages(){

        FirebaseRecyclerOptions<MessageList> options = new FirebaseRecyclerOptions.Builder<MessageList>()
                .setQuery(messagesQuery, MessageList.class)
                .build();

        firebaseRecyclerAdapter
                = new FirebaseRecyclerAdapter<MessageList, MessageAdapter>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MessageAdapter holder, int position, @NonNull MessageList model) {
                String key = firebaseRecyclerAdapter.getRef(position).getKey();
                holder.setDetails(key, userId);

            }

            @NonNull
            @Override
            public MessageAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_message, parent, false);
                loading_messages.setVisibility(View.GONE);
                return new MessageAdapter(view);
            }

            @Override
            public int getItemCount() {
                if(super.getItemCount()==0){
                    loading_messages.setVisibility(View.GONE);
                }
                return super.getItemCount();
            }
        };

        messagesRecyclerView.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();
    }

    @Override
    public void onResume() {
        if (firebaseRecyclerAdapter != null){
            firebaseRecyclerAdapter.startListening();
        }
        super.onResume();
    }
}










