package com.ezerka.smartaquapotent;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ezerka.smartaquapotent.user_activities.UserMenuActivity;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    private EditText mLoginEmailId;
    private EditText mLoginPassId;
    private Button mLoginButtonId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        mLoginEmailId = (EditText) findViewById(R.id.id_login_email);
        mLoginPassId = (EditText) findViewById(R.id.id_login_pass);
        mLoginButtonId = (Button) findViewById(R.id.id_login_button);
    }


    public void openRegisterPage(View view) {

        Intent intent_register = new Intent(this, RegisterActivity.class);
        startActivity(intent_register);
        finish();
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    public void loginFunction(View view) {
        login();
    }

    public void login() {

        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
        }

        mLoginButtonId.setEnabled(false);

        final ProgressDialog pd = new ProgressDialog(LoginActivity.this);
        pd.setMessage("Authenticating...");
        pd.setIndeterminate(true);
        pd.show();
        pd.setCancelable(false);

        String email = mLoginEmailId.getText().toString();
        String password = mLoginPassId.getText().toString();

        // TODO: I am going to write the logic here for the Connection.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        onUserLoginSuccess();
                        // onLoginFailed();
                        pd.dismiss();
                    }
                }, 3000);
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

    public void onUserLoginSuccess() {
        startActivity(new Intent(this, UserMenuActivity.class));

        mLoginButtonId.setEnabled(true);

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
}
