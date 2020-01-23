package com.example.mvvmexample.viewModels;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mvvmexample.models.Hero;
import com.example.mvvmexample.repos.HerosRepo;

import java.util.List;

public class AvangersDetailsViewModels extends ViewModel {
     private MutableLiveData<List<Hero>> mutableLiveDataHeros;
     private HerosRepo repo;
     //mutable live data is  a sub class of Live data
public void init(){
if (mutableLiveDataHeros!= null){
    Log.d("ADVM", "init: ");
    return;
}
repo= HerosRepo.getInstance();
mutableLiveDataHeros = repo.getHeroes();
}
    public MutableLiveData<List<Hero>> getMutableLiveDataHeros() {
        return mutableLiveDataHeros;
    }
}
