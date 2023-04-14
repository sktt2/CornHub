package com.cornhub;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {
    private static final String DB_Name = "farmSim";
    private static final int DB_VERSION = 1;
    private static final String Table_Name = "farm";

    public DBHandler(Context context){
        super(context, DB_Name, null, DB_VERSION);
        drop();
        SQLiteDatabase db = getWritableDatabase();
        String query = "CREATE TABLE IF NOT EXISTS farm " +
                "(id INT, farmerCount INT, plant1Assigned INT, plant2Assigned INT, plant3Assigned INT," +
                "corn1Level INT, corn2Level INT, corn3Level INT,"+
                "farmerCost INT, gold INT, total INT, CONSTRAINT id_pk PRIMARY KEY(id));";
        db.execSQL(query);
        String insert_query = "INSERT INTO farm (id, farmerCount, plant1Assigned, plant2Assigned, plant3Assigned,corn1Level, corn2Level, corn3Level, farmerCost, gold, total) VALUES (1,3,0,0,0,0,0,0,10,15,15)";

        db.execSQL(insert_query);

        String query_check = "SELECT * FROM farm";
        SQLiteDatabase readDB = this.getReadableDatabase();
        //Cursor cursor = readDB.rawQuery(query_check, null);

        int[] data = new int[10];
        int i = 0;
        cursor.moveToFirst();

        String stored ="ALLAH LOVES US ALL : ";
        while(cursor.isAfterLast() == false ) {
            stored += cursor.getInt(i);
            cursor.moveToNext();
            i++;
        }
        System.out.println(stored);
    }


    @Override
    public void onCreate(SQLiteDatabase db){
//            String query = "CREATE TABLE IF NOT EXISTS farm " +
//                    "(ID INT PRIMARY KEY, farmerCount INT, plant1Assigned INT, plant2Assigned INT, plant3Assigned INT," +
//                    "corn1Level INT, corn2Level INT, corn3Level INT,"+
//                    "farmerCost INT, gold INT, total INT);";
//            db.execSQL(query);
//            String insert_query = "INSERT INTO farm (farmerCount, plant1Assigned, plant2Assigned, plant3Assigned, corn1Level, corn2Level, corn3Level, farmerCost, gold, total) " +
//                    "SELECT * FROM (SELECT 1,3,0,0,0,0,0,0,10,15,15) WHERE NOT EXISTS (SELECT * FROM farm)";
//            db.execSQL(insert_query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL("DROP TABLE IF EXISTS farm");
        onCreate(db);
    }

    public int[] getData(){
        String query_check = "SELECT * FROM farm";
        SQLiteDatabase readDB = this.getReadableDatabase();
        Cursor cursor = readDB.rawQuery(query_check, null);

        int[] data = new int[10];
        int i = 0;
        while(cursor.moveToNext()) {
            data[i] = cursor.getInt(i);
            i++;
        }
        return data;
    }

    public void updateData(int farmerCount, int plant1Assigned, int plant2Assigned, int plant3Assigned,int corn1Level, int corn2Level, int corn3Level, int farmerCost, int gold, int total){
        SQLiteDatabase writeDB = this.getWritableDatabase();
        String update = "UPDATE farm SET farmerCount = " + farmerCount + ", plant1Assigned = " + plant1Assigned + ", plant2Assigned = " + plant2Assigned + ", plant3Assigned = " + plant3Assigned +
                ", corn1Level = " + corn1Level + ", corn2Level = " + corn2Level + ", corn3Level = " + corn3Level +
                ", farmerCost = " + farmerCost + ", gold = " + gold + ", total = " + total + " WHERE ID = 1;";
        System.out.println (update);
        Cursor c = writeDB.rawQuery(update,null);
        c.moveToFirst();
        c.close();

        int[] data = getData();
        for(int i : data){
            System.out.println(i);
        }
    }

    public void drop(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS farm");
    }

}
