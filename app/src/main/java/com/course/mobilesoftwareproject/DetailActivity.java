package com.course.mobilesoftwareproject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.course.mobilesoftwareproject.structure.FoodDetail;
import com.course.mobilesoftwareproject.structure.MealDetail;

import java.io.FileNotFoundException;
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
        try {
            InputStream inputStream = getContentResolver().openInputStream(Uri.parse(mealDetail.getImage()));
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            imageView.setImageBitmap(bitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            // 이미지를 찾을 수 없는 경우 기본 이미지 설정 또는 오류 처리 로직 추가
            imageView.setImageResource(R.drawable.add);
        }
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