package com.unilab.gmp.adapter.templates;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.unilab.gmp.R;
import com.unilab.gmp.model.ModelDistribution;
import com.unilab.gmp.model.TemplateModelDistributionOthers;
import com.unilab.gmp.utility.Variable;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by c_jhcanuto on 8/24/2017.
 */

public class AdapterDistributionOthers extends RecyclerView.Adapter<AdapterDistributionOthers.Widgets> {
    LayoutInflater inflater;
    Context context;
    List<TemplateModelDistributionOthers> templateModelDistributionOthers;

    public AdapterDistributionOthers(List<TemplateModelDistributionOthers> templateModelDistributionOthers, Context context) {
        this.templateModelDistributionOthers = templateModelDistributionOthers;
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getItemCount() {
        return templateModelDistributionOthers.size();
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public Widgets onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_listview_template_other_distribution, parent, false);
        return new Widgets(view);
    }

    @Override
    public void onBindViewHolder(final Widgets widgets, int i) {
        final int z = i;
        List<ModelDistribution> distributionList = ModelDistribution.listAll(ModelDistribution.class);
        if (distributionList.size() > i) {
            widgets.distributionOther.setText(distributionList.get(z).getDistribution_name());
        }
        widgets.distributionOther.setText(templateModelDistributionOthers.get(i).getDistribution_other());
        widgets.distributionOther.setEnabled(Variable.isAuthorized);
        widgets.distributionOther.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                templateModelDistributionOthers.get(z).setDistribution_other(widgets.distributionOther.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    public static boolean isEmpty(String distribution_other) {
        return distribution_other == null || distribution_other.isEmpty() || distribution_other.equals("");
    }

    public void save(String report_id) {
        TemplateModelDistributionOthers.deleteAll(TemplateModelDistributionOthers.class, "reportid = ?", report_id);

        for (TemplateModelDistributionOthers t : templateModelDistributionOthers) {
            if (isEmpty(t.getDistribution_other()))
                continue;
            t.setReport_id(report_id);
            t.save();
        }
    }

    public class Widgets extends RecyclerView.ViewHolder {
        EditText distributionOther;

        Widgets(View rowView) {
            super(rowView);
            this.distributionOther = (EditText) rowView.findViewById(R.id.et_template_next_distribution_other);
        }
    }
}
