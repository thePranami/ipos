package com.loopin.ipos.Utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;

import androidx.annotation.NonNull;

import com.loopin.ipos.R;

public class IposProgress {
    public static ProgressDialog progressDialog;

    public static ProgressDialog progressDialog(@NonNull Activity activity) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(activity);
            progressDialog.setCancelable(false);
            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    progressDialog = null;
                }
            });
            try {
                progressDialog.show();
            } catch (Exception e) {
            }
        }
        return progressDialog;
    }

    public static ProgressDialog transParentDialog(@NonNull Activity activity) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(activity, R.style.CustomTransparentDialog);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    progressDialog = null;
                }
            });
            try {
                progressDialog.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return progressDialog;
    }

    public static void stop() {
        if (progressDialog != null && progressDialog.isShowing()) {
            try {
                progressDialog.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        progressDialog = null;
    }
}
