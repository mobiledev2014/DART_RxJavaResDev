package com.unilab.gmp.adapter.templates;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;

import com.unilab.gmp.R;
import com.unilab.gmp.model.ModelDateOfAudit;
import com.unilab.gmp.utility.DateTimeUtils;

import java.util.Calendar;
import java.util.List;

/**
 * Created by c_rcmiguel on 9/29/2017.
 */

public class AdapterInspectionDate extends BaseAdapter {
    Context context;
    List<ModelDateOfAudit> datesOfAudit;
    LayoutInflater inflater;

    Calendar dateSelected = Calendar.getInstance();

    public AdapterInspectionDate(Context context, List<ModelDateOfAudit> datesOfAudit) {
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
        EditText etInspectionDate;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        final Widgets widgets = new Widgets();
        View rowView;
        rowView = inflater.inflate(R.layout.custom_listview_template_inspection_date, null);
        widgets.etInspectionDate = (EditText) rowView.findViewById(R.id.et_template_next_company_background_inspection_date);

        if(!datesOfAudit.get(position).getDateOfAudit().isEmpty()){
            widgets.etInspectionDate.setText(DateTimeUtils.parseDateMonthToWord(datesOfAudit.get(position).getDateOfAudit()));
        }

        return rowView;
    }


}
