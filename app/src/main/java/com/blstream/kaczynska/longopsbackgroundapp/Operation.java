package com.blstream.kaczynska.longopsbackgroundapp;

public class Operation {
    public final int id;
    public final String content;
//    public final String details;

    public Operation(int id, String content) {
        this.id = id;
        this.content = content;
//        this.details = details;
    }

    @Override
    public String toString() {
        return content;
    }
}

