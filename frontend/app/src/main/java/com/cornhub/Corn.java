package com.cornhub;

import java.util.Random;

public class Corn {
    private int level = 0;

    public Corn(int level) {
        this.level = level;
    }

    public void grow() {
        this.level++;
    }

    public int getLevel() {
        return this.level;
    }

    public int reap() {
        if (this.level < 10) {
            throw new Error("Level too low");
        } else {
            Random random = new Random();
            this.level = 0;
            return random.nextInt(100) + 1;
        }
    }

}

