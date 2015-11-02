package com.univtop.univtop.utils;

import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.univtop.univtop.R;
import com.univtop.univtop.UnivtopApplication;

import retrofit.RetrofitError;
import rx.Subscriber;

/**
 * Created by dungpham on 11/2/15.
 */
public class UnivtopSubscriber<T> extends Subscriber<T> {
    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        Crashlytics.logException(e);
        if(e instanceof RetrofitError) {
            if(((RetrofitError) e).getKind() == RetrofitError.Kind.NETWORK) {
                UnivtopApplication.getInstance().getMainThreadHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(UnivtopApplication.getInstance(), UnivtopApplication.getInstance().getString(R.string.oops_a_server_error_has_occurred), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }

    @Override
    public void onNext(T t) {

    }
}
