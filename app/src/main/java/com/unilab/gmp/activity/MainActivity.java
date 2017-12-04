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
import android.os.CountDownTimer;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
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
import com.unilab.gmp.model.ModelUser;
import com.unilab.gmp.retrofit.Async.PostAsync;
import com.unilab.gmp.utility.Glovar;
import com.unilab.gmp.utility.SharedPreferenceManager;

import java.net.InetAddress;
import java.util.List;

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

    CountDownTimer countDownTimer;

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
//        DatabaseHelper.createDatabase(context, new AppDb());
        ButterKnife.bind(this);

        SugarContext.init(this);

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

        //checkConnectionStatus();
    }

    private void checkConnectionStatus() {
        countDownTimer = new CountDownTimer(2000, 1000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                onlineIndicator(isNetworkConnected());
                checkConnectionStatus();
            }
        }.start();
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
//        Intent intent = new Intent(context, HomeActivity.class);
//        startActivity(intent);
//        finish();

        String email = etUsername.getText().toString();
        String password = etPassword.getText().toString();

        if (cbRemember.isChecked()) {
            sharedPref.saveData("CHECKED", true);
            sharedPref.saveData("EMAIL", email);
            sharedPref.saveData("PASSWORD", password);
        } else {
            sharedPref.saveData("CHECKED", false);
        }

        boolean fieldsEmpty = false;
        if (email.equals(""))
        {
            etUsername.setError("This field is required.");
            fieldsEmpty = true;
        }
        if (password.equals(""))
        {
            etPassword.setError("This field is required.");
            fieldsEmpty = true;
        }
        if (!fieldsEmpty) {
            Log.e("TAG", "CLICK!!!" + isInternetAvailable() + " ");
            if (isNetworkConnected()) {
                Log.e("TAGTRUE", "CLICK!!!" + isNetworkConnected() + " ");
                Log.i("ERROR", "POSTASYNC 1");
                //if (isInternetAvailable())
                    new PostAsync(context, loginDialog, email, password, Glovar.LOGIN, null, null).execute();
                //else
                    //dialogErrorLogin();
            } else {
                Log.e("TAGFALSE", "CLICK!!!" + isNetworkConnected() + " " + email);
                List<ModelUser> users = ModelUser.find(ModelUser.class, "email = ?", email);
                boolean found = false;
//            List<ModelUser> users = ModelUser.listAll(ModelUser.class);
//            for (int i = 0; i < users.size(); i++) {
//                Log.e("ModelUser", "email : " + users.get(i).getEmail() + "");
//                if (users.get(i).getEmail().equals(email))
//                {
//                    found = true;
//                }
//            }


                if (users.size() > 0) {
//            if (found) {
                    Log.e("Users count", users.size() + " name : " + users.get(0).getEmail());
//                if (users.get(0).getEmail().equals(email)) {
                    Intent intent = new Intent(context, HomeActivity.class);
                    startActivity(intent);
                    finish();
//                }
                } else
                //Toast.makeText(context, "User does not exist.", Toast.LENGTH_SHORT).show();
                {
                    Log.e("TAGFALSEELSE", "CLICK!!!" + users.size());

                    dialogErrorLogin();
                }
            }
        }

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(btnLogin.getWindowToken(), 0);
    }

    //checks if internet connection is available
    private boolean isNetworkConnected() {
        /*ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;*/

        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    //for specified site
    public boolean isInternetAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName("www.google.com"); //You can replace it with your name

            Log.e("IP",""+ipAddr.getHostAddress());
            return !ipAddr.equals("");

        } catch (Exception e) {

            Log.e("tag","catch "+e.getLocalizedMessage());
            return false;
        }

    }

    public void dialogErrorLogin() {
        dialogErrorLogin = new Dialog(context);
        dialogErrorLogin.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialogErrorLogin.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogErrorLogin.setCancelable(false);
        dialogErrorLogin.setContentView(R.layout.dialog_error_login);
        dialogErrorLogin.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        String message = "No internet connection. Make sure Wi-Fi or cellular data is turned on, then try again.";

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

    public void onlineIndicator(boolean ind) {
        if (ind) {
            tvIndicator.setText("ONLINE");
            tvIndicator.setTextColor(Color.parseColor("#2da82d"));
            ivIndicator.setBackgroundResource(R.drawable.ic_online);
        } else {
            tvIndicator.setText("OFFLINE");
            tvIndicator.setTextColor(Color.parseColor("#ff0000"));
            ivIndicator.setBackgroundResource(R.drawable.ic_offline);
        }
    }
}
