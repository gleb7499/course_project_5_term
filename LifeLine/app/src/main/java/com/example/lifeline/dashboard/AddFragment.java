package com.example.lifeline.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.lifeline.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddFragment extends BottomSheetDialogFragment {
    private MaterialDatePicker<Long> datePicker;
    private TextInputEditText textFieldDate;
    private Button buttonAddDonation;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add, container, false);

        LinearLayout standardBottomSheet = view.findViewById(R.id.standard_bottom_sheet);
        BottomSheetBehavior<LinearLayout> standardBottomSheetBehavior = BottomSheetBehavior.from(standardBottomSheet);

        // Установить начальное состояние
        standardBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

        standardBottomSheetBehavior.setPeekHeight(BottomSheetBehavior.PEEK_HEIGHT_AUTO);

        datePicker = MaterialDatePicker.Builder.datePicker()
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .setTitleText("Дата кровосдачи")
                .build();

        datePicker.addOnPositiveButtonClickListener(selection -> {
            // Получить выбранную дату в миллисекундах
            Long selectedDateInMillis = datePicker.getSelection();

            // Создать объект Date из миллисекунд
            Date date = new Date(selectedDateInMillis);

            // Создать форматтер для преобразования даты в строку нужного формата
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

            // Преобразовать дату в строку
            String formattedDate = dateFormat.format(date);

            // Установить отформатированную дату в текстовое поле
            textFieldDate.setFocusable(true);
            textFieldDate.setText(formattedDate);
            textFieldDate.setFocusable(false);
        });

        textFieldDate = view.findViewById(R.id.textInputEditTextDate);
        textFieldDate.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                textFieldDate.setFocusable(false);
                datePicker.show(getChildFragmentManager(), "datePicker");
            }
        });
        textFieldDate.setOnClickListener(v -> {
            textFieldDate.setFocusable(false);
            datePicker.show(getChildFragmentManager(), "datePicker");
        });

        buttonAddDonation = view.findViewById(R.id.buttonAddDonation);

        return view;
    }

    public static final String TAG = "AddFragment";
}
