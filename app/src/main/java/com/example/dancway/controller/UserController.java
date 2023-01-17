package com.example.dancway.controller;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.dancway.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * The class that holds all the data that is relevant for user. Has singleton pattern in order to be able to get a single instance from everywhere
 */
public class UserController {
    private User user;
    private FirebaseAuth auth;
    private Activity context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private FirebaseFirestore fStore;
    private String userID;

    /**
     * Instance Reference
     */
    private static UserController single_instance = null;

    private UserController(Activity context){

        auth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        this.context = context;
        // Setup storage
        sharedPreferences = context.getSharedPreferences("Session", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    /**
     * Restrict to singular Object
     * @param context current state of the object
     * @return a single instance
     */
    public static UserController getInstance(Activity context){
        if (single_instance == null)
            single_instance = new UserController(context);
        return single_instance;
    }

    public void setUser(User newUser){user = newUser;}

    /**
     * @return the user
     */
    public User getUser(){return user;}

    public void setActivity(Activity newContext){context = newContext;}

    /**
     * @return the activity context
     */
    public Activity getActivityContext(){return context;}

    /**
     *Calls {@link #register(String, String, String, String) register} method
     */
    public void register(String email, String password, String repeat_password, String username){
        registerUser(email, password, repeat_password, username);
    }

    /**
     * This method register a user
     * @param email The user email address
     * @param password The user password
     * @param repeat_password password has to be repeated
     * @param username desired username of the user
     */
    private void registerUser(String email, String password, String repeat_password, String username) {

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            user = User.getCurrentUser(auth.getCurrentUser(), username, auth.getCurrentUser().getUid());
                            Toast.makeText(context, "User registered successfully", Toast.LENGTH_SHORT).show();

                            /*
                            Here the user is stored in the Cloud Firestore
                            Only email, userID and username are stored there, under the collection "users"
                             */
                            userID = auth.getCurrentUser().getUid();
                            DocumentReference documentReference = fStore.collection("users").document(userID);
                            Map<String, Object> user = new HashMap<>();
                            user.put("uName",username);
                            user.put("email", email);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Log.d(TAG, "onSuccess: User profile is created for" + userID);
                                }
                            });
							// On success store in permanent Storage
                            editor.putString("email",email);
                            editor.putString("pass",password);
                            editor.putBoolean("loggedin",true);
                            editor.apply();
                        }else{
                            Toast.makeText(context, "Registration Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    
    /**
     * @return True, if the user is already loggedin
     */
    public boolean autologin(){

        if ( sharedPreferences.getBoolean("loggedin" , false))
        {
            String email = sharedPreferences.getString("email", "");
            String password = sharedPreferences.getString("pass" , "");
            loginUser(email,password);
            return true;
        }
            return false;
    }

    public void login(String email, String password){loginUser(email,password);}

    /**
     * This method login a user and stores the login state
     * @param email The email which was given at registration part
     * @param password The password which was given at registration part
     */
    private void loginUser(String email, String password){

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    user = User.getCurrentUser(auth.getCurrentUser(), email, auth.getCurrentUser().getUid()); //Stores FirebaseUser in user if its not null
                    Toast.makeText(context, "User logged in", Toast.LENGTH_SHORT).show();
                    
					// Permanent Storage
                    editor.putString("email",email);
                    editor.putString("pass",password);
                    editor.putBoolean("loggedin",true);
                    editor.apply();

                    DocumentReference documentReference = fStore.collection("users").document(user.getUid());      //Sets the username from the database
                    documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                            user.setUsername(value.getString("uName"));
                        }
                    });
                } else {
                    Toast.makeText(context, "Login Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    // Remove permanent Login Details
    public void logout(){
        editor.putString("email","");
        editor.putString("pass","");
        editor.putBoolean("loggedin",false);
        editor.apply();
    }

    /**
     * Checks the user is logged in or not
     * @return the state
     */
    public boolean logstate(){
        boolean loggedin = sharedPreferences.getBoolean("loggedin", false);
        return loggedin;
    }


}
