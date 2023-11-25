package com.course.mobilesoftwareproject;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

public class FoodProvider extends ContentProvider {
    static final String PROVIDER_NAME = "com.course.mobilesoftwareproject.FoodProvider";
    static final String TABLE_NAME = "food";
    static final String URL = "content://" + PROVIDER_NAME + "/" + TABLE_NAME;
    static final Uri CONTENT_URI = Uri.parse(URL);
    static final String _ID = "_id";
    static final String FOOD_LIST_ID = "foodlist_id";
    static final String NAME = "name";
    static final String CALORIE = "calorie";
    public DBHelper dbManager;

    public FoodProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return dbManager.delete(selection, selectionArgs, TABLE_NAME);
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long rowId = dbManager.insert(values, TABLE_NAME);
        return null;
    }

    @Override
    public boolean onCreate() {
        dbManager = DBHelper.getInstance(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        return dbManager.query(TABLE_NAME, projection, selection, selectionArgs,
                null, null, sortOrder);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}