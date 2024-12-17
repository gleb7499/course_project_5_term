package com.example.lifeline.dashboard;

import android.os.Bundle;

import com.example.lifeline.interfaces.AddViewFragment;

public class ViewFragment extends AddViewFragment {
    @Override
    protected void getExtraFromIntent() {
        Bundle bundle = getArguments();
        String date = bundle.getString("date");
        super.whatsFragment(date);
    }
}
