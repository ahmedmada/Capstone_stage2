package com.e.k.m.a.capstone_stage2.adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.e.k.m.a.capstone_stage2.DetailActivity;
import com.e.k.m.a.capstone_stage2.Models.Movie;
import com.e.k.m.a.capstone_stage2.MovieWidget;
import com.e.k.m.a.capstone_stage2.Network.BaseUrls;
import com.e.k.m.a.capstone_stage2.R;
import com.e.k.m.a.capstone_stage2.constants.Constants;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.paperdb.Paper;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class MovieAdatpter extends RecyclerView.Adapter<MovieAdatpter.MovieViewHolder> {

    Context context;
    List<Movie> movies;
    public MovieAdatpter() {
    }

    public MovieAdatpter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
        Paper.init(context);
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.movie_card,parent,false);
        MovieViewHolder movieViewHolder = new MovieViewHolder(view);
        return movieViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MovieViewHolder holder, final int position) {
        holder.bindData(position);
    holder.itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (!movies.isEmpty()){
            Intent i = new Intent(context, DetailActivity.class);
            i.putExtra(Constants.MOVIE_ID,movies.get(position).getId());
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    Bundle bundle = ActivityOptions.makeSceneTransitionAnimation((Activity) context).toBundle();
                    i.setFlags(FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i,bundle);
                } else {
                    i.setFlags(FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i);
                }
            }
        }
    });
    }

    @Override
    public int getItemCount() {
        if (movies.size() > 0)
        return movies.size();
        else return 10;
    }

    class MovieViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.img_movie_card)
        ImageView movieImageView;
        @BindView(R.id.tv_name_movie_card)
        TextView movieTitleTextView;
        @BindView(R.id.tv_vote_movie_card)
        TextView movieVoteTextView;
        public MovieViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,
                    itemView);
        }

        public void bindData(int position){
            if (!movies.isEmpty()){
            String movieImage = movies.get(position).getPoster_path();
            String movieTitle = movies.get(position).getTitle();
            double movieVote = movies.get(position).getVote_average();

            BaseUrls baseUrls = new BaseUrls();

            movieTitleTextView.setText(movieTitle);
            movieVoteTextView.setText(String.valueOf(movieVote));
            if (!movieImage.isEmpty()){
                Picasso.get().load(baseUrls.getIMAGE_BASE_URL()+movieImage).into(movieImageView);
                movieImageView.setScaleType(ImageView.ScaleType.FIT_XY);
            }
            }
        }

    }

}
