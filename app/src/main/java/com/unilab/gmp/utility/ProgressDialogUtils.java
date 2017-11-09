package com.unilab.gmp.utility;

/**
 * Created by c_rcmiguel on 10/2/2017.
 */

import android.app.ProgressDialog;
import android.content.Context;

public class ProgressDialogUtils {

    private static ProgressDialog mProgressDialog;

    public static void showSimpleProgressDialog(Context context, int progress,
                                                String msg, boolean isCancelable) {
        try {
            if (mProgressDialog == null) {
                mProgressDialog = new ProgressDialog(context);
//                mProgressDialog = ProgressDialog.show(context, title, msg);
//                mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//                mProgressDialog.setProgress(progress);
                mProgressDialog.setMessage(msg);
                mProgressDialog.setCancelable(isCancelable);
                mProgressDialog.show();
            }

            if (!mProgressDialog.isShowing()) {
                mProgressDialog.show();
            }

        } catch (IllegalArgumentException ie) {
            ie.printStackTrace();
        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void removeSimpleProgressDialog() {
        try {
            if (mProgressDialog != null) {
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                    mProgressDialog = null;
                }
            }
        } catch (IllegalArgumentException ie) {
            ie.printStackTrace();

        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}