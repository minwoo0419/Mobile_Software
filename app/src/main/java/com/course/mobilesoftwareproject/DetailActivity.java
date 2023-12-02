package com.course.mobilesoftwareproject;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.course.mobilesoftwareproject.structure.FoodDetail;
import com.course.mobilesoftwareproject.structure.MealDetail;

import java.text.NumberFormat;
import java.util.List;

public class DetailActivity extends AppCompatActivity {
    MealDetailRepository mealDetailRepository;
    MealDetail mealDetail;
    List<FoodDetail> foodDetailList;
    TextView typeView, dateView, whereView, priceView, reviewView;
    ImageView imageView;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new DBHelper(this);
        mealDetailRepository = new MealDetailRepository(this);
        mealDetail = mealDetailRepository.getMealDetail(getIntent().getLongExtra("id", -1L));
        foodDetailList = mealDetail.getFoods();
        setContentView(R.layout.activity_detail);
        typeView = findViewById(R.id.detailType);
        dateView = findViewById(R.id.detailTime);
        whereView = findViewById(R.id.detailWhere);
        priceView = findViewById(R.id.detailPrice);
        reviewView = findViewById(R.id.detailReview);
        imageView = findViewById(R.id.detailImg);
        typeView.setText(mealDetail.getType());
        dateView.setText(setDay());
        whereView.setText(mealDetail.getPlace());
        priceView.setText("총 " + formatNumberWithCommas(mealDetail.getPrice()));
        reviewView.setText(mealDetail.getReview());
        imageView.setImageBitmap(mealDetail.getImage());
        RecyclerView recyclerView = findViewById(R.id.detailRecycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        DetailAdapter adapter = new DetailAdapter(foodDetailList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
    public String setDay(){
        String month, dayOfMonth;
        month = mealDetail.getDate().split("-")[1];
        dayOfMonth = mealDetail.getDate().split("-")[2];
        return month + "월 " + dayOfMonth + "일 " + mealDetail.getTime();
    }
    public void backListener(View view){
        finish();
    }
    public static String formatNumberWithCommas(long number) {
        NumberFormat numberFormat = NumberFormat.getInstance();
        return numberFormat.format(number) + "원";
    }
    public void delete(View view){
        long id = getIntent().getLongExtra("id", -1L);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try{
            db.delete("foodlist", "id=?", new String[]{String.valueOf(id)});
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally {
            db.close();
            Toast.makeText(this, "삭제가 완료되었습니다.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
    }
}