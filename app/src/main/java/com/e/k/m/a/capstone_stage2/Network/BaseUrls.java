package com.e.k.m.a.capstone_stage2.Network;

public class BaseUrls {
    private String BASE_URL = "http://api.themoviedb.org/3/";
    private String IMAGE_BASE_URL ="http://image.tmdb.org/t/p/w185/";
    private String VIDEO_BASE_URL ="https://www.youtube.com/watch?v=";
    public BaseUrls() {
    }
    public String getBASE_URL(){
            return BASE_URL;
    }

    public String getIMAGE_BASE_URL() {
        return IMAGE_BASE_URL;
    }

    public String getVIDEO_BASE_URL() {
        return VIDEO_BASE_URL;
    }


}
