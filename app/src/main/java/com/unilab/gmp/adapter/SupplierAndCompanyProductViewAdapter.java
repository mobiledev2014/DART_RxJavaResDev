package com.unilab.gmp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.unilab.gmp.R;
import com.unilab.gmp.model.ModelProduct;

import java.util.List;

/**
 * Created by c_rcmiguel on 8/29/2017.
 */

public class SupplierAndCompanyProductViewAdapter extends BaseAdapter {
    LayoutInflater inflater;
    List<ModelProduct> productList;
    Context context;
    String id;

    public SupplierAndCompanyProductViewAdapter(Context context, List<ModelProduct> productList) {
        this.productList = productList;
        this.context = context;
        this.id = id;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return productList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View rowview, ViewGroup viewGroup) {
        Widgets widgets;
//        if (rowview == null) {
            widgets = new Widgets();
            rowview = inflater.inflate(R.layout.custom_listview_product_views, null);

            widgets.rowBackground = (LinearLayout) rowview.findViewById(R.id.row_background);
            if (position % 2 == 0)
                widgets.rowBackground.setBackgroundColor(context.getResources().getColor(R.color.white));
            else
                widgets.rowBackground.setBackgroundColor(context.getResources().getColor(R.color.row_color));

            //widgets.type = (TextView) rowview.findViewById(R.id.tv_dialog_type);
            widgets.name = (TextView) rowview.findViewById(R.id.tv_dialog_name);

            final ModelProduct product = productList.get(position);
            //widgets.type.setText(product.getType());
            widgets.name.setText(product.getProduct_name());
            rowview.setTag(widgets);
//        } else {
//            widgets = (Widgets) rowview.getTag();
//        }
        return rowview;
    }

    public class Widgets {
        TextView type, name;
        LinearLayout rowBackground;
    }
}
