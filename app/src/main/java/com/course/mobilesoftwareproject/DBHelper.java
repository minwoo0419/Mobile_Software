package com.course.mobilesoftwareproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "project";
    private static final int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // foodlist 테이블 생성 쿼리
    private static final String CREATE_ORDER_TABLE = "CREATE TABLE foodlist (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "place TEXT," +
            "image TEXT," +
            "type TEXT," +
            "review TEXT," +
            "date DATE DEFAULT (CURRENT_DATE)," +
            "time TIME," +
            "price INTEGER);";

    // food 테이블 생성 쿼리
    private static final String CREATE_FOOD_TABLE = "CREATE TABLE food (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "foodlist_id INTEGER," +
            "name TEXT," +
            "calorie NUMERIC(10, 2)," +
            "FOREIGN KEY (foodlist_id) REFERENCES foodlist (id) ON DELETE CASCADE ON UPDATE CASCADE);";

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 테이블 생성
        db.execSQL(CREATE_ORDER_TABLE);
        db.execSQL(CREATE_FOOD_TABLE);
        Log.d("DB", "생성완료");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS foodlist");
        db.execSQL("DROP TABLE IF EXISTS food");
        onCreate(db);
    }
}
