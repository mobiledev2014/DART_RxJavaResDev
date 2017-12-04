package com.unilab.gmp.fragment;

import android.app.ProgressDialog;
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
import com.unilab.gmp.adapter.TemplateAdapter;
import com.unilab.gmp.model.ModelTemplates;
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

public class TemplateFragment extends Fragment {

    Unbinder unbinder;
    Context context;
    ProgressDialog pDialog;

    SelectedTemplateFragment selectedTemplateFragment;
    SharedPreferenceManager sharedPref;

    @BindView(R.id.tv_sync_date)
    TextView tvSyncDate;
    @BindView(R.id.lv_template_list)
    ListView lvTemplateList;
    @BindView(R.id.tv_template_count)
    TextView tvTemplateCount;
    @BindView(R.id.et_search_template)
    EditText etSearchTemplate;
    @BindView(R.id.iv_search)
    ImageView ivSearch;
    @BindView(R.id.tv_no_result)
    TextView tvNoResult;

    List<ModelTemplates> templateList;

    TemplateAdapter templateAdapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_template, container, false);
        ButterKnife.bind(this, rootView);
        unbinder = ButterKnife.bind(this, rootView);
        context = getActivity();

        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);

        Variable.menu = true;
        Variable.onTemplate = false;
        Variable.onAudit = false;
        sharedPref = new SharedPreferenceManager(context);

        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Loading please wait...");
        pDialog.setCancelable(false);

//        templateList = ModelTemplates.listAll(ModelTemplates.class, "date_Created DESC");
        templateList = ModelTemplates.find(ModelTemplates.class, "status = '1' OR status = '2' ",
                new String[]{}, null, "date_Created DESC", "50");
        templateAdapter = new TemplateAdapter(context, templateList);
        lvTemplateList.setAdapter(templateAdapter);
        tvTemplateCount.setText(templateList.size() + " Total Record(s)");
        tvSyncDate.setText("Data as of: " + sharedPref.getStringData("DATE"));
        for(ModelTemplates m : templateList) {
            Log.e("status", m.getStatus());
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
        searchTemplate();
        InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(ivSearch.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
    }

    public void searchTemplate() {
        String audName = etSearchTemplate.getText().toString();

        if (!audName.equals("")) {
            templateList = ModelTemplates.findWithQuery(ModelTemplates.class, "SELECT * from MODEL_TEMPLATES WHERE " +
                    "(product_Type LIKE '%" + audName + "%' OR " +
                    "template_Name LIKE '%" + audName + "%' OR " +
                    "date_Updated LIKE '%" + audName + "%') AND " +
                    "(status = '1' OR status = '2') " +
                    "ORDER BY date_Created DESC");
            Log.e("AuditorsCount", templateList.size() + "");
            if (templateList.size() > 0) {
                setTemplateList();
                tvNoResult.setVisibility(View.GONE);
            } else if (templateList.size() <= 0) {
                setTemplateList();
                tvNoResult.setVisibility(View.VISIBLE);
            }
            pDialog.dismiss();
        } else {
            templateList = ModelTemplates.find(ModelTemplates.class, "status = '1' OR status = '2' ",
                    new String[]{}, null, "date_Created DESC", "50");
            setTemplateList();
            tvNoResult.setVisibility(View.GONE);
            pDialog.dismiss();
        }
    }

    public void setTemplateList() {
        templateAdapter = new TemplateAdapter(context, templateList);
        lvTemplateList.setAdapter(templateAdapter);
        tvTemplateCount.setText(templateList.size() + " Total Record(s)");
    }

}
