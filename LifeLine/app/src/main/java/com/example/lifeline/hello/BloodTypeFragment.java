package com.example.lifeline.hello;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.lifeline.R;
import com.google.android.material.textfield.TextInputLayout;

public class BloodTypeFragment extends Fragment {

    private BloodTypeFragment.OnFragmentInteractionListener mListener;

    public BloodTypeFragment() {
        // Required empty public constructor
    }

    public static BloodTypeFragment newInstance(BloodTypeFragment.OnFragmentInteractionListener listener) {
        BloodTypeFragment fragment = new BloodTypeFragment();
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
            Log.d("BloodTypeFragment", "mListener is null");
        }
    }

    private TextInputLayout textInputLayoutBloodType;
    private TextInputLayout textInputLayoutRhesusFactor;
    private TextView resulBloodType;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blood_type, container, false);

        textInputLayoutBloodType = view.findViewById(R.id.textInputLayoutBloodType);
        textInputLayoutRhesusFactor = view.findViewById(R.id.textInputLayoutRhesusFactor);
        resulBloodType = view.findViewById(R.id.resulBloodType);

//        textInputLayoutBloodType.setOnClickListener(v -> {
//            if (!textInputLayoutRhesusFactor.getEditText().getText().toString().isEmpty() || !textInputLayoutBloodType.getEditText().getText().toString().isEmpty()) {
//                String bloodType = textInputLayoutBloodType.getEditText().getText().toString();
//                String rhesusFactor = textInputLayoutRhesusFactor.getEditText().getText().toString();
//                String result = bloodType + " " + rhesusFactor;
//                resulBloodType.setText(result);
//                sendDataToActivity(result);
//                Log.d("BloodTypeFragment", "onTextChanged: " + result);
//            }
//        });
//        textInputLayoutRhesusFactor.setOnClickListener(v -> {
//            if (!textInputLayoutRhesusFactor.getEditText().getText().toString().isEmpty() || !textInputLayoutBloodType.getEditText().getText().toString().isEmpty()) {
//                String bloodType = textInputLayoutBloodType.getEditText().getText().toString();
//                String rhesusFactor = textInputLayoutRhesusFactor.getEditText().getText().toString();
//                String result = bloodType + " " + rhesusFactor;
//                resulBloodType.setText(result);
//                sendDataToActivity(result);
//                Log.d("BloodTypeFragment", "onTextChanged: " + result);
//            }
//        });

        return view;
    }
}