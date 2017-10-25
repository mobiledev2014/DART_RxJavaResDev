package com.unilab.gmp.adapter.templates;

import android.content.Context;
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
import com.unilab.gmp.model.AuditorsModel;
import com.unilab.gmp.model.TemplateModelAuditors;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by c_jhcanuto on 8/24/2017.
 */

public class AdapterAuditors extends BaseAdapter {
    List<TemplateModelAuditors> templateModelAuditors;
    LayoutInflater inflater;
    Context context;
    List<AuditorsModel> auditorsList;

    public AdapterAuditors(List<TemplateModelAuditors> templateModelScopeAudit, Context context) {
        this.templateModelAuditors = templateModelScopeAudit;
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        auditorsList = AuditorsModel.listAll(AuditorsModel.class);
    }

    @Override
    public int getCount() {
        return templateModelAuditors.size();
    }

    @Override
    public Object getItem(int i) {
        return templateModelAuditors.get(i);
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

            rowView = inflater.inflate(R.layout.custom_listview_template_auditors, null);

            widgets.name = (Spinner) rowView.findViewById(R.id.s_template_next_auditor_co_name);
            widgets.position = (EditText) rowView.findViewById(R.id.et_template_next_auditor_co_position);
            widgets.department = (EditText) rowView.findViewById(R.id.et_template_next_auditor_co_department);

            final List<String> list = new ArrayList<>();
            Log.d("SIZE", auditorsList.size() + "");
            int x = auditorsList.size();
            for (int count = 0; count < x; count++) {
                list.add(auditorsList.get(count).getFname() + " " + auditorsList.get(count).getLname());
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, list);
            widgets.name.setAdapter(adapter);
            widgets.name.setSelection(templateModelAuditors.get(i).getSelected());
            widgets.name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    templateModelAuditors.get(z).setName(widgets.name.getSelectedItem().toString());
                    templateModelAuditors.get(z).setSelected(i);
                    widgets.position.setText(auditorsList.get(i).getDesignation());
                    widgets.department.setText(auditorsList.get(i).getDesignation());
                    templateModelAuditors.get(z).setPosition(auditorsList.get(i).getDesignation());
                    templateModelAuditors.get(z).setDepartment(auditorsList.get(i).getDepartment());
                    templateModelAuditors.get(z).setAuditor_id(auditorsList.get(i).getAuditor_id());
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            widgets.position.setText(templateModelAuditors.get(i).getPosition());
            widgets.department.setText(templateModelAuditors.get(i).getDepartment());
            rowView.setTag(widgets);
        } else {
            widgets = (Widgets) rowView.getTag();
        }
        return rowView;
    }

    public int getAuditorSize() {
        return auditorsList.size();
    }

    public void save(String report_id) {
        for (TemplateModelAuditors t : templateModelAuditors) {
            t.setReport_id(report_id);
            t.save();
        }
    }

    public class Widgets {
        Spinner name;
        EditText position;
        EditText department;
    }
}
