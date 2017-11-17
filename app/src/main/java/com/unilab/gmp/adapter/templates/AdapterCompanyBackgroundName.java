package com.unilab.gmp.adapter.templates;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;

import com.unilab.gmp.R;
import com.unilab.gmp.model.TemplateModelCompanyBackgroundName;

import java.util.List;

/**
 * Created by c_jhcanuto on 8/24/2017.
 */

public class AdapterCompanyBackgroundName extends RecyclerView.Adapter<AdapterCompanyBackgroundName.Widgets> {
    List<TemplateModelCompanyBackgroundName> templateModelCompanyBackgroundNames;
    LayoutInflater inflater;
    Context context;
    int disable = 0;

    public AdapterCompanyBackgroundName(List<TemplateModelCompanyBackgroundName> templateModelCompanyBackgroundNames, Context context, int disable) {
        this.disable = disable;
        this.templateModelCompanyBackgroundNames = templateModelCompanyBackgroundNames;
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getItemCount() {
        return templateModelCompanyBackgroundNames.size();
    }

//    @Override
//    public Object getItem(int i) {
//        return templateModelCompanyBackgroundNames.get(i);
//    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public Widgets onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_listview_template_company_background_name,parent, false);
        return new Widgets(v);
    }

    @Override
    public void onBindViewHolder(Widgets widgets, int i) {
        final int z = i;

        widgets.bgname.setText(templateModelCompanyBackgroundNames.get(i).getBgname());
        widgets.bgname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                templateModelCompanyBackgroundNames.get(z).setBgname(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
//            rowView.setTag(widgets);
//        } else {
//            widgets = (Widgets) rowView.getTag();
//        }

        if (disable > i) {
            widgets.bgname.setEnabled(false);
        }
    }

    public void save(String report_id) {
        TemplateModelCompanyBackgroundName.deleteAll(TemplateModelCompanyBackgroundName.class, "reportid = ?", report_id);
        int i = 0;
        for (TemplateModelCompanyBackgroundName t : templateModelCompanyBackgroundNames) {
            if (i++ < disable) {
                continue;
            }
            t.setReport_id(report_id);
            t.save();
        }
    }

    public class Widgets extends RecyclerView.ViewHolder{
        EditText bgname;
        Widgets(View rowView){
            super(rowView);
            this.bgname = (EditText) rowView.findViewById(R.id.et_template_next_company_background_name);
        }
    }
}
