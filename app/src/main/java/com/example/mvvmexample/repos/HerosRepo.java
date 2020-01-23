package com.example.mvvmexample.repos;

import androidx.lifecycle.MutableLiveData;

import com.example.mvvmexample.models.Hero;

import java.util.ArrayList;
import java.util.List;

public class HerosRepo {

    private static  HerosRepo instance;
    private ArrayList<Hero> dataset = new ArrayList<>();
    public static HerosRepo  getInstance(){
        return instance==null?new HerosRepo():instance;
    }
    private HerosRepo(){
    }
    public MutableLiveData<List<Hero>> getHeroes(){
        MutableLiveData<List<Hero>>data =new MutableLiveData<>();
        data.setValue(dataset);
        return  data;
    }
    public void setHeroes(List<Hero> heroes) {
        this.dataset=(ArrayList<Hero>) heroes;
    }
}
