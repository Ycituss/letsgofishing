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

public class BaitActivity extends AppCompatActivity {

    private MusicServer myService = null;
    private boolean isBind = false;
    private ImageView bait1;
    private ImageView bait2;
    private ImageView bait3;
    private ImageView bait4;
    private ImageView bait5;
    private Button baitSet1;
    private Button baitSet2;
    private Button baitSet3;
    private Button baitSet4;
    private Button baitSet5;
    private int baitState1;
    private int baitState2;
    private int baitState3;
    private int baitState4;
    private int baitState5;
    private int baitPrice1 = 600;
    private int baitPrice2 = 600;
    private int baitPrice3 = 1200;
    private int baitPrice4 = 3000;
    private int baitPrice5 = 6000;
    private int userMoney;
    SQLiteDatabase dbBait,dbUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bait);

        //初始化
        rodInit();

        //背景音乐设置
        if(MainActivity.bgmFlag == false){
            Intent intent = new Intent(BaitActivity.this,MusicServer.class);
            bindService(intent,conn, Context.BIND_AUTO_CREATE);
        }
    }

    //按钮对应点击事件
    public void baitSet1(View view){
        if(baitState1 == 0){
            if(baitPrice1 > userMoney){
                Toast toast = Toast.makeText(BaitActivity.this,"金币不足",Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER,0,0);
                toast.show();
            }else{
                userMoney = userMoney - baitPrice1;
                TextView textView = (TextView)findViewById(R.id.moneyBait);
                textView.setText("金币："+userMoney+" ");
                dbBait.execSQL("update bait set state = 1 where id = 1");
                dbUser.execSQL("update user set money ="+userMoney+" where id = 1");
                Drawable drawable = getResources().getDrawable(R.mipmap.bait1);
                bait1.setBackground(drawable);
                baitSet1.setText("选择");
                baitState1 = 1;
            }
        }else if(baitState1 == 1){
            dbBait.execSQL("update bait set state = 2 where id = 1");
            Drawable drawable = getResources().getDrawable(R.mipmap.bait1);
            bait1.setBackground(drawable);
            baitSet1.setText("已选择");
            baitState1 = 2;
            refresh(1);
        }
    }

    public void baitSet2(View view){
        if(baitState2 == 0){
            if(baitPrice2 > userMoney){
                Toast toast = Toast.makeText(BaitActivity.this,"金币不足",Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER,0,0);
                toast.show();
            }else{
                userMoney = userMoney - baitPrice2;
                TextView textView = (TextView)findViewById(R.id.moneyBait);
                textView.setText("金币："+userMoney+" ");
                dbBait.execSQL("update bait set state = 1 where id = 2");
                dbUser.execSQL("update user set money ="+userMoney+" where id = 1");
                Drawable drawable = getResources().getDrawable(R.mipmap.bait2);
                bait2.setBackground(drawable);
                baitSet2.setText("选择");
                baitState2 = 1;
            }
        }else if(baitState2 == 1){
            dbBait.execSQL("update bait set state = 2 where id = 2");
            Drawable drawable = getResources().getDrawable(R.mipmap.bait2);
            bait2.setBackground(drawable);
            baitSet2.setText("已选择");
            baitState2 = 2;
            refresh(2);
        }
    }

    public void baitSet3(View view){
        if(baitState3 == 0){
            if(baitPrice3 > userMoney){
                Toast toast = Toast.makeText(BaitActivity.this,"金币不足",Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER,0,0);
                toast.show();
            }else{
                userMoney = userMoney - baitPrice3;
                TextView textView = (TextView)findViewById(R.id.moneyBait);
                textView.setText("金币："+userMoney+" ");
                dbBait.execSQL("update bait set state = 1 where id = 3");
                dbUser.execSQL("update user set money ="+userMoney+" where id = 1");
                Drawable drawable = getResources().getDrawable(R.mipmap.bait3);
                bait3.setBackground(drawable);
                baitSet3.setText("选择");
                baitState3 = 1;
            }
        }else if(baitState3 == 1){
            dbBait.execSQL("update bait set state = 2 where id = 3");
            Drawable drawable = getResources().getDrawable(R.mipmap.bait3);
            bait3.setBackground(drawable);
            baitSet3.setText("已选择");
            baitState3 = 2;
            refresh(3);
        }
    }

    public void baitSet4(View view){
        if(baitState4 == 0){
            if(baitPrice4 > userMoney){
                Toast toast = Toast.makeText(BaitActivity.this,"金币不足",Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER,0,0);
                toast.show();
            }else{
                userMoney = userMoney - baitPrice4;
                TextView textView = (TextView)findViewById(R.id.moneyBait);
                textView.setText("金币："+userMoney+" ");
                dbBait.execSQL("update bait set state = 1 where id = 4");
                dbUser.execSQL("update user set money ="+userMoney+" where id = 1");
                Drawable drawable = getResources().getDrawable(R.mipmap.bait4);
                bait4.setBackground(drawable);
                baitSet4.setText("选择");
                baitState4 = 1;
            }
        }else if(baitState4 == 1){
            dbBait.execSQL("update bait set state = 2 where id = 4");
            Drawable drawable = getResources().getDrawable(R.mipmap.bait4);
            bait4.setBackground(drawable);
            baitSet4.setText("已选择");
            baitState4 = 2;
            refresh(4);
        }
    }

    public void baitSet5(View view){
        if(baitState5 == 0){
            if(baitPrice5 > userMoney){
                Toast toast = Toast.makeText(BaitActivity.this,"金币不足",Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER,0,0);
                toast.show();
            }else{
                userMoney = userMoney - baitPrice5;
                TextView textView = (TextView)findViewById(R.id.moneyBait);
                textView.setText("金币："+userMoney+" ");
                dbBait.execSQL("update bait set state = 1 where id = 5");
                dbUser.execSQL("update user set money ="+userMoney+" where id = 1");
                Drawable drawable = getResources().getDrawable(R.mipmap.bait5);
                bait5.setBackground(drawable);
                baitSet5.setText("选择");
                baitState5 = 1;
            }
        }else if(baitState5 == 1){
            dbBait.execSQL("update bait set state = 2 where id = 5");
            Drawable drawable = getResources().getDrawable(R.mipmap.bait5);
            bait5.setBackground(drawable);
            baitSet5.setText("已选择");
            baitState5 = 2;
            refresh(5);
        }
    }

    //状态刷新函数
    public void refresh(int i){
        if(i == 1){
            Drawable drawable = getResources().getDrawable(R.mipmap.bait1);
            bait1.setBackground(drawable);
            baitSet1.setText("已选择");
            baitState1 = 2;
            dbBait.execSQL("update bait set state = 2 where id = 1");
        }else if(baitState1 != 0){
            Drawable drawable = getResources().getDrawable(R.mipmap.bait1);
            bait1.setBackground(drawable);
            baitSet1.setText("选择");
            baitState1 = 1;
            dbBait.execSQL("update bait set state = 1 where id = 1");
        }
        if(i == 2){
            Drawable drawable = getResources().getDrawable(R.mipmap.bait2);
            bait2.setBackground(drawable);
            baitSet2.setText("已选择");
            baitState2 = 2;
            dbBait.execSQL("update bait set state = 2 where id = 2");
        }else if(baitState2 != 0){
            Drawable drawable = getResources().getDrawable(R.mipmap.bait2);
            bait2.setBackground(drawable);
            baitSet2.setText("选择");
            baitState2 = 1;
            dbBait.execSQL("update bait set state = 1 where id = 2");
        }
        if(i == 3){
            Drawable drawable = getResources().getDrawable(R.mipmap.bait3);
            bait3.setBackground(drawable);
            baitSet3.setText("已选择");
            baitState3 = 2;
            dbBait.execSQL("update bait set state = 2 where id = 3");
        }else if(baitState3 != 0){
            Drawable drawable = getResources().getDrawable(R.mipmap.bait3);
            bait3.setBackground(drawable);
            baitSet3.setText("选择");
            baitState3 = 1;
            dbBait.execSQL("update bait set state = 1 where id = 3");
        }
        if(i == 4){
            Drawable drawable = getResources().getDrawable(R.mipmap.bait4);
            bait4.setBackground(drawable);
            baitSet4.setText("已选择");
            baitState4 = 2;
            dbBait.execSQL("update bait set state = 2 where id = 4");
        }else if(baitState4 != 0){
            Drawable drawable = getResources().getDrawable(R.mipmap.bait4);
            bait4.setBackground(drawable);
            baitSet4.setText("选择");
            baitState4 = 1;
            dbBait.execSQL("update bait set state = 1 where id = 4");
        }
        if(i == 5){
            Drawable drawable = getResources().getDrawable(R.mipmap.bait5);
            bait5.setBackground(drawable);
            baitSet5.setText("已选择");
            baitState5 = 2;
            dbBait.execSQL("update bait set state = 2 where id = 5");
        }else if(baitState5 != 0){
            Drawable drawable = getResources().getDrawable(R.mipmap.bait5);
            bait5.setBackground(drawable);
            baitSet5.setText("选择");
            baitState5 = 1;
            dbBait.execSQL("update bait set state = 1 where id = 5");
        }
    }


    protected void onRestart(){
        if(MainActivity.bgmFlag == false){
            Intent intent = new Intent(BaitActivity.this,MusicServer.class);
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

        dbBait.close();
        dbUser.close();

    }

    //初始化函数
    public void rodInit(){

        bait1 = (ImageView)findViewById(R.id.bait1);
        bait2 = (ImageView)findViewById(R.id.bait2);
        bait3 = (ImageView)findViewById(R.id.bait3);
        bait4 = (ImageView)findViewById(R.id.bait4);
        bait5 = (ImageView)findViewById(R.id.bait5);
        baitSet1 = (Button) findViewById(R.id.baitSet1);
        baitSet2 = (Button) findViewById(R.id.baitSet2);
        baitSet3 = (Button) findViewById(R.id.baitSet3);
        baitSet4 = (Button) findViewById(R.id.baitSet4);
        baitSet5 = (Button) findViewById(R.id.baitSet5);

        //获取当前用户金币值
        dbUser = SQLiteDatabase.openOrCreateDatabase("data/data/com.example.letsgofishing/dbUser.db",null);
        Cursor cursor = dbUser.rawQuery("select * from user where id = 1",null);
        if(cursor.moveToFirst()) userMoney = cursor.getInt(cursor.getColumnIndex("money"));
        TextView textView = (TextView)findViewById(R.id.moneyBait);
        textView.setText("金币："+userMoney+" ");

        //从数据库取鱼饵
        dbBait = SQLiteDatabase.openOrCreateDatabase("data/data/com.example.letsgofishing/dbBait.db",null);
        Cursor cursor1 = dbBait.rawQuery("select * from bait where id = 1",null);
        Cursor cursor2 = dbBait.rawQuery("select * from bait where id = 2",null);
        Cursor cursor3 = dbBait.rawQuery("select * from bait where id = 3",null);
        Cursor cursor4 = dbBait.rawQuery("select * from bait where id = 4",null);
        Cursor cursor5 = dbBait.rawQuery("select * from bait where id = 5",null);

        if(cursor1.moveToFirst()){
            baitState1 = cursor1.getInt(cursor1.getColumnIndex("state"));
//            rodPrice1 = cursor1.getInt(cursor1.getColumnIndex("price"));
        }
        if(cursor2.moveToFirst()){
            baitState2 = cursor2.getInt(cursor2.getColumnIndex("state"));
//            rodPrice2 = cursor2.getInt(cursor2.getColumnIndex("price"));
        }
        if(cursor3.moveToFirst()){
            baitState3 = cursor3.getInt(cursor3.getColumnIndex("state"));
//            rodPrice3 = cursor3.getInt(cursor3.getColumnIndex("price"));
        }
        if(cursor4.moveToFirst()){
            baitState4 = cursor4.getInt(cursor4.getColumnIndex("state"));
//            rodPrice4 = cursor4.getInt(cursor4.getColumnIndex("price"));
        }
        if(cursor5.moveToFirst()){
            baitState5 = cursor5.getInt(cursor5.getColumnIndex("state"));
//            rodPrice5 = cursor5.getInt(cursor5.getColumnIndex("price"));
        }

        //改变商店初始状态
        if(baitState1 == 0){
            Drawable drawable = getResources().getDrawable(R.mipmap.bait1);
            bait1.setBackground(drawable);
            baitSet1.setText("600金币");
        }else if(baitState1 == 1){
            Drawable drawable = getResources().getDrawable(R.mipmap.bait1);
            bait1.setBackground(drawable);
            baitSet1.setText("选择");
        }else if(baitState1 == 2){
            Drawable drawable = getResources().getDrawable(R.mipmap.bait1);
            bait1.setBackground(drawable);
            baitSet1.setText("已选择");
        }

        if(baitState2 == 0){
            Drawable drawable = getResources().getDrawable(R.mipmap.bait2f);
            bait2.setBackground(drawable);
            baitSet2.setText("600金币");
        }else if(baitState2 == 1){
            Drawable drawable = getResources().getDrawable(R.mipmap.bait2);
            bait2.setBackground(drawable);
            baitSet2.setText("选择");
        }else if(baitState2 == 2){
            Drawable drawable = getResources().getDrawable(R.mipmap.bait2);
            bait2.setBackground(drawable);
            baitSet2.setText("已选择");
        }

        if(baitState3 == 0){
            Drawable drawable = getResources().getDrawable(R.mipmap.bait3f);
            bait3.setBackground(drawable);
            baitSet3.setText("1200金币");
        }else if(baitState3 == 1){
            Drawable drawable = getResources().getDrawable(R.mipmap.bait3);
            bait3.setBackground(drawable);
            baitSet3.setText("选择");
        }else if(baitState3 == 2){
            Drawable drawable = getResources().getDrawable(R.mipmap.bait3);
            bait3.setBackground(drawable);
            baitSet3.setText("已选择");
        }

        if(baitState4 == 0){
            Drawable drawable = getResources().getDrawable(R.mipmap.bait4f);
            bait4.setBackground(drawable);
            baitSet4.setText("3000金币");
        }else if(baitState4 == 1){
            Drawable drawable = getResources().getDrawable(R.mipmap.bait4);
            bait4.setBackground(drawable);
            baitSet4.setText("选择");
        }else if(baitState4 == 2){
            Drawable drawable = getResources().getDrawable(R.mipmap.bait4);
            bait4.setBackground(drawable);
            baitSet4.setText("已选择");
        }

        if(baitState5 == 0){
            Drawable drawable = getResources().getDrawable(R.mipmap.bait5f);
            bait5.setBackground(drawable);
            baitSet5.setText("6000金币");
        }else if(baitState5 == 1){
            Drawable drawable = getResources().getDrawable(R.mipmap.bait5);
            bait5.setBackground(drawable);
            baitSet5.setText("选择");
        }else if(baitState5 == 2){
            Drawable drawable = getResources().getDrawable(R.mipmap.bait5);
            bait5.setBackground(drawable);
            baitSet5.setText("已选择");
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
