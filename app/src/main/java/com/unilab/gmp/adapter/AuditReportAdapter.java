package com.unilab.gmp.adapter;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.unilab.gmp.R;
import com.unilab.gmp.fragment.SelectedAuditReportFragment;
import com.unilab.gmp.model.AuditorsModel;
import com.unilab.gmp.model.ModelAuditReports;
import com.unilab.gmp.model.ModelCompany;
import com.unilab.gmp.utility.Utils;

import java.util.List;

/**
 * Created by c_jhcanuto on 8/29/2017.
 */

public class AuditReportAdapter extends BaseAdapter {
    Context context;
    LayoutInflater inflater;
    List<ModelAuditReports> modelAuditReports;

    public AuditReportAdapter(Context context, List<ModelAuditReports> modelAuditReports) {
        this.context = context;
        this.modelAuditReports = modelAuditReports;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return modelAuditReports.size();
    }

    @Override
    public Object getItem(int i) {
        return modelAuditReports.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View rowView, ViewGroup viewGroup) {
        Widgets widgets;
        widgets = new Widgets();

        rowView = inflater.inflate(R.layout.custom_listview_audit_report, null);
        widgets.tv_ur_no = (TextView) rowView.findViewById(R.id.tv_ur_num);
        widgets.tv_audited_site = (TextView) rowView.findViewById(R.id.tv_audited_site);
        widgets.tv_auditors = (TextView) rowView.findViewById(R.id.tv_auditor);
        widgets.tv_date_modified = (TextView) rowView.findViewById(R.id.tv_date_modified);
        widgets.rowBackground = (LinearLayout) rowView.findViewById(R.id.row_background);
        widgets.viewInfo = (Button) rowView.findViewById(R.id.btn_view_info);

        widgets.rowBackground = (LinearLayout) rowView.findViewById(R.id.row_background);
        if (i % 2 == 0)
            widgets.rowBackground.setBackgroundColor(context.getResources().getColor(R.color.white));
        else
            widgets.rowBackground.setBackgroundColor(context.getResources().getColor(R.color.row_color));

        List<ModelCompany> modelCompany = ModelCompany.find(ModelCompany.class, "companyid = ?", modelAuditReports.get(i).getCompany_id());
        List<AuditorsModel> auditorsModels = AuditorsModel.find(AuditorsModel.class, "auditorid = ?", modelAuditReports.get(i).getAuditor_id());

        widgets.tv_ur_no.setText(modelAuditReports.get(i).getReport_no());
        if (modelCompany.size() > 0)
            widgets.tv_audited_site.setText(modelCompany.get(0).getCompany_name());
        else
            widgets.tv_audited_site.setText("");
        if (auditorsModels.size() > 0)
            widgets.tv_auditors.setText(auditorsModels.get(0).getFname() + " " + auditorsModels.get(0).getMname() + " " + auditorsModels.get(0).getLname());
        else
            widgets.tv_auditors.setText("");
        widgets.tv_date_modified.setText(modelAuditReports.get(i).getModified_date());
        final int z = i;

        widgets.viewInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (modelAuditReports.get(i).getStatus().equals("1")) {
                    Utils.pdfIfExist(modelAuditReports.get(i).getReport_id(), context);
                } else {
                    //Toast.makeText(context, "clicked", Toast.LENGTH_SHORT).show();
                    FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
                    fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    fragmentManager.beginTransaction()
                            .replace(R.id.fl_content, new SelectedAuditReportFragment(modelAuditReports.get(z))).addToBackStack(null).commit();
                }
            }
        });


        return rowView;
    }

    public class Widgets {
        TextView tv_ur_no, tv_audited_site, tv_auditors, tv_date_modified;
        LinearLayout rowBackground;
        Button viewInfo;
    }
}
