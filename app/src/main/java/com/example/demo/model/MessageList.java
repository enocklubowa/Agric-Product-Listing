package com.example.demo.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MessageList {
    List<Chat> messages;

    public MessageList() {
    }

    public MessageList(List<Chat> messages) {
        this.messages = messages;
    }

    public List<Chat> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<Chat> messages) {
        this.messages = messages;
    }

    /*
    public Chat getRecentMessage() {
        if (messages.size()>0){
            return messages.get(messages.size()-1);
        }
        return null;
    }

    public String getSender() {
        if(messages.size()>0){
            return messages.get(0).getSender();

        }
        return null;
    }

     */


}
