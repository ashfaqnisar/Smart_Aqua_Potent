package com.ezerka.smartaquapotent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void registerFunction(View view) {

    }

    public void openLoginPage(View view) {
        Intent intent_login = new Intent(this, LoginActivity.class);
        startActivity(intent_login);

    }
}
