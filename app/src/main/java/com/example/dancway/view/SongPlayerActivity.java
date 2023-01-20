package com.example.dancway.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import static com.example.dancway.service.ApplicationClass.ACTION_NEXT;
import static com.example.dancway.service.ApplicationClass.ACTION_PLAY;
import static com.example.dancway.service.ApplicationClass.ACTION_PREV;
import static com.example.dancway.service.ApplicationClass.CHANNEL_ID_2;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.IBinder;
import android.support.v4.media.session.MediaSessionCompat;
import android.util.Log;

import com.example.dancway.model.PartyMode;
import com.example.dancway.service.MusicService;
import com.example.dancway.service.NotificationReceiver;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.dancway.R;
import com.example.dancway.controller.MusicPlayerControllerSingleton;
import com.example.dancway.controller.SongsQueueAdapter;
import com.example.dancway.model.Song;
import com.example.dancway.model.SongsList;

import java.util.Collections;
import java.util.List;

/**
 * Activity which shows the music player. In Solo mode the user can listen to music as normal, they can vote however they like, they can switch to next song, loop and shuffle songs.
 * In party mode only the master can listen to the music for guests it is only displayed and also for both groups the buttons are disabled and they can vote once every 4 minutes
 */
public class SongPlayerActivity extends AppCompatActivity implements ServiceConnection {

    public static final String CLASS_TAG = SongPlayerActivity.class.getName();

    MusicPlayerControllerSingleton musicPlayerController;
    ImageView profileButton;
    ImageView previousSong, pausePlay, nextSong, shuffle, loop;
    TextView artistName, songName, seekbarEnd, seekbarStart;
    SeekBar seekBar;
    Handler handler = new Handler();
    Runnable runnable;
    int songPosition;
    Song currentSong;
    ListView comingNext;
    SongsQueueAdapter adapter;
    List<Song> upcomingNext;

