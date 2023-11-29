package com.course.mobilesoftwareproject;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "projects";
    private static final int DATABASE_VERSION = 1;
    private static DBHelper dbHelper = null;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public static DBHelper getInstance(Context context) {
        if (dbHelper == null) {
            dbHelper = new DBHelper(context);
        }
        return dbHelper;
    }
    private static final String CREATE_ORDER_TABLE = "CREATE TABLE foodlist (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "place TEXT," +
            "image BLOB," +
            "type TEXT," +
            "review TEXT," +
            "date DATE DEFAULT (CURRENT_DATE)," +
            "time TIME," +
            "price INTEGER);";
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
    public long insert(ContentValues addValue, String table) {
        return getWritableDatabase().insert(table, null, addValue);
    }

    public Cursor query(String table, String[] columns, String selection, String[] selectionArgs,
                        String groupBy, String having, String orderBy) {
        return getReadableDatabase().query(table, columns, selection, selectionArgs, groupBy, having, orderBy);
    }

    public int delete(String whereClause, String[] whereArgs, String table) {
        return getWritableDatabase().delete(table, whereClause,
                whereArgs);
    }

}
