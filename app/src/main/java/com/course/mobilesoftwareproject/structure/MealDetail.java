package com.course.mobilesoftwareproject.structure;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MealDetail {
    private Long id;
    private String place;
    private String image;
    private String type;
    private String review;
    private String date;
    private String time;
    private Long price;
    private List<FoodDetail> foods;
    private Double calories = 0.0;
    private String name;
    public MealDetail(Long id, String place, String image, String type, String review, String date, String time, Long price, List<FoodDetail> foods){
        this.id = id;
        this.place = place;
        this.image = image;
        this.type = type;
        this.review = review;
        this.date = date;
        this.time = time;
        this.price = price;
        this.foods = foods;
        for(int i = 0 ; i < foods.size() ; i++){
            if (!foods.get(i).getName().equals("")){
                calories += foods.get(i).getCalorie();
                if (i == 0){
                    name = foods.get(i).getName();
                }
                else if (i == 1){
                    name = name + ", " + foods.get(i).getName();
                }
                else if (i >= 2 && foods.size() != 3){
                    name += "...";
                    break;
                }
            }
        }
    }
    public Long getId(){
        return this.id;
    }
    public String getPlace(){
        return this.place;
    }
    public String getImage(){
        return this.image;
    }
    public String getType(){
        switch(this.type){
            case "아침":
                return "BreakFast";
            case "점심":
                return "Lunch";
            case "저녁":
                return "Dinner";
            case "디저트":
                return "Dessert";
            default:
                return "알 수 없음";
        }
    }
    public String getReview(){
        return this.review;
    }
    public String getDate(){
        return this.date;
    }
    public String getTime(){
        return this.time;
    }
    public Long getPrice(){
        return this.price;
    }
    public List<FoodDetail> getFoods(){
        return this.foods;
    }
    public Double getCalories(){
        return this.calories;
    }
    public String getName() {return this.name;}
}
