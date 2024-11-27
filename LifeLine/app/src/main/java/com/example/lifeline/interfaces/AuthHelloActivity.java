package com.example.lifeline.interfaces;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.lifeline.R;
import com.example.lifeline.authentication.EmailFragment;
import com.example.lifeline.authentication.SignFragment;
import com.example.lifeline.hello.BloodTypeFragment;
import com.example.lifeline.hello.HelloFragment;
import com.example.lifeline.hello.ThanksFragment;
import com.example.lifeline.hello.UserFragment;
import com.example.lifeline.hello.YouIsHeroFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class AuthHelloActivity extends FragmentActivity {
    private FloatingActionButton nextButton;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    private String email;
    private String password;
    private String user;
    private String bloodType;
    private boolean isRegister = false;
    private ArrayList<Fragment> fragments;
    private int counterFragments = 0;
    private ArrayList<Fragment> fragmentsToAcquaint;
    private int counterForAcquaint = 0;
    private boolean isAcquainted = false;
    boolean isFirstForNextButton = true;
    boolean isFirstForProgressBar = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_hello);

        nextButton = findViewById(R.id.buttonNext);
        AtomicInteger currentBottomForNextButton = new AtomicInteger();
        ViewCompat.setOnApplyWindowInsetsListener(nextButton, (v, windowInsets) -> {
            Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());
            ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            // Сохраняем текущие отступы из XML
            if (isFirstForNextButton) {
                currentBottomForNextButton.set(mlp.bottomMargin);
                isFirstForNextButton = false;
            }
            // Добавляем системные отступы к текущим отступам
            mlp.bottomMargin = currentBottomForNextButton.get() + insets.bottom;
            v.setLayoutParams(mlp);
            return WindowInsetsCompat.CONSUMED;
        });

        progressBar = findViewById(R.id.progressBar);
        AtomicInteger currentBottomForProgressBar = new AtomicInteger();
        ViewCompat.setOnApplyWindowInsetsListener(progressBar, (v, windowInsets) -> {
            Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());
            ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            // Сохраняем текущие отступы из XML
            if (isFirstForProgressBar) {
                currentBottomForProgressBar.set(mlp.bottomMargin);
                isFirstForProgressBar = false;
            }
            // Добавляем системные отступы к текущим отступам
            mlp.bottomMargin = currentBottomForProgressBar.get() + insets.bottom;
            v.setLayoutParams(mlp);
            return WindowInsetsCompat.CONSUMED;
        });

        mAuth = FirebaseAuth.getInstance();

        nextButton.setOnClickListener(v -> nextButtonClick());

        if (savedInstanceState == null) {
            nextFragment(fragments.get(counterFragments++));
        }
    }

    private void setAcquainted() {
        fragmentsToAcquaint.add(UserFragment.newInstance(data -> user = data));
        fragmentsToAcquaint.add(BloodTypeFragment.newInstance(data -> bloodType = data));
    }

    private void nextButtonClick() {
        if (!isAcquainted) {
            if (counterFragments == fragments.size()) {
                if (password == null || password.isEmpty()) {
                    Snackbar.make(nextButton, "Введите пароль", Snackbar.LENGTH_SHORT).show();
                } else if (isRegister) {
                    registerUser();
                    isAcquainted = true;
                    fragmentsToAcquaint = new ArrayList<>();
                    setAcquainted();
                } else {
                    loginUser();
                }
            } else if (counterFragments == fragments.size() - 1 && (email == null || email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches())) {
                Snackbar.make(nextButton, "Неверный email", Snackbar.LENGTH_SHORT).show();
            } else {
                nextFragment(fragments.get(counterFragments++));
            }
        } else {
            nextFragment(fragmentsToAcquaint.get(counterForAcquaint++));
        }
    }

    protected void addFragmentsInList(boolean isHelloFragment) {
        fragments = new ArrayList<>();
        if (isHelloFragment) {
            fragments.add(new HelloFragment());
            fragments.add(new YouIsHeroFragment());
            fragments.add(new ThanksFragment());
        }
        fragments.add(EmailFragment.newInstance(data -> email = data));
        fragments.add(SignFragment.newInstance((data, isRegister) -> {
            password = data;
            this.isRegister = isRegister;
        }));
    }

    private void nextFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left).replace(R.id.Frame, fragment).commit();
    }

    private void loginUser() {
        progressBar.setVisibility(View.VISIBLE);
        nextButton.setVisibility(View.GONE);
        // Логика для входа пользователя
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                // Вход успешен
                Snackbar.make(nextButton, "Вход успешен", Snackbar.LENGTH_LONG).show();
                Intent intent = new Intent();
                setResult(Activity.RESULT_OK, intent);
                finish();
            } else {
                // Ошибка входа
                Exception exception = task.getException();
                if (exception instanceof FirebaseAuthInvalidCredentialsException) {
                    String errorCode = ((FirebaseAuthInvalidCredentialsException) exception).getErrorCode();
                    if (errorCode.equals("ERROR_USER_NOT_FOUND")) {
                        // Пользователь с таким email не найден
                        Snackbar.make(nextButton, "Пользователь с таким email не найден", Snackbar.LENGTH_LONG).show();
                    } else {
                        // Пароль неверен
                        Snackbar.make(nextButton, "Пароль неверен", Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    // Другая ошибка
                    Snackbar.make(nextButton, "Произошла ошибка", Snackbar.LENGTH_LONG).show();
                }
                progressBar.setVisibility(View.GONE);
                nextButton.setVisibility(View.VISIBLE);
            }
        });
    }

    private void registerUser() {
        progressBar.setVisibility(View.VISIBLE);
        nextButton.setVisibility(View.GONE);
        // Логика для регистрации пользователя
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                // Регистрация успешна
                Snackbar.make(nextButton, "Регистрация успешна", Snackbar.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
                nextButton.setVisibility(View.VISIBLE);
                nextFragment(fragmentsToAcquaint.get(counterForAcquaint++));
            } else {
                // Ошибка регистрации
                Exception exception = task.getException();
                if (exception instanceof FirebaseAuthWeakPasswordException) {
                    // Пароль слишком слабый
                    Snackbar.make(nextButton, "Пароль слишком слабый", Snackbar.LENGTH_LONG).show();
                } else if (exception instanceof FirebaseAuthInvalidCredentialsException) {
                    // Некорректные учетные данные
                    Snackbar.make(nextButton, "Некорректные учетные данные", Snackbar.LENGTH_LONG).show();
                } else if (exception instanceof FirebaseAuthUserCollisionException) {
                    // Пользователь с таким email уже существует
                    Snackbar.make(nextButton, "Пользователь с таким email уже существует", Snackbar.LENGTH_LONG).show();
                } else {
                    // Другая ошибка
                    Snackbar.make(nextButton, "Произошла ошибка", Snackbar.LENGTH_LONG).show();
                }
                progressBar.setVisibility(View.GONE);
                nextButton.setVisibility(View.VISIBLE);
            }
        });
    }
}