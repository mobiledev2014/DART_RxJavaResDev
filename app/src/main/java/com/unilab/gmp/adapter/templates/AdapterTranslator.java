package com.unilab.gmp.adapter.templates;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.unilab.gmp.R;
import com.unilab.gmp.model.TemplateModelTranslator;
import com.unilab.gmp.utility.Variable;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by c_jhcanuto on 8/24/2017.
 */

public class AdapterTranslator extends RecyclerView.Adapter<AdapterTranslator.Widgets> {
    List<TemplateModelTranslator> templateModelTranslators;
    LayoutInflater inflater;
    Context context;

    boolean isCheck = true;

    public AdapterTranslator(List<TemplateModelTranslator> templateModelTranslators, Context context) {
        this.templateModelTranslators = templateModelTranslators;
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getItemCount() {
        return templateModelTranslators.size();
    }

    @Override
    public long getItemId(int id) {
        return id;
    }

    @Override
    public Widgets onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_listview_template_translator, parent, false);
        return new Widgets(view);
    }

    @Override
    public void onBindViewHolder(final Widgets widgets, int position) {
        if (templateModelTranslators.size() > position) {
            widgets.translator.setText(templateModelTranslators.get(widgets.getAdapterPosition()).getTranslator());
            widgets.translator.setEnabled(Variable.isAuthorized);
            widgets.translator.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (widgets.getAdapterPosition() < templateModelTranslators.size())
                        templateModelTranslators.get(widgets.getAdapterPosition()).setTranslator(charSequence.toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            if (!isCheck) {
                if (templateModelTranslators.get(widgets.getAdapterPosition()).getTranslator().isEmpty()) {
                    isCheck = true;
                    widgets.translator.setError("This field is required");
                }
            }
        }
    }

    public boolean check() {
        isCheck = true;
        Log.e("getWidgets", "getWidgets1");
        for (TemplateModelTranslator tmsa : templateModelTranslators) {
            Log.e("getWidgets", "getWidgets2");
            if (tmsa.getTranslator().isEmpty()) {
                isCheck = false;
                break;
            }
        }
        if (!isCheck) {
            notifyDataSetChanged();
        }
        return isCheck;
    }

    public String save(String report_id) {
        String translator = "";
        TemplateModelTranslator.deleteAll(TemplateModelTranslator.class, "reportid = ?", report_id);
        int counter = 1;
        for (TemplateModelTranslator templateModelTranslator : templateModelTranslators) {
            if (templateModelTranslator.getTranslator().isEmpty())
                continue;
            templateModelTranslator.setReport_id(report_id);
            templateModelTranslator.save();
            translator += templateModelTranslator.getTranslator();
            if (counter++ != templateModelTranslators.size()) {
                translator += "\n";
            }
        }
        return translator;
    }

    public class Widgets extends RecyclerView.ViewHolder {
        EditText translator;

        Widgets(View rowView) {
            super(rowView);
            this.translator = (EditText) rowView.findViewById(R.id.et_template_next_translator);
        }
    }
}
