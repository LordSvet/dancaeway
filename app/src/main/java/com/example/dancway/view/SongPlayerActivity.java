package com.example.dancway.view;

import static com.example.dancway.service.ApplicationClass.ACTION_NEXT;
import static com.example.dancway.service.ApplicationClass.ACTION_PLAY;
import static com.example.dancway.service.ApplicationClass.ACTION_PREV;
import static com.example.dancway.service.ApplicationClass.CHANNEL_ID_2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.media.session.MediaSessionCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.dancway.R;
import com.example.dancway.controller.MusicPlayerControllerSingleton;
import com.example.dancway.controller.SongsListController;
import com.example.dancway.model.Song;
import com.example.dancway.service.MusicService;
import com.example.dancway.service.NotificationReceiver;

/**
 * This is song player activity
 */
public class SongPlayerActivity extends AppCompatActivity implements ServiceConnection {
    public static final String CLASS_TAG = SongPlayerActivity.class.getName();
    MusicPlayerControllerSingleton musicPlayerController;
    ImageView profileButton, upVoteButton, downVoteButton;
    ImageView previousSong, pausePlay, nextSong;
    TextView artistName, songName, seekbarEnd;
    SeekBar seekBar;
    Handler handler = new Handler();
    Runnable runnable;
    int songPosition;
    Song currentSong;
    MusicService musicService;
    MediaSessionCompat mediaSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_player);

        artistName = findViewById(R.id.artistName);
        songName = findViewById(R.id.songTittle);
        seekBar = findViewById(R.id.seekBar);
        seekbarEnd = findViewById(R.id.seekbarEnd);
        previousSong = findViewById(R.id.previousSong);
        nextSong = findViewById(R.id.nextSong);
        pausePlay = findViewById(R.id.pausePlay);
        upVoteButton =findViewById(R.id.upVoteButton);
        downVoteButton= findViewById(R.id.downVoteButton);
        musicPlayerController = MusicPlayerControllerSingleton.getInstance();

        mediaSession = new MediaSessionCompat(this, "PlayerAudio");

        profileButton = (ImageView) findViewById(R.id.profileButton);
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SongPlayerActivity.this, UserProfileActivity.class);
                startActivity(intent);
            }
        });

        upVoteButton = (ImageView) findViewById(R.id.upVoteButton);
        upVoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SongPlayerActivity.this, UserProfileActivity.class);
                startActivity(intent);
            }
        });

        downVoteButton = (ImageView) findViewById(R.id.downVoteButton);
        downVoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SongPlayerActivity.this, UserProfileActivity.class);
                startActivity(intent);
            }
        });


        previousSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                previous();
            }
        });

        nextSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                next();
            }
        });

        pausePlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pausePlay();
            }
        });

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            songPosition = extras.getInt("pos", -1);
            if (songPosition != -1) {
                currentSong = SongsListController.getSongsList().getSongAt(songPosition);

                updateViews(currentSong);

                musicPlayerController.playSong(currentSong);
                showNotification(R.drawable.pause_button);
                updateSeekbar();

                musicPlayerController.getMusicPlayer().setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        songPosition++;
                        currentSong = SongsListController.getSongsList().getSongAt(songPosition);
                        musicPlayerController.changeSong(currentSong);
                        updateViews(currentSong);
                        updateSeekbar();

                    }
                });
            }
        }
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int position, boolean fromUser) {
                if(fromUser){       //Check is user clicked this or if it was done in the Handler
                    musicPlayerController.seekTo(position*1000);    //No idea why it has to be multiplied by a thousand but its the only way it works
                    seekBar.setProgress(position);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { //We don't really need to do this but it has to be here
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {  //We don't really need to do this but it has to be here

            }
        });

    }

    public void pausePlay() {
        if(musicPlayerController.getMusicPlayer().isPlaying()){
            musicPlayerController.pausePlayer();
            pausePlay.setImageResource(R.drawable.play_button);
            showNotification(R.drawable.play_button);
        }else if(!musicPlayerController.getMusicPlayer().isPlaying()){
            musicPlayerController.unPausePlayer();
            pausePlay.setImageResource(R.drawable.pause_button);
            showNotification(R.drawable.pause_button);
        }
    }

    /**
     * Go to next song
     */
    public void next() {
        songPosition ++;
        if(songPosition >= SongsListController.getSongsList().getSize()){
            songPosition = 0; //If it was last song it goes back around
        }
        currentSong = SongsListController.getSongsList().getSongAt(songPosition);
        musicPlayerController.changeSong(currentSong);
        if(musicPlayerController.getMusicPlayer().isPlaying()){
            showNotification(R.drawable.play_button);
        }else if(!musicPlayerController.getMusicPlayer().isPlaying()){
            showNotification(R.drawable.pause_button);
        }
        updateViews(currentSong);
    }

    /**
     * Go to previous song
     */
    public void previous() {
        songPosition --;
        if(songPosition < 0){
            songPosition = SongsListController.getSongsList().getSize()-1; //If it was first song it goes back around
        }
        currentSong = SongsListController.getSongsList().getSongAt(songPosition);
        musicPlayerController.changeSong(currentSong);
        if(musicPlayerController.getMusicPlayer().isPlaying()){
            showNotification(R.drawable.play_button);
        }else if(!musicPlayerController.getMusicPlayer().isPlaying()){
            showNotification(R.drawable.pause_button);
        }
        updateViews(currentSong);
    }

    /**
     * This method is for updating the seekebar of the player
     */
    public void updateSeekbar(){
        int currentPosition = musicPlayerController.getMusicPlayer().getCurrentPosition();
        seekBar.setProgress(currentPosition/1000);

        runnable = new Runnable() {
            @Override
            public void run() {
                updateSeekbar();
            }
        };
        handler.postDelayed(runnable,1000);
    }

    public void updateViews(Song song){
        seekBar.setProgress(0);
        seekBar.setMax((int) song.getDuration());
        String seekbarEndString = "";
        seekbarEndString += song.getDuration()/60 + ":";
        if(song.getDuration()%60 < 10){
            seekbarEndString+="0";
        }
        seekbarEndString += song.getDuration()%60;
        seekbarEnd.setText(seekbarEndString);

        artistName.setText(song.getArtist().getName());
        songName.setText(song.getTitle());
    }

    public void showNotification(int playPauseBtn) {
        Intent intent = new Intent(this, SongPlayerActivity.class);
        intent.putExtra("pos", songPosition);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Intent prevIntent = new Intent(this, NotificationReceiver.class)
                .setAction(ACTION_PREV);
        PendingIntent prevPendingIntent = PendingIntent.getBroadcast(this, 0,
                prevIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Intent playIntent = new Intent(this, NotificationReceiver.class)
                .setAction(ACTION_PLAY);
        PendingIntent playPendingIntent = PendingIntent.getBroadcast(this, 0,
                playIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Intent nextIntent = new Intent(this, NotificationReceiver.class)
                .setAction(ACTION_NEXT);
        PendingIntent nextPendingIntent = PendingIntent.getBroadcast(this, 0,
                nextIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Bitmap picture = BitmapFactory.decodeResource(getResources(),
                R.drawable.music_service_large_icon);
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID_2)
                .setSmallIcon(R.drawable.logo_icon)
                .setLargeIcon(picture)
                .setContentTitle(currentSong.getTitle())
                .setContentText(currentSong.getArtist().getName())
                .addAction(R.drawable.previous_song_button, "Previous", prevPendingIntent)
                .addAction(playPauseBtn, "Play", playPendingIntent)
                .addAction(R.drawable.next_song_button, "Next", nextPendingIntent)
                .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
                        .setMediaSession(mediaSession.getSessionToken()))
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setContentIntent(contentIntent)
                .setOnlyAlertOnce(true)
                .build();
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);
    }


    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        MusicService.MyBinder binder = (MusicService.MyBinder) iBinder;
        musicService = binder.getService();
        musicService.setCallBack(musicPlayerController, SongPlayerActivity.this);
        Log.d(CLASS_TAG, "MusicService connected!");
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {
        musicService = null;
        Log.d(CLASS_TAG, "MusicService disconnected!");
    }

    @Override
    protected void onResume() {
        super.onResume();
        //--- Bind Music Service ---
        Intent intent = new Intent(this, MusicService.class);
        bindService(intent, this, BIND_AUTO_CREATE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unbindService(this);
    }
}