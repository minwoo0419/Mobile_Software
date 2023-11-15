package com.course.mobilesoftwareproject.structure;

public class FoodDetail {
    private Long id;
    private String name;
    private Long calorie;
    public FoodDetail(Long id, String name, Long calorie){
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
    public Long getCalorie(){
        return this.calorie;
    }
}
