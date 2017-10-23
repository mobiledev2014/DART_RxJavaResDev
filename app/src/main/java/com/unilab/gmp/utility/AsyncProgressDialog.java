package com.unilab.gmp.utility;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

/**
 * Created by c_rcmiguel on 10/2/2017.
 */

public class AsyncProgressDialog extends AsyncTask {

    Context context;

    public AsyncProgressDialog(Context context) {
        this.context = context;
    }

    ProgressDialog progressDialog;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading. . .");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        return null;
    }
}
