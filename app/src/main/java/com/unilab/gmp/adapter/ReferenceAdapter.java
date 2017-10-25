package com.unilab.gmp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.unilab.gmp.R;
import com.unilab.gmp.model.ReferenceModel;

import java.util.ArrayList;

/**
 * Created by c_jhcanuto on 7/31/2017.
 */

public class ReferenceAdapter extends BaseAdapter {
    ArrayList<ReferenceModel> referenceModels;
    Context context;
    LayoutInflater inflater;

    public ReferenceAdapter(Context context, ArrayList<ReferenceModel> referenceModels) {
        this.referenceModels = referenceModels;
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return referenceModels.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class Widgets {
        TextView name_of_site, name_of_license, date_modified;
        LinearLayout rowBackground;
    }

    @Override
    public View getView(int position, View rowview, ViewGroup viewGroup) {
        Widgets widgets;
//        if (rowview == null) {
            widgets = new Widgets();
            rowview = inflater.inflate(R.layout.custom_listview_reference, null);

            widgets.rowBackground = (LinearLayout) rowview.findViewById(R.id.row_background);
            if (position % 2 == 0)
                widgets.rowBackground.setBackgroundColor(context.getResources().getColor(R.color.white));
            else
                widgets.rowBackground.setBackgroundColor(context.getResources().getColor(R.color.row_color));

            widgets.name_of_license = (TextView) rowview.findViewById(R.id.tv_reference_license);
            widgets.date_modified = (TextView) rowview.findViewById(R.id.tv_reference_date_modified);
            widgets.name_of_site = (TextView) rowview.findViewById(R.id.tv_reference_name_site);

            ReferenceModel referenceModel = referenceModels.get(position);
            widgets.name_of_license.setText(referenceModel.getName_of_license());
            widgets.date_modified.setText(referenceModel.getDate_modified());
            widgets.name_of_site.setText(referenceModel.getName_of_site());

//            rowview.setTag(widgets);
//        } else {
//            widgets = (Widgets) rowview.getTag();
//        }

        return rowview;
    }
}
