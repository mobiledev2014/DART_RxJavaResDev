package com.unilab.gmp.adapter;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.unilab.gmp.R;
import com.unilab.gmp.model.ModelReportActivities;
import com.unilab.gmp.model.ModelReportSubActivities;
import com.unilab.gmp.model.ModelTemplateActivities;
import com.unilab.gmp.model.ModelTemplateSubActivities;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by c_jhcanuto on 7/31/2017.
 */

public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.Widgets> {
    LayoutInflater inflater;
    Context context;

    Dialog dialogViewApprover;
    List<ModelTemplateActivities> modelTemplateActivities;
    List<SubActivityAdapter> subActivityAdapters;
    String report_id;

    public ActivityAdapter(Context context, List<ModelTemplateActivities> modelTemplateActivities, String report_id) {
        this.report_id = report_id;
        this.context = context;
        this.modelTemplateActivities = modelTemplateActivities;
        subActivityAdapters = new ArrayList<>();

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
    public void onBindViewHolder(Widgets widgets, final int position) {


        widgets.name.setText(modelTemplateActivities.get(position).getActivityName());
        widgets.cbActivity.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                //Toast.makeText(context, "" + widgets.name.getText().toString(), Toast.LENGTH_SHORT).show();
                modelTemplateActivities.get(position).setCheck(b);
            }
        });

        for (ModelTemplateActivities mta : modelTemplateActivities) {

            subActivityAdapters.add(new SubActivityAdapter(context, ModelTemplateSubActivities.
                    find(ModelTemplateSubActivities.class, "templateid = ? AND activityid = ?",
                            mta.getTemplate_id(), mta.getActivityID()), report_id, mta.getActivityID()));

            List<ModelReportActivities> mra = ModelReportActivities.find(ModelReportActivities.class,
                    "reportid = ? AND activityid = ?", report_id, mta.getActivityID());

//            List<ModelTemplateSubActivities> counterList = ModelTemplateSubActivities.find
//                    (ModelTemplateSubActivities.class, "activityid = ?", mta.getActivityID());

            if (mra.size() > 0) {
                mta.setCheck(mra.get(0).isCheck());
            }
        }

        if (modelTemplateActivities.get(position).isCheck()) {
            widgets.cbActivity.setChecked(true);
        }

        widgets.lv.setAdapter(subActivityAdapters.get(position));

        if (subActivityAdapters.get(position).getItemCount() > 0) {
            widgets.cbActivity.setVisibility(View.GONE);
        } else {
            widgets.cbActivity.setVisibility(View.VISIBLE);
        }

//        } else {
//            widgets = (Widgets) rowView.getTag();
//        }
        //widgets.lv.setExpanded(true);
    }

    public void save(String report_id) {
        ModelReportActivities.deleteAll(ModelReportActivities.class, "reportid = ?", report_id);
        for (ModelTemplateActivities mta : modelTemplateActivities) {
            ModelReportActivities mra = new ModelReportActivities();
            mra.setReport_id(report_id);
            mra.setActivity_id(mta.getActivityID());
            mra.setCheck(mta.isCheck());
            mra.save();
        }
        ModelReportSubActivities.deleteAll(ModelReportSubActivities.class, "reportid = ?", report_id);
        for (SubActivityAdapter saa : subActivityAdapters) {
            saa.save(report_id);
        }
    }

    @Override
    public Widgets onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_listview_activities, parent, false);
        return new Widgets(v);
    }

    public class Widgets extends RecyclerView.ViewHolder {
        TextView name;
        LinearLayout rowBackground;
        RecyclerView lv;
        CheckBox cbActivity;

        public Widgets(View rowView) {
            super(rowView);
            this.name = (TextView) rowView.findViewById(R.id.tv_activity_name);
            this.lv = (RecyclerView) rowView.findViewById(R.id.lv_sub_activity);
            this.cbActivity = (CheckBox) rowView.findViewById(R.id.cb_activity);
            this.lv.setLayoutManager(new LinearLayoutManager(context));
            this.lv.setItemAnimator(new DefaultItemAnimator());

        }

    }

}
