package com.unilab.gmp.fragment;

import android.content.Context;
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
import com.unilab.gmp.adapter.AuditorsAdapter;
import com.unilab.gmp.model.AuditorsModel;
import com.unilab.gmp.model.ModelAuditorInfo;
import com.unilab.gmp.retrofit.ApiInterface;
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

public class AuditorsFragment extends Fragment {

    Unbinder unbinder;
    Context context;

    @BindView(R.id.tv_sync_date)
    TextView tvSyncDate;
    @BindView(R.id.lv_auditor_list)
    ListView lvAuditList;
    @BindView(R.id.tv_auditor_count)
    TextView tvAuditCount;
    @BindView(R.id.et_search_auditor)
    EditText etSearchAuditor;
    @BindView(R.id.iv_search)
    ImageView ivSearch;
    @BindView(R.id.tv_no_result)
    TextView tvNoResult;

    List<AuditorsModel> auditorsList;
    List<AuditorsModel> sortList;
    SharedPreferenceManager sharedPref;

    AuditorsAdapter auditorsAdapter;
    ApiInterface apiInterface;
    ModelAuditorInfo modelAuditorInfo;
    boolean loopIsDone = false;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_auditors, container, false);
        ButterKnife.bind(this, rootView);
        unbinder = ButterKnife.bind(this, rootView);
        context = getActivity();

        Variable.menu = true;
        Variable.onTemplate = false;
        Variable.onReferenceData = true;
        sharedPref = new SharedPreferenceManager(context);

        //auditorsList = AuditorsModel.listAll(AuditorsModel.class);
        //auditorsList = AuditorsModel.listAll(AuditorsModel.class, "createdate DESC");
        auditorsList = AuditorsModel.find(AuditorsModel.class, "status = '1'", new String[]{}, null, "updatedate DESC", "100");
        Log.d("SIZE", auditorsList.size() + "");
        auditorsAdapter = new AuditorsAdapter(context, auditorsList);
        lvAuditList.setAdapter(auditorsAdapter);
        tvAuditCount.setText(auditorsList.size() + " Total Record(s)");
        tvSyncDate.setText("Data as of: " + sharedPref.getStringData("DATE"));

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.iv_search)
    public void onViewClicked() {
        searchAuditor();
        InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(ivSearch.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
    }

    public void searchAuditor() {
        String audName = etSearchAuditor.getText().toString();

        if (!audName.equals("")) {
            auditorsList = AuditorsModel.findWithQuery(AuditorsModel.class, "SELECT * from AUDITORS_MODEL WHERE " +
                    "(status = '1' AND fname LIKE '%" + audName + "%') OR " +
                    "(status = '1' AND mname LIKE '%" + audName + "%') OR " +
                    "(status = '1' AND lname LIKE '%" + audName + "%') " +
                    "ORDER BY createdate DESC");
            Log.e("AuditorsCount", auditorsList.size() + "");
            if (auditorsList.size() > 0) {
                setAuditorList();
                tvNoResult.setVisibility(View.GONE);
            } else if (auditorsList.size() <= 0) {
                setAuditorList();
                tvNoResult.setVisibility(View.VISIBLE);
            }
        } else {
            auditorsList = AuditorsModel.find(AuditorsModel.class, "status = '1'");
            setAuditorList();
            tvNoResult.setVisibility(View.GONE);
        }
    }

    public void setAuditorList() {
        auditorsAdapter = new AuditorsAdapter(context, auditorsList);
        lvAuditList.setAdapter(auditorsAdapter);
        tvAuditCount.setText(auditorsList.size() + " Total Record(s)");
    }
}
