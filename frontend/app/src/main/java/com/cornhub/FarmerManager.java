package com.cornhub;

import java.util.ArrayList;

public class FarmerManager {
    private ArrayList<Farmer> farmers = new ArrayList<>();

    public void addFarmer(Farmer farmer){
        farmers.add(farmer);
    }

    public ArrayList<Farmer> getFarmers(){
        return this.farmers;
    }
}
