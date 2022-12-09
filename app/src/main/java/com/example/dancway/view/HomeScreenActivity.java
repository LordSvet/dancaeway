package com.example.dancway.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.dancway.R;
import com.example.dancway.model.User;

public class HomeScreenActivity extends AppCompatActivity {
    TextView partyMode;
    TextView greetings;
    CardView profileCard;
    CardView songsListCard;
    CardView playlistsCard;
    CardView settingsCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        Intent intent = getIntent();

        greetings = findViewById(R.id.greetingsTextView);
        partyMode = findViewById(R.id.partyModeTextView);
        profileCard = findViewById(R.id.profileCard);
        songsListCard = findViewById(R.id.songsListCard);
        playlistsCard = findViewById(R.id.playlistsCard);
        settingsCard = findViewById(R.id.settingsCard);

        if(User.getCurrentUser().getUsername()!=null){
            greetings.append(User.getCurrentUser().getUsername());
        }

        if(intent.getBooleanExtra("PartyModeEnabled", false)){
            partyMode.append(intent.getStringExtra("SeshCode"));
            partyMode.setTextColor(Color.WHITE);
            partyMode.setVisibility(View.VISIBLE);
        }

        profileCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nextActivity = new Intent(HomeScreenActivity.this, UserProfileActivity.class);
                startActivity(nextActivity);
            }
        });
        songsListCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nextActivity = new Intent(HomeScreenActivity.this, MainActivity.class);
                startActivity(nextActivity);
            }
        });
//        playlistsCard.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent nextActivity = new Intent(HomeScreenActivity.this, UserProfileActivity.class);
//            }
//        });
        //TODO: SETTINGS AND PLAYLISTS CREATION LATER

    }

}