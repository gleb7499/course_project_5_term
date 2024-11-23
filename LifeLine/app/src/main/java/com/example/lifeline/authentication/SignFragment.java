package com.example.lifeline.authentication;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.example.lifeline.R;

public class SignFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    public SignFragment() {
        // Required empty public constructor
    }

    public static SignFragment newInstance(SignFragment.OnFragmentInteractionListener listener) {
        SignFragment fragment = new SignFragment();
        fragment.mListener = listener;
        return fragment;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(String data, boolean isRegister);
    }

    private void sendDataToActivity(String data, boolean isRegister) {
        if (mListener != null) {
            mListener.onFragmentInteraction(data, isRegister);
        } else {
            Log.d("SignFragment", "mListener is null");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign, container, false);

        EditText editTextPasswordAgain = view.findViewById(R.id.editTextPasswordAgain);
        EditText editTextPassword = view.findViewById(R.id.editTextPassword);

        Button buttonSwitchEntryMethods = view.findViewById(R.id.buttonSwitchEntryMethods);
        buttonSwitchEntryMethods.setOnClickListener(v -> {
            if (buttonSwitchEntryMethods.getText().equals("Регистрация")) {
                buttonSwitchEntryMethods.setText("Вход");
                editTextPasswordAgain.setText("");
                editTextPassword.setText("");
                editTextPasswordAgain.setVisibility(View.GONE);
            } else {
                buttonSwitchEntryMethods.setText("Регистрация");
                editTextPasswordAgain.setText("");
                editTextPassword.setText("");
                editTextPasswordAgain.setVisibility(View.VISIBLE);
            }
        });

        editTextPasswordAgain.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Действия до изменения текста
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Действия при изменении текста
                if (s.toString().equals(editTextPassword.getText().toString())) {
                    sendDataToActivity(s.toString(), true);
                } else {
                    editTextPasswordAgain.setError("Пароли не совпадают");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Действия после изменения текста
            }
        });

        editTextPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Действия до изменения текста
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Действия при изменении текста
                sendDataToActivity(s.toString(), false);
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Действия после изменения текста
            }
        });

        return view;
    }
}