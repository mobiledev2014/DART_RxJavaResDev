package com.unilab.gmp.adapter.templates;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.unilab.gmp.R;
import com.unilab.gmp.model.ModelDisposition;
import com.unilab.gmp.model.ModelProduct;
import com.unilab.gmp.model.TemplateModelScopeAuditInterest;
import com.unilab.gmp.utility.Variable;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by c_jhcanuto on 8/24/2017.
 */

public class AdapterScopeAuditInterest extends RecyclerView.Adapter<AdapterScopeAuditInterest.Widgets> {
    List<TemplateModelScopeAuditInterest> templateModelScopeAuditInterests;
    LayoutInflater inflater;
    Context context;
    List<ModelProduct> modelProducts;
    List<ModelDisposition> modelDispositions;

    ArrayAdapter<String> adapterDispo;
    List<String> idDispotList;

    ArrayAdapter<String> adapter;
    List<String> idList;
    String position = "";

    private static final String TAG = "AdapterScopeAuditIntere";
    private static boolean isCheck = true;

    public AdapterScopeAuditInterest(List<TemplateModelScopeAuditInterest> templateModelScopeAuditInterests, Context context, String company_id) {
        this.templateModelScopeAuditInterests = templateModelScopeAuditInterests;
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        modelProducts = ModelProduct.find(ModelProduct.class, "companyid = ? AND status > 0", company_id);
        modelDispositions = ModelDisposition.find(ModelDisposition.class, "status > 0");

        List<String> dispotList = new ArrayList<>();
        idDispotList = new ArrayList<>();
        int d = modelDispositions.size();

        dispotList.add("Select");
        idDispotList.add("0");

        for (int count = 0; count < d; count++) {
            dispotList.add(modelDispositions.get(count).getDisposition_name());
            idDispotList.add(modelDispositions.get(count).getDisposition_id());
        }

        List<String> list = new ArrayList<>();
        idList = new ArrayList<>();
        Log.d("SIZE", modelProducts.size() + "");
        int x = modelProducts.size();

        list.add("Select");
        idList.add("0");

        for (int count = 0; count < x; count++) {
            list.add(modelProducts.get(count).getProduct_name());
            idList.add(modelProducts.get(count).getProduct_id());
        }

        adapterDispo = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, dispotList);
        adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, list);
    }

    public int getTypeAuditSize() {
        return modelProducts.size();
    }

    public void save(String report_id, long id) {
        for (TemplateModelScopeAuditInterest t : templateModelScopeAuditInterests) {
            t.setReport_id(report_id);
            t.setAudit_id(id + "");
            t.save();
        }
        for (int i = 0; i < Variable.selectedDisposition.size(); i++) {
            Log.i("FOR TESTING DISPO-PRO", "REPORT ID: " + Variable.report_id +
                    " DISPO: " + Variable.selectedDisposition.get(i + "") +
                    " PRODUCT: " + Variable.selectedProduct.get(i + ""));
        }
    }

    @Override
    public Widgets onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_listview_template_scope_audit_interest, parent, false);
        return new AdapterScopeAuditInterest.Widgets(v);
    }

    @Override
    public void onBindViewHolder(Widgets widgets, int position) {

        widgets.spnDisposition.setAdapter(adapterDispo);
        widgets.spnDisposition.setEnabled(Variable.isAuthorized);
        widgets.spnTypeAudit.setAdapter(adapter);
        widgets.spnTypeAudit.setEnabled(Variable.isAuthorized);

        if (templateModelScopeAuditInterests.get(position).getDisposition_id().equals("0")) {
            templateModelScopeAuditInterests.get(position).setDisposition_id(modelDispositions.get(
                    widgets.spnDisposition.getSelectedItemPosition()).getDisposition_id());
        } else {
            templateModelScopeAuditInterests.get(position).setSelected2(idDispotList.indexOf(
                    templateModelScopeAuditInterests.get(position).getDisposition_id()));
            Log.e("testing", "asd dispo - " + templateModelScopeAuditInterests.get(position).getDisposition_id());
        }
        widgets.spnDisposition.setSelection(templateModelScopeAuditInterests.get(position).getSelected2());
        if (templateModelScopeAuditInterests.get(position).getProduct_id().equals("0")) {
            templateModelScopeAuditInterests.get(position).setProduct_id(modelProducts.get(widgets.spnTypeAudit.getSelectedItemPosition()).getProduct_id());
        } else {
            templateModelScopeAuditInterests.get(position).setSelected(idList.indexOf(
                    templateModelScopeAuditInterests.get(position).getProduct_id()
            ));
            Log.e("testing", "asd product - " + templateModelScopeAuditInterests.get(position).getProduct_id());
        }

        widgets.spnTypeAudit.setSelection(templateModelScopeAuditInterests.get(position).getSelected());
        selectDisposition(widgets);
        selectProductofInterest(widgets);
    }

    private void selectDisposition(final Widgets widgets) {
        widgets.spnDisposition.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    int adapter_position = widgets.getAdapterPosition();
                    if (position <= 0) {
                        isCheck = false;
                        templateModelScopeAuditInterests.get(adapter_position).setDisposition("");
                        templateModelScopeAuditInterests.get(adapter_position).setSelected2(0);
                        templateModelScopeAuditInterests.get(adapter_position).setDisposition_id("");
                    } else {
                        isCheck = true;
                        templateModelScopeAuditInterests.get(adapter_position).setDisposition(parent.getSelectedItem().toString());
                        templateModelScopeAuditInterests.get(adapter_position).setSelected2(position);
                        templateModelScopeAuditInterests.get(adapter_position).setDisposition_id(modelDispositions.get(position - 1).getDisposition_id());
                        Log.e("dis_id", modelDispositions.get(position - 1).getDisposition_id());
                        Log.e("TESTING-DISPOSITION", "POSITION: " + adapter_position + " DISPOSITION ID: " + modelDispositions.get(position - 1).getDisposition_id());
                        Variable.selectedDisposition.put(String.valueOf(adapter_position), modelDispositions.get(position - 1).getDisposition_id());
                    }
                } catch (Exception e) {
                    Log.e(TAG, "onItemSelected: " + e);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void selectProductofInterest(final Widgets widgets) {
        widgets.spnTypeAudit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    int adapter_position = widgets.getAdapterPosition();
                    if (position <= 0) {
                        isCheck = false;
                        templateModelScopeAuditInterests.get(adapter_position).setProduct_name("");
                        templateModelScopeAuditInterests.get(adapter_position).setSelected(0);
                        templateModelScopeAuditInterests.get(adapter_position).setProduct_id("");
                    } else {
                        isCheck = true;
                        templateModelScopeAuditInterests.get(adapter_position).setProduct_name(parent.getSelectedItem().toString());
                        templateModelScopeAuditInterests.get(adapter_position).setSelected(position);
                        templateModelScopeAuditInterests.get(adapter_position).setProduct_id(modelProducts.get(position - 1).getProduct_id());
                        Log.e("pro_id", modelProducts.get(position - 1).getProduct_id());
                        Variable.selectedProduct.put(String.valueOf(adapter_position), modelProducts.get(position - 1).getProduct_id());
                        Log.e("TESTING-PRODUCT", "POSITION: " + adapter_position + " PRODUCT ID: " + modelProducts.get(position - 1).getProduct_id());
                    }
                } catch (Exception e) {
                    Log.e(TAG, "onItemSelected: " + e);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public static boolean checkProdofInterestAndDisposition() {
        return isCheck;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getItemCount() {
        return templateModelScopeAuditInterests.size();
    }

    public class Widgets extends RecyclerView.ViewHolder {
        Spinner spnTypeAudit, spnDisposition;

        public Widgets(View rowView) {
            super(rowView);
            this.spnDisposition = (Spinner) rowView.findViewById(R.id.s_template_next_summary_recommendation_disposition);
            this.spnTypeAudit = (Spinner) rowView.findViewById(R.id.s_template_next_next_scope_audit_interest);
        }
    }


}
