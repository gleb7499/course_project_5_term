package com.example.lifeline.hello;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.lifeline.R;
import com.google.android.material.textfield.TextInputEditText;

public class UserFragment extends Fragment {

    private UserFragment.OnFragmentInteractionListener mListener;

    public UserFragment() {
        // Required empty public constructor
    }

    public static UserFragment newInstance(UserFragment.OnFragmentInteractionListener listener) {
        UserFragment fragment = new UserFragment();
        fragment.mListener = listener;
        return fragment;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(String data);
    }

    private void sendDataToActivity(String data) {
        if (mListener != null) {
            mListener.onFragmentInteraction(data);
        } else {
            Log.d("UserFragment", "mListener is null");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);

        TextInputEditText editTextUser = view.findViewById(R.id.editTextUser);
        editTextUser.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Действия до изменения текста
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Действия при изменении текста
                sendDataToActivity(s.toString());
                Log.d("UserFragment", "onTextChanged: " + s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Действия после изменения текста
            }
        });

        return view;
    }
}