package com.e.k.m.a.capstone_stage2;

import android.app.ActivityOptions;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.LightingColorFilter;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.e.k.m.a.capstone_stage2.Models.Movie;
import com.e.k.m.a.capstone_stage2.Models.MovieDetail;
import com.e.k.m.a.capstone_stage2.Models.MovieInfo;
import com.e.k.m.a.capstone_stage2.Models.MoviesResponse;
import com.e.k.m.a.capstone_stage2.Network.BaseUrls;
import com.e.k.m.a.capstone_stage2.Network.rest.ApiClient;
import com.e.k.m.a.capstone_stage2.Network.rest.ApiInteface;
import com.e.k.m.a.capstone_stage2.adapter.MovieAdatpter;
import com.e.k.m.a.capstone_stage2.adapter.MovieInfoAdapter;
import com.e.k.m.a.capstone_stage2.constants.Constants;
import com.e.k.m.a.capstone_stage2.contentprovider.MovieProvider;
import com.e.k.m.a.capstone_stage2.database.MovieContruct;
import com.e.k.m.a.capstone_stage2.fragment.TrailerFragment;
import com.github.zagum.switchicon.SwitchIconView;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;
import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.e.k.m.a.capstone_stage2.constants.Constants.MOVIE_ID;

public class DetailActivity extends AppCompatActivity {


