package com.gautam.socialx.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.gautam.socialx.R;
import com.gautam.socialx.Shared.SharedPref;
import com.gautam.socialx.api.SignupListener;
import com.gautam.socialx.databinding.ActivityLoginBinding;
import com.gautam.socialx.viewmodels.LoginViewModel;
import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import java.util.Arrays;

public class Login extends AppCompatActivity {
    private static final int RC_SIGN_IN = 100;
    private SignInClient oneTapClient;
    private BeginSignInRequest signInRequest;

    ActivityLoginBinding login;
    private LoginViewModel loginViewModel;
    private GoogleSignInOptions gso;
    private GoogleSignInClient mGoogleSignInClient;
    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        login = DataBindingUtil.setContentView(this, R.layout.activity_login);
//        setContentView(R.layout.activity_login);
        initUI();
    }

    private void initUI() {

        callbackManager = CallbackManager.Factory.create();

      createFacebookReq();
        createGoogleReq();
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);

        login.signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToSignupPage();
            }
        });

        login.dontHaveAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToSignupPage();
            }
        });

        login.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fieldValidation();
            }
        });

        login.forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendPasswordResetMail();
            }
        });

        login.googleIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

        login.facebookIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.getInstance().logInWithReadPermissions(Login.this, Arrays.asList("public_profile"));
            }
        });

    }

    private void createFacebookReq() {
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        goToHomePage();
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                    }
                });

    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void createGoogleReq() {
         gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> task) {
        try {
            GoogleSignInAccount account = task.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            goToHomePage();
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
//            updateUI(null);
        }
    }

    private void goToHomePage() {

        SharedPref.sharedPref(getApplicationContext()).setLOGGED(true);
        startActivity(new Intent(getApplicationContext(),HomeActivity.class));
        finish();
    }

    private void sendPasswordResetMail() {
        if(!validateEmail())
        {
            return;
        }
        String email = login.loginEmail.getText().toString().trim();

        loginViewModel.resetPassword(resetPassListener,email);
    }

    private void fieldValidation() {

        if(!validatePassword() |!validateEmail())
        {
            return;
        }
        String email = login.loginEmail.getText().toString().trim();
        String password = login.loginPass.getText().toString().trim();

        loginViewModel.loginWithFirebase(signupListener,email,password);
    }

    private void goToSignupPage() {
        Intent i = new Intent(getApplicationContext(),Signup.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(i);
        finish();
    }

    private final SignupListener signupListener = new SignupListener() {
        @Override
        public void signupSuccess(boolean b) {
            goToHomePage();
        }

        @Override
        public void signupError(boolean b,String message) {
            Toast.makeText(getApplicationContext(), "Error : "+message, Toast.LENGTH_SHORT).show();
        }
    };

    private final SignupListener resetPassListener = new SignupListener() {
        @Override
        public void signupSuccess(boolean b) {
            Toast.makeText(getApplicationContext(), "Reset Mail sent!", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void signupError(boolean b,String message) {
            Toast.makeText(getApplicationContext(), "Error : "+message, Toast.LENGTH_SHORT).show();
        }
    };

    private boolean validateEmail() {
        String val = login.loginEmail.getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty()) {
            login.loginEmail.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(emailPattern)) {
            login.loginEmail.setError("Invalid email address");
            return false;
        } else {
            login.loginEmail.setError(null);

            return true;
        }
    }
    private Boolean validatePassword() {
        String val = login.loginPass.getText().toString();
        String passwordVal = "^" +
                //"(?=.*[0-9])" +         //at least 1 digit
                //"(?=.*[a-z])" +         //at least 1 lower case letter
                //"(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
                ".{8,}" +               //at least 8 characters
                "$";

        if (val.isEmpty()) {
            login.loginPass.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(passwordVal)) {
            login.loginPass.setError("Password should be minimum 8 characters with combination of atleast a Uppercase, Lowercase , Symbols and numbers");
            return false;
        } else {
            login.loginPass.setError(null);

            return true;
        }


    }

}