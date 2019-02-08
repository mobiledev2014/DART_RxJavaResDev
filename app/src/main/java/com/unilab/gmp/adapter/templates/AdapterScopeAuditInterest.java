package com.unilab.gmp.adapter.templates;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.unilab.gmp.R;
import com.unilab.gmp.model.ModelDisposition;
import com.unilab.gmp.model.ModelProduct;
import com.unilab.gmp.model.TemplateModelScopeAuditInterest;
import com.unilab.gmp.utility.Variable;

import java.util.ArrayList;
import java.util.List;

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

        adapterDispo = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, dispotList);
        adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, list);
    }

 /*   @Override
    public int getCount() {
        return templateModelScopeAuditInterests.size();
    }

    @Override
    public Object getItem(int i) {
        return templateModelScopeAuditInterests.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View rowView, ViewGroup viewGroup) {
        final int z = i;
        position = z + "";
        final Widgets widgets;
//        if (rowView == null) {

        rowView = inflater.inflate(R.layout.custom_listview_template_scope_audit_interest, null);

        widgets = new Widgets(rowView);
        widgets.spnDisposition.setAdapter(adapterDispo);
        widgets.spnDisposition.setEnabled(Variable.isAuthorized);
        widgets.spnTypeAudit.setAdapter(adapter);
        widgets.spnTypeAudit.setEnabled(Variable.isAuthorized);

        if (templateModelScopeAuditInterests.get(z).getDisposition_id().equals("0")) {
            templateModelScopeAuditInterests.get(z).setDisposition_id(modelDispositions.get(
                    widgets.spnDisposition.getSelectedItemPosition()).getDisposition_id());
        } else {
            templateModelScopeAuditInterests.get(z).setSelected2(idDispotList.indexOf(
                    templateModelScopeAuditInterests.get(i).getDisposition_id()));
            Log.e("testing", "asd dispo - " + templateModelScopeAuditInterests.get(i).getDisposition_id());
        }
        widgets.spnDisposition.setSelection(templateModelScopeAuditInterests.get(i).getSelected2());
        widgets.spnDisposition.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i <= 0) {
                    templateModelScopeAuditInterests.get(z).setDisposition("");
                    templateModelScopeAuditInterests.get(z).setSelected2(0);
                    templateModelScopeAuditInterests.get(z).setDisposition_id("");
                } else {
                    templateModelScopeAuditInterests.get(z).setDisposition(widgets.spnDisposition.getSelectedItem().toString());
                    templateModelScopeAuditInterests.get(z).setSelected2(i);
                    templateModelScopeAuditInterests.get(z).setDisposition_id(modelDispositions.get(i - 1).getDisposition_id());
                    Log.e("dis_id", modelDispositions.get(i - 1).getDisposition_id());
                    Log.e("TESTING-DISPOSITION", "POSITION: " + z + " DISPOSITION ID: " + modelDispositions.get(i - 1).getDisposition_id());
                    Variable.selectedDisposition.put(String.valueOf(z), modelDispositions.get(i - 1).getDisposition_id());
                    //productDisposition.put("", widgets.spnDisposition.getSelectedItem().toString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        if (templateModelScopeAuditInterests.get(z).getProduct_id().equals("0")) {
            templateModelScopeAuditInterests.get(z).setProduct_id(modelProducts.get(widgets.spnTypeAudit.getSelectedItemPosition()).getProduct_id());
//            templateModelScopeAuditInterests.get(z).setSelected(0);
        } else {
            templateModelScopeAuditInterests.get(z).setSelected(idList.indexOf(
                    templateModelScopeAuditInterests.get(i).getProduct_id()
            ));
            Log.e("testing", "asd product - " + templateModelScopeAuditInterests.get(i).getProduct_id());
        }

        widgets.spnTypeAudit.setSelection(templateModelScopeAuditInterests.get(i).getSelected());
        widgets.spnTypeAudit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //if (i < modelProducts.size() && i > 0) {
                if (i <= 0) {
                    templateModelScopeAuditInterests.get(z).setProduct_name("");
                    templateModelScopeAuditInterests.get(z).setSelected(0);
                    templateModelScopeAuditInterests.get(z).setProduct_id("");
                } else {
                    templateModelScopeAuditInterests.get(z).setProduct_name(widgets.spnTypeAudit.getSelectedItem().toString());
                    templateModelScopeAuditInterests.get(z).setSelected(i);
                    templateModelScopeAuditInterests.get(z).setProduct_id(modelProducts.get(i - 1).getProduct_id());
                    Log.e("pro_id", modelProducts.get(i - 1).getProduct_id());
                    Variable.selectedProduct.put(String.valueOf(z), modelProducts.get(i - 1).getProduct_id());
                    Log.e("TESTING-PRODUCT", "POSITION: " + z + " PRODUCT ID: " + modelProducts.get(i - 1).getProduct_id());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
//            rowView.setTag(widgets);
//        } else {
//            widgets = (Widgets) rowView.getTag();
//        }
        return rowView;
    }*/

