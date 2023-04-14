package com.cornhub;

public class Farmer {
    private Corn corn = null;
    private Object lock = null;
    public Corn getCorn() {
        return corn;
    }
    public void setCorn(Corn corn) {
        this.corn = corn;
    }

    public Object getLock() {
        return lock;
    }
    public void setLock(Object lock) {this.lock = lock;}
    public Runnable water = new Runnable() {
        @Override
        public void run() {
            while (true) {
                try {
                    System.out.println("Sleepin for 5 seconds");
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    System.out.println("Thread is interrupted");
                    break;
                }
                int level = corn.getLevel();
                if (level == 10) {
                    System.out.println("passaway is good");
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
