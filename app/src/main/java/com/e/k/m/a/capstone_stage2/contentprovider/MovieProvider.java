package com.e.k.m.a.capstone_stage2.contentprovider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.e.k.m.a.capstone_stage2.database.MovieDatabaseHelper;


public class MovieProvider extends ContentProvider {
    static final String PROVIDER_NAME = "movienewsprovider";
    static final String URL = "content://"+PROVIDER_NAME+"/movies";
    public static final Uri CONTENT_URI = Uri.parse(URL);
    static final String ID = "id";
    static final String TITLE = "title";
    static final int MOVIES = 1;
    static final int MOVIE_ID = 2;
    static final UriMatcher uriMatcher;
    MovieDatabaseHelper databaseHelper;
    static{
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "movies", MOVIES);
        uriMatcher.addURI(PROVIDER_NAME, "movies/#", MOVIE_ID);
    }
    @Override
    public boolean onCreate() {
        Context context = getContext();
        databaseHelper = new MovieDatabaseHelper(context);
        return (databaseHelper == null)? false:true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        switch (uriMatcher.match(uri)) {
            case MOVIES:
                Cursor c1 = databaseHelper.queryMoiveData();
                c1.setNotificationUri(getContext().getContentResolver(),uri);
                return c1;
            case MOVIE_ID:
                Cursor c2 = databaseHelper.queryMoiveData(selection, selectionArgs);
                c2.setNotificationUri(getContext().getContentResolver(),uri);
                return c2;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }
    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri)){
            case MOVIES:
                return "vnd.android.cursor.dir/vnd.example.movies";
            case MOVIE_ID:
                return "vnd.android.cursor.item/vnd.example.movies";
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
            long rowId = databaseHelper.insertFavorite(values);
        if (rowId > -1) {
            Uri _uri = ContentUris.withAppendedId(uri,rowId);
            getContext().getContentResolver().notifyChange(_uri,null);
            return _uri;
        }
        throw new SQLException("Faild To add record to " + uri);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int delete = databaseHelper.deleteFavorite(selection,selectionArgs);
        return delete;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
