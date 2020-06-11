package com.example.letsgofishing;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;

public class MainActivity extends AppCompatActivity {

    public static Boolean bgmFlag = false;
    public static Boolean bgmFlag2 = false;
    public static int mode = 1;
    private ImageView gotoGame;
    private Button music;
    private RadioButton mode1,mode2,mode3;
    private MusicServer myService = null;
    private boolean isBind = false;
    private SQLiteDatabase dbBgm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //按钮激活
        setClickTrue();

        //用数据库存储背景音乐状态及操作方式
        dbBgm = SQLiteDatabase.openOrCreateDatabase("data/data/com.example.letsgofishing/dbBgm.db",null);
        try{
            Cursor cursor1 = dbBgm.rawQuery("select * from bgmFlag where id = 1",null);
        }catch (Exception e){
            dbBgm.execSQL("create table bgmFlag(id integer primary key autoincrement,flag int,flag1 int)");
            dbBgm.execSQL("create table mode (id integer primary key autoincrement,mode int)");

            dbBgm.execSQL("insert into bgmFlag values(1,0,0)");
            dbBgm.execSQL("insert into mode values(1,1)");

        }
        Cursor cursor = dbBgm.rawQuery("select * from bgmFlag where id = 1",null);
        Cursor cursor1 = dbBgm.rawQuery("select * from mode where id = 1",null);
        int flag = 0,flag1 = 0;
        if(cursor.moveToFirst()){
            if(cursor.moveToFirst()){
                flag = cursor.getInt(cursor.getColumnIndex("flag"));
                flag1 = cursor.getInt(cursor.getColumnIndex("flag1"));
            }
            if(flag == 0)bgmFlag = false;
            else bgmFlag = true;
            if(flag1 == 0)bgmFlag2 = false;
            else bgmFlag2 = true;
        }
        if(cursor1.moveToFirst()) mode = cursor1.getInt(cursor1.getColumnIndex("mode"));

        //背景音乐服务调用
        music = (Button)findViewById(R.id.music);
//        music.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                bgmFlag = !isChecked;
//            }
//        });
        if(!bgmFlag){
            Intent intent = new Intent(MainActivity.this,MusicServer.class);
            bindService(intent,conn,Context.BIND_AUTO_CREATE);
        }

        gotoGame = (ImageView)findViewById(R.id.button1);
        gotoGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //按钮锁定
                setClickFalse();

                Intent intent = new Intent();
                intent.setClass(MainActivity.this,GameActivity.class);
                startActivity(intent);
            }
        });

        //数据库创建
        MyData myData = new MyData();
        myData.CreatFish();
        myData.CreatRod();
        myData.CreatBait();
        myData.CreatUser();
    }

    //操作方式选择
    public void mode1(View view){
        mode = 1;
        dbBgm.execSQL("update mode set mode = 1 where id = 1");
    }
    public void mode2(View view){
        mode = 2;
        dbBgm.execSQL("update mode set mode = 2 where id = 1");
    }
    public void mode3(View view){
        mode = 3;
        dbBgm.execSQL("update mode set mode = 3 where id = 1");
    }

    //音乐设置开关对应事件
    public void musicToggle(View view)
    {
        bgmSetState(bgmFlag);
        bgmFlag = !bgmFlag;

        if(bgmFlag == true)dbBgm.execSQL("update bgmFlag set flag = 1 where id = 1");
        else dbBgm.execSQL("update bgmFlag set flag = 0 where id = 1");

    }
    public void musicToggle1(View view)
    {
        bgmFlag2 = !bgmFlag2;

        if(bgmFlag2 == true)dbBgm.execSQL("update bgmFlag set flag1 = 1 where id = 1");
        else dbBgm.execSQL("update bgmFlag set flag1 = 0 where id = 1");

    }

    protected void bgmSetState(Boolean bgmFlag)
    {
        Intent intent = new Intent(MainActivity.this,MusicServer.class);
        if(bgmFlag == true){
            bindService(intent,conn,Context.BIND_AUTO_CREATE);
        }else{
            if(isBind){
                unbindService(conn);
            }
            stopService(intent);
        }
    }

    protected void onRestart(){
        //按钮激活
        setClickTrue();

        if(bgmFlag == false){
            Intent intent = new Intent(MainActivity.this,MusicServer.class);
            bindService(intent,conn, Context.BIND_AUTO_CREATE);
        }
        super.onRestart();

        //打开背景音乐存储数据库文件
        dbBgm = SQLiteDatabase.openOrCreateDatabase("data/data/com.example.letsgofishing/dbBgm.db",null);
    }

    protected void onStop(){
        if(bgmFlag == false){
            unbindService(conn);
        }
        super.onStop();

        //关闭数据库
        dbBgm.close();
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

    //设置按钮点击事件
    public void showSetting(View view)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("设置");

        View v = getLayoutInflater().inflate(R.layout.setting111,null);
        if(bgmFlag && bgmFlag2){
            if(mode == 1)v = getLayoutInflater().inflate(R.layout.setting001,null);
            if(mode == 2)v = getLayoutInflater().inflate(R.layout.setting002,null);
            if(mode == 3)v = getLayoutInflater().inflate(R.layout.setting003,null);
        }
        else if(bgmFlag && !bgmFlag2){
            if(mode == 1)v = getLayoutInflater().inflate(R.layout.setting011,null);
            if(mode == 2)v = getLayoutInflater().inflate(R.layout.setting012,null);
            if(mode == 3)v = getLayoutInflater().inflate(R.layout.setting013,null);
        }
        else if(!bgmFlag && bgmFlag2){
            if(mode == 1)v = getLayoutInflater().inflate(R.layout.setting101,null);
            if(mode == 2)v = getLayoutInflater().inflate(R.layout.setting102,null);
            if(mode == 3)v = getLayoutInflater().inflate(R.layout.setting103,null);
        }
        else if(!bgmFlag && !bgmFlag2){
            if(mode == 1)v = getLayoutInflater().inflate(R.layout.setting111,null);
            if(mode == 2)v = getLayoutInflater().inflate(R.layout.setting112,null);
            if(mode == 3)v = getLayoutInflater().inflate(R.layout.setting113,null);
        }
        builder.setView(v);
        builder.show();
    }

    //帮助按钮点击事件
    public void showHelp(View view)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final View v = getLayoutInflater().inflate(R.layout.help,null);
        builder.setView(v);
        builder.show();
    }

    //按钮激活
    public void setClickTrue(){
        ImageView go = (ImageView)findViewById(R.id.button1);
        ImageView set = (ImageView)findViewById(R.id.button2);
        ImageView help = (ImageView)findViewById(R.id.button3);
        go.setClickable(true);
        set.setClickable(true);
        help.setClickable(true);
    }

    //按钮锁定
    public void setClickFalse(){
        ImageView go = (ImageView)findViewById(R.id.button1);
        ImageView set = (ImageView)findViewById(R.id.button2);
        ImageView help = (ImageView)findViewById(R.id.button3);
        go.setClickable(false);
        set.setClickable(false);
        help.setClickable(false);
    }
}
