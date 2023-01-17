package com.example.dancway.view;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dancway.R;
import com.example.dancway.controller.SongsListController;
import com.example.dancway.model.PartyGuest;
import com.example.dancway.model.PartyMaster;
import com.example.dancway.model.PartyMode;
import com.example.dancway.model.SessionCodeGen;
import com.example.dancway.model.Song;
import com.example.dancway.model.SongQueue;
import com.example.dancway.model.User;

import java.util.ArrayList;
import java.util.Locale;

/**
 * This is the join party activity
 */
public class JoinPartyActivity extends AppCompatActivity {

    TextView becomeMaster;
    Button joinSesh;
    TextView title;
    EditText inputBar;
    PartyMode partyMode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_party);

        becomeMaster = findViewById(R.id.partyMasterButton);
        joinSesh = findViewById(R.id.joinButton);
        title = findViewById(R.id.title);
        inputBar = findViewById(R.id.partyCode);
        User currentUser = User.getCurrentUser();

        becomeMaster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                currentUser.setPartyRole(PartyRole.MASTER); REMOVE
                String seshCode = SessionCodeGen.getCode();
                partyMode = new PartyMaster(seshCode,new ArrayList<>(),new ArrayList<>(), new SongQueue(50));   //TODO: TEST
                Intent intent = new Intent(JoinPartyActivity.this, HomeScreenActivity.class);
                intent.putExtra("SeshCode",partyMode.getCodeGenerator());
                intent.putExtra("PartyModeEnabled",true);
                startActivity(intent);
            }
        });

        /**
         * When join session button is clicked, user is taken to party mode
         */
        joinSesh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(inputBar.getText().toString().length() != 5){
                    Toast.makeText(JoinPartyActivity.this, "Invalid Code Entered", Toast.LENGTH_SHORT).show();
                }else{
                    partyMode = new PartyGuest(inputBar.getText().toString().toUpperCase(),new ArrayList<>(),new ArrayList<>(), new SongQueue(50)); //TODO: TEST
                    Intent intent = new Intent(JoinPartyActivity.this, HomeScreenActivity.class);
                    intent.putExtra("SeshCode", inputBar.getText().toString().toUpperCase());
                    intent.putExtra("PartyModeEnabled",true);
                    startActivity(intent);
                }
            }
        });

    }
}
