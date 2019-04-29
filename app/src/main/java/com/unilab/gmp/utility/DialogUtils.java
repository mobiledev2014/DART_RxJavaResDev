package com.unilab.gmp.utility;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.unilab.gmp.R;
import com.unilab.gmp.retrofit.ApiInterface;
/**
 * Created by c_jsbustamante on 10/12/2016.
 */

public class DialogUtils {
    Context context;
    public Dialog dialog;

    // *********
    DateTimeUtils dtUtils;
    SharedPreferenceManager sharedPref;
    ApiInterface apiInterface;
    public static boolean modified = false;

    public DialogUtils(Context context) {
        this.context = context;
        this.dtUtils = new DateTimeUtils();
        this.sharedPref = new SharedPreferenceManager(this.context);
        this.apiInterface = Utils.initializeRetrofit();
    }

}
