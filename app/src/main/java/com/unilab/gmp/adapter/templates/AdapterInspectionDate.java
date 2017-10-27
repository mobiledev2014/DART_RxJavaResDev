package com.unilab.gmp.adapter.templates;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;

import com.unilab.gmp.R;
import com.unilab.gmp.model.ModelSiteDate;
import com.unilab.gmp.utility.DateTimeUtils;

import java.util.Calendar;
import java.util.List;

/**
 * Created by c_rcmiguel on 9/29/2017.
 */

public class AdapterInspectionDate extends BaseAdapter {
    Context context;
    List<ModelSiteDate> siteDates;
    LayoutInflater inflater;

    Calendar dateSelected = Calendar.getInstance();

    public AdapterInspectionDate(Context context, List<ModelSiteDate> siteDates) {
        this.context = context;
        this.siteDates = siteDates;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return siteDates.size();
    }

    @Override
    public Object getItem(int i) {
        return siteDates.get(i).getInspection_date();
    }

    @Override
    public long getItemId(int i) {
        return i;
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

        if(!siteDates.get(position).getInspection_date().isEmpty()){
            widgets.etInspectionDate.setText(DateTimeUtils.parseDateMonthToWord(siteDates.get(position).getInspection_date()));
        }

        return rowView;
    }


}