    MusicService musicService;
    MediaSessionCompat mediaSession;
    PartyMode partyMan = JoinPartyActivity.partyMode;
    boolean isLoop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_player);

        mediaSession = new MediaSessionCompat(this, "PlayerAudio");

        artistName = findViewById(R.id.artistName);
        songName = findViewById(R.id.songTittle);
        seekBar = findViewById(R.id.seekBar);
        seekbarEnd = findViewById(R.id.seekbarEnd);
        seekbarStart = findViewById(R.id.seekbarStart);
        previousSong = findViewById(R.id.previousSong);
        nextSong = findViewById(R.id.nextSong);
        pausePlay = findViewById(R.id.pausePlay);
        comingNext = findViewById(R.id.coming_next_preview);
        shuffle = findViewById(R.id.shuffleButton);
        loop = findViewById(R.id.loopButton);
        musicPlayerController = MusicPlayerControllerSingleton.getInstance();
        isLoop = false;

        loop.setAlpha(.5f);

        if (partyMan != null) {
            if(partyMan.isMaster()) {
                playSong();             //Plays the current song
            }else setForPartyGuest();
        }else {
            setOnClickListeners();  //Sets on Click Listeners to all buttons and such
            playSong();
        }

    }

    /**
     * Prepares music player to play current song
     */
    public void playSong(){
        //Position of selected song from list is put in intent Extras under name "pos"

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            songPosition = extras.getInt("pos", 0);
            currentSong = ModeSelectionActivity.upcomingSongs.get(songPosition);


            if (musicPlayerController.isPaused()) {       //Added this because if music player gets paused and then user clicks another song it doesnt play it automatically
                musicPlayerController.unPausePlayer();
            }
            musicPlayerController.playSong(currentSong);
            showNotification(R.drawable.pause_button);


            musicPlayerController.getMusicPlayer().setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    songPosition++;
                    if (songPosition >= ModeSelectionActivity.upcomingSongs.size()) {
                        songPosition = 0; //If it was last song it goes back around
                    }
                    currentSong = ModeSelectionActivity.upcomingSongs.get(songPosition);
                    musicPlayerController.changeSong(currentSong);
                    updateViews(currentSong);
                    updateSeekbar();

                }
            });

        }else{
            currentSong = MusicPlayerControllerSingleton.getInstance().getSong();
        }
        int currentSongIndex = SongsList.getIndexOfSongInList(ModeSelectionActivity.upcomingSongs, currentSong.getTitle());
        if(ModeSelectionActivity.upcomingSongs.size() == 1){
            currentSongIndex = -1;
        }
        upcomingNext = ModeSelectionActivity.upcomingSongs.subList(currentSongIndex+1, ModeSelectionActivity.upcomingSongs.size());
        if(partyMan!=null) {
            partyMan.setListToPass(upcomingNext);
        }
        adapter = new SongsQueueAdapter(upcomingNext,this);
        comingNext.setAdapter(adapter);
        updateViews(currentSong);
        updateSeekbar();

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int position, boolean fromUser) {
                if(fromUser){       //Check is user clicked this or if it was done in the Handler
                    if(!musicPlayerController.isPrepared()) return;
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

    /**
     * Sets up the views for the Party Guest mode
     */
    public void setForPartyGuest(){

        currentSong = ModeSelectionActivity.upcomingSongs.get(0);
        int currentSongIndex = SongsList.getIndexOfSongInList(ModeSelectionActivity.upcomingSongs, currentSong.getTitle());
        if(ModeSelectionActivity.upcomingSongs.size() == 1){
            currentSongIndex = -1;
        }
        upcomingNext = ModeSelectionActivity.upcomingSongs.subList(currentSongIndex+1, ModeSelectionActivity.upcomingSongs.size());
        if(partyMan!=null) {
            partyMan.setListToPass(upcomingNext);
        }
        adapter = new SongsQueueAdapter(upcomingNext,this);
        comingNext.setAdapter(adapter);
        updateViews(currentSong);
    }

    /**
     * Sets the onClick listeners to all views that need them
     */
    public void setOnClickListeners(){
        loop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                musicPlayerController.setLoop(!isLoop);
                isLoop = !isLoop;
                if(isLoop) loop.setAlpha(1f);
                else loop.setAlpha(.5f);
            }
        });

        shuffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shuffleQueue(upcomingNext);
                adapter.notifyDataSetChanged();
            }
        });


        profileButton = (ImageView) findViewById(R.id.profileButton);
        profileButton.setOnClickListener(new View.OnClickListener() {
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
    }

    /**
     * This method is for updating the seekebar of the player
     */
    public void updateSeekbar(){
        int currentPosition = musicPlayerController.getMusicPlayer().getCurrentPosition();
        seekBar.setProgress(currentPosition/1000);
        String minutes = String.valueOf(((currentPosition/1000) % 3600) / 60);
        if(Integer.parseInt(minutes) < 10){
            minutes = "0" + minutes;
        }

        String seconds = String.valueOf((currentPosition/1000) % 60);
        if(Integer.parseInt(seconds) < 10){
            seconds = "0" + seconds;
        }
        seekbarStart.setText(minutes + ":" + seconds);

        runnable = new Runnable() {
            @Override
            public void run() {
                updateSeekbar();
            }
        };
        handler.postDelayed(runnable,1000);
    }

    /**
     * Updates the UI according to the song passed
     * @param song Song that is passed
     */
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

        if(adapter != null) {
            int index = SongsList.getIndexOfSongInList(ModeSelectionActivity.upcomingSongs, currentSong.getTitle());
            if(ModeSelectionActivity.upcomingSongs.size() == 1){
                index = -1;
            }
            upcomingNext = ModeSelectionActivity.upcomingSongs.subList(index+1, ModeSelectionActivity.upcomingSongs.size());

            if(partyMan!=null) {
                partyMan.setListToPass(upcomingNext);
            }

            adapter.updateDataChanged(upcomingNext);
        }
    }

    /**
     * Shuffles the queue
     */
    public static void shuffleQueue(List<Song> queueToShuffle) {
        if (queueToShuffle != null) {
            Collections.shuffle(queueToShuffle);
        }
    }

    /**
     * Shows the player in the quick settings
     */
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
        //Update views on opening
    }

    @Override
    protected void onPause() {
        super.onPause();
        unbindService(this);
    }

    /**
     * Pauses if player is playing and plays if it is paused
     */
    public void pausePlay() {
        if(!musicPlayerController.isPrepared()) return;
        if(!musicPlayerController.isPaused()){
            musicPlayerController.pausePlayer();
            pausePlay.setImageResource(R.drawable.play_button);
            showNotification(R.drawable.play_button);
        }else if(musicPlayerController.isPaused()){
            musicPlayerController.unPausePlayer();
            pausePlay.setImageResource(R.drawable.pause_button);
            showNotification(R.drawable.pause_button);
        }
    }

    /**
     * Switches to next song
     */
    public void next() {
        if(!musicPlayerController.isPrepared()) return;
        songPosition ++;
        if(songPosition >= ModeSelectionActivity.upcomingSongs.size()){
            songPosition = 0; //If it was last song it goes back around
        }
        currentSong = ModeSelectionActivity.upcomingSongs.get(songPosition);
        musicPlayerController.changeSong(currentSong);
        pausePlay.setImageResource(R.drawable.pause_button);
        if(musicPlayerController.getMusicPlayer().isPlaying()){
            showNotification(R.drawable.play_button);
        }else if(!musicPlayerController.getMusicPlayer().isPlaying()){
            showNotification(R.drawable.pause_button);
        }
        updateViews(currentSong);
    }

    /**
     * Switches to previous song
     */
    public void previous() {
        if(!musicPlayerController.isPrepared()) return;
        songPosition --;
        if(songPosition < 0){
            songPosition = 0; //If it was first song it plays it again and again
        }
        currentSong = ModeSelectionActivity.upcomingSongs.get(songPosition);
        musicPlayerController.changeSong(currentSong);
        pausePlay.setImageResource(R.drawable.pause_button);
        if(musicPlayerController.getMusicPlayer().isPlaying()){
            showNotification(R.drawable.play_button);
        }else if(!musicPlayerController.getMusicPlayer().isPlaying()){
            showNotification(R.drawable.pause_button);
        }
        updateViews(currentSong);
    }
}