package com.unilab.gmp.utility;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.unilab.gmp.R;
import com.unilab.gmp.retrofit.ApiInterface;

import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Utils {
    //public static String pdfPath = System.getenv("EXTERNAL_STORAGE") + "/DART/AuditReports/";
    public static String pdfPath = System.getenv("EXTERNAL_STORAGE") + "/DART/AuditReports/";
    public static Dialog dialogError;
    public static Dialog dialogSelectPdf;
//
//    public static void snack(View view, String text) {
//        Snackbar.make(view, text, Snackbar.LENGTH_SHORT).show();
//    }

    public static void toast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    public static void anim(Context context, TextView textView,
                            int offsetTimeMillis, int animResource) {
        Animation a = AnimationUtils.loadAnimation(context, animResource);
        a.setStartOffset(offsetTimeMillis);
        textView.startAnimation(a);
    }

    public static void anim(Context context, ImageView imageView,
                            int offsetTimeMillis, int animResource) {
        Animation a = AnimationUtils.loadAnimation(context, animResource);
        a.setStartOffset(offsetTimeMillis);
        imageView.startAnimation(a);
    }

    public static void animMove(Context context, ImageView imageview,
                                float fromXDelta, float toXDelta, float fromYDelta, float toYDelta,
                                long durationMillis, long offSetdurationMillis) {
        TranslateAnimation animation = new TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_PARENT, fromXDelta,
                TranslateAnimation.RELATIVE_TO_PARENT, toXDelta,
                TranslateAnimation.RELATIVE_TO_PARENT, fromYDelta,
                TranslateAnimation.RELATIVE_TO_PARENT, toYDelta);
        animation.setDuration(durationMillis);
        animation.setStartOffset(offSetdurationMillis);
        animation.setFillAfter(true);
        animation.setRepeatCount(Animation.INFINITE);
        animation.setRepeatMode(Animation.RESTART);
        imageview.startAnimation(animation);
    }

    public static Animation animMoveStop(Context context, ImageView imageview,
                                         float fromXDelta, float toXDelta, float fromYDelta, float toYDelta,
                                         long durationMillis, long offSetdurationMillis) {
        TranslateAnimation animation = new TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_PARENT, fromXDelta,
                TranslateAnimation.RELATIVE_TO_PARENT, toXDelta,
                TranslateAnimation.RELATIVE_TO_PARENT, fromYDelta,
                TranslateAnimation.RELATIVE_TO_PARENT, toYDelta);
        animation.setDuration(durationMillis);
        animation.setStartOffset(offSetdurationMillis);
        animation.setFillAfter(true);
        animation.setRepeatCount(Animation.INFINITE);
        animation.setRepeatMode(Animation.RESTART);
        imageview.startAnimation(animation);

        return animation;
    }

    public static Animation animMoveStop(Context context, TextView textView,
                                         float fromXDelta, float toXDelta, float fromYDelta, float toYDelta,
                                         long durationMillis, long offSetdurationMillis) {
        TranslateAnimation animation = new TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_PARENT, fromXDelta,
                TranslateAnimation.RELATIVE_TO_PARENT, toXDelta,
                TranslateAnimation.RELATIVE_TO_PARENT, fromYDelta,
                TranslateAnimation.RELATIVE_TO_PARENT, toYDelta);
        animation.setDuration(durationMillis);
        animation.setStartOffset(offSetdurationMillis);
        animation.setFillAfter(true);
        animation.setRepeatCount(Animation.INFINITE);
        animation.setRepeatMode(Animation.RESTART);
        textView.startAnimation(animation);

        return animation;
    }

    public static Animation animMoveStop(Context context, Button button,
                                         float fromXDelta, float toXDelta, float fromYDelta, float toYDelta,
                                         long durationMillis, long offSetdurationMillis) {
        TranslateAnimation animation = new TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_PARENT, fromXDelta,
                TranslateAnimation.RELATIVE_TO_PARENT, toXDelta,
                TranslateAnimation.RELATIVE_TO_PARENT, fromYDelta,
                TranslateAnimation.RELATIVE_TO_PARENT, toYDelta);
        animation.setDuration(durationMillis);
        animation.setStartOffset(offSetdurationMillis);
        animation.setFillAfter(true);
        animation.setRepeatCount(Animation.INFINITE);
        animation.setRepeatMode(Animation.RESTART);
        button.startAnimation(animation);

        return animation;
    }

    public static void animMove(Context context, Button button,
                                float fromXDelta, float toXDelta, float fromYDelta, float toYDelta,
                                long durationMillis, long offSetdurationMillis) {
        TranslateAnimation animation = new TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_PARENT, fromXDelta,
                TranslateAnimation.RELATIVE_TO_PARENT, toXDelta,
                TranslateAnimation.RELATIVE_TO_PARENT, fromYDelta,
                TranslateAnimation.RELATIVE_TO_PARENT, toYDelta);
        animation.setDuration(durationMillis);
        animation.setStartOffset(offSetdurationMillis);
        animation.setFillAfter(true);
        button.startAnimation(animation);
    }

    public static void animShake(Context context, LinearLayout llt_layout) {
        TranslateAnimation animation = new TranslateAnimation(-15f, 15f, 00, 0);
        animation.setDuration(50);
        animation.setRepeatCount(3);
        animation.setRepeatMode(Animation.REVERSE);
        llt_layout.startAnimation(animation);
    }

    public static void animMove(Context context, TextView textView,
                                float fromXDelta, float toXDelta, float fromYDelta, float toYDelta,
                                long durationMillis, long offSetdurationMillis) {
        TranslateAnimation animation = new TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_PARENT, fromXDelta,
                TranslateAnimation.RELATIVE_TO_PARENT, toXDelta,
                TranslateAnimation.RELATIVE_TO_PARENT, fromYDelta,
                TranslateAnimation.RELATIVE_TO_PARENT, toYDelta);
        animation.setDuration(durationMillis);
        animation.setStartOffset(offSetdurationMillis);
        animation.setFillAfter(true);
        textView.startAnimation(animation);
    }

    public static void animMove(View v, float fromXDelta, float toXDelta,
                                float fromYDelta, float toYDelta, long durationMillis) {
        TranslateAnimation animation = new TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_PARENT, fromXDelta,
                TranslateAnimation.RELATIVE_TO_PARENT, toXDelta,
                TranslateAnimation.RELATIVE_TO_PARENT, fromYDelta,
                TranslateAnimation.RELATIVE_TO_PARENT, toYDelta);
        animation.setDuration(durationMillis);
        animation.setFillAfter(true);
        v.startAnimation(animation);
    }

    static void shuffleArray(int[] ar) {
        Random rnd = new Random();
        for (int i = ar.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            int a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }

    static void shuffleArray(String[] ar) {
        Random rnd = new Random();
        for (int i = ar.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            String a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }

//    public static void replaceParentFragment(FragmentManager fm,
//                                             Fragment fragment) {
//        fm.beginTransaction().replace(R.id.container, fragment).commit();
//    }

    /*public static void replaceFragment(FragmentManager fm, Fragment fragment) {
        fm.beginTransaction().replace(R.id.container_main_screen, fragment)
                .commit();
    }*/

    public static ApiInterface initializeRetrofit() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Glovar.URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        return apiInterface;
    }

    public static boolean isNetworkAvailable(final Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    public static boolean isURLExists() {
        try {
            HttpURLConnection.setFollowRedirects(false);
            HttpURLConnection con = (HttpURLConnection) new URL(Glovar.URL).openConnection();
            con.setConnectTimeout(1000);
            con.setReadTimeout(1000);
            con.setRequestMethod("HEAD");
            return (con.getResponseCode() == HttpURLConnection.HTTP_OK);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String checkIfNull(String string) {
        if (string == null)
            string = "";
        return string;
    }

    public static void dialogAlert(Context context, String title, String message, final Runnable negative, final Runnable positive, boolean okButtonOnly) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setMessage(message);

        if (okButtonOnly) {
            alertDialogBuilder.setNeutralButton("OK", null);
        } else {

            alertDialogBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (negative != null)
                        negative.run();
                }
            });
            alertDialogBuilder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (positive != null)
                        positive.run();
                }
            });
        }
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public static void setImageViewGrayScale(ImageView imageView, boolean setEnabled) {

        if (setEnabled) {
            imageView.setColorFilter(null);
            imageView.setAlpha(255);
        } else {
            ColorMatrix matrix = new ColorMatrix();
            matrix.setSaturation(0);  //0 means grayscale
            ColorMatrixColorFilter cf = new ColorMatrixColorFilter(matrix);
            imageView.setColorFilter(cf);
//            imageView.setAlpha(128);   // 128 = 0.5
        }
    }

    public static boolean validateEmailFormat(String emailStr) {
        Pattern pattern =
                Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

        Matcher matcher = pattern.matcher(emailStr);
        return matcher.find();
    }

    public static void delayRunnable(Runnable runnable, int seconds) {
        Handler mHandler;
        mHandler = new Handler();
        mHandler.postDelayed(runnable, seconds * 1000);

    }

    public static String properNameFormat(String name) {
        String properName = "";
        String[] splitName = name.trim().split(" ");
        for (String sName : splitName) {
            String fName = String.valueOf(sName.charAt(0)).toUpperCase(Locale.US);
            String eName = sName.substring(1, sName.length()).toLowerCase(Locale.US);
            properName += (fName + eName + " ");
        }

        return properName.trim();
    }

    public static String dateBuilder(String year, String month, String day) {
        String m = "" + (Integer.parseInt(month) + 1);
        String d = (day.length() > 1) ? day : "0" + day;
        String date = year + "-" + ((m.length() > 1) ? m : "0" + m) + "-" + d;
        return date;
    }

    public static Dialog setDialogSizeByOrientation(int orientation, Dialog dialog) {
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();

        // WHEN ORIENTATION CHANGED, MODIFY SIZE OF DIALOG
        lp.copyFrom(dialog.getWindow().getAttributes());
        if (orientation == 1) {
            lp.width = (int) (Glovar.SCREEN_WIDTH / 1.05);
            lp.height = (int) (Glovar.SCREEN_HEIGHT / 1.9);
        } else {
            lp.width = (int) (Glovar.SCREEN_WIDTH / 1.9);
            lp.height = (int) (Glovar.SCREEN_HEIGHT / 1.05);
        }
        dialog.getWindow().setAttributes(lp);
        return dialog;
    }

    public static Dialog setDialogSize(int orientation, Dialog dialog) {
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();

        // WHEN ORIENTATION CHANGED, MODIFY SIZE OF DIALOG
        lp.copyFrom(dialog.getWindow().getAttributes());

        if (Glovar.SCREEN_SIZE < 3) {
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        } else {
            if (orientation == 1) {
                lp.width = (int) (Glovar.SCREEN_WIDTH / 1.3);
                lp.height = (int) (Glovar.SCREEN_HEIGHT / 1.9);
            } else {
                lp.width = (int) (Glovar.SCREEN_WIDTH / 1.8);
                lp.height = (int) (Glovar.SCREEN_HEIGHT / 1.5);
            }
        }


        dialog.getWindow().setAttributes(lp);

        return dialog;
    }


    public static String[] yearArray() {
        int startYear = 2050;
        String[] years = new String[152];

        years[0] = "Select Year";
        for (int i = 1; i < years.length; i++) {
            years[i] = String.valueOf(startYear--);
        }

        return years;
    }

    public static String[] monthArray() {
        String[] years = new String[]{"Select Month", "January", "February", "March", "April",
                "May", "June", "July", "August", "September", "October", "November", "December"};

        return years;
    }

    public static String[] productArray() {
        String[] products = new String[]{"Algesia", "Amoclav", "Andros", "Atepros", "Exigo", "Forgram",
                "Hemostan", "Inoflox", "Klindex", "Koretezol", "Merop", "Prozelax",
                "Renogen", "Siverol", "Stancef", "Tascit"};

        return products;
    }

    public static int getIndex(Spinner spinner, String myString) {
        int index = 0;
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).equals(myString)) {
                index = i;
            }
        }
        return index;
    }

    public static int getProductIndex(String myProduct) {
        int index = 0;
        for (int i = 0; i < productArray().length; i++) {
            if (productArray()[i].equals(myProduct)) {
                index = i;
            }
        }
        return index;
    }

    public static void hideKeyboard(Context ctx) {
        InputMethodManager inputManager = (InputMethodManager) ctx
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        // check if no view has focus:
        View v = ((Activity) ctx).getCurrentFocus();
        if (v == null)
            return;

        inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    public static String dateStamp() {
        return new SimpleDateFormat("yyyy-MM-dd").format(Calendar
                .getInstance().getTime());
    }

    public static void pdfIfExist(String id, Context context) {

        File file_Annexure = new File(pdfPath + "/" + id + "_Annexure.pdf");
        File file_Audit_Report = new File(pdfPath + "/" + id + "_Audit_Report.pdf");
        File file_Executive_Report = new File(pdfPath + "/" + id + "_Executive_Report.pdf");
        File dirFile = new File(pdfPath);

        Log.i("file location", file_Annexure.toString() + "");
        dirFile.mkdir();
        if (file_Annexure.exists() && file_Audit_Report.exists() && file_Executive_Report.exists()) {
            dialogSelectPdf(id, context);
            //openPdf(context, id);
        } else {
            Log.i("File", "Not existing");
            if (isNetworkConnected(context)) {
                //Toast.makeText(context, "Downloading file online", Toast.LENGTH_SHORT).show();

                String annexure = "Annexure";
                String audit_report = "Audit_Report";
                String executive_report = "Executive_Report";

                DownloadFile downloadFileAnnexure = new DownloadFile(context, id, annexure);
                downloadFileAnnexure.execute("https://sams.unilab.com.ph/json/export/approved/" + id + "/Annexure.pdf");

                DownloadFile downloadFileAudit_Report = new DownloadFile(context, id, audit_report);
                downloadFileAudit_Report.execute("https://sams.unilab.com.ph/json/export/approved/" + id + "/Audit_Report.pdf");

                DownloadFile downloadFileExecutive_Report = new DownloadFile(context, id, executive_report);
                downloadFileExecutive_Report.execute("https://sams.unilab.com.ph/json/export/approved/" + id + "/Executive_Report.pdf");

                //after download openPdf
            } else {
                //not connected
                dialogError(context);
            }
        }
    }

    public static void openPdf(Context context, String id) {
        File pdfFile = new File(pdfPath + id + ".pdf");
        Uri path = Uri.fromFile(pdfFile);

        // Setting the intent for pdf reader
        Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
        pdfIntent.setDataAndType(path, "application/pdf");
        pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        pdfIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        try {
            context.startActivity(pdfIntent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, "Can't read pdf file", Toast.LENGTH_SHORT).show();
        }
    }

    private static boolean isNetworkConnected(Context context) {
        /*ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;*/

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public static void dialogError(Context context) {
        dialogError = new Dialog(context);
        dialogError.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialogError.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogError.setCancelable(false);
        dialogError.setContentView(R.layout.dialog_error_login);
        dialogError.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        String message = "No internet connection. Make sure Wi-Fi or cellular data is turned on, then try again.";

        TextView tv_message = (TextView) dialogError.findViewById(R.id.tv_message);
        Button ok = (Button) dialogError.findViewById(R.id.btn_ok);

        tv_message.setText(message);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogError.dismiss();
            }
        });

        dialogError.show();
    }

    public static void dialogSelectPdf(final String id, final Context context) {
        dialogSelectPdf = new Dialog(context);
        dialogSelectPdf.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialogSelectPdf.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogSelectPdf.setCancelable(false);
        dialogSelectPdf.setContentView(R.layout.dialog_pdf_picker);
        dialogSelectPdf.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        Button audit = (Button) dialogSelectPdf.findViewById(R.id.btn_audit_report);
        Button executive = (Button) dialogSelectPdf.findViewById(R.id.btn_executive_summary);
        Button annexure = (Button) dialogSelectPdf.findViewById(R.id.btn_annexure);
        Button cancel = (Button) dialogSelectPdf.findViewById(R.id.btn_cancel);

        audit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.openPdf(context, id + "_Audit_Report");
                //dialogSelectPdf.dismiss();
            }
        });

        executive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.openPdf(context, id + "_Executive_Report");
                //dialogSelectPdf.dismiss();
            }
        });

        annexure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.openPdf(context, id + "_Annexure");
                //dialogSelectPdf.dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogSelectPdf.dismiss();
            }
        });


        dialogSelectPdf.show();
    }
}
