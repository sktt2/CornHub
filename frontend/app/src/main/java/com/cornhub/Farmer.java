package com.cornhub;

public class Farmer {
    private Corn corn;
    private Object lock;
    public Corn getCorn() {
        return corn;
    }
    public void setCorn(Corn corn) {
        this.corn = corn;
    }

    public Object getLock() {
        return lock;
    }
    public void setLock(Object lock) {this.lock = lock}
    public Runnable water = new Runnable() {
        @Override
        public void run() {

        }
    }
    public void water(){
        //TODO add timer

    }
}
