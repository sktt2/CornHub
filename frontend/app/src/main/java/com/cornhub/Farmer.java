package com.cornhub;

public class Farmer {
    private Corn corn = null;
    private Object lock = null;

    public void setCorn(Corn corn) {
        this.corn = corn;
    }

    public void setLock(Object lock) {this.lock = lock;}
    public Runnable water = new Runnable() {
        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    break;
                }
                int level = corn.getLevel();
                if (level == 10) {
                    break;
                }
                synchronized (lock){
                    corn.grow();
                    lock.notifyAll();
                }
            }
        }
    };
}
