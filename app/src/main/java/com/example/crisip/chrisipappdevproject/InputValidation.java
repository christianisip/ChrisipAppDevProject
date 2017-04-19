package com.example.crisip.chrisipappdevproject;

/**
 * Created by Crisip on 4/3/2017.
 */

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

public class InputValidation
{
    private Context context;
    public InputValidation(Context context) {
        this.context = context;
    }


    public boolean isInputEditTextFilled(TextInputEditText validInputOnEditText,
                                         TextInputLayout textInputLayout,
                                         String errorMessage) {
        String value = validInputOnEditText.getText().toString().trim();
        if (value.isEmpty())
        {
            textInputLayout.setError(errorMessage);
            hideKeyboardFrom(validInputOnEditText);
            return false;
        } else {
            textInputLayout.setErrorEnabled(false);
        }

        return true;
    }

    public boolean isInputEditTextUsername(TextInputEditText validInputEditUsername,
                                           TextInputLayout textInputLayout,
                                           String errorMessage) {
        String value = validInputEditUsername.getText().toString().trim();
        if (value.isEmpty())
        {
            textInputLayout.setError(errorMessage);
            hideKeyboardFrom(validInputEditUsername);
            return false;
        } else {
            textInputLayout.setErrorEnabled(false);
        }
        return true;
    }

    public boolean isInputEditTextMatches(TextInputEditText validInputEditText,
                                          TextInputEditText validInputEditText2,
                                          TextInputLayout textInputLayout,
                                          String errorMessage)
    {
        String inputValue = validInputEditText.getText().toString().trim();
        String inputValue2nd = validInputEditText2.getText().toString().trim();
        if (!inputValue.contentEquals(inputValue2nd))
        {
            textInputLayout.setError(errorMessage);
            hideKeyboardFrom(validInputEditText2);
            return false;
        } else
            {
            textInputLayout.setErrorEnabled(false);
        }
        return true;
    }

    /**
     * method to Hide keyboard
     *
     * @param view
     */
    private void hideKeyboardFrom(View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }
}