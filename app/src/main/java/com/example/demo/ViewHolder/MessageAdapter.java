package com.example.demo.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.elyeproj.loaderviewlibrary.LoaderTextView;
import com.example.demo.R;
import com.example.demo.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MessageAdapter extends RecyclerView.ViewHolder {
    View view;
    public MessageAdapter(@NonNull View itemView) {
        super(itemView);
        view = itemView;
    }

    public void setDetails(String senderId, String messageDescription){
        LoaderTextView messageDescriptionField = view.findViewById(R.id.message_description);
        final LoaderTextView senderNameField = view.findViewById(R.id.message_sender);

        DatabaseReference userReference = FirebaseDatabase.getInstance().getReference().child("users").child(senderId);
        userReference.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        User user = dataSnapshot.getValue(User.class);
                        if(user != null){
                            senderNameField.setText(user.getName());
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                }
        );

        messageDescriptionField.setText(messageDescription);

    }
}
