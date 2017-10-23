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
import com.unilab.gmp.model.ReviewerModel;

import java.util.List;

/**
 * Created by c_jhcanuto on 7/31/2017.
 */

public class ReviewerAdapter extends BaseAdapter {
    LayoutInflater inflater;
    List<ReviewerModel> reviewerModels;
    Context context;

    Dialog dialogViewReviewer;
    ReviewerModel reviewerModel;

    public ReviewerAdapter(Context context, List<ReviewerModel> reviewerModels) {
        this.context = context;
        this.reviewerModels = reviewerModels;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return reviewerModels.size();
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

            rowView = inflater.inflate(R.layout.custom_listview_reviewer, null);

            widgets.rowBackground = (LinearLayout) rowView.findViewById(R.id.row_background);
            if (position % 2 == 0)
                widgets.rowBackground.setBackgroundColor(context.getResources().getColor(R.color.white));
            else
                widgets.rowBackground.setBackgroundColor(context.getResources().getColor(R.color.row_color));

            widgets.name = (TextView) rowView.findViewById(R.id.tv_reviewer_name);
            widgets.designation = (TextView) rowView.findViewById(R.id.tv_reviewer_designation);
            widgets.viewInfo = (Button) rowView.findViewById(R.id.btn_view_info);

            reviewerModel = reviewerModels.get(position);
            final String firstname = reviewerModel.getFirstname();
            final String middlename = reviewerModel.getMiddlename();
            final String lastname = reviewerModel.getLastname();
            final String designation = reviewerModel.getDesignation();
            final String company = reviewerModel.getCompany();
            final String department = reviewerModel.getDepartment();
            final String email = reviewerModel.getEmail();

            widgets.name.setText(firstname + " " + middlename + " " + lastname);
            widgets.designation.setText(designation);

            widgets.viewInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialogViewReviewer(firstname + " " + middlename + " " + lastname,
                            designation, company, department, email);
                }
            });
            rowView.setTag(widgets);
        } else {
            widgets = (Widgets) rowView.getTag();
        }
        return rowView;
    }

    public class Widgets {
        TextView name, designation;
        LinearLayout rowBackground;
        Button viewInfo;
    }

    public void dialogViewReviewer(String Name, String Designation, String Company, String Department
            , String Email) {
        dialogViewReviewer = new Dialog(context);
        dialogViewReviewer.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialogViewReviewer.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogViewReviewer.setCancelable(false);
        dialogViewReviewer.setContentView(R.layout.dialog_approver_view);
        dialogViewReviewer.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        TextView name = (TextView) dialogViewReviewer.findViewById(R.id.tv_name);
        TextView designation = (TextView) dialogViewReviewer.findViewById(R.id.tv_designation);
        TextView company = (TextView) dialogViewReviewer.findViewById(R.id.tv_company);
        TextView department = (TextView) dialogViewReviewer.findViewById(R.id.tv_department);
        TextView email = (TextView) dialogViewReviewer.findViewById(R.id.tv_email);
        Button done = (Button) dialogViewReviewer.findViewById(R.id.btn_done);

        name.setText("Name: " + Name);
        designation.setText("Designation: " + Designation);
        company.setText("Company: " + Company);
        department.setText("Department: " + Department);
        email.setText("Email: " + Email);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogViewReviewer.dismiss();
            }
        });

        dialogViewReviewer.show();
    }
}
