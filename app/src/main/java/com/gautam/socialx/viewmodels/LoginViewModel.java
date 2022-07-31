package com.gautam.socialx.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.gautam.socialx.api.SignupListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginViewModel extends ViewModel
{
    FirebaseAuth firebaseAuth;

    public LoginViewModel() {
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void loginWithFirebase(SignupListener signupListener, String email, String password){
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    signupListener.signupSuccess(true);
                }else{
                    signupListener.signupError(false,task.getException().getMessage());
                }
            }
        });
    }

    public void resetPassword(SignupListener signupListener, String email ){
        firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    signupListener.signupSuccess(true);
                }else{
                    signupListener.signupError(false,task.getException().getMessage());
                }
            }
        });
    }
}
