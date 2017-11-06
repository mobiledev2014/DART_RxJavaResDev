package com.unilab.gmp.adapter.templates;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.github.paolorotolo.expandableheightlistview.ExpandableHeightListView;
import com.unilab.gmp.R;
import com.unilab.gmp.fragment.NextSelectedAuditReportFragment;
import com.unilab.gmp.fragment.NextSelectedTemplateFragment;
import com.unilab.gmp.model.ModelDisposition;
import com.unilab.gmp.model.ModelTypeAudit;
import com.unilab.gmp.model.TemplateModelScopeAudit;
import com.unilab.gmp.model.TemplateModelScopeAuditInterest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by c_rcmiguel on 8/23/2017.
 */

public class AdapterScopeAudit extends BaseAdapter {
    List<List<TemplateModelScopeAuditInterest>> templateModelScopeAuditInterests;
    List<TemplateModelScopeAudit> templateModelScopeAudit;
    LayoutInflater inflater;
    Context context;
    List<ModelTypeAudit> scopeAudits;
    List<ModelDisposition> dispositions;
    String companyId;
    //AdapterScopeAuditInterest adapterScope;
    NextSelectedTemplateFragment nextSelectedTemplateFragment;
    ArrayAdapter<String> adapter;
    List<String> idList;
    boolean isCheck = true;

