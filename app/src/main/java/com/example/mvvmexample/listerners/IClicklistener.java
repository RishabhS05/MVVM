package com.example.mvvmexample.listerners;


import com.example.mvvmexample.models.Hero;

public interface IClicklistener {
     int UPDATE =1;
     int DELETE =0;
     void onclick(Hero hero, int Flag);
}
