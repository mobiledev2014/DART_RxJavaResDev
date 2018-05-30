package com.unilab.gmp.adapter.templates;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.unilab.gmp.R;
import com.unilab.gmp.model.ModelTemplateElements;
import com.unilab.gmp.model.TemplateModelSummaryRecommendation;
import com.unilab.gmp.utility.Variable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by c_jhcanuto on 8/24/2017.
 */

public class AdapterSummaryRecommendation extends RecyclerView.Adapter<AdapterSummaryRecommendation.Widgets> {
    List<TemplateModelSummaryRecommendation> templateModelSummaryRecommendations;
    LayoutInflater inflater;
    Context context;
    List<ModelTemplateElements> mte;

    public AdapterSummaryRecommendation(List<TemplateModelSummaryRecommendation> templateModelSummaryRecommendations, Context context, String id) {
        this.templateModelSummaryRecommendations = templateModelSummaryRecommendations;
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mte = ModelTemplateElements.find(ModelTemplateElements.class, "templateid = ?", id);

        ModelTemplateElements mte2 = new ModelTemplateElements();
        mte2.setElement_id("0");
        mte2.setElement_name("None");
        mte.add(0, mte2);
    }

    @Override
    public int getItemCount() {
        return templateModelSummaryRecommendations.size();
    }

//    @Override
//    public Object getItem(int i) {
//        return templateModelSummaryRecommendations.get(i);
//    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public Widgets onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_listview_template_summary_recommendation, parent, false);
        return new Widgets(v);
    }

    @Override
    public void onBindViewHolder(final Widgets widgets, int i) {
        final int z = i;

        List<String> list = new ArrayList<>();
//        list.add("Select element");
        Log.d("SIZE", mte.size() + "");
        int x = mte.size();
        for (int count = 0; count < x; count++) {
            if (templateModelSummaryRecommendations.get(i).getElement_id().equals(mte.get(count).getElement_id())) {
                templateModelSummaryRecommendations.get(i).setSelected(count);
            }
            list.add(mte.get(count).getElement_name());
        }

        if (templateModelSummaryRecommendations.get(i).getElement_id().isEmpty()) {
            templateModelSummaryRecommendations.get(i).setElement_id(mte.get(0).getElement_id());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, list);
        widgets.element.setAdapter(adapter);
        widgets.element.setSelection(templateModelSummaryRecommendations.get(i).getSelected());
        widgets.element.setEnabled(Variable.isAuthorized);

        widgets.element.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                templateModelSummaryRecommendations.get(z).setElement(widgets.element.getSelectedItem().toString());
                templateModelSummaryRecommendations.get(z).setSelected(i);
                templateModelSummaryRecommendations.get(z).setElement_id(mte.get(i).getElement_id());

                if (i > 0) {
                    if (Variable.isAuthorized) {
                        widgets.remarks.setEnabled(true);
                    } else {
                        widgets.remarks.setEnabled(Variable.isAuthorized);
                    }
                } else {
                    widgets.remarks.setEnabled(false);
                    widgets.remarks.setText("");
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        widgets.remarks.setText(templateModelSummaryRecommendations.get(i).getRemarks());
        widgets.remarks.setEnabled(Variable.isAuthorized);
        widgets.remarks.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                templateModelSummaryRecommendations.get(z).setRemarks(widgets.remarks.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        if (widgets.element.getSelectedItemPosition() == 0) {
            widgets.remarks.setEnabled(false);
        }
    }

    public int getRSize() {
        return mte.size();
    }

    public void save(String report_id) {
        TemplateModelSummaryRecommendation.deleteAll(TemplateModelSummaryRecommendation.class, "reportid = ?", report_id);
        for (TemplateModelSummaryRecommendation t : templateModelSummaryRecommendations) {
            if (t.getElement().equalsIgnoreCase("none"))
                continue;
            t.setReport_id(report_id);
            t.save();
        }
    }

    public class Widgets extends RecyclerView.ViewHolder {
        Spinner element;
        EditText remarks;

        Widgets(View rowView) {
            super(rowView);
            this.element = (Spinner) rowView.findViewById(R.id.s_template_next_summary_recommendation_element);
            this.remarks = (EditText) rowView.findViewById(R.id.et_template_next_summary_recommendation_remarks);
        }
    }
}
