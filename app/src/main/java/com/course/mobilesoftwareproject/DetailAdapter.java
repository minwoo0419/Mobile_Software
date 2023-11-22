package com.course.mobilesoftwareproject;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.course.mobilesoftwareproject.structure.FoodDetail;

import java.util.List;

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.MyViewHolder>{
    private List<FoodDetail> foodList;
    public DetailAdapter(List<FoodDetail> foodList){
        this.foodList = foodList;
    }
    @Override
    public DetailAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_name_item, parent, false);
        return new MyViewHolder(view);
    }
    @Override
    public void onBindViewHolder(DetailAdapter.MyViewHolder holder, int position) {
        FoodDetail data = foodList.get(position);
        holder.bind(data);
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView name, cal;
        public MyViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.detailName);
            cal = itemView.findViewById(R.id.detailCal);
        }
        public void bind(FoodDetail data) {
            String calStr = String.format("%.2f", data.getCalorie());
            cal.setText(calStr + "kcal");
            name.setText(data.getName());
        }
    }
}
