package com.e.k.m.a.capstone_stage2.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MoviesResponse {

    @SerializedName("results")
    private List<Movie> results;

    public MoviesResponse() {
    }

    public MoviesResponse(List<Movie> results) {
        this.results = results;
    }

    public List<Movie> getResults() {
        return results;
    }

    public void setResults(List<Movie> results) {
        this.results = results;
    }
}
