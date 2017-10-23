package com.unilab.gmp.fragment;

import android.content.Context;
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

public class ReferenceDataFragment extends Fragment {

    Unbinder unbinder;
    Context context;

    @BindView(R.id.iv_auditor)
    ImageView ivAuditor;
    @BindView(R.id.iv_reviewer)
    ImageView ivReviewer;
    @BindView(R.id.iv_approver)
    ImageView ivApprover;
    @BindView(R.id.iv_supplier)
    ImageView ivSupplier;

    AuditorsFragment auditorsFragment;
    ReviewerFragment reviewerFragment;
    ApproverFragment approverFragment;
    SupplierFragment supplierFragment;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_reference_data, container, false);
        ButterKnife.bind(this, rootView);
        unbinder = ButterKnife.bind(this, rootView);
        context = getActivity();

        Variable.menu = true;
        Variable.onTemplate = false;
        Variable.onReferenceData = false;
        setFragment();

        return rootView;
    }

    private void setFragment() {
        auditorsFragment = new AuditorsFragment();
        reviewerFragment = new ReviewerFragment();
        approverFragment = new ApproverFragment();
        supplierFragment = new SupplierFragment();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.iv_auditor, R.id.iv_reviewer, R.id.iv_approver, R.id.iv_supplier})
    public void onViewClicked(View view) {
        FragmentManager fragmentManager = getFragmentManager();
        switch (view.getId()) {
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
            case R.id.iv_supplier:
                fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                fragmentManager.beginTransaction()
                        .replace(R.id.fl_content, supplierFragment).addToBackStack(null).commit();
                break;
        }
    }
}
