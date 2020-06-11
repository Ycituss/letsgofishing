package com.example.letsgofishing;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

public class MusicStart extends Service {


    private MediaPlayer mediaPlayer = null;

    @Override
    public void onStart(Intent intent,int startld)
    {
        super.onStart(intent,startld);

        if(mediaPlayer == null)
        {
            mediaPlayer = MediaPlayer.create(this,R.raw.start);
            mediaPlayer.setLooping(false);
            mediaPlayer.start();

        }
    }

    public void onDestroy()
    {
        super.onDestroy();
        mediaPlayer.stop();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
