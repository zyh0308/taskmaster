package com.example.taskmaster;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity

public class Task {
    @PrimaryKey (autoGenerate = true)
           public long id;


    String body;
    String title;
    String state;

    public Task(String body, String title, String state) {
        this.body = body;
        this.title = title;
        this.state = state;
    }

    @Ignore
    public Task(){

    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
