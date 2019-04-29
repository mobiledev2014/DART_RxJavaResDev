package com.unilab.gmp.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.unilab.gmp.R;
import com.unilab.gmp.model.ModelReportActivities;
import com.unilab.gmp.model.ModelReportSubActivities;
import com.unilab.gmp.model.ModelTemplateActivities;
import com.unilab.gmp.model.ModelTemplateSubActivities;
import com.unilab.gmp.utility.Variable;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by c_jhcanuto on 7/31/2017.
 */

public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.Widgets> {
    LayoutInflater inflater;
    Context context;

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

    @Override
    public long getItemId(int id) {
        return id;
    }

    @Override
    public void onBindViewHolder(Widgets widgets, final int position) {
        widgets.name.setText(modelTemplateActivities.get(position).getActivityName());
        widgets.cbActivity.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                modelTemplateActivities.get(position).setCheck(b);
            }
        });

        widgets.cbActivity.setEnabled(Variable.isAuthorized);

        subActivityAdapters.add(new SubActivityAdapter(context, ModelTemplateSubActivities.
                find(ModelTemplateSubActivities.class, "templateid = ? AND activityid = ?",
                        modelTemplateActivities.get(position).getTemplate_id(), modelTemplateActivities.get(position).getActivityID()), report_id, modelTemplateActivities.get(position).getActivityID()));

        List<ModelReportActivities> mra = ModelReportActivities.find(ModelReportActivities.class,
                "reportid = ? AND activityid = ?", report_id, modelTemplateActivities.get(position).getActivityID());

        if (mra.size() > 0) {
            modelTemplateActivities.get(position).setCheck(mra.get(0).isCheck());
        }

        if (modelTemplateActivities.get(position).isCheck()) {
            widgets.cbActivity.setChecked(true);
        }

        widgets.recyclerView.setAdapter(subActivityAdapters.get(position));

        if (subActivityAdapters.get(position).getItemCount() > 0) {
            widgets.cbActivity.setVisibility(View.GONE);
            widgets.cbActivity.setChecked(true);
        } else {
            widgets.cbActivity.setVisibility(View.VISIBLE);
        }
    }

    public void save(String report_id) {
        ModelReportActivities.deleteAll(ModelReportActivities.class, "reportid = ?", report_id);
        for (ModelTemplateActivities modelTemplateActivity : modelTemplateActivities) {
            ModelReportActivities modelReportActivity = new ModelReportActivities();
            modelReportActivity.setReport_id(report_id);
            modelReportActivity.setActivity_id(modelTemplateActivity.getActivityID());
            modelReportActivity.setCheck(modelTemplateActivity.isCheck());
            modelReportActivity.save();
            Log.e("activity saving", modelTemplateActivity.getActivityID() + " --- " + modelTemplateActivity.isCheck());
        }
        ModelReportSubActivities.deleteAll(ModelReportSubActivities.class, "reportid = ?", report_id);
        for (SubActivityAdapter saa : subActivityAdapters) {
            saa.save(report_id);
        }
    }

    @Override
    public Widgets onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_listview_activities, parent, false);
        return new Widgets(view);
    }

    public class Widgets extends RecyclerView.ViewHolder {
        TextView name;
        RecyclerView recyclerView;
        CheckBox cbActivity;

        public Widgets(View rowView) {
            super(rowView);
            this.name = (TextView) rowView.findViewById(R.id.tv_activity_name);
            this.recyclerView = (RecyclerView) rowView.findViewById(R.id.lv_sub_activity);
            this.cbActivity = (CheckBox) rowView.findViewById(R.id.cb_activity);
            this.recyclerView.setLayoutManager(new LinearLayoutManager(context));
            this.recyclerView.setItemAnimator(new DefaultItemAnimator());
        }

    }

}
