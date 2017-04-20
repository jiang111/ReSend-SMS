package com.jiang.android.resend_sms;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import org.apache.commons.mail.HtmlEmail;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by jiang on 2017/4/20.
 */

public class SendEmail {
    private static Handler mHandler = new Handler(Looper.getMainLooper());

    public static ExecutorService executorService = Executors.newCachedThreadPool();

    private static String sendTo = "";
    private static String sendFrom = "";
    private static String user = "";
    private static String passwd = "";
    private static int ssLPort = 0;
    private static String hostName = "";
    private static String subject = "";

    public SendEmail() {
    }

    public void send(final Context context, final String body) {

        executorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    initConfig(context);
                    if (TextUtils.isEmpty(sendTo) ||
                            TextUtils.isEmpty(sendFrom) ||
                            ssLPort == 0 ||
                            TextUtils.isEmpty(user) ||
                            TextUtils.isEmpty(passwd)) {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(context, "请先配置账号", Toast.LENGTH_SHORT).show();
                            }
                        });
                        return;
                    }
                    Log.i(TAG, "send: " + body);
                    HtmlEmail email = new HtmlEmail();
                    email.setHostName(hostName);
                    email.setSmtpPort(ssLPort);
                    email.setSSL(true);
                    email.setCharset("utf8");
                    email.addTo(sendTo);
                    email.setFrom(sendFrom);
                    email.setAuthentication(user, passwd);
                    email.setSubject(TextUtils.isEmpty(subject) ? "你有新短信了" : subject);
                    email.setMsg(TextUtils.isEmpty(body) ? "null" : body);
                    email.send();
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, "邮件发送成功", Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (final Exception e) {
                    e.printStackTrace();
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, "邮件发送失败:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });

    }

    public static void initConfig(Context context) {
        if (TextUtils.isEmpty(sendFrom)) {
            sendFrom = SharePrefUtil.getString(context.getApplicationContext(),
                    Common.SEND_FROM, "");
        }
        if (TextUtils.isEmpty(sendTo)) {
            sendTo = SharePrefUtil.getString(context.getApplicationContext(),
                    Common.SEND_TO, "");
        }
        if (TextUtils.isEmpty(user)) {
            user = SharePrefUtil.getString(context.getApplicationContext(),
                    Common.AUTHENTIATION_USER, "");
        }
        if (TextUtils.isEmpty(passwd)) {
            passwd = SharePrefUtil.getString(context.getApplicationContext(),
                    Common.AUTHENTIATION_PASSWD, "");
        }
        if (ssLPort == 0) {
            ssLPort = SharePrefUtil.getInt(context.getApplicationContext(),
                    Common.SSL_PORT, 0);
        }
        if (TextUtils.isEmpty(hostName)) {
            hostName = SharePrefUtil.getString(context.getApplicationContext(),
                    Common.SMTP, "");
        }
        if (TextUtils.isEmpty(subject)) {
            subject = SharePrefUtil.getString(context.getApplicationContext(),
                    Common.SUBJEECT, "");
        }

    }

    public static void clear() {
        sendTo = null;
        sendFrom = null;
        user = null;
        passwd = null;
        ssLPort = 0;
        hostName = null;
        subject = null;

    }

    private static final String TAG = "SendEmail";
}
