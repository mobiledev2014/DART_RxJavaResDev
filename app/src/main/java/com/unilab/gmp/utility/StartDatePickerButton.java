package com.unilab.gmp.utility;

/**
 * Created by c_rcmiguel on 3/1/2017.
 */

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.widget.Button;
import android.widget.DatePicker;

import com.unilab.gmp.model.ModelDateOfAudit;

import java.util.Calendar;
import java.util.List;

public class StartDatePickerButton extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    public String date = "";
    Calendar c = Calendar.getInstance();
    int startYear = c.get(Calendar.YEAR);
    int startMonth = c.get(Calendar.MONTH);
    int startDay = c.get(Calendar.DAY_OF_MONTH);
    Button button;
    List<ModelDateOfAudit> datesOfAudit;
    int position;

    public StartDatePickerButton(Button button, List<ModelDateOfAudit> datesOfAudit, int position) {
        this.datesOfAudit = datesOfAudit;
        this.button = button;
        this.position = position;
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
        date = year + "-" + (month + 1) + "-" + day;
        datesOfAudit.get(position).setDateOfAudit(date);

        Log.i("DATE OF AUDIT : ", date);

        button.setText(DateTimeUtils.parseDateMonthToWord(date));

        //updateStartDateDisplay();
    }
}
