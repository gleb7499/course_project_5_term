package com.example.lifeline.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.lifeline.R;
import com.example.lifeline.database.Database;
import com.example.lifeline.database.DatabaseManager;
import com.example.lifeline.models.Donations;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddFragment extends BottomSheetDialogFragment {
    private MaterialDatePicker<Long> datePicker;
    private TextInputEditText textFieldDate;
    private TextInputLayout textInputLayoutDate;
    private AutoCompleteTextView autoCompleteTextViewDonationType;
    private String donationType;
    private TextInputEditText textInputEditTextQuantity;
    private Button buttonAddDonation;
    private Database database;
    private Donations donation;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add, container, false);

        database = DatabaseManager.getDatabase();

        donation = new Donations();

        // Установить начальное состояние
        LinearLayout standardBottomSheet = view.findViewById(R.id.standard_bottom_sheet);
        BottomSheetBehavior<LinearLayout> standardBottomSheetBehavior = BottomSheetBehavior.from(standardBottomSheet);
        standardBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        standardBottomSheetBehavior.setPeekHeight(BottomSheetBehavior.PEEK_HEIGHT_AUTO);

        BottomSheetDialog modalBottomSheet = (BottomSheetDialog) getDialog();
        BottomSheetBehavior modalBottomSheetBehavior = modalBottomSheet.getBehavior();

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
                textInputLayoutDate.setError(null);
                datePicker.show(getChildFragmentManager(), "datePicker");
            }
        });
        textFieldDate.setOnClickListener(v -> {
            textFieldDate.setFocusable(false);
            textInputLayoutDate.setError(null);
            datePicker.show(getChildFragmentManager(), "datePicker");
        });

        textInputLayoutDate = view.findViewById(R.id.textInputLayoutDate);

        autoCompleteTextViewDonationType = view.findViewById(R.id.autoCompleteTextViewDonationType);
        autoCompleteTextViewDonationType.setOnItemClickListener((parent, view1, position, id) -> {
            donationType = autoCompleteTextViewDonationType.getText().toString();
        });

        textInputEditTextQuantity = view.findViewById(R.id.textInputEditTextQuantity);

        buttonAddDonation = view.findViewById(R.id.buttonAddDonation);
        buttonAddDonation.setOnClickListener(v -> {

            donation.setUserFirebaseID(FirebaseAuth.getInstance().getCurrentUser().getUid());
            donation.setDonationDate(textFieldDate.getText().toString());
            donation.setDonationTypeID(database.getDonationTypeID(donationType));
            donation.setQuantity(textInputEditTextQuantity.getText().toString());

            if (!database.setDonation(donation)) {
                textInputLayoutDate.setError("Такая запись уже существует");
            } else {
                modalBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            }
        });

        return view;
    }

    public static final String TAG = "AddFragment";
}
