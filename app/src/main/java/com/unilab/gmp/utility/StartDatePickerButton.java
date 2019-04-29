package com.unilab.gmp.utility;

/**
 * Created by c_rcmiguel on 3/1/2017.
 */

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.unilab.gmp.R;
import com.unilab.gmp.model.ModelDateOfAudit;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.fragment.app.DialogFragment;

@SuppressLint("ValidFragment")
public class StartDatePickerButton extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    public String date = "";
    Calendar c = Calendar.getInstance();
    int startYear = c.get(Calendar.YEAR);
    int startMonth = c.get(Calendar.MONTH);
    int startDay = c.get(Calendar.DAY_OF_MONTH);
    Button button;
    List<ModelDateOfAudit> datesOfAudit;
    int position;
    Dialog dialogErrorDate;
    Context cont;

    String monthStr, dayStr;

    public StartDatePickerButton(Button button, List<ModelDateOfAudit> datesOfAudit, int position, Context context) {
        this.datesOfAudit = datesOfAudit;
        this.button = button;
        this.position = position;
        this.cont = context;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        // Use the current date as the default date in the picker
//        DatePickerDialog dialog = new DatePickerDialog(getActivity(), this, startYear, startMonth, startDay);
        return new DatePickerDialog(getActivity(), this, startYear, startMonth, startDay);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        // TODO Auto-generated method stub
        // Do something with the date chosen by the user
        //month = month + 1;
        //date = year + "-" + (month + 1) + "-" + day;
        Log.i("DATE OF AUDIT : ", year + "-" + (month + 1) + "-" + day);
        if (month <= 8){
            monthStr = "0" + (month + 1);
        } else {
            monthStr = (month+1) + "";
        }

        if (day <= 9){
            dayStr = "0" + day;
        } else {
            dayStr = day + "";
        }
        date = year + "-" + monthStr + "-" + dayStr;

        datesOfAudit.get(position).setDateOfAudit(date);

        Log.i("DATE OF AUDIT : ", date);

        if (errorDate(date)) {
            button.setText(DateTimeUtils.parseDateMonthToWord(date));
        } else {
            datesOfAudit.get(position).setDateOfAudit("");
            button.setText("SELECT DATE");
            dialogErrorDate();
        }
        //updateStartDateDisplay();
    }

    public void dialogErrorDate() {
        dialogErrorDate = new Dialog(cont);
        dialogErrorDate.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialogErrorDate.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogErrorDate.setCancelable(false);
        dialogErrorDate.setContentView(R.layout.dialog_error_login);
        dialogErrorDate.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        String message = "Invalid date.";

        TextView tv_message = (TextView) dialogErrorDate.findViewById(R.id.tv_message);
        Button ok = (Button) dialogErrorDate.findViewById(R.id.btn_ok);

        tv_message.setText(message);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogErrorDate.dismiss();
            }
        });


        dialogErrorDate.show();
    }

    public boolean errorDate(String date) {
        boolean result = false;
        Log.i("CurrentDate : ", getDate() + ", pickDate : " + date);
        date = date.replace("-", "");
        int currentDate = Integer.parseInt(getDate());

        Log.i("CurrentDate : ", getDate() + ", pickDate : " + date);

        if (currentDate >= Integer.parseInt(date)) {
            result = true;
        } else {
            result = false;
        }

        return result;
    }

    public String getDate() {
        String dateStr = "";

        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        dateStr = dateFormat.format(date);

        return dateStr;
    }
}
