package com.example.lifeline.dashboard;

import static android.app.Activity.RESULT_OK;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
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
import com.example.lifeline.interfaces.OnFragmentInteractionListener;
import com.example.lifeline.models.Donations;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class AddViewFragment extends BottomSheetDialogFragment {
    public static final String TAG = "AddViewFragment";
    private static final int ADD = 0;
    private static final int UPDATE = 1;
    private static final int DELETE = 2;

    private View view;
    private BottomSheetBehavior modalBottomSheetBehavior;

    private MaterialDatePicker<Long> datePicker;
    private TextInputEditText textFieldDate;
    private TextInputLayout textInputLayoutDate;
    private AutoCompleteTextView autoCompleteTextViewDonationType;
    private String donationType;
    private TextInputEditText textInputEditTextQuantity;
    private Button buttonAddDonation;
    private Button buttonEditDonation;
    private Button buttonDeleteDonation;
    private Database database;
    private Donations donation;

    private OnFragmentInteractionListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            listener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (listener != null) {
            listener.onFragmentInteraction(RESULT_OK);
        }
        dismiss();
        listener = null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_view, container, false);

        database = DatabaseManager.getDatabase();

        donation = new Donations();

        textFieldDate = view.findViewById(R.id.textInputEditTextDate);
        autoCompleteTextViewDonationType = view.findViewById(R.id.autoCompleteTextViewDonationType);
        textInputEditTextQuantity = view.findViewById(R.id.textInputEditTextQuantity);
        buttonAddDonation = view.findViewById(R.id.buttonAddDonation);
        buttonEditDonation = view.findViewById(R.id.buttonEditDonation);
        buttonDeleteDonation = view.findViewById(R.id.buttonDeleteDonation);

        // Установить начальное состояние
        LinearLayout standardBottomSheet = view.findViewById(R.id.standard_bottom_sheet);
        BottomSheetBehavior<LinearLayout> standardBottomSheetBehavior = BottomSheetBehavior.from(standardBottomSheet);
        standardBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        standardBottomSheetBehavior.setPeekHeight(BottomSheetBehavior.PEEK_HEIGHT_AUTO);

        BottomSheetDialog modalBottomSheet = (BottomSheetDialog) getDialog();
        modalBottomSheetBehavior = modalBottomSheet.getBehavior();

        setDatePicker();
        setTextFieldDate();

        getExtraFromIntent();

        return view;
    }

    protected abstract void getExtraFromIntent();

    public void whatsFragment(@NonNull String date) {
        if (date.isEmpty()) {
            setDonationType();
            setAddNewDonation();
            buttonEditDonation.setVisibility(View.GONE);
            buttonDeleteDonation.setVisibility(View.GONE);
        } else {
            setDataDonation(date);
            setUpdateDonation();
            setDeleteDonation();
            buttonAddDonation.setVisibility(View.GONE);
        }
    }

    private void setDataDonation(String date) {
        donation = database.getDonationByDate(date, FirebaseAuth.getInstance().getCurrentUser().getUid());

        textFieldDate.setText(donation.getDonationDate());
        donationType = database.getDonationType(donation.getDonationTypeID());
        autoCompleteTextViewDonationType.setText(donationType);
        textInputEditTextQuantity.setText(donation.getQuantity());
    }

    private void setUpdateDonation() {
        buttonEditDonation.setVisibility(View.VISIBLE);
        buttonEditDonation.setOnClickListener(v -> editDonation(AddViewFragment.UPDATE));
    }

    private void setAddNewDonation() {
        buttonAddDonation.setVisibility(View.VISIBLE);
        buttonAddDonation.setOnClickListener(v -> editDonation(AddViewFragment.ADD));
    }

    private void setDeleteDonation() {
        buttonDeleteDonation.setVisibility(View.VISIBLE);
        String message = "Вы действительно хотите удалить запись о сдаче за " + donation.getDonationDate() + " число?";
        int startIndexDate = message.indexOf(donation.getDonationDate());
        int endIndexDate = startIndexDate + donation.getDonationDate().length();
        SpannableString spannableString = new SpannableString(message);
        spannableString.setSpan(new StyleSpan(Typeface.BOLD), startIndexDate, endIndexDate, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        buttonDeleteDonation.setOnClickListener(v -> new MaterialAlertDialogBuilder(requireContext())
                .setTitle("Вы уверены?")
                .setMessage(spannableString)
                .setNeutralButton("Отмена", (dialog, which) -> {
                })
                .setPositiveButton("Удалить", (dialog, which) -> editDonation(AddViewFragment.DELETE))
                .show()
        );
    }

    private void editDonation(int action) {
        donation.setUserFirebaseID(FirebaseAuth.getInstance().getCurrentUser().getUid());
        donation.setDonationDate(textFieldDate.getText().toString());
        donation.setDonationTypeID(database.getDonationTypeID(donationType));
        donation.setQuantity(textInputEditTextQuantity.getText().toString());
        if (action == ADD ? !database.addDonation(donation) : action == DELETE ? !database.deleteDonation(donation) : !database.updateDonation(donation)) {
            textInputLayoutDate.setError("Такая запись уже существует");
        } else {
            modalBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        }
    }

    private void setDonationType() {
        autoCompleteTextViewDonationType.setOnItemClickListener((parent, view1, position, id) -> {
            donationType = autoCompleteTextViewDonationType.getText().toString();
        });
    }

    private void setTextFieldDate() {
        textInputLayoutDate = view.findViewById(R.id.textInputLayoutDate);
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
    }

    private void setDatePicker() {
        datePicker = MaterialDatePicker.Builder.datePicker()
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .setTitleText("Дата сдачи")
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
    }
}
