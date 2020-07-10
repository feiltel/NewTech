package com.nut2014.baselibrary.utils;

import android.app.Activity;
import android.content.pm.PackageManager;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

/**
 * @author feiltel 2020/4/8 0008
 */
public class FPermission {
    /**
     * group:android.permission-group.CONTACTS
     * permission:android.permission.WRITE_CONTACTS
     * permission:android.permission.GET_ACCOUNTS
     * permission:android.permission.READ_CONTACTS
     * <p>
     * group:android.permission-group.PHONE
     * permission:android.permission.READ_CALL_LOG
     * permission:android.permission.READ_PHONE_STATE
     * permission:android.permission.CALL_PHONE
     * permission:android.permission.WRITE_CALL_LOG
     * permission:android.permission.USE_SIP
     * permission:android.permission.PROCESS_OUTGOING_CALLS
     * permission:com.android.voicemail.permission.ADD_VOICEMAIL
     * <p>
     * group:android.permission-group.CALENDAR
     * permission:android.permission.READ_CALENDAR
     * permission:android.permission.WRITE_CALENDAR
     * <p>
     * group:android.permission-group.CAMERA
     * permission:android.permission.CAMERA
     * <p>
     * group:android.permission-group.SENSORS
     * permission:android.permission.BODY_SENSORS
     * <p>
     * group:android.permission-group.LOCATION
     * permission:android.permission.ACCESS_FINE_LOCATION
     * permission:android.permission.ACCESS_COARSE_LOCATION
     * <p>
     * group:android.permission-group.STORAGE
     * permission:android.permission.READ_EXTERNAL_STORAGE
     * permission:android.permission.WRITE_EXTERNAL_STORAGE
     * <p>
     * group:android.permission-group.MICROPHONE
     * permission:android.permission.RECORD_AUDIO
     * <p>
     * group:android.permission-group.SMS
     * permission:android.permission.READ_SMS
     * permission:android.permission.RECEIVE_WAP_PUSH
     * permission:android.permission.RECEIVE_MMS
     * permission:android.permission.RECEIVE_SMS
     * permission:android.permission.SEND_SMS
     * permission:android.permission.READ_CELL_BROADCASTS
     */
    /*使用方法
    1.  FPermission.getInstance().checkPermission(CompressActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE_REQUEST_CODE, new FPermission.FPermissionCallBack() {
                @Override
                public void granted() {
                    compressAct();
                }

                @Override
                public void refuse() {
                    showToast("存储权限被拒绝");
                }
            });
      2.
        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            FPermission.getInstance().onRequestResult(requestCode, permissions, grantResults);
        }

     */
    private static FPermission manager;

    public static FPermission getInstance() {
        if (manager == null) {
            synchronized (FPermission.class) {
                if (manager == null) {
                    manager = new FPermission();
                }
            }
        }
        return manager;
    }

    private CallBack callBack;

    private int requestCode = 0;

    public void checkPermission(final @NonNull Activity activity, @NonNull String permission, final @IntRange(from = 0) int requestCode, CallBack callBack) {
        this.requestCode = requestCode;
        this.callBack = callBack;
        if (ContextCompat.checkSelfPermission(activity, permission)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                new AlertDialog.Builder(activity)
                        .setTitle("请求权限")
                        .setMessage("需要获取权限保证软件正常使用")
                        .setPositiveButton("确定", (dialog, which) -> ActivityCompat.requestPermissions(activity,
                                new String[]{permission},
                                requestCode)).show();
            } else {
                //请求权限
                ActivityCompat.requestPermissions(activity,
                        new String[]{permission},
                        requestCode);
            }
        } else {
            //有权限访问
            if (callBack != null) {
                callBack.granted();
            }
        }
    }

    public void checkPermission(final @NonNull Activity activity, @NonNull String[] permissions, final @IntRange(from = 0) int requestCode, CallBack callBack) {
        this.requestCode = requestCode;
        this.callBack = callBack;
        if (!checkGranted(activity, permissions)) {
            //请求权限
            ActivityCompat.requestPermissions(activity,
                    permissions,
                    requestCode);
        } else {
            //有权限访问
            if (callBack != null) {
                callBack.granted();
            }
        }
    }

    private boolean checkGranted(Activity activity, String[] permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(activity, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    public interface CallBack {
        void granted();//允许

        void refuse();//拒绝
    }

    private boolean checkPass(@NonNull int[] grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 多权限检查 只有所以权限都通过才返回已授权
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    public void onRequestResults(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (this.requestCode == requestCode) {

            if (checkPass(grantResults)) {
                if (callBack != null) {
                    callBack.granted();
                }
            } else {
                if (callBack != null) {
                    callBack.refuse();
                }
            }
        }
    }

    public void onRequestResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (this.requestCode == requestCode) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (callBack != null) {
                    callBack.granted();
                }
            } else {
                if (callBack != null) {
                    callBack.refuse();
                }
            }
        }
    }

}
