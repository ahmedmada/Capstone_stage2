package com.e.k.m.a.capstone_stage2.Network.rest;

import com.e.k.m.a.capstone_stage2.Models.MovieDetail;
import com.e.k.m.a.capstone_stage2.Models.MoviesResponse;
import com.e.k.m.a.capstone_stage2.Models.TrailersResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInteface {

    @GET("movie/popular")
    Call<MoviesResponse> getPopularMovies(@Query("api_key") String apiKey);

    @GET("movie/top_rated")
    Call<MoviesResponse> getTopRatedMovies(@Query("api_key") String apiKey);

    @GET("movie/upcoming")
    Call<MoviesResponse> getUpcomingMovies(@Query("api_key") String apiKey);

    @GET("movie/now_playing")
    Call<MoviesResponse> getNowPlayingMovies(@Query("api_key") String apiKey);

    @GET("movie/{id}")
    Call<MovieDetail> getMovieDetails(@Path("id") int id, @Query("api_key") String apiKey);

    @GET("movie/{id}/similar")
    Call<MoviesResponse> getSimilarMovies(@Path("id") int id, @Query("api_key") String apiKey);

    @GET("movie/{id}/recommendations")
    Call<MoviesResponse> getRecommendationsMovies(@Path("id") int id, @Query("api_key") String apiKey);
    @GET("movie/{id}/videos")
    Call<TrailersResponse> getTrailerMovies(@Path("id") int id, @Query("api_key") String apiKey);
}
