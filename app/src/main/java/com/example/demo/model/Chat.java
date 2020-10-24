package com.example.demo.model;

public class Chat {
    private String text, time, sender, receiver;
    private boolean isRead;

    public Chat() {
    }

    public Chat(String text, String time, String sender, String receiver, boolean isRead) {
        this.text = text;
        this.time = time;
        this.receiver = receiver;
        this.sender = sender;
        this.isRead = isRead;
    }

    public Chat(String text, String time, String sender, String receiver) {
        this.text = text;
        this.time = time;
        this.sender = sender;
        this.receiver = receiver;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }
}
