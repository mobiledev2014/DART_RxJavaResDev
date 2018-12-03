package com.unilab.gmp.adapter.templates;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.paolorotolo.expandableheightlistview.ExpandableHeightListView;
import com.unilab.gmp.R;
import com.unilab.gmp.fragment.NextSelectedAuditReportFragment;
import com.unilab.gmp.fragment.NextSelectedTemplateFragment;
import com.unilab.gmp.model.ModelDisposition;
import com.unilab.gmp.model.ModelTypeAudit;
import com.unilab.gmp.model.TemplateModelScopeAudit;
import com.unilab.gmp.model.TemplateModelScopeAuditInterest;
import com.unilab.gmp.utility.Variable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by c_rcmiguel on 8/23/2017.
 */

public class AdapterScopeAudit extends RecyclerView.Adapter<AdapterScopeAudit.Widgets> {
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
    Dialog dialogDeleteDateOfAudit;
    Button btn_add;
    int simpleMessageDialog = -1, delete = 1;
    boolean isDialogOpen = false;
    private boolean onBind;
    boolean clicked = false;

    public AdapterScopeAudit(List<TemplateModelScopeAudit> templateModelScopeAudit, Context context
            , String company_id, NextSelectedTemplateFragment nextSelectedTemplateFragment,
                             NextSelectedAuditReportFragment nextSelectedAuditReportFragment, Button btn_add) {
        this.templateModelScopeAudit = templateModelScopeAudit;
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        scopeAudits = ModelTypeAudit.find(ModelTypeAudit.class, "status > 0");
        dispositions = ModelDisposition.find(ModelDisposition.class, "status > 0");
        this.companyId = company_id;
        this.nextSelectedTemplateFragment = nextSelectedTemplateFragment;

        templateModelScopeAuditInterests = new ArrayList<>();
        this.btn_add = btn_add;

        List<String> list = new ArrayList<>();
        idList = new ArrayList<>();
        Log.d("SIZE", scopeAudits.size() + "");
        int x = scopeAudits.size();

        list.add("Select");
        idList.add("0");

        for (int count = 0; count < x; count++) {
            list.add(scopeAudits.get(count).getScope_name());
            idList.add(scopeAudits.get(count).getScope_id());
        }

        adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, list);

    }

    @Override
    public int getItemCount() {
        return templateModelScopeAudit.size();
    }

