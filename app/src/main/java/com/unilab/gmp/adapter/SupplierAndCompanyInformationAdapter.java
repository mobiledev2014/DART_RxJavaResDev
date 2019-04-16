package com.unilab.gmp.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.paolorotolo.expandableheightlistview.ExpandableHeightListView;
import com.unilab.gmp.R;
import com.unilab.gmp.adapter.templates.AdapterCompanyBackgroundMajorChanges;
import com.unilab.gmp.adapter.templates.AdapterCompanyBackgroundName;
import com.unilab.gmp.adapter.templates.AdapterInspectionDate;
import com.unilab.gmp.model.ModelCompany;
import com.unilab.gmp.model.ModelProduct;
import com.unilab.gmp.model.ModelSiteDate;
import com.unilab.gmp.model.TemplateModelCompanyBackgroundMajorChanges;
import com.unilab.gmp.model.TemplateModelCompanyBackgroundName;

import java.util.List;

/**
 * Created by c_rcmiguel on 7/31/2017.
 */

public class SupplierAndCompanyInformationAdapter extends BaseAdapter {
    LayoutInflater inflater;
    List<ModelCompany> supplierModels;
    Context context;
    Dialog dialogViewSupplier;

    List<ModelProduct> productLists;
    List<ModelSiteDate> previousDateList;
    List<TemplateModelCompanyBackgroundName> inspectorList;
    List<TemplateModelCompanyBackgroundMajorChanges> majorChangesList;

    SupplierAndCompanyProductViewAdapter supplierAndCompanyProductViewAdapter;
    AdapterInspectionDate adapterInspectionDate;
    AdapterCompanyBackgroundName adapterCompanyBackgroundName;
    AdapterCompanyBackgroundMajorChanges adapterCompanyBackgroundMajorChanges;

    boolean isDialogOpen = false;

    public SupplierAndCompanyInformationAdapter(Context context, List<ModelCompany> supplierModels) {
        this.supplierModels = supplierModels;
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return supplierModels.size();
    }

    @Override
    public Object getItem(int item) {
        return item;
    }

    @Override
    public long getItemId(int id) {
        return id;
    }

    @Override
    public View getView(int position, View rowView, ViewGroup viewGroup) {
        Widgets widgets;
        widgets = new Widgets();
        rowView = inflater.inflate(R.layout.custom_listview_supplier, null);

        widgets.rowBackground = (LinearLayout) rowView.findViewById(R.id.row_background);
        if (position % 2 == 0)
            widgets.rowBackground.setBackgroundColor(context.getResources().getColor(R.color.white));
        else
            widgets.rowBackground.setBackgroundColor(context.getResources().getColor(R.color.row_color));

        widgets.date_modified = (TextView) rowView.findViewById(R.id.tv_date_modified);
        widgets.viewInfo = (Button) rowView.findViewById(R.id.btn_view_info);
        widgets.name_of_site = (TextView) rowView.findViewById(R.id.tv_supplier_name_site);

        final ModelCompany supplierModel = supplierModels.get(position);
        final String address1 = supplierModel.getAddress1();
        final String address3 = supplierModel.getAddress2();
        final String address4 = supplierModel.getAddress3();
        final String company_name = supplierModel.getCompany_name();
        final String company_id = supplierModel.getCompany_id();
        final String background = supplierModel.getBackground();

        widgets.date_modified.setText(supplierModel.getUpdate_date());

        widgets.name_of_site.setText(company_name);

        widgets.viewInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogViewSupplier(company_id,
                        company_name,
                        address1 + ", " + address3 + ", " + address4,
                        background);
            }
        });
        return rowView;
    }

    public void dialogViewSupplier(String id, String Name, String Address, String Background) {
        if (!isDialogOpen) {
            dialogViewSupplier = new Dialog(context);
            dialogViewSupplier.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            dialogViewSupplier.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialogViewSupplier.setCancelable(false);
            dialogViewSupplier.setContentView(R.layout.dialog_supplier_view);
            dialogViewSupplier.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

            TextView name = (TextView) dialogViewSupplier.findViewById(R.id.tv_name);
            TextView address = (TextView) dialogViewSupplier.findViewById(R.id.tv_address);
            TextView background = (TextView) dialogViewSupplier.findViewById(R.id.tv_background);
            ExpandableHeightListView products = (ExpandableHeightListView) dialogViewSupplier.findViewById(R.id.lv_products);
            RecyclerView lvPreviousDate = (RecyclerView) dialogViewSupplier.findViewById(R.id.lv_previous_date);
            RecyclerView lvInspector = (RecyclerView) dialogViewSupplier.findViewById(R.id.lv_inspector);
            RecyclerView lvMajorChanges = (RecyclerView) dialogViewSupplier.findViewById(R.id.lv_major_changes);
            Button done = (Button) dialogViewSupplier.findViewById(R.id.btn_done);

            name.setText("Name: " + Name);
            address.setText("Address: " + Address);
            background.setText(Background);

            productLists = ModelProduct.find(ModelProduct.class, "companyid = ?", id);
            previousDateList = ModelSiteDate.find(ModelSiteDate.class, "companyid = ?", id);
            inspectorList = TemplateModelCompanyBackgroundName.find(TemplateModelCompanyBackgroundName.class, "companyid = ?", id);
            majorChangesList = TemplateModelCompanyBackgroundMajorChanges.find(TemplateModelCompanyBackgroundMajorChanges.class, "companyid = ?", id);

            supplierAndCompanyProductViewAdapter = new SupplierAndCompanyProductViewAdapter(context, productLists);
            products.setAdapter(supplierAndCompanyProductViewAdapter);
            products.setExpanded(true);

            adapterInspectionDate = new AdapterInspectionDate(context, previousDateList);

            lvPreviousDate.setLayoutManager(new LinearLayoutManager(context));
            lvPreviousDate.setItemAnimator(new DefaultItemAnimator());

            lvPreviousDate.setAdapter(adapterInspectionDate);

            adapterCompanyBackgroundName = new AdapterCompanyBackgroundName(inspectorList, context, 100);

            lvInspector.setLayoutManager(new LinearLayoutManager(context));
            lvInspector.setItemAnimator(new DefaultItemAnimator());

            lvInspector.setAdapter(adapterCompanyBackgroundName);
            adapterCompanyBackgroundMajorChanges = new AdapterCompanyBackgroundMajorChanges(majorChangesList, context, 100);

            lvMajorChanges.setLayoutManager(new LinearLayoutManager(context));
            lvMajorChanges.setItemAnimator(new DefaultItemAnimator());

            lvMajorChanges.setAdapter(adapterCompanyBackgroundMajorChanges);

            done.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogViewSupplier.dismiss();
                    isDialogOpen = false;
                }
            });

            isDialogOpen = true;
            dialogViewSupplier.show();
        }
    }

    public class Widgets {
        TextView name_of_site, date_modified;
        LinearLayout rowBackground;
        Button viewInfo;
    }
}
