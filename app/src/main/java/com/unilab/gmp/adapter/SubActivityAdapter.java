package com.unilab.gmp.adapter;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.unilab.gmp.R;
import com.unilab.gmp.model.ModelReportSubActivities;
import com.unilab.gmp.model.ModelTemplateSubActivities;

import java.util.List;

/**
 * Created by c_jhcanuto on 7/31/2017.
 */

public class SubActivityAdapter extends RecyclerView.Adapter<SubActivityAdapter.Widgets> {
    LayoutInflater inflater;
    Context context;

    Dialog dialogViewApprover;
    List<ModelTemplateSubActivities> modelTemplateActivities;
    String activity_id;

    public SubActivityAdapter(Context context, List<ModelTemplateSubActivities> modelTemplateActivities,
                              String report_id, String activity_id) {
        this.context = context;
        this.activity_id = activity_id;
        this.modelTemplateActivities = modelTemplateActivities;
        for (ModelTemplateSubActivities mtsa : modelTemplateActivities) {

            List<ModelReportSubActivities> mra = ModelReportSubActivities.find(ModelReportSubActivities.class, "reportid = ? AND subitemid = ?", report_id, mtsa.getSubItemID());
            if (mra.size() > 0) {
                mtsa.setCheck(mra.get(0).isCheck());
                Log.e("SubActivityAdapter", "ifSize:" + modelTemplateActivities.size());
            } else {
                Log.e("SubActivityAdapter", "elseSize:" + modelTemplateActivities.size());
            }
        }
        Log.e("SubActivityAdapter", "size:" + modelTemplateActivities.size());
//        if (modelTemplateActivities.size()<1)
//            chBox.setVisibility(View.VISIBLE);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getItemCount() {
        return modelTemplateActivities.size();
    }

//    @Override
//    public Object getItem(int i) {
//        return i;
//    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public Widgets onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_listview_subactivities, parent, false);
        return new Widgets(v);
    }

    @Override
    public void onBindViewHolder(Widgets widgets, final int z) {
        widgets.name.setText(modelTemplateActivities.get(z).getSubItemName());
        widgets.cbSubactivity.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                modelTemplateActivities.get(z).setCheck(b);
            }
        });

        if (modelTemplateActivities.get(z).isCheck()) {
            widgets.cbSubactivity.setChecked(true);
        }

    }

    public void save(String report_id) {
        for (ModelTemplateSubActivities mta : modelTemplateActivities) {
            ModelReportSubActivities mra = new ModelReportSubActivities();
            mra.setReport_id(report_id);
            mra.setSub_item_id(mta.getSubItemID());
            mra.setCheck(mta.isCheck());
            mra.setActivity_id(activity_id);
            mra.save();
        }
    }

    public class Widgets extends RecyclerView.ViewHolder {
        TextView name;
        CheckBox cbSubactivity;

        public Widgets(View rowView) {
            super(rowView);
            this.name = (TextView) rowView.findViewById(R.id.tv_subactivity_name);
            this.cbSubactivity = (CheckBox) rowView.findViewById(R.id.cb_subactivity);
        }
    }

}
