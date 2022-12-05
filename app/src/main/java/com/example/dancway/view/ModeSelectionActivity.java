package com.example.dancway.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dancway.R;


public class ModeSelectionActivity extends AppCompatActivity {

    ImageView partyMode, soloMode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        partyMode = (ImageView) findViewById(R.id.party_illustration);
        partyMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (ModeSelectionActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        soloMode = (ImageView) findViewById(R.id.solo_illustration);
        soloMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ModeSelectionActivity.this, MainActivity.class);
            }
        });

    }



}
