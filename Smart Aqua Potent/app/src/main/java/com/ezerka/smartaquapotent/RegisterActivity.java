package com.ezerka.smartaquapotent;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ezerka.smartaquapotent.user_activities.UserMenuActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import com.ezerka.smartaquapotent.staff_activities.StaffMenuActivity;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "Sign UP Activity";

    private EditText mRegisterCanId;
    private EditText mRegisterEmailId;
    private EditText mRegisterPassId, mRegisterRePassId;

    private Button mRegisterButtonId;

    private final ProgressDialog progressDialog = null;
    private ProgressBar progressBar;
    private String can, email, password, reEnterPassword;

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
    }

    public void init() {
        mRegisterCanId = (EditText) findViewById(R.id.id_register_can);
        mRegisterEmailId = (EditText) findViewById(R.id.id_register_email);
        mRegisterPassId = (EditText) findViewById(R.id.id_register_pass);
        mRegisterRePassId = (EditText) findViewById(R.id.id_register_re_pass);

        mRegisterButtonId = (Button) findViewById(R.id.id_register_button);
        mRegisterButtonId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp();
            }
        });

        mDatabase = FirebaseDatabase.getInstance().getReference();

        can = mRegisterCanId.getText().toString();
        email = mRegisterEmailId.getText().toString();
        password = mRegisterPassId.getText().toString();
        reEnterPassword = mRegisterRePassId.getText().toString();
    }

    public void signUp() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            return;
        }

        mRegisterButtonId.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(RegisterActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        can = mRegisterCanId.getText().toString();
        email = mRegisterEmailId.getText().toString();
        password = mRegisterPassId.getText().toString();
        reEnterPassword = mRegisterRePassId.getText().toString();

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        can = mRegisterCanId.getText().toString();
                        email = mRegisterEmailId.getText().toString();
                        password = mRegisterPassId.getText().toString();
                        reEnterPassword = mRegisterRePassId.getText().toString();

                        registerTheUser(email, password);

                        progressDialog.dismiss();
                    }
                }, 6000);
    }

    public void registerTheUser(String p_email, String p_pass) {

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(p_email, p_pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d(TAG, "Oncomplete");
                if (task.isSuccessful()) {
                    Log.d(TAG, "AuthState: " + FirebaseAuth.getInstance().getCurrentUser().getUid());
                    FirebaseAuth.getInstance().signOut();
                    openUsersMenuPage();
                } else {
                    Log.d(TAG, "onComplete: Error");
                    Toast.makeText(RegisterActivity.this, "Unable To Register", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void openUsersMenuPage() {
        Intent openUsers = new Intent(this, UserMenuActivity.class);
        startActivity(openUsers);
        finish();
    }


    public void onSignupSuccess() {
        mRegisterButtonId.setEnabled(true);
        startActivity(new Intent(this, StaffMenuActivity.class));
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        mRegisterButtonId.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String can = mRegisterCanId.getText().toString();
        String email = mRegisterEmailId.getText().toString();
        String password = mRegisterPassId.getText().toString();
        String reEnterPassword = mRegisterRePassId.getText().toString();

        if (can.isEmpty() || can.length() < 9) {
            mRegisterCanId.setError("Enter A Valid CAN Number");
            valid = false;
        } else {
            mRegisterCanId.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mRegisterEmailId.setError("Enter A Valid Email Address");
            valid = false;
        } else {
            mRegisterEmailId.setError(null);
        }

        if (password.isEmpty() || password.length() < 4) {
            mRegisterPassId.setError("More than 4 Characters");
            valid = false;
        } else {
            mRegisterRePassId.setError(null);
        }

        if (reEnterPassword.isEmpty() || reEnterPassword.length() < 4 || reEnterPassword.length() > 10 || !(reEnterPassword.equals(password))) {
            mRegisterRePassId.setError("Password's Do not match");
            valid = false;
        } else {
            mRegisterRePassId.setError(null);
        }

        return valid;
    }


    public void openLoginPage(View view) {
        Intent intent_login = new Intent(this, LoginActivity.class);
        startActivity(intent_login);
        finish();
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    private void hideSoftKeyboard() {
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }
}
