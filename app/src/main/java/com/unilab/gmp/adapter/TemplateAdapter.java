package com.unilab.gmp.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.unilab.gmp.R;
import com.unilab.gmp.fragment.SelectedTemplateFragment;
import com.unilab.gmp.model.ModelTemplates;

import java.util.List;

/**
 * Created by c_jhcanuto on 7/31/2017.
 */

public class TemplateAdapter extends BaseAdapter {
    LayoutInflater inflater;
    List<ModelTemplates> modelTemplates;
    Context context;

    Dialog dialogViewApprover;
    ModelTemplates modelTemplate;


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
                    FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
                    fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    fragmentManager.beginTransaction()
                            .replace(R.id.fl_content, new SelectedTemplateFragment(template)).addToBackStack(null).commit();
                    //Toast.makeText(context, "Use this template", Toast.LENGTH_SHORT).show();
                    template.setStatus("2");
                    template.save();
                }
            });

            if (modelTemplate.getStatus().equals("1")) {
                //widgets.rowBackground.setBackgroundColor(context.getResources().getColor(R.color.template_new));
                widgets.product_type.setTypeface(Typeface.DEFAULT_BOLD);
                widgets.stand_ref.setTypeface(Typeface.DEFAULT_BOLD);
                widgets.date_modified.setTypeface(Typeface.DEFAULT_BOLD);
            }

        return rowView;
    }

    public void dialogViewApprover(String Name, String Designation, String Company, String Department
            , String Email) {
        dialogViewApprover = new Dialog(context);
        dialogViewApprover.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialogViewApprover.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogViewApprover.setCancelable(false);
        dialogViewApprover.setContentView(R.layout.dialog_approver_view);
        dialogViewApprover.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        TextView name = (TextView) dialogViewApprover.findViewById(R.id.tv_name);
        TextView designation = (TextView) dialogViewApprover.findViewById(R.id.tv_designation);
        TextView company = (TextView) dialogViewApprover.findViewById(R.id.tv_company);
        TextView department = (TextView) dialogViewApprover.findViewById(R.id.tv_department);
        TextView email = (TextView) dialogViewApprover.findViewById(R.id.tv_email);
        Button done = (Button) dialogViewApprover.findViewById(R.id.btn_done);

        name.setText("Name: " + Name);
        designation.setText("Designation: " + Designation);
        company.setText("Company: " + Company);
        department.setText("Department: " + Department);
        email.setText("Email: " + Email);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogViewApprover.dismiss();
            }
        });

        dialogViewApprover.show();
    }

    public class Widgets {
        TextView product_type, stand_ref, date_modified;
        LinearLayout rowBackground;
        Button useTemplate;
    }
}
