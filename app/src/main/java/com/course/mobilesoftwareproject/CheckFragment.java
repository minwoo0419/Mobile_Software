package com.course.mobilesoftwareproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;

import com.course.mobilesoftwareproject.structure.MealDetail;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;


public class CheckFragment extends Fragment {
    RecyclerView recyclerView;
    private CalendarView calendarView;
    private TextView dateText;
    MealDetailRepository mealDetailRepository;
    List<MealDetail> foodList;
    LinearLayoutManager layoutManager;
    String day;
    Double todayCal;
    Long todayPay;
    TextView calView;
    TextView priceView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mealDetailRepository = new MealDetailRepository(requireContext());
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_check, container, false);
        calendarView = view.findViewById(R.id.calendarView);
        dateText = view.findViewById(R.id.dateText);
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        day = year + "-" + month + "-" + dayOfMonth;
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        String[] daysOfWeek = {"일", "월", "화", "수", "목", "금", "토"};
        String dayName = daysOfWeek[dayOfWeek - 1];
        dateText.setText(String.format("%s (%s)", day, dayName));
        // 캘린더 리스너 설정
        calendarView.setOnDateChangeListener(new DateChangeListener());
        recyclerView = view.findViewById(R.id.recyclerView2);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        foodList = mealDetailRepository.getMealDetailByDate(day);
        todayCal = 0.0;
        todayPay = 0L;
        for (int i = 0 ; i < foodList.size() ; i++){
            todayCal += foodList.get(i).getCalories();
            todayPay += foodList.get(i).getPrice();
        }
        calView = view.findViewById(R.id.textView9);
        priceView = view.findViewById(R.id.textView11);
        calView.setText(String.format("%.2f", todayCal) + "kcal");
        priceView.setText(todayPay.toString() + "원");
        MealAdapter adapter = new MealAdapter(foodList);
        recyclerView.setAdapter(adapter);
        return view;
    }

    class DateChangeListener implements CalendarView.OnDateChangeListener {
        @Override
        public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, dayOfMonth);
            day = year + "-" + (month+1) + "-" + dayOfMonth;
            foodList = mealDetailRepository.getMealDetailByDate(day);
            todayCal = 0.0;
            todayPay = 0L;
            for (int i = 0 ; i < foodList.size() ; i++){
                todayCal += foodList.get(i).getCalories();
                todayPay += foodList.get(i).getPrice();
            }
            Log.d("calnum", todayCal.toString());
            Log.d("pricenum", todayPay.toString());
            calView.setText(String.format("%.2f", todayCal) + "kcal");
            priceView.setText(todayPay.toString() + "원");
            SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE");
            String dayOfWeek = dateFormat.format(calendar.getTime());
            MealAdapter adapter = new MealAdapter(foodList);
            recyclerView.setAdapter(adapter);
            dateText.setText(String.format("%d.%d.%d (%s)", year, month+1, dayOfMonth, convertToKoreanDayOfWeek(dayOfWeek)));
        }
    }

    public static String convertToKoreanDayOfWeek(String dayOfWeek) {
        String koreanDayOfWeek;
        switch (dayOfWeek) {
            case "Monday":
                koreanDayOfWeek = "월";
                break;
            case "Tuesday":
                koreanDayOfWeek = "화";
                break;
            case "Wednesday":
                koreanDayOfWeek = "수";
                break;
            case "Thursday":
                koreanDayOfWeek = "목";
                break;
            case "Friday":
                koreanDayOfWeek = "금";
                break;
            case "Saturday":
                koreanDayOfWeek = "토";
                break;
            case "Sunday":
                koreanDayOfWeek = "일";
                break;
            default:
                koreanDayOfWeek = "알 수 없음";
        }
        return koreanDayOfWeek;
    }
}
