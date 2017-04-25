package com.jiang.android.resend_sms.service;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;

import com.jiang.android.resend_sms.model.SmsInfo;

/**
 * Created by jiang on 2017/3/21.
 */

public class SmsHandler extends Handler {
    private Context mcontext;

    public SmsHandler(Context context) {
        this.mcontext = context;
    }

    @Override
    public void handleMessage(Message msg) {
        SmsInfo smsInfo = (SmsInfo) msg.obj;

        if (smsInfo.action == 1) {
            ContentValues values = new ContentValues();
            values.put("read", "1");
            mcontext.getContentResolver().update(
                    Uri.parse("content://sms/inbox"), values, "thread_id=?",
                    new String[]{smsInfo.thread_id});
        } else if (smsInfo.action == 2) {
//            Uri mUri = Uri.parse("content://sms/");
//            mcontext.getContentResolver().delete(mUri, "_id=?",
//                    new String[] { smsInfo._id });
        }
    }
}
