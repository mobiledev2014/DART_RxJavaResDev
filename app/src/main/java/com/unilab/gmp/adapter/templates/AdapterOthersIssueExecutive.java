package com.unilab.gmp.adapter.templates;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;

import com.unilab.gmp.R;
import com.unilab.gmp.model.ModelDistribution;
import com.unilab.gmp.model.TemplateModelDistributionOthers;

import java.util.List;

/**
 * Created by c_jhcanuto on 8/24/2017.
 */

public class AdapterOthersIssueExecutive extends BaseAdapter {
    LayoutInflater inflater;
    Context context;
    List<TemplateModelDistributionOthers> templateModelDistributionOthers;

    public AdapterOthersIssueExecutive(List<TemplateModelDistributionOthers> templateModelDistributionOthers, Context context) {
        this.templateModelDistributionOthers = templateModelDistributionOthers;
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return templateModelDistributionOthers.size();
    }

    @Override
    public Object getItem(int i) {
        return templateModelDistributionOthers.get(i);
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
            rowView = inflater.inflate(R.layout.custom_listview_template_other_issue_executive, null);

            List<ModelDistribution> distributionList = ModelDistribution.listAll(ModelDistribution.class);

            widgets.otherIssueExecutive = (EditText) rowView.findViewById(R.id.et_template_next_other_issue_executive);

            widgets.otherIssueExecutive.setText(distributionList.get(z).getDistribution_name());
            widgets.otherIssueExecutive.setText(templateModelDistributionOthers.get(i).getDistribution_other());
            widgets.otherIssueExecutive.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    templateModelDistributionOthers.get(z).setDistribution_other(widgets.otherIssueExecutive.getText().toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
//            rowView.setTag(widgets);
//        } else {
//            widgets = (Widgets) rowView.getTag();
//        }
        return rowView;
    }

    public void save(String report_id) {
        TemplateModelDistributionOthers.deleteAll(TemplateModelDistributionOthers.class, "reportid = ?", report_id);
        for (TemplateModelDistributionOthers t : templateModelDistributionOthers) {
            t.setReport_id(report_id);
            t.save();
        }
    }

    public class Widgets {
        EditText otherIssueExecutive;

    }
}
