package com.unilab.gmp.adapter.templates;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
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

//    @Override
//    public Object getItem(int i) {
//        return templateModelTranslators.get(i);
//    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public Widgets onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_listview_template_translator, parent, false);
        return new Widgets(v);
    }

    @Override
    public void onBindViewHolder(Widgets widgets, final int position) {
        if (templateModelTranslators.size() > position) {
            widgets.translator.setText(templateModelTranslators.get(position).getTranslator());
            widgets.translator.setEnabled(Variable.isAuthorized);
            widgets.translator.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (position < templateModelTranslators.size())
                        templateModelTranslators.get(position).setTranslator(charSequence.toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            if (!isCheck) {
                if (templateModelTranslators.get(position).getTranslator().isEmpty()) {
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
        for (TemplateModelTranslator t : templateModelTranslators) {
            if (t.getTranslator().isEmpty())
                continue;
            t.setReport_id(report_id);
            t.save();
            translator += t.getTranslator();
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
