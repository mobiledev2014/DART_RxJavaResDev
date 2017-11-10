package com.unilab.gmp.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.unilab.gmp.R;
import com.unilab.gmp.fragment.AuditReportFragment;
import com.unilab.gmp.fragment.AuditorsFragment;
import com.unilab.gmp.fragment.HomeFragment;
import com.unilab.gmp.fragment.ReferenceDataFragment;
import com.unilab.gmp.fragment.SelectedTemplateFragment;
import com.unilab.gmp.fragment.TemplateFragment;
import com.unilab.gmp.model.ModelUser;
import com.unilab.gmp.utility.APICalls;
import com.unilab.gmp.utility.SharedPreferenceManager;
import com.unilab.gmp.utility.Variable;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends AppCompatActivity {

    public static ProgressDialog pDialog;
    Context context;
    @BindView(R.id.iv_logout)
    ImageView ivLogout;
    @BindView(R.id.iv_sync)
    ImageView ivSync;
    @BindView(R.id.fl_content)
    FrameLayout flContent;
    @BindView(R.id.iv_template)
    ImageView ivTemplate;
    @BindView(R.id.iv_reference_data)
    ImageView ivReferenceData;
    @BindView(R.id.iv_audit_report)
    ImageView ivAuditReport;
    TemplateFragment templateFragment;
    AuditReportFragment auditReportFragment;
    AuditorsFragment auditorsFragment;
    ReferenceDataFragment referenceDataFragment;
    SelectedTemplateFragment selectedTemplateFragment;
    HomeFragment homeFragment;
    String selected = "";
    Dialog dialogCloseConfirmation;
    Dialog dialogCancelTemplate;
    Dialog dialogSyncConfirmation;
    Dialog dialogErrorLogin;
    SharedPreferenceManager sharedPref;
    @BindView(R.id.iv_logo)
    ImageView ivLogo;
    @BindView(R.id.iv_indicator)
    ImageView ivIndicator;
    @BindView(R.id.tv_indicator)
    TextView tvIndicator;
    @BindView(R.id.ll_online_indicator)
    LinearLayout llOnlineIndicator;
    //@BindView(R.id.tv_sync_notif_count)
    public static TextView tvSyncNotifCount;

    CountDownTimer countDownTimer;

    String newTemplates = "";

    public HomeActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        context = this;
        ButterKnife.bind(this);

        tvSyncNotifCount = (TextView) findViewById(R.id.tv_sync_notif_count);

        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Loading please wait...");
        pDialog.setCancelable(false);

        homeFragment = new HomeFragment();
        templateFragment = new TemplateFragment();

        newTemplates = getIntent().getStringExtra("NEWTEMPLATE");

        if (!newTemplates.equals("")) {
            if (Integer.parseInt(newTemplates) > 0) {
                tvSyncNotifCount.setText(Integer.parseInt(getIntent().getStringExtra("NEWTEMPLATE")) + "");
                tvSyncNotifCount.setVisibility(View.VISIBLE);
            }
        }

        if (savedInstanceState == null) {
            buttonSelector("template");
            selected = "template";
            getSupportFragmentManager().beginTransaction().add(R.id.fl_content, homeFragment).addToBackStack(null).commit();
        }
        initializeHome();

        checkConnectionStatus();
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

    public void initializeHome() {
        auditReportFragment = new AuditReportFragment();
        auditorsFragment = new AuditorsFragment();
        referenceDataFragment = new ReferenceDataFragment();
        //selectedTemplateFragment = new SelectedTemplateFragment();

        sharedPref = new SharedPreferenceManager(context);
//        int emp_id = sharedPref.getIntData("EMP_ID");
        String email = sharedPref.getStringData("EMAIL");
        String password = sharedPref.getStringData("PASSWORD");

//        List<ModelUser> users = ModelUser.find(ModelUser.class, "empid = ?", emp_id + "");
        List<ModelUser> users = ModelUser.find(ModelUser.class, "email = ?", email + "");
        if (users.size() <= 0) {
            ModelUser modelUser = new ModelUser();
//            modelUser.setEmp_id(emp_id);
            modelUser.setEmail(email);
            modelUser.setPassword(password);
            modelUser.save();
            Log.e("UserSaved", " name : " + modelUser.getEmail() + " id : ");
        } else {
            Log.e("UserFound", "found");
        }
    }

    /*@Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        getSupportFragmentManager().beginTransaction().add(R.id.fl_content, selectedTemplateFragment).addToBackStack(null).commit();
    }*/

    @Override
    public void onBackPressed() {
        if (Variable.onTemplate) {
            if (Variable.onAudit) {
                //Toast.makeText(context, "On Template", Toast.LENGTH_SHORT).show();
                dialogCancelTemplate(auditReportFragment, "audit report");
            } else {
                dialogCancelTemplate(templateFragment, "template");
            }
        } else {
            if (Variable.menu) {
                buttonSelector("template");
                selected = "template";
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                fragmentManager.beginTransaction().replace(R.id.fl_content, homeFragment).addToBackStack(null).commit();
                Log.i("TAG", "EXIT");
            } else {
                dialogCloseConfirmation("Are you sure you want to close the application?");
            }
        }
    }

    @OnClick({R.id.iv_sync, R.id.iv_logout, R.id.iv_template, R.id.iv_reference_data, R.id.iv_audit_report})
    public void onViewClicked(View view) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        switch (view.getId()) {
            case R.id.iv_sync:
                Log.i("newTemplates", newTemplates);

                if (!newTemplates.equals("") && !newTemplates.equals("0")) {
                    fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    fragmentManager.beginTransaction()
                            .replace(R.id.fl_content, templateFragment).addToBackStack(null).commit();
                    newTemplates = "";
                    tvSyncNotifCount.setText("");
                } else {
                    dialogSyncConfirmation("Are you sure you want to sync data?");
                }
                break;
            case R.id.iv_logout:
                dialogCloseConfirmation("Are you sure you want to log out?");
                break;
            case R.id.iv_template:
                if (Variable.onTemplate) {
                    dialogCancelTemplate(templateFragment, "template");
                } else {
                    if (!selected.equals("template")) {
                        buttonSelector("template");
                        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        fragmentManager.beginTransaction()
                                .replace(R.id.fl_content, templateFragment).addToBackStack(null).commit();
                        selected = "template";
                    }
                }
                break;
            case R.id.iv_reference_data:
                if (Variable.onTemplate) {
                    dialogCancelTemplate(referenceDataFragment, "reference");
                } else {
                    if (!selected.equals("reference")) {
                        buttonSelector("reference");
                        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        fragmentManager.beginTransaction()
                                .replace(R.id.fl_content, referenceDataFragment).addToBackStack(null).commit();
                        selected = "reference";
                    } else {
                        if (Variable.onReferenceData) {
                            buttonSelector("reference");
                            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                            fragmentManager.beginTransaction()
                                    .replace(R.id.fl_content, referenceDataFragment).addToBackStack(null).commit();
                            selected = "reference";
                        } else {
                            Log.i("R E F E R E N C E : ", String.valueOf(Variable.onReferenceData));
                        }
                    }
                }

                break;
            case R.id.iv_audit_report:
//                NotificationCreator notificationCreator = new NotificationCreator(context);
//                notificationCreator.createNotification();
                if (Variable.onTemplate) {
                    dialogCancelTemplate(auditReportFragment, "audit");
                } else {
                    if (!selected.equals("audit")) {
                        buttonSelector("audit");
                        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        fragmentManager.beginTransaction()
                                .replace(R.id.fl_content, auditReportFragment).addToBackStack(null).commit();
                        selected = "audit";
                    }
                }
                break;
        }
    }

    public void buttonSelector(String selected) {
        if (selected.equals("template")) {
            ivTemplate.setImageDrawable(getBaseContext().getResources().getDrawable(R.drawable.btn_template_pressed));
            ivReferenceData.setImageDrawable(getBaseContext().getResources().getDrawable(R.drawable.btn_referencedata));
            ivAuditReport.setImageDrawable(getBaseContext().getResources().getDrawable(R.drawable.btn_auditreports));
        } else if (selected.equals("reference")) {
            ivTemplate.setImageDrawable(getBaseContext().getResources().getDrawable(R.drawable.btn_template));
            ivReferenceData.setImageDrawable(getBaseContext().getResources().getDrawable(R.drawable.btn_referencedata_pressed));
            ivAuditReport.setImageDrawable(getBaseContext().getResources().getDrawable(R.drawable.btn_auditreports));
        } else if (selected.equals("audit")) {
            ivTemplate.setImageDrawable(getBaseContext().getResources().getDrawable(R.drawable.btn_template));
            ivReferenceData.setImageDrawable(getBaseContext().getResources().getDrawable(R.drawable.btn_referencedata));
            ivAuditReport.setImageDrawable(getBaseContext().getResources().getDrawable(R.drawable.btn_auditreports_pressed));
        }
    }

    public void dialogCloseConfirmation(String mess) {
        dialogCloseConfirmation = new Dialog(context);
        dialogCloseConfirmation.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialogCloseConfirmation.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogCloseConfirmation.setCancelable(false);
        dialogCloseConfirmation.setContentView(R.layout.dialog_exit_confirmation);
        dialogCloseConfirmation.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        TextView msg = (TextView) dialogCloseConfirmation.findViewById(R.id.tv_message);
        Button yes = (Button) dialogCloseConfirmation.findViewById(R.id.btn_yes);
        Button no = (Button) dialogCloseConfirmation.findViewById(R.id.btn_no);

        msg.setText(mess);

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
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

    public void dialogCancelTemplate(final Fragment selectedFragment, final String selectedFragmentText) {
        dialogCancelTemplate = new Dialog(context);
        dialogCancelTemplate.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialogCancelTemplate.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogCancelTemplate.setCancelable(false);
        dialogCancelTemplate.setContentView(R.layout.dialog_cancel_template_confirmation);
        dialogCancelTemplate.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        Button yes = (Button) dialogCancelTemplate.findViewById(R.id.btn_yes);
        Button no = (Button) dialogCancelTemplate.findViewById(R.id.btn_no);

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                buttonSelector(selectedFragmentText);
                selected = selectedFragmentText;
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                fragmentManager.beginTransaction()
                        .replace(R.id.fl_content, selectedFragment).addToBackStack(null).commit();
                dialogCancelTemplate.dismiss();
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogCancelTemplate.dismiss();
            }
        });


        dialogCancelTemplate.show();
    }

    public void dialogSyncConfirmation(String mess) {
        dialogSyncConfirmation = new Dialog(context);
        dialogSyncConfirmation.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialogSyncConfirmation.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogSyncConfirmation.setCancelable(false);
        dialogSyncConfirmation.setContentView(R.layout.dialog_exit_confirmation);
        dialogSyncConfirmation.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        TextView msg = (TextView) dialogSyncConfirmation.findViewById(R.id.tv_message);
        Button yes = (Button) dialogSyncConfirmation.findViewById(R.id.btn_yes);
        Button no = (Button) dialogSyncConfirmation.findViewById(R.id.btn_no);

        msg.setText(mess);

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogSyncConfirmation.dismiss();
                //check internet connection
                if (isNetworkConnected()) {
                    new APICalls(context, "Syncing...", true, HomeActivity.this).execute();
                } else {
                    dialogErrorLogin();
                }
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogSyncConfirmation.dismiss();
            }
        });


        dialogSyncConfirmation.show();
    }

    private boolean isNetworkConnected() {
        /*ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;*/

        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
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

    @OnClick(R.id.iv_logo)
    public void onViewClicked() {
        if (Variable.menu) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            fragmentManager.beginTransaction()
                    .replace(R.id.fl_content, new HomeFragment()).addToBackStack(null).commit();
        }

        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(ivLogo.getWindowToken(), 0);
    }

    public void onlineIndicator(boolean ind) {
        llOnlineIndicator.setVisibility(View.VISIBLE);
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
