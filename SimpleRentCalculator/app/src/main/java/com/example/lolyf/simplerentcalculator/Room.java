package com.example.lolyf.simplerentcalculator;

/**
 * Created by lolyf on 2/2/2018.
 */


public class Room {

    private String type, name;
    private float size, price;

    public Room (float size, String type, String name){
        this.type = type;
        this.size = size;
        this.name = name;
        this.price = 0;
    }

    public float getPrice(){
        return price;
    }

    public float getSize(){
        return size;
    }

    public String getType(){
        return type;
    }

    public String getName(){
        return name;
    }

    public void setPrice(float price){
        this.price = price;
    }
}
