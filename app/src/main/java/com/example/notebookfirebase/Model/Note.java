package com.example.notebookfirebase.Model;


import java.io.Serializable;

public class Note implements Serializable {
    private String id;
    private String title;
    private String content;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isNew() {
        return this.id == null;
    }

    @Override
    public String toString() {
        return this.title;
    }
}