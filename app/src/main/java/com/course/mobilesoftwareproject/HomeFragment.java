package com.course.mobilesoftwareproject;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.course.mobilesoftwareproject.structure.MealDetail;
import java.util.Calendar;
import java.util.List;


public class HomeFragment extends Fragment {
    MealDetailRepository mealDetailRepository;
    List<MealDetail> foodList;
    Double todayCal;
    Long todayPay;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mealDetailRepository = new MealDetailRepository(requireContext());
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        String formattedDate = year + "-" + month + "-" + dayOfMonth;
        todayCal = 0.0;
        todayPay = 0L;
        foodList = mealDetailRepository.getMealDetailByDate(formattedDate);
        for (int i = 0 ; i < foodList.size() ; i++){
            todayCal += foodList.get(i).getCalories();
            todayPay += foodList.get(i).getPrice();
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        TextView calView = view.findViewById(R.id.todayCaloryNum);
        TextView priceView = view.findViewById(R.id.todayPaynum);
        calView.setText(String.format("%.2f", todayCal) + "kcal");
        priceView.setText(todayPay.toString() + "원");
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        MealAdapter adapter = new MealAdapter(foodList);
        recyclerView.setAdapter(adapter);
        return view;
    }
}