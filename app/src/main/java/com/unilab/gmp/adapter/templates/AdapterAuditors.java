package com.unilab.gmp.adapter.templates;

import android.content.Context;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import androidx.recyclerview.widget.RecyclerView;

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

        widgets.name.setEnabled(Variable.isAuthorized);

        list.add("Select");

        for (int count = 0; count < x; count++) {
            if (templateModelAuditors.get(i).getAuditor_id().equals(auditorsList.get(count).getAuditor_id())) {
                templateModelAuditors.get(i).setSelected(count + 1);
            }
            list.add(auditorsList.get(count).getFname() + " " + auditorsList.get(count).getLname());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, list);
        widgets.name.setAdapter(adapter);
        widgets.name.setSelection(templateModelAuditors.get(i).getSelected());
        widgets.name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int index = 0;
                if (i > 0) {
                    index = i - 1;
                }

                templateModelAuditors.get(z).setName(widgets.name.getSelectedItem().toString());
                templateModelAuditors.get(z).setSelected(i);

                if (widgets.name.getSelectedItem().toString().equals("Select")) {
                    widgets.department.setText("Select");
                    widgets.position.setText("Select");
                } else {
                    widgets.position.setText(auditorsList.get(index).getDesignation());
                    widgets.department.setText(auditorsList.get(index).getDepartment());
                    templateModelAuditors.get(z).setPosition(auditorsList.get(index).getDesignation());
                    templateModelAuditors.get(z).setDepartment(auditorsList.get(index).getDepartment());
                }

                if (i == 0) {
                    templateModelAuditors.get(z).setAuditor_id("0");
                } else {
                    templateModelAuditors.get(z).setAuditor_id(auditorsList.get(index).getAuditor_id());
                }

                Log.e("auditor ID", widgets.name.getSelectedItem().toString() + " --- " +
                        " " + i + " sss " + templateModelAuditors.get(z).getAuditor_id());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Log.e("auditor ID", widgets.name.getSelectedItem().toString() + " --- " +
                        " " + "sample" + " sss " + templateModelAuditors.get(z).getAuditor_id());
            }
        });

        widgets.position.setText(templateModelAuditors.get(i).getPosition());
        widgets.department.setText(templateModelAuditors.get(i).getDepartment());
    }

    public int getAuditorSize() {
        return auditorsList.size();
    }

    public void save(String report_id) {
        for (TemplateModelAuditors templateModelAuditor : templateModelAuditors) {
            templateModelAuditor.setReport_id(report_id);
            templateModelAuditor.save();
            Log.e("auditorID", "id : " + templateModelAuditor.getAuditor_id());
        }
    }

    public boolean check() {
        boolean isCheck = true;
        Set<String> lump = new HashSet<>();
        for (TemplateModelAuditors templateModelAuditor : templateModelAuditors) {
            if (!templateModelAuditor.getAuditor_id().equals("0")) {
                if (lump.contains(templateModelAuditor.getAuditor_id())) {
                    isCheck = false;
                    break;
                }
                lump.add(templateModelAuditor.getAuditor_id());
            }
        }

        if (!isCheck) {
            notifyDataSetChanged();
        }
        return isCheck;
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
