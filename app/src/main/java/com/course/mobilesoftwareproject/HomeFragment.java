package com.course.mobilesoftwareproject;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.course.mobilesoftwareproject.structure.MealDetail;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;


public class HomeFragment extends Fragment {
    MealDetailRepository mealDetailRepository;
    List<MealDetail> foodList;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mealDetailRepository = new MealDetailRepository(requireContext());
        LocalDate currentDate = LocalDate.now(ZoneId.of("Asia/Seoul"));
        // 날짜를 특정 형식으로 포맷팅
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        String formattedDate = currentDate.format(formatter);
        Log.d("date", formattedDate);
        foodList = mealDetailRepository.getMealDetailByDate(formattedDate);
        if (!foodList.isEmpty())
            Log.d("foodList", foodList.get(0).getName());
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ImageView button = view.findViewById(R.id.addBtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addMealListener(view);
            }
        });
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        MealAdapter adapter = new MealAdapter(foodList);
        recyclerView.setAdapter(adapter);
        // Inflate the layout for this fragment
        return view;
    }
    public void addMealListener(View view){
        Intent intent = new Intent(getActivity(), InputPage.class);
        startActivity(intent);
    }
}