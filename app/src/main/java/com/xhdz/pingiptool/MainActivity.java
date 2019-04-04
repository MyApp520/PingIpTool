package com.xhdz.pingiptool;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends Activity {

    private TextView mTvPingResult;
    private EditText mEditIpAddress;
    private Button mBtnStartPing;
    private ProgressDialog mProgressDialog;
    private Toast mToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTvPingResult = findViewById(R.id.tv_ping_result);
        mEditIpAddress = findViewById(R.id.edit_ip_address);
        mBtnStartPing = findViewById(R.id.btn_start_ping);
        mBtnStartPing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(mEditIpAddress.getText().toString().trim())) {
                    Toast.makeText(getApplicationContext(), "请输入服务器地址", Toast.LENGTH_SHORT).show();
                    return;
                }

                new PingIpAsyncTask().execute(mEditIpAddress.getText().toString().trim());
            }
        });
    }

    private boolean pingIpAddress(String ipAddress) {
        try {
            Process process = Runtime.getRuntime().exec("/system/bin/ping -c 1 -w 100 " + ipAddress);
            int status = process.waitFor();
            if (status == 0) {
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("正在进行中");
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.setCancelable(false);
            mProgressDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    dismissProgressDialog();
                }
            });
        }
        if (mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
        mProgressDialog.show();
    }

    private void dismissProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @SuppressLint("StaticFieldLeak")
    class PingIpAsyncTask extends AsyncTask<String, Integer, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mTvPingResult.setText("正在ping服务器。。。请稍等");
            mTvPingResult.setTextColor(Color.BLACK);
            showProgressDialog();
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            return pingIpAddress(strings[0]);
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            dismissProgressDialog();
            if (result) {
                mTvPingResult.setText("成功了，服务器是通的！");
                mTvPingResult.setTextColor(Color.BLUE);
                Toast.makeText(getApplicationContext(), "成功了，服务器是通的", Toast.LENGTH_SHORT).show();
            } else {
                mTvPingResult.setText("失败了，ping不通服务器！！！");
                mTvPingResult.setTextColor(Color.RED);
                Toast.makeText(getApplicationContext(), "失败了，ping不通服务器", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @SuppressLint("ShowToast")
    private void showToast(String toastInfo) {
        if (TextUtils.isEmpty(toastInfo)) {
            return;
        }
        if (mToast == null) {
            mToast = Toast.makeText(getApplicationContext(), toastInfo, Toast.LENGTH_SHORT);
            mToast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            mToast.setText(toastInfo);
        }
        mToast.show();
    }
}
