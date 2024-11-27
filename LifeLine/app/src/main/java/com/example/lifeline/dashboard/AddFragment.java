package com.example.lifeline.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.lifeline.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class AddFragment extends BottomSheetDialogFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add, container, false);

        LinearLayout standardBottomSheet = view.findViewById(R.id.standard_bottom_sheet);
        BottomSheetBehavior<LinearLayout> standardBottomSheetBehavior = BottomSheetBehavior.from(standardBottomSheet);

        // Установить начальное состояние
        standardBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

        standardBottomSheetBehavior.setPeekHeight(BottomSheetBehavior.PEEK_HEIGHT_AUTO);

        return view;
    }

    public static final String TAG = "AddFragment";
}
