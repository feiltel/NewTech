package com.nut2014.newtech.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.nut2014.newtech.R;

/**
 * @author feiltel 2020/4/9 0009
 */
public class FProgressDialog {
    private static final FProgressDialog ourInstance = new FProgressDialog();


    public static FProgressDialog getInstance() {
        return ourInstance;
    }

    private FProgressDialog() {
    }

    private AlertDialog alertDialog;
    private ProgressBar progressBar1;

    public void show(Context context, String msg, boolean hasProgress) {
        if (alertDialog == null) {
            alertDialog = new AlertDialog.Builder(context, R.style.ProgressDialog).create();
            View inflate = LayoutInflater.from(context).inflate(R.layout.layout_alert_progress, null);
            TextView tv_tip = inflate.findViewById(R.id.tv_tip);
            progressBar1 = inflate.findViewById(R.id.iv_pb1);
            ProgressBar progressBar2 = inflate.findViewById(R.id.iv_pb2);
            tv_tip.setText(msg);
            progressBar1.setVisibility(hasProgress ? View.VISIBLE : View.GONE);
            progressBar2.setVisibility(hasProgress ? View.GONE : View.VISIBLE);
            alertDialog.setView(inflate);
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.setCancelable(false);
            alertDialog.show();
        }
    }

    public void setProgress(int progress) {
        if (alertDialog != null && alertDialog.isShowing() && progressBar1 != null) {
            progressBar1.setProgress(progress);
        }
    }

    public void dismiss() {
        if (alertDialog != null && alertDialog.isShowing()) {
            alertDialog.dismiss();
            alertDialog = null;
        }

    }

}
