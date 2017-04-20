package com.example.crisip.chrisipappdevproject.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.View;


import com.example.crisip.chrisipappdevproject.DatabaseHelper;
import com.example.crisip.chrisipappdevproject.InputValidation;
import com.example.crisip.chrisipappdevproject.R;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private final AppCompatActivity activity = LoginActivity.this;
    private NestedScrollView nestedScrollView;

    private TextInputLayout txtILemail;
    private TextInputLayout txtILpassword;

    private TextInputEditText txtILeditemail;
    private TextInputEditText txtILeditpassword;

    private AppCompatButton btnAppCompatLogin;

    private AppCompatTextView tvLinkRegister;

    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);

        txtILemail = (TextInputLayout) findViewById(R.id.txtInputLayoutEmail);
        txtILpassword = (TextInputLayout) findViewById(R.id.txtInputLayoutPassword);

        txtILeditemail = (TextInputEditText) findViewById(R.id.textInputEditTextEmail);
        Intent intent = getIntent();
        String newString = intent.getStringExtra("rssfeeds");
        txtILeditemail.setText(newString);
        txtILeditpassword = (TextInputEditText) findViewById(R.id.textInputEditTextPassword);

        btnAppCompatLogin = (AppCompatButton) findViewById(R.id.appCompatButtonLogin);

        tvLinkRegister = (AppCompatTextView) findViewById(R.id.textViewLinkRegister);
        databaseHelper = new DatabaseHelper(activity);
        inputValidation = new InputValidation(activity);
        btnAppCompatLogin.setOnClickListener(this);
        tvLinkRegister.setOnClickListener(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.appCompatButtonLogin:
                verifyFromSQLite();
                break;
            case R.id.textViewLinkRegister:
                Intent intentRegister = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intentRegister);
                break;
        }
    }


    private void verifyFromSQLite()
    {
        if (!inputValidation.isInputEditTextFilled(txtILeditemail, txtILemail, getString(R.string.notvalidemail)))
        {
            return;
        }
        if (!inputValidation.isInputEditTextUsername(txtILeditemail, txtILemail, getString(R.string.notvalidemail))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(txtILeditpassword, txtILpassword, getString(R.string.notvalidemail))) {
            return;
        }

        if (databaseHelper.checkUser(txtILeditemail.getText().toString().trim()
                , txtILeditpassword.getText().toString().trim())) {


            Intent intent = new Intent(activity, MainActivity.class);
            intent.putExtra("Username", txtILeditemail.getText().toString().trim());
            emptyInputEditText();
            startActivity(intent);


        }
        else
        {
            Snackbar.make(nestedScrollView, getString(R.string.error_valid_email_password), Snackbar.LENGTH_LONG).show();
        }
    }


    private void emptyInputEditText() {
        txtILeditemail.setText(null);
        txtILeditpassword.setText(null);
    }

}
