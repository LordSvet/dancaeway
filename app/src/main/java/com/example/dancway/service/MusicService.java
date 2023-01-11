package com.example.dancway.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.dancway.R;
import com.example.dancway.controller.MusicPlayerControllerSingleton;
import com.example.dancway.view.SongPlayerActivity;

/**
 * Music service class
 */
public class MusicService extends Service {
    public static final String CLASS_TAG = MusicService.class.getName();
    public static final String ACTION_NEXT = "NEXT";
    public static final String ACTION_PLAY = "PLAY";
    public static final String ACTION_PREV = "PREV";
    MusicPlayerControllerSingleton musicPlayerControllerSingleton;
    SongPlayerActivity songPlayerActivity;
    private IBinder mBinder = new MyBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(CLASS_TAG, "OnBind called");
        return mBinder;
    }

    /**
     * The Binder class
     */
    public class MyBinder extends Binder {
        public MusicService getService() {
            return MusicService.this;
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String actionName = intent.getStringExtra("action_name");
        if (actionName != null) {
            switch (actionName) {
                case ACTION_PLAY:
                    if (songPlayerActivity != null) {
                        songPlayerActivity.pausePlay();
                    }
                    break;
                case ACTION_NEXT:
                    if (songPlayerActivity != null) {
                        songPlayerActivity.next();
                    }
                    break;
                case ACTION_PREV:
                    if (songPlayerActivity != null) {
                        songPlayerActivity.previous();
                    }
                    break;
            }
        }
        return START_STICKY;
    }

    /**
     * Initialization of dependencies of service
     *
     * @param musicPlayerControllerSingleton musicPlayerControllerSingleton
     * @param songPlayerActivity             songPlayerActivity
     */
    public void setCallBack(MusicPlayerControllerSingleton musicPlayerControllerSingleton, SongPlayerActivity songPlayerActivity) {
        this.musicPlayerControllerSingleton = musicPlayerControllerSingleton;
        this.songPlayerActivity = songPlayerActivity;
    }

}
