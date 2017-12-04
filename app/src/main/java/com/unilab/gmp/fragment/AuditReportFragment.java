package com.unilab.gmp.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.unilab.gmp.R;
import com.unilab.gmp.adapter.AuditReportAdapter;
import com.unilab.gmp.model.AuditorsModel;
import com.unilab.gmp.model.ModelAuditReports;
import com.unilab.gmp.model.ModelCompany;
import com.unilab.gmp.utility.SharedPreferenceManager;
import com.unilab.gmp.utility.Variable;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by c_rcmiguel on 7/14/2017.
 */

public class AuditReportFragment extends Fragment {

    Unbinder unbinder;
    Context context;
    ProgressDialog pDialog;
    Handler handler;

    List<ModelAuditReports> modelAuditReports;

    @BindView(R.id.tv_sync_date)
    TextView tvSyncDate;
    @BindView(R.id.lv_audit_report_list)
    ListView lvAuditReportList;
    @BindView(R.id.tv_audit_report_count)
    TextView tvAuditReportCount;
    @BindView(R.id.et_search_template)
    EditText etSearchTemplate;
    @BindView(R.id.iv_search)
    ImageView ivSearch;
    @BindView(R.id.tv_no_result)
    TextView tvNoResult;

    SharedPreferenceManager sharedPref;
    AuditReportAdapter auditReportAdapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_audit_report, container, false);
        ButterKnife.bind(this, rootView);
        unbinder = ButterKnife.bind(this, rootView);
        context = getActivity();

        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);

        Variable.menu = true;
        Variable.onTemplate = false;
        sharedPref = new SharedPreferenceManager(context);

        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Loading please wait...");
        pDialog.setCancelable(false);
        handler = new Handler();

        //modelAuditReports = ModelAuditReports.listAll(ModelAuditReports.class, "modifieddate DESC");
        modelAuditReports = ModelAuditReports.find(ModelAuditReports.class, "status >= '0'",
                new String[]{}, null, "modifieddate DESC", "50");

        auditReportAdapter = new AuditReportAdapter(context, modelAuditReports);
        lvAuditReportList.setAdapter(auditReportAdapter);
        tvAuditReportCount.setText(modelAuditReports.size() + " Total Record(s)");
        tvSyncDate.setText("Data as of: " + sharedPref.getStringData("DATE"));

        for(ModelAuditReports mar : modelAuditReports){
            Log.e("Tset", mar.getReport_id() + " --- " + mar.getCompany_id());
        }

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.iv_search)
    public void onViewClicked() {
        pDialog.show();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                searchTemplate();
            }
        }, 700);

        InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(ivSearch.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
    }

    public void searchTemplate() {
        String audName = etSearchTemplate.getText().toString();

        List<ModelCompany> site = ModelCompany.find(ModelCompany.class, "companyname LIKE ?", "%"+audName+"%");
        List<AuditorsModel> auditor = AuditorsModel.find(AuditorsModel.class, "fname LIKE ? OR mname LIKE ? OR lname LIKE ?",
                "%" + audName + "%", "%" + audName + "%", "%" + audName + "%");

        if (!audName.equals("")) {
            if (site.size() > 0)
                modelAuditReports = ModelAuditReports.find(ModelAuditReports.class, "companyid LIKE ?",
                        "%" + site.get(0).getCompany_id() + "" + "%", "modifieddate DESC", "50");
            else if (auditor.size() > 0)
                modelAuditReports = ModelAuditReports.find(ModelAuditReports.class, "auditorid LIKE ?",
                        "%" + auditor.get(0).getAuditor_id() + "" + "%", "modifieddate DESC", "50");
            else
                modelAuditReports = ModelAuditReports.find(ModelAuditReports.class, "reportno LIKE ?",
                        "%" + audName + "%", "modifieddate DESC", "50");
            Log.e("AuditorsCount", modelAuditReports.size() + "");
            if (modelAuditReports.size() > 0) {
                setTemplateList();
                tvNoResult.setVisibility(View.GONE);
            } else if (modelAuditReports.size() <= 0) {
                setTemplateList();
                tvNoResult.setVisibility(View.VISIBLE);
            }
        } else {
//            modelAuditReports = ModelAuditReports.listAll(ModelAuditReports.class, "templateid DESC");
            modelAuditReports =  ModelAuditReports.find(ModelAuditReports.class, "status >= '0'",
                    new String[]{}, null, "modifieddate DESC", "50");
            setTemplateList();
            tvNoResult.setVisibility(View.GONE);
        }
    }

    public void setTemplateList() {
        auditReportAdapter = new AuditReportAdapter(context, modelAuditReports);
        lvAuditReportList.setAdapter(auditReportAdapter);
        tvAuditReportCount.setText(modelAuditReports.size() + " Total Record(s)");

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                pDialog.dismiss();
            }
        }, 1000);
    }
}
