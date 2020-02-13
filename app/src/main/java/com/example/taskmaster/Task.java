package com.example.taskmaster;

public class Task {
    String body;
    String title;
    String state;

    public Task(String body, String title, String state) {
        this.body = body;
        this.title = title;
        this.state = state;
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
