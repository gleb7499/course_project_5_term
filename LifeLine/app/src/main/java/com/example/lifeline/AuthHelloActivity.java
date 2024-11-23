package com.example.lifeline;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.lifeline.authentication.EmailFragment;
import com.example.lifeline.authentication.SignFragment;
import com.example.lifeline.hello.HelloFragment;
import com.example.lifeline.hello.ThanksFragment;
import com.example.lifeline.hello.YouIsHeroFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public abstract class AuthHelloActivity extends FragmentActivity {
    private ImageButton nextButton;
    private ProgressBar progressBar;
    private int counterFragments = 0;
    private FirebaseAuth mAuth;
    private String email;
    private String password;
    private boolean isRegister;
    private ArrayList<Fragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_hello);

        nextButton = findViewById(R.id.imageButtonNext);
        setMargin(nextButton);

        progressBar = findViewById(R.id.progressBar);
        setMargin(progressBar);

        mAuth = FirebaseAuth.getInstance();

        nextButton.setOnClickListener(v -> {
            nextButtonClick();
        });

        if (savedInstanceState == null) {
            nextFragment(fragments.get(counterFragments++));
        }
    }

    private void nextButtonClick() {
        if (counterFragments == fragments.size()) {
            if (password.isEmpty()) {
                Toast.makeText(AuthHelloActivity.this, "Введите пароль", Toast.LENGTH_SHORT).show();
            }
            else if (isRegister) {
                registerUser();
            } else {
                loginUser();
            }
        } else if (counterFragments == fragments.size() - 1 && (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches())) {
            Toast.makeText(AuthHelloActivity.this, "Неверный email", Toast.LENGTH_SHORT).show();
        } else {
            nextFragment(fragments.get(counterFragments++));
        }
    }

    protected void addFragmentsInList(boolean isHelloFragment) {
        fragments = new ArrayList<>();
        if (isHelloFragment) {
            fragments.add(new HelloFragment());
            fragments.add(new YouIsHeroFragment());
            fragments.add(new ThanksFragment());
        }
        fragments.add(EmailFragment.newInstance((data) -> {
            email = data;
        }));
        fragments.add(SignFragment.newInstance((data, isRegister) -> {
            password = data;
            AuthHelloActivity.this.isRegister = isRegister;
        }));
    }

    private void nextFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left).replace(R.id.Frame, fragment).commit();
    }

    private void setMargin(View view) {
        ViewCompat.setOnApplyWindowInsetsListener(view, (v, windowInsets) -> {
            Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());
            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            layoutParams.bottomMargin = insets.bottom + 35;
            v.setLayoutParams(layoutParams);
            return WindowInsetsCompat.CONSUMED;
        });
    }

    private void loginUser() {
        progressBar.setVisibility(View.VISIBLE);
        nextButton.setVisibility(View.GONE);
        // Логика для входа пользователя
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                // Вход успешен
                FirebaseUser user = mAuth.getCurrentUser();
                Toast.makeText(AuthHelloActivity.this, "Вход успешен", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(AuthHelloActivity.this, DashboardActivity.class);
                startActivity(intent);
                finish();
            } else {
                // Ошибка входа
                Exception exception = task.getException();
                if (exception instanceof FirebaseAuthInvalidCredentialsException) {
                    String errorCode = ((FirebaseAuthInvalidCredentialsException) exception).getErrorCode();
                    if (errorCode.equals("ERROR_USER_NOT_FOUND")) {
                        // Пользователь с таким email не найден
                        Toast.makeText(AuthHelloActivity.this, "Пользователь с таким email не найден", Toast.LENGTH_LONG).show();
                    } else {
                        // Пароль неверен
                        Toast.makeText(AuthHelloActivity.this, "Пароль неверен", Toast.LENGTH_LONG).show();
                    }
                } else {
                    // Другая ошибка
                    Toast.makeText(AuthHelloActivity.this, "Произошла ошибка", Toast.LENGTH_LONG).show();
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
                FirebaseUser user = mAuth.getCurrentUser();
                Toast.makeText(AuthHelloActivity.this, "Регистрация успешна", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(AuthHelloActivity.this, DashboardActivity.class);
                startActivity(intent);
                finish();
            } else {
                // Ошибка регистрации
                Exception exception = task.getException();
                if (exception instanceof FirebaseAuthWeakPasswordException) {
                    // Пароль слишком слабый
                    Toast.makeText(AuthHelloActivity.this, "Пароль слишком слабый", Toast.LENGTH_LONG).show();
                } else if (exception instanceof FirebaseAuthInvalidCredentialsException) {
                    // Некорректные учетные данные
                    Toast.makeText(AuthHelloActivity.this, "Некорректные учетные данные", Toast.LENGTH_LONG).show();
                } else if (exception instanceof FirebaseAuthUserCollisionException) {
                    // Пользователь с таким email уже существует
                    Toast.makeText(AuthHelloActivity.this, "Пользователь с таким email уже существует", Toast.LENGTH_LONG).show();
                } else {
                    // Другая ошибка
                    Toast.makeText(AuthHelloActivity.this, "Произошла ошибка", Toast.LENGTH_LONG).show();
                }
                progressBar.setVisibility(View.GONE);
                nextButton.setVisibility(View.VISIBLE);
            }
        });
    }
}