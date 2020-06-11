package com.example.letsgofishing;

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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class RodActivity extends AppCompatActivity {

    private MusicServer myService = null;
    private boolean isBind = false;
    private ImageView rod1;
    private ImageView rod2;
    private ImageView rod3;
    private ImageView rod4;
    private ImageView rod5;
    private Button rodSet1;
    private Button rodSet2;
    private Button rodSet3;
    private Button rodSet4;
    private Button rodSet5;
    private int rodState1;
    private int rodState2;
    private int rodState3;
    private int rodState4;
    private int rodState5;
    private int rodPrice1 = 600;
    private int rodPrice2 = 600;
    private int rodPrice3 = 1200;
    private int rodPrice4 = 3000;
    private int rodPrice5 = 6000;
    private int userMoney;
    SQLiteDatabase dbRod,dbUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rod);

        //初始化
        rodInit();

        //背景音乐设置
        if(MainActivity.bgmFlag == false){
            Intent intent = new Intent(RodActivity.this,MusicServer.class);
            bindService(intent,conn, Context.BIND_AUTO_CREATE);
        }
    }

    //按钮对应点击事件
    public void rodSet1(View view){
        if(rodState1 == 0){
            if(rodPrice1 > userMoney){
                Toast toast = Toast.makeText(RodActivity.this,"金币不足",Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER,0,0);
                toast.show();
            }else{
                userMoney = userMoney - rodPrice1;
                TextView textView = (TextView)findViewById(R.id.moneyRod);
                textView.setText("金币："+userMoney+" ");
                dbRod.execSQL("update rod set state = 1 where id = 1");
                dbUser.execSQL("update user set money ="+userMoney+" where id = 1");
                Drawable drawable = getResources().getDrawable(R.mipmap.rod1);
                rod1.setBackground(drawable);
                rodSet1.setText("选择");
                rodState1 = 1;
            }
        }else if(rodState1 == 1){
            dbRod.execSQL("update rod set state = 2 where id = 1");
            Drawable drawable = getResources().getDrawable(R.mipmap.rod1);
            rod1.setBackground(drawable);
            rodSet1.setText("已选择");
            rodState1 = 2;
            refresh(1);
        }
    }

    public void rodSet2(View view){
        if(rodState2 == 0){
            if(rodPrice2 > userMoney){
                Toast toast = Toast.makeText(RodActivity.this,"金币不足",Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER,0,0);
                toast.show();
            }else{
                userMoney = userMoney - rodPrice2;
                TextView textView = (TextView)findViewById(R.id.moneyRod);
                textView.setText("金币："+userMoney+" ");
                dbRod.execSQL("update rod set state = 1 where id = 2");
                dbUser.execSQL("update user set money ="+userMoney+" where id = 1");
                Drawable drawable = getResources().getDrawable(R.mipmap.rod2);
                rod2.setBackground(drawable);
                rodSet2.setText("选择");
                rodState2 = 1;
            }
        }else if(rodState2 == 1){
            dbRod.execSQL("update rod set state = 2 where id = 2");
            Drawable drawable = getResources().getDrawable(R.mipmap.rod2);
            rod2.setBackground(drawable);
            rodSet2.setText("已选择");
            rodState2 = 2;
            refresh(2);
        }
    }

    public void rodSet3(View view){
        if(rodState3 == 0){
            if(rodPrice3 > userMoney){
                Toast toast = Toast.makeText(RodActivity.this,"金币不足",Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER,0,0);
                toast.show();
            }else{
                userMoney = userMoney - rodPrice3;
                TextView textView = (TextView)findViewById(R.id.moneyRod);
                textView.setText("金币："+userMoney+" ");
                dbRod.execSQL("update rod set state = 1 where id = 3");
                dbUser.execSQL("update user set money ="+userMoney+" where id = 1");
                Drawable drawable = getResources().getDrawable(R.mipmap.rod3);
                rod3.setBackground(drawable);
                rodSet3.setText("选择");
                rodState3 = 1;
            }
        }else if(rodState3 == 1){
            dbRod.execSQL("update rod set state = 2 where id = 3");
            Drawable drawable = getResources().getDrawable(R.mipmap.rod3);
            rod3.setBackground(drawable);
            rodSet3.setText("已选择");
            rodState3 = 2;
            refresh(3);
        }
    }

    public void rodSet4(View view){
        if(rodState4 == 0){
            if(rodPrice4 > userMoney){
                Toast toast = Toast.makeText(RodActivity.this,"金币不足",Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER,0,0);
                toast.show();
            }else{
                userMoney = userMoney - rodPrice4;
                TextView textView = (TextView)findViewById(R.id.moneyRod);
                textView.setText("金币："+userMoney+" ");
                dbRod.execSQL("update rod set state = 1 where id = 4");
                dbUser.execSQL("update user set money ="+userMoney+" where id = 1");
                Drawable drawable = getResources().getDrawable(R.mipmap.rod4);
                rod4.setBackground(drawable);
                rodSet4.setText("选择");
                rodState4 = 1;
            }
        }else if(rodState4 == 1){
            dbRod.execSQL("update rod set state = 2 where id = 4");
            Drawable drawable = getResources().getDrawable(R.mipmap.rod4);
            rod4.setBackground(drawable);
            rodSet4.setText("已选择");
            rodState4 = 2;
            refresh(4);
        }
    }

    public void rodSet5(View view){
        if(rodState5 == 0){
            if(rodPrice5 > userMoney){
                Toast toast = Toast.makeText(RodActivity.this,"金币不足",Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER,0,0);
                toast.show();
            }else{
                userMoney = userMoney - rodPrice5;
                TextView textView = (TextView)findViewById(R.id.moneyRod);
                textView.setText("金币："+userMoney+" ");
                dbRod.execSQL("update rod set state = 1 where id = 5");
                dbUser.execSQL("update user set money ="+userMoney+" where id = 1");
                Drawable drawable = getResources().getDrawable(R.mipmap.rod5);
                rod5.setBackground(drawable);
                rodSet5.setText("选择");
                rodState5 = 1;
            }
        }else if(rodState5 == 1){
            dbRod.execSQL("update rod set state = 2 where id = 5");
            Drawable drawable = getResources().getDrawable(R.mipmap.rod5);
            rod5.setBackground(drawable);
            rodSet5.setText("已选择");
            rodState5 = 2;
            refresh(5);
        }
    }

    //状态刷新函数
    public void refresh(int i){
        if(i == 1){
            Drawable drawable = getResources().getDrawable(R.mipmap.rod1);
            rod1.setBackground(drawable);
            rodSet1.setText("已选择");
            rodState1 = 2;
            dbRod.execSQL("update rod set state = 2 where id = 1");
        }else if(rodState1 != 0){
            Drawable drawable = getResources().getDrawable(R.mipmap.rod1);
            rod1.setBackground(drawable);
            rodSet1.setText("选择");
            rodState1 = 1;
            dbRod.execSQL("update rod set state = 1 where id = 1");
        }
        if(i == 2){
            Drawable drawable = getResources().getDrawable(R.mipmap.rod2);
            rod2.setBackground(drawable);
            rodSet2.setText("已选择");
            rodState2 = 2;
            dbRod.execSQL("update rod set state = 2 where id = 2");
        }else if(rodState2 != 0){
            Drawable drawable = getResources().getDrawable(R.mipmap.rod2);
            rod2.setBackground(drawable);
            rodSet2.setText("选择");
            rodState2 = 1;
            dbRod.execSQL("update rod set state = 1 where id = 2");
        }
        if(i == 3){
            Drawable drawable = getResources().getDrawable(R.mipmap.rod3);
            rod3.setBackground(drawable);
            rodSet3.setText("已选择");
            rodState3 = 2;
            dbRod.execSQL("update rod set state = 2 where id = 3");
        }else if(rodState3 != 0){
            Drawable drawable = getResources().getDrawable(R.mipmap.rod3);
            rod3.setBackground(drawable);
            rodSet3.setText("选择");
            rodState3 = 1;
            dbRod.execSQL("update rod set state = 1 where id = 3");
        }
        if(i == 4){
            Drawable drawable = getResources().getDrawable(R.mipmap.rod4);
            rod4.setBackground(drawable);
            rodSet4.setText("已选择");
            rodState4 = 2;
            dbRod.execSQL("update rod set state = 2 where id = 4");
        }else if(rodState4 != 0){
            Drawable drawable = getResources().getDrawable(R.mipmap.rod4);
            rod4.setBackground(drawable);
            rodSet4.setText("选择");
            rodState4 = 1;
            dbRod.execSQL("update rod set state = 1 where id = 4");
        }
        if(i == 5){
            Drawable drawable = getResources().getDrawable(R.mipmap.rod5);
            rod5.setBackground(drawable);
            rodSet5.setText("已选择");
            rodState5 = 2;
            dbRod.execSQL("update rod set state = 2 where id = 5");
        }else if(rodState5 != 0){
            Drawable drawable = getResources().getDrawable(R.mipmap.rod5);
            rod5.setBackground(drawable);
            rodSet5.setText("选择");
            rodState5 = 1;
            dbRod.execSQL("update rod set state = 1 where id = 5");
        }
    }

    protected void onRestart(){
        if(MainActivity.bgmFlag == false){
            Intent intent = new Intent(RodActivity.this,MusicServer.class);
            bindService(intent,conn, Context.BIND_AUTO_CREATE);
        }
        super.onRestart();

        //初始化
        rodInit();
    }

    protected void onStop(){
        if(isBind && MainActivity.bgmFlag == false){
            unbindService(conn);
        }
        super.onStop();

        dbRod.close();
        dbUser.close();

    }

    //初始化函数
    public void rodInit(){

        rod1 = (ImageView)findViewById(R.id.rod1);
        rod2 = (ImageView)findViewById(R.id.rod2);
        rod3 = (ImageView)findViewById(R.id.rod3);
        rod4 = (ImageView)findViewById(R.id.rod4);
        rod5 = (ImageView)findViewById(R.id.rod5);
        rodSet1 = (Button) findViewById(R.id.rodSet1);
        rodSet2 = (Button) findViewById(R.id.rodSet2);
        rodSet3 = (Button) findViewById(R.id.rodSet3);
        rodSet4 = (Button) findViewById(R.id.rodSet4);
        rodSet5 = (Button) findViewById(R.id.rodSet5);

        //获取当前用户金币值
        dbUser = SQLiteDatabase.openOrCreateDatabase("data/data/com.example.letsgofishing/dbUser.db",null);
        Cursor cursor = dbUser.rawQuery("select * from user where id = 1",null);
        if(cursor.moveToFirst()) userMoney = cursor.getInt(cursor.getColumnIndex("money"));
        TextView textView = (TextView)findViewById(R.id.moneyRod);
        textView.setText("金币："+userMoney+" ");

        //从数据库取鱼竿
        dbRod = SQLiteDatabase.openOrCreateDatabase("data/data/com.example.letsgofishing/dbRod.db",null);
        Cursor cursor1 = dbRod.rawQuery("select * from rod where id = 1",null);
        Cursor cursor2 = dbRod.rawQuery("select * from rod where id = 2",null);
        Cursor cursor3 = dbRod.rawQuery("select * from rod where id = 3",null);
        Cursor cursor4 = dbRod.rawQuery("select * from rod where id = 4",null);
        Cursor cursor5 = dbRod.rawQuery("select * from rod where id = 5",null);

        if(cursor1.moveToFirst()){
            rodState1 = cursor1.getInt(cursor1.getColumnIndex("state"));
//            rodPrice1 = cursor1.getInt(cursor1.getColumnIndex("price"));
        }
        if(cursor2.moveToFirst()){
            rodState2 = cursor2.getInt(cursor2.getColumnIndex("state"));
//            rodPrice2 = cursor2.getInt(cursor2.getColumnIndex("price"));
        }
        if(cursor3.moveToFirst()){
            rodState3 = cursor3.getInt(cursor3.getColumnIndex("state"));
//            rodPrice3 = cursor3.getInt(cursor3.getColumnIndex("price"));
        }
        if(cursor4.moveToFirst()){
            rodState4 = cursor4.getInt(cursor4.getColumnIndex("state"));
//            rodPrice4 = cursor4.getInt(cursor4.getColumnIndex("price"));
        }
        if(cursor5.moveToFirst()){
            rodState5 = cursor5.getInt(cursor5.getColumnIndex("state"));
//            rodPrice5 = cursor5.getInt(cursor5.getColumnIndex("price"));
        }

        //改变商店初始状态
        if(rodState1 == 0){
            Drawable drawable = getResources().getDrawable(R.mipmap.rod1);
            rod1.setBackground(drawable);
            rodSet1.setText("600金币");
        }else if(rodState1 == 1){
            Drawable drawable = getResources().getDrawable(R.mipmap.rod1);
            rod1.setBackground(drawable);
            rodSet1.setText("选择");
        }else if(rodState1 == 2){
            Drawable drawable = getResources().getDrawable(R.mipmap.rod1);
            rod1.setBackground(drawable);
            rodSet1.setText("已选择");
        }

        if(rodState2 == 0){
            Drawable drawable = getResources().getDrawable(R.mipmap.rod2f);
            rod2.setBackground(drawable);
            rodSet2.setText("600金币");
        }else if(rodState2 == 1){
            Drawable drawable = getResources().getDrawable(R.mipmap.rod2);
            rod2.setBackground(drawable);
            rodSet2.setText("选择");
        }else if(rodState2 == 2){
            Drawable drawable = getResources().getDrawable(R.mipmap.rod2);
            rod2.setBackground(drawable);
            rodSet2.setText("已选择");
        }

        if(rodState3 == 0){
            Drawable drawable = getResources().getDrawable(R.mipmap.rod3f);
            rod3.setBackground(drawable);
            rodSet3.setText("1200金币");
        }else if(rodState3 == 1){
            Drawable drawable = getResources().getDrawable(R.mipmap.rod3);
            rod3.setBackground(drawable);
            rodSet3.setText("选择");
        }else if(rodState3 == 2){
            Drawable drawable = getResources().getDrawable(R.mipmap.rod3);
            rod3.setBackground(drawable);
            rodSet3.setText("已选择");
        }

        if(rodState4 == 0){
            Drawable drawable = getResources().getDrawable(R.mipmap.rod4f);
            rod4.setBackground(drawable);
            rodSet4.setText("3000金币");
        }else if(rodState4 == 1){
            Drawable drawable = getResources().getDrawable(R.mipmap.rod4);
            rod4.setBackground(drawable);
            rodSet4.setText("选择");
        }else if(rodState4 == 2){
            Drawable drawable = getResources().getDrawable(R.mipmap.rod4);
            rod4.setBackground(drawable);
            rodSet4.setText("已选择");
        }

        if(rodState5 == 0){
            Drawable drawable = getResources().getDrawable(R.mipmap.rod5f);
            rod5.setBackground(drawable);
            rodSet5.setText("6000金币");
        }else if (rodState5 == 1){
            Drawable drawable = getResources().getDrawable(R.mipmap.rod5);
            rod5.setBackground(drawable);
            rodSet5.setText("选择");
        }else if(rodState5 == 2){
            Drawable drawable = getResources().getDrawable(R.mipmap.rod5);
            rod5.setBackground(drawable);
            rodSet5.setText("已选择");
        }
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
