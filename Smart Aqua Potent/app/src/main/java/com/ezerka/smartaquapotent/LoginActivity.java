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
    }
}
