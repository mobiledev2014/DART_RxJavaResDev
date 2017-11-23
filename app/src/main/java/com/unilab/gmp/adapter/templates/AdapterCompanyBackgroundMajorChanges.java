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
import com.unilab.gmp.model.TemplateModelCompanyBackgroundMajorChanges;

import java.util.List;

/**
 * Created by c_jhcanuto on 8/24/2017.
 */

public class AdapterCompanyBackgroundMajorChanges extends RecyclerView.Adapter<AdapterCompanyBackgroundMajorChanges.Widgets> {
    List<TemplateModelCompanyBackgroundMajorChanges> templateModelCompanyBackgroundMajorChanges;
    LayoutInflater inflater;
    Context context;
    boolean isCheck = true;
    int disable = 0;

    public AdapterCompanyBackgroundMajorChanges(List<TemplateModelCompanyBackgroundMajorChanges> templateModelCompanyBackgroundMajorChanges, Context context, int disable) {
        this.templateModelCompanyBackgroundMajorChanges = templateModelCompanyBackgroundMajorChanges;
        this.disable = disable;
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getItemCount() {
        return templateModelCompanyBackgroundMajorChanges.size();
    }

//    @Override
//    public Object getItem(int i) {
//        return templateModelCompanyBackgroundMajorChanges.get(i);
//    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public Widgets onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_listview_template_company_background_major_changes, parent, false);
        return new Widgets(v);
    }

    @Override
    public void onBindViewHolder(final Widgets widgets, final int position) {
        if (templateModelCompanyBackgroundMajorChanges.size() > position) {
            widgets.majorchanges.setText(templateModelCompanyBackgroundMajorChanges.get(position).getMajorchanges());
            widgets.majorchanges.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (position < templateModelCompanyBackgroundMajorChanges.size())
                        templateModelCompanyBackgroundMajorChanges.get(position).setMajorchanges(charSequence.toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        }
        if (!isCheck) {
            if (templateModelCompanyBackgroundMajorChanges.get(position).getMajorchanges().isEmpty()) {
                widgets.majorchanges.setError("This field is required");
            }
        }

        if (disable > position) {
            widgets.majorchanges.setEnabled(false);
        }

    }

    public boolean check() {
        isCheck = true;
        Log.e("getWidgets", "getWidgets1");
        for (TemplateModelCompanyBackgroundMajorChanges tmsa : templateModelCompanyBackgroundMajorChanges) {
            Log.e("getWidgets", "getWidgets2");
            if (tmsa.getMajorchanges().isEmpty()) {
                isCheck = false;
                break;
            }
        }
        if (!isCheck) {
            notifyDataSetChanged();
        }
        return isCheck;
    }

    public void save(String report_id, String company_id) {
        TemplateModelCompanyBackgroundMajorChanges.deleteAll(TemplateModelCompanyBackgroundMajorChanges.class, "reportid = ?", report_id);
        int i = 0;
        for (TemplateModelCompanyBackgroundMajorChanges t : templateModelCompanyBackgroundMajorChanges) {
            if (i++ >= disable && !t.getMajorchanges().isEmpty()) {
                t.setReport_id(report_id);
                t.setCompany_id(company_id);
                t.save();
                Log.e("nagsave ba?", "" + i);
            }
        }
    }

    public class Widgets extends RecyclerView.ViewHolder {
        EditText majorchanges;

        Widgets(View rowView) {
            super(rowView);
            this.majorchanges = (EditText) rowView.findViewById(R.id.et_template_next_company_background_major_changes);
        }
    }
}
