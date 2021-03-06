package com.nut2014.baselibrary.utils;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.nut2014.baselibrary.R;

import java.util.Locale;

public class FProgressDialog {
    private Context context;

    public FProgressDialog(Context context) {
        this.context = context;
    }

    private AlertDialog alertDialog;
    private ProgressBar progressBar1;
    private TextView progress_tv;

    public void show(String msg, boolean hasProgress) {
        if (alertDialog == null) {
            alertDialog = new AlertDialog.Builder(context, R.style.ProgressDialog).create();
            View inflate = LayoutInflater.from(context).inflate(R.layout.layout_alert_progress, null);
            TextView tv_tip = inflate.findViewById(R.id.tv_tip);
            progress_tv = inflate.findViewById(R.id.progress_tv);
            progressBar1 = inflate.findViewById(R.id.iv_pb1);
            ProgressBar progressBar2 = inflate.findViewById(R.id.iv_pb2);
            tv_tip.setText(msg);
            progressBar1.setVisibility(hasProgress ? View.VISIBLE : View.GONE);
            progress_tv.setVisibility(hasProgress ? View.VISIBLE : View.GONE);
            progressBar2.setVisibility(hasProgress ? View.GONE : View.VISIBLE);
            alertDialog.setView(inflate);
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.setCancelable(false);
            alertDialog.show();
        }
    }

    public void setProgress(int progress) {
        if (alertDialog != null && alertDialog.isShowing() && progressBar1 != null && progress_tv != null) {
            progressBar1.setProgress(progress);
            progress_tv.setText(String.format(Locale.CHINA, "%d%%", progress));
        }
    }

    public void dismiss() {
        if (alertDialog != null && alertDialog.isShowing()) {
            alertDialog.dismiss();
            alertDialog = null;
        }

    }

}