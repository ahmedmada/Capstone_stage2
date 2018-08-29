package com.e.k.m.a.capstone_stage2.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;
public class MovieDatabaseHelper extends SQLiteOpenHelper {
    Context context;
    public MovieDatabaseHelper(Context context) {
        super(context, "Movie Data Base", null, 1);
        this.context = context;
    }
    private static final String TAG = MovieDatabaseHelper.class.getSimpleName();
    private final String SQL_CREATE_FAVORITE_TABLE = "CREATE TABLE " + MovieContruct.Favorite.TABLE_NAME + " (" +
            MovieContruct.Favorite.MOVIE_ID + " INTEGER PRIMARY KEY," +
            MovieContruct.Favorite.POSTER_PATH + " TEXT, " +
            MovieContruct.Favorite.OVERVIEW + " TEXT, " +
            MovieContruct.Favorite.RELEASE_DATE + " TEXT," +
            MovieContruct.Favorite.TITLE + " TEXT, " +
            MovieContruct.Favorite.VOTE_AVERAGE + " TEXT" +
            " );";
    private static final String SQL_DELETE_FAVORITE_TABLE=
            "DROP TABLE IF EXISTS " + MovieContruct.Favorite.TABLE_NAME;
    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(SQL_CREATE_FAVORITE_TABLE);
        }catch (SQLException e){
            Log.e(TAG + " DATABASE CREATE",e.getMessage());
        }
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL(SQL_DELETE_FAVORITE_TABLE);
            onCreate(db);
        }catch (SQLException e){
            Log.e(TAG + " DATABASE DELETE",e.getMessage());
        }
    }
    public long insertFavorite(ContentValues values) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        long insert = sqLiteDatabase.insert(MovieContruct.Favorite.TABLE_NAME, null, values);
        return insert;
    }
    public int  deleteFavorite(String selection,String[] selectionArgs) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        int delete = sqLiteDatabase.delete(MovieContruct.Favorite.TABLE_NAME, selection, selectionArgs);
        return delete;
    }
    public Cursor queryMoiveData(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor c=sqLiteDatabase.query(MovieContruct.Favorite.TABLE_NAME, null, null, null, null, null, null);
        return c;
    }
    public Cursor queryMoiveData(String selection, String[] selectionArgs){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor c=sqLiteDatabase.query(MovieContruct.Favorite.TABLE_NAME, null, selection, selectionArgs, null, null, null);
        return c;
    }    public boolean checkFavoritFilm(int id){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor c=sqLiteDatabase.query(MovieContruct.Favorite.TABLE_NAME,null,"movie_id = ?", new String[]{id + ""},null,null,null);
        return c.moveToFirst();
    }

}
