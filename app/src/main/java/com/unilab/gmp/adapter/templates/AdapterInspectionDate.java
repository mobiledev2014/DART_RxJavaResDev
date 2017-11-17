package com.unilab.gmp.adapter.templates;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
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

public class AdapterInspectionDate extends RecyclerView.Adapter<AdapterInspectionDate.Widgets> {
    Context context;
    List<ModelSiteDate> siteDates;
    LayoutInflater inflater;

    Calendar dateSelected = Calendar.getInstance();

    public AdapterInspectionDate(Context context, List<ModelSiteDate> siteDates) {
        this.context = context;
        this.siteDates = siteDates;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getItemCount() {
        return siteDates.size();
    }

//    @Override
//    public Object getItem(int i) {
//        return siteDates.get(i).getInspection_date();
//    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    public class Widgets extends RecyclerView.ViewHolder{
        EditText etInspectionDate;

        Widgets(View rowView) {
            super(rowView);
            this.etInspectionDate = (EditText) rowView.findViewById(R.id.et_template_next_company_background_inspection_date);
        }
    }
    @Override
    public Widgets onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_listview_template_inspection_date,parent, false);
        return new Widgets(v);
    }
    @Override
    public void onBindViewHolder(Widgets widgets, int position) {
        if (!siteDates.get(position).getInspection_date().isEmpty()) {
            widgets.etInspectionDate.setText(DateTimeUtils.parseDateMonthToWord(siteDates.get(position).getInspection_date()));
        }

    }


}
