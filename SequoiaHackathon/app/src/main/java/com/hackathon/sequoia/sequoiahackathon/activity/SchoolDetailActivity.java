package com.hackathon.sequoia.sequoiahackathon.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.hackathon.sequoia.sequoiahackathon.R;
import com.hackathon.sequoia.sequoiahackathon.api.Review;
import com.hackathon.sequoia.sequoiahackathon.fragment.LoginFragment;
import com.hackathon.sequoia.sequoiahackathon.fragment.ProgressDialogFragment;
import com.hackathon.sequoia.sequoiahackathon.fragment.ReviewFragment;
import com.hackathon.sequoia.sequoiahackathon.fragment.SchoolDetailFragment;
import com.hackathon.sequoia.sequoiahackathon.fragment.WriteReviewFragment;

import java.util.ArrayList;

public class SchoolDetailActivity extends ActionBarActivity implements
        SchoolDetailFragment.OnFragmentInteractionListener,
        WriteReviewFragment.OnFragmentInteractionListener{

    public static final String KEY_SCREEN_NAME = "screen_name";
    public static final String SCREEN_SCHOOL_DETAIL = "school_detail";
    public static final String SCREEN_SCHOOL_REVIEW = "school_review";
    public static final String SCREEN_SCHOOL_ADD_REVIEW = "school_add_review";

    private TextView mTitleView;
    private Toolbar mToolbar;

    private DialogFragment mDialog = null;

    public static void showSchoolDetail(Context context, int id) {
        Intent intent = new Intent(context, SchoolDetailActivity.class);
        intent.putExtra(KEY_SCREEN_NAME, SCREEN_SCHOOL_DETAIL);
        intent.putExtra("school_id", id);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_detail);

        mToolbar = (Toolbar) findViewById(R.id.ul_toolbar);
        mTitleView = (TextView) findViewById(R.id.toolbar_title);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            String screenName = getIntent().getStringExtra(KEY_SCREEN_NAME);
            if (screenName.equals(SCREEN_SCHOOL_DETAIL)) {
                int id = getIntent().getIntExtra("school_id", -1);
                addSchoolDetailFragment(id);
            }
            else if (screenName.equals(SCREEN_SCHOOL_REVIEW)) {
                //addSchoolReviewFragment();
            }
        }

        setResult(RESULT_CANCELED);
    }

    private void setTitleView(String title) {
        mTitleView.setText(title.toUpperCase());
    }

    private void addSchoolDetailFragment(int id) {
        setTitleView("SCHOOL DETAIL");
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container_school_detail,
                        SchoolDetailFragment.newInstance(id),
                        "SchoolDetailFragment")
                .commitAllowingStateLoss();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_school_menu, menu);
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

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void writeReview(int id, String name, float rating1, float rating2) {
        setTitleView("WRITE A REVIEW");
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container_school_detail,
                        WriteReviewFragment.newInstance(id, name),
                        "WriteReviewFragment")
                .commitAllowingStateLoss();
    }

    @Override
    public void showReview(ArrayList<Review> reviewArrayList) {
        setTitleView("REVIEWS");
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container_school_detail,
                        ReviewFragment.newInstance(reviewArrayList),
                        "ReviewFragment")
                .commitAllowingStateLoss();
    }

    @Override
    public void onSubmitSuccesful() {
        setResult(RESULT_OK);
        finish();
    }
}
