package com.hackathon.sequoia.sequoiahackathon.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.location.Geocoder;
import android.location.Location;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.hackathon.sequoia.sequoiahackathon.api.School;
import com.hackathon.sequoia.sequoiahackathon.fragment.LoginFragment;
import com.hackathon.sequoia.sequoiahackathon.fragment.MapScreenFragment;
import com.hackathon.sequoia.sequoiahackathon.R;
import com.hackathon.sequoia.sequoiahackathon.global.AppPreference;
import com.hackathon.sequoia.sequoiahackathon.global.Constants;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity implements MapScreenFragment.OnFragmentInteraction{

    private BroadcastReceiver mBroadcastReceiver;

    private CharSequence mTitle;
    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;
    private TextView mTitleView;

    private ActionBarDrawerToggle mNavigationDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up the drawer.
        setupNavigation();

        // Add home screen fragment
        if (savedInstanceState == null) {
            addHomeScreenFragment();
        }

        mTitle = getTitle();

    }

    @Override
    protected void onStart() {
        super.onStart();

        registerReceivers();
    }

    private void registerReceivers() {
        mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction() == null) {
                    return;
                }

                if (intent.getAction().equals(Constants.INTENT_ACTION_LOGIN)) {
                    Toast.makeText(getApplicationContext(), "Logged In", Toast.LENGTH_LONG).show();
                    supportInvalidateOptionsMenu();
                    return;
                }

                else if (intent.getAction().equals(Constants.INTENT_ACTION_LOGOUT)) {
                    AppPreference.getInstance(getApplicationContext()).setUserLoginId(-1);
                    AppPreference.getInstance(getApplicationContext()).setUserLoginStatus(false);
                    supportInvalidateOptionsMenu();
                    Toast.makeText(getApplicationContext(), "Logged out", Toast.LENGTH_LONG).show();
                }
            }
        };

        IntentFilter loginIntentFilter = new IntentFilter(Constants.INTENT_ACTION_LOGIN);
        IntentFilter logoutIntentFilter = new IntentFilter(Constants.INTENT_ACTION_LOGOUT);

        LocalBroadcastManager.getInstance(getApplicationContext()).
                registerReceiver(mBroadcastReceiver, loginIntentFilter);
        LocalBroadcastManager.getInstance(getApplicationContext()).
                registerReceiver(mBroadcastReceiver, logoutIntentFilter);
    }

    private void setupNavigation() {

        mToolbar = (Toolbar) findViewById(R.id.ul_toolbar);
        mTitleView = (TextView) findViewById(R.id.toolbar_title);

        setSupportActionBar(mToolbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationDrawerToggle = new ActionBarDrawerToggle(this,
                mDrawerLayout,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close) {

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                // Should not call super.onDrawerSlide()
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Should not call super.onDrawerOpened()
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                // Should not call super.onDrawerClosed()
            }
        };
        mDrawerLayout.setDrawerListener(mNavigationDrawerToggle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayUseLogoEnabled(false);
    }

    private void addHomeScreenFragment() {

        setTitleView("HOME");
        setDrawerIndicator(true);
        MapScreenFragment mapScreenFragment = MapScreenFragment.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, mapScreenFragment,
                        "MapScreenFragment").commitAllowingStateLoss();
    }

    private void setTitleView(String title) {
        mTitleView.setText(title.toUpperCase());
    }

    public void setDrawerIndicator(boolean isHome) {
        mNavigationDrawerToggle.setDrawerIndicatorEnabled(isHome);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mNavigationDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mNavigationDrawerToggle.syncState();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
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
        } else if(id == R.id.action_account) {
            if(AppPreference.getInstance(getApplicationContext()).isUserLoggedin()) {
                //show basic profile details
            } else {
                //show login/signup screen
                UserAccountActivity.showLogin(this);
            }
        } else if(id == R.id.action_logout) {
            Intent intent = new Intent(Constants.INTENT_ACTION_LOGOUT);
            LocalBroadcastManager.getInstance(getApplicationContext())
                    .sendBroadcast(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem account = menu.findItem(R.id.action_account);
        MenuItem profile = menu.findItem(R.id.action_profile);
        MenuItem logout = menu.findItem(R.id.action_logout);

        if (!AppPreference.getInstance(getApplicationContext()).isUserLoggedin()) {
            account.setVisible(true);
            profile.setVisible(false);
            logout.setVisible(false);
            return true;
        }

        account.setVisible(false);
        profile.setVisible(true);
        logout.setVisible(true);

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mBroadcastReceiver != null) {
            LocalBroadcastManager.getInstance(getApplicationContext()).
                    unregisterReceiver(mBroadcastReceiver);
        }
    }

    @Override
    public void onMarkerClick(int id) {
        SchoolDetailActivity.showSchoolDetail(this, id);
    }
}
