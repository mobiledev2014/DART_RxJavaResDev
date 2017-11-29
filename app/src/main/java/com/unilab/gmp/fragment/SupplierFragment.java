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
import com.unilab.gmp.adapter.SupplierAndCompanyInformationAdapter;
import com.unilab.gmp.model.ModelCompany;
import com.unilab.gmp.model.ModelCompanyInfo;
import com.unilab.gmp.model.SupplierAndCompanyInformationModel;
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

public class SupplierFragment extends Fragment {

    Unbinder unbinder;
    Context context;

    @BindView(R.id.tv_sync_date)
    TextView tvSyncDate;
    @BindView(R.id.lv_supplier_list)
    ListView lvSupplierList;
    @BindView(R.id.tv_supplier_count)
    TextView tvSupplierCount;
    @BindView(R.id.et_search_site)
    EditText etSearchSite;
    @BindView(R.id.iv_search_site)
    ImageView ivSearchSite;
    @BindView(R.id.tv_no_result)
    TextView tvNoResult;

    SupplierAndCompanyInformationModel supplierModel = new SupplierAndCompanyInformationModel();
    List<ModelCompany> supplierList;
    SharedPreferenceManager sharedPref;

    SupplierAndCompanyInformationAdapter supplierAdapter;
    ApiInterface apiInterface;
    ModelCompanyInfo modelCompanyInfo;

    boolean loopIsDone = false;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_supplier, container, false);
        ButterKnife.bind(this, rootView);
        unbinder = ButterKnife.bind(this, rootView);
        context = getActivity();

        Variable.menu = true;
        Variable.onTemplate = false;
        Variable.onReferenceData = true;
        sharedPref = new SharedPreferenceManager(context);

        supplierModel = new SupplierAndCompanyInformationModel();
        //supplierList = ModelCompany.listAll(ModelCompany.class, "createdate DESC");
        supplierList = ModelCompany.find(ModelCompany.class, "status > 0");
        Log.d("SIZE", supplierList.size() + "");

        supplierAdapter = new SupplierAndCompanyInformationAdapter(context, supplierList);
        lvSupplierList.setAdapter(supplierAdapter);
        tvSupplierCount.setText(supplierList.size() + " Total Record(s)");
        tvSyncDate.setText("Data as of: " + sharedPref.getStringData("DATE"));

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.iv_search_site)
    public void onViewClicked() {
        searchSite();
        InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(ivSearchSite.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
    }

    public void searchSite() {
        String audName = etSearchSite.getText().toString();

        if (!audName.equals("")) {
            supplierList = ModelCompany.findWithQuery(ModelCompany.class, "SELECT * from MODEL_COMPANY WHERE " +
                    "(companyname LIKE '%" + audName + "%' AND " + "status = '1')" +
                    "ORDER BY createdate DESC");
            Log.e("AuditorsCount", supplierList.size() + "");
            if (supplierList.size() > 0) {
                setSiteList();
                tvNoResult.setVisibility(View.GONE);
            } else if (supplierList.size() <= 0) {
                setSiteList();
                tvNoResult.setVisibility(View.VISIBLE);
            }
        } else {
            supplierList = ModelCompany.find(ModelCompany.class, "status = '1'");
            setSiteList();
            tvNoResult.setVisibility(View.GONE);
        }
    }

    public void setSiteList() {
        supplierAdapter = new SupplierAndCompanyInformationAdapter(context, supplierList);
        lvSupplierList.setAdapter(supplierAdapter);
        tvSupplierCount.setText(supplierList.size() + " Total Record(s)");
    }
}
