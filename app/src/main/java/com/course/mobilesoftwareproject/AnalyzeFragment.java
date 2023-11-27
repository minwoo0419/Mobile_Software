package com.course.mobilesoftwareproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.course.mobilesoftwareproject.structure.FoodDetail;
import com.course.mobilesoftwareproject.structure.MealDetail;

import java.util.Calendar;
import java.util.List;


public class AnalyzeFragment extends Fragment {
    MealDetailRepository mealDetailRepository;
    List<MealDetail> foodList;
    String day;
    Double totalCal;
    Long totalBreak, totalLunch, totalDinner, totalDessert;
    double breakCnt, lunchCnt, dinnerCnt, dessertCnt;
    Double avgBreak, avgLunch, avgDinner, avgDessert;
    Long avgPay;
    TextView breakView, lunchView, dinnerView, dessertView, dateView;
    TextView totBreak, totLunch, totCal, totDinner, totDessert;
    ImageView minus, plus;
    ProgressBar pBreak, pLunch, pDinner, pDessert, pCal;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mealDetailRepository = new MealDetailRepository(requireContext());
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        day = year + "-" + month;
        calcul(day);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_analyze, container, false);
        breakView = view.findViewById(R.id.avgBreak);
        lunchView = view.findViewById(R.id.avgLunch);
        dinnerView = view.findViewById(R.id.avgDinner);
        dessertView = view.findViewById(R.id.avgDessert);
        dateView = view.findViewById(R.id.analDate);
        minus = view.findViewById(R.id.leftBtn);
        plus = view.findViewById(R.id.rightBtn);
        pBreak = view.findViewById(R.id.progressbreak);
        pLunch = view.findViewById(R.id.progresslunch);
        pDinner = view.findViewById(R.id.progressdinner);
        pDessert = view.findViewById(R.id.progressdessert);
        totBreak = view.findViewById(R.id.tot_break);
        totLunch = view.findViewById(R.id.tot_lunch);
        totDinner = view.findViewById(R.id.tot_dinner);
        totDessert = view.findViewById(R.id.tot_dessert);
        totCal = view.findViewById(R.id.tot_cal);
        totBreak.setText(totalBreak.toString()+"원");
        totLunch.setText(totalLunch.toString()+"원");
        totDinner.setText(totalDinner.toString()+"원");
        totDessert.setText(totalDessert.toString()+"원");
        totCal.setText(totalCal.toString()+"kcal");
        pCal = view.findViewById(R.id.progresscal);
        pBreak.setMax(620000);
        pLunch.setMax(620000);
        pDinner.setMax(620000);
        pDessert.setMax(620000);
        pCal.setMax(77500);
        pBreak.setProgress(totalBreak.intValue());
        pLunch.setProgress(totalLunch.intValue());
        pDinner.setProgress(totalDinner.intValue());
        pDessert.setProgress(totalDessert.intValue());
        pCal.setProgress(totalCal.intValue());
        dateView.setText(day);
        breakView.setText(avgBreak.toString());
        lunchView.setText(avgLunch.toString());
        dinnerView.setText(avgDinner.toString());
        dessertView.setText(avgDessert.toString());
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                minusDate(view);
            }
        });
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                plusDate(view);
            }
        });

        // Inflate the layout for this fragment
        return view;
    }
    public void minusDate(View view){
        Integer year = Integer.parseInt(day.split("-")[0]);
        Integer month = Integer.parseInt(day.split("-")[1]);
        if (month == 1){
            year -= 1;
            month = 12;
        }
        else{
            month -= 1;
        }
        day = year + "-" + month;
        dateView.setText(day);
        calcul(day);
        breakView.setText(avgBreak.toString());
        lunchView.setText(avgLunch.toString());
        dinnerView.setText(avgDinner.toString());
        dessertView.setText(avgDessert.toString());
        totBreak.setText(totalBreak.toString()+"원");
        totLunch.setText(totalLunch.toString()+"원");
        totDinner.setText(totalDinner.toString()+"원");
        totDessert.setText(totalDessert.toString()+"원");
        totCal.setText(totalCal.toString()+"kcal");
        pBreak.setProgress(totalBreak.intValue());
        pLunch.setProgress(totalLunch.intValue());
        pDinner.setProgress(totalDinner.intValue());
        pDessert.setProgress(totalDessert.intValue());
        pCal.setProgress(totalCal.intValue());
        Log.d("day", day);
    }
    public void plusDate(View view){
        Integer year = Integer.parseInt(day.split("-")[0]);
        Integer month = Integer.parseInt(day.split("-")[1]);
        if (month == 12){
            year += 1;
            month = 1;
        }
        else{
            month += 1;
        }
        day = year + "-" + month;
        dateView.setText(day);
        calcul(day);
        breakView.setText(avgBreak.toString());
        lunchView.setText(avgLunch.toString());
        dinnerView.setText(avgDinner.toString());
        dessertView.setText(avgDessert.toString());
        totBreak.setText(totalBreak.toString()+"원");
        totLunch.setText(totalLunch.toString()+"원");
        totDinner.setText(totalDinner.toString()+"원");
        totDessert.setText(totalDessert.toString()+"원");
        totCal.setText(totalCal.toString()+"kcal");
        pBreak.setProgress(totalBreak.intValue());
        pLunch.setProgress(totalLunch.intValue());
        pDinner.setProgress(totalDinner.intValue());
        pDessert.setProgress(totalDessert.intValue());
        pCal.setProgress(totalCal.intValue());
        Log.d("day", day);
    }
    public void calcul(String day){
        totalCal = 0.0;
        totalBreak = 0L;
        totalLunch = 0L;
        totalDinner = 0L;
        totalDessert = 0L;
        breakCnt = 0;
        lunchCnt = 0;
        dinnerCnt = 0;
        dessertCnt = 0;
        avgBreak = 0.0;
        avgLunch = 0.0;
        avgDinner = 0.0;
        avgDessert = 0.0;
        avgPay = 0L;
        foodList = mealDetailRepository.getMealDetailByMonth(this.day);
        for (int i = 0 ; i < foodList.size() ; i++){
            String type = foodList.get(i).getType();
            totalCal += foodList.get(i).getCalories();
            switch (type){
                case "BreakFast":
                    totalBreak += foodList.get(i).getPrice();
                    breakCnt += 1;
                    break;
                case "Lunch":
                    totalLunch += foodList.get(i).getPrice();
                    lunchCnt += 1;
                    break;
                case "Dinner":
                    totalDinner += foodList.get(i).getPrice();
                    dinnerCnt += 1;
                    break;
                case "Dessert":
                    totalDessert += foodList.get(i).getPrice();
                    dessertCnt += 1;
                    break;
                default:
                    Log.d("err", "err");
            }
        }
        if (breakCnt > 0)
            avgBreak = totalBreak / breakCnt;
        if (lunchCnt > 0)
            avgLunch = totalLunch / lunchCnt;
        if (dinnerCnt > 0)
            avgDinner = totalDinner / dinnerCnt;
        if (dessertCnt > 0)
            avgDessert = totalDessert / dessertCnt;
    }
}