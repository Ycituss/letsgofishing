package com.example.letsgofishing;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class ArchiveActivity extends AppCompatActivity {

    private MusicServer myService = null;
    private boolean isBind = false;
    private ImageView fish1;
    private ImageView fish2;
    private ImageView fish3;
    private ImageView fish4;
    private ImageView fish5;
    private ImageView fish6;
    private ImageView fish7;
    private ImageView fish8;
    private ImageView fish9;
    private ImageView fish10;
    private int fishState1;
    private int fishState2;
    private int fishState3;
    private int fishState4;
    private int fishState5;
    private int fishState6;
    private int fishState7;
    private int fishState8;
    private int fishState9;
    private int fishState10;
    private SQLiteDatabase dbFish;
    private int resetFlag = 0;
    private int testFlag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_archive);

        //背景音乐设置
        if(MainActivity.bgmFlag == false){
            Intent intent = new Intent(ArchiveActivity.this,MusicServer.class);
            bindService(intent,conn, Context.BIND_AUTO_CREATE);
        }

        archiveInit();

    }

    protected void onRestart(){
        if(MainActivity.bgmFlag == false){
            Intent intent = new Intent(ArchiveActivity.this,MusicServer.class);
            bindService(intent,conn, Context.BIND_AUTO_CREATE);
        }
        super.onRestart();

        archiveInit();
    }

    protected void onStop(){
        if(isBind && MainActivity.bgmFlag == false){
            unbindService(conn);
        }
        super.onStop();

    }

    public void archiveInit(){
        fish1 = (ImageView)findViewById(R.id.fish1);
        fish2 = (ImageView)findViewById(R.id.fish2);
        fish3 = (ImageView)findViewById(R.id.fish3);
        fish4 = (ImageView)findViewById(R.id.fish4);
        fish5 = (ImageView)findViewById(R.id.fish5);
        fish6 = (ImageView)findViewById(R.id.fish6);
        fish7 = (ImageView)findViewById(R.id.fish7);
        fish8 = (ImageView)findViewById(R.id.fish8);
        fish9 = (ImageView)findViewById(R.id.fish9);
        fish10 = (ImageView)findViewById(R.id.fish10);

        dbFish = SQLiteDatabase.openDatabase("data/data/com.example.letsgofishing/dbFish.db",null,SQLiteDatabase.CREATE_IF_NECESSARY);
        Cursor cursor1 = dbFish.rawQuery("select * from fish where id = 1",null);
        Cursor cursor2 = dbFish.rawQuery("select * from fish where id = 2",null);
        Cursor cursor3 = dbFish.rawQuery("select * from fish where id = 3",null);
        Cursor cursor4 = dbFish.rawQuery("select * from fish where id = 4",null);
        Cursor cursor5 = dbFish.rawQuery("select * from fish where id = 5",null);
        Cursor cursor6 = dbFish.rawQuery("select * from fish where id = 6",null);
        Cursor cursor7 = dbFish.rawQuery("select * from fish where id = 7",null);
        Cursor cursor8 = dbFish.rawQuery("select * from fish where id = 8",null);
        Cursor cursor9 = dbFish.rawQuery("select * from fish where id = 9",null);
        Cursor cursor10 = dbFish.rawQuery("select * from fish where id = 10",null);

        if(cursor1.moveToFirst()) fishState1 = cursor1.getInt(cursor1.getColumnIndex("state"));
        if(cursor2.moveToFirst()) fishState2 = cursor2.getInt(cursor2.getColumnIndex("state"));
        if(cursor3.moveToFirst()) fishState3 = cursor3.getInt(cursor3.getColumnIndex("state"));
        if(cursor4.moveToFirst()) fishState4 = cursor4.getInt(cursor4.getColumnIndex("state"));
        if(cursor5.moveToFirst()) fishState5 = cursor5.getInt(cursor5.getColumnIndex("state"));
        if(cursor6.moveToFirst()) fishState6 = cursor6.getInt(cursor6.getColumnIndex("state"));
        if(cursor7.moveToFirst()) fishState7 = cursor7.getInt(cursor7.getColumnIndex("state"));
        if(cursor8.moveToFirst()) fishState8 = cursor8.getInt(cursor8.getColumnIndex("state"));
        if(cursor9.moveToFirst()) fishState9 = cursor9.getInt(cursor9.getColumnIndex("state"));
        if(cursor10.moveToFirst()) fishState10 = cursor10.getInt(cursor10.getColumnIndex("state"));
        dbFish.close();

        if(fishState1 == 0){
            Drawable drawable = getResources().getDrawable(R.mipmap.fish1f);
            fish1.setBackground(drawable);
        }else{
            Drawable drawable = getResources().getDrawable(R.mipmap.fish1s);
            fish1.setBackground(drawable);
        }

        if(fishState2 == 0){
            Drawable drawable = getResources().getDrawable(R.mipmap.fish2f);
            fish2.setBackground(drawable);
        }else{
            Drawable drawable = getResources().getDrawable(R.mipmap.fish2s);
            fish2.setBackground(drawable);
        }

        if(fishState3 == 0){
            Drawable drawable = getResources().getDrawable(R.mipmap.fish3f);
            fish3.setBackground(drawable);
        }else{
            Drawable drawable = getResources().getDrawable(R.mipmap.fish3s);
            fish3.setBackground(drawable);
        }

        if(fishState4 == 0){
            Drawable drawable = getResources().getDrawable(R.mipmap.fish4f);
            fish4.setBackground(drawable);
        }else{
            Drawable drawable = getResources().getDrawable(R.mipmap.fish4s);
            fish4.setBackground(drawable);
        }

        if(fishState5 == 0){
            Drawable drawable = getResources().getDrawable(R.mipmap.fish5f);
            fish5.setBackground(drawable);
        }else{
            Drawable drawable = getResources().getDrawable(R.mipmap.fish5s);
            fish5.setBackground(drawable);
        }

        if(fishState6 == 0){
            Drawable drawable = getResources().getDrawable(R.mipmap.fish6f);
            fish6.setBackground(drawable);
        }else{
            Drawable drawable = getResources().getDrawable(R.mipmap.fish6s);
            fish6.setBackground(drawable);
        }

        if(fishState7 == 0){
            Drawable drawable = getResources().getDrawable(R.mipmap.fish7f);
            fish7.setBackground(drawable);
        }else{
            Drawable drawable = getResources().getDrawable(R.mipmap.fish7s);
            fish7.setBackground(drawable);
        }

        if(fishState8 == 0){
            Drawable drawable = getResources().getDrawable(R.mipmap.fish8f);
            fish8.setBackground(drawable);
        }else{
            Drawable drawable = getResources().getDrawable(R.mipmap.fish8s);
            fish8.setBackground(drawable);
        }

        if(fishState9 == 0){
            Drawable drawable = getResources().getDrawable(R.mipmap.fish9f);
            fish9.setBackground(drawable);
        }else{
            Drawable drawable = getResources().getDrawable(R.mipmap.fish9s);
            fish9.setBackground(drawable);
        }

        if(fishState10 == 0){
            Drawable drawable = getResources().getDrawable(R.mipmap.fish10f);
            fish10.setBackground(drawable);
        }else{
            Drawable drawable = getResources().getDrawable(R.mipmap.fish10s);
            fish10.setBackground(drawable);
        }
    }

    public void fish1(View view){
        if(testFlag == 6)testFlag = 7;
        if(fishState1 == 1){
            Toast toast = Toast.makeText(ArchiveActivity.this,"钓了个寂寞",Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
        }
    }
    public void fish2(View view){
        if(testFlag == 5)testFlag = 6;
        if(fishState2 == 1){
            Toast toast = Toast.makeText(ArchiveActivity.this,"像一颗海草海草，随波飘摇",Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
        }
    }
    public void fish3(View view){
        if(testFlag == 4)testFlag = 5;
        if(fishState3 == 1){
            Toast toast = Toast.makeText(ArchiveActivity.this,"一只普通的小鱼,被钓上来不太开心的样子",Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
        }
    }
    public void fish4(View view){
        if(testFlag == 7)testFlag = 8;
        if(fishState4 == 1){
            Toast toast = Toast.makeText(ArchiveActivity.this,"好看的热带鱼，等等池塘里怎么会有热带鱼？",Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
        }
    }
    public void fish5(View view){
        if(testFlag == 0)testFlag = 1;
        if(fishState5 == 1){
            Toast toast = Toast.makeText(ArchiveActivity.this,"整个湖里最正常的鱼",Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
        }
    }
    public void fish6(View view){
        if(testFlag == 3)testFlag = 4;
        if(fishState6 == 1){
            Toast toast = Toast.makeText(ArchiveActivity.this,"北湖锦鲤，甚至敢揍北理的鹅",Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
        }
    }
    public void fish7(View view){
        if(testFlag == 8)testFlag = 9;
        if(fishState7 == 1){
            Toast toast = Toast.makeText(ArchiveActivity.this,"一只气鼓鼓的河豚···或许可以用来擦鞋",Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
        }
    }
    public void fish8(View view){
        if(testFlag == 1)testFlag = 2;
        if(fishState8 == 1){
            Toast toast = Toast.makeText(ArchiveActivity.this,"河神：请问你掉的是这个金鱼竿吗",Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
        }
    }
    public void fish9(View view){
        if(testFlag == 2)testFlag = 3;
        if(fishState9 == 1){
            Toast toast = Toast.makeText(ArchiveActivity.this,"一只···练浮潜的猫??",Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
        }
    }
    public void fish10(View view){
        if(fishState10 == 0){
            if(testFlag == 11)testFlag = 12;
            if(testFlag == 10)testFlag = 11;
            if(testFlag == 9)testFlag = 10;
        }
        if(fishState10 == 1){
            Toast toast = Toast.makeText(ArchiveActivity.this,"一只普通的龙宝宝···？？",Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
        }
    }

    //隐藏按钮
    //清除数据
    public  void resetView(View view){
        resetFlag = resetFlag + 1;
        if(resetFlag == 10){
            resetFlag = 0;
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            final View v = getLayoutInflater().inflate(R.layout.reset,null);
            builder.setView(v);
            builder.show();
        }
    }

    public void reset(View view){
        File file1 = new File("data/data/com.example.letsgofishing/dbFish.db");
        File file2 = new File("data/data/com.example.letsgofishing/dbRod.db");
        File file3 = new File("data/data/com.example.letsgofishing/dbBait.db");
        File file4 = new File("data/data/com.example.letsgofishing/dbUser.db");
        if(file1.isFile())file1.delete();
        if(file2.isFile())file2.delete();
        if(file3.isFile())file3.delete();
        if(file4.isFile())file4.delete();
        MyData myData = new MyData();
        myData.CreatFish();
        myData.CreatRod();
        myData.CreatBait();
        myData.CreatUser();

        Toast toast = Toast.makeText(ArchiveActivity.this,"数据清除成功",Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }

    public void resetState(View view){
        SQLiteDatabase dbUser = SQLiteDatabase.openDatabase("data/data/com.example.letsgofishing/dbUser.db",null,SQLiteDatabase.CREATE_IF_NECESSARY);
        dbUser.execSQL("update user set state = 0 where id = 1");
        dbUser.close();
        Toast toast = Toast.makeText(ArchiveActivity.this,"新手引导已重置",Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }

    //测试：增加金币
    public void testView(View view){
        if(testFlag == 14)testFlag = 15;
        if(testFlag == 13)testFlag = 14;
        if(testFlag == 12)testFlag = 13;
        if(resetFlag == 7 && testFlag == 15){
            testFlag = 0;
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            final View v = getLayoutInflater().inflate(R.layout.test,null);
            builder.setView(v);
            builder.show();
        }
    }

    public void test1(View view){
        int money = 0;
        SQLiteDatabase dbUser = SQLiteDatabase.openOrCreateDatabase("data/data/com.example.letsgofishing/dbUser.db",null);
        Cursor cursor = dbUser.rawQuery("select * from user where id = 1",null);
        if(cursor.moveToFirst())money = cursor.getInt(cursor.getColumnIndex("money"));
        money = money + 10;
        dbUser.execSQL("update user set money = "+money+" where id = 1");

        Toast toast = Toast.makeText(ArchiveActivity.this,"当前金币:"+money,Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }
    public void test2(View view){
        int money = 0;
        SQLiteDatabase dbUser = SQLiteDatabase.openOrCreateDatabase("data/data/com.example.letsgofishing/dbUser.db",null);
        Cursor cursor = dbUser.rawQuery("select * from user where id = 1",null);
        if(cursor.moveToFirst())money = cursor.getInt(cursor.getColumnIndex("money"));
        money = money + 100;
        dbUser.execSQL("update user set money = "+money+" where id = 1");

        Toast toast = Toast.makeText(ArchiveActivity.this,"当前金币:"+money,Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }
    public void test3(View view){
        int money = 0;
        SQLiteDatabase dbUser = SQLiteDatabase.openOrCreateDatabase("data/data/com.example.letsgofishing/dbUser.db",null);
        Cursor cursor = dbUser.rawQuery("select * from user where id = 1",null);
        if(cursor.moveToFirst())money = cursor.getInt(cursor.getColumnIndex("money"));
        money = money + 1000;
        dbUser.execSQL("update user set money = "+money+" where id = 1");

        Toast toast = Toast.makeText(ArchiveActivity.this,"当前金币:"+money,Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }
    public void test4(View view){
        int money = 0;
        SQLiteDatabase dbUser = SQLiteDatabase.openOrCreateDatabase("data/data/com.example.letsgofishing/dbUser.db",null);
        Cursor cursor = dbUser.rawQuery("select * from user where id = 1",null);
        if(cursor.moveToFirst())money = cursor.getInt(cursor.getColumnIndex("money"));
        money = money + 10000;
        dbUser.execSQL("update user set money = "+money+" where id = 1");

        Toast toast = Toast.makeText(ArchiveActivity.this,"当前金币:"+money,Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }

    //背景音乐服务连接
    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            isBind = true;
            MusicServer.MusicBinder myBinder = (MusicServer.MusicBinder) service;
            myService = myBinder.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBind = false;
        }
    };
}
