package com.example.letsgofishing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.SystemClock;
import android.os.Vibrator;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;
import java.util.Random;

public class GameActivity extends AppCompatActivity {


    private MusicServer myService = null;
    private boolean isBind = false;
    private Button archive;
    private Button rodSet;
    private Button baitSet;
    private Button startGame;
    private ImageView rodView;
    private ImageView baitView;
    private VideoView videoView;
    private Vibrator vibrator;
    private SensorManager sensorManager;
    private Sensor accelerometerSensor;
    private SensorEventListener rvListener;
    private int courseState = 0,state = 0;
    private int fish,time,userState;
    Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_game);

        //按钮激活
        setClickTrue();

        //背景视频播放
        videoView = (VideoView)findViewById(R.id.videoView);
        final String videoPath = Uri.parse("android.resource://" + getPackageName() + "/"+R.raw.gamebg).toString();
        videoView.setVideoPath(videoPath);
        videoView.start();
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
                mp.setLooping(true);
            }});
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                videoView.setVideoPath(videoPath);
                videoView.start();
            }
        });

        //钓鱼算法所用随机数
        random.setSeed(SystemClock.currentThreadTimeMillis());

        //刷新右上角的金币值
        int money = 0;
        SQLiteDatabase dbUser = SQLiteDatabase.openDatabase("data/data/com.example.letsgofishing/dbUser.db",null,SQLiteDatabase.CREATE_IF_NECESSARY);
        Cursor cursor = dbUser.rawQuery("select * from user where id = 1",null);
        if(cursor.moveToFirst())money = cursor.getInt(cursor.getColumnIndex("money"));
        TextView textView = (TextView)findViewById(R.id.money);
        textView.setText("金币："+money+" ");

        //刷新新手引导状态
        if(cursor.moveToFirst())userState = cursor.getInt(cursor.getColumnIndex("state"));
        dbUser.execSQL("update user set state = 1 where id = 1");
        dbUser.close();
        if(userState == 0)firstStart();

        //初始化鱼竿
        SQLiteDatabase dbRod = SQLiteDatabase.openDatabase("data/data/com.example.letsgofishing/dbRod.db",null,SQLiteDatabase.CREATE_IF_NECESSARY);
        Cursor cursor1 = dbRod.rawQuery("select * from rod where id = 1",null);
        Cursor cursor2 = dbRod.rawQuery("select * from rod where id = 2",null);
        Cursor cursor3 = dbRod.rawQuery("select * from rod where id = 3",null);
        Cursor cursor4 = dbRod.rawQuery("select * from rod where id = 4",null);
        Cursor cursor5 = dbRod.rawQuery("select * from rod where id = 5",null);
        Drawable drawable = getResources().getDrawable(R.mipmap.rod_view1);
        if(cursor1.moveToFirst())if(cursor1.getInt(cursor1.getColumnIndex("state")) == 2)
            drawable = getResources().getDrawable(R.mipmap.rod_view1);
        if(cursor2.moveToFirst())if(cursor2.getInt(cursor2.getColumnIndex("state")) == 2)
            drawable = getResources().getDrawable(R.mipmap.rod_view2);
        if(cursor3.moveToFirst())if(cursor3.getInt(cursor3.getColumnIndex("state")) == 2)
            drawable = getResources().getDrawable(R.mipmap.rod_view3);
        if(cursor4.moveToFirst())if(cursor4.getInt(cursor4.getColumnIndex("state")) == 2)
            drawable = getResources().getDrawable(R.mipmap.rod_view4);
        if(cursor5.moveToFirst())if(cursor5.getInt(cursor5.getColumnIndex("state")) == 2)
            drawable = getResources().getDrawable(R.mipmap.rod_view5);
        rodView = (ImageView)findViewById(R.id.rodView);
        rodView.setBackground(drawable);

        //初始化鱼鱼饵
        SQLiteDatabase dbBait = SQLiteDatabase.openDatabase("data/data/com.example.letsgofishing/dbBait.db",null,SQLiteDatabase.CREATE_IF_NECESSARY);
        Cursor cursor6 = dbBait.rawQuery("select * from bait where id = 1",null);
        Cursor cursor7 = dbBait.rawQuery("select * from bait where id = 2",null);
        Cursor cursor8 = dbBait.rawQuery("select * from bait where id = 3",null);
        Cursor cursor9 = dbBait.rawQuery("select * from bait where id = 4",null);
        Cursor cursor10 = dbBait.rawQuery("select * from bait where id = 5",null);
        Drawable drawable1 = getResources().getDrawable(R.mipmap.bait1);
        if(cursor6.moveToFirst())if(cursor6.getInt(cursor6.getColumnIndex("state")) == 2)
            drawable = getResources().getDrawable(R.mipmap.bait1);
        if(cursor7.moveToFirst())if(cursor7.getInt(cursor7.getColumnIndex("state")) == 2)
            drawable = getResources().getDrawable(R.mipmap.bait2);
        if(cursor8.moveToFirst())if(cursor8.getInt(cursor8.getColumnIndex("state")) == 2)
            drawable = getResources().getDrawable(R.mipmap.bait3);
        if(cursor9.moveToFirst())if(cursor9.getInt(cursor9.getColumnIndex("state")) == 2)
            drawable = getResources().getDrawable(R.mipmap.bait4);
        if(cursor10.moveToFirst())if(cursor10.getInt(cursor10.getColumnIndex("state")) == 2)
            drawable = getResources().getDrawable(R.mipmap.bait5);
        baitView = (ImageView)findViewById(R.id.baitView);
        baitView.setBackground(drawable);

        //传感器服务调用
        startSensor();

        //震动功能调用
        vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);

        //背景音乐设置
        if(MainActivity.bgmFlag == false){
            Intent intent = new Intent(GameActivity.this,MusicServer.class);
            bindService(intent,conn, Context.BIND_AUTO_CREATE);
        }

        //按钮对应点击跳转
        archive = (Button)findViewById(R.id.archive);
        archive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //按钮锁定
                setClickFalse();

                Intent intent = new Intent();
                intent.setClass(GameActivity.this,ArchiveActivity.class);
                startActivity(intent);
            }
        });

        rodSet = (Button)findViewById(R.id.rodSet);
        rodSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //按钮锁定
                setClickFalse();

                Intent intent = new Intent();
                intent.setClass(GameActivity.this,RodActivity.class);
                startActivity(intent);
            }
        });

        baitSet = (Button)findViewById(R.id.baitSet);
        baitSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //按钮锁定
                setClickFalse();

                Intent intent = new Intent();
                intent.setClass(GameActivity.this,BaitActivity.class);
                startActivity(intent);
            }
        });
    }

    //开始游戏按钮
    public void startGame(View view){
        startGame = (Button)findViewById(R.id.startGame);
        startGame.setText("请甩竿");
        courseState = 1;
    }

    protected void onRestart(){
        //按钮激活
        setClickTrue();

        if(MainActivity.bgmFlag == false){
            Intent intent = new Intent(GameActivity.this,MusicServer.class);
            bindService(intent,conn, Context.BIND_AUTO_CREATE);
        }

        //金币状态刷新
        int money = 0;
        SQLiteDatabase dbUser = SQLiteDatabase.openDatabase("data/data/com.example.letsgofishing/dbUser.db",null,SQLiteDatabase.CREATE_IF_NECESSARY);
        Cursor cursor = dbUser.rawQuery("select * from user where id = 1",null);
        if(cursor.moveToFirst())money = cursor.getInt(cursor.getColumnIndex("money"));
        TextView textView = (TextView)findViewById(R.id.money);
        textView.setText("金币："+money+" ");

        //刷新新手引导状态
        if(cursor.moveToFirst())userState = cursor.getInt(cursor.getColumnIndex("state"));
        dbUser.execSQL("update user set state = 1 where id = 1");
        dbUser.close();
        if(userState == 0)firstStart();

        //初始化鱼竿
        SQLiteDatabase dbRod = SQLiteDatabase.openDatabase("data/data/com.example.letsgofishing/dbRod.db",null,SQLiteDatabase.CREATE_IF_NECESSARY);
        Cursor cursor1 = dbRod.rawQuery("select * from rod where id = 1",null);
        Cursor cursor2 = dbRod.rawQuery("select * from rod where id = 2",null);
        Cursor cursor3 = dbRod.rawQuery("select * from rod where id = 3",null);
        Cursor cursor4 = dbRod.rawQuery("select * from rod where id = 4",null);
        Cursor cursor5 = dbRod.rawQuery("select * from rod where id = 5",null);
        Drawable drawable = getResources().getDrawable(R.mipmap.rod_view1);
        if(cursor1.moveToFirst())if(cursor1.getInt(cursor1.getColumnIndex("state")) == 2)
            drawable = getResources().getDrawable(R.mipmap.rod_view1);
        if(cursor2.moveToFirst())if(cursor2.getInt(cursor2.getColumnIndex("state")) == 2)
            drawable = getResources().getDrawable(R.mipmap.rod_view2);
        if(cursor3.moveToFirst())if(cursor3.getInt(cursor3.getColumnIndex("state")) == 2)
            drawable = getResources().getDrawable(R.mipmap.rod_view3);
        if(cursor4.moveToFirst())if(cursor4.getInt(cursor4.getColumnIndex("state")) == 2)
            drawable = getResources().getDrawable(R.mipmap.rod_view4);
        if(cursor5.moveToFirst())if(cursor5.getInt(cursor5.getColumnIndex("state")) == 2)
            drawable = getResources().getDrawable(R.mipmap.rod_view5);
        rodView = (ImageView)findViewById(R.id.rodView);
        rodView.setBackground(drawable);

        //初始化鱼鱼饵
        SQLiteDatabase dbBait = SQLiteDatabase.openDatabase("data/data/com.example.letsgofishing/dbBait.db",null,SQLiteDatabase.CREATE_IF_NECESSARY);
        Cursor cursor6 = dbBait.rawQuery("select * from bait where id = 1",null);
        Cursor cursor7 = dbBait.rawQuery("select * from bait where id = 2",null);
        Cursor cursor8 = dbBait.rawQuery("select * from bait where id = 3",null);
        Cursor cursor9 = dbBait.rawQuery("select * from bait where id = 4",null);
        Cursor cursor10 = dbBait.rawQuery("select * from bait where id = 5",null);
        Drawable drawable1 = getResources().getDrawable(R.mipmap.bait1);
        if(cursor6.moveToFirst())if(cursor6.getInt(cursor6.getColumnIndex("state")) == 2)
            drawable = getResources().getDrawable(R.mipmap.bait1);
        if(cursor7.moveToFirst())if(cursor7.getInt(cursor7.getColumnIndex("state")) == 2)
            drawable = getResources().getDrawable(R.mipmap.bait2);
        if(cursor8.moveToFirst())if(cursor8.getInt(cursor8.getColumnIndex("state")) == 2)
            drawable = getResources().getDrawable(R.mipmap.bait3);
        if(cursor9.moveToFirst())if(cursor9.getInt(cursor9.getColumnIndex("state")) == 2)
            drawable = getResources().getDrawable(R.mipmap.bait4);
        if(cursor10.moveToFirst())if(cursor10.getInt(cursor10.getColumnIndex("state")) == 2)
            drawable = getResources().getDrawable(R.mipmap.bait5);
        baitView = (ImageView)findViewById(R.id.baitView);
        baitView.setBackground(drawable);


        //中途退出游戏处理
        courseState = 0;
        state = 0;
        startGame = (Button)findViewById(R.id.startGame);
        startGame.setText("开始钓鱼");

        //传感器
       startSensor();

        super.onRestart();
    }

    protected void onStop(){
        if(isBind && MainActivity.bgmFlag == false){
            unbindService(conn);
        }

        courseState = 0;
        state = 0;

        sensorManager.unregisterListener(rvListener);

        super.onStop();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        if(MainActivity.mode == 2){
            //动作判断
            if(event.getAction() == MotionEvent.ACTION_UP) {
                //甩竿
//                        Toast.makeText(GameActivity.this,"甩竿",Toast.LENGTH_SHORT).show();
                if(courseState == 1){
                    courseState = 2;
                    courseStart();
                }
            } else if(event.getAction() == MotionEvent.ACTION_DOWN) {
                //收竿
//                        Toast.makeText(GameActivity.this,"收竿",Toast.LENGTH_SHORT).show();
                if(courseState == 4){
                    courseState = 0;
                    startGame = (Button)findViewById(R.id.startGame);
                    startGame.setText("开始钓鱼");
                    cursorEnd();
                }
            }
        }
        return true;
    }

    public void startSensor(){
        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);

        //设置监听
        rvListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                float[] rotationMatrix = new float[16];
                SensorManager.getRotationMatrixFromVector(rotationMatrix,sensorEvent.values);

                float[] remappedRotationMatrix = new float[16];
                if(MainActivity.mode == 2)SensorManager.remapCoordinateSystem(rotationMatrix,SensorManager.AXIS_Y,SensorManager.AXIS_Z,remappedRotationMatrix);
                else SensorManager.remapCoordinateSystem(rotationMatrix,SensorManager.AXIS_X,SensorManager.AXIS_Z,remappedRotationMatrix);


                float[] orientations = new float[3];
                SensorManager.getOrientation(remappedRotationMatrix,orientations);
                for(int i = 0; i < 3; i++){
                    orientations[i] =(float)(Math.toDegrees(orientations[i]));

                    if(MainActivity.mode == 1)startMode1(orientations);
//                    if(MainActivity.mode == 2)startMode2(orientations);
                    if(MainActivity.mode == 3)startMode3(orientations);

                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
            }
        };
        sensorManager.registerListener(rvListener, accelerometerSensor,SensorManager.SENSOR_DELAY_NORMAL);
    }

    //新手引导
    public void firstStart(){
        Toast toast = Toast.makeText(GameActivity.this,"新手引导",Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }

    public void startMode1(float[] orientations){

        //动作判断
        if(orientations[2] < -40&& orientations[2] > -120) {
            //甩竿
//                        Toast.makeText(GameActivity.this,"甩竿",Toast.LENGTH_SHORT).show();
            if(courseState == 1){
                courseState = 2;
                courseStart();
            }
        } else if(orientations[2] > 0&& orientations[2] < 80) {
            //收竿
//                        Toast.makeText(GameActivity.this,"收竿",Toast.LENGTH_SHORT).show();
            if(courseState == 4){
                courseState = 0;
                startGame = (Button)findViewById(R.id.startGame);
                startGame.setText("开始钓鱼");
                cursorEnd();
            }
        }
    }

    public void startMode2(float[] orientations){

//        Toast.makeText(GameActivity.this,""+orientations[2],Toast.LENGTH_SHORT).show();

        //动作判断
        if((orientations[2] < -160&& orientations[2] > -180)||(orientations[2] < 180&& orientations[2] > 160)) {
            //甩竿
//                        Toast.makeText(GameActivity.this,"甩竿",Toast.LENGTH_SHORT).show();
            if(courseState == 1){
                courseState = 2;
                courseStart();
            }
        } else if(orientations[2] > 65&& orientations[2] < 105) {
            //收竿
//                        Toast.makeText(GameActivity.this,"收竿",Toast.LENGTH_SHORT).show();
            if(courseState == 4){
                courseState = 0;
                startGame = (Button)findViewById(R.id.startGame);
                startGame.setText("开始钓鱼");
                cursorEnd();
            }
        }
    }

    public void startMode3(float[] orientations){

        //动作判断
        if(orientations[2] > 40&& orientations[2] < 120) {
            //甩竿
//                        Toast.makeText(GameActivity.this,"甩竿",Toast.LENGTH_SHORT).show();
            if(courseState == 1){
                courseState = 2;
                courseStart();
            }
        } else if(orientations[2] < 0&& orientations[2] > -80) {
            //收竿
//                        Toast.makeText(GameActivity.this,"收竿",Toast.LENGTH_SHORT).show();
            if(courseState == 4){
                courseState = 0;
                startGame = (Button)findViewById(R.id.startGame);
                startGame.setText("开始钓鱼");
                cursorEnd();
            }
        }
    }

    public void  courseStart(){

        state = 1;

        //成功甩竿，震动反馈
        long [] pattern = {100,200};
        vibrator.vibrate(pattern,-1);

        //按钮显示改变
        startGame = (Button)findViewById(R.id.startGame);
        startGame.setText("正在钓鱼");

        //甩竿音效
        if(MainActivity.bgmFlag2 == false){
            final Intent intent = new Intent(GameActivity.this,MusicStart.class);
            startService(intent);
            new Thread(){
                @Override
                public void run(){
                    super.run();
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    stopService(intent);
                }
            }.start();
        }

        int rod = 1,bait = 1, rodWeight = 1,baitWeight = 1;
        SQLiteDatabase dbUser,dbRod,dbBait;

        //从数据库读取当前鱼竿鱼饵状态
        dbUser = SQLiteDatabase.openDatabase("data/data/com.example.letsgofishing/dbUser.db",null,SQLiteDatabase.CREATE_IF_NECESSARY);
        dbRod = SQLiteDatabase.openDatabase("data/data/com.example.letsgofishing/dbRod.db",null,SQLiteDatabase.CREATE_IF_NECESSARY);
        dbBait = SQLiteDatabase.openDatabase("data/data/com.example.letsgofishing/dbBait.db",null,SQLiteDatabase.CREATE_IF_NECESSARY);

        Cursor cursorUser = dbUser.rawQuery("select * from user where id = 1",null);

        if(cursorUser.moveToFirst()) rod = cursorUser.getInt(cursorUser.getColumnIndex("rod"));
        if(cursorUser.moveToFirst()) bait = cursorUser.getInt(cursorUser.getColumnIndex("bait"));

        Cursor cursorRod = dbRod.rawQuery("select * from rod where id = "+rod,null);
        Cursor cursorBait = dbBait.rawQuery("select * from bait where id = "+bait,null);

        if(cursorRod.moveToFirst()) rodWeight = cursorRod.getInt(cursorRod.getColumnIndex("weight"));
        if(cursorBait.moveToFirst()) baitWeight = cursorBait.getInt(cursorBait.getColumnIndex("weight"));
        dbUser.close();
        dbRod.close();
        dbBait.close();

        //确定钓上来的鱼
        int num = random.nextInt(20)+1;
        int nums = random.nextInt(4);
        if(bait == 1){
            if(num >= 1 && num <= 5)fish = 1;
            if(num >= 6 && num <= 10)fish = 2;
            if(num >= 11 && num <= 14)fish = 3;
            if(num >= 15 && num <= 17)fish = 4;
            if(num >= 18 && num <= 19)fish = 5;
            if(num == 20)fish = 6;
        }else if(bait == 2){
            if(num >= 1 && num <= 5)fish = 1;
            if(num >= 6 && num <= 9)fish = 2;
            if(num >= 10 && num <= 13)fish = 3;
            if(num >= 14 && num <= 16)fish = 4;
            if(num >= 17 && num <= 18)fish = 5;
            if(num == 19)fish = 6;
            if(num == 20)fish = 7;
        }else if(bait == 3){
            if(num >= 1 && num <= 5)fish = 1;
            if(num >= 6 && num <= 9)fish = 2;
            if(num >= 10 && num <= 12)fish = 3;
            if(num >= 13 && num <= 15)fish = 4;
            if(num >= 16 && num <= 17)fish = 5;
            if(num == 18)fish = 6;
            if(num == 19)fish = 7;
            if(num == 20)fish = 8;
        }else if(bait == 4){
            if(num >= 1 && num <= 4)fish = 1;
            if(num >= 5 && num <= 8)fish = 2;
            if(num >= 9 && num <= 11)fish = 3;
            if(num >= 12 && num <= 13)fish = 4;
            if(num >= 14 && num <= 15)fish = 5;
            if(num >= 16 && num <= 17)fish = 6;
            if(num == 18)fish = 7;
            if(num == 19)fish = 8;
            if(num == 20)fish = 9;
        }else if(bait == 5){
            if(num >= 1 && num <= 4)fish = 1;
            if(num >= 5 && num <= 7)fish = 2;
            if(num >= 8 && num <= 10)fish = 3;
            if(num >= 11 && num <= 12)fish = 4;
            if(num >= 13 && num <= 14)fish = 5;
            if(num >= 15 && num <= 16)fish = 6;
            if(num == 17)fish = 7;
            if(num == 18)fish = 8;
            if(num == 19)fish = 9;
            if(num == 20)fish = 10;
        }
        //确定鱼上钩的时间
        if(rod == 1)time = 14 + nums;
        if(rod == 2)time = 12 + nums;
        if(rod == 3)time = 10 + nums;
        if(rod == 4)time = 8 + nums;
        if(rod == 5)time = 6 + nums;

        //延时
//        Toast.makeText(GameActivity.this,""+time,Toast.LENGTH_SHORT).show();
        new Thread(){
            @Override
            public void run(){
                super.run();
                try {
                    Thread.sleep(time * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                courseState = 3;
                if(state == 1) judge();
            }
        }.start();
    }

    public void judge(){
        //手机振动
        long [] pattern = {100,400,100,400};
        vibrator.vibrate(pattern,-1);
        courseState = 4;

        Message message = new Message();
        handlerButton.sendMessage(message);

        //延时2.5秒
        new Thread(){
            @Override
            public void run(){
                super.run();
                try {
                    Thread.sleep(2500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //钓鱼失败
                if(courseState == 4){
                    courseState = 0;
                    Message message = new Message();
                    handler.sendMessage(message);

                }
            }
        }.start();
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            startGame = (Button)findViewById(R.id.startGame);
            startGame.setText("开始钓鱼");
            AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
            final View v = getLayoutInflater().inflate(R.layout.fail_fishing,null);
            builder.setView(v);
            builder.show();
        }
    };

    @SuppressLint("HandlerLeak")
    Handler handlerButton = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            startGame = (Button)findViewById(R.id.startGame);
            startGame.setText("请提竿");
        }
    };


    public void cursorEnd(){

        //收竿音效
        if(MainActivity.bgmFlag2 == false){
            final Intent intent = new Intent(GameActivity.this,MusicEnd.class);
            startService(intent);
            new Thread(){
                @Override
                public void run(){
                    super.run();
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    stopService(intent);
                }
            }.start();

        }
        //从数据库获取鱼的属性
        int fishValue = 1,money = 0;
        SQLiteDatabase dbFish = SQLiteDatabase.openDatabase("data/data/com.example.letsgofishing/dbFish.db",null,SQLiteDatabase.CREATE_IF_NECESSARY);
        SQLiteDatabase dbUser = SQLiteDatabase.openDatabase("data/data/com.example.letsgofishing/dbUser.db",null,SQLiteDatabase.CREATE_IF_NECESSARY);
        Cursor cursorFish = dbFish.rawQuery("select * from fish where id = "+fish,null);
        Cursor cursorUser = dbUser.rawQuery("select * from user where id = 1",null);
        if(cursorFish.moveToFirst())fishValue = cursorFish.getInt(cursorFish.getColumnIndex("value"));
        if(cursorUser.moveToFirst())money = cursorUser.getInt(cursorUser.getColumnIndex("money"));
        money = money + fishValue;
        //更新数据库中的数据
        dbUser.execSQL("update user set money = "+ money + " where id = 1");
        dbFish.execSQL("update fish set state = 1 where id = "+ fish);
        dbUser.close();
        dbFish.close();
        //刷新金币
        TextView moneyText = (TextView)findViewById(R.id.money);
        moneyText.setText("金币："+money+" ");

        //成功钓鱼，弹窗说明
        View v = getLayoutInflater().inflate(R.layout.succeed_fishing1,null);
        if(fish == 1)v = getLayoutInflater().inflate(R.layout.succeed_fishing1,null);
        else if(fish == 2)v = getLayoutInflater().inflate(R.layout.succeed_fishing2,null);
        else if(fish == 3)v = getLayoutInflater().inflate(R.layout.succeed_fishing3,null);
        else if(fish == 4)v = getLayoutInflater().inflate(R.layout.succeed_fishing4,null);
        else if(fish == 5)v = getLayoutInflater().inflate(R.layout.succeed_fishing5,null);
        else if(fish == 6)v = getLayoutInflater().inflate(R.layout.succeed_fishing6,null);
        else if(fish == 7)v = getLayoutInflater().inflate(R.layout.succeed_fishing7,null);
        else if(fish == 8)v = getLayoutInflater().inflate(R.layout.succeed_fishing8,null);
        else if(fish == 9)v = getLayoutInflater().inflate(R.layout.succeed_fishing9,null);
        else if(fish == 10)v = getLayoutInflater().inflate(R.layout.succeed_fishing10,null);

        AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
        builder.setView(v);
        builder.show();

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

    //按钮激活
    public void setClickTrue(){
        Button archive = (Button)findViewById(R.id.archive);
        Button rodSet = (Button)findViewById(R.id.rodSet);
        Button baitSet = (Button)findViewById(R.id.baitSet);
        archive.setClickable(true);
        rodSet.setClickable(true);
        baitSet.setClickable(true);
    }
    //按钮锁定
    public void setClickFalse(){
        Button archive = (Button)findViewById(R.id.archive);
        Button rodSet = (Button)findViewById(R.id.rodSet);
        Button baitSet = (Button)findViewById(R.id.baitSet);
        archive.setClickable(false);
        rodSet.setClickable(false);
        baitSet.setClickable(false);
    }

    //测试用代码
    public void clear(View view){
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
        TextView moneyText = (TextView)findViewById(R.id.money);
        moneyText.setText("金币：0"+" ");
    }

    public void test(View view){
        int money = 0;
        SQLiteDatabase dbUser = SQLiteDatabase.openOrCreateDatabase("data/data/com.example.letsgofishing/dbUser.db",null);
        Cursor cursor = dbUser.rawQuery("select * from user where id = 1",null);
        if(cursor.moveToFirst())money = cursor.getInt(cursor.getColumnIndex("money"));
        money = money + 100;
        dbUser.execSQL("update user set money = "+money+" where id = 1");

        TextView moneyText = (TextView)findViewById(R.id.money);
        moneyText.setText("金币："+money+" ");
    }
}
