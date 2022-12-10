package com.example.dancway.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dancway.R;
import com.example.dancway.model.PartyMode;
import com.example.dancway.model.PartyRole;
import com.example.dancway.model.User;

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
                currentUser.setPartyRole(PartyRole.MASTER);
                partyMode = new PartyMode(User.getCurrentUser());
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
                    User.getCurrentUser().setPartyRole(PartyRole.GUEST);
                    partyMode = new PartyMode(User.getCurrentUser(), inputBar.getText().toString());
                    Intent intent = new Intent(JoinPartyActivity.this, HomeScreenActivity.class);
                    intent.putExtra("SeshCode", inputBar.getText().toString());
                    intent.putExtra("PartyModeEnabled",true);
                    startActivity(intent);
                }
            }
        });

    }
}
