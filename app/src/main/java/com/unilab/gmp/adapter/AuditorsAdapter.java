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

import com.unilab.gmp.R;
import com.unilab.gmp.model.AuditorsModel;

import java.util.List;

/**
 * Created by c_jhcanuto on 7/31/2017.
 */

public class AuditorsAdapter extends BaseAdapter {
    LayoutInflater inflater;
    List<AuditorsModel> auditorsModels;
    Context context;
    AuditorsModel auditorsModel;

    Dialog dialogViewAuditor;

    public AuditorsAdapter(Context context, List<AuditorsModel> auditorsModels) {
        this.context = context;
        this.auditorsModels = auditorsModels;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return auditorsModels.size();
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
        rowView = inflater.inflate(R.layout.custom_listview_auditors, null);

        widgets.rowBackground = (LinearLayout) rowView.findViewById(R.id.row_background);
        if (position % 2 == 0)
            widgets.rowBackground.setBackgroundColor(context.getResources().getColor(R.color.white));
        else
            widgets.rowBackground.setBackgroundColor(context.getResources().getColor(R.color.row_color));

        widgets.name = (TextView) rowView.findViewById(R.id.tv_auditors_name);
        widgets.date_modified = (TextView) rowView.findViewById(R.id.tv_date_modified);
        widgets.viewInfo = (Button) rowView.findViewById(R.id.btn_view_info);

        auditorsModel = auditorsModels.get(position);
        final String fname = auditorsModel.getFname();
        final String mname = auditorsModel.getMname();
        final String lname = auditorsModel.getLname();
        final String designation = auditorsModel.getDesignation();
        final String company = auditorsModel.getCompany();
        final String department = auditorsModel.getDepartment();
        final String email = auditorsModel.getEmail();

        widgets.name.setText(fname + " " + mname + " " + lname);

        widgets.date_modified.setText(auditorsModel.getUpdate_date());

        widgets.viewInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogViewAuditor(fname + " " + mname + " " + lname,
                        designation, company, department,
                        email);
            }
        });
        return rowView;
    }

    public class Widgets {
        TextView name, date_modified;
        LinearLayout rowBackground;
        Button viewInfo;
    }

    public void dialogViewAuditor(String Name, String Designation, String Company, String Department
            , String Email) {
        dialogViewAuditor = new Dialog(context);
        dialogViewAuditor.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialogViewAuditor.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogViewAuditor.setCancelable(false);
        dialogViewAuditor.setContentView(R.layout.dialog_auditor_view);
        dialogViewAuditor.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        TextView name = (TextView) dialogViewAuditor.findViewById(R.id.tv_name);
        TextView designation = (TextView) dialogViewAuditor.findViewById(R.id.tv_designation);
        TextView company = (TextView) dialogViewAuditor.findViewById(R.id.tv_company);
        TextView department = (TextView) dialogViewAuditor.findViewById(R.id.tv_department);
        TextView email = (TextView) dialogViewAuditor.findViewById(R.id.tv_email);
        Button done = (Button) dialogViewAuditor.findViewById(R.id.btn_done);

        name.setText("Name: " + Name);
        designation.setText("Designation: " + Designation);
        company.setText("Company: " + Company);
        department.setText("Department: " + Department);
        email.setText("Email: " + Email);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogViewAuditor.dismiss();
            }
        });

        dialogViewAuditor.show();
    }
}
