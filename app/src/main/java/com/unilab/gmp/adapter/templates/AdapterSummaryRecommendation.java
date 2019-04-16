package com.unilab.gmp.adapter.templates;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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
    List<ModelTemplateElements> modelTemplateElementsList;

    public AdapterSummaryRecommendation(List<TemplateModelSummaryRecommendation> templateModelSummaryRecommendations, Context context, String id) {
        this.templateModelSummaryRecommendations = templateModelSummaryRecommendations;
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        modelTemplateElementsList = ModelTemplateElements.find(ModelTemplateElements.class, "templateid = ?", id);

        ModelTemplateElements modelTemplateElementsList2 = new ModelTemplateElements();
        modelTemplateElementsList2.setElement_id("0");
        modelTemplateElementsList2.setElement_name("None");
        modelTemplateElementsList.add(0, modelTemplateElementsList2);
    }

    @Override
    public int getItemCount() {
        return templateModelSummaryRecommendations.size();
    }

    @Override
    public long getItemId(int id) {
        return id;
    }

    @Override
    public Widgets onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_listview_template_summary_recommendation, parent, false);
        return new Widgets(view);
    }

    @Override
    public void onBindViewHolder(final Widgets widgets, int position) {
        List<String> list = new ArrayList<>();
        Log.d("SIZE", modelTemplateElementsList.size() + "");
        int x = modelTemplateElementsList.size();
        for (int count = 0; count < x; count++) {
            if (templateModelSummaryRecommendations.get(widgets.getAdapterPosition()).getElement_id().equals(modelTemplateElementsList.get(count).getElement_id())) {
                templateModelSummaryRecommendations.get(widgets.getAdapterPosition()).setSelected(count);
            }
            list.add(modelTemplateElementsList.get(count).getElement_name());
        }

        if (templateModelSummaryRecommendations.get(widgets.getAdapterPosition()).getElement_id().isEmpty()) {
            templateModelSummaryRecommendations.get(widgets.getAdapterPosition()).setElement_id(modelTemplateElementsList.get(0).getElement_id());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, list);
        widgets.element.setAdapter(adapter);
        widgets.element.setSelection(templateModelSummaryRecommendations.get(widgets.getAdapterPosition()).getSelected());
        widgets.element.setEnabled(Variable.isAuthorized);

        widgets.element.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                templateModelSummaryRecommendations.get(widgets.getAdapterPosition()).setElement(widgets.element.getSelectedItem().toString());
                templateModelSummaryRecommendations.get(widgets.getAdapterPosition()).setSelected(i);
                templateModelSummaryRecommendations.get(widgets.getAdapterPosition()).setElement_id(modelTemplateElementsList.get(i).getElement_id());

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

        widgets.remarks.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (widgets.remarks.hasFocus()) {
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                    switch (event.getAction() & MotionEvent.ACTION_MASK) {
                        case MotionEvent.ACTION_SCROLL:
                            v.getParent().requestDisallowInterceptTouchEvent(false);
                            return true;
                    }
                }
                return false;
            }
        });


        Log.e("GET REMARKS", "onBindViewHolder: " + templateModelSummaryRecommendations.get(widgets.getAdapterPosition()).getRemarks());

        if (templateModelSummaryRecommendations.get(widgets.getAdapterPosition()).getRemarks() != null) {
            widgets.remarks.setText(templateModelSummaryRecommendations.get(widgets.getAdapterPosition()).getRemarks().replace("&lt;br&gt;", "\n").replace("&#34;", "\""));
        } else {
            widgets.remarks.setText(templateModelSummaryRecommendations.get(widgets.getAdapterPosition()).getRemarks());
        }

        widgets.remarks.setEnabled(Variable.isAuthorized);
        widgets.remarks.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                templateModelSummaryRecommendations.get(widgets.getAdapterPosition()).setRemarks(widgets.remarks.getText().toString().replaceAll("[\r\n]+", "&lt;br&gt;").replace("\"", "&#34;"));
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
        return modelTemplateElementsList.size();
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
