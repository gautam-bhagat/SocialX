package com.gautam.socialx.api;

import com.gautam.socialx.model.Articles;

import java.util.List;

public interface SignupListener {
    void signupSuccess(boolean b);
    void signupError(boolean b,String message);
}
