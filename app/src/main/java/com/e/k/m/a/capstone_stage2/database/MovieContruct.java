package com.e.k.m.a.capstone_stage2.database;

import android.provider.BaseColumns;

/**
 * Created by ahmedelmoselhy on 4/14/2018.
 */
public class MovieContruct {
    public static final class Favorite implements BaseColumns {
        public static final String TABLE_NAME="Favorite";
        public static final String MOVIE_ID="movie_id";
        public static final String POSTER_PATH="poster_path";
        public static final String OVERVIEW="overview";
        public static final String RELEASE_DATE="release_date";
        public static final String TITLE="title";
        public static final String VOTE_AVERAGE="vote_average";
    }
}
