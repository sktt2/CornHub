package com.cornhub;

import android.widget.ImageView;

import java.util.Random;

public class Corn {
    private int level = 0;
    private ImageView status = null;
    public Corn() {
        this(0);
    }
    public Corn(int level) {
        this.level = level;
    }

    public void grow() {
        this.level++;
        updateImg();
        System.out.println("Growing "+this.level);
    }

    public void updateImg(){
        if (level > 1){
            this.status.setImageResource(R.drawable.corn_1);
        }
        if (level > 2){
            this.status.setImageResource(R.drawable.corn_2);
        }
        if (level > 4){
            this.status.setImageResource(R.drawable.corn_3);
        }
        if (level > 6){
            this.status.setImageResource(R.drawable.corn_4);
        }
        if (level > 9){
            this.status.setImageResource(R.drawable.corn_5);
        }
    }
    public int getLevel() {
        return this.level;
    }
    public void setStatus(ImageView status) {
        this.status = status;
    }

    public int reap() {
        if (this.level < 10) {
            throw new Error("Level too low");
        } else {
            Random random = new Random();
            this.level = 0;
            this.status.setImageResource(R.drawable.corn_1);
            return random.nextInt(100) + 1;
        }
    }

}

