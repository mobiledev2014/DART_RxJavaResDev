package com.unilab.gmp.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.unilab.gmp.R;
import com.unilab.gmp.fragment.SelectedTemplateFragment;
import com.unilab.gmp.model.ModelTemplates;
import com.unilab.gmp.utility.ProgressDialogUtils;
import com.unilab.gmp.utility.Variable;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

/**
 * Created by c_jhcanuto on 7/31/2017.
 */

public class TemplateAdapter extends BaseAdapter {
    LayoutInflater inflater;
    List<ModelTemplates> modelTemplates;
    Context context;

    Dialog dialogViewApprover;
    ModelTemplates modelTemplate;

    private static final String TAG = "TemplateAdapter";

    public TemplateAdapter(Context context, List<ModelTemplates> modelTemplates) {
        this.context = context;
        this.modelTemplates = modelTemplates;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return modelTemplates.size();
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
        widgets = new Widgets();

        rowView = inflater.inflate(R.layout.custom_listview_template, null);

        widgets.rowBackground = (LinearLayout) rowView.findViewById(R.id.ll_row_view);
        if (position % 2 == 0)
            widgets.rowBackground.setBackgroundColor(context.getResources().getColor(R.color.white));
        else
            widgets.rowBackground.setBackgroundColor(context.getResources().getColor(R.color.row_color));

        widgets.product_type = (TextView) rowView.findViewById(R.id.tv_template_type);
        widgets.stand_ref = (TextView) rowView.findViewById(R.id.tv_template_stand);
        widgets.date_modified = (TextView) rowView.findViewById(R.id.tv_template_date_modified);
        widgets.useTemplate = (Button) rowView.findViewById(R.id.btn_use_template);

        modelTemplate = modelTemplates.get(position);
        widgets.product_type.setText(modelTemplate.getProductType());
        widgets.stand_ref.setText(modelTemplate.getTemplateName());
        widgets.date_modified.setText(modelTemplate.getDateUpdated());

        final ModelTemplates template = modelTemplate;

        widgets.useTemplate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProgressDialogUtils.showSimpleProgressDialog(context, 50, "Loading . . .", false);
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Variable.selectedProduct.clear();
                        Variable.selectedDisposition.clear();
                        Variable.elementSelect.clear();
                        Variable.report_id = "0";

                        FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
                        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        fragmentManager.beginTransaction()
                                .replace(R.id.fl_content, new SelectedTemplateFragment(template)).addToBackStack(null).commit();
                        template.setStatus("2");
                        template.save();
                        ProgressDialogUtils.removeSimpleProgressDialog();
                    }
                }, 700);
            }
        });

        if (modelTemplate.getStatus().equals("1")) {
            widgets.product_type.setTypeface(Typeface.DEFAULT_BOLD);
            widgets.stand_ref.setTypeface(Typeface.DEFAULT_BOLD);
            widgets.date_modified.setTypeface(Typeface.DEFAULT_BOLD);
        }

        return rowView;
    }

    public class Widgets {
        TextView product_type, stand_ref, date_modified;
        LinearLayout rowBackground;
        Button useTemplate;
    }
}
