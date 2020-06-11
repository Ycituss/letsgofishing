package com.example.letsgofishing;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyData{

    public void CreatFish(){
        SQLiteDatabase dbFish;
        dbFish = SQLiteDatabase.openOrCreateDatabase("data/data/com.example.letsgofishing/dbFish.db",null);
        try{
            Cursor cursor = dbFish.rawQuery("select * from fish",null);
        }catch (Exception e){
            dbFish.execSQL("create  table fish(id integer primary key autoincrement,value int,state int,weight int)");
            dbFish.execSQL("insert into fish values(1,5,0,1)");
            dbFish.execSQL("insert into fish values(2,10,0,2)");
            dbFish.execSQL("insert into fish values(3,15,0,3)");
            dbFish.execSQL("insert into fish values(4,30,0,4)");
            dbFish.execSQL("insert into fish values(5,50,0,5)");
            dbFish.execSQL("insert into fish values(6,70,0,6)");
            dbFish.execSQL("insert into fish values(7,100,0,7)");
            dbFish.execSQL("insert into fish values(8,200,0,8)");
            dbFish.execSQL("insert into fish values(9,400,0,9)");
            dbFish.execSQL("insert into fish values(10,1000,0,10)");
        }
        dbFish.close();
    }

    public void CreatRod(){
        SQLiteDatabase dbRod;
        dbRod = SQLiteDatabase.openOrCreateDatabase("data/data/com.example.letsgofishing/dbRod.db",null);
        try{
            Cursor cursor = dbRod.rawQuery("select * from rod",null);
        }catch(Exception e){
            dbRod.execSQL("create  table rod(id integer primary key autoincrement,price int,state int,weight int)");
            dbRod.execSQL("insert into rod values(1,600,2,1)");
            dbRod.execSQL("insert into rod values(2,1200,0,2)");
            dbRod.execSQL("insert into rod values(3,2400,0,3)");
            dbRod.execSQL("insert into rod values(4,4000,0,4)");
            dbRod.execSQL("insert into rod values(5,6000,0,5)");
        }
        dbRod.close();
    }

    public void CreatBait(){
        SQLiteDatabase dbBait;
        dbBait = SQLiteDatabase.openOrCreateDatabase("data/data/com.example.letsgofishing/dbBait.db",null);
        try{
            Cursor cursor = dbBait.rawQuery("select * from bait",null);
        }catch(Exception e){
            dbBait.execSQL("create  table bait(id integer primary key autoincrement,price int,state int,weight int)");
            dbBait.execSQL("insert into bait values(1,600,2,1)");
            dbBait.execSQL("insert into bait values(2,1200,0,2)");
            dbBait.execSQL("insert into bait values(3,2400,0,3)");
            dbBait.execSQL("insert into bait values(4,4000,0,4)");
            dbBait.execSQL("insert into bait values(5,6000,0,5)");
        }
        dbBait.close();
    }

    public void CreatUser(){
        SQLiteDatabase dbUser;
        dbUser = SQLiteDatabase.openOrCreateDatabase("data/data/com.example.letsgofishing/dbUser.db",null);
        try{
            Cursor cursor = dbUser.rawQuery("select * from user",null);
        }catch(Exception e){
            dbUser.execSQL("create  table user(id integer primary key autoincrement,rod int,bait int,money int,state int)");
            dbUser.execSQL("insert into user values(1,1,1,0,0)");
        }
        dbUser.close();
    }
}
