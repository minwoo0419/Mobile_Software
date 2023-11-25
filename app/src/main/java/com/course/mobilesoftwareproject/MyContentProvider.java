package com.course.mobilesoftwareproject;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.net.Uri;

public class MyContentProvider extends ContentProvider {
    static final String PROVIDER_NAME = "com.course.mobilesoftwareproject.MyContentProvider";
    static final String TABLE_NAME = "foodlist";
    static final String URL = "content://" + PROVIDER_NAME + "/" + TABLE_NAME;
    static final Uri CONTENT_URI = Uri.parse(URL);
    static final String _ID = "_id";
    static final String REVIEW = "review";
    static final String TYPE = "type";
    static final String PLACE = "place";
    static final String DATE = "date";
    static final String TIME = "time";
    static final String PRICE = "price";
    static final String IMAGE_URI = "image";
    public DBHelper dbManager;

    public MyContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return dbManager.delete(selection, selectionArgs, TABLE_NAME);
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    public Uri insert(Uri uri, ContentValues values) {
        long rowId = dbManager.insert(values, TABLE_NAME);

        if (rowId > 0) {
            Uri insertedItemUri = ContentUris.withAppendedId(CONTENT_URI, rowId);
            getContext().getContentResolver().notifyChange(insertedItemUri, null);
            return insertedItemUri;
        }

        throw new SQLException("Failed to add a record into " + uri);
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