//    public boolean check() {
//        isCheck = true;
//        tempPro = "";
//        tempDispo = "";
//
//        for (int i = 0; i < selectedProduct.size(); i++) {
//            if (tempPro.equals("")) {
//                tempPro = selectedProduct.get(i);
//                tempDispo = selectedDisposition.get(i);
//            } else {
//                for (int j = 0; j < selectedProduct.size(); j++) {
//                    if (j==i)
//                        continue;
//                    if (selectedProduct.get(i).equals(tempPro) &&
//                            selectedDisposition.get(i).equals(tempDispo))
//                        isCheck=false;
//                        break;
//                }
//
//            }
//            if (!isCheck)
//                break;
//        }
//
//        return isCheck;
//    }

    public int getTypeAuditSize() {
        return modelProducts.size();
    }

    public void save(String report_id, long id) {
        for (TemplateModelScopeAuditInterest t : templateModelScopeAuditInterests) {
            /*if (Variable.report_id.equals("0")) {
                t.setReport_id(report_id);
            } else {
                t.setReport_id(Variable.report_id);
            }

            t.setProduct_id(Variable.selectedProduct.get(position));
            t.setDisposition_id(Variable.selectedDisposition.get(position));

            t.setAudit_id(id + "");
            t.save();
        }*/
            t.setReport_id(report_id);
            t.setAudit_id(id + "");
            t.save();
        }

        //for testing
        //picked products and disposition
        //ModelAuditReports mar = ModelAuditReports.find(ModelAuditReports.class, "reportid = ?", Variable.report_id).get(0);
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
/*        widgets.spnDisposition.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });*/

        if (templateModelScopeAuditInterests.get(position).getProduct_id().equals("0")) {
            templateModelScopeAuditInterests.get(position).setProduct_id(modelProducts.get(widgets.spnTypeAudit.getSelectedItemPosition()).getProduct_id());
//            templateModelScopeAuditInterests.get(z).setSelected(0);
        } else {
            templateModelScopeAuditInterests.get(position).setSelected(idList.indexOf(
                    templateModelScopeAuditInterests.get(position).getProduct_id()
            ));
            Log.e("testing", "asd product - " + templateModelScopeAuditInterests.get(position).getProduct_id());
        }

        widgets.spnTypeAudit.setSelection(templateModelScopeAuditInterests.get(position).getSelected());
/*        widgets.spnTypeAudit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //if (i < modelProducts.size() && i > 0) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });*/

        selectDisposition(widgets);
        selectProductofInterest(widgets);
    }

    private void selectDisposition(final Widgets widgets){
        widgets.spnDisposition.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    int adapter_position = widgets.getAdapterPosition();
                   // Toast.makeText(context, "Position : " + position + " Adapter Position: " + widgets.getAdapterPosition()
                   //         , Toast.LENGTH_SHORT).show();
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
                        //productDisposition.put("", widgets.spnDisposition.getSelectedItem().toString());
                    }
                }catch (Exception e){
                    Log.e(TAG, "onItemSelected: "+e);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void selectProductofInterest(final Widgets widgets){
        widgets.spnTypeAudit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    int adapter_position = widgets.getAdapterPosition();
                   // Toast.makeText(context, "Position : " + position + " Adapter Position: " + widgets.getAdapterPosition()
                   //         , Toast.LENGTH_SHORT).show();
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
                }catch (Exception e){
                    Log.e(TAG, "onItemSelected: "+e);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

     public static boolean  checkProdofInterestAndDisposition(){
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
