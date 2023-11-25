package com.course.mobilesoftwareproject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentUris;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.course.mobilesoftwareproject.structure.FoodDetail;
import com.course.mobilesoftwareproject.structure.MealDetail;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class DetailActivity extends AppCompatActivity {
    MealDetailRepository mealDetailRepository;
    MealDetail mealDetail;
    List<FoodDetail> foodDetailList;
    TextView typeView, dateView, whereView, priceView, reviewView;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        priceView.setText("총 " + mealDetail.getPrice().toString() + "원");
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
}