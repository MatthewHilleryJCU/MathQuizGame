package au.com.hillnet.mathquizgame;

/*
  Created by matt- on 11/05/2017. ;)
 */

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;


public class MusicPlayer extends Service {


    private MediaPlayer player;
    private int lastbkgdchecked = SettingsActivity.bkgdchecked;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        if (lastbkgdchecked == 0) {
            player = MediaPlayer.create(this, R.raw.menumusic);
            player.setLooping(true);
            player.start();
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (lastbkgdchecked == 0) {
            player.stop();
            player.release();
        }
        SettingsActivity.bkgdchecked = 0; //
    }
}
