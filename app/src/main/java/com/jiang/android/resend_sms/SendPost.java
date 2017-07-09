package com.jiang.android.resend_sms;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiang on 2017/7/9.
 */

public class SendPost {

    public void send(final String message) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                postData(message);
            }
        }).start();

    }

    private void postData(String message) {
        try {
            HttpClient httpclient = new DefaultHttpClient();

            HttpPost httppost = new HttpPost("http://192.168.199.140:1024/send_sms");
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
            nameValuePairs.add(new BasicNameValuePair("msg", message));
            httppost.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8));
            httpclient.execute(httppost);
        } catch (Exception e) {

        }

    }
}
