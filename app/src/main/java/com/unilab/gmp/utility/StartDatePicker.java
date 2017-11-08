package com.unilab.gmp.utility;

/**
 * Created by c_rcmiguel on 3/1/2017.
 */

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

public class StartDatePicker extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    Calendar c = Calendar.getInstance();
    int startYear = c.get(Calendar.YEAR);
    int startMonth = c.get(Calendar.MONTH);
    int startDay = c.get(Calendar.DAY_OF_MONTH);
    public String date= "";
    EditText editText;

    public StartDatePicker(EditText editText) {

        this.editText = editText;
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
        date = year + "-" + (month+1) + "-" + day;
        editText.setText(DateTimeUtils.parseDateMonthToWord(date));

        editText.setError(null);
//        editText.setText(DateTimeUtils.DateTimeStamp());
//        try {
//            editText.setText(DateTimeUtils.parseDateMonthToWord(date));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
        //updateStartDateDisplay();
    }
}
