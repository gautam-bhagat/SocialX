package com.gautam.socialx.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.gautam.socialx.api.SignupListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignupViewModel extends ViewModel {

    FirebaseAuth firebaseAuth;

    public SignupViewModel() {
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void AuthenticateThroughFirebase(SignupListener signupListener, String name, String email, String phone, String password)
    {
        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
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
}
