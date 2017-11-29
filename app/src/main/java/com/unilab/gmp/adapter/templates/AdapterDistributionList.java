package com.unilab.gmp.adapter.templates;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Spinner;

import com.unilab.gmp.R;
import com.unilab.gmp.model.ModelDistribution;
import com.unilab.gmp.model.TemplateModelDistributionList;
import com.unilab.gmp.utility.Variable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by c_jhcanuto on 8/24/2017.
 */

public class AdapterDistributionList extends RecyclerView.Adapter<AdapterDistributionList.Widgets> {
    List<TemplateModelDistributionList> templateModelDistributionLists;
    LayoutInflater inflater;
    Context context;
    ArrayAdapter<String> adapterDistri;
    List<ModelDistribution> distributionList;
    List<String> distriIdList;

    public AdapterDistributionList(List<TemplateModelDistributionList> templateModelDistributionLists, Context context) {
        this.templateModelDistributionLists = templateModelDistributionLists;
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        distributionList = ModelDistribution.find(ModelDistribution.class, "status > 0");
        List<String> distriList = new ArrayList<>();
        distriIdList = new ArrayList<>();
        int d = distributionList.size();
        for (int count = 0; count < d; count++) {
            distriList.add(distributionList.get(count).getDistribution_name());
            distriIdList.add(distributionList.get(count).getDistribution_id());
        }

        adapterDistri = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, distriList);
    }

    @Override
    public int getItemCount() {
        return templateModelDistributionLists.size();
    }

//    @Override
//    public Object getItem(int i) {
//        return templateModelDistributionLists.get(i);
//    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public Widgets onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_listview_template_distribution_list,parent, false);
        return new Widgets(v);
    }

    @Override
    public void onBindViewHolder(final Widgets widgets, int i) {
        final int z = i;

        widgets.spnTemplateNextDistributionList.setAdapter(adapterDistri);
        widgets.spnTemplateNextDistributionList.setEnabled(Variable.isAuthorized);

        if (!templateModelDistributionLists.get(z).getDistribution_id().isEmpty()) {
            templateModelDistributionLists.get(z).setSelected(distriIdList.indexOf(
                    templateModelDistributionLists.get(z).getDistribution_id()));
        }

        widgets.spnTemplateNextDistributionList.setSelection(templateModelDistributionLists.get(i).getSelected());
        widgets.spnTemplateNextDistributionList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                templateModelDistributionLists.get(z).setDistribution(widgets.spnTemplateNextDistributionList.getSelectedItem().toString());
                templateModelDistributionLists.get(z).setSelected(i);
                templateModelDistributionLists.get(z).setDistribution_id(distributionList.get(i).getDistribution_id());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

//            rowView.setTag(widgets);
//        } else {
//            widgets = (Widgets) rowView.getTag();
//        }
    }

    public void save(String report_id) {
        TemplateModelDistributionList.deleteAll(TemplateModelDistributionList.class, "reportid = ?", report_id);
        for (TemplateModelDistributionList t : templateModelDistributionLists) {
            t.setReport_id(report_id);
            t.save();
        }
    }

    public class Widgets extends RecyclerView.ViewHolder{
        Spinner spnTemplateNextDistributionList;
        Widgets(View rowView){
            super(rowView);
            this.spnTemplateNextDistributionList = (Spinner) rowView.findViewById(R.id.s_template_next_distribution_list);
        }
    }
}
