package com.unilab.gmp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.unilab.gmp.R;
import com.unilab.gmp.utility.Variable;

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

    //ReferenceTable referenceTable;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_reference, container, false);
        ButterKnife.bind(this, rootView);
        unbinder = ButterKnife.bind(this, rootView);
        context = getActivity();
        //referenceTable = new ReferenceTable(context);

        Variable.menu = true;

//        ArrayList<ReferenceModel> referenceModels = new ArrayList<>();
//
//        ReferenceModel ReferenceModel = new ReferenceModel();
//        ReferenceModel.setName_of_site("Robert Baratheon");
//        ReferenceModel.setName_of_license("Sample License");
//        ReferenceModel.setDate_modified("Sample Date");
//
//        ReferenceModel ReferenceModel2 = new ReferenceModel();
//        ReferenceModel2.setName_of_site("Robert Baratheon");
//        ReferenceModel2.setName_of_license("Sample License");
//        ReferenceModel2.setDate_modified("Sample Date");
//
//        ReferenceModel ReferenceModel3 = new ReferenceModel();
//        ReferenceModel3.setName_of_site("Robert Baratheon");
//        ReferenceModel3.setName_of_license("Sample License");
//        ReferenceModel3.setDate_modified("Sample Date");
//
//        ReferenceModel ReferenceModel4 = new ReferenceModel();
//        ReferenceModel4.setName_of_site("Robert Baratheon");
//        ReferenceModel4.setName_of_license("Sample License");
//        ReferenceModel4.setDate_modified("Sample Date");
//
//        referenceModels.add(ReferenceModel);
//        referenceModels.add(ReferenceModel2);
//        referenceModels.add(ReferenceModel3);
//        referenceModels.add(ReferenceModel4);

        //ArrayList<ReferenceModel> referenceModels = referenceTable.getReferences();

        //ReferenceAdapter referenceAdapter = new ReferenceAdapter(context, referenceModels);
        //lvReferenceList.setAdapter(referenceAdapter);

        //tvAuditReportCount.setText(referenceAdapter.getCount()+" Total Record(s)");

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
