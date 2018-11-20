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
import com.unilab.gmp.adapter.ApproverAdapter;
import com.unilab.gmp.model.ApproverModel;
import com.unilab.gmp.model.ModelApproverInfo;
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

public class ApproverFragment extends Fragment {

    Unbinder unbinder;
    Context context;

    @BindView(R.id.tv_sync_date)
    TextView tvSyncDate;
    @BindView(R.id.lv_approver_list)
    ListView lvApproverList;
    @BindView(R.id.tv_approver_count)
    TextView tvApproverCount;
    @BindView(R.id.et_search_approver)
    EditText etSearchApprover;
    @BindView(R.id.iv_search)
    ImageView ivSearch;
    @BindView(R.id.tv_no_result)
    TextView tvNoResult;

    boolean loopIsDone = false;

    ApproverModel approverModel = new ApproverModel();
    List<ApproverModel> approverList;
    SharedPreferenceManager sharedPref;

    ApproverAdapter approverAdapter;
    ModelApproverInfo modelApproverInfo;
    ApiInterface apiInterface;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_approvers, container, false);
        ButterKnife.bind(this, rootView);
        unbinder = ButterKnife.bind(this, rootView);
        context = getActivity();

        Variable.menu = true;
        Variable.onTemplate = false;
        Variable.onReferenceData = true;
        sharedPref = new SharedPreferenceManager(context);

        approverModel = new ApproverModel();
        approverList = ApproverModel.find(ApproverModel.class, "status = '1'", new String[]{}, null, "updatedate DESC", "100");

        for(ApproverModel i: approverList){

            Log.e("ApproverFragment",i.getFirstname() + " " + i.getLastname());
        }


        Log.d("SIZE", approverList.size() + "");
        approverAdapter = new ApproverAdapter(context, approverList);
        lvApproverList.setAdapter(approverAdapter);
        tvApproverCount.setText(approverList.size() + " Total Record(s)");
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
        searchApprover();
        InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(ivSearch.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
    }

    public void searchApprover() {
        String audName = etSearchApprover.getText().toString();

        if (!audName.equals("")) {
            approverList = ApproverModel.findWithQuery(ApproverModel.class, "SELECT * from APPROVER_MODEL WHERE " +
                    "(status = '1' AND firstname LIKE '%" + audName + "%') OR " +
                    "(status = '1' AND middlename LIKE '%" + audName + "%') OR " +
                    "(status = '1' AND lastname LIKE '%" + audName + "%') "  +
                    "ORDER BY createdate DESC");
            Log.e("AuditorsCount", approverList.size() + "");
            if (approverList.size() > 0) {
                setApproverList();
                tvNoResult.setVisibility(View.GONE);
            } else if (approverList.size() <= 0) {
                setApproverList();
                tvNoResult.setVisibility(View.VISIBLE);
            }
        } else {
            approverList = ApproverModel.find(ApproverModel.class, "status = '1'");
            setApproverList();
            tvNoResult.setVisibility(View.GONE);
        }
    }

    public void setApproverList() {
        approverAdapter = new ApproverAdapter(context, approverList);
        lvApproverList.setAdapter(approverAdapter);
        tvApproverCount.setText(approverList.size() + " Total Record(s)");
    }
}
