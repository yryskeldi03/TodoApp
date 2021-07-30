package com.geek.todoapp.models;

import com.google.firebase.Timestamp;

import java.io.Serializable;

public class Post implements Serializable {
    private String title, text, email;
    private Timestamp createdAt;
    private int viewCount;
    private String docId;

    public Post() {}

    public Post(String title, String text, String email) {
        this.title = title;
        this.text = text;
        this.email = email;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }
}
