package com.course.mobilesoftwareproject.structure;

public class FoodDetail {
    private Long id;
    private String name;
    private Double calorie;
    public FoodDetail(Long id, String name, Double calorie){
        this.id = id;
        this.name = name;
        this.calorie = calorie;
    }
    public Long getId(){
        return this.id;
    }
    public String getName(){
        return this.name;
    }
    public Double getCalorie(){
        return this.calorie;
    }
}
