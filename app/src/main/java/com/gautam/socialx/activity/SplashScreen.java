package com.gautam.socialx.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.facebook.AccessToken;
import com.gautam.socialx.R;
import com.gautam.socialx.Shared.SharedPref;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;

public class SplashScreen extends AppCompatActivity {

    private int SPLASH_SCREEN_TIME_OUT = 2000;
FirebaseAuth firebaseAuth;
    private boolean loggedIn=false;
    private GoogleSignInOptions gso;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        createGoogleReq();

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();

        firebaseAuth = FirebaseAuth.getInstance();

        /*if(firebaseAuth.getCurrentUser()!=null)
        {
            loggedIn = true;
        }
        else if(account != null){
            loggedIn = true;
        }else if(accessToken != null && !accessToken.isExpired() ){
            loggedIn = true;
        }
        else{
            loggedIn = false;

        }
*/
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(SharedPref.sharedPref(getApplicationContext()).getLOGGED()){

                    Intent i=new Intent(SplashScreen.this, HomeActivity.class);
                    startActivity(i);
                    finish();
                }else{
                    Intent i=new Intent(SplashScreen.this, Login.class);
                    startActivity(i);
                    finish();}
            }
        }, SPLASH_SCREEN_TIME_OUT);
    }

    private void createGoogleReq() {
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

}