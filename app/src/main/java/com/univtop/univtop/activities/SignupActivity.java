package com.univtop.univtop.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.univtop.univtop.R;
import com.univtop.univtop.models.LoginResponse;
import com.univtop.univtop.services.APIService;
import com.univtop.univtop.utils.UnivtopSubscriber;
import com.univtop.univtop.utils.Utilities;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SignupActivity extends AbstractBaseActivity {
    private static final String TAG = "SignupActivity";

    @Bind(R.id.input_firstname) EditText mEdtFirstName;
    @Bind(R.id.input_lastname) EditText mEdtLastName;
    @Bind(R.id.input_email) EditText mEdtEmail;
    @Bind(R.id.input_password) EditText mEdtPassword;
    @Bind(R.id.input_confirm_password) EditText mEdtConfirmPassword;
    @Bind(R.id.btn_signup) Button mBtnSignup;
    @Bind(R.id.link_login) TextView mTvLogin;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);

        mBtnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        mTvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                finish();
            }
        });
    }

    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        mBtnSignup.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
                R.style.AppTheme_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        String firstname = mEdtFirstName.getText().toString();
        String lastname = mEdtLastName.getText().toString();
        String email = mEdtEmail.getText().toString();
        String password = mEdtPassword.getText().toString();
        String confirmPassword = mEdtConfirmPassword.getText().toString();
        APIService.getInstance().signUp(firstname, lastname, email, password).subscribe(new UnivtopSubscriber<LoginResponse>() {
            @Override
            public void onCompleted() {
                super.onCompleted();
            }

            @Override
            public void onNext(LoginResponse loginResponse) {
                if (loginResponse != null) {
                    if (loginResponse.api_key != null && !loginResponse.api_key.isEmpty()) {
                        Utilities.setApiKey(SignupActivity.this, loginResponse.api_key);
                        Intent top = new Intent(getApplicationContext(), HomeActivity.class);
                        startActivity(top);
                        finish();
                    } else {
                        onSignupFailed();
                    }
                }
                super.onNext(loginResponse);
            }

            @Override
            public void onError(Throwable e) {
                onSignupFailed();
                super.onError(e);
            }
        });

    }


    public void onSignupSuccess() {
        mBtnSignup.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        mBtnSignup.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String firstname = mEdtFirstName.getText().toString();
        String lastname = mEdtLastName.getText().toString();
        String email = mEdtEmail.getText().toString();
        String password = mEdtPassword.getText().toString();
        String confirmPassword = mEdtConfirmPassword.getText().toString();
        if (firstname.isEmpty()) {
            mEdtFirstName.setError("Cannot empty First Name");
            valid = false;
        } else {
            mEdtFirstName.setError(null);
        }
        if (lastname.isEmpty()) {
            mEdtLastName.setError("Cannot empty Last Name");
            valid = false;
        } else {
            mEdtLastName.setError(null);
        }
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mEdtEmail.setError("enter a valid email address");
            valid = false;
        } else {
            mEdtEmail.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            mEdtPassword.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            mEdtPassword.setError(null);
        }

        if (!password.equals(confirmPassword)) {
            mEdtConfirmPassword.setError("Confirm Password must match with Password");
            valid = false;
        } else {
            mEdtConfirmPassword.setError(null);
        }

        return valid;
    }
}