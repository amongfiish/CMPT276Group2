package ca.accessarchitects.cmpt276group2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity
{

    private FirebaseAuth mAuth;
    private EditText emailField, passwordField;
    private Button signUpButton;
    private TextView backToLoginText;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();

        emailField = findViewById(R.id.emailField);
        passwordField = findViewById(R.id.passwordField);
        signUpButton = findViewById(R.id.signUpButton);
        backToLoginText = findViewById(R.id.backToLoginText);

        signUpButton.setOnClickListener(v -> {
            String email = emailField.getText().toString().trim();
            String password = passwordField.getText().toString().trim();

            if (email.isEmpty())
            {
                emailField.setError("Email is required");
                emailField.requestFocus();
                return;
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
            {
                emailField.setError("Please enter a valid email address");
                emailField.requestFocus();
                return;
            }

            if (password.isEmpty())
            {
                passwordField.setError("Password is required");
                passwordField.requestFocus();
                return;
            }

            String passwordError = validatePassword(password);
            if (passwordError != null)
            {
                passwordField.setError(passwordError);
                passwordField.requestFocus();
                return;
            }

            registerUser(email, password);
        });

        backToLoginText.setOnClickListener(v -> finish());
    }

    private String validatePassword(String password)
    {
        if (password.length() < 6)
        {
            return "Password must be at least 6 characters";
        }

        boolean hasUpper = false;
        boolean hasLower = false;
        boolean hasNum = false;
        boolean hasSpecial = false;

        for (int i = 0; i < password.length(); i++)
        {
            char c = password.charAt(i);
            if (Character.isUpperCase(c))
            {
                hasUpper = true;
            }
            else if (Character.isLowerCase(c))
            {
                hasLower = true;
            }
            else if (Character.isDigit(c))
            {
                hasNum = true;
            }
            else
            {
                hasSpecial = true;
            }
        }

        if (!hasUpper)
        {
            return "Password must include at least 1 uppercase letter";
        }
        if (!hasLower)
        {
            return "Password must include at least 1 lowercase letter";
        }
        if (!hasNum)
        {
            return "Password must include at least 1 number";
        }
        if (!hasSpecial)
        {
            return "Password must include at least 1 special character";
        }

        return null;
    }

    private void registerUser(String email, String password)
    {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful())
                    {
                        Toast.makeText(SignUpActivity.this, "Registration Successful!", Toast.LENGTH_SHORT).show();
                        navigateToMainActivity();
                    }
                    else
                    {
                        Toast.makeText(SignUpActivity.this, "Authentication failed: " +
                                task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void navigateToMainActivity()
    {
        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}