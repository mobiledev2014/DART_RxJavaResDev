package com.unilab.gmp.adapter.templates;

import android.content.Context;
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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by c_jhcanuto on 8/24/2017.
 */

public class AdapterDistributionList extends BaseAdapter {
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

        distributionList = ModelDistribution.listAll(ModelDistribution.class);
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
    public int getCount() {
        return templateModelDistributionLists.size();
    }

    @Override
    public Object getItem(int i) {
        return templateModelDistributionLists.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View rowView, ViewGroup viewGroup) {
        final int z = i;
        final Widgets widgets;
//        if (rowView == null) {
            widgets = new Widgets();
            rowView = inflater.inflate(R.layout.custom_listview_template_distribution_list, null);

            widgets.spnTemplateNextDistributionList = (Spinner) rowView.findViewById(R.id.s_template_next_distribution_list);
            widgets.spnTemplateNextDistributionList.setAdapter(adapterDistri);

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

        return rowView;
    }

    public void save(String report_id) {
        TemplateModelDistributionList.deleteAll(TemplateModelDistributionList.class, "reportid = ?", report_id);
        for (TemplateModelDistributionList t : templateModelDistributionLists) {
            t.setReport_id(report_id);
            t.save();
        }
    }

    public class Widgets {
        Spinner spnTemplateNextDistributionList;
    }
}
