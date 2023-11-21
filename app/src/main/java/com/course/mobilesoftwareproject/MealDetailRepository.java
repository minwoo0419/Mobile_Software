package com.course.mobilesoftwareproject;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.course.mobilesoftwareproject.structure.FoodDetail;
import com.course.mobilesoftwareproject.structure.MealDetail;

import java.util.ArrayList;
import java.util.List;

public class MealDetailRepository {

    private DBHelper dbHelper;

    public MealDetailRepository(Context context) {
        dbHelper = new DBHelper(context);
    }

    // 모든 식사 정보 가져오기
    public List<MealDetail> getAllMealDetails() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                "id",
                "place",
                "image",
                "type",
                "review",
                "date",
                "time",
                "price"
        };

        Cursor cursor = db.query(
                "foodlist",   // 테이블 이름
                projection,   // 가져올 컬럼 배열
                null,
                null,
                null,
                null,
                null
        );

        List<MealDetail> mealDetails = new ArrayList<>();

        if (cursor != null) {
            while (cursor.moveToNext()) {
                long id = cursor.getLong(cursor.getColumnIndexOrThrow("id"));
                String place = cursor.getString(cursor.getColumnIndexOrThrow("place"));
                String image = cursor.getString(cursor.getColumnIndexOrThrow("image"));
                String type = cursor.getString(cursor.getColumnIndexOrThrow("type"));
                String review = cursor.getString(cursor.getColumnIndexOrThrow("review"));
                String date = cursor.getString(cursor.getColumnIndexOrThrow("date"));
                String time = cursor.getString(cursor.getColumnIndexOrThrow("time"));
                long price = cursor.getLong(cursor.getColumnIndexOrThrow("price"));

                // 식사에 속한 음식 정보 가져오기
                List<FoodDetail> foodDetails = getFoodDetails(id);

                MealDetail mealDetail = new MealDetail(id, place, image, type, review, date, time, price, foodDetails);
                mealDetails.add(mealDetail);
            }

            cursor.close();
        }

        db.close();

        return mealDetails;
    }
    public List<MealDetail> getMealDetailByDate(String toDay) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                "id",
                "place",
                "image",
                "type",
                "review",
                "date",
                "time",
                "price"
        };

        String selection = "date=?";
        String[] selectionArgs = {toDay};

        Cursor cursor = db.query(
                "foodlist",   // 테이블 이름
                projection,   // 가져올 컬럼 배열
                selection,    // WHERE 절
                selectionArgs, // WHERE 절 인자
                null,
                null,
                null
        );

        List<MealDetail> mealDetails = new ArrayList<>();

        if (cursor != null) {
            while (cursor.moveToNext()) {
                long id = cursor.getLong(cursor.getColumnIndexOrThrow("id"));
                String place = cursor.getString(cursor.getColumnIndexOrThrow("place"));
                String image = cursor.getString(cursor.getColumnIndexOrThrow("image"));
                String type = cursor.getString(cursor.getColumnIndexOrThrow("type"));
                String review = cursor.getString(cursor.getColumnIndexOrThrow("review"));
                String date = cursor.getString(cursor.getColumnIndexOrThrow("date"));
                String time = cursor.getString(cursor.getColumnIndexOrThrow("time"));
                long price = cursor.getLong(cursor.getColumnIndexOrThrow("price"));

                // 식사에 속한 음식 정보 가져오기
                List<FoodDetail> foodDetails = getFoodDetails(id);

                MealDetail mealDetail = new MealDetail(id, place, image, type, review, date, time, price, foodDetails);
                mealDetails.add(mealDetail);
            }

            cursor.close();
        }

        db.close();

        return mealDetails;
    }

    // 특정 식사에 속한 음식 정보 가져오기
    private List<FoodDetail> getFoodDetails(long mealId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                "id",
                "foodlist_id",
                "name",
                "calorie"
        };

        String selection = "foodlist_id=?";
        String[] selectionArgs = {String.valueOf(mealId)};

        Cursor cursor = db.query(
                "food",        // 테이블 이름
                projection,    // 가져올 컬럼 배열
                selection,     // WHERE 절
                selectionArgs,  // WHERE 절 인자
                null,
                null,
                null
        );

        List<FoodDetail> foodDetails = new ArrayList<>();

        if (cursor != null) {
            while (cursor.moveToNext()) {
                long id = cursor.getLong(cursor.getColumnIndexOrThrow("id"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                double calorie = cursor.getDouble(cursor.getColumnIndexOrThrow("calorie"));

                FoodDetail foodDetail = new FoodDetail(id, name, calorie);
                foodDetails.add(foodDetail);
            }

            cursor.close();
        }

        db.close();

        return foodDetails;
    }
}
