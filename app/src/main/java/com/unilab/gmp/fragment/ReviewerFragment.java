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
import com.unilab.gmp.adapter.ReviewerAdapter;
import com.unilab.gmp.model.ModelReviewerInfo;
import com.unilab.gmp.model.ReviewerModel;
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

public class ReviewerFragment extends Fragment {

    Unbinder unbinder;
    Context context;

    @BindView(R.id.tv_sync_date)
    TextView tvSyncDate;
    @BindView(R.id.lv_reviewer_list)
    ListView lvReviewerList;
    @BindView(R.id.tv_reviewer_count)
    TextView tvReviewerCount;
    @BindView(R.id.et_search_reviewer)
    EditText etSearchReviewer;
    @BindView(R.id.iv_search_reviewer)
    ImageView ivSearchReviewer;
    @BindView(R.id.tv_no_result)
    TextView tvNoResult;

    ReviewerModel reviewerModel = new ReviewerModel();
    List<ReviewerModel> reviewerList;
    SharedPreferenceManager sharedPref;

    ReviewerAdapter reviewerAdapter;
    ModelReviewerInfo modelReviewerInfo;
    ApiInterface apiInterface;
    boolean loopIsDone = false;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_reviewers, container, false);
        ButterKnife.bind(this, rootView);
        unbinder = ButterKnife.bind(this, rootView);
        context = getActivity();

        Variable.menu = true;
        Variable.onTemplate = false;
        Variable.onReferenceData = true;
        sharedPref = new SharedPreferenceManager(context);

        reviewerModel = new ReviewerModel();
        reviewerList = ReviewerModel.listAll(ReviewerModel.class, "createdate DESC");
        Log.d("SIZE", reviewerList.size() + "");
        reviewerAdapter = new ReviewerAdapter(context, reviewerList);
        lvReviewerList.setAdapter(reviewerAdapter);
        tvReviewerCount.setText(reviewerList.size() + " Total Record(s)");
        tvSyncDate.setText("Data as of: " + sharedPref.getStringData("DATE"));

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.iv_search_reviewer)
    public void onViewClicked() {
        searchReviewer();
        InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(etSearchReviewer.getWindowToken(), 0);
    }

    public void searchReviewer() {
        String audName = etSearchReviewer.getText().toString();

        if (!audName.equals("")) {
            reviewerList = ReviewerModel.findWithQuery(ReviewerModel.class, "SELECT * from REVIEWER_MODEL WHERE " +
                    "firstname LIKE '%" + audName + "%' OR " +
                    "middlename LIKE '%" + audName + "%' OR " +
                    "lastname LIKE '%" + audName + "%'" +
                    "ORDER BY createdate DESC");
            Log.e("AuditorsCount", reviewerList.size() + "");
            if (reviewerList.size() > 0) {
                setReviewerList();
                tvNoResult.setVisibility(View.GONE);
            } else if (reviewerList.size() <= 0) {
                setReviewerList();
                tvNoResult.setVisibility(View.VISIBLE);
            }
        } else {
            reviewerList = ReviewerModel.listAll(ReviewerModel.class, "createdate DESC");
            setReviewerList();
            tvNoResult.setVisibility(View.GONE);
        }
    }

    public void setReviewerList() {
        reviewerAdapter = new ReviewerAdapter(context, reviewerList);
        lvReviewerList.setAdapter(reviewerAdapter);
        tvReviewerCount.setText(reviewerList.size() + " Total Record(s)");
    }
}
