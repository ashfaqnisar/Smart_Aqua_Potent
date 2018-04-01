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

import com.ezerka.smartaquapotent.staff_activities.StaffMenuActivity;
import com.ezerka.smartaquapotent.user_activities.UserMenuActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class StaffLoginActivity extends AppCompatActivity {

    private static final String TAG = "StaffLoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    private EditText mLoginEmailId;
    private EditText mLoginPassId;
    private Button mLoginButtonId;

    private ProgressDialog progressDialog = null;

    private FirebaseAuth.AuthStateListener mAuthStateList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        firebaseSetupAuth();
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
            return;
        }

        mLoginButtonId.setEnabled(false);

        final ProgressDialog pd = new ProgressDialog(StaffLoginActivity.this);
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
                openStaffMenuPage();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "Not Logged In");
                Toast.makeText(StaffLoginActivity.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                onStaffLoginFailed();
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

    public void openStaffMenuPage() {
        startActivity(new Intent(this, StaffMenuActivity.class));
        finish();

    }

    public void onStaffLoginFailed() {
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

        Intent intent_register = new Intent(this, LoginActivity.class);
        startActivity(intent_register);
        finish();
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    private void firebaseSetupAuth() {
        mAuthStateList = new FirebaseAuth.AuthStateListener() {
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
        FirebaseAuth.getInstance().addAuthStateListener(mAuthStateList);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthStateList != null) {
            FirebaseAuth.getInstance().removeAuthStateListener(mAuthStateList);
        }
    }
}
