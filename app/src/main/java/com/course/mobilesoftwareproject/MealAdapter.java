package com.course.mobilesoftwareproject;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.course.mobilesoftwareproject.structure.MealDetail;

import java.text.NumberFormat;
import java.util.List;

public class MealAdapter extends RecyclerView.Adapter<MealAdapter.MyViewHolder> {
    private List<MealDetail> foodList;
    public MealAdapter(List<MealDetail> foodList){
        this.foodList = foodList;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        if (foodList.isEmpty()) {
            View emptyView = inflater.inflate(R.layout.food_list_empty, parent, false);
            return new EmptyViewHolder(emptyView);
        } else {
            View itemView = inflater.inflate(R.layout.food_list_item, parent, false);
            return new MyViewHolder(itemView);
        }
    }

    @Override
    public int getItemCount() {
        return Math.max(foodList.size(), 1);}
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if (!foodList.isEmpty()) {
            MealDetail data = foodList.get(position);
            holder.bind(data);

            holder.tab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra("id", data.getId());
                    context.startActivity(intent);
                }
            });
            holder.list.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra("id", data.getId());
                    context.startActivity(intent);
                }
            });
        }
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView type, name, price, cal, tab;
        private ConstraintLayout list;
        private ImageView image;

        public MyViewHolder(View itemView) {
            super(itemView);
            type = itemView.findViewById(R.id.type);
            name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.priceNum);
            cal = itemView.findViewById(R.id.calNum);
            image = itemView.findViewById(R.id.foodImg);
            tab = itemView.findViewById(R.id.tab);
            list = itemView.findViewById(R.id.food_list);
        }
        public void bind(MealDetail data) {
            type.setText(data.getType());
            price.setText(formatNumberWithCommas(data.getPrice()));
            String calStr = String.format("%.2f", data.getCalories());
            cal.setText(calStr);
            name.setText(data.getName());
            Glide.with(itemView.getContext())
                    .load(data.getImage())
                    .circleCrop()
                    .fitCenter()
                    .into(image);
        }
    }
    public static class EmptyViewHolder extends MyViewHolder {
        public EmptyViewHolder(View itemView) {
            super(itemView);
        }
    }
    public static String formatNumberWithCommas(long number) {
        NumberFormat numberFormat = NumberFormat.getInstance();
        return numberFormat.format(number) + "원";
    }
}
