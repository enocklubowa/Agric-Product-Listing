package com.example.demo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demo.ViewHolder.ChatAdapter;
import com.example.demo.model.Chat;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private ImageView btn_send;
    private EditText textContent;
    private ChatAdapter adapter;
    private RecyclerView recycler_view;
    private Query chatsQuery;
    private ActionBar actionBar;
    private String userId;
    private final int CHAT_ME = 100;
    private final int CHAT_YOU = 200;
    private String sellerName, sellerId;
    private TextView sellerNameField;
    private ArrayList<Chat> chats = new ArrayList<>();
    private List<Chat> sentChats = new ArrayList<>();
    private List<Chat> receivedChats = new ArrayList<>();
    private ChatAdapter chatAdapter;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        getIntentExtra();
        initToolbar();
        iniComponent();
    }


    public void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setHomeButtonEnabled(false);
        actionBar.setTitle(null);
        Tools.setSystemBarColorInt(this, Color.parseColor("#426482"));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void iniComponent() {
        recycler_view = findViewById(R.id.chat_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recycler_view.setLayoutManager(layoutManager);
        recycler_view.setHasFixedSize(true);
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        updateChats();

        /*
        retrieveReceivedChats();

        sentChats.addAll(receivedChats);
        chats = sentChats;
        chats.sort(new Comparator<Chat>() {
            @Override
            public int compare(Chat o1, Chat o2) {
                return o1.getTime().compareTo(o2.getTime());
            }
        });

         */


        btn_send = findViewById(R.id.btn_send);
        textContent = findViewById(R.id.text_content);
        showKeyboard(textContent);
        sellerNameField = findViewById(R.id.seller_name);
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendChat();
            }
        });
        textContent.addTextChangedListener(contentWatcher);

        (findViewById(R.id.lyt_back)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    private void sendChat() {
        if(textContent.getText().length() != 0){
            Long time = System.currentTimeMillis()/1000;
            Chat chat = new Chat(textContent.getText().toString(), time.toString(), userId, sellerId, false);
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("chats").push();
            reference.setValue(chat);
            //chatAdapter.insertItem(chat);
            chatAdapter.notifyDataSetChanged();
            textContent.getText().clear();
            hideKeyboard();
        }

    }

    private TextWatcher contentWatcher = new TextWatcher() {
        @Override
        public void afterTextChanged(Editable etd) {
            if (etd.toString().trim().length() == 0) {
                btn_send.setImageResource(R.drawable.ic_send_message);
            } else {
                btn_send.setImageResource(R.drawable.ic_send_message);
            }
        }

        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
        }

        @Override
        public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
        }
    };

    private void retrieveChats(){
        DatabaseReference chatsReference = FirebaseDatabase.getInstance().getReference().child("chats");
        chatsReference.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                            Chat chat = snapshot.getValue(Chat.class);
                            if((chat.getSender().equals(userId) && chat.getReceiver().equals(sellerId))
                                    || (chat.getSender().equals(sellerId) && chat.getReceiver().equals(userId))
                            ){
                                chats.add(chat);
                            }
                        }
                        displayChats(chats);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                }
        );


    }



    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }



    public void showKeyboard(View view) {
        if (view.requestFocus()) {
            InputMethodManager imm = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    private void updateChats(){
        DatabaseReference chatsReference = FirebaseDatabase.getInstance().getReference().child("chats");
        chatsReference.addChildEventListener(
                new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        Chat chat = dataSnapshot.getValue(Chat.class);
                        if((chat.getSender().equals(userId) && chat.getReceiver().equals(sellerId))
                                || (chat.getSender().equals(sellerId) && chat.getReceiver().equals(userId))){
                            chats.add(chat);
                            //displayChat(chat);
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

        displayChats(chats);
    }


    private void getIntentExtra(){
        Bundle extras = getIntent().getExtras();
        sellerId = extras.getString("SELLER_ID");
        DatabaseReference userReference = FirebaseDatabase.getInstance().getReference().child("users").child(sellerId);
        userReference.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        User user = dataSnapshot.getValue(User.class);
                        if(user != null){
                            sellerNameField.setText(user.getName());
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                }
        );

    }

    private void displayChats(ArrayList<Chat> chats){
        Log.e("Chats", chats.toString());
        chatAdapter = new ChatAdapter(chats, userId);
        recycler_view.setAdapter(chatAdapter);
    }


}