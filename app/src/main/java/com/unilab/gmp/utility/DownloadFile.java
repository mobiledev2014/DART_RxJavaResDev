package com.unilab.gmp.utility;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.unilab.gmp.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadFile extends AsyncTask<String, Void, Void> {
    String id;
    String pdfType;
    Dialog dialogSelectPdf;
    private Context context;

    public DownloadFile(Context context, String id, String pdfType) {
        this.context = context;
        this.id = id;
        this.pdfType = pdfType;
    }

    public void setContext(Context contextf) {
        context = contextf;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        ProgressDialogUtils.showSimpleProgressDialog(context, 50, "Downloading. . .", false);
    }

    @Override
    protected Void doInBackground(String... arg0) {
        try {
            Log.e("DownloadFile", "DownloadFile! " + arg0[0]);
            URL url = new URL(arg0[0]);
            HttpURLConnection c = (HttpURLConnection) url.openConnection();
            c.connect();
            Log.e("DownloadFile", "DownloadFile! " + c.toString());

            String pdfPath = System.getenv("EXTERNAL_STORAGE") + "/DART/AuditReports/";
            Log.e("DownloadFile", "Path: " + pdfPath);
            File file = new File(pdfPath);
            Log.e("DownloadFile", "File! " + file.toString());
            file.mkdirs();
            File outputFile = new File(pdfPath, id + "_" + pdfType + ".pdf");
            if (outputFile.exists()) {
                outputFile.delete();
            }
            FileOutputStream fos = new FileOutputStream(outputFile);
            Log.e("DownloadFile", "pass " + c.toString());
            InputStream is = c.getInputStream();

            Log.e("DownloadFile", "passed " + c.toString());
            byte[] buffer = new byte[1024];
            int len1 = 0;
            while ((len1 = is.read(buffer)) != -1) {
                fos.write(buffer, 0, len1);
            }
            fos.close();
            is.close();

        } catch (Exception e) {
            Log.e("UpdateAPP", "download error! " + e.getMessage());
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if (pdfType.equals("Executive_Report")) {
            ProgressDialogUtils.removeSimpleProgressDialog();
            dialogSelectPdf();
        }
    }

    public void dialogSelectPdf() {
        dialogSelectPdf = new Dialog(context);
        dialogSelectPdf.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialogSelectPdf.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogSelectPdf.setCancelable(false);
        dialogSelectPdf.setContentView(R.layout.dialog_pdf_picker);
        dialogSelectPdf.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        Button audit = dialogSelectPdf.findViewById(R.id.btn_audit_report);
        Button executive = dialogSelectPdf.findViewById(R.id.btn_executive_summary);
        Button annexure = dialogSelectPdf.findViewById(R.id.btn_annexure);
        Button cancel = dialogSelectPdf.findViewById(R.id.btn_cancel);

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