package com.example.dancway.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dancway.R;
import com.example.dancway.controller.MusicPlayerControllerSingleton;
import com.example.dancway.model.PartyMode;
import com.example.dancway.model.Song;
import com.example.dancway.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the homescreen activity
 */
public class HomeScreenActivity extends AppCompatActivity {
    TextView partyMode;
    ImageButton spinningLogo;
    TextView greetings;
    CardView profileCard;
    CardView songsListCard;
    CardView playlistsCard;
    CardView settingsCard;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;

    PartyMode partyMan;

    /**
     * If user clicks the back button it minimizes the app so issues with previous activities don't occur
     */
    @Override
    public void onBackPressed() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }

    /**
     * Adds a button that opens the SongPlayerActivity either if player is playing a song or if its party mode and user is a guest
     */
    @Override
    protected void onResume() {
        if (spinningLogo != null || ModeSelectionActivity.upcomingSongs.size() > 0) {
            if (!MusicPlayerControllerSingleton.getInstance().getMusicPlayer().isPlaying()) {
                if (partyMan == null) {
                    spinningLogo.setVisibility(View.INVISIBLE);
                    spinningLogo.setClickable(false);
                }
            } else {
                spinningLogo.setVisibility(View.VISIBLE);
                spinningLogo.setClickable(true);
                Animation rotateAnimation = AnimationUtils.loadAnimation(this, R.anim.rotation);
                rotateAnimation.setRepeatCount(Animation.INFINITE);
                spinningLogo.startAnimation(rotateAnimation);
                spinningLogo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent songPlayer = new Intent(HomeScreenActivity.this, SongPlayerActivity.class);

                        if (partyMan != null && ModeSelectionActivity.upcomingSongs.isEmpty()) {
                            ModeSelectionActivity.upcomingSongs.addAll(partyMan.getSongList());
                        }
                        if (partyMan != null && ModeSelectionActivity.upcomingSongs.isEmpty()) {
                            Toast.makeText(HomeScreenActivity.this, "Song list not loaded. Please wait :)", Toast.LENGTH_LONG).show();
                            return;
                        }

                        startActivity(songPlayer);
                    }
                });
            }
        }
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        spinningLogo = findViewById(R.id.openPlayerButton);


        Intent intent = getIntent();

        greetings = findViewById(R.id.greetingsTextView);
        partyMode = findViewById(R.id.partyModeTextView);
        profileCard = findViewById(R.id.profileCard);
        songsListCard = findViewById(R.id.songsListCard);
        playlistsCard = findViewById(R.id.playlistsCard);
        settingsCard = findViewById(R.id.settingsCard);
        partyMan = JoinPartyActivity.partyMode;

        //Add username to greetings here
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();

        DocumentReference documentReference = fStore.collection("users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                greetings.setText("Hello, " + value.getString("uName"));

            }
        });

        if (intent.getBooleanExtra("PartyModeEnabled", false)) {
            partyMode.append(intent.getStringExtra("SeshCode"));
            partyMode.setTextColor(Color.WHITE);
            partyMode.setVisibility(View.VISIBLE);
        }

        profileCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nextActivity = new Intent(HomeScreenActivity.this, UserProfileActivity.class);
                startActivity(nextActivity);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
            }
        });
        songsListCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nextActivity = new Intent(HomeScreenActivity.this, MainActivity.class);
                startActivity(nextActivity);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
            }
        });
        playlistsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nextActivity = new Intent(HomeScreenActivity.this, PlaylistsActivity.class);
                startActivity(nextActivity);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
            }
        });

    }
}