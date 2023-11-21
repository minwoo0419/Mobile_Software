package com.course.mobilesoftwareproject.structure;

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
    private List<FoodDetail> foods = new ArrayList<>();
    private Double calories;
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
        int flag = 0;
        for(int i = 0 ; i < foods.size() ; i++){
            calories += foods.get(i).getCalorie();
            if (i < 2){
                name = name + " " + foods.get(i).getName();
            }
            else if (i >= 2 && flag == 0){
                name += "...";
                flag = 1;
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
        return this.type;
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
