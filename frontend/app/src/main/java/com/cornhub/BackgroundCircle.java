package com.cornhub;

import java.util.Random;

/**
 * A class that represents details of a single circle shape to be shown in the background.
 */
public class BackgroundCircle {
    private final int x;

    private final int y;

    private final int r;

    private final int g;

    private final int b;

    private int radius;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getR() {
        return r;
    }

    public int getG() {
        return g;
    }

    public int getB() {
        return b;
    }

    public int getRadius() {
        return radius;
    }

    public void grow() {
        ++radius;
    }

    public BackgroundCircle(final int width, final int height) {
        Random dice = new Random();
        x = dice.nextInt(width);
        y = dice.nextInt(height);
        r = dice.nextInt(256);
        g = dice.nextInt(256);
        b = dice.nextInt(256);
        radius = dice.nextInt(256);
    }
}
