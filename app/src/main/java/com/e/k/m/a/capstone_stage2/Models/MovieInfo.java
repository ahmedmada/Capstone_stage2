package com.e.k.m.a.capstone_stage2.Models;

public class MovieInfo {

    private int imgSrc;
    private String text;

    public MovieInfo() {
    }

    public int getImgSrc() {
        return imgSrc;
    }

    public MovieInfo(int imgSrc, String text) {
        this.imgSrc = imgSrc;
        this.text = text;
    }

    public void setImgSrc(int imgSrc) {
        this.imgSrc = imgSrc;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
