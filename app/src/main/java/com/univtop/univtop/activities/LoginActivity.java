package com.univtop.univtop.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.univtop.univtop.R;
import com.univtop.univtop.models.LoginResponse;
import com.univtop.univtop.services.APIService;
import com.univtop.univtop.utils.DebugLog;
import com.univtop.univtop.utils.UnivtopSubscriber;
import com.univtop.univtop.utils.Utilities;

import butterknife.ButterKnife;
import butterknife.Bind;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class LoginActivity extends AbstractBaseActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    public static final int RESULT_LOGIN_CANCEL = 1001;

    @Bind(R.id.input_email) EditText mTvEmail;
    @Bind(R.id.input_password) EditText mTvPassword;
    @Bind(R.id.btn_login) Button mBtnLogin;
    @Bind(R.id.link_signup) TextView mTvSignup;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        mBtnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        mTvSignup.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });
    }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        mBtnLogin.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        String email = mTvEmail.getText().toString();
        String password = mTvPassword.getText().toString();

        APIService.getInstance().login(email, password).subscribe(new UnivtopSubscriber<LoginResponse>() {
            @Override
            public void onNext(LoginResponse loginResponse) {
                if (loginResponse != null) {
                    DebugLog.d("api key:" + loginResponse.api_key);
                    if (loginResponse.api_key != null && !loginResponse.api_key.isEmpty()) {
                        Utilities.setApiKey(LoginActivity.this, loginResponse.api_key);
                        Intent top = new Intent(getApplicationContext(), HomeActivity.class);
                        startActivity(top);
                        finish();
                    } else {
                        onLoginFailed();
                    }
                }
                super.onNext(loginResponse);
            }

            @Override
            public void onError(Throwable e) {
                onLoginFailed();
                super.onError(e);
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

    public void onLoginSuccess() {
        mBtnLogin.setEnabled(true);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        mBtnLogin.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = mTvEmail.getText().toString();
        String password = mTvPassword.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mTvEmail.setError("enter a valid email address");
            valid = false;
        } else {
            mTvEmail.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            mTvPassword.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            mTvPassword.setError(null);
        }

        return valid;
    }
}