package com.geek.todoapp.models;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

@Entity
public class Task implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private String title;
    private long createdAt;
    private String docId;

    public Task() {
    }

    public Task(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String convert(long currentTime) {
        DateFormat date = new SimpleDateFormat("HH:mm dd MMMM yyyy 'г'");
        return date.format(System.currentTimeMillis());
    }
}
