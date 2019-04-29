package com.unilab.gmp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.unilab.gmp.R;
import com.unilab.gmp.utility.Variable;

import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by c_rcmiguel on 7/14/2017.
 */

public class ReferenceFragment extends Fragment {
    Unbinder unbinder;
    Context context;
    @BindView(R.id.tv_sync_date)
    TextView tvSyncDate;
    @BindView(R.id.s_search_auditor)
    Spinner sSearchAuditor;
    @BindView(R.id.lv_reference_list)
    ListView lvReferenceList;
    @BindView(R.id.tv_supplier_count)
    TextView tvAuditReportCount;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_reference, container, false);
        ButterKnife.bind(this, rootView);
        unbinder = ButterKnife.bind(this, rootView);
        context = getActivity();
        Variable.menu = true;
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