    public AdapterScopeAudit(List<TemplateModelScopeAudit> templateModelScopeAudit, Context context
            , String company_id, NextSelectedTemplateFragment nextSelectedTemplateFragment,
                             NextSelectedAuditReportFragment nextSelectedAuditReportFragment) {
        this.templateModelScopeAudit = templateModelScopeAudit;
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        scopeAudits = ModelTypeAudit.listAll(ModelTypeAudit.class);
        dispositions = ModelDisposition.listAll(ModelDisposition.class);
        this.companyId = company_id;
        this.nextSelectedTemplateFragment = nextSelectedTemplateFragment;
        Log.e("AdapterScope", getCount() + " count");
        templateModelScopeAuditInterests = new ArrayList<>();

        List<String> list = new ArrayList<>();
        idList = new ArrayList<>();
        Log.d("SIZE", scopeAudits.size() + "");
        int x = scopeAudits.size();
        for (int count = 0; count < x; count++) {
            list.add(scopeAudits.get(count).getScope_name());
            idList.add(scopeAudits.get(count).getScope_id());
        }

        adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, list);

    }

    @Override
    public int getCount() {
        return templateModelScopeAudit.size();
    }

    @Override
    public Object getItem(int i) {
        return templateModelScopeAudit.get(i);
    }

    @Override
    public long getItemId(int i) {
        //return Long.parseLong(templateModelScopeAudit.get(i).getScope_id());
        return i;
    }

    @Override
    public View getView(int i, View rowView, final ViewGroup viewGroup) {
        final Widgets widgets;
        final int z = i;
        if (rowView == null) {
            widgets = new Widgets();
            rowView = inflater.inflate(R.layout.custom_listview_template_scope_audit, null);
            widgets.spnTypeAudit = (Spinner) rowView.findViewById(R.id.s_template_next_next_scope_audit);
            widgets.remarks = (EditText) rowView.findViewById(R.id.et_template_next_scope_audit_remarks);
            widgets.disposition = (Spinner) rowView.findViewById(R.id.s_template_next_summary_recommendation_disposition);
            widgets.btnTemplateNextScopeAuditInterestAdd = (Button) rowView.findViewById(R.id.btn_template_next_scope_audit_interest_add);
            widgets.btnTemplateNextScopeAuditInterestDelete = (Button) rowView.findViewById(R.id.btn_template_next_scope_audit_interest_delete);
            widgets.lvTemplateNextScopeAuditInterest = (ExpandableHeightListView) rowView.findViewById(R.id.lv_template_next_scope_audit_interest);
            rowView.setTag(widgets);
            widgets.spnTypeAudit.setAdapter(adapter);


        } else {
            widgets = (Widgets) rowView.getTag();
        }

        if (templateModelScopeAuditInterests.size() < templateModelScopeAudit.size()) {
            templateModelScopeAuditInterests.add(new ArrayList<TemplateModelScopeAuditInterest>());
            templateModelScopeAuditInterests.get(i).addAll(TemplateModelScopeAuditInterest.find(TemplateModelScopeAuditInterest.class, "reportid = ? AND auditid = ?", templateModelScopeAudit.get(i).getReport_id(), templateModelScopeAudit.get(i).getId() + ""));
        }
        templateModelScopeAudit.get(i).setAdapterScope(new AdapterScopeAuditInterest(templateModelScopeAuditInterests.get(i), context, companyId));
        widgets.lvTemplateNextScopeAuditInterest.setAdapter(templateModelScopeAudit.get(i).getAdapterScope());
        widgets.lvTemplateNextScopeAuditInterest.setExpanded(true);

        if (templateModelScopeAuditInterests.get(i).size() == 0) {
            addScopeAuditTypeInterest(templateModelScopeAudit.get(i).getAdapterScope(), i);
        }

        if (templateModelScopeAudit.get(z).getScope_id().isEmpty()) {
            templateModelScopeAudit.get(z).setScope_id(scopeAudits.get(widgets.spnTypeAudit.getSelectedItemPosition()).getScope_id());
        } else {
            templateModelScopeAudit.get(z).setSelected(idList.indexOf(templateModelScopeAudit.get(z).getScope_id()));
        }

        widgets.spnTypeAudit.setSelection(templateModelScopeAudit.get(i).getSelected());
        widgets.spnTypeAudit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                templateModelScopeAudit.get(z).setScope_name(widgets.spnTypeAudit.getSelectedItem().toString());
                templateModelScopeAudit.get(z).setSelected(i);
                templateModelScopeAudit.get(z).setScope_id(scopeAudits.get(i).getScope_id());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

//        templateModelScopeAudit.get(i).setEtremarks(widgets.remarks);
        widgets.remarks.setText(templateModelScopeAudit.get(i).getScope_detail());
        widgets.remarks.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                templateModelScopeAudit.get(z).setScope_detail(widgets.remarks.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        widgets.btnTemplateNextScopeAuditInterestAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addScopeAuditTypeInterest(templateModelScopeAudit.get(z).getAdapterScope(), z);
                //Toast.makeText(context, "Product of interest add:" + templateModelScopeAuditInterests.size(), Toast.LENGTH_SHORT).show();
                getView(z, null, viewGroup);
            }
        });

        widgets.btnTemplateNextScopeAuditInterestDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(context, "Product of interest delete", Toast.LENGTH_SHORT).show();
                if (templateModelScopeAuditInterests.get(z).size() > 1) {
                    templateModelScopeAuditInterests.get(z).remove(templateModelScopeAuditInterests.get(z).size() - 1);
                    //templateModelScopeAudit.get(z).getAdapterScope().notifyDataSetChanged();
                    notifyDataSetChanged();
                }
            }
        });


        if (!isCheck) {
            if (templateModelScopeAudit.get(i).getScope_detail().isEmpty()) {
                widgets.remarks.setError("This field is required");
            }
        }
        return rowView;
    }

    public boolean check() {
        isCheck = true;
        Log.e("getWidgets", "getWidgets1");
        for (TemplateModelScopeAudit tmsa : templateModelScopeAudit) {
            Log.e("getWidgets", "getWidgets2");
            if (tmsa.getScope_detail().isEmpty()) {
                isCheck = false;
                break;
            }
        }
        if (!isCheck) {
            notifyDataSetChanged();
        }
        return isCheck;
    }

    public int getTypeAuditSize() {
        return scopeAudits.size();
    }

    public void save(String report_id) {
        TemplateModelScopeAudit.deleteAll(TemplateModelScopeAudit.class, "reportid = ?", report_id);
        TemplateModelScopeAuditInterest.deleteAll(TemplateModelScopeAuditInterest.class, "reportid = ?", report_id);
        for (TemplateModelScopeAudit tmsa : templateModelScopeAudit) {
            tmsa.setReport_id(report_id);
            tmsa.save();
            tmsa.getAdapterScope().save(report_id, tmsa.getId());
        }
    }


    private void addScopeAuditTypeInterest(AdapterScopeAuditInterest adapterScopeAuditInterest, int pos) {
        if (adapterScopeAuditInterest.getTypeAuditSize() > templateModelScopeAuditInterests.get(pos).size()) {
            Log.i("ADD", "CLICKED");
            TemplateModelScopeAuditInterest t = new TemplateModelScopeAuditInterest();
            t.setTemplate_id(companyId);
            templateModelScopeAuditInterests.get(pos).add(t);
            // adapterScope.notifyDataSetChanged();
            notifyDataSetChanged();
        }
    }

    public class Widgets {
        EditText remarks;
        Spinner spnTypeAudit;
        Spinner disposition;
        Button btnTemplateNextScopeAuditInterestAdd;
        Button btnTemplateNextScopeAuditInterestDelete;
        ExpandableHeightListView lvTemplateNextScopeAuditInterest;
    }

//    public class Test extends AsyncTask<String, String, Boolean> {
//
//        int z;
//        Widgets widgets;
//        public Test(Widgets widgets,int z){
//            this.z = z;
//            this.widgets = widgets;
//        }
//
//        @Override
//        protected Boolean doInBackground(String... strings) {
//
//            return null;
//        }
//    }
}
