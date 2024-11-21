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

public class EmailFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    public EmailFragment() {
    }

    public static EmailFragment newInstance(OnFragmentInteractionListener listener) {
        EmailFragment fragment = new EmailFragment();
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
        View view = inflater.inflate(R.layout.fragment_email, container, false);

        EditText email = view.findViewById(R.id.editTextTextEmailAddress);
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Действия до изменения текста
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Действия при изменении текста
                if (!s.toString().isEmpty()) {
                    sendDataToActivity(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Действия после изменения текста
            }
        });

        return view;
    }
}