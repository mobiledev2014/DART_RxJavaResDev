package com.unilab.gmp.retrofit.Async;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.unilab.gmp.R;
import com.unilab.gmp.activity.HomeActivity;
import com.unilab.gmp.activity.MainActivity;
import com.unilab.gmp.fragment.AuditReportFragment;
import com.unilab.gmp.fragment.NextSelectedAuditReportFragment;
import com.unilab.gmp.fragment.NextSelectedTemplateFragment;
import com.unilab.gmp.fragment.TemplateFragment;
import com.unilab.gmp.model.ModelUser;
import com.unilab.gmp.retrofit.ApiInterface;
import com.unilab.gmp.retrofit.user.ResultUser;
import com.unilab.gmp.utility.APICalls;
import com.unilab.gmp.utility.DialogUtils;
import com.unilab.gmp.utility.Glovar;
import com.unilab.gmp.utility.SharedPreferenceManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class PostAsync extends AsyncTask<String, String, String> implements Callback<ResultUser> {
    String email, password, action;
    ProgressDialog dialog;
    DialogUtils dUtils;
    Context context;
    ApiInterface apiInterface;
    SharedPreferenceManager sharedPref;
    Dialog dialogLoginError;
    Dialog dialogPostError;
    Dialog dialogSuccess;
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
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://mrdgnsndp.hol.es/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        apiInterface = retrofit.create(ApiInterface.class);
        sharedPref = new SharedPreferenceManager(context);
        Log.e("TAG", "CLICK!!! 2 ");
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = new ProgressDialog(context);
        dialog.setMessage("Validating user");
        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    protected String doInBackground(String... data) {
        // Create a new HttpClient and Post Header
        dialog.setProgress(1);
        HttpClient httpclient = new DefaultHttpClient();
        InputStream inputStream = null;
        HttpPost httppost = new HttpPost("http://ojl.ecomqa.com/azure/pwgrant.php");
        httppost.setHeader("Content-type", "application/x-www-form-urlencoded");
        String s = "";
        try {
            // add data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(
                    1);
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
            Log.e("TAG", "CLICK!!! ClientProtocolException");
        } catch (IOException e) {
            Log.e("TAG", "CLICK!!! IOException");
            loginError = true;
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
            if (obj.getString("status").equals("success")) {
                Call<ResultUser> call = apiInterface.getUser(email);
                call.enqueue(this);
                dialog.setProgress(2);
                Log.e("TAG", "CLICK!!! success" + email + " PASSWORD : " + password);

//                sharedPref.saveData("ID", email);
                sharedPref.saveData("EMAIL", email);
                sharedPref.saveData("PASSWORD", password);
                Log.e("UserEmail", " EMAIL : " + email + " PASSWORD : " + sharedPref.getStringData("PASSWORD"));

                if (this.action.equals(Glovar.LOGIN)) {
                    new APICalls(context, "Loading...", false, null).execute();

                } else if (this.action.equals(Glovar.POST_AUDIT)) {
                    nextSelectedAuditReportFragment.postData();
                    //dialogSuccess("Successfully submitted.", false);
                } else if (this.action.equals(Glovar.POST_TEMPLATE)) {
                    nextSelectedTemplateFragment.postData();
                    //dialogSuccess("Successfully submitted.", true);
                }
            } else {
                dialog.dismiss();
                //dUtils.DialogWarning("Login error", "The username/password is invalid.", MainActivity.orientation);

                if (this.action.equals(Glovar.LOGIN)) {
                    Log.i("ERROR", "invalid email");
                    //dialogLoginError("Invalid email address. Please make sure that your email address is correct.");
                    dialogLoginError("Email and password do not match.");
                } else {
                    //invalid credentials please re-login to continue
                    dialogPostError("Invalid credentials please re-login to continue.");
                }
                Log.e("TAG", "CLICK!!! fail");
            }
        } catch (JSONException e) {
            dialog.dismiss();
        }
    }

    @Override
    public void onResponse(Call<ResultUser> call, Response<ResultUser> response) {
        try {
            List<ModelUser> result = response.body().getResult();
            Log.e("TAG", "RESULT : " + result.toString());
            if (result != null) {
                dialog.dismiss();
                if (result.size() > 0) {
                    // Save to database the retrieved user here.
                    ModelUser mUser = new ModelUser();
                    mUser.setEmp_id(result.get(0).getEmp_id());
                    mUser.setEmail(email);
                    mUser.setPassword(password);

                    sharedPref.saveData("EMP_ID", String.valueOf(mUser.getEmp_id()));

                    context.startActivity(new Intent(context, HomeActivity.class));
                }
            } else {
                dialog.dismiss();
                /*dUtils.DialogWarning("Login error",
                        "There is no internet connection detected. Please check your connection and try again.",
                        MainActivity.orientation);*/
                Log.i("ERROR", "no internet 1");
                dialogLoginError("There is no internet connection detected. Please check your connection and try again.");
            }
        } catch (Exception e) {
            dialog.dismiss();
            /*dUtils.DialogWarning("Network error",
                    "Internet connection cannot access the web service, please connect to other network.",
                    MainActivity.orientation);*/
            Log.i("ERROR", "invalid email 2");
            //dialogLoginError("Internet connection cannot access the web service, please connect to other network.");
        }

    }

    @Override
    public void onFailure(Call<ResultUser> call, Throwable throwable) {
        Log.e("DEBUG", "ERROR : " + throwable.toString());
        Log.e("TAGRename", "CLICK!!!" + throwable.toString());
        /*dUtils.DialogWarning("Login Error",
                "There is no internet connection detected. Please check your connection and try again.",
                MainActivity.orientation);*/
        Log.i("ERROR", "invalid email 3");
        dialogLoginError("There is no internet connection detected. Please check your connection and try again.");
        dialog.dismiss();
    }

    public void dialogLoginError(String mess) {
        dialogLoginError = new Dialog(context);
        dialogLoginError.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialogLoginError.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogLoginError.setCancelable(false);
        dialogLoginError.setContentView(R.layout.dialog_error_login);
        dialogLoginError.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        Button ok = (Button) dialogLoginError.findViewById(R.id.btn_ok);
        TextView msg = (TextView) dialogLoginError.findViewById(R.id.tv_message);

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

        Button ok = (Button) dialogPostError.findViewById(R.id.btn_ok);
        TextView msg = (TextView) dialogPostError.findViewById(R.id.tv_message);

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

    public void dialogSuccess(String mess, final boolean template) {
        dialogSuccess = new Dialog(context);
        dialogSuccess.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialogSuccess.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogSuccess.setCancelable(false);
        dialogSuccess.setContentView(R.layout.dialog_error_login);
        dialogSuccess.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        TextView msg = (TextView) dialogSuccess.findViewById(R.id.tv_message);
        Button ok = (Button) dialogSuccess.findViewById(R.id.btn_ok);

        msg.setText(mess);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
                if (template) {
                    fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    fragmentManager.beginTransaction()
                            .replace(R.id.fl_content, templateFragment).addToBackStack(null).commit();
                } else {
                    fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    fragmentManager.beginTransaction()
                            .replace(R.id.fl_content, auditReportFragment).addToBackStack(null).commit();
                }
                dialogSuccess.dismiss();
            }
        });

        dialogSuccess.show();
    }
}