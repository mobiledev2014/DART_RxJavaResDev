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
import com.unilab.gmp.model.ApproverModel;

import java.util.List;

/**
 * Created by c_jhcanuto on 7/31/2017.
 */

public class ApproverAdapter extends BaseAdapter {
    LayoutInflater inflater;
    List<ApproverModel> approverModels;
    Context context;

    Dialog dialogViewApprover;
    ApproverModel approverModel;

    public ApproverAdapter(Context context, List<ApproverModel> approverModels) {
        this.context = context;
        this.approverModels = approverModels;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return approverModels.size();
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
        rowView = inflater.inflate(R.layout.custom_listview_approver, null);
        widgets.rowBackground = (LinearLayout) rowView.findViewById(R.id.row_background);
        if (position % 2 == 0)
            widgets.rowBackground.setBackgroundColor(context.getResources().getColor(R.color.white));
        else
            widgets.rowBackground.setBackgroundColor(context.getResources().getColor(R.color.row_color));

        widgets.name = (TextView) rowView.findViewById(R.id.tv_approver_name);
        widgets.date_modified = (TextView) rowView.findViewById(R.id.tv_date_modified);
        widgets.viewInfo = (Button) rowView.findViewById(R.id.btn_view_info);

        approverModel = approverModels.get(position);
        final String firstname = approverModel.getFirstname();
        final String middlename = approverModel.getMiddlename();
        final String lastname = approverModel.getLastname();
        final String designation = approverModel.getDesignation();
        final String company = approverModel.getCompany();
        final String department = approverModel.getDepartment();
        final String email = approverModel.getEmail();

        widgets.name.setText(firstname + " " + middlename + " " +
                lastname);
        widgets.date_modified.setText(approverModel.getUpdate_date());

        widgets.viewInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogViewApprover(firstname + " " + middlename + " " + lastname,
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
}
