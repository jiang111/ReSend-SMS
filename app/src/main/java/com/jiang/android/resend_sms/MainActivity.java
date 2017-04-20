package com.jiang.android.resend_sms;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jiang.android.resend_sms.service.SmsService;

public class MainActivity extends AppCompatActivity {

    private Intent intentService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        requestPermission();

        startService();

    }

    private void initView() {
        final EditText hostName = $(R.id.host);
        final EditText port = $(R.id.port);
        final EditText user = $(R.id.send_user);
        final EditText passwd = $(R.id.send_passwd);
        final EditText subject = $(R.id.send_subject);
        final EditText from = $(R.id.send_from);
        final EditText to = $(R.id.send_to);
        Button save = $(R.id.send_save);
        String hostNameS = SharePrefUtil.getString(MainActivity.this, Common.SMTP, "");
        int postS = SharePrefUtil.getInt(MainActivity.this, Common.SSL_PORT, 0);
        String userS = SharePrefUtil.getString(MainActivity.this, Common.AUTHENTIATION_USER, "");
        String passwdS = SharePrefUtil.getString(MainActivity.this, Common.AUTHENTIATION_PASSWD, "");
        String subjectS = SharePrefUtil.getString(MainActivity.this, Common.SUBJEECT, "");
        String fromS = SharePrefUtil.getString(MainActivity.this, Common.SEND_FROM, "");
        String toS = SharePrefUtil.getString(MainActivity.this, Common.SEND_TO, "");


        hostName.setText(hostNameS);
        user.setText(userS);
        passwd.setText(passwdS);
        subject.setText(subjectS);
        from.setText(fromS);
        to.setText(toS);
        if (postS != 0) {
            port.setText(postS + "");
        }
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharePrefUtil.saveString(MainActivity.this, Common.SMTP, hostName.getText().toString());
                SharePrefUtil.saveString(MainActivity.this, Common.AUTHENTIATION_USER, user.getText().toString());
                SharePrefUtil.saveString(MainActivity.this, Common.AUTHENTIATION_PASSWD, passwd.getText().toString());
                SharePrefUtil.saveString(MainActivity.this, Common.SUBJEECT, subject.getText().toString());
                SharePrefUtil.saveString(MainActivity.this, Common.SEND_FROM, from.getText().toString());
                SharePrefUtil.saveString(MainActivity.this, Common.SEND_TO, to.getText().toString());
                SharePrefUtil.saveInt(MainActivity.this, Common.SSL_PORT, Integer.valueOf(port.getText().toString()));

                SendEmail.clear();
                Toast.makeText(MainActivity.this, "保存成功", Toast.LENGTH_SHORT).show();

            }
        });

    }


    public <T> T $(int id) {
        return (T) findViewById(id);
    }

    private void requestPermission() {
        //判断Android版本是否大于23
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS);

            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS},
                        100);
                return;
            }
        }
    }


    private void startService() {
        intentService = new Intent(MainActivity.this, SmsService.class);
        startService(intentService);
    }
}
