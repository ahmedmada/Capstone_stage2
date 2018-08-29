package com.e.k.m.a.capstone_stage2.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.e.k.m.a.capstone_stage2.DetailActivity;
import com.e.k.m.a.capstone_stage2.Models.Trailer;
import com.e.k.m.a.capstone_stage2.Network.BaseUrls;
import com.e.k.m.a.capstone_stage2.Network.CheckInternetConnection;
import com.e.k.m.a.capstone_stage2.R;
import com.squareup.picasso.Picasso;

import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;


public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder> {

    Context context;
    List<Trailer> trailers;
    String imageUrl;
    public TrailerAdapter() {
    }

    public TrailerAdapter(Context context, List<Trailer> trailers,String imageUrl) {
        this.context = context;
        this.trailers = trailers;
        this.imageUrl = imageUrl;
    }

    @NonNull
    @Override
    public TrailerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.trailer_card,parent,false);
        TrailerViewHolder trailerViewHolder = new TrailerViewHolder(view);
        return trailerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerViewHolder holder, final int position) {
        if (!trailers.isEmpty()){
        holder.bindData(position);
        }
    }

    @Override
    public int getItemCount() {
        if (trailers.size() > 0)
            return trailers.size();
        else return 10;
    }

    class TrailerViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.trailer_image)
        ImageView trailerImageView;
        @BindView(R.id.trailer_name)
        TextView trailerTextView;
        public TrailerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,
                    itemView);
        }

        public void bindData(final int position){
            final BaseUrls baseUrls = new BaseUrls();
            if (!imageUrl.isEmpty()){
                Picasso.get().load(baseUrls.getIMAGE_BASE_URL()+imageUrl).into(trailerImageView);
                trailerImageView.setScaleType(ImageView.ScaleType.FIT_XY);
            }
            if (!trailers.get(position).getName().isEmpty()){
                trailerTextView.setText(trailers.get(position).getName());
            }

            trailerImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (CheckInternetConnection.isConnected(context)) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(baseUrls.getVIDEO_BASE_URL() +
                                trailers.get(position).getKey()));
                        if (intent.resolveActivity(context.getPackageManager()) != null) {
                            context.startActivity(intent);

                        }
                    }else
                        Toast.makeText(context, R.string.no_internet, Toast.LENGTH_SHORT).show();
                }
            });

        }

    }

}

