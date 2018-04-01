package com.ezerka.smartaquapotent;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ezerka.smartaquapotent.user_activities.UserMenuActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    private EditText mLoginEmailId;
    private EditText mLoginPassId;
    private Button mLoginButtonId;

    private ProgressDialog progressDialog = null;

    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        firebaseSetupAuth();
    }

    private void initView() {
        mLoginEmailId = (EditText) findViewById(R.id.id_login_email);
        mLoginPassId = (EditText) findViewById(R.id.id_login_pass);
        mLoginButtonId = (Button) findViewById(R.id.id_login_button);
    }


    public void loginFunction(View view) {
        login();
    }

    public void login() {

        Log.d(TAG, "Login");

        if (!validate()) {
            return;
        }

        mLoginButtonId.setEnabled(false);

        final ProgressDialog pd = new ProgressDialog(LoginActivity.this);
        pd.setMessage("Authenticating...");
        pd.setIndeterminate(true);
        pd.show();
        pd.setCancelable(false);

        // TODO: I am going to write the logic here for the Connection.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        loginTheUser();
                        // onLoginFailed();
                        pd.dismiss();
                    }
                }, 6000);
    }

    public void loginTheUser() {
        String email = mLoginEmailId.getText().toString();
        String password = mLoginPassId.getText().toString();

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d(TAG, "Logged In");
                openUserLoginPage();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "Not Logged In");
                Toast.makeText(LoginActivity.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                onLoginFailed();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void openUserLoginPage() {
        startActivity(new Intent(this, UserMenuActivity.class));
        finish();

    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login Failed", Toast.LENGTH_LONG).show();
        mLoginButtonId.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = mLoginEmailId.getText().toString();
        String password = mLoginPassId.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mLoginEmailId.setError("Enter A Valid Email Address");
            valid = false;
        } else {
            mLoginEmailId.setError(null);
        }

        if (password.isEmpty() || password.length() < 4) {
            mLoginPassId.setError("Enter A Correct Password");
            valid = false;
        } else {
            mLoginPassId.setError(null);
        }

        return valid;
    }

    public void openStaffLoginPage(View view) {
        Intent staffLogin = new Intent(this, StaffLoginActivity.class);
        startActivity(staffLogin);
        finish();
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    public void openRegisterPage(View view) {

        Intent intent_register = new Intent(this, RegisterActivity.class);
        startActivity(intent_register);
        finish();
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    private void firebaseSetupAuth() {
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.d(TAG, "Signed In");
                } else {
                    Log.d(TAG, "Signed Out");
                }
            }
        };

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseAuth.getInstance().addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthStateListener != null) {
            FirebaseAuth.getInstance().removeAuthStateListener(mAuthStateListener);
        }
    }
}
