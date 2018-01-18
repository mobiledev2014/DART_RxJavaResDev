package com.unilab.gmp.fragment;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
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

        //modelAuditReports = ModelAuditReports.listAll(ModelAuditReports.class, "modifieddate DESC");
        modelAuditReports = ModelAuditReports.find(ModelAuditReports.class, "status >= '0'", new String[]{}, null, "modifieddate DESC", "50");

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
        searchTemplate();
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
                modelAuditReports = ModelAuditReports.find(ModelAuditReports.class, "status >= '0' AND companyid LIKE ?",
                        "%" + site.get(0).getCompany_id() + "" + "%");
//            modelAuditReports = ModelAuditReports.find(ModelAuditReports.class, "status > '0' AND status != '3' AND companyid LIKE ?",
//                    "%" + site.get(0).getCompany_id() + "" + "%");
            else if (auditor.size() > 0)
                modelAuditReports = ModelAuditReports.find(ModelAuditReports.class, "status >= '0' AND auditorid LIKE ?",
                        "%" + auditor.get(0).getAuditor_id() + "" + "%");
//            modelAuditReports = ModelAuditReports.find(ModelAuditReports.class, "status > '0' AND status != '3' AND auditorid LIKE ?",
//                    "%" + auditor.get(0).getAuditor_id() + "" + "%");
            else
                modelAuditReports = ModelAuditReports.find(ModelAuditReports.class, "status >= '0' AND reportno LIKE ?",
                        "%" + audName + "%");

//            modelAuditReports = ModelAuditReports.find(ModelAuditReports.class, "status > '0' AND status != '3' AND reportno LIKE ?",
//                    "%" + audName + "%");
            Log.e("AuditorsCount", modelAuditReports.size() + "");
            if (modelAuditReports.size() > 0) {
                setTemplateList();
                tvNoResult.setVisibility(View.GONE);
            } else if (modelAuditReports.size() <= 0) {
                setTemplateList();
                tvNoResult.setVisibility(View.VISIBLE);
            }
        } else {
//            modelAuditReports = ModelAuditReports.find(ModelAuditReports.class, "status > '0' AND status != '3'", new String[]{}, null, "modifieddate DESC", "50");
            modelAuditReports = ModelAuditReports.find(ModelAuditReports.class, "status >= '0'", new String[]{}, null, "modifieddate DESC", "50");
            setTemplateList();
            tvNoResult.setVisibility(View.GONE);
        }
    }

    public void setTemplateList() {
        auditReportAdapter = new AuditReportAdapter(context, modelAuditReports);
        lvAuditReportList.setAdapter(auditReportAdapter);
        tvAuditReportCount.setText(modelAuditReports.size() + " Total Record(s)");
    }
}
