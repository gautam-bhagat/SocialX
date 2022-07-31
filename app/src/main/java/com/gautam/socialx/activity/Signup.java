package com.gautam.socialx.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.gautam.socialx.R;
import com.gautam.socialx.api.SignupListener;
import com.gautam.socialx.databinding.ActivitySignupBinding;
import com.gautam.socialx.viewmodels.SignupViewModel;
import com.google.firebase.auth.FirebaseAuth;

public class Signup extends AppCompatActivity {

    ActivitySignupBinding activitySignupBinding;
    FirebaseAuth firebaseAuth;
    private SignupViewModel signupViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activitySignupBinding = DataBindingUtil.setContentView(this,R.layout.activity_signup);

        initUI();
    }

    private void initUI()
    { firebaseAuth = FirebaseAuth.getInstance();


        signupViewModel = ViewModelProviders.of(this).get(SignupViewModel.class);

        activitySignupBinding.loginIntBtn.setOnClickListener(view -> {
            goToLoginPage();
        });

        activitySignupBinding.alreadyHaveAcc.setOnClickListener(view -> {
            goToLoginPage();
        });

        activitySignupBinding.registerBtn.setOnClickListener(view -> {
            fieldValidation();
        });
    }

    private void fieldValidation()
    {if(!validateName() |!validatePassword() |!validateEmail()|!validatePhoneNo())
    {
        return;
    }
        String name = activitySignupBinding.signupName.getText().toString().trim();
        String email = activitySignupBinding.signupEmail.getText().toString().trim();
        String phone = activitySignupBinding.signupPhone.getText().toString().trim();
        String password = activitySignupBinding.signupPass.getText().toString().trim();

        if(activitySignupBinding.termsCheckbox.isChecked()){
        signupViewModel.AuthenticateThroughFirebase(signupListener,name,email,phone,password);}else{
            Toast.makeText(getApplicationContext(), "Terms and Conditions Required!", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validateEmail() {
        String val = activitySignupBinding.signupEmail.getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty()) {
            activitySignupBinding.signupEmail.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(emailPattern)) {
            activitySignupBinding.signupEmail.setError("Invalid email address");
            return false;
        } else {
            activitySignupBinding.signupEmail.setError(null);

            return true;
        }
    }
    private Boolean validatePassword() {
        String val = activitySignupBinding.signupPass.getText().toString();
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
            activitySignupBinding.signupPass.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(passwordVal)) {
            activitySignupBinding.signupPass.setError("Password should be minimum 8 characters with combination of atleast a Uppercase, Lowercase , Symbols and numbers");
            return false;
        } else {
            activitySignupBinding.signupPass.setError(null);

            return true;
        }


    }

    private boolean validatePhoneNo()
    {
        String val = activitySignupBinding.signupPhone.getText().toString();

        if (val.isEmpty()) {
            activitySignupBinding.signupPhone.setError("Field cannot be empty");
            return false;
        } else {
            activitySignupBinding.signupPhone.setError(null);
            
            return true;
        }
    }

    private Boolean validateName() {
        String val = activitySignupBinding.signupName.getText().toString();

        if (val.isEmpty()) {
            activitySignupBinding.signupName.setError("Field cannot be empty");
            return false;
        }
        else {
            activitySignupBinding.signupName.setError(null);
            return true;
        }
    }
    private void goToLoginPage()
    {
        Intent i = new Intent(getApplicationContext(), Login.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION );
        startActivity(i);
        finish();
    }

    private final SignupListener signupListener = new SignupListener() {
        @Override
        public void signupSuccess(boolean b) {
            Toast.makeText(getApplicationContext(), "Please Login to Continue", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(),Login.class));
            finish();
        }

        @Override
        public void signupError(boolean b,String message) {
            Toast.makeText(getApplicationContext(), "Error : "+message, Toast.LENGTH_SHORT).show();
        }
    };



    private void testCrash() {
        throw new RuntimeException("Test Crash"); // Force a crash
    }
}