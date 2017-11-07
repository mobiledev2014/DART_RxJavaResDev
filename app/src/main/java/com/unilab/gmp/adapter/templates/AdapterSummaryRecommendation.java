package com.unilab.gmp.adapter.templates;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.unilab.gmp.R;
import com.unilab.gmp.model.ModelTemplateElements;
import com.unilab.gmp.model.TemplateModelSummaryRecommendation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by c_jhcanuto on 8/24/2017.
 */

public class AdapterSummaryRecommendation extends BaseAdapter {
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
    public int getCount() {
        return templateModelSummaryRecommendations.size();
    }

    @Override
    public Object getItem(int i) {
        return templateModelSummaryRecommendations.get(i);
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
            rowView = inflater.inflate(R.layout.custom_listview_template_summary_recommendation, null);

            widgets.element = (Spinner) rowView.findViewById(R.id.s_template_next_summary_recommendation_element);
            widgets.remarks = (EditText) rowView.findViewById(R.id.et_template_next_summary_recommendation_remarks);


            List<String> list = new ArrayList<>();
//        list.add("Select element");
            Log.d("SIZE", mte.size() + "");
            int x = mte.size();
            for (int count = 0; count < x; count++) {
                list.add(mte.get(count).getElement_name());
            }

            if (templateModelSummaryRecommendations.get(i).getElement_id().isEmpty()) {
                templateModelSummaryRecommendations.get(i).setElement_id(mte.get(0).getElement_id());
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, list);
            widgets.element.setAdapter(adapter);
            widgets.element.setSelection(templateModelSummaryRecommendations.get(i).getSelected());
            widgets.element.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    templateModelSummaryRecommendations.get(z).setElement(widgets.element.getSelectedItem().toString());
                    templateModelSummaryRecommendations.get(z).setSelected(i);
                    templateModelSummaryRecommendations.get(z).setElement_id(mte.get(i).getElement_id());
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            widgets.remarks.setText(templateModelSummaryRecommendations.get(i).getRemarks());
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
//            rowView.setTag(widgets);
//        } else {
//            widgets = (Widgets) rowView.getTag();
//        }
        return rowView;
    }

    public int getRSize() {
        return mte.size();
    }

    public void save(String report_id) {
        TemplateModelSummaryRecommendation.deleteAll(TemplateModelSummaryRecommendation.class, "reportid = ?", report_id);
        for (TemplateModelSummaryRecommendation t : templateModelSummaryRecommendations) {
            t.setReport_id(report_id);
            t.save();
        }
    }

    public class Widgets {
        Spinner element;
        EditText remarks;
    }
}
