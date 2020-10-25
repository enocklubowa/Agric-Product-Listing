package com.example.demo.ViewHolder;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.elyeproj.loaderviewlibrary.LoaderTextView;
import com.example.demo.ChatActivity;
import com.example.demo.R;
import com.example.demo.User;
import com.example.demo.model.Chat;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MessageAdapter extends RecyclerView.ViewHolder {
    View view;
    private LoaderTextView senderNameField;
    private DatabaseReference databaseReference;
    private LoaderTextView messageDescriptionField;

    public MessageAdapter(@NonNull View itemView) {
        super(itemView);
        view = itemView;
    }

    public void setDetails(final String senderId, String userId){
        LinearLayout messageLayout = view.findViewById(R.id.message_layout);
        messageDescriptionField = (LoaderTextView) view.findViewById(R.id.message_description);
        senderNameField = (LoaderTextView) view.findViewById(R.id.message_sender);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        setSenderName(senderId);
        setMessageDescription(senderId, userId);
        Log.e("Sender and me",senderId+" "+userId);


        messageLayout.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        goToChatActivity(senderId);
                    }
                }
        );
    }

    private void goToChatActivity(String senderId) {
        Intent intent = new Intent(view.getContext(), ChatActivity.class);
        Bundle extras = new Bundle();
        extras.putString("SELLER_ID", senderId);
        intent.putExtras(extras);
        view.getContext().startActivity(intent);
    }

    private void setMessageDescription(String senderId, String userId) {
        Query messageReference = databaseReference.child("chats").child(userId).child(senderId).limitToLast(1);
        messageReference.addChildEventListener(
                new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        Chat chat = dataSnapshot.getValue(Chat.class);
                        if(chat != null){
                            messageDescriptionField.setText(chat.getText());
                        }
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                }
        );

    }

    private void setSenderName(final String senderId) {
        DatabaseReference userReference = databaseReference.child("users").child(senderId);

        userReference.addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        User user = dataSnapshot.getValue(User.class);
                        if(user != null){
                            senderNameField.setText(user.getName());
                            Log.e("Sender name is ","Sender name is "+user.getName());
                        }
                        else {
                            Log.e("Sender doesn't exist ","Sender doesn't exist "+senderId);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.e("Call cancelled ","Unsuccessful call "+databaseError.getMessage());
                    }
                }
        );
    }
}
