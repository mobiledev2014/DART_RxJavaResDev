package com.unilab.gmp.retrofit.Async;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.unilab.gmp.R;
import com.unilab.gmp.activity.HomeActivity;
import com.unilab.gmp.activity.MainActivity;
import com.unilab.gmp.fragment.AuditReportFragment;
import com.unilab.gmp.fragment.NextSelectedAuditReportFragment;
import com.unilab.gmp.fragment.NextSelectedTemplateFragment;
import com.unilab.gmp.fragment.TemplateFragment;
import com.unilab.gmp.model.ModelUser;
import com.unilab.gmp.retrofit.user.ResultUser;
import com.unilab.gmp.utility.APICalls;
import com.unilab.gmp.utility.DialogUtils;
import com.unilab.gmp.utility.Glovar;
import com.unilab.gmp.utility.SharedPreferenceManager;
import com.unilab.gmp.utility.Variable;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import androidx.annotation.RequiresApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PostAsync extends AsyncTask<String, String, String> implements Callback<ResultUser> {
    String email, password, action;
    public ProgressDialog dialog;
    DialogUtils dUtils;
    Context context;
    SharedPreferenceManager sharedPref;
    Dialog dialogLoginError;
    Dialog dialogPostError;
    NextSelectedAuditReportFragment nextSelectedAuditReportFragment;
    NextSelectedTemplateFragment nextSelectedTemplateFragment;
    TemplateFragment templateFragment;
    AuditReportFragment auditReportFragment;
    boolean loginError = false;


    public PostAsync(Context context, ProgressDialog dialog, String email, String password,
                     String action, NextSelectedAuditReportFragment nextSelectedAuditReportFragment,
                     NextSelectedTemplateFragment nextSelectedTemplateFragment) {
        this.context = context;
        this.nextSelectedTemplateFragment = nextSelectedTemplateFragment;
        this.nextSelectedAuditReportFragment = nextSelectedAuditReportFragment;
        this.email = email.toLowerCase(Locale.US);
        this.password = password;
        this.dialog = dialog;
        this.action = action;
        templateFragment = new TemplateFragment();
        auditReportFragment = new AuditReportFragment();

        dUtils = new DialogUtils(context);
        sharedPref = new SharedPreferenceManager(context);
        Log.e("TAG", "CLICK!!! 2 ");
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (this.action.equals(Glovar.LOGIN)) {
            dialog = new ProgressDialog(context);
            dialog.setMessage("Validating user");
            dialog.setCancelable(false);
            dialog.show();
        } else {
            dialog = new ProgressDialog(context);
            dialog.setMessage("Loading");
            dialog.setCancelable(false);
            dialog.show();
        }
    }

    @Override
    protected String doInBackground(String... data) {
        // Create a new HttpClient and Post Header
        HttpClient httpclient = new DefaultHttpClient();
        InputStream inputStream = null;
        HttpPost httppost = new HttpPost("http://sams.webqa.unilab.com.ph/api"); //new api link applied
        httppost.setHeader("Content-type", "application/x-www-form-urlencoded");
        String s = "";
        try {
            // add data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(
                    1);
            nameValuePairs.add(new BasicNameValuePair("token",
                    "35ced0a2f0ad35bdc9ae075ee213ea4b8e6c2839"));
            nameValuePairs.add(new BasicNameValuePair("cmdEvent",
                    "authenticate"));
            nameValuePairs.add(new BasicNameValuePair("email",
                    email));
            nameValuePairs.add(new BasicNameValuePair("password",
                    password));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            // execute http post
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();

            inputStream = entity.getContent();
            // json is UTF-8 by default
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    inputStream, "UTF-8"), 8);
            StringBuilder sb = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            s = sb.toString();
        } catch (ClientProtocolException e) {
            Log.e("HI", "CLICK!!! ClientProtocolException");
        } catch (IOException e) {
            Log.e("HI", "CLICK!!! IOException");
            loginError = true;
        } catch (Exception e) {
            Log.e("HI", "doInBackground: " + e.toString());
        }

        return s;
    }

    @Override
    protected void onPostExecute(String result) {
        // TODO Auto-generated method stub
        super.onPostExecute(result);

        if (loginError)
            dialogLoginError("Please check your internet connection and try again.");
        try {
            JSONObject obj = new JSONObject(result);
            Log.e("TAG", "RESULT LOG IN : " + obj.getString("status") + " Result : " + result);
            String message = obj.getString("message");
            if (obj.getString("status").equals("success")) {
                Log.e("TAG", "CLICK!!! success" + email + " PASSWORD : " + password);
                email = obj.getJSONArray("data").getJSONObject(0).getString("email");
                sharedPref.saveData("EMAIL", email);
                sharedPref.saveData("PASSWORD", password);
                Log.e("UserEmail", " EMAIL : " + email + " PASSWORD : " + sharedPref.getStringData("PASSWORD"));

                if (this.action.equals(Glovar.LOGIN)) {
                    Variable.showDialog = true;
                    new APICalls(context, "Loading...", false, null, "login").execute();
                } else if (this.action.equals(Glovar.POST_AUDIT)) {
                    dialog.dismiss();
                    nextSelectedAuditReportFragment.postData();
                } else if (this.action.equals(Glovar.POST_TEMPLATE)) {
                    dialog.dismiss();
                    nextSelectedTemplateFragment.postData();
                }
            } else {
                if (this.action.equals(Glovar.LOGIN)) {
                    Log.i("ERROR", "invalid email");
                    dialogLoginError(message);
                    dialog.dismiss();
                } else {
                    //invalid credentials please re-login to continue
                    dialogPostError("Invalid credentials please re-login to continue.");
                    dialog.dismiss();
                }
                Log.e("TAG", "CLICK!!! fail");
            }
        } catch (JSONException e) {

            dialog.dismiss();

            if (!dialogLoginError.equals(null)) {
                if (dialogLoginError.isShowing()) {
                    dialogLoginError.dismiss();
                }
            }

            dialogLoginError = new Dialog(context);
            dialogLoginError.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            dialogLoginError.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialogLoginError.setCancelable(false);
            dialogLoginError.setContentView(R.layout.dialog_error_login);
            dialogLoginError.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

            Button ok = dialogLoginError.findViewById(R.id.btn_ok);
            TextView msg = dialogLoginError.findViewById(R.id.tv_message);

            msg.setText("Server is down. Please contact your administrator.");

            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogLoginError.dismiss();
                }
            });

            dialogLoginError.show();

        } catch (Exception e) {
            Log.e("HI", "onPostExecute: " + e.toString());
            if (this.action.equals(Glovar.LOGIN)) {
                dialog.dismiss();
            }
        }

    }

    @Override
    public void onResponse(Call<ResultUser> call, Response<ResultUser> response) {
        try {
            List<ModelUser> result = response.body().getResult();
            Log.e("TAG_EMAIL", "RESULT : " + result.toString());
            if (result != null) {
                Log.e("TAG_EMAIL_2", "RESULT : " + result.toString());
                // Save to database the retrieved user here.
                ModelUser mUser = new ModelUser();
                mUser.setEmp_id(result.get(0).getEmp_id());
                mUser.setEmail(email);
                mUser.setPassword(password);

                sharedPref.saveData("EMP_ID", String.valueOf(mUser.getEmp_id()));
                context.startActivity(new Intent(context, HomeActivity.class));
            } else {
                dialog.dismiss();
            }
        } catch (Exception e) {
            Log.i("      ", "invalid email 2");
            dialogLoginError("Internet connection cannot access the web service, please connect to other network.");
        }


    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onFailure(Call<ResultUser> call, Throwable throwable) {
        Log.e("HI", "ERROR : " + throwable.toString());
        Log.e("DEBUG", "ERROR : " + throwable.toString());
        Log.e("TAGRename", "CLICK!!!" + throwable.toString());
        Log.e("TAGRename", "CLICK!!!" + throwable);
        Log.i("ERROR", "invalid email 3");
        Activity activity = dialog.getOwnerActivity();
        if (activity != null && !activity.isFinishing() && dialog != null) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }

    }

    public void dialogLoginError(String mess) {
        dialogLoginError = new Dialog(context);
        dialogLoginError.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialogLoginError.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogLoginError.setCancelable(false);
        dialogLoginError.setContentView(R.layout.dialog_error_login);
        dialogLoginError.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        Button ok = dialogLoginError.findViewById(R.id.btn_ok);
        TextView msg = dialogLoginError.findViewById(R.id.tv_message);

        if (mess.equals("Invalid Email")) {
            mess = "Invalid email address. Please make sure that your email address is correct.";
        }

        msg.setText(mess);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogLoginError.dismiss();
            }
        });

        dialogLoginError.show();
    }

    public void dialogPostError(String mess) {
        dialogPostError = new Dialog(context);
        dialogPostError.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialogPostError.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogPostError.setCancelable(false);
        dialogPostError.setContentView(R.layout.dialog_error_login);
        dialogPostError.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        Button ok = dialogPostError.findViewById(R.id.btn_ok);
        TextView msg = dialogPostError.findViewById(R.id.tv_message);

        msg.setText(mess);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);
                ((Activity) context).finish();
                dialogPostError.dismiss();
            }
        });


        dialogPostError.show();
    }
}