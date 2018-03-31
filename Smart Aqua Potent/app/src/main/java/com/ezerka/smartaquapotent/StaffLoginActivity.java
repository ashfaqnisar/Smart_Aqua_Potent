package com.ezerka.smartaquapotent;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ezerka.smartaquapotent.staff_activities.StaffMenuActivity;
import com.ezerka.smartaquapotent.user_activities.UserMenuActivity;

public class StaffLoginActivity extends AppCompatActivity {

    private static final String TAG = "StaffLoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    private EditText mLoginEmailId;
    private EditText mLoginPassId;
    private Button mLoginButtonId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_login);
        initView();

    }

    private void initView() {
        mLoginEmailId = (EditText) findViewById(R.id.id_login_email);
        mLoginPassId = (EditText) findViewById(R.id.id_login_pass);
        mLoginButtonId = (Button) findViewById(R.id.id_login_button);
    }

    public void staffLoginFunction(View view) {
        staffLogin();
    }

    public void staffLogin() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
        }

        mLoginButtonId.setEnabled(false);

        final ProgressDialog pd = new ProgressDialog(StaffLoginActivity.this);
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
                        onStaffLoginSuccess();
                        // onLoginFailed();
                        pd.dismiss();
                    }
                }, 3000);
    }


    public void onStaffLoginSuccess() {
        startActivity(new Intent(this, StaffMenuActivity.class));

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

    public void openLoginPage(View view) {
        Intent intent_login = new Intent(this, LoginActivity.class);
        startActivity(intent_login);
        finish();
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }
}
