package com.example.lifeline.hello;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.lifeline.R;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;

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

    private MaterialAutoCompleteTextView autoCompleteBloodType;
    private MaterialAutoCompleteTextView autoCompleteRhesusFactor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blood_type, container, false);

        autoCompleteBloodType = view.findViewById(R.id.autoCompleteBloodType);
        autoCompleteRhesusFactor = view.findViewById(R.id.autoCompleteRhesusFactor);

        autoCompleteBloodType.setOnItemClickListener((parent, view1, position, id) -> {
            onTextChanged();
        });

        autoCompleteRhesusFactor.setOnItemClickListener((parent, view12, position, id) -> {
            onTextChanged();
        });

        return view;
    }

    private void onTextChanged() {
        String bloodType = autoCompleteBloodType.getText().toString();
        String rhesusFactor = autoCompleteRhesusFactor.getText().toString();
        String result = bloodType + " " + rhesusFactor;
        sendDataToActivity(result);
    }
}
