package com.example.crisip.chrisipappdevproject.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;


import com.example.crisip.chrisipappdevproject.R;
import com.example.crisip.chrisipappdevproject.User;
import com.example.crisip.chrisipappdevproject.DatabaseHelper;
import com.example.crisip.chrisipappdevproject.InputValidation;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private final AppCompatActivity activity = RegisterActivity.this;

    private NestedScrollView nestedScrollView;

    private TextInputLayout txtInputName;
    private TextInputLayout txtInputEmail;
    private TextInputLayout txtInputPassword;
    private TextInputLayout txtInputConfirmPass;

    private TextInputEditText txtInputEditName;
    private TextInputEditText txtInputEditEmail;
    private TextInputEditText txtInputEditPassword;
    private TextInputEditText txtInputEditConfirmPass;

    private AppCompatButton btnAppCompatRegister;
    private AppCompatTextView lnkAppCompatLogin;

    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;
    private User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
//        getSupportActionBar().hide();

        nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);

        txtInputName = (TextInputLayout)findViewById(R.id.txtInputLayoutName);
        txtInputEmail = (TextInputLayout)findViewById(R.id.txtInputLayoutEmail);
        txtInputPassword = (TextInputLayout)findViewById(R.id.txtInputLayoutPassword);
        txtInputConfirmPass = (TextInputLayout)findViewById(R.id.txtInputLayoutConfirmPassword);

        txtInputEditName = (TextInputEditText) findViewById(R.id.txtInputEditTextName);
        txtInputEditEmail = (TextInputEditText) findViewById(R.id.txtInputEditTextEmail);
        txtInputEditPassword = (TextInputEditText) findViewById(R.id.txtInputEditTextPassword);
        txtInputEditConfirmPass = (TextInputEditText) findViewById(R.id.txtInputEditTextConfirmPassword);

        btnAppCompatRegister = (AppCompatButton)findViewById(R.id.appCompatBtnRegister);
        lnkAppCompatLogin = (AppCompatTextView) findViewById(R.id.appCompatTextViewLoginLink);
        btnAppCompatRegister.setOnClickListener(this);
        lnkAppCompatLogin.setOnClickListener(this);
        inputValidation = new InputValidation(activity);
        databaseHelper = new DatabaseHelper(activity);
        user = new User();
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId()) {

            case R.id.appCompatBtnRegister:
                postDataToSQLite();
                break;

            case R.id.appCompatTextViewLoginLink:
                finish();
                break;
        }
    }

    private void postDataToSQLite() {
        if (!inputValidation.isInputEditTextFilled(txtInputEditName, txtInputName, getString(R.string.notvalidname))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(txtInputEditEmail, txtInputEmail, getString(R.string.notvalidemail))) {
            return;
        }
        if (!inputValidation.isInputEditTextUsername(txtInputEditEmail, txtInputEmail, getString(R.string.notvalidemail))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(txtInputEditPassword, txtInputPassword, getString(R.string.notvalidpassword))) {
            return;
        }
        if (!inputValidation.isInputEditTextMatches(txtInputEditPassword, txtInputEditConfirmPass,
                txtInputConfirmPass, getString(R.string.error_password_match))) {
            return;
        }

        if (!databaseHelper.checkUser(txtInputEditEmail.getText().toString().trim())) {


            user.setName(txtInputEditName.getText().toString().trim());
            user.setEmail(txtInputEditEmail.getText().toString().trim());
            user.setPassword(txtInputEditPassword.getText().toString().trim());

            databaseHelper.addUser(user);



            Snackbar.make(nestedScrollView, getString(R.string.success_message), Snackbar.LENGTH_LONG).show();
            emptyInputEditText();

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run()
                {
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                }
            }, 2000);


        } else
            {

            Snackbar.make(nestedScrollView, getString(R.string.error_email_exists), Snackbar.LENGTH_LONG).show();
        }


    }

    private void emptyInputEditText()
    {
        txtInputEditName.setText(null);
        txtInputEditEmail.setText(null);
        txtInputEditPassword.setText(null);
        txtInputEditConfirmPass.setText(null);
    }
}
