package com.e.k.m.a.capstone_stage2.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.e.k.m.a.capstone_stage2.Models.MovieInfo;
import com.e.k.m.a.capstone_stage2.Network.BaseUrls;
import com.e.k.m.a.capstone_stage2.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieInfoAdapter extends RecyclerView.Adapter<MovieInfoAdapter.MovieInfoViewHolder> {

    Context context;
    List<MovieInfo> movies;
    public MovieInfoAdapter() {
    }

    public MovieInfoAdapter(Context context, List<MovieInfo> movies) {
        this.context = context;
        this.movies = movies;
    }

    @NonNull
    @Override
    public MovieInfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.movie_info_card,parent,false);
        MovieInfoViewHolder movieViewHolder = new MovieInfoViewHolder(view);
        return movieViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MovieInfoViewHolder holder, int position) {
        holder.bindData(position);
    }

    @Override
    public int getItemCount() {
        if (movies.size() > 0)
            return movies.size();
        else return 10;
    }

    class MovieInfoViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.movie_info_img_src)
        ImageView movieImageView;
        @BindView(R.id.movie_info_text)
        TextView movieTitleTextView;

        public MovieInfoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void bindData(int position){
            movieImageView.setImageResource(movies.get(position).getImgSrc());
            movieTitleTextView.setText(movies.get(position).getText());
        }

    }

}
