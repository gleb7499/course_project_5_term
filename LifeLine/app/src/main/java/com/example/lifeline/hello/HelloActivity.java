package com.example.lifeline.hello;

import android.os.Bundle;

import com.example.lifeline.AuthHelloActivity;

public class HelloActivity extends AuthHelloActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        addFragmentsInList(true);
        super.onCreate(savedInstanceState);
    }
}