//    @Override
//    public Object getItem(int i) {
//        return templateModelScopeAudit.get(i);
//    }

    @Override
    public long getItemId(int i) {
        //return Long.parseLong(templateModelScopeAudit.get(i).getScope_id());
        return i;
    }


    @Override
    public Widgets onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_listview_template_scope_audit, parent, false);
        return new Widgets(v);
    }

    @Override
    public void onBindViewHolder(final Widgets widgets, int i) {
        onBind = true;
        final int z = i;

        widgets.spnTypeAudit.setAdapter(adapter);
        widgets.spnTypeAudit.setEnabled(Variable.isAuthorized);
        if (templateModelScopeAuditInterests.size() < templateModelScopeAudit.size()) {
//        if (TemplateModelScopeAuditInterest.find(TemplateModelScopeAuditInterest.class, "reportid = ? AND auditid = ?", templateModelScopeAudit.get(i).getReport_id(), templateModelScopeAudit.get(i).getId() + "").size() > 0) {
                templateModelScopeAuditInterests.add(new ArrayList<TemplateModelScopeAuditInterest>());
                templateModelScopeAuditInterests.get(i).addAll(TemplateModelScopeAuditInterest.find(TemplateModelScopeAuditInterest.class, "reportid = ? AND auditid = ?", templateModelScopeAudit.get(i).getReport_id(), i + ""));// templateModelScopeAudit.get(i).getId() + ""));
                Log.e("Tessst", templateModelScopeAudit.get(i).getReport_id() + " --- " + templateModelScopeAuditInterests.size());
        } else {
            templateModelScopeAuditInterests.add(new ArrayList<TemplateModelScopeAuditInterest>());
        }
        templateModelScopeAudit.get(i).setAdapterScope(new AdapterScopeAuditInterest(templateModelScopeAuditInterests.get(i), context, companyId));
        widgets.lvTemplateNextScopeAuditInterest.setAdapter(templateModelScopeAudit.get(i).getAdapterScope());
        widgets.lvTemplateNextScopeAuditInterest.setExpanded(true);
        widgets.lvTemplateNextScopeAuditInterest.setEnabled(Variable.isAuthorized);

        if (templateModelScopeAuditInterests.get(i).size() == 0) {
            addScopeAuditTypeInterest(templateModelScopeAudit.get(i).getAdapterScope(), i);
        }

        if (templateModelScopeAudit.get(z).getScope_id().isEmpty()) {
            //templateModelScopeAudit.get(z).setScope_id(scopeAudits.get(widgets.spnTypeAudit.getSelectedItemPosition()).getScope_id());
            templateModelScopeAudit.get(z).setScope_id(0 + "");
            Log.i("SAVED-ITEM", "SCOPE TEST: " + scopeAudits.get(widgets.spnTypeAudit.getSelectedItemPosition()).getScope_id());
            Log.i("SAVED-ITEM", "SCOPE TEST 2: " + scopeAudits.get(0).getScope_id());
            Log.i("SAVED-ITEM", "SCOPE TEST POSITION: " + widgets.spnTypeAudit.getSelectedItemPosition());
        } else {
            templateModelScopeAudit.get(z).setSelected(idList.indexOf(templateModelScopeAudit.get(z).getScope_id()));
            Log.i("SAVED-ITEM", "SCOPE TEST ELSE: " + idList.indexOf(templateModelScopeAudit.get(z).getScope_id()));
            Log.i("SAVED-ITEM", "SCOPE TEST POSITION ELSE: " + templateModelScopeAudit.get(z).getScope_id());
        }

        //widgets.spnTypeAudit.setSelection(templateModelScopeAudit.get(i).getSelected());
        Log.i("GET ITEM", "VALUE: " + templateModelScopeAudit.get(i).getSelected());
        widgets.spnTypeAudit.setSelection(templateModelScopeAudit.get(i).getSelected());

        widgets.spnTypeAudit.setEnabled(Variable.isAuthorized);
        widgets.spnTypeAudit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    int index = i - 1;
                    templateModelScopeAudit.get(z).setScope_name(widgets.spnTypeAudit.getSelectedItem().toString());
                    templateModelScopeAudit.get(z).setSelected(i);
                    templateModelScopeAudit.get(z).setScope_id(scopeAudits.get(index).getScope_id());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

//        templateModelScopeAudit.get(i).setEtremarks(widgets.remarks);
        //widgets.remarks.setText(templateModelScopeAudit.get(i).getScope_detail().replace("&lt;br&gt;", "\n").replace("&#8718;","▪").replace("\"","&#34;"));
        //widgets.remarks.setText(templateModelScopeAudit.get(i).getScope_detail().replace("&lt;br&gt;", "\n").replace("&#8718;","▪").replace("\"","&#34;"));

        widgets.remarks.setText(templateModelScopeAudit.get(i).getScope_detail().replace("&lt;br&gt;", "\n").replace("&#34;","\""));
        widgets.remarks.setEnabled(Variable.isAuthorized);
        widgets.remarks.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (z < templateModelScopeAudit.size())
                    templateModelScopeAudit.get(z).setScope_detail(widgets.remarks.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        widgets.remarks.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (widgets.remarks.hasFocus()) {
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                    switch (event.getAction() & MotionEvent.ACTION_MASK){
                        case MotionEvent.ACTION_SCROLL:
                            v.getParent().requestDisallowInterceptTouchEvent(false);
                            return true;
                    }
                }
                return false;
            }
        });

        widgets.btnTemplateNextScopeAuditInterestAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clicked = true;
                addScopeAuditTypeInterest(templateModelScopeAudit.get(z).getAdapterScope(), z);
                //Toast.makeText(context, "Product of interest add:" + templateModelScopeAuditInterests.size(), Toast.LENGTH_SHORT).show();
//                getView(z, null, viewGroup);
            }
        });
        widgets.btnTemplateNextScopeAuditInterestAdd.setEnabled(Variable.isAuthorized);

        widgets.btnTemplateNextScopeAuditInterestDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(context, "Product of interest delete", Toast.LENGTH_SHORT).show();
                if (templateModelScopeAuditInterests.get(z).size() > 1) {
                    dialogInterestdelete("Are you sure you want to delete?", z, delete,templateModelScopeAudit.get(z).getAdapterScope());
                }
            }
        });

        widgets.btnTemplateNextScopeAuditInterestDelete.setEnabled(Variable.isAuthorized);

        /*if (!isCheck) {
            if (templateModelScopeAudit.get(i).getScope_detail().isEmpty()) {
                widgets.remarks.setError("This field is required");
            }
        }*/
        onBind = false;
    }

    public void dialogDeleteDateConfirmation(String mess, final int z, int action) {
        if (!isDialogOpen) {
            dialogDeleteDateOfAudit = new Dialog(context);
            dialogDeleteDateOfAudit.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            dialogDeleteDateOfAudit.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialogDeleteDateOfAudit.setCancelable(false);
            dialogDeleteDateOfAudit.setContentView(R.layout.dialog_exit_confirmation);
            dialogDeleteDateOfAudit.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

            TextView msg = (TextView) dialogDeleteDateOfAudit.findViewById(R.id.tv_message);
            Button yes = (Button) dialogDeleteDateOfAudit.findViewById(R.id.btn_yes);
            Button no = (Button) dialogDeleteDateOfAudit.findViewById(R.id.btn_no);

            msg.setText(mess);
            if (action == simpleMessageDialog) {
                yes.setVisibility(View.GONE);
                no.setText("Close");
            }
            yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.i("BUTTON-POSITION", "VALUE : " + z + " SIZE : " + (templateModelScopeAuditInterests.get(z).size() - 1));
                    Variable.selectedProduct.remove((templateModelScopeAuditInterests.get(z).size() - 1) + "");
                    Variable.selectedDisposition.remove((templateModelScopeAuditInterests.get(z).size() - 1) + "");

                    templateModelScopeAuditInterests.get(z).remove(templateModelScopeAuditInterests.get(z).size() - 1);
                    //templateModelScopeAudit.get(z).getAdapterScope().notifyDataSetChanged();
                    notifyDataSetChanged();
                    isDialogOpen = false;
                    dialogDeleteDateOfAudit.dismiss();
                }
            });

            no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    isDialogOpen = false;
                    dialogDeleteDateOfAudit.dismiss();
                }
            });

            isDialogOpen = true;
            dialogDeleteDateOfAudit.show();
        }
    }

    public void dialogInterestdelete(String mess, final int z, int action,final AdapterScopeAuditInterest adapterScopeAuditInterest) {
        if (!isDialogOpen) {
            dialogDeleteDateOfAudit = new Dialog(context);
            dialogDeleteDateOfAudit.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            dialogDeleteDateOfAudit.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialogDeleteDateOfAudit.setCancelable(false);
            dialogDeleteDateOfAudit.setContentView(R.layout.dialog_exit_confirmation);
            dialogDeleteDateOfAudit.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

            TextView msg = (TextView) dialogDeleteDateOfAudit.findViewById(R.id.tv_message);
            Button yes = (Button) dialogDeleteDateOfAudit.findViewById(R.id.btn_yes);
            Button no = (Button) dialogDeleteDateOfAudit.findViewById(R.id.btn_no);

            msg.setText(mess);
            if (action == simpleMessageDialog) {
                yes.setVisibility(View.GONE);
                no.setText("Close");
            }
            yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.i("BUTTON-POSITION", "VALUE : " + z + " SIZE : " + (templateModelScopeAuditInterests.get(z).size() - 1));
                    Variable.selectedProduct.remove((templateModelScopeAuditInterests.get(z).size() - 1) + "");
                    Variable.selectedDisposition.remove((templateModelScopeAuditInterests.get(z).size() - 1) + "");

                    templateModelScopeAuditInterests.get(z).remove(templateModelScopeAuditInterests.get(z).size() - 1);
                    //templateModelScopeAudit.get(z).getAdapterScope().notifyDataSetChanged();

                    adapterScopeAuditInterest.notifyDataSetChanged();

                    isDialogOpen = false;
                    dialogDeleteDateOfAudit.dismiss();
                }
            });

            no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    isDialogOpen = false;
                    dialogDeleteDateOfAudit.dismiss();
                }
            });

            isDialogOpen = true;
            dialogDeleteDateOfAudit.show();
        }
    }

    public boolean check() {
        isCheck = true;
        Log.e("getWidgets", "getWidgets1");


        Set<String> lump = new HashSet<>();
        for (TemplateModelScopeAudit tmsa : templateModelScopeAudit) {
            /*if (tmsa.getScope_detail().isEmpty()) {
                isCheck = false;
                break;
            }*/

            if (lump.contains(tmsa.getScope_id())) {
                isCheck = false;
                break;
            }
            lump.add(tmsa.getScope_id());
        }


        if (!isCheck) {
            notifyDataSetChanged();
        }
        return isCheck;
    }

    public boolean check2() {
        isCheck = true;
        Log.e("getWidgets", "getWidgets1");
        Set<String> lump = new HashSet<>();
        for (TemplateModelScopeAudit tmsa : templateModelScopeAudit) {

            if (lump.contains(tmsa.getScope_id())) {
                isCheck = false;
                break;
            }
            lump.add(tmsa.getScope_id());
        }
        if (!isCheck) {
            notifyDataSetChanged();
        }

        return isCheck;
    }

    public boolean check4() {
        isCheck = true;

        for (TemplateModelScopeAudit tmsa : templateModelScopeAudit) {
            Log.i("SCOPE-COUNT", "" + tmsa.getScope_id());
            if (tmsa.getScope_id().equals("0")){
                isCheck = false;
                break;
            }
        }

        return isCheck;
    }

    public boolean check3() {
        isCheck = true;
        if (checkProDis()) {
            Log.e("DUP-PRO-DISPO", "VALUE : " + checkProDis());
            isCheck = true;
        } else {
            Log.e("DUP-PRO-DISPO", "VALUE : " + checkProDis());
            isCheck = false;
        }
        return isCheck;
    }

    public boolean checkProDis() {
        isCheck = true;
        String temporaryProduct = "xxx";
        String temporaryDisposition = "xxx";

        Log.i("TEST-PRODUCT", "" + Variable.selectedProduct.get("0"));
        Log.i("TEST-DISPOSITION", "" + Variable.selectedDisposition.get("0"));
        Log.i("TEST-SIZE", "" + Variable.selectedProduct.size());

        for (int i = 0; i < Variable.selectedProduct.size(); i++) {
            Log.i("TEST-PRODUCT IF", "" + temporaryProduct);
            Log.i("TEST-DISPOSITION IF", "" + temporaryDisposition);

            if (temporaryProduct.equals("xxx")) {
                temporaryProduct = Variable.selectedProduct.get(i + "");
                temporaryDisposition = Variable.selectedDisposition.get(i + "");

                Log.i("TEST-PRODUCT IF IF", "" + Variable.selectedProduct.get(i + ""));
                Log.i("TEST-DISPOSITION IF IF", "" + Variable.selectedDisposition.get(i + ""));
            } else {
                Log.i("TEST-PRODUCT ELSE", "" + temporaryProduct);
                Log.i("TEST-DISPOSITION ELSE", "" + temporaryDisposition);

                for (int j = 0; j < Variable.selectedProduct.size(); j++) {
                    Log.i("ERROR-TRAP", "" + Variable.selectedDisposition.get(i + ""));
                    if (j == i) {
                        continue;
                    }
                    if (Variable.selectedProduct.get(i + "").equals(temporaryProduct) &&
                            Variable.selectedDisposition.get(i + "").equals(temporaryDisposition)) {
                        isCheck = false;
                        break;
                    }
                }
            }
            if (!isCheck)
                break;
        }
        return isCheck;
    }

    boolean duplicates(final List<String> array) {
        Set<String> lump = new HashSet<>();
        for (String i : array) {
            if (lump.contains(i)) return true;
            lump.add(i);
        }
        return false;
    }

    public int getTypeAuditSize() {
        return scopeAudits.size();
    }

    public void save(String report_id) {
        TemplateModelScopeAudit.deleteAll(TemplateModelScopeAudit.class, "reportid = ?", report_id);
        TemplateModelScopeAuditInterest.deleteAll(TemplateModelScopeAuditInterest.class, "reportid = ?", report_id);
        int counter = 0;
        for (TemplateModelScopeAudit tmsa : templateModelScopeAudit) {
            //if (!tmsa.getScope_detail().isEmpty()) {
            Log.i("SAVED-ITEM", "SCOPE: " + tmsa.getScope_id() + " SIZE: " + templateModelScopeAudit.size() + "\n");
            tmsa.setReport_id(report_id);
            tmsa.setAudit_id("" + counter);
            tmsa.save();
            if (tmsa.getAdapterScope() != null)
                tmsa.getAdapterScope().save(report_id, counter);

            counter++;
            //}
        }
    }


    public void addScopeAuditTypeInterest(AdapterScopeAuditInterest adapterScopeAuditInterest, int pos) {
        if (adapterScopeAuditInterest.getTypeAuditSize() > templateModelScopeAuditInterests.get(pos).size()) {

            if (clicked) {

            }

            Log.i("ADD", "CLICKED");

            TemplateModelScopeAuditInterest t = new TemplateModelScopeAuditInterest();
            t.setTemplate_id(companyId);

            templateModelScopeAuditInterests.get(pos).add(t);

            Log.e("I am here", "addScopeAuditTypeInterest: I am here "+templateModelScopeAudit.get(0).getScope_detail());

            // adapterScope.notifyDataSetChanged();
            if (!onBind) {
                if(clicked){
                    adapterScopeAuditInterest.notifyDataSetChanged();
                }else{
                    notifyDataSetChanged();
                }
            }

        } else {
            if (adapterScopeAuditInterest.getTypeAuditSize() > 0)
                dialogDeleteDateConfirmation("You've reached the maximum number of "
                        + adapterScopeAuditInterest.getTypeAuditSize(), 0, simpleMessageDialog);
        }
    }

    public class Widgets extends RecyclerView.ViewHolder {
        EditText remarks;
        Spinner spnTypeAudit;
        Spinner disposition;
        Button btnTemplateNextScopeAuditInterestAdd;
        Button btnTemplateNextScopeAuditInterestDelete;
        ExpandableHeightListView lvTemplateNextScopeAuditInterest;

        public Widgets(View rowView) {
            super(rowView);
            this.spnTypeAudit = (Spinner) rowView.findViewById(R.id.s_template_next_next_scope_audit);
            this.remarks = (EditText) rowView.findViewById(R.id.et_template_next_scope_audit_remarks);
            this.disposition = (Spinner) rowView.findViewById(R.id.s_template_next_summary_recommendation_disposition);
            this.btnTemplateNextScopeAuditInterestAdd = (Button) rowView.findViewById(R.id.btn_template_next_scope_audit_interest_add);
            this.btnTemplateNextScopeAuditInterestDelete = (Button) rowView.findViewById(R.id.btn_template_next_scope_audit_interest_delete);
            this.lvTemplateNextScopeAuditInterest = (ExpandableHeightListView) rowView.findViewById(R.id.lv_template_next_scope_audit_interest);
        }
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
