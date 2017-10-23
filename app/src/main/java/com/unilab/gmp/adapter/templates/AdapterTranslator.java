package com.unilab.gmp.adapter.templates;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;

import com.unilab.gmp.R;
import com.unilab.gmp.model.TemplateModelTranslator;

import java.util.List;

/**
 * Created by c_jhcanuto on 8/24/2017.
 */

public class AdapterTranslator extends BaseAdapter {
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
    public int getCount() {
        return templateModelTranslators.size();
    }

    @Override
    public Object getItem(int i) {
        return templateModelTranslators.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View rowView, ViewGroup viewGroup) {
        final int z = i;
        final Widgets widgets;
        if (rowView == null) {
            widgets = new Widgets();
            rowView = inflater.inflate(R.layout.custom_listview_template_translator, null);

            widgets.translator = (EditText) rowView.findViewById(R.id.et_template_next_translator);

            widgets.translator.setText(templateModelTranslators.get(i).getTranslator());
            widgets.translator.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    templateModelTranslators.get(z).setTranslator(widgets.translator.getText().toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            rowView.setTag(widgets);
        } else {
            widgets = (Widgets) rowView.getTag();
        }
        if (!isCheck) {
            if (templateModelTranslators.get(i).getTranslator().isEmpty()) {
                widgets.translator.setError("This field is required");
            }
        }
        return rowView;
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
            t.setReport_id(report_id);
            t.save();
            translator += t.getTranslator();
            if (counter++ != templateModelTranslators.size()) {
                translator += "\n";
            }
        }
        return translator;
    }

    public class Widgets {
        EditText translator;
    }
}
