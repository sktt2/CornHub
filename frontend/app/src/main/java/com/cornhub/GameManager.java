package com.cornhub;

import java.util.ArrayList;

public class GameManager {

    private final Object lock1 = new Object();
    private final Object lock2 = new Object();
    private final Object lock3 = new Object();
    private ArrayList<Thread> plot1Threads = new ArrayList<>();
    private ArrayList<Thread> plot2Threads = new ArrayList<>();
    private ArrayList<Thread> plot3Threads = new ArrayList<>();
    private Corn corn1;
    private Corn corn2;
    private Corn corn3;
//    private ArrayList<Farmer> farmers;
    private ArrayList<Farmer> freeFarmers = new ArrayList<>();
    private ArrayList<Farmer> plot1Farmers = new ArrayList<>();
    private ArrayList<Farmer> plot2Farmers = new ArrayList<>();
    private ArrayList<Farmer> plot3Farmers = new ArrayList<>();

    private int farmerCost = 0;

    private int gold = 0;
    private int total = gold;

    public GameManager(ArrayList<Farmer> farmers, int plant1Assigned, int plant2Assigned, int plant3Assigned,int corn1Level, int corn2Level, int corn3Level, int farmerCost, int gold, int total){
        this.freeFarmers = farmers;
        this.corn1 = new Corn(corn1Level);
        this.corn2 = new Corn(corn2Level);
        this.corn3 = new Corn(corn3Level);
        this.farmerCost = farmerCost;
        this.gold = gold;
        this.total = total;
        for (int i = 0; i< plant1Assigned; i++ ){
            Farmer farmer = this.freeFarmers.remove(0);
            farmer.setLock(lock1);
            farmer.setCorn(this.corn1);
            this.plot1Farmers.add(farmer);
            this.plot1Threads.add(new Thread(farmer.water));
        }
        for (int i = 0; i< plant2Assigned; i++ ){
            Farmer farmer = this.freeFarmers.remove(0);
            farmer.setLock(lock2);
            farmer.setCorn(this.corn2);
            this.plot2Farmers.add(farmer);
            this.plot2Threads.add(new Thread(farmer.water));
        }
        for (int i = 0; i< plant3Assigned; i++ ){
            Farmer farmer = this.freeFarmers.remove(0);
            farmer.setLock(lock3);
            farmer.setCorn(this.corn3);
            this.plot3Farmers.add(farmer);
            this.plot3Threads.add(new Thread(farmer.water));
        }
        for (Thread thread: this.plot1Threads){
            thread.start();
        }
        for (Thread thread: this.plot2Threads){
            thread.start();
        }
        for (Thread thread: this.plot3Threads){
            thread.start();
        }
    }
    public GameManager(ArrayList<Farmer> farmers){
        this(farmers, 0, 0, 0, 0,0,0, 10, 0, 0);
    }
    public void addFarmer(int plant){
        Farmer farmer = this.freeFarmers.remove(0);
        if (plant == 1){
            farmer.setLock(lock1);
            farmer.setCorn(corn1);
            this.plot1Farmers.add(farmer);
            this.plot1Threads.add(new Thread(farmer.water));
            this.plot1Threads.get(this.plot1Threads.size()-1).start();

        }else if (plant == 2){
            farmer.setLock(lock2);
            farmer.setCorn(corn2);
            this.plot2Farmers.add(farmer);
            this.plot2Threads.add(new Thread(farmer.water));
            this.plot2Threads.get(this.plot2Threads.size()-1).start();

        }else{
            farmer.setLock(lock3);
            farmer.setCorn(corn3);
            this.plot3Farmers.add(farmer);
            this.plot3Threads.add(new Thread(farmer.water));
            this.plot3Threads.get(this.plot3Threads.size()-1).start();
        }
    }
    public void removeFarmer(int plant){
        Farmer farmer = null;
        Thread thread = null;
        if (plant == 1){
            farmer = this.plot1Farmers.remove(0);
            thread = this.plot1Threads.remove(0);
        }
        else if (plant == 2){
            farmer = this.plot2Farmers.remove(0);
            thread = this.plot2Threads.remove(0);
        }
        else{
            farmer = this.plot3Farmers.remove(0);
            thread = this.plot3Threads.remove(0);
        }
        this.freeFarmers.add(farmer);
        farmer.setCorn(null);
        farmer.setLock(null);
        thread.interrupt();
    }
    public boolean buyFarmer(){
        if (this.gold < this.farmerCost){
            return false;
        }
        this.gold -= this.farmerCost;
        this.farmerCost = this.farmerCost*10;
        this.freeFarmers.add(new Farmer());
        return true;
    }

    public void reap(int plant) {
        int amount = 0;
        if (plant == 1){
            amount = corn1.reap();
        }else if (plant == 2){
            amount = corn2.reap();
        }else{
            amount= corn3.reap();
        }
        total += amount;
        gold +=amount;
    }

    public void startThread(int plant) {
        if (plant == 1){
            while (this.plot1Threads.size() != 0){
                Thread thread = this.plot1Threads.remove(0);
                thread.interrupt();
            }
            for (Farmer farmer : plot1Farmers){
                this.plot1Threads.add(new Thread(farmer.water));
            }
            for (Thread thread: plot1Threads){
                thread.start();
            }
        }
        if (plant == 2){
            while (this.plot2Threads.size() != 0){
                Thread thread = this.plot2Threads.remove(0);
                thread.interrupt();
            }
            for (Farmer farmer : plot2Farmers){
                this.plot2Threads.add(new Thread(farmer.water));
            }
            for (Thread thread: plot2Threads){
                thread.start();
            }
        }
        if (plant == 3){
            while (this.plot3Threads.size() != 0){
                Thread thread = this.plot3Threads.remove(0);
                thread.interrupt();
            }
            for (Farmer farmer : plot3Farmers){
                this.plot3Threads.add(new Thread(farmer.water));
            }
            for (Thread thread: plot3Threads){
                thread.start();
            }
        }
    }
}
