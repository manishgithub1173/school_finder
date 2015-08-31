package com.hackathon.sequoia.sequoiahackathon.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.hackathon.sequoia.sequoiahackathon.R;
import com.hackathon.sequoia.sequoiahackathon.fragment.LoginFragment;
import com.hackathon.sequoia.sequoiahackathon.fragment.ProgressDialogFragment;
import com.hackathon.sequoia.sequoiahackathon.fragment.SignUpFragment;
import com.hackathon.sequoia.sequoiahackathon.fragment.SimpleDialogFragment;
import com.hackathon.sequoia.sequoiahackathon.global.AppPreference;
import com.hackathon.sequoia.sequoiahackathon.global.Constants;
import com.hackathon.sequoia.sequoiahackathon.global.Utilities;

public class UserAccountActivity extends ActionBarActivity implements LoginFragment.LoginFragmentInterface,
        SignUpFragment.SignupFragmentInterface{

    public static final String KEY_SCREEN_NAME = "screen_name";
    public static final String KEY_SOURCE = "requested_screen";
    public static final String SCREEN_LOGIN = "login";
    public static final String SCREEN_PROFILE = "profile";
    public static final String SCREEN_SIGNUP = "signup";

    private TextView mTitleView;
    private Toolbar mToolbar;

    private DialogFragment mDialog = null;
    private boolean mBackPressed = false;

    public static void showLogin(Context context) {
        Intent intent = new Intent(context, UserAccountActivity.class);
        intent.putExtra(KEY_SCREEN_NAME, SCREEN_LOGIN);
        context.startActivity(intent);
    }


    public static void showProfile(Context context) {
        Intent intent = new Intent(context, UserAccountActivity.class);
        intent.putExtra(KEY_SCREEN_NAME, SCREEN_PROFILE);
        context.startActivity(intent);
    }

    public static void showSignup(Context context) {
        Intent intent = new Intent(context, UserAccountActivity.class);
        intent.putExtra(KEY_SCREEN_NAME, SCREEN_SIGNUP);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_user_account);

        mToolbar = (Toolbar) findViewById(R.id.ul_toolbar);
        mTitleView = (TextView) findViewById(R.id.toolbar_title);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            String screenName = getIntent().getStringExtra(KEY_SCREEN_NAME);
            if (screenName.equals(SCREEN_LOGIN)) {
                addLoginFragment();
            }
            else if (screenName.equals(SCREEN_PROFILE)) {
                addProfileFragment();
            }
        }

        setResult(RESULT_CANCELED);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_account, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.action_profile) {
            return true;
        }

        if (id == R.id.action_logout) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void addLoginFragment() {
        setTitleView("LOGIN TO YOUR ACCOUNT");
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container_user_account,
                        LoginFragment.newInstance(),
                        "LoginFragment")
                .commitAllowingStateLoss();
    }

    private void addProfileFragment() {
        /*
        setTitleView("MY ACCOUNT");
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container_user_account,
                        UserProfileFragment.newInstance(),
                        UserProfileFragment.TAG)
                .commitAllowingStateLoss();
                */
    }


    private void setTitleView(String title) {
        mTitleView.setText(title.toUpperCase());
    }

    public void showProgressDialog(String message) {

        if (mDialog != null) {
            mDialog.dismissAllowingStateLoss();
        }

        mDialog = ProgressDialogFragment.newInstance(message);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(mDialog, "DIALOG_FRAGMENT_TAG");
        ft.commitAllowingStateLoss();
    }

    public void hideDialog() {
        if (mDialog != null) {
            mDialog.dismissAllowingStateLoss();
        }
    }


    @Override
    public void onLoginSuccessful() {

        Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();
        broadcastLogIn();
        setResult(Activity.RESULT_OK);
        finish();

    }

    @Override
    public void onSignupClicked() {
        setTitleView("CREATE ACCOUNT");
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container_user_account,
                        SignUpFragment.newInstance(),
                        SignUpFragment.TAG)
                .addToBackStack(SignUpFragment.TAG)
                .commitAllowingStateLoss();
    }

    public void showMessageDialog(String message) {
        if (mDialog != null)
            mDialog.dismissAllowingStateLoss();

        if (TextUtils.isEmpty(message)) {
            return;
        }

        mDialog = SimpleDialogFragment.newInstance(message);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(mDialog, "SIMPLE_DIALOG_FRAGMENT_TAG");
        ft.commitAllowingStateLoss();
    }

    @Override
    public void onSignupSuccessful() {
        broadcastLogIn();
        setResult(RESULT_OK);
        finish();
    }

    private void broadcastLogIn() {
        Intent intent = new Intent(Constants.INTENT_ACTION_LOGIN);
        LocalBroadcastManager.getInstance(getApplicationContext())
                .sendBroadcast(intent);
    }
}
