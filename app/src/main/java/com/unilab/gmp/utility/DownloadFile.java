package com.unilab.gmp.utility;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadFile extends AsyncTask<String,Void,Void>{
    private Context context;
    public void setContext(Context contextf){
        context = contextf;
    }

    @Override
    protected Void doInBackground(String... arg0) {
        try {
        	Log.e("DownloadFile", "DownloadFile! " + arg0[0]);
//        	File c = new File(arg0[0]);

          URL url = new URL(arg0[0]);
            HttpURLConnection c = (HttpURLConnection) url.openConnection();
//            c.setRequestMethod("GET");
//            c.setDoOutput(true);
            c.connect();
            
//            Log.e("DownloadFile", "DownloadFile! " + c.getResponseMessage());
//            Log.e("DownloadFile", "DownloadFile! " + c.getResponseCode());
        	
        	Log.e("DownloadFile", "DownloadFile! " + c.toString());

            String pdfPath = System.getenv("EXTERNAL_STORAGE") + "/DART/AuditReports/";
//            String PATH = System.getenv("EXTERNAL_STORAGE")+"/apk";
            Log.e("DownloadFile", "Path: " + pdfPath);
            File file = new File(pdfPath);
            Log.e("DownloadFile", "File! " + file.toString());
            file.mkdirs();
            File outputFile = new File(pdfPath, "351.pdf");
            if(outputFile.exists()){
                outputFile.delete();
            }
            FileOutputStream fos = new FileOutputStream(outputFile);
            Log.e("DownloadFile", "pass " + c.toString());
            InputStream is = c.getInputStream();
            
//            InputStream is = new FileInputStream("file:///172.16.9.244/website/Bayanihan_Files/APK/UL_R_RX.apk");
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
        Utils.openPdf(context, "");
    }
}