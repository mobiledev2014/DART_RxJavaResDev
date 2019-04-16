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
import com.unilab.gmp.utility.Variable;

import java.util.List;

/**
 * Created by c_jhcanuto on 7/31/2017.
 */

public class SubActivityAdapter extends RecyclerView.Adapter<SubActivityAdapter.Widgets> {
    LayoutInflater inflater;
    Context context;
    List<ModelTemplateSubActivities> modelTemplateActivities;
    String activity_id;

    public SubActivityAdapter(Context context, List<ModelTemplateSubActivities> modelTemplateActivities,
                              String report_id, String activity_id) {
        this.context = context;
        this.activity_id = activity_id;
        this.modelTemplateActivities = modelTemplateActivities;
        for (ModelTemplateSubActivities modelTemplateSubActivity : modelTemplateActivities) {

            List<ModelReportSubActivities> modelReportSubActivities = ModelReportSubActivities.find(ModelReportSubActivities.class, "reportid = ? AND subitemid = ?", report_id, modelTemplateSubActivity.getSubItemID());
            if (modelReportSubActivities.size() > 0) {
                modelTemplateSubActivity.setCheck(modelReportSubActivities.get(0).isCheck());
                Log.e("SubActivityAdapter", "ifSize:" + modelTemplateActivities.size());
            } else {
                Log.e("SubActivityAdapter", "elseSize:" + modelTemplateActivities.size());
            }
        }
        Log.e("SubActivityAdapter", "size:" + modelTemplateActivities.size());
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getItemCount() {
        return modelTemplateActivities.size();
    }

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

        widgets.cbSubactivity.setEnabled(Variable.isAuthorized);

        if (modelTemplateActivities.get(z).isCheck()) {
            widgets.cbSubactivity.setChecked(true);
        }

    }

    public void save(String report_id) {
        for (ModelTemplateSubActivities modelTemplateSubActivity : modelTemplateActivities) {
            ModelReportSubActivities modelReportSubActivities = new ModelReportSubActivities();
            modelReportSubActivities.setReport_id(report_id);
            modelReportSubActivities.setSub_item_id(modelTemplateSubActivity.getSubItemID());
            modelReportSubActivities.setCheck(modelTemplateSubActivity.isCheck());
            modelReportSubActivities.setActivity_id(activity_id);
            modelReportSubActivities.save();
            Log.e("sub activity saving", modelTemplateSubActivity.getSubItemID() + " --- " + modelTemplateSubActivity.isCheck());
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
