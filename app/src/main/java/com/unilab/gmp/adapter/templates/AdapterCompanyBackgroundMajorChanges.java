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
import com.unilab.gmp.model.TemplateModelCompanyBackgroundMajorChanges;

import java.util.List;

/**
 * Created by c_jhcanuto on 8/24/2017.
 */

public class AdapterCompanyBackgroundMajorChanges extends BaseAdapter {
    List<TemplateModelCompanyBackgroundMajorChanges> templateModelCompanyBackgroundMajorChanges;
    LayoutInflater inflater;
    Context context;
    boolean isCheck = true;

    public AdapterCompanyBackgroundMajorChanges(List<TemplateModelCompanyBackgroundMajorChanges> templateModelCompanyBackgroundMajorChanges, Context context) {
        this.templateModelCompanyBackgroundMajorChanges = templateModelCompanyBackgroundMajorChanges;
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return templateModelCompanyBackgroundMajorChanges.size();
    }

    @Override
    public Object getItem(int i) {
        return templateModelCompanyBackgroundMajorChanges.get(i);
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
            rowView = inflater.inflate(R.layout.custom_listview_template_company_background_major_changes, null);

            widgets.majorchanges = (EditText) rowView.findViewById(R.id.et_template_next_company_background_major_changes);

            widgets.majorchanges.setText(templateModelCompanyBackgroundMajorChanges.get(i).getMajorchanges());
            widgets.majorchanges.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    templateModelCompanyBackgroundMajorChanges.get(z).setMajorchanges(widgets.majorchanges.getText().toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
//            rowView.setTag(widgets);
//        } else {
//            widgets = (Widgets) rowView.getTag();
//        }
        if (!isCheck) {
            if (templateModelCompanyBackgroundMajorChanges.get(i).getMajorchanges().isEmpty()) {
                widgets.majorchanges.setError("This field is required");
            }
        }
        return rowView;
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

    public void save(String report_id) {
        TemplateModelCompanyBackgroundMajorChanges.deleteAll(TemplateModelCompanyBackgroundMajorChanges.class, "reportid = ?", report_id);
        for (TemplateModelCompanyBackgroundMajorChanges t : templateModelCompanyBackgroundMajorChanges) {
            t.setReport_id(report_id);
            t.save();
        }
    }

    public class Widgets {
        EditText majorchanges;
    }
}
