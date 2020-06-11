package com.example.letsgofishing;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class MusicServer extends Service {

    public class MusicBinder extends Binder{
        public MusicServer getService(){
            return MusicServer.this;
        }
    }

    private MediaPlayer mediaPlayer = null;
    public final IBinder mBinder= new MusicBinder();



//    @Override
//    public void onStart(Intent intent,int startld)
//    {
//        super.onStart(intent,startld);
//
//        if(mediaPlayer == null)
//        {
//            mediaPlayer = MediaPlayer.create(this,R.raw.bgm2);
//            mediaPlayer.setLooping(true);
//            mediaPlayer.start();
//
//        }
//    }
//
//    public void onDestroy()
//    {
//        super.onDestroy();
//        mediaPlayer.stop();
//    }

    @Override
    public boolean onUnbind(Intent intent){
        mediaPlayer.stop();
        return true;
    }

    public void onRebind(Intent intent){
        mediaPlayer = MediaPlayer.create(this,R.raw.bgmfinal);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        mediaPlayer = MediaPlayer.create(this,R.raw.bgmfinal);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
        return mBinder;
    }
}

