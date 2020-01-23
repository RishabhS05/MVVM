package com.example.mvvmexample.models;

public class Hero {
   private int id ;
   private String name;
   private String realName;
   private String firstAppearance;
   private String createdBy;
   private String publisher;
   private String imageUrl;

    public Hero(int id ,String name, String realName, String firstAppearance, String createdBy, String publisher, String imageUrl) {
        this.name = name;
        this.id =id;
        this.realName = realName;
        this.firstAppearance = firstAppearance;
        this.createdBy = createdBy;
        this.publisher = publisher;
        this.imageUrl = imageUrl;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getRealName() {
        return realName;
    }

    public String getFirstAppearance() {
        return firstAppearance;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public void setFirstAppearance(String firstAppearance) {
        this.firstAppearance = firstAppearance;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
