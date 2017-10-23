package com.unilab.gmp.adapter.templates;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.unilab.gmp.R;
import com.unilab.gmp.model.ModelDateOfAudit;
import com.unilab.gmp.utility.DateTimeUtils;
import com.unilab.gmp.utility.StartDatePickerButton;

import java.util.Calendar;
import java.util.List;

/**
 * Created by c_rcmiguel on 9/29/2017.
 */

public class DateOfAuditAdapter extends BaseAdapter {
    Context context;
    List<ModelDateOfAudit> datesOfAudit;
    LayoutInflater inflater;

    Calendar dateSelected = Calendar.getInstance();

    public DateOfAuditAdapter(Context context, List<ModelDateOfAudit> datesOfAudit) {
        this.context = context;
        this.datesOfAudit = datesOfAudit;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return datesOfAudit.size();
    }

    @Override
    public Object getItem(int i) {
        return datesOfAudit.get(i).getDateOfAudit();
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public void save(String report_id) {
        ModelDateOfAudit.deleteAll(ModelDateOfAudit.class,"reportid = ?", report_id);
        for (ModelDateOfAudit t : datesOfAudit) {
            t.setReport_id(report_id);
            t.save();
        }
    }

    public class Widgets
    {
        Button btn_date_of_audit;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        final Widgets widgets = new Widgets();
        View rowView;
        rowView = inflater.inflate(R.layout.custom_listview_template_audit_date, null);
        widgets.btn_date_of_audit = (Button) rowView.findViewById(R.id.btn_template_audit_date);

        if(!datesOfAudit.get(position).getDateOfAudit().isEmpty()){
            widgets.btn_date_of_audit.setText(DateTimeUtils.parseDateMonthToWord(datesOfAudit.get(position).getDateOfAudit()));
        }

        widgets.btn_date_of_audit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartDatePickerButton datePicker = new StartDatePickerButton(widgets.btn_date_of_audit,datesOfAudit,position);
                datePicker.show(((AppCompatActivity) context).getSupportFragmentManager(), "datePicker");
            }
        });

        return rowView;
    }


}
