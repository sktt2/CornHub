package com.cornhub;

import java.util.ArrayList;

public class GameManager {

    private final Object lock1 = new Object();
    private final Object lock2 = new Object();
    private final Object lock3 = new Object();
    private ArrayList<Thread> plant1 = new ArrayList<>();
    private ArrayList<Thread> plant2 = new ArrayList<>();
    private ArrayList<Thread> plant3 = new ArrayList<>();
    private Corn corn1;
    private Corn corn2;
    private Corn corn3;
//    private ArrayList<Farmer> farmers;
    private ArrayList<Farmer> freeFarmers = new ArrayList<>();
    private ArrayList<Farmer> plot1Farmers = new ArrayList<>();
    private ArrayList<Farmer> plot2Farmers = new ArrayList<>();
    private ArrayList<Farmer> plot3Farmers = new ArrayList<>();


    public GameManager(ArrayList<Farmer> farmers, int plant1Assigned, int plant2Assigned, int plant3Assigned,int corn1Level, int corn2Level, int corn3Level){
        this.freeFarmers = farmers;
        this.corn1 = new Corn(corn1Level);
        this.corn2 = new Corn(corn2Level);
        this.corn3 = new Corn(corn3Level);

        for (int i = 0; i< plant1Assigned; i++ ){
            Farmer farmer = this.freeFarmers.remove(0);
            farmer.setLock(lock1);
            farmer.setCorn(this.corn1);
            this.plot1Farmers.add(farmer);
        }
        for (int i = 0; i< plant2Assigned; i++ ){
            Farmer farmer = this.freeFarmers.remove(0);
            farmer.setLock(lock2);
            farmer.setCorn(this.corn2);
            this.plot2Farmers.add(farmer);
        }
        for (int i = 0; i< plant3Assigned; i++ ){
            Farmer farmer = this.freeFarmers.remove(0);
            farmer.setLock(lock3);
            farmer.setCorn(this.corn3);
            this.plot3Farmers.add(farmer);
        }
    }
    public GameManager(ArrayList<Farmer> farmers){
        this(farmers, 0, 0, 0, 0,0,0);
    }
    public void addFarmer(int plant){
        Farmer farmer = this.freeFarmers.remove(0);
        if (plant == 1){
            farmer.setLock(lock1);
            farmer.setCorn(corn1);
            this.plot1Farmers.add(farmer);
        }else if (plant == 2){
            farmer.setLock(lock2);
            farmer.setCorn(corn2);
            this.plot2Farmers.add(farmer);
        }else{
            farmer.setLock(lock3);
            farmer.setCorn(corn3);
            this.plot3Farmers.add(farmer);
        }
    }
    public void removeFarmer(int plant){
        Farmer farmer = null;
        if (plant == 1){
            farmer = this.plot1Farmers.remove(0);
        }
        else if (plant == 2){
            farmer = this.plot2Farmers.remove(0);
        }
        else{
            farmer = this.plot3Farmers.remove(0);
        }
        this.freeFarmers.add(farmer);
        farmer.setCorn(null);
        farmer.setLock(null);
    }
}
