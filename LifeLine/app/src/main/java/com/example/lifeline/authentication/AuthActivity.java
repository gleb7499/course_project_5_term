package com.example.lifeline.authentication;

import android.os.Bundle;

import com.example.lifeline.AuthHelloActivity;

public class AuthActivity extends AuthHelloActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        addFragmentsInList(false);
        super.onCreate(savedInstanceState);
    }
}