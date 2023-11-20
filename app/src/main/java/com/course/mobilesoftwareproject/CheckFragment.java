package com.course.mobilesoftwareproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;


public class CheckFragment extends Fragment {
    private CalendarView calendarView;
    private TextView dateText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_check, container, false);
        // 추가: 캘린더 뷰와 텍스트 뷰 참조
        calendarView = view.findViewById(R.id.calendarView);
        dateText = view.findViewById(R.id.dateText);
        LocalDate currentDate = LocalDate.now();
        // 날짜를 특정 형식으로 포맷팅
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        String formattedDate = currentDate.format(formatter);
        DayOfWeek dayOfWeek = currentDate.getDayOfWeek();
        String dayOfWeekStr = dayOfWeek.getDisplayName(java.time.format.TextStyle.FULL, java.util.Locale.getDefault());
        dateText.setText(String.format("%s (%s)", formattedDate, convertToKoreanDayOfWeek(dayOfWeekStr)));
        // 캘린더 리스너 설정
        calendarView.setOnDateChangeListener(new DateChangeListener());
        return view;
    }

    class DateChangeListener implements CalendarView.OnDateChangeListener {
        @Override
        public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, dayOfMonth);
            SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE");
            String dayOfWeek = dateFormat.format(calendar.getTime());
            // 선택된 날짜를 텍스트 뷰에 표시
            dateText.setText(String.format("%d.%d.%d (%s)", year, month + 1, dayOfMonth, convertToKoreanDayOfWeek(dayOfWeek)));
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
