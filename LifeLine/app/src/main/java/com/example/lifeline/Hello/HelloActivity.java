package com.example.lifeline.Hello;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.example.lifeline.Authantication.EmailFragment;
import com.example.lifeline.Authantication.SignFragment;
import com.example.lifeline.MainActivity;
import com.example.lifeline.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class HelloActivity extends AppCompatActivity {

    private ImageButton nextButton;
    private ProgressBar progressBar;
    private int counter_fragments = 0;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private String email, password;
    private boolean isRegister;
    private ArrayList<Fragment> fragments;

    private void setMargin(View view) {
        ViewCompat.setOnApplyWindowInsetsListener(view, (v, windowInsets) -> {
            Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());
            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            layoutParams.bottomMargin = insets.bottom + 35;
            v.setLayoutParams(layoutParams);
            return WindowInsetsCompat.CONSUMED;
        });
    }

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

        fragments = new ArrayList<>();
        fragments.add(new HelloFragment());
        fragments.add(new YouIsHeroFragment());
        fragments.add(new ThanksFragment());
        fragments.add(EmailFragment.newInstance(new EmailFragment.OnFragmentInteractionListener() {
            @Override
            public void onFragmentInteraction(String data) {
                email = data;
            }
        }));
        fragments.add(SignFragment.newInstance(new SignFragment.OnFragmentInteractionListener() {
            @Override
            public void onFragmentInteraction(String data, boolean isRegister) {
                password = data;
                HelloActivity.this.isRegister = isRegister;
            }
        }));

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (counter_fragments == fragments.size()) {
                    if (password != null) {
                        progressBar.setVisibility(View.VISIBLE);
                        nextButton.setVisibility(View.GONE);
                        if (isRegister) {
                            registerUser();
                        } else {
                            loginUser();
                        }
                    }
                } else {
                    if (counter_fragments == fragments.size() - 1) {
                        if (email != null && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                            nextFragment(fragments.get(counter_fragments++));
                        } else {
                            Toast.makeText(HelloActivity.this, "Неверный email", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        nextFragment(fragments.get(counter_fragments++));
                    }
                }
            }
        });

        if (savedInstanceState == null) {
            nextFragment(fragments.get(counter_fragments++));
        }
    }

    private void nextFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left)
                .replace(R.id.Frame, fragment)
                .commit();
    }

    private void loginUser() {
        // Логика для входа пользователя
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Вход успешен
                    user = mAuth.getCurrentUser();
                    Toast.makeText(HelloActivity.this, "Вход успешен", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(HelloActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    // Ошибка входа
                    Exception exception = task.getException();
                    if (exception instanceof FirebaseAuthInvalidCredentialsException) {
                        String errorCode = ((FirebaseAuthInvalidCredentialsException) exception).getErrorCode();
                        if (errorCode.equals("ERROR_USER_NOT_FOUND")) {
                            // Пользователь с таким email не найден
                            Toast.makeText(HelloActivity.this, "Пользователь с таким email не найден", Toast.LENGTH_LONG).show();
                        } else {
                            // Пароль неверен
                            Toast.makeText(HelloActivity.this, "Пароль неверен", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        // Другая ошибка
                        Toast.makeText(HelloActivity.this, "Произошла ошибка", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    private void registerUser() {
        // Логика для регистрации пользователя
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Регистрация успешна
                    user = mAuth.getCurrentUser();
                    Toast.makeText(HelloActivity.this, "Регистрация успешна", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(HelloActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    // Ошибка регистрации
                    Exception exception = task.getException();
                    if (exception instanceof FirebaseAuthWeakPasswordException) {
                        // Пароль слишком слабый
                        Toast.makeText(HelloActivity.this, "Пароль слишком слабый", Toast.LENGTH_LONG).show();
                    } else if (exception instanceof FirebaseAuthInvalidCredentialsException) {
                        // Некорректные учетные данные
                        Toast.makeText(HelloActivity.this, "Некорректные учетные данные", Toast.LENGTH_LONG).show();
                    } else if (exception instanceof FirebaseAuthUserCollisionException) {
                        // Пользователь с таким email уже существует
                        Toast.makeText(HelloActivity.this, "Пользователь с таким email уже существует", Toast.LENGTH_LONG).show();
                    } else {
                        // Другая ошибка
                        Toast.makeText(HelloActivity.this, "Произошла ошибка", Toast.LENGTH_LONG).show();
                    }
                }
                progressBar.setVisibility(View.GONE);
                nextButton.setVisibility(View.VISIBLE);
            }
        });
    }
}