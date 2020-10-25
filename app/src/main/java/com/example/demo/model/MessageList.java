package com.example.demo.model;

import java.util.ArrayList;

public class MessageList {
    ArrayList<Chat> messages;
    Chat recentMessage;
    String sender;

    public MessageList() {
    }

    public MessageList(ArrayList<Chat> messages) {
        this.messages = messages;
    }

    public ArrayList<Chat> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<Chat> messages) {
        this.messages = messages;
    }

    public Chat getRecentMessage() {

        return messages.get(messages.size()-1);
    }

    public String getSender() {
        return messages.get(0).getSender();
    }
}
