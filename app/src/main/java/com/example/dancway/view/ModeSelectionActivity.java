package com.example.dancway.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dancway.R;
import com.example.dancway.controller.SongsListAdapter;
import com.example.dancway.controller.SongsListController;
import com.example.dancway.model.Song;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.List;

/**
 * This activity is to change to the mode, party mode or solo mode
 */
public class ModeSelectionActivity extends AppCompatActivity {

   ImageView partyMode, soloMode;
   TextView partyModeSelection, soloModeSelection, welcomeUsername;
   FirebaseAuth fAuth;
   FirebaseFirestore fStore;
   String userID;
   public static ArrayList<Song> upcomingSongs; //This will be used to access future queue of songs. I want it initialized early so its here

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode_selection);

        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){
            Log.i("Error: ", e.getMessage());
        }

        upcomingSongs = new ArrayList<>();


        /*
         Those are used to connect with the Cloud Firestore and check the UserID in order to get the correct username
         */
        welcomeUsername = findViewById(R.id.welcomeUsername);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();

        /*
        Gets the username from the Firestore and displays as the Greeting msg
         */
        DocumentReference documentReference = fStore.collection("users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                welcomeUsername.setText("Welcome " + value.getString("uName"));

            }
        });


        partyMode = (ImageView) findViewById(R.id.party_illustration);
        partyMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (ModeSelectionActivity.this, JoinPartyActivity.class);
                startActivity(intent);

            }
        });

        soloMode = (ImageView) findViewById(R.id.solo_illustration);
        soloMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ModeSelectionActivity.this, HomeScreenActivity.class);
                startActivity(intent);
            }
        });

        partyModeSelection = (TextView) findViewById(R.id.partyMode);
        partyModeSelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ModeSelectionActivity.this, JoinPartyActivity.class);
                startActivity(intent);
            }
        });
        soloModeSelection = (TextView) findViewById(R.id.soloMode);
        soloModeSelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ModeSelectionActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

}