    @BindView(R.id.recycler_movie_info)
    RecyclerView movieInfoRecyclerView;
    @BindView(R.id.detail_container_layout)
    LinearLayout linearLayout;
    @BindView(R.id.recyclerview_similar)
    RecyclerView similarRecyclerView;
    @BindView(R.id.recyclerview_recomended)
    RecyclerView recomendedRecyclerView;
    @BindView(R.id.img_backdrop_path)
    ImageView backDrobPathImageView;
    @BindView(R.id.tv_tagline)
    TextView tagLineTextView;
    @BindView(R.id.tv_overview)
    TextView overviewTextView;
    @BindView(R.id.btn_trailer)
    Button trailerButton;
    @BindView(R.id.btn_favorite)
    SwitchIconView favorite;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.my_toolbar)
    Toolbar myToolbar ;



    private static final String TAG = DetailActivity.class.getSimpleName();
    private final static String API_KEY = "41049e5d4f52d9922464f0055a20caaa";
    private BaseUrls baseUrls;
    private ApiInteface apiService;
    private MovieDetail movieDetail;
    private MovieInfoAdapter movieInfoAdapter;
    private int movieId;
    private List<Movie> movies = new ArrayList<>();
    private List<MovieInfo> movieInfo = new ArrayList<>();
    private MovieAdatpter movieAdatpter;
    private Cursor c;
    private FirebaseAnalytics mFirebaseAnalytics;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);
        baseUrls = new BaseUrls();
        linearLayout.setVisibility(View.GONE);
        apiService = ApiClient.getClient().create(ApiInteface.class);
        movieId = getIntent().getIntExtra(MOVIE_ID,0);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        getMovieDetail(movieId);
        addMovieToFavorites();
    }

    private void updateAllWidgets() {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(getApplicationContext());
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(getApplication(), MovieWidget.class));
        if (appWidgetIds.length > 0) {
            new MovieWidget().onUpdate(getApplicationContext(), appWidgetManager, appWidgetIds);
        }
    }

    private void initUi(){

        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, String.valueOf(movieDetail.getId()));
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, movieDetail.getTitle());
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, baseUrls.getIMAGE_BASE_URL()+movieDetail.getPoster_path());
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

        myToolbar.setTitle(movieDetail.getTitle());
        setSupportActionBar(myToolbar);

        tagLineTextView.setText(movieDetail.getTagline());
        overviewTextView.setText(movieDetail.getOverview());

        if (!movieDetail.getBackdrop_path().isEmpty()){
            Picasso.get().load(baseUrls.getIMAGE_BASE_URL()+movieDetail.getBackdrop_path()).into(backDrobPathImageView);
            backDrobPathImageView.setScaleType(ImageView.ScaleType.FIT_XY);
        }

        movieInfo.add(new MovieInfo(R.drawable.ic_clock,movieDetail.getRuntime()+""));
        movieInfo.add(new MovieInfo(R.drawable.ic_release_date,movieDetail.getRelease_date()+""));
        movieInfo.add(new MovieInfo(R.drawable.ic_money,movieDetail.getRevenue()+""));
        movieInfo.add(new MovieInfo(R.drawable.ic_budget,movieDetail.getBudget()+""));
        movieInfo.add(new MovieInfo(R.drawable.ic_employee,movieDetail.getVote_count()+""));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        movieInfoRecyclerView.setLayoutManager(linearLayoutManager);
        movieInfoAdapter = new MovieInfoAdapter(this,movieInfo);
        movieInfoRecyclerView.setAdapter(movieInfoAdapter);

        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        similarRecyclerView.setLayoutManager(linearLayoutManager1);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recomendedRecyclerView.setLayoutManager(linearLayoutManager2);

        Handler uiHandler = new Handler(Looper.getMainLooper());
        uiHandler.post(new Runnable(){
            @Override
            public void run() {
                Paper.book().write("title", movieDetail.getTitle());
                Paper.book().write("image", movieDetail.getPoster_path());
                updateAllWidgets();
            }
        });

    }
    public void getMovieDetail(final int id){
        Log.e("id",id+"");
        Call<MovieDetail> call = apiService.getMovieDetails(id,API_KEY);
        call.enqueue(new Callback<MovieDetail>() {
            @Override
            public void onResponse(Call<MovieDetail> call, Response<MovieDetail> response) {

                movieDetail = response.body();
                if (!movieDetail.equals(null)){
                    initUi();
                    getSimilarMovies(id);
                    getRecomendedMovies(id);
                    checkIfMovieFavorite();
                }
                Log.e("movieDetail",movieDetail.getTitle());
            }

            @Override
            public void onFailure(Call<MovieDetail> call, Throwable t) {
                Log.e(TAG,t.getMessage());
            }
        });
    }
    public void getSimilarMovies(int id){
        Call<MoviesResponse> call = apiService.getSimilarMovies(id,API_KEY);
        call.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                movies = response.body().getResults();
                movieAdatpter = new MovieAdatpter(DetailActivity.this,movies);
                similarRecyclerView.setAdapter(movieAdatpter);

            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                Log.e(TAG,""+t.getMessage());
            }
        });
    }
    public void getRecomendedMovies(int id){
        Call<MoviesResponse> call = apiService.getRecommendationsMovies(id,API_KEY);
        call.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                Log.e(TAG,movies.size()+"");
                movies = response.body().getResults();
                movieAdatpter = new MovieAdatpter(DetailActivity.this,movies);
                recomendedRecyclerView.setAdapter(movieAdatpter);
                progressBar.setVisibility(View.GONE);
                linearLayout.setVisibility(View.VISIBLE);

            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                Log.e(TAG,""+t.getMessage());
            }
        });
    }
    public void addMovieToFavorites(){
        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!favorite.isIconEnabled()){
                    // here add movie to database
                    ContentValues values =  new ContentValues();
                    values.put(MovieContruct.Favorite.MOVIE_ID, movieDetail.getId());
                    values.put(MovieContruct.Favorite.POSTER_PATH, movieDetail.getPoster_path());
                    values.put(MovieContruct.Favorite.OVERVIEW, movieDetail.getOverview());
                    values.put(MovieContruct.Favorite.RELEASE_DATE, movieDetail.getRelease_date());
                    values.put(MovieContruct.Favorite.TITLE, movieDetail.getTitle());
                    values.put(MovieContruct.Favorite.VOTE_AVERAGE, movieDetail.getVote_average());
                    Uri uri = getContentResolver().insert(MovieProvider.CONTENT_URI,values);
                    Log.e(DetailActivity.class.getSimpleName(),uri+"");
                    favorite.setIconEnabled(true);
                    Toasty.success(getApplicationContext(), getResources().getString(R.string.movie_add), Toast.LENGTH_SHORT).show();
                }else {
                    // here delete movie from database
                    String selection = "movie_id = ?";
                    String selectionArgs [] = new String[]{movieDetail.getId() + ""};
                    int delete = getContentResolver().delete(MovieProvider.CONTENT_URI,selection,selectionArgs);
                    if (delete > -1) {
                        Toasty.error(getApplicationContext(), getResources().getString(R.string.movie_deleted), Toast.LENGTH_SHORT).show();
                        favorite.setIconEnabled(false   );
                    }
                    else
                        Toasty.error(getApplicationContext(), getResources().getString(R.string.deleted_failed), Toast.LENGTH_SHORT).show();
                }
                }
        });
    }
    public void checkIfMovieFavorite(){

        //this method to check if the movie in favorite list
        String URL = MovieProvider.CONTENT_URI+"/"+movieDetail.getId();
        Uri uri = Uri.parse(URL);
        String selection = "movie_id = ?";
        String selectionArgs [] = new String[]{movieDetail.getId() + ""};
        c = getContentResolver().query(uri,null,selection,selectionArgs,null);
        if (c.moveToFirst()){
            favorite.setIconEnabled(true);
        }else {
            favorite.setIconEnabled(false);
        }
        }

        public void openTrailers(View view){
        ContainerActivity.myFragment = new TrailerFragment();
            TrailerFragment.MOVIE_ID = movieDetail.getId();
            TrailerFragment.MOVIE_POSTER = movieDetail.getBackdrop_path();
            Intent i = new Intent(this,ContainerActivity.class);
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(this).toBundle();
                startActivity(i, bundle);
            } else {
                startActivity(i);
            }
        }
}
