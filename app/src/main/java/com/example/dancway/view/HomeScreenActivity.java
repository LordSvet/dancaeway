package com.example.dancway.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.dancway.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

/**
 * This is the homescreen activity
 */
public class HomeScreenActivity extends AppCompatActivity {
    TextView partyMode;
    TextView greetings;
    CardView profileCard;
    CardView songsListCard;
    CardView playlistsCard;
    CardView settingsCard;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;

    @Override
    public void onBackPressed() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }

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