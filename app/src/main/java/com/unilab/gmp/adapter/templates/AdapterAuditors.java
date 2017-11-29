package com.unilab.gmp.adapter.templates;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.unilab.gmp.R;
import com.unilab.gmp.model.AuditorsModel;
import com.unilab.gmp.model.TemplateModelAuditors;
import com.unilab.gmp.utility.SharedPreferenceManager;
import com.unilab.gmp.utility.Variable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by c_jhcanuto on 8/24/2017.
 */

public class AdapterAuditors extends RecyclerView.Adapter<AdapterAuditors.Widgets> {
    List<TemplateModelAuditors> templateModelAuditors;
    LayoutInflater inflater;
    Context context;
    List<AuditorsModel> auditorsList;

    public AdapterAuditors(List<TemplateModelAuditors> templateModelScopeAudit, Context context, String lead) {
        this.templateModelAuditors = templateModelScopeAudit;
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        auditorsList = AuditorsModel.find(AuditorsModel.class, "status > 0 AND auditorid != ?", new String[]{lead});
    }

    @Override
    public int getItemCount() {
        return templateModelAuditors.size();
    }

//    @Override
//    public Object getItem(int i) {
//        return templateModelAuditors.get(i);
//    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public Widgets onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_listview_template_auditors, parent, false);
        return new Widgets(v);
    }

    @Override
    public void onBindViewHolder(final Widgets widgets, int i) {
        final int z = i;

        final List<String> list = new ArrayList<>();
        Log.d("SIZE", auditorsList.size() + "");
        int x = auditorsList.size();
        SharedPreferenceManager sharedPreferenceManager = new SharedPreferenceManager(context);
        String email = sharedPreferenceManager.getStringData("EMAIL");

        for (int count = 0; count < x; count++) {
//            if (auditorsList.get(count).getEmail().equals(email)) {
//                auditorsList.remove(count);
//                x--;
//            }
            if (templateModelAuditors.get(i).getAuditor_id().equals(auditorsList.get(count).getAuditor_id())) {
                templateModelAuditors.get(i).setSelected(count);
            }
            list.add(auditorsList.get(count).getFname() + " " + auditorsList.get(count).getLname());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, list);
        widgets.name.setAdapter(adapter);
        widgets.name.setSelection(templateModelAuditors.get(i).getSelected());
        widgets.name.setEnabled(Variable.isAuthorized);
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
                Log.e("auditor ID", widgets.name.getSelectedItem().toString() + " --- " + templateModelAuditors.get(z).getAuditor_id());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        widgets.position.setText(templateModelAuditors.get(i).getPosition());
        widgets.position.setEnabled(Variable.isAuthorized);
        widgets.department.setText(templateModelAuditors.get(i).getDepartment()+", "+templateModelAuditors.get(i).getCompany());
        widgets.department.setEnabled(Variable.isAuthorized);
    }

    public int getAuditorSize() {
        return auditorsList.size();
    }

    public void save(String report_id) {
        for (TemplateModelAuditors t : templateModelAuditors) {
            t.setReport_id(report_id);
            t.save();
            Log.e("auditor ID", t.getAuditor_id());
        }
    }

    public class Widgets extends RecyclerView.ViewHolder {
        Spinner name;
        EditText position;
        EditText department;

        Widgets(View rowView) {
            super(rowView);
            this.name = (Spinner) rowView.findViewById(R.id.s_template_next_auditor_co_name);
            this.position = (EditText) rowView.findViewById(R.id.et_template_next_auditor_co_position);
            this.department = (EditText) rowView.findViewById(R.id.et_template_next_auditor_co_department);
        }

    }
}
