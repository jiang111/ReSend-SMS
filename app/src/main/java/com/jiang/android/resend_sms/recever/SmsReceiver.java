package com.jiang.android.resend_sms.recever;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

import com.jiang.android.resend_sms.SendEmail;

/**
 * Created by jiang on 2017/3/21.
 */

public class SmsReceiver extends BroadcastReceiver {
    private static final String TAG = "SmsReceiver";
    private Context mContext;
    public static final String SMS_RECEIVED_ACTION = "android.provider.Telephony.SMS_RECEIVED";
    public static final String SMS_DELIVER_ACTION = "android.provider.Telephony.SMS_DELIVER";


    @Override
    public void onReceive(Context context, Intent intent) {
        this.mContext = context;
        try {
            String action = intent.getAction();
            if (SMS_RECEIVED_ACTION.equals(action) || SMS_DELIVER_ACTION.equals(action)) {
                Toast.makeText(context, "开始接收短信.....", Toast.LENGTH_SHORT).show();
                Bundle bundle = intent.getExtras();
                if (bundle != null) {
                    Object[] pdus = (Object[]) bundle.get("pdus");
                    if (pdus != null && pdus.length > 0) {
                        SmsMessage[] messages = new SmsMessage[pdus.length];
                        for (int i = 0; i < pdus.length; i++) {
                            byte[] pdu = (byte[]) pdus[i];
                            messages[i] = SmsMessage.createFromPdu(pdu);
                        }
                        StringBuffer buffer = new StringBuffer();
                        String sender = null;
                        for (SmsMessage message : messages) {
                            buffer.append(message.getMessageBody());
                            sender = message.getOriginatingAddress();
                            this.abortBroadcast();
                        }

                        sendSMS("发件人:" + sender + "\n 内容:" + buffer.toString());

                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void sendSMS(String message) {
//        if (IntenetUtil.getNetworkState(mContext) == IntenetUtil.NETWORN_WIFI) {
//            WifiManager wifiManager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
//            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
//            final String ssid = wifiInfo.getSSID();
//            if (ssid.startsWith("\"HiWiFi")) {
//                Log.i(TAG, "sendSMS: 发送微信");
//                new SendPost().send(message);
//                return;
//            }
//        }
        new SendEmail().send(mContext, message);

    }
}

