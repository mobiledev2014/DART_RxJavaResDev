package com.unilab.gmp.utility;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by c_jsbustamante on 10/12/2016.
 */

public class DateTimeUtils {

    private static final String dateFormat = "yyyy-MM-dd";
    private static final String dateFormat2 = "dd-MMMM-yyyy";
    private final String TAG = DateTimeUtils.class.getSimpleName();
    private final String dateTimeFormat = "yyyy-MM-dd HH:mm:ss";

    public static String parseDateMonthToWord(String date) {

        String formattedDate = "";
        if (date.equals(""))
            return formattedDate;

        SimpleDateFormat dt = new SimpleDateFormat(dateFormat);
        Date dateToFormat = null;
        try {
            dateToFormat = dt.parse(date);
            Log.i("DATE UTILS","TRY");
        } catch (ParseException e) {
            e.printStackTrace();
            Log.i("DATE UTILS","CATCH");
        }
        SimpleDateFormat dateformatter = new SimpleDateFormat(dateFormat2);
        if (dateToFormat != null) {
            formattedDate = dateformatter.format(dateToFormat);
        }

        return formattedDate;
    }

    public static String DateTimeStamp() {
        return new SimpleDateFormat(dateFormat2).format(Calendar
                .getInstance().getTime());
    }

    public static String parseDate(Date date) {
        return new SimpleDateFormat(dateFormat).format(date);
    }


    public static String parseDateMonthToDigit(String date) {
        Log.e("DateUtil", "Month to digit : " + date);
        if (date.equals("")) {
            return "";
        }
        SimpleDateFormat dt = new SimpleDateFormat(dateFormat2);
        Date dateToFormat = null;
        try {
            if (date == null) {
                date = DateTimeStamp();
            }
            dateToFormat = dt.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat dateformatter = new SimpleDateFormat(dateFormat);

        return dateformatter.format(dateToFormat);
    }


    public static Date parseDate(String date) throws Exception {
        return new SimpleDateFormat(dateFormat).parse(date);
    }

    public static String getDate(String format, Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.US);
        return dateFormat.format(date);
    }

    public static String getDate(String format, long timestamp) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.US);
        return dateFormat.format(new Date(timestamp));
    }

    public static String getDate(String format, Date date, Locale locale) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, locale);
        return dateFormat.format(date);
    }
}
