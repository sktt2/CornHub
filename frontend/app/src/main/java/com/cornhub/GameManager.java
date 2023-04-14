package com.cornhub;

import java.util.ArrayList;

public class GameManager {

    private final Object lock1 = new Object();
    private final Object lock2 = new Object();
    private final Object lock3 = new Object();
    private ArrayList<Thread> plant1 = new ArrayList<>();
    private ArrayList<Thread> plant2 = new ArrayList<>();
    private ArrayList<Thread> plant3 = new ArrayList<>();
    private int plant1Assigned;
    private int plant2Assigned;
    private int plant3Assigned;
    private ArrayList<Farmer> farmers;
    private final Runnable farmer;
    public GameManager(Runnable farmer, ArrayList<Farmer> farmers, int plant1Assigned, int plant2Assigned, int plant3Assigned){
        this.farmer = farmer;
        this.farmers = farmers;
        this.plant1Assigned = plant1Assigned;
        this.plant2Assigned = plant2Assigned;
        this.plant3Assigned = plant3Assigned;
    }
    public GameManager(Runnable farmer, ArrayList<Farmer> farmers)

}
