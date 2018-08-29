package com.e.k.m.a.capstone_stage2.Models;

public class Trailer {
    private String name;
    private String key;

    public Trailer() {
    }

    public Trailer(String name, String key) {
        this.name = name;
        this.key = key;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public String getKey() {
        return key;
    }
}
