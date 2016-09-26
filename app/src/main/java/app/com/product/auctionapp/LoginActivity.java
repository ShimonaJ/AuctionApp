package app.com.product.auctionapp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.ContentProviderOperation;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.ContentResolver;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import app.com.product.auctionapp.data.ItemsContract;
import app.com.product.auctionapp.util.Config;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity  {


    // UI references.
    private EditText mEmailView;
    private EditText mPasswordView;
    private EditText   mPhoneText;

    private EditText   mUsernameText;
    private View mProgressView;
    private View mLoginFormView;
    private  Button mSubmitButton ;
   private Button mCancelButton;
    private Button mRegisterButton;
    private Button mSignInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mEmailView = (EditText) findViewById(R.id.email);


        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

         mRegisterButton = (Button) findViewById(R.id.email_register_button);
        mRegisterButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                showRegisterationForm();
            }
        });

         mSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
        mPhoneText =(EditText)findViewById(R.id.phone);

        mUsernameText =(EditText)findViewById(R.id.username);
        mSubmitButton = (Button) findViewById(R.id.submit_button);
        mSubmitButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                submitButton();
            }
        });
          mCancelButton = (Button) findViewById(R.id.cancel_button);

        mCancelButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelForm();
            }
        });
        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }

private void submitButton(){
    UserLoginTask task =new UserLoginTask(mEmailView.getText().toString(),mPasswordView.getText().toString(),mPhoneText.getText().toString(),mUsernameText.getText().toString(),"Register");
    task.execute();
}
private void showRegisterationForm(){
mUsernameText.setVisibility(View.VISIBLE);
    mPhoneText.setVisibility(View.VISIBLE);
    mSubmitButton.setVisibility(View.VISIBLE);
    mCancelButton.setVisibility(View.VISIBLE);

    mRegisterButton.setVisibility(View.INVISIBLE);
    mSignInButton.setVisibility(View.INVISIBLE);


}
    public void cancelForm(){
        mUsernameText.setVisibility(View.INVISIBLE);
        mPhoneText.setVisibility(View.INVISIBLE);
        mSubmitButton.setVisibility(View.INVISIBLE);
        mCancelButton.setVisibility(View.INVISIBLE);

        mRegisterButton.setVisibility(View.VISIBLE);
        mSignInButton.setVisibility(View.VISIBLE);
    }
    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            UserLoginTask task =new UserLoginTask(email,password,"","","Validate");
            task.execute();
        }
    }
    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }






    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;
        private final String mPhone;
        private final String mAction;

        private final String mUsername;

        UserLoginTask(String email, String password,String phone,String username,String action) {
            mEmail = email;
            mPassword = password;
            mAction = action;
            mPhone = phone;
            mUsername = username;
        }
private void AddSharedPref(int userId) {
    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

    SharedPreferences.Editor editor = prefs.edit();
    editor.putInt(Config.USERID, userId);

    editor.commit();
}
        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
            Uri dirUri = ItemsContract.Users.buildDirUri();
           // if (params[0] == "Validate") {
            if(mAction =="Validate") {
                boolean isAuthenticated = false;
                try {
                    ContentResolver cr = getContentResolver();
                    Cursor emailCur = cr.query(dirUri, new String[]{ItemsContract.Users._ID}, "Email=? and Password=?", new String[]{mEmail, mPassword}, null);
                    int userid=0;
                    if(emailCur!=null) {
                        if (emailCur.moveToNext()) {
                            isAuthenticated = true;
                            userid = Integer.parseInt(emailCur.getString(0));
                            AddSharedPref(userid);
                        }
                        emailCur.close();
                    }
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    return false;
                }
                return isAuthenticated;
            }else {
                try {
                    ContentResolver cr = getContentResolver();
                    ContentValues values= new ContentValues();
                    values.put(ItemsContract.Users.EMAIL, mEmail);
                    values.put(ItemsContract.Users.PASSWORD, mPassword);
                    values.put(ItemsContract.Users.PHONE, mPhone);
                    values.put(ItemsContract.Users.NAME, mUsername);


                    cr.insert(dirUri, values);

                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    return false;
                }
                    return true;
                }
            }


        @Override
        protected void onPostExecute(final Boolean success) {
            showProgress(false);
                if(mAction=="Validate") {
                    if (success) {

                        Intent mainActivity = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(mainActivity);
                        finish();
                    } else {
                        mPasswordView.setError(getString(R.string.error_incorrect_password));
                        mPasswordView.requestFocus();
                    }
                }else{
                    if(success){

                        Snackbar.make(getCurrentFocus(),  "Registration Successfull. Login to proceed", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        cancelForm();

                    }else{

                    }
                }
        }

        @Override
        protected void onCancelled() {

            showProgress(false);
        }
    }
}

