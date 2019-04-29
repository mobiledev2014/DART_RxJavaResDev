package com.unilab.gmp.activity;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.orm.SugarContext;
import com.unilab.gmp.R;
import com.unilab.gmp.model.ModelAuditReports;
import com.unilab.gmp.model.ModelUser;
import com.unilab.gmp.retrofit.Async.PostAsync;
import com.unilab.gmp.utility.Glovar;
import com.unilab.gmp.utility.SharedPreferenceManager;

import java.net.InetAddress;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    public static int orientation;
    public ProgressDialog loginDialog;
    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.cb_remember)
    CheckBox cbRemember;
    @BindView(R.id.iv_indicator)
    ImageView ivIndicator;
    @BindView(R.id.tv_indicator)
    TextView tvIndicator;

    Context context;

    Dialog dialogErrorLogin;
    Dialog dialogCloseConfirmation;

    SharedPreferenceManager sharedPref;

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        orientation = getResources().getConfiguration().orientation;
        ButterKnife.bind(this);

        SugarContext.init(this);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        loginDialog = new ProgressDialog(context);
        loginDialog.setCanceledOnTouchOutside(false);
        loginDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        loginDialog.setMax(2);

        getCredentials();

        int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    this,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }

        List<ModelAuditReports> auditReps = ModelAuditReports.findWithQuery(ModelAuditReports.class,
                "SELECT * FROM MODEL_AUDIT_REPORTS ORDER BY CAST(reportid as INT) ASC", null);

        int size = auditReps.size() + 1;
        Log.i("AUDIT-REPORT-SIZE", "VALUE : " + size);
    }

    public void getCredentials() {
        sharedPref = new SharedPreferenceManager(context);
        String email = sharedPref.getStringData("EMAIL");
        String password = sharedPref.getStringData("PASSWORD");

        if (sharedPref.getBooleanData("CHECKED")) {
            cbRemember.setChecked(true);
            etUsername.setText(email);
            etPassword.setText(password);
        }
    }


    @Override
    public void onBackPressed() {
        dialogCloseConfirmation();
    }

    @OnClick(R.id.btn_login)
    public void onViewClicked() {
        String email = etUsername.getText().toString();
        String password = etPassword.getText().toString();

        boolean fieldsEmpty = false;
        if (email.equals("")) {
            etUsername.setError("This field is required.");
            fieldsEmpty = true;
        }
        if (password.equals("")) {
            etPassword.setError("This field is required.");
            fieldsEmpty = true;
        }
        if (!email.contains("@unilab.com.ph")) {
            etUsername.setError("Invalid email address. Please make sure that your email address is correct.");
            fieldsEmpty = true;
        }

        if (!fieldsEmpty) {
            Log.e("TAG", "CLICK!!!" + isInternetAvailable() + " ");
            if (isNetworkConnected()) {
                Log.e("TAGTRUE", "CLICK!!!" + isNetworkConnected() + " ");
                Log.i("ERROR", "POSTASYNC 1");
                if (cbRemember.isChecked()) {
                    sharedPref.saveData("CHECKED", true);
                    sharedPref.saveData("EMAIL", email);
                    sharedPref.saveData("PASSWORD", password);
                } else {
                    sharedPref.saveData("CHECKED", false);
                }
                new PostAsync(context, loginDialog, email, password, Glovar.LOGIN, null, null).execute();
            } else {
                Log.e("TAGFALSE", "CLICK!!!" + isNetworkConnected() + " " + email);
                List<ModelUser> users = ModelUser.find(ModelUser.class, "email = ?", email);

                String emailSp = sharedPref.getStringData("EMAIL");
                String passwordSp = sharedPref.getStringData("PASSWORD");

                if (!emailSp.equals(null) && !passwordSp.equals(null) && emailSp != null && passwordSp != null) {
                    if (emailSp.equals(email) && passwordSp.equals(password)) {

                        if (cbRemember.isChecked()) {
                            sharedPref.saveData("CHECKED", true);
                        } else {
                            sharedPref.saveData("CHECKED", false);
                        }

                        Intent intent = new Intent(context, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Log.e("TAGFALSEELSE", "CLICK!!!" + users.size());

                        if (!sharedPref.getBooleanData("CHECKED")) {
                            dialogErrorLogin("Please check your internet connection.");
                        } else {
                            if (!email.equals(emailSp)) {
                                dialogErrorLogin("Please check your internet connection.");
                            } else {
                                dialogErrorLogin("Email and password do not match.");
                            }
                        }
                    }
                }
            }
        }
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(btnLogin.getWindowToken(), 0);
    }

    //checks if internet connection is available
    private boolean isNetworkConnected() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    //for specified site
    public boolean isInternetAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName("www.google.com"); //You can replace it with your name

            Log.e("IP", "" + ipAddr.getHostAddress());
            return !ipAddr.equals("");

        } catch (Exception e) {

            Log.e("tag", "catch " + e.getLocalizedMessage());
            return false;
        }

    }

    public void dialogErrorLogin(String message) {
        dialogErrorLogin = new Dialog(context);
        dialogErrorLogin.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialogErrorLogin.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogErrorLogin.setCancelable(false);
        dialogErrorLogin.setContentView(R.layout.dialog_error_login);
        dialogErrorLogin.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        TextView tv_message = (TextView) dialogErrorLogin.findViewById(R.id.tv_message);
        Button ok = (Button) dialogErrorLogin.findViewById(R.id.btn_ok);

        tv_message.setText(message);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogErrorLogin.dismiss();
            }
        });

        dialogErrorLogin.show();
    }

    public void dialogCloseConfirmation() {
        dialogCloseConfirmation = new Dialog(context);
        dialogCloseConfirmation.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialogCloseConfirmation.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogCloseConfirmation.setCancelable(false);
        dialogCloseConfirmation.setContentView(R.layout.dialog_close_confirmation);
        dialogCloseConfirmation.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        Button yes = (Button) dialogCloseConfirmation.findViewById(R.id.btn_yes);
        Button no = (Button) dialogCloseConfirmation.findViewById(R.id.btn_no);

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogCloseConfirmation.dismiss();
            }
        });

        dialogCloseConfirmation.show();
    }

}
