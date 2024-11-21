package com.example.lifeline.Authantication;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.example.lifeline.R;

public class SignFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    public SignFragment() {
    }

    public static SignFragment newInstance(SignFragment.OnFragmentInteractionListener listener) {
        SignFragment fragment = new SignFragment();
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
            Log.d("EmailFragment", "mListener is null");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign, container, false);

        EditText editTextPassword = view.findViewById(R.id.editTextPassword);
        editTextPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Действия до изменения текста
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Действия при изменении текста
                sendDataToActivity(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Действия после изменения текста
            }
        });

        return view;
    }
}