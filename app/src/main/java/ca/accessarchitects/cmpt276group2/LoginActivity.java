package ca.accessarchitects.cmpt276group2;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity
{

    private FirebaseAuth mAuth;
    private EditText emailField, passwordField;
    private Button loginButton;
    private TextView forgotPassword;
    private TextView goToSignUpText;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        emailField = findViewById(R.id.loginEmailField);
        passwordField = findViewById(R.id.loginPasswordField);
        loginButton = findViewById(R.id.loginButton);
        forgotPassword = findViewById(R.id.forgotPassword);
        goToSignUpText = findViewById(R.id.goToSignUpText);

        loginButton.setOnClickListener(v -> {
            String email = emailField.getText().toString().trim();
            String password = passwordField.getText().toString().trim();

            if (!validateEmailField(email)) {
                return;
            }

            if (password.isEmpty()) {
                passwordField.setError("Password is required");
                passwordField.requestFocus();
                return;
            }

            loginUser(email, password);
        });

        forgotPassword.setOnClickListener(v -> {
            String enteredEmail = emailField.getText().toString().trim();
            if (!enteredEmail.isEmpty()) {
                sendPasswordReset(enteredEmail);
            } else {
                resetPassword();
            }
        });

        goToSignUpText.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
        });

        forgotPassword.setOnClickListener(v -> {
            String enteredEmail = emailField.getText().toString().trim();
            if (!enteredEmail.isEmpty()) {
                if (!validateEmailField(enteredEmail)) {
                    return;
                }
                sendPasswordReset(enteredEmail);
            } else {
                resetPassword();
            }
        });
    }

    private void loginUser(String email, String password)
    {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(LoginActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                        navigateToMainActivity();
                    } else {
                        Toast.makeText(LoginActivity.this, "Authentication failed: " +
                                task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void navigateToMainActivity()
    {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void resetPassword()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Reset Password");
        builder.setMessage("Enter the email address associated with your account.");

        final EditText resetEmailInput = new EditText(this);
        resetEmailInput.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        resetEmailInput.setHint("Email");
        builder.setView(resetEmailInput);

        builder.setPositiveButton("Send Link", (dialog, which) -> {
            String email = resetEmailInput.getText().toString().trim();

            if (email.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Please enter an email address", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(LoginActivity.this, "Please enter a valid email address", Toast.LENGTH_SHORT).show();
                return;
            }

            sendPasswordReset(email);
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.show();
    }

    private void sendPasswordReset(String email)
    {
        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(
                                LoginActivity.this,
                                "Password reset email sent. Check your inbox (and spam folder).",
                                Toast.LENGTH_LONG
                        ).show();
                    } else {
                        String errorMessage = (task.getException() != null)
                                ? task.getException().getMessage()
                                : "Could not send reset email";
                        Toast.makeText(LoginActivity.this, "Error: " + errorMessage, Toast.LENGTH_LONG).show();
                    }
                });
    }

    private boolean validateEmailField(String email)
    {
        if (email.isEmpty())
        {
            emailField.setError("Email is required");
            emailField.requestFocus();
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            emailField.setError("Please enter a valid email address");
            emailField.requestFocus();
            return false;
        }
        return true;
    }
}