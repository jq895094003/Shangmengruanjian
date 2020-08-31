package com.smrj.shangmengruanjian.progressdialog;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.smrj.shangmengruanjian.R;

public class ProgressDialogUtil {
    private  AlertDialog mAlertDialog;


    /**
     * 弹出耗时对话框
     *
     * @param activity
     */
    public  void showProgressDialog(Activity activity) {
        if (mAlertDialog == null) {
            mAlertDialog = new AlertDialog.Builder(activity, R.style.CustomProgressDialog).create();
        }
        if (activity.isFinishing() || activity == null) {
            return;
        }
        View loadView = LayoutInflater.from(activity).inflate(R.layout.custom_progress_dialog, null);
        mAlertDialog.setView(loadView);
        mAlertDialog.setCanceledOnTouchOutside(true);
        TextView tvTip = loadView.findViewById(R.id.tvTip);
        tvTip.setText("加载中……");
        mAlertDialog.show();


    }

    public  void showProgressDialog(Activity activity, String tip) {
        if (TextUtils.isEmpty(tip)) {
            tip = "加载中";
        }
        if (mAlertDialog == null) {
            mAlertDialog = new AlertDialog.Builder(activity, R.style.CustomProgressDialog).create();
        }
        if (activity.isFinishing() || activity == null) {
            return;
        }
        View loadView = LayoutInflater.from(activity).inflate(R.layout.custom_progress_dialog, null);
        mAlertDialog.setView(loadView);
        mAlertDialog.setCanceledOnTouchOutside(true);

        TextView tvTip = loadView.findViewById(R.id.tvTip);
        tvTip.setText(tip);
        mAlertDialog.show();
    }

    public  void dismiss(Activity activity) {
        if (mAlertDialog.isShowing()) {
                mAlertDialog.dismiss();
        }
    }


}
