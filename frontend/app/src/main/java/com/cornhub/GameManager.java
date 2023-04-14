package com.cornhub;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class GameManager implements View.OnClickListener {

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
    private int farmerCount;

    private GameActivity activity;
    private TextView coinCount;
    private TextView count1;
    private TextView count2;
    private TextView count3;
    private TextView farmerCountText;
    private Button plot1Plus;
    private Button plot2Plus;
    private Button plot3Plus;
    private Button plot1Minus;
    private Button plot2Minus;
    private Button plot3Minus;
    private Button buyButton;
    private ImageView corn1Button;
    private ImageView corn2Button;
    private ImageView corn3Button;
    private TextView minus1Text;
    private TextView minus2Text;
    private TextView minus3Text;
    private TextView plus1Text;
    private TextView plus2Text;
    private TextView plus3Text;
    private DBHandler db;
    public GameManager(GameActivity activity,ArrayList<Farmer> farmers, int plant1Assigned, int plant2Assigned, int plant3Assigned,int corn1Level, int corn2Level, int corn3Level, int farmerCost, int gold, int total){

        db = new DBHandler(activity);
        this.activity = activity;
        this.farmerCount = farmers.size();
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
        this.init();
    }
    public GameManager(GameActivity activity, ArrayList<Farmer> farmers){
        this(activity, farmers, 0, 0, 0, 0,0,0, 10, 0, 0);
    }
    private void init() {
        coinCount = activity.findViewById(R.id.coinCount);
        coinCount.setText(gold+"");
        plot1Plus = activity.findViewById(R.id.buttonPlus1);
        plot1Plus.setOnClickListener(this);
        plot1Minus = activity.findViewById(R.id.buttonMinus1);
        plot1Minus.setOnClickListener(this);
        plot2Plus = activity.findViewById(R.id.buttonPlus2);
        plot2Plus.setOnClickListener(this);
        plot2Minus = activity.findViewById(R.id.buttonMinus2);
        plot2Minus.setOnClickListener(this);
        plot3Plus = activity.findViewById(R.id.buttonPlus3);
        plot3Plus.setOnClickListener(this);
        plot3Minus = activity.findViewById(R.id.buttonMinus3);
        plot3Minus.setOnClickListener(this);

        count1 = activity.findViewById(R.id.Count1);
        count1.setText(plot1Farmers.size()+"");
        count2 = activity.findViewById(R.id.Count2);
        count2.setText(plot2Farmers.size()+"");
        count3 = activity.findViewById(R.id.Count3);
        count3.setText(plot3Farmers.size()+"");

        buyButton = activity.findViewById(R.id.button3);
        buyButton.setOnClickListener(this);
        buyButton.setText("BUY FARMER ("+this.farmerCost+"G)");

        corn1Button = activity.findViewById(R.id.corn1);
        corn1Button.setOnClickListener(this);
        corn1.setStatus(corn1Button);
        corn1.updateImg();

        corn2Button = activity.findViewById(R.id.corn2);
        corn2Button.setOnClickListener(this);
        corn2.setStatus(corn2Button);
        corn2.updateImg();

        corn3Button = activity.findViewById(R.id.corn3);
        corn3Button.setOnClickListener(this);
        corn3.setStatus(corn3Button);
        corn3.updateImg();

        farmerCountText = activity.findViewById(R.id.farmerCount);
        farmerCountText.setText(this.farmerCount+"");
        minus1Text = activity.findViewById(R.id.Minus1);
        minus2Text = activity.findViewById(R.id.Minus2);
        minus3Text = activity.findViewById(R.id.Minus3);
        plus1Text = activity.findViewById(R.id.Plus1);
        plus2Text = activity.findViewById(R.id.Plus2);
        plus3Text = activity.findViewById(R.id.Plus3);
        updateButtons();
    }
    public void addFarmer(int plant){
        Farmer farmer = this.freeFarmers.remove(0);
        if (plant == 1){
            farmer.setLock(lock1);
            farmer.setCorn(corn1);
            this.plot1Farmers.add(farmer);
            this.plot1Threads.add(new Thread(farmer.water));
            this.plot1Threads.get(this.plot1Threads.size()-1).start();
            this.count1.setText(plot1Farmers.size()+"");

        }else if (plant == 2){
            farmer.setLock(lock2);
            farmer.setCorn(corn2);
            this.plot2Farmers.add(farmer);
            this.plot2Threads.add(new Thread(farmer.water));
            this.plot2Threads.get(this.plot2Threads.size()-1).start();
            this.count2.setText(plot2Farmers.size()+"");

        }else{
            farmer.setLock(lock3);
            farmer.setCorn(corn3);
            this.plot3Farmers.add(farmer);
            this.plot3Threads.add(new Thread(farmer.water));
            this.plot3Threads.get(this.plot3Threads.size()-1).start();
            this.count3.setText(plot3Farmers.size()+"");
        }
        updateButtons();
    }
    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.buttonPlus1){
            addFarmer(1);
        }
        if (id == R.id.buttonMinus1){
            removeFarmer(1);
        }
        if (id == R.id.buttonPlus2){
            addFarmer(2);
        }
        if (id == R.id.buttonMinus2){
            removeFarmer(2);
        }
        if (id == R.id.buttonPlus3){
            addFarmer(3);
        }
        if (id == R.id.buttonMinus3){
            removeFarmer(3);
        }
        if (id == R.id.corn1){
            reap(1);
        }
        if (id == R.id.corn2){
            reap(2);
        }
        if (id == R.id.corn3){
            reap(3);
        }
        if (id == R.id.button3){
            buyFarmer();
        }
    }

    public void updateButtons() {
        db.updateData(this.farmerCount, this.plot1Farmers.size(), this.plot2Farmers.size(), this.plot3Farmers.size(), this.corn1.getLevel(), this.corn2.getLevel(), this.corn3.getLevel(), this.farmerCost, this.gold, this.total );

        if (this.freeFarmers.size() == 0){
            this.plot1Plus.setVisibility(View.INVISIBLE);
            this.plot2Plus.setVisibility(View.INVISIBLE);
            this.plot3Plus.setVisibility(View.INVISIBLE);
            this.plus1Text.setVisibility(View.INVISIBLE);
            this.plus2Text.setVisibility(View.INVISIBLE);
            this.plus3Text.setVisibility(View.INVISIBLE);
        }
        if (this.freeFarmers.size() > 0 ){
            this.plot1Plus.setVisibility(View.VISIBLE);
            this.plot2Plus.setVisibility(View.VISIBLE);
            this.plot3Plus.setVisibility(View.VISIBLE);
            this.plus1Text.setVisibility(View.VISIBLE);
            this.plus2Text.setVisibility(View.VISIBLE);
            this.plus3Text.setVisibility(View.VISIBLE);
        }
        if (this.plot1Farmers.size() == 0) {
            this.plot1Minus.setVisibility(View.INVISIBLE);
            this.minus1Text.setVisibility(View.INVISIBLE);
        }
        if (this.plot2Farmers.size() == 0) {
            this.plot2Minus.setVisibility(View.INVISIBLE);
            this.minus2Text.setVisibility(View.INVISIBLE);
        }
        if (this.plot3Farmers.size() == 0) {
            this.plot3Minus.setVisibility(View.INVISIBLE);
            this.minus3Text.setVisibility(View.INVISIBLE);
        }
        if (this.plot1Farmers.size() > 0) {
            this.plot1Minus.setVisibility(View.VISIBLE);
            this.minus1Text.setVisibility(View.VISIBLE);
        }
        if (this.plot2Farmers.size() > 0) {
            this.plot2Minus.setVisibility(View.VISIBLE);
            this.minus2Text.setVisibility(View.VISIBLE);
        }
        if (this.plot3Farmers.size() > 0) {
            this.plot3Minus.setVisibility(View.VISIBLE);
            this.minus3Text.setVisibility(View.VISIBLE);
        }
    }
    public void removeFarmer(int plant){
        Farmer farmer = null;
        Thread thread = null;
        if (plant == 1){
            farmer = this.plot1Farmers.remove(0);
            thread = this.plot1Threads.remove(0);
            this.count1.setText(plot1Farmers.size()+"");

        }
        else if (plant == 2){
            farmer = this.plot2Farmers.remove(0);
            thread = this.plot2Threads.remove(0);
            this.count2.setText(plot2Farmers.size()+"");
        }
        else{
            farmer = this.plot3Farmers.remove(0);
            thread = this.plot3Threads.remove(0);
            this.count3.setText(plot3Farmers.size()+"");
        }
        this.freeFarmers.add(farmer);
        farmer.setCorn(null);
        farmer.setLock(null);
        thread.interrupt();
        updateButtons();

    }
    public boolean buyFarmer(){
        if (this.gold < this.farmerCost){
            return false;
        }

        this.gold -= this.farmerCost;
        this.farmerCost = this.farmerCost*2;
        this.freeFarmers.add(new Farmer());
        coinCount.setText(this.gold+"");
        buyButton.setText("BUY FARMER ("+this.farmerCost+"G)");
        this.farmerCount++;
        this.farmerCountText.setText(this.farmerCount+"");
        updateButtons();
        return true;
    }

    public void reap(int plant) {
        int amount = 0;
        if (plant == 1){
            try {
                amount = corn1.reap();
            } catch(Error e){

            }
        }else if (plant == 2){
            try {
                amount = corn2.reap();
            } catch(Error e){

            }
        }else{
            try {
                amount = corn3.reap();
            } catch(Error e){

            }
        }
        total += amount;
        gold +=amount;
        coinCount.setText(this.gold+"");
        startThread(plant);
        db.updateData(this.farmerCount, this.plot1Farmers.size(), this.plot2Farmers.size(), this.plot3Farmers.size(), this.corn1.getLevel(), this.corn2.getLevel(), this.corn3.getLevel(), this.farmerCost, this.gold, this.total );
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
