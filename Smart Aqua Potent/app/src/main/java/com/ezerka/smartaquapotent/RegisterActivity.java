package com.ezerka.smartaquapotent;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "Sign UP Activity";
    private EditText mRegisterCanId;
    private EditText mRegisterEmailId;
    private EditText mRegisterPassId, mRegisterRePassId;

    private Button mRegisterButtonId;

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
    }

    public void registerFunction(View view) {
        signUp();
    }

    public void signUp() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        mRegisterButtonId.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(RegisterActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        String can = mRegisterCanId.getText().toString();
        String email = mRegisterEmailId.getText().toString();
        String password = mRegisterPassId.getText().toString();
        String reEnterPassword = mRegisterRePassId.getText().toString();

        // TODO: Implement your own signup logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        onSignupSuccess();
                        // onSignupFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }


    public void onSignupSuccess() {
        mRegisterButtonId.setEnabled(true);
        setResult(RESULT_OK, null);
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

        if (can.isEmpty() || can.length() < 3) {
            mRegisterCanId.setError("at least 3 characters");
            valid = false;
        } else {
            mRegisterCanId.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mRegisterEmailId.setError("enter a valid email address");
            valid = false;
        } else {
            mRegisterEmailId.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            mRegisterPassId.setError("between 4 and 10 alphanumeric characters");
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
}
