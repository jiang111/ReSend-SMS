package com.jiang.android.resend_sms.service;

import android.app.Service;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.os.Process;
import android.widget.Toast;

/**
 * Created by jiang on 2017/3/21.
 */

public class SmsService extends Service {

    private SmsObserver mObserver;
    public static long currentTime;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {

        currentTime = System.currentTimeMillis();
        Toast.makeText(this, "SmsService 服务器启动了....", Toast.LENGTH_LONG).show();
        ContentResolver resolver = getContentResolver();
        mObserver = new SmsObserver(resolver, new SmsHandler(this));
        resolver.registerContentObserver(Uri.parse("content://sms"), true, mObserver);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.getContentResolver().unregisterContentObserver(mObserver);
        Process.killProcess(Process.myPid());
    }
}
