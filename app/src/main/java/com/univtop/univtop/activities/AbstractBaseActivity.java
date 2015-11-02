package com.univtop.univtop.activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.univtop.univtop.R;
import com.univtop.univtop.UnivtopApplication;
import com.univtop.univtop.events.NotificationEvent;
import com.univtop.univtop.utils.PrefManager;
import com.univtop.univtop.utils.Utilities;

import butterknife.Bind;
import de.greenrobot.event.EventBus;

/**
 * Created by dungpham on 11/1/15.
 */
public class AbstractBaseActivity extends AppCompatActivity {
    public static int REQUEST_CODE_LOGIN = 7051;
    private Dialog mWaitDialog;
    private boolean isResumed = false;
    private Dialog mForceUpdateDialog;

    Handler handler;
    Runnable mHandlerTask;
    @Nullable
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    public enum AccessLevel {
        PUBLIC,
        ACCOUNT;
    }

    public AccessLevel getAccessLevel() {
        return AccessLevel.PUBLIC;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UnivtopApplication.getInstance().setCurrentActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UnivtopApplication.getInstance().setCurrentActivity(null);
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);

        isResumed = true;
//        if (getAccessLevel() == AccessLevel.ACCOUNT && !Utilities.isLoggedIn()) {
//            startActivityForResult(new Intent(this, PrefManager.getInstance().getAnnouncements().getMainPageBlocked() ? OnboardingActivity.class : LoginActivity.class), REQUEST_CODE_LOGIN);
//        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        cancelTimer();
        EventBus.getDefault().unregister(this);
        isResumed = false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_LOGIN && resultCode == RESULT_CANCELED) {
            if(getAccessLevel() != AccessLevel.PUBLIC) {
                finish();
            }
        } else if (requestCode == REQUEST_CODE_LOGIN && resultCode == RESULT_OK) {
            onLoggedIn();
        } else if(requestCode == REQUEST_CODE_LOGIN && resultCode == LoginActivity.RESULT_LOGIN_CANCEL) {
            if(getAccessLevel() != AccessLevel.PUBLIC) {
                finish();
            } else {
                onLogInCancel();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    public void setupActionBar(String title) {
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            if (title != null) {
                ab.setTitle(title);
            } else {
                ab.setTitle("");
            }
        }
    }

    protected void setupToolbar() {
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
    }

    public void showPleaseWaitDialog(final boolean on) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (on) {
                    if (mWaitDialog != null) {
                        mWaitDialog.cancel();
                    }
                    mWaitDialog = ProgressDialog.show(AbstractBaseActivity.this, "",
                            getString(R.string.please_wait), true);
                    mWaitDialog.show();
                } else {
                    if (mWaitDialog != null) {
                        mWaitDialog.cancel();
                        mWaitDialog = null;
                    }
                }
            }
        });
    }

    protected boolean isActivityResumed() {
        return isResumed;
    }

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    public boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                finish();
            }
            return false;
        }
        return true;
    }

    private void cancelTimer() {
        if (this.handler != null) {
            this.handler.removeCallbacks(this.mHandlerTask);
        }
    }

    public void onEvent(NotificationEvent event) {
        //
    }

    public void onLoggedIn() {}

    public void onLogInCancel() {}
}
