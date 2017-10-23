package com.unilab.gmp.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import com.unilab.gmp.model.ModelCompany;
import com.unilab.gmp.model.ModelProduct;

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
    SupplierAndCompanyProductViewAdapter supplierAndCompanyProductViewAdapter;

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
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View rowView, ViewGroup viewGroup) {
        Widgets widgets;
        if (rowView == null) {
            widgets = new Widgets();
            rowView = inflater.inflate(R.layout.custom_listview_supplier, null);

            widgets.rowBackground = (LinearLayout) rowView.findViewById(R.id.row_background);
            if (position % 2 == 0)
                widgets.rowBackground.setBackgroundColor(context.getResources().getColor(R.color.white));
            else
                widgets.rowBackground.setBackgroundColor(context.getResources().getColor(R.color.row_color));

            widgets.address = (TextView) rowView.findViewById(R.id.tv_supplier_address);
            widgets.viewInfo = (Button) rowView.findViewById(R.id.btn_view_info);
            widgets.name_of_site = (TextView) rowView.findViewById(R.id.tv_supplier_name_site);

            final ModelCompany supplierModel = supplierModels.get(position);
            final String address1 = supplierModel.getAddress1();
            final String address3 = supplierModel.getAddress2();
            final String address4 = supplierModel.getAddress3();
            final String company_name = supplierModel.getCompany_name();
            final String company_id = supplierModel.getCompany_id();
            final String background = supplierModel.getBackground();
            widgets.address.setText(address1 + ", " + address3 + ", " + address4);

            widgets.name_of_site.setText(company_name);

            widgets.viewInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Toast.makeText(context, "View supplier info and products", Toast.LENGTH_SHORT).show();

                    dialogViewSupplier(company_id,
                            company_name,
                            address1 + ", " + address3 + ", " + address4,
                            background);
                }
            });
            rowView.setTag(widgets);
        } else {
            widgets = (Widgets) rowView.getTag();
        }
        return rowView;
    }

    public class Widgets {
        TextView name_of_site, address;// date_modified;
        LinearLayout rowBackground;
        Button viewInfo;
    }

    public void dialogViewSupplier(String id, String Name, String Address, String Background) {
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
        Button done = (Button) dialogViewSupplier.findViewById(R.id.btn_done);

        name.setText("Name: " + Name);
        address.setText("Designation: " + Address);
        background.setText("Company: " + Background);

        productLists = ModelProduct.find(ModelProduct.class, "companyid = ?", id);
        supplierAndCompanyProductViewAdapter = new SupplierAndCompanyProductViewAdapter(context, productLists);
        products.setAdapter(supplierAndCompanyProductViewAdapter);
        products.setExpanded(true);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogViewSupplier.dismiss();
            }
        });

        dialogViewSupplier.show();
    }
}
