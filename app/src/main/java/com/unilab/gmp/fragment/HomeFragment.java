package com.unilab.gmp.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.unilab.gmp.R;
import com.unilab.gmp.utility.Variable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by c_rcmiguel on 7/14/2017.
 */

public class HomeFragment extends Fragment {

    Unbinder unbinder;

    @BindView(R.id.iv_template)
    ImageView ivTemplate;
    @BindView(R.id.iv_report)
    ImageView ivReport;
    @BindView(R.id.iv_supplier)
    ImageView ivSupplier;
    @BindView(R.id.iv_auditor)
    ImageView ivAuditor;
    @BindView(R.id.iv_reviewer)
    ImageView ivReviewer;
    @BindView(R.id.iv_approver)
    ImageView ivApprover;

    TemplateFragment templateFragment;
    AuditReportFragment auditReportFragment;
    AuditorsFragment auditorsFragment;
    SupplierFragment supplierFragment;
    ReviewerFragment reviewerFragment;
    ApproverFragment approverFragment;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        ButterKnife.bind(this, rootView);
        unbinder = ButterKnife.bind(this, rootView);

        fragmentSetter();

        Variable.menu = false;

        return rootView;
    }

    private void fragmentSetter() {
        templateFragment = new TemplateFragment();
        auditReportFragment = new AuditReportFragment();
        auditorsFragment = new AuditorsFragment();
        supplierFragment = new SupplierFragment();
        reviewerFragment = new ReviewerFragment();
        approverFragment = new ApproverFragment();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.iv_template, R.id.iv_report, R.id.iv_supplier, R.id.iv_auditor, R.id.iv_reviewer, R.id.iv_approver})
    public void onViewClicked(View view) {
        FragmentManager fragmentManager = getFragmentManager();
        switch (view.getId()) {
            case R.id.iv_template:
                fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                fragmentManager.beginTransaction()
                        .replace(R.id.fl_content, templateFragment).addToBackStack(null).commit();
                break;
            case R.id.iv_report:
                fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                fragmentManager.beginTransaction()
                        .replace(R.id.fl_content, auditReportFragment).addToBackStack(null).commit();
                break;
            case R.id.iv_supplier:
                fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                fragmentManager.beginTransaction()
                        .replace(R.id.fl_content, supplierFragment).addToBackStack(null).commit();
                break;
            case R.id.iv_auditor:
                fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                fragmentManager.beginTransaction()
                        .replace(R.id.fl_content, auditorsFragment).addToBackStack(null).commit();
                break;
            case R.id.iv_reviewer:
                fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                fragmentManager.beginTransaction()
                        .replace(R.id.fl_content, reviewerFragment).addToBackStack(null).commit();
                break;
            case R.id.iv_approver:
                fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                fragmentManager.beginTransaction()
                        .replace(R.id.fl_content, approverFragment).addToBackStack(null).commit();
                break;
        }
    }
}
