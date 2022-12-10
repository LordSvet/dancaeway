package com.example.dancway.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dancway.R;
import com.example.dancway.controller.SongsListAdapter;
import com.example.dancway.controller.SongsListController;

/**
 * This activity is to change to the mode, party mode or solo mode
 */
public class ModeSelectionActivity extends AppCompatActivity {

   ImageView partyMode, soloMode;
   TextView partyModeSelection, soloModeSelection;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode_selection);

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
                Intent intent = new Intent(ModeSelectionActivity.this, MainActivity.class);
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
