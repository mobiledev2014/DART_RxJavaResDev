package com.unilab.gmp.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.unilab.gmp.R;
import com.unilab.gmp.adapter.ActivityAdapter;
import com.unilab.gmp.adapter.TemplateElementAdapter;
import com.unilab.gmp.adapter.templates.AdapterAuditors;
import com.unilab.gmp.adapter.templates.AdapterCompanyBackgroundMajorChanges;
import com.unilab.gmp.adapter.templates.AdapterCompanyBackgroundName;
import com.unilab.gmp.adapter.templates.AdapterDistributionList;
import com.unilab.gmp.adapter.templates.AdapterDistributionOthers;
import com.unilab.gmp.adapter.templates.AdapterInspectionDate;
import com.unilab.gmp.adapter.templates.AdapterOthersIssueAudit;
import com.unilab.gmp.adapter.templates.AdapterOthersIssueExecutive;
import com.unilab.gmp.adapter.templates.AdapterPersonelMetDuring;
import com.unilab.gmp.adapter.templates.AdapterPreAuditDoc;
import com.unilab.gmp.adapter.templates.AdapterPresentDuringMeeting;
import com.unilab.gmp.adapter.templates.AdapterReference;
import com.unilab.gmp.adapter.templates.AdapterScopeAudit;
import com.unilab.gmp.adapter.templates.AdapterSummaryRecommendation;
import com.unilab.gmp.adapter.templates.AdapterTranslator;
import com.unilab.gmp.model.ApproverModel;
import com.unilab.gmp.model.AuditorsModel;
import com.unilab.gmp.model.ModelAuditReportReply;
import com.unilab.gmp.model.ModelAuditReports;
import com.unilab.gmp.model.ModelCompany;
import com.unilab.gmp.model.ModelDateOfAudit;
import com.unilab.gmp.model.ModelReportActivities;
import com.unilab.gmp.model.ModelReportApprover;
import com.unilab.gmp.model.ModelReportQuestion;
import com.unilab.gmp.model.ModelReportReviewer;
import com.unilab.gmp.model.ModelReportSubActivities;
import com.unilab.gmp.model.ModelSiteDate;
import com.unilab.gmp.model.ModelTemplateActivities;
import com.unilab.gmp.model.ModelTemplates;
import com.unilab.gmp.model.ReviewerModel;
import com.unilab.gmp.model.TemplateModelAuditors;
import com.unilab.gmp.model.TemplateModelCompanyBackgroundMajorChanges;
import com.unilab.gmp.model.TemplateModelCompanyBackgroundName;
import com.unilab.gmp.model.TemplateModelDistributionList;
import com.unilab.gmp.model.TemplateModelDistributionOthers;
import com.unilab.gmp.model.TemplateModelOtherIssuesAudit;
import com.unilab.gmp.model.TemplateModelOtherIssuesExecutive;
import com.unilab.gmp.model.TemplateModelPersonelMetDuring;
import com.unilab.gmp.model.TemplateModelPreAuditDoc;
import com.unilab.gmp.model.TemplateModelPresentDuringMeeting;
import com.unilab.gmp.model.TemplateModelReference;
import com.unilab.gmp.model.TemplateModelScopeAudit;
import com.unilab.gmp.model.TemplateModelScopeAuditInterest;
import com.unilab.gmp.model.TemplateModelSummaryRecommendation;
import com.unilab.gmp.model.TemplateModelTranslator;
import com.unilab.gmp.retrofit.ApiClient;
import com.unilab.gmp.retrofit.ApiInterface;
import com.unilab.gmp.retrofit.Async.PostAsync;
import com.unilab.gmp.utility.DateTimeUtils;
import com.unilab.gmp.utility.Glovar;
import com.unilab.gmp.utility.SharedPreferenceManager;
import com.unilab.gmp.utility.SimpleDividerItemDecoration;
import com.unilab.gmp.utility.StartDatePicker;
import com.unilab.gmp.utility.Variable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.orm.SugarRecord.find;
import static com.unilab.gmp.activity.HomeActivity.pDialog;

/**
 * Created by c_rcmiguel on 7/14/2017.
 */

@SuppressLint("ValidFragment")
public class NextSelectedTemplateFragment extends Fragment {

    public AdapterScopeAudit adapterScopeAudit;
    Unbinder unbinder;
    Context context;


    @BindView(R.id.scrl_main)
    ScrollView scrlMain;
    @BindView(R.id.btn_cancel)
    Button btnCancel;
    @BindView(R.id.btn_save_draft)
    Button btnSaveDraft;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.lv_template_next_activities_carried)
    RecyclerView lvTemplateNextActivitiesCarried;
    @BindView(R.id.et_template_next_activity_carried)
    EditText etTemplateNextActivityCarried;
    @BindView(R.id.btn_template_next_scope_audit_add)
    Button btnTemplateNextScopeAuditAdd;
    @BindView(R.id.btn_template_next_scope_audit_delete)
    Button btnTemplateNextScopeAuditDelete;
    @BindView(R.id.lv_template_next_scope_audit)
    RecyclerView lvTemplateNextScopeAudit;
    @BindView(R.id.btn_template_next_reference_add)
    Button btnTemplateNextReferenceAdd;
    @BindView(R.id.btn_template_next_reference_delete)
    Button btnTemplateNextReferenceDelete;
    @BindView(R.id.lv_template_next_reference)
    RecyclerView lvTemplateNextReference;
    @BindView(R.id.lv_template_next_pre_audit_doc)
    RecyclerView lvTemplateNextPreAuditDoc;
    @BindView(R.id.btn_template_next_pre_audit_doc_add)
    Button btnTemplateNextPreAuditDocAdd;
    @BindView(R.id.btn_template_next_pre_audit_doc_delete)
    Button btnTemplateNextPreAuditDocDelete;
    @BindView(R.id.et_template_next_audited_area)
    EditText etTemplateNextAuditedArea;
    @BindView(R.id.et_template_next_not_audited_area)
    EditText etTemplateNextNotAuditedArea;
    @BindView(R.id.et_template_next_date_of_wrap_up)
    EditText etTemplateNextDateOfWrapUp;
    @BindView(R.id.btn_template_next_present_close_up_add)
    Button btnTemplateNextPresentCloseUpAdd;
    @BindView(R.id.btn_template_next_present_close_up_delete)
    Button btnTemplateNextPresentCloseUpDelete;
    @BindView(R.id.lv_template_next_present_during_meeting)
    RecyclerView lvTemplateNextPresentDuringMeeting;
    @BindView(R.id.btn_template_next_personnel_inspection_add)
    Button btnTemplateNextPersonnelInspectionAdd;
    @BindView(R.id.btn_template_next_personnel_inspection_delete)
    Button btnTemplateNextPersonnelInspectionDelete;
    @BindView(R.id.lv_template_next_personnel_inspection)
    RecyclerView lvTemplateNextPersonnelInspection;
    @BindView(R.id.lv_template_next_distribution_list)
    RecyclerView lvTemplateNextDistributionList;
    @BindView(R.id.ll_template_next_distribution_list)
    LinearLayout llTemplateNextDistributionList;
    @BindView(R.id.btn_template_next_distribution_add)
    Button btnTemplateNextDistributionAdd;
    @BindView(R.id.btn_template_next_distribution_delete)
    Button btnTemplateNextDistributionDelete;
    @BindView(R.id.btn_template_next_summary_recommendation_add)
    Button btnTemplateNextSummaryRecommendationAdd;
    @BindView(R.id.btn_template_next_summary_recommendation_delete)
    Button btnTemplateNextSummaryRecommendationDelete;
    @BindView(R.id.lv_template_next_summary_recommendation)
    RecyclerView lvTemplateNextSummaryRecommendation;
    @BindView(R.id.et_template_next_summary_recommendation_audit_close_date)
    EditText etTemplateNextSummaryRecommendationAuditCloseDate;
    /*@BindView(R.id.et_template_next_summary_recommendation_other_issues_audit)
    EditText etTemplateNextSummaryRecommendationOtherIssuesAudit;
    @BindView(R.id.et_template_next_summary_recommendation_other_issues_executive)
    EditText etTemplateNextSummaryRecommendationOtherIssuesExecutive;*/
    @BindView(R.id.et_template_next_company_background_history)
    EditText etTemplateNextCompanyBackgroundHistory;
    @BindView(R.id.lv_template_next_company_background_name)
    RecyclerView lvTemplateNextCompanyBackgroundName;
    @BindView(R.id.ll_template_next_company_background_name)
    LinearLayout llTemplateNextCompanyBackgroundName;
    @BindView(R.id.btn_template_next_company_background_inspector_name_add)
    Button btnTemplateNextCompanyBackgroundInspectorNameAdd;
    @BindView(R.id.btn_template_next_company_background_inspector_name_delete)
    Button btnTemplateNextCompanyBackgroundInspectorNameDelete;
    @BindView(R.id.lv_template_next_company_background_major_changes)
    RecyclerView lvTemplateNextCompanyBackgroundMajorChanges;
    @BindView(R.id.ll_template_next_company_background_major_changes)
    LinearLayout llTemplateNextCompanyBackgroundMajorChanges;
    @BindView(R.id.btn_template_next_company_background_major_changes_add)
    Button btnTemplateNextCompanyBackgroundMajorChangesAdd;
    @BindView(R.id.btn_template_next_company_background_major_changes_delete)
    Button btnTemplateNextCompanyBackgroundMajorChangesDelete;
    @BindView(R.id.s_template_next_auditor_lead_name)
    Spinner sTemplateNextAuditorLeadName;
    @BindView(R.id.et_template_next_auditor_lead_position)
    EditText etTemplateNextAuditorLeadPosition;
    @BindView(R.id.et_template_next_auditor_lead_department)
    EditText etTemplateNextAuditorLeadDepartment;
    @BindView(R.id.btn_template_next_auditor_add)
    Button btnTemplateNextAuditorAdd;
    @BindView(R.id.btn_template_next_auditor_delete)
    Button btnTemplateNextAuditorDelete;
    @BindView(R.id.lv_template_next_auditors)
    RecyclerView lvTemplateNextAuditors;
    @BindView(R.id.s_template_next_reviewer_name)
    Spinner sTemplateNextReviewerName;
    @BindView(R.id.et_template_next_reviewer_position)
    EditText etTemplateNextReviewerPosition;
    @BindView(R.id.et_template_next_reviewer_department)
    EditText etTemplateNextReviewerDepartment;
    @BindView(R.id.s_template_next_approver_name)
    Spinner sTemplateNextApproverName;
    @BindView(R.id.et_template_next_approver_position)
    EditText etTemplateNextApproverPosition;
    @BindView(R.id.et_template_next_approver_department)
    EditText etTemplateNextApproverDepartment;
    @BindView(R.id.lv_template_next_translator)
    RecyclerView lvTemplateNextTranslator;
    @BindView(R.id.ll_template_next_translator)
    LinearLayout llTemplateNextTranslator;
    @BindView(R.id.btn_template_next_translator_add)
    Button btnTemplateNextTranslatorAdd;
    @BindView(R.id.btn_template_next_translator_delete)
    Button btnTemplateNextTranslatorDelete;
    @BindView(R.id.lv_template_next_other_distribution)
    RecyclerView lvTemplateNextOtherDistribution;
    @BindView(R.id.cb_template_next_reviewer)
    CheckBox cbTemplateNextReviewer;
    @BindView(R.id.btn_prev)
    Button btnPrev;
    @BindView(R.id.lv_template_next_company_background_inspection_date)
    RecyclerView lvTemplateNextCompanyBackgroundInspectionDate;
    @BindView(R.id.lv_template_next_summary_recommendation_other_issues_audit)
    RecyclerView lvTemplateNextSummaryRecommendationOtherIssuesAudit;
    @BindView(R.id.ll_template_next_summary_recommendation_other_issues_audit)
    LinearLayout llTemplateNextSummaryRecommendationOtherIssuesAudit;
    @BindView(R.id.btn_template_next_summary_recommendation_other_issues_audit_add)
    Button btnTemplateNextSummaryRecommendationOtherIssuesAuditAdd;
    @BindView(R.id.btn_template_next_summary_recommendation_other_issues_audit_delete)
    Button btnTemplateNextSummaryRecommendationOtherIssuesAuditDelete;
    @BindView(R.id.lv_template_next_summary_recommendation_other_issues_executive)
    RecyclerView lvTemplateNextSummaryRecommendationOtherIssuesExecutive;
    @BindView(R.id.ll_template_next_summary_recommendation_other_issues_executive)
    LinearLayout llTemplateNextSummaryRecommendationOtherIssuesExecutive;
    @BindView(R.id.btn_template_next_summary_recommendation_other_issues_executive_add)
    Button btnTemplateNextSummaryRecommendationOtherIssuesExecutiveAdd;
    @BindView(R.id.btn_template_next_summary_recommendation_other_issues_executive_delete)
    Button btnTemplateNextSummaryRecommendationOtherIssuesExecutiveDelete;

    Dialog dialogCancelTemplate;
    Dialog dialogSaveDraft;
    Dialog dialogSucSaveDraft;
    Dialog dialogSubmit;
    Dialog dialogSubmitFailed;
    Dialog dialogErrorDate;

    TemplateFragment templateFragment;
    ModelTemplates modelTemplates;
    ActivityAdapter activityAdapter;
    TemplateElementAdapter templateElementAdapter;
    List<ModelTemplateActivities> modelTemplateActivities;
    List<TemplateModelScopeAudit> templateModelScopeAudits;
    //    List<TemplateModelScopeAuditInterest> templateModelScopeAuditInterests;
    List<TemplateModelReference> templateModelReferences;
    List<TemplateModelPreAuditDoc> templateModelPreAuditDocs;
    List<TemplateModelPresentDuringMeeting> templateModelPresentDuringMeetings;
    List<TemplateModelPersonelMetDuring> templateModelPersonelMetDurings;
    List<TemplateModelDistributionList> templateModelDistributionLists;
    List<TemplateModelDistributionOthers> templateModelDistributionOthers;
    List<TemplateModelSummaryRecommendation> templateModelSummaryRecommendations;
    List<TemplateModelCompanyBackgroundName> templateModelCompanyBackgroundNames;
    List<TemplateModelCompanyBackgroundMajorChanges> templateModelCompanyBackgroundMajorChanges;
    List<TemplateModelAuditors> templateModelAuditorses;
    List<TemplateModelTranslator> templateModelTranslators;
    List<TemplateModelOtherIssuesAudit> templateModelOtherIssuesAudits;
    List<TemplateModelOtherIssuesExecutive> templateModelOtherIssuesExecutives;

    //AdapterScopeAuditInterest adapterScope;
    AdapterReference adapterReference;
    AdapterPreAuditDoc adapterPreAuditDoc;
    AdapterPresentDuringMeeting adapterPresentDuringMeeting;
    AdapterPersonelMetDuring adapterPersonelMetDuring;
    AdapterDistributionList adapterDistributionList;
    AdapterDistributionOthers adapterDistributionOthers;
    AdapterSummaryRecommendation adapterSummaryRecommendation;
    AdapterCompanyBackgroundName adapterCompanyBackgroundName;
    AdapterCompanyBackgroundMajorChanges adapterCompanyBackgroundMajorChanges;
    AdapterAuditors adapterAuditors;
    AdapterTranslator adapterTranslator;
    AdapterOthersIssueAudit adapterOthersIssueAudit;
    AdapterOthersIssueExecutive adapterOthersIssueExecutive;

    List<AuditorsModel> auditorsModels;
    List<ReviewerModel> reviewerModels;
    List<ApproverModel> approverModels;

    String wrapupdate = "";
    ApiInterface apiInterface;
    ModelAuditReports report;

    SharedPreferenceManager sharedPref;
    String reviewer_id = "", approver_id = "";
    SelectedTemplateFragment selectedTemplateFragment;
    View rootView;
    int sitemajorchangescount = 0;
    String message = "";

    Dialog dialogDeleteDateOfAudit;
    boolean dialogDeleteIsShowing = false;
    int simpleMessageDialog = -1, distributionDelete = 0, translatorDelete = 1,
            preAuditDocDelete = 2, distributionOthersDelete = 3, typeOfAuditDelete = 4,
            personnelMetDelete = 5, elementsRequiringDelete = 6, otherIssuesAuditDelete = 7,
            otherIssuesExecutiveDelete = 8, auditorDelete = 9, reviewerDelete = 10, presentDuringDelete = 11,
            majorChangesDelete = 12;

    public NextSelectedTemplateFragment(ModelTemplates modelTemplates, TemplateElementAdapter templateElementAdapter, SelectedTemplateFragment selectedTemplateFragment) {
        this.modelTemplates = modelTemplates;
        this.templateElementAdapter = templateElementAdapter;
        this.selectedTemplateFragment = selectedTemplateFragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        if (this.rootView != null) {
            ButterKnife.bind(this, this.rootView);
            unbinder = ButterKnife.bind(this, this.rootView);
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    pDialog.dismiss();
                }
            }, 500);
            return this.rootView;
        }
        View rootView = inflater.inflate(R.layout.fragment_template_selected_next, container, false);
        ButterKnife.bind(this, rootView);
        unbinder = ButterKnife.bind(this, rootView);
        context = getActivity();

        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        sharedPref = new SharedPreferenceManager(context);

        Variable.menu = true;
        Variable.onTemplate = true;
        Variable.onAudit = false;
        templateFragment = new TemplateFragment();
        wrapupdate = "";
        btnSubmit.setText("SUBMIT");

        modelTemplateActivities = find(ModelTemplateActivities.class, "templateid = ?", modelTemplates.getTemplateID());
        activityAdapter = new ActivityAdapter(context, modelTemplateActivities, "");
        lvTemplateNextActivitiesCarried.setLayoutManager(new LinearLayoutManager(context));
        lvTemplateNextActivitiesCarried.setItemAnimator(new DefaultItemAnimator());
//        Log.i("activityadapter", "call : " + activityAdapter.getCount());
        lvTemplateNextActivitiesCarried.setAdapter(activityAdapter);
//        lvTemplateNextActivitiesCarried.setExpanded(true);

        //--- Lead Auditor setting start
        //auditorsModels = AuditorsModel.listAll(AuditorsModel.class);
        String emailUsed = sharedPref.getStringData("EMAIL");
        Log.i("EMAILUSED", emailUsed);
        auditorsModels = AuditorsModel.find(AuditorsModel.class, "email = ?", emailUsed);
        List<String> scopeAuditList = new ArrayList<>();
        for (int x = 0; x < auditorsModels.size(); x++) {
            scopeAuditList.add(auditorsModels.get(x).getFname() + " " + auditorsModels.get(x).getMname()
                    + " " + auditorsModels.get(x).getLname());
        }
        ArrayAdapter<String> scopeAuditAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, scopeAuditList);
        sTemplateNextAuditorLeadName.setEnabled(false);
        sTemplateNextAuditorLeadName.setClickable(false);
        sTemplateNextAuditorLeadName.setAdapter(scopeAuditAdapter);
        //  for saved data
        // sTemplateNextAuditorLeadName.setSelection();

        sTemplateNextAuditorLeadName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                etTemplateNextAuditorLeadPosition.setText(auditorsModels.get(i).getDesignation());
                etTemplateNextAuditorLeadDepartment.setText(auditorsModels.get(i).getDepartment());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //--- Lead Auditor setting end

        //--- Reviewer setting start
        reviewerModels = ReviewerModel.find(ReviewerModel.class, "status > 0");
        List<String> reviewerList = new ArrayList<>();
        Log.i("REVIEWER", "SIZE : " + reviewerModels.size());
        for (int x = 0; x < reviewerModels.size(); x++) {
            reviewerList.add(reviewerModels.get(x).getFirstname() + " " + reviewerModels.get(x).getMiddlename()
                    + " " + reviewerModels.get(x).getLastname());
            if (reviewer_id.isEmpty()) {
                reviewer_id = reviewerModels.get(0).getReviewer_id();
            }
        }
        ArrayAdapter<String> reviewerAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, reviewerList);
        sTemplateNextReviewerName.setAdapter(reviewerAdapter);

        sTemplateNextReviewerName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                etTemplateNextReviewerPosition.setText(reviewerModels.get(i).getDesignation() + "a");
                etTemplateNextReviewerDepartment.setText(reviewerModels.get(i).getDepartment());
                reviewer_id = reviewerModels.get(i).getReviewer_id();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        cbTemplateNextReviewer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isReviewerChecked = !cbTemplateNextReviewer.isChecked();
                sTemplateNextReviewerName.setEnabled(isReviewerChecked);
                modelTemplates.setReviewerChecked(!isReviewerChecked);
            }
        });
        //--- Reviewer setting end
        //
        // --- Approver setting start
        approverModels = ApproverModel.find(ApproverModel.class, "status > 0");
        List<String> approverList = new ArrayList<>();
        for (int x = 0; x < approverModels.size(); x++) {
            approverList.add(approverModels.get(x).getFirstname() + " " + approverModels.get(x).getMiddlename()
                    + " " + approverModels.get(x).getLastname());
            if (approver_id.isEmpty()) {
                approver_id = approverModels.get(0).getApprover_id();
                approver_id = approverModels.get(0).getApprover_id();
            }
        }
        ArrayAdapter<String> approverAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, approverList);
        sTemplateNextApproverName.setAdapter(approverAdapter);

        sTemplateNextApproverName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                etTemplateNextApproverPosition.setText(approverModels.get(i).getDesignation());
                etTemplateNextApproverDepartment.setText(approverModels.get(i).getDepartment());
                approver_id = approverModels.get(i).getApprover_id();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //--- Approver setting end

        // --- Audit Scope
        templateModelScopeAudits = new ArrayList<>();
        adapterScopeAudit = new AdapterScopeAudit(templateModelScopeAudits, context, modelTemplates.getCompany_id(), this, null, btnTemplateNextScopeAuditAdd);

        lvTemplateNextScopeAudit.setLayoutManager(new LinearLayoutManager(context));
        lvTemplateNextScopeAudit.setItemAnimator(new DefaultItemAnimator());
        lvTemplateNextScopeAudit.setAdapter(adapterScopeAudit);
        lvTemplateNextScopeAudit.addItemDecoration(new SimpleDividerItemDecoration(context));
//        lvTemplateNextScopeAudit.setExpanded(true);
        //templateModelScopeAudits.addAll(TemplateModelScopeAudit.find(TemplateModelScopeAudit.class, "templateid = ?", modelTemplates.getTemplateID()));
        if (templateModelScopeAudits.size() <= 0) {
            addScopeAuditType();
        }

        // --- Other issues Audit
        templateModelOtherIssuesAudits = new ArrayList<>();
        adapterOthersIssueAudit = new AdapterOthersIssueAudit(templateModelOtherIssuesAudits, context);
        lvTemplateNextSummaryRecommendationOtherIssuesAudit.setLayoutManager(new LinearLayoutManager(context));
        lvTemplateNextSummaryRecommendationOtherIssuesAudit.setItemAnimator(new DefaultItemAnimator());
        lvTemplateNextSummaryRecommendationOtherIssuesAudit.setAdapter(adapterOthersIssueAudit);
//        lvTemplateNextSummaryRecommendationOtherIssuesAudit.setExpanded(true);
//        templateModelScopeAudits.addAll(TemplateModelScopeAudit.find(TemplateModelScopeAudit.class, "templateid = ? AND reportid = ?", report.getTemplate_id(), report.getReport_id()));
        if (templateModelOtherIssuesAudits.size() <= 0) {
            addOtherIssuesAudit();
        }
        // --- Other issues Executive
        templateModelOtherIssuesExecutives = new ArrayList<>();
        adapterOthersIssueExecutive = new AdapterOthersIssueExecutive(templateModelOtherIssuesExecutives, context);
        lvTemplateNextSummaryRecommendationOtherIssuesExecutive.setLayoutManager(new LinearLayoutManager(context));
        lvTemplateNextSummaryRecommendationOtherIssuesExecutive.setItemAnimator(new DefaultItemAnimator());
        lvTemplateNextSummaryRecommendationOtherIssuesExecutive.setAdapter(adapterOthersIssueExecutive);
//        lvTemplateNextSummaryRecommendationOtherIssuesExecutive.setExpanded(true);
//        templateModelScopeAudits.addAll(TemplateModelScopeAudit.find(TemplateModelScopeAudit.class, "templateid = ? AND reportid = ?", report.getTemplate_id(), report.getReport_id()));
        if (templateModelOtherIssuesExecutives.size() <= 0) {
            addOtherIssuesExecutive();
        }

        // --- Reference
        templateModelReferences = new ArrayList<>();
        adapterReference = new AdapterReference(templateModelReferences, context);
        lvTemplateNextReference.setLayoutManager(new LinearLayoutManager(context));
        lvTemplateNextReference.setItemAnimator(new DefaultItemAnimator());
        lvTemplateNextReference.setAdapter(adapterReference);
        lvTemplateNextReference.addItemDecoration(new SimpleDividerItemDecoration(context));
//        lvTemplateNextReference.setExpanded(true);
        //templateModelReferences.addAll(TemplateModelReference.find(TemplateModelReference.class, "templateid = ?", modelTemplates.getTemplateID()));
        if (templateModelReferences.size() <= 0) {
            addReference();
        }

        // --- Pre Audit Doc
        templateModelPreAuditDocs = new ArrayList<>();
        adapterPreAuditDoc = new AdapterPreAuditDoc(templateModelPreAuditDocs, context);
        lvTemplateNextPreAuditDoc.setLayoutManager(new LinearLayoutManager(context));
        lvTemplateNextPreAuditDoc.setItemAnimator(new DefaultItemAnimator());
        lvTemplateNextPreAuditDoc.setAdapter(adapterPreAuditDoc);
//        lvTemplateNextPreAuditDoc.setExpanded(true);
        //templateModelPreAuditDocs.addAll(TemplateModelPreAuditDoc.find(TemplateModelPreAuditDoc.class, "templateid = ?", modelTemplates.getTemplateID()));
        if (templateModelPreAuditDocs.size() <= 0) {
            addPreAuditDoc();
        }
        // --- Present during meeting
        templateModelPresentDuringMeetings = new ArrayList<>();
        adapterPresentDuringMeeting = new AdapterPresentDuringMeeting(templateModelPresentDuringMeetings, context);
        lvTemplateNextPresentDuringMeeting.setLayoutManager(new LinearLayoutManager(context));
        lvTemplateNextPresentDuringMeeting.setItemAnimator(new DefaultItemAnimator());
        lvTemplateNextPresentDuringMeeting.setAdapter(adapterPresentDuringMeeting);
        lvTemplateNextPresentDuringMeeting.addItemDecoration(new SimpleDividerItemDecoration(context));
//        lvTemplateNextPresentDuringMeeting.setExpanded(true);
        //templateModelPresentDuringMeetings.addAll(TemplateModelPresentDuringMeeting.find(TemplateModelPresentDuringMeeting.class, "templateid = ?", modelTemplates.getTemplateID()));
        if (templateModelPresentDuringMeetings.size() <= 0) {
            addPresentDuringMeeting();
        }
        // --- Personel Met During
        templateModelPersonelMetDurings = new ArrayList<>();
        adapterPersonelMetDuring = new AdapterPersonelMetDuring(templateModelPersonelMetDurings, context);
        lvTemplateNextPersonnelInspection.setLayoutManager(new LinearLayoutManager(context));
        lvTemplateNextPersonnelInspection.setItemAnimator(new DefaultItemAnimator());
        lvTemplateNextPersonnelInspection.setAdapter(adapterPersonelMetDuring);
        lvTemplateNextPersonnelInspection.addItemDecoration(new SimpleDividerItemDecoration(context));
//        lvTemplateNextPersonnelInspection.setExpanded(true);
        //templateModelPersonelMetDurings.addAll(TemplateModelPersonelMetDuring.find(TemplateModelPersonelMetDuring.class, "templateid = ?", modelTemplates.getTemplateID()));
        if (templateModelPersonelMetDurings.size() <= 0) {
            addPersonelMet();
        }
        // --- Distribution List
        templateModelDistributionLists = new ArrayList<>();
        adapterDistributionList = new AdapterDistributionList(templateModelDistributionLists, context);
        lvTemplateNextDistributionList.setLayoutManager(new LinearLayoutManager(context));
        lvTemplateNextDistributionList.setItemAnimator(new DefaultItemAnimator());
        lvTemplateNextDistributionList.setAdapter(adapterDistributionList);
//        lvTemplateNextDistributionList.setExpanded(true);
        //templateModelDistributionLists.addAll(TemplateModelDistributionList.find(TemplateModelDistributionList.class, "templateid = ?", modelTemplates.getTemplateID()));
        if (templateModelDistributionLists.size() <= 0) {
            addDistribution();
        }

        //--- Distribution others

        templateModelDistributionOthers = new ArrayList<>();
        adapterDistributionOthers = new AdapterDistributionOthers(templateModelDistributionOthers, context);
        lvTemplateNextOtherDistribution.setLayoutManager(new LinearLayoutManager(context));
        lvTemplateNextOtherDistribution.setItemAnimator(new DefaultItemAnimator());
        lvTemplateNextOtherDistribution.setAdapter(adapterDistributionOthers);
//        lvTemplateNextOtherDistribution.setExpanded(true);
        //templateModelTranslators.addAll(TemplateModelTranslator.find(TemplateModelTranslator.class, "templateid = ?", modelTemplates.getTemplateID()));
        if (templateModelDistributionOthers.size() <= 0) {
            addDistributionOthers();
        }


        // --- Reocommendation
        templateModelSummaryRecommendations = new ArrayList<>();
        adapterSummaryRecommendation = new AdapterSummaryRecommendation(templateModelSummaryRecommendations, context, modelTemplates.getTemplateID());
        lvTemplateNextSummaryRecommendation.setLayoutManager(new LinearLayoutManager(context));
        lvTemplateNextSummaryRecommendation.setItemAnimator(new DefaultItemAnimator());
        lvTemplateNextSummaryRecommendation.setAdapter(adapterSummaryRecommendation);
        lvTemplateNextSummaryRecommendation.addItemDecoration(new SimpleDividerItemDecoration(context));
//        lvTemplateNextSummaryRecommendation.setExpanded(true);
        //templateModelSummaryRecommendations.addAll(TemplateModelSummaryRecommendation.find(TemplateModelSummaryRecommendation.class, "templateid = ?", modelTemplates.getTemplateID()));
        if (templateModelSummaryRecommendations.size() <= 0) {
            addRecommendation();
        }
        // ---
        templateModelCompanyBackgroundNames = new ArrayList<>();
        templateModelCompanyBackgroundNames.addAll(TemplateModelCompanyBackgroundName.find(TemplateModelCompanyBackgroundName.class, "companyid = ?", modelTemplates.getCompany_id()));
        adapterCompanyBackgroundName = new AdapterCompanyBackgroundName(templateModelCompanyBackgroundNames, context, templateModelCompanyBackgroundNames.size());
        lvTemplateNextCompanyBackgroundName.setLayoutManager(new LinearLayoutManager(context));
        lvTemplateNextCompanyBackgroundName.setItemAnimator(new DefaultItemAnimator());
        lvTemplateNextCompanyBackgroundName.setAdapter(adapterCompanyBackgroundName);
//        lvTemplateNextCompanyBackgroundName.setExpanded(true);
        if (templateModelCompanyBackgroundNames.size() <= 0) {
            addBackgroundName();
        }
        // ---
        templateModelCompanyBackgroundMajorChanges = new ArrayList<>();

        sitemajorchangescount = TemplateModelCompanyBackgroundMajorChanges
                .find(TemplateModelCompanyBackgroundMajorChanges.class, "companyid = ? AND reportid = '0'", modelTemplates.getCompany_id()).size();

        templateModelCompanyBackgroundMajorChanges.addAll(TemplateModelCompanyBackgroundMajorChanges
                .find(TemplateModelCompanyBackgroundMajorChanges.class, "companyid = ? AND reportid = '0'", modelTemplates.getCompany_id()));
        adapterCompanyBackgroundMajorChanges = new AdapterCompanyBackgroundMajorChanges(templateModelCompanyBackgroundMajorChanges, context, sitemajorchangescount);//templateModelCompanyBackgroundMajorChanges.size());
        lvTemplateNextCompanyBackgroundMajorChanges.setLayoutManager(new LinearLayoutManager(context));
        lvTemplateNextCompanyBackgroundMajorChanges.setItemAnimator(new DefaultItemAnimator());
        lvTemplateNextCompanyBackgroundMajorChanges.setAdapter(adapterCompanyBackgroundMajorChanges);
//        lvTemplateNextCompanyBackgroundMajorChanges.setExpanded(true);
        if (templateModelCompanyBackgroundMajorChanges.size() <= 0) {
            addMajorChanges();
        }
        // ---
        templateModelAuditorses = new ArrayList<>();
        adapterAuditors = new AdapterAuditors(templateModelAuditorses, context, auditorsModels.get(0).getAuditor_id());
        lvTemplateNextAuditors.setLayoutManager(new LinearLayoutManager(context));
        lvTemplateNextAuditors.setItemAnimator(new DefaultItemAnimator());
        lvTemplateNextAuditors.setAdapter(adapterAuditors);
        lvTemplateNextAuditors.addItemDecoration(new SimpleDividerItemDecoration(context));
//        lvTemplateNextAuditors.setExpanded(true);
        //templateModelAuditorses.addAll(TemplateModelAuditors.find(TemplateModelAuditors.class, "templateid = ?", modelTemplates.getTemplateID()));
        if (templateModelAuditorses.size() <= 0) {
            addAuditors();
        }
        // --- Translator
        templateModelTranslators = new ArrayList<>();
        adapterTranslator = new AdapterTranslator(templateModelTranslators, context);
        lvTemplateNextTranslator.setLayoutManager(new LinearLayoutManager(context));
        lvTemplateNextTranslator.setItemAnimator(new DefaultItemAnimator());
        lvTemplateNextTranslator.setAdapter(adapterTranslator);
//        lvTemplateNextTranslator.setExpanded(true);
        //templateModelTranslators.addAll(TemplateModelTranslator.find(TemplateModelTranslator.class, "templateid = ?", modelTemplates.getTemplateID()));
        if (templateModelTranslators.size() <= 0) {
            addTranslator();
        }
        // ---

        etTemplateNextActivityCarried.setText(modelTemplates.getOther_activities());

        List<ModelCompany> mc = ModelCompany.find(ModelCompany.class, "companyid = ?", modelTemplates.getCompany_id());
        if (mc.size() > 0) {
            etTemplateNextCompanyBackgroundHistory.setText(mc.get(0).getBackground());
        }

        //adapter inspection date call and set
        List<ModelSiteDate> modelSiteDates = ModelSiteDate.find(ModelSiteDate.class, "companyid = ?", modelTemplates.getCompany_id());
        AdapterInspectionDate adapterInspectionDate = new AdapterInspectionDate(context, modelSiteDates);

        lvTemplateNextCompanyBackgroundInspectionDate.setLayoutManager(new LinearLayoutManager(context));
        lvTemplateNextCompanyBackgroundInspectionDate.setItemAnimator(new DefaultItemAnimator());
        lvTemplateNextCompanyBackgroundInspectionDate.setAdapter(adapterInspectionDate);
//        lvTemplateNextCompanyBackgroundInspectionDate.setExpanded(true);


        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                pDialog.dismiss();
            }
        }, 500);
        this.rootView = rootView;
        return rootView;
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        if (context != null) {
//            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//        }
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        if (context != null) {
//            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
//        }
//    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btn_cancel, R.id.btn_save_draft, R.id.btn_submit,
            R.id.btn_template_next_scope_audit_add, R.id.btn_template_next_scope_audit_delete,
            /*R.id.btn_template_next_scope_audit_interest_add, R.id.btn_template_next_scope_audit_interest_delete,*/
            R.id.btn_template_next_reference_add, R.id.btn_template_next_reference_delete,
            R.id.btn_template_next_pre_audit_doc_add, R.id.btn_template_next_pre_audit_doc_delete,
            R.id.btn_template_next_present_close_up_add, R.id.btn_template_next_present_close_up_delete,
            R.id.btn_template_next_personnel_inspection_add, R.id.btn_template_next_personnel_inspection_delete,
            R.id.btn_template_next_distribution_add, R.id.btn_template_next_distribution_delete,
            R.id.btn_template_next_summary_recommendation_add, R.id.btn_template_next_summary_recommendation_delete,
            R.id.btn_template_next_company_background_inspector_name_add, R.id.btn_template_next_company_background_inspector_name_delete,
            R.id.btn_template_next_company_background_major_changes_add, R.id.btn_template_next_company_background_major_changes_delete,
            R.id.btn_template_next_auditor_add, R.id.btn_template_next_auditor_delete,
            R.id.btn_template_next_translator_add, R.id.btn_template_next_translator_delete,
            R.id.et_template_next_date_of_wrap_up,
            R.id.btn_template_next_other_distribution_add,
            R.id.btn_template_next_other_distribution_delete,
            R.id.et_template_next_summary_recommendation_audit_close_date, R.id.btn_prev,
            R.id.btn_template_next_summary_recommendation_other_issues_audit_add,
            R.id.btn_template_next_summary_recommendation_other_issues_audit_delete,
            R.id.btn_template_next_summary_recommendation_other_issues_executive_add,
            R.id.btn_template_next_summary_recommendation_other_issues_executive_delete})
    public void onViewClicked(View view) {
        Log.e("clicked", "button : " + view.getId());
        switch (view.getId()) {
            case R.id.btn_cancel:
                dialogCancelTemplate();
                break;
            case R.id.btn_save_draft:
                //save();
                dialogSaveDraft("Are you sure you want to save as draft?");
                break;
            case R.id.btn_submit:
                dialogSubmit("Are you sure you want to submit?");
                break;
            case R.id.btn_template_next_summary_recommendation_other_issues_audit_add:
                addOtherIssuesAudit();
                break;
            case R.id.btn_template_next_summary_recommendation_other_issues_audit_delete:
                if (templateModelOtherIssuesAudits.size() > 1)
                    dialogDeleteFromListConfirmation("Are you sure you want to delete?", otherIssuesAuditDelete);
                else {
                    if (!templateModelOtherIssuesAudits.get(0).getOther_issues_audit().equals(""))
                        dialogDeleteFromListConfirmation("Are you sure you want to delete?", otherIssuesAuditDelete);
                }
                break;
            case R.id.btn_template_next_summary_recommendation_other_issues_executive_add:
                addOtherIssuesExecutive();
                break;
            case R.id.btn_template_next_summary_recommendation_other_issues_executive_delete:
                if (templateModelOtherIssuesExecutives.size() > 1)
                    dialogDeleteFromListConfirmation("Are you sure you want to delete?", otherIssuesExecutiveDelete);
                else {
                    if (!templateModelOtherIssuesExecutives.get(0).getOther_issues_executive().equals(""))
                        dialogDeleteFromListConfirmation("Are you sure you want to delete?", otherIssuesExecutiveDelete);
                }
                break;
            case R.id.btn_template_next_scope_audit_add:
                addScopeAuditType();
                break;
            case R.id.btn_template_next_scope_audit_delete:
                if (templateModelScopeAudits.size() > 1) {
                    dialogDeleteFromListConfirmation("Are you sure you want to delete?", typeOfAuditDelete);
                }
                break;
            case R.id.btn_template_next_reference_add:
                addReference();
                break;
            case R.id.btn_template_next_reference_delete:
                if (templateModelReferences.size() > 1) {
                    templateModelReferences.remove(templateModelReferences.size() - 1);
                    adapterReference.notifyItemRemoved(templateModelReferences.size());
                }
                break;
            case R.id.btn_template_next_pre_audit_doc_add:
                addPreAuditDoc();
                break;
            case R.id.btn_template_next_pre_audit_doc_delete:
                if (templateModelPreAuditDocs.size() > 1)
                    dialogDeleteFromListConfirmation("Are you sure you want to delete?", preAuditDocDelete);
                else {
                    if (!templateModelPreAuditDocs.get(0).getPreaudit().equals(""))
                        dialogDeleteFromListConfirmation("Are you sure you want to delete?", preAuditDocDelete);
                }
                break;
            case R.id.btn_template_next_present_close_up_add:
                addPresentDuringMeeting();
                break;
            case R.id.btn_template_next_present_close_up_delete:
                if (templateModelPresentDuringMeetings.size() > 1)
                    dialogDeleteFromListConfirmation("Are you sure you want to delete?", presentDuringDelete);
                else {
                    if (!templateModelPresentDuringMeetings.get(0).getName().equals("")
                            || !templateModelPresentDuringMeetings.get(0).getPosition().equals(""))
                        dialogDeleteFromListConfirmation("Are you sure you want to delete?", presentDuringDelete);
                }
                break;
            case R.id.btn_template_next_personnel_inspection_add:
                addPersonelMet();
                break;
            case R.id.btn_template_next_personnel_inspection_delete:
                if (templateModelPersonelMetDurings.size() > 1)
                    dialogDeleteFromListConfirmation("Are you sure you want to delete?", personnelMetDelete);
                else {
                    if (!templateModelPersonelMetDurings.get(0).getName().equals("")
                            || !templateModelPersonelMetDurings.get(0).getPosition().equals(""))
                        dialogDeleteFromListConfirmation("Are you sure you want to delete?", personnelMetDelete);
                }
                break;
            case R.id.btn_template_next_distribution_add:
                addDistribution();
                break;
            case R.id.btn_template_next_distribution_delete:
                if (templateModelDistributionLists.size() > 1) {
                    dialogDeleteFromListConfirmation("Are you sure you want to delete?", distributionDelete);
                }
                break;
            case R.id.btn_template_next_other_distribution_add:
                addDistributionOthers();
                break;
            case R.id.btn_template_next_other_distribution_delete:
                if (templateModelDistributionOthers.size() > 1)
                    dialogDeleteFromListConfirmation("Are you sure you want to delete?", distributionOthersDelete);
                else {
                    if (!templateModelDistributionOthers.get(0).getDistribution_other().equals(""))
                        dialogDeleteFromListConfirmation("Are you sure you want to delete?", distributionOthersDelete);
                }
                break;
            case R.id.btn_template_next_summary_recommendation_add:
                addRecommendation();
                break;
            case R.id.btn_template_next_summary_recommendation_delete:
                if (templateModelSummaryRecommendations.size() > 1) {
                    dialogDeleteFromListConfirmation("Are you sure you want to delete?", elementsRequiringDelete);

                }
                break;
            case R.id.btn_template_next_company_background_inspector_name_add:
                addBackgroundName();
                break;
            case R.id.btn_template_next_company_background_inspector_name_delete:
                if (templateModelCompanyBackgroundNames.size() > 1) {
                    templateModelCompanyBackgroundNames.remove(templateModelCompanyBackgroundNames.size() - 1);
                    adapterCompanyBackgroundName.notifyItemRemoved(templateModelCompanyBackgroundNames.size());
                }
                break;
            case R.id.btn_template_next_company_background_major_changes_add:
                addMajorChanges();
                break;
            case R.id.btn_template_next_company_background_major_changes_delete:
                List<TemplateModelCompanyBackgroundMajorChanges> majorChanges =
                        TemplateModelCompanyBackgroundMajorChanges.find(
                                TemplateModelCompanyBackgroundMajorChanges.class, "companyid = ?", modelTemplates.getCompany_id());
                if (templateModelCompanyBackgroundMajorChanges.size() > majorChanges.size()) {
                    if (templateModelCompanyBackgroundMajorChanges.size() > 1)
                        dialogDeleteFromListConfirmation("Are you sure you want to delete?", majorChangesDelete);
                    else {
                        if (!templateModelCompanyBackgroundMajorChanges.get(0).getMajorchanges().equals(""))
                            dialogDeleteFromListConfirmation("Are you sure you want to delete?", majorChangesDelete);
                    }
                }
                break;
            case R.id.btn_template_next_auditor_add:
                addAuditors();
                break;
            case R.id.btn_template_next_auditor_delete:
                if (templateModelAuditorses.size() > 1) {
                    dialogDeleteFromListConfirmation("Are you sure you want to delete?", auditorDelete);
                }
                break;
            case R.id.btn_template_next_translator_add:
                addTranslator();
                break;
            case R.id.btn_template_next_translator_delete:
                if (templateModelTranslators.size() > 1)
                    dialogDeleteFromListConfirmation("Are you sure you want to delete?", translatorDelete);
                else {
                    if (!templateModelTranslators.get(0).getTranslator().equals(""))
                        dialogDeleteFromListConfirmation("Are you sure you want to delete?", translatorDelete);
                }
                break;
            case R.id.et_template_next_date_of_wrap_up:
                callDatePicker(etTemplateNextDateOfWrapUp);
                break;
//            case R.id.et_template_next_company_background_date_from:
//                callDatePicker(etTemplateNextCompanyBackgroundDateFrom);
//                break;
//            case R.id.et_template_next_company_background_date_to:
//                callDatePicker(etTemplateNextCompanyBackgroundDateTo);
//                break;
            case R.id.et_template_next_summary_recommendation_audit_close_date:
                callDatePicker(etTemplateNextSummaryRecommendationAuditCloseDate);
                break;
            case R.id.btn_prev:
                /*FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.fl_content, selectedTemplateFragment, "TemplateSelect");
                ft.commit();*/

                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                fragmentManager.beginTransaction()
                        .replace(R.id.fl_content, selectedTemplateFragment).addToBackStack(null).commit();
                break;
        }
    }

    private void callDatePicker(EditText editText) {
        StartDatePicker datePicker = new StartDatePicker(editText, context);
        datePicker.show(((AppCompatActivity) context).getSupportFragmentManager(), "datePicker");
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    private void analyzeInputs() {
        ProgressDialog loginDialog = new ProgressDialog(context);
        loginDialog.setCanceledOnTouchOutside(false);
        loginDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        loginDialog.setMax(2);

        if (validate()) {
            if (isNetworkConnected()) {
                saveReport();

                String email = sharedPref.getStringData("EMAIL");
                String password = sharedPref.getStringData("PASSWORD");

                Log.i("ERROR", "POSTASYNC 3");
                new PostAsync(context, loginDialog, email, password, Glovar.POST_TEMPLATE, null, NextSelectedTemplateFragment.this).execute();
                //Toast.makeText(context, "True", Toast.LENGTH_SHORT).show();
//                postData();
            } else {
                //dialog if save as draft or self destruct
                dialogSaveDraft("No internet connection. Would you like to save as draft?");
            }
        } else {
            //Toast.makeText(context, "False", Toast.LENGTH_SHORT).show();
            dialogSubmitFailed(message);
        }
    }

    private void saveReport() {
        ModelAuditReports mar = new ModelAuditReports();
        int size = ModelAuditReports.listAll(ModelAuditReports.class).size() + 1;
        String zero = "";
        if (size < 100) {
            zero = "0";
        }
        if (size < 10) {
            zero = "00";
        }

        String report_id = zero + size;
        mar.setReport_id(report_id);
        mar.setReport_no("GMP-00-" + zero + size);
        mar.setStatus("1");
        mar.setTemplate_id(modelTemplates.getTemplateID());
        mar.setCompany_id(modelTemplates.getCompany_id());
        mar.setAudit_date_1(modelTemplates.getAudit_date_1());
        mar.setAudit_date_2(modelTemplates.getAudit_date_2());
        mar.setAuditor_id(auditorsModels.get(sTemplateNextAuditorLeadName.getSelectedItemPosition()).getAuditor_id());
        mar.setReviewer_id(reviewer_id);
        mar.setApprover_id(approver_id);
        mar.setReviewerChecked(modelTemplates.isReviewerChecked());
        mar.setWrap_date(DateTimeUtils.parseDateMonthToDigit(etTemplateNextDateOfWrapUp.getText().toString()));

        mar.setHead_lead(cbTemplateNextReviewer.isChecked() ? "1" : "0");

        adapterAuditors.save(report_id);

        ModelReportReviewer.deleteAll(ModelReportReviewer.class, "reportid = ?", report_id);
        ModelReportReviewer mrr = new ModelReportReviewer();
        mrr.setReport_id(report_id);
        mrr.setReviewer_id(reviewerModels.get(sTemplateNextReviewerName.getSelectedItemPosition()).getReviewer_id());
        mrr.save();

        ModelReportApprover.deleteAll(ModelReportApprover.class, "reportid = ?", report_id);
        ModelReportApprover mra = new ModelReportApprover();
        mra.setReport_id(report_id);
        mra.setApprover_id(approverModels.get(sTemplateNextApproverName.getSelectedItemPosition()).getApprover_id());
        mra.save();

        ModelDateOfAudit.deleteAll(ModelDateOfAudit.class, "reportid = ?", report_id);
        for (ModelDateOfAudit t : modelTemplates.getModelDateOfAudits()) {
            t.setReport_id(report_id);
            t.save();
        }

        mar.setAudit_close_date(DateTimeUtils.parseDateMonthToDigit(
                etTemplateNextSummaryRecommendationAuditCloseDate.getText().toString()));
        mar.setAudited_areas(etTemplateNextAuditedArea.getText().toString());
        mar.setAreas_to_consider(etTemplateNextNotAuditedArea.getText().toString());

        adapterTranslator.save(report_id);
        adapterScopeAudit.save(report_id);
        adapterPreAuditDoc.save(report_id);
        adapterReference.save(report_id);
        adapterCompanyBackgroundMajorChanges.save(report_id, mar.getCompany_id());//inspection
        adapterCompanyBackgroundName.save(report_id);//inspector
        adapterPersonelMetDuring.save(report_id);
        activityAdapter.save(report_id);
        templateElementAdapter.save(report_id);
        adapterOthersIssueAudit.save(mar.getReport_id());
        adapterOthersIssueExecutive.save(mar.getReport_id());

        mar.setOther_activities(etTemplateNextActivityCarried.getText().toString());
        adapterPresentDuringMeeting.save(report_id);//w
        adapterDistributionList.save(report_id);//w
        adapterDistributionOthers.save(report_id);

        adapterSummaryRecommendation.save(report_id);//(y)
        adapterOthersIssueAudit.save(report_id);
        adapterOthersIssueExecutive.save(report_id);
        mar.setModified_date(getDate());
        mar.save();
        report = mar;
    }

    private void addOtherIssuesAudit() {
        if (20 > templateModelOtherIssuesAudits.size()) {
            TemplateModelOtherIssuesAudit t = new TemplateModelOtherIssuesAudit();
            t.setOther_issues_audit("");
            templateModelOtherIssuesAudits.add(t);
            adapterOthersIssueAudit.notifyItemInserted(templateModelOtherIssuesAudits.size() - 1);
        } else
            dialogDeleteFromListConfirmation("You've reached the maximum number of 20", simpleMessageDialog);


    }

    private void addOtherIssuesExecutive() {
        if (20 > templateModelOtherIssuesExecutives.size()) {
            TemplateModelOtherIssuesExecutive t = new TemplateModelOtherIssuesExecutive();
            t.setOther_issues_executive("");
            templateModelOtherIssuesExecutives.add(t);
            adapterOthersIssueExecutive.notifyItemInserted(templateModelOtherIssuesExecutives.size() - 1);
        } else
            dialogDeleteFromListConfirmation("You've reached the maximum number of 20", simpleMessageDialog);
    }

    private void addScopeAuditType() {
        //if (adapterScopeAudit.getTypeAuditSize() > templateModelScopeAudits.size()) {
        if (adapterScopeAudit.getTypeAuditSize() > templateModelScopeAudits.size()) {
            Log.i("SIZE LOG", adapterScopeAudit.getTypeAuditSize() + " > " + templateModelScopeAudits.size());
            TemplateModelScopeAudit t = new TemplateModelScopeAudit();
            t.setScope_detail("");
            t.setTemplate_id(modelTemplates.getTemplateID());
            templateModelScopeAudits.add(t);
            adapterScopeAudit.notifyItemInserted(templateModelScopeAudits.size() - 1);
        } else
            dialogDeleteFromListConfirmation("You've reached the maximum number of " + adapterScopeAudit.getTypeAuditSize(), simpleMessageDialog);
    }

    private void addReference() {
        if (20 > templateModelReferences.size()) {
            TemplateModelReference t = new TemplateModelReference();
            t.setTemplate_id(modelTemplates.getTemplateID());
            templateModelReferences.add(t);
            adapterReference.notifyItemInserted(templateModelReferences.size() - 1);
        } else
            dialogDeleteFromListConfirmation("You've reached the maximum number of 20", simpleMessageDialog);
    }

    private void addPreAuditDoc() {
        if (20 > templateModelPreAuditDocs.size()) {
            TemplateModelPreAuditDoc t = new TemplateModelPreAuditDoc();
            t.setTemplate_id(modelTemplates.getTemplateID());
            templateModelPreAuditDocs.add(t);
            adapterPreAuditDoc.notifyItemInserted(templateModelPreAuditDocs.size() - 1);
        } else
            dialogDeleteFromListConfirmation("You've reached the maximum number of 20", simpleMessageDialog);
    }

    private void addPresentDuringMeeting() {
        if (30 > templateModelPresentDuringMeetings.size()) {
            TemplateModelPresentDuringMeeting t = new TemplateModelPresentDuringMeeting();
            t.setTemplate_id(modelTemplates.getTemplateID());
            templateModelPresentDuringMeetings.add(t);
            adapterPresentDuringMeeting.notifyItemInserted(templateModelPresentDuringMeetings.size() - 1);
        } else
            dialogDeleteFromListConfirmation("You've reached the maximum number of 30", simpleMessageDialog);
    }

    private void addPersonelMet() {
        if (30 > templateModelPersonelMetDurings.size()) {
            TemplateModelPersonelMetDuring t = new TemplateModelPersonelMetDuring();
            t.setTemplate_id(modelTemplates.getTemplateID());
            templateModelPersonelMetDurings.add(t);
            adapterPersonelMetDuring.notifyItemInserted(templateModelPersonelMetDurings.size() - 1);
        } else
            dialogDeleteFromListConfirmation("You've reached the maximum number of 30", simpleMessageDialog);
    }

    private void addDistribution() {
        Log.i("distri count", templateModelDistributionLists.size() + "");
        if (10 > templateModelDistributionLists.size()) {
            TemplateModelDistributionList t = new TemplateModelDistributionList();
            t.setTemplate_id(modelTemplates.getTemplateID());
            templateModelDistributionLists.add(t);
            adapterDistributionList.notifyItemInserted(templateModelDistributionLists.size() - 1);
        } else
            dialogDeleteFromListConfirmation("You've reached the maximum number of 10", simpleMessageDialog);
    }

    private void addDistributionOthers() {
        if (10 > templateModelDistributionOthers.size()) {
            TemplateModelDistributionOthers t = new TemplateModelDistributionOthers();
            t.setTemplate_id(modelTemplates.getTemplateID());
            t.setDistribution_other("");
            templateModelDistributionOthers.add(t);
            adapterDistributionOthers.notifyItemInserted(templateModelDistributionOthers.size() - 1);
        } else
            dialogDeleteFromListConfirmation("You've reached the maximum number of 10", simpleMessageDialog);
    }

    private void addRecommendation() {
        if (adapterSummaryRecommendation.getRSize() > templateModelSummaryRecommendations.size()) {
            TemplateModelSummaryRecommendation t = new TemplateModelSummaryRecommendation();
            t.setTemplate_id(modelTemplates.getTemplateID());
            templateModelSummaryRecommendations.add(t);
            adapterSummaryRecommendation.notifyItemInserted(templateModelSummaryRecommendations.size() - 1);
        } else
            dialogDeleteFromListConfirmation("You've reached the maximum number of " + adapterSummaryRecommendation.getRSize(), simpleMessageDialog);

    }

    private void addBackgroundName() {//disabled
        if (4 > templateModelCompanyBackgroundNames.size()) {
            TemplateModelCompanyBackgroundName t = new TemplateModelCompanyBackgroundName();
            t.setTemplate_id(modelTemplates.getTemplateID());
            templateModelCompanyBackgroundNames.add(t);
            adapterCompanyBackgroundName.notifyItemInserted(templateModelCompanyBackgroundNames.size() - 1);
        }
    }

    private void addMajorChanges() {
        if (20 > templateModelCompanyBackgroundMajorChanges.size()) {
            TemplateModelCompanyBackgroundMajorChanges t = new TemplateModelCompanyBackgroundMajorChanges();
            t.setTemplate_id(modelTemplates.getTemplateID());
            templateModelCompanyBackgroundMajorChanges.add(t);
            adapterCompanyBackgroundMajorChanges.notifyItemInserted(templateModelCompanyBackgroundMajorChanges.size() - 1);
        } else
            dialogDeleteFromListConfirmation("You've reached the maximum number of 20", simpleMessageDialog);

    }

    private void addAuditors() {
        if (adapterAuditors.getAuditorSize() > templateModelAuditorses.size()) {
            TemplateModelAuditors t = new TemplateModelAuditors();
            t.setTemplate_id(modelTemplates.getTemplateID());
            templateModelAuditorses.add(t);
            adapterAuditors.notifyItemInserted(templateModelAuditorses.size() - 1);
        } else
            dialogDeleteFromListConfirmation("You've reached the maximum number of "
                    + adapterAuditors.getAuditorSize(), simpleMessageDialog);
    }

    private void addTranslator() {
        if (10 > templateModelTranslators.size()) {
            TemplateModelTranslator t = new TemplateModelTranslator();
            t.setTemplate_id(modelTemplates.getTemplateID());
            templateModelTranslators.add(t);
            adapterTranslator.notifyItemInserted(templateModelTranslators.size() - 1);
            if (templateModelTranslators.size() > 1) {
                scrlMain.post(new Runnable() {
                    public void run() {
                        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(btnTemplateNextTranslatorAdd.getWindowToken(), 0);
                        scrlMain.fullScroll(View.FOCUS_DOWN);
                    }
                });
            }
        } else
            dialogDeleteFromListConfirmation("You've reached the maximum number of 10", simpleMessageDialog);

    }

    public void dialogCancelTemplate() {
        dialogCancelTemplate = new Dialog(context);
        dialogCancelTemplate.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialogCancelTemplate.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogCancelTemplate.setCancelable(false);
        dialogCancelTemplate.setContentView(R.layout.dialog_cancel_template_confirmation);
        dialogCancelTemplate.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        Button yes = (Button) dialogCancelTemplate.findViewById(R.id.btn_yes);
        Button no = (Button) dialogCancelTemplate.findViewById(R.id.btn_no);

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                fragmentManager.beginTransaction()
                        .replace(R.id.fl_content, new TemplateFragment()).addToBackStack(null).commit();
                dialogCancelTemplate.dismiss();
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogCancelTemplate.dismiss();
            }
        });

        dialogCancelTemplate.show();
    }

    public void set_error(EditText editText) {
        editText.setError("This field is required");
    }

    public boolean validate() {
        message = "Please fill up all the required fields.";
        boolean passed = true;
        if (etTemplateNextAuditedArea.getText().toString().equals("")) {
            passed = false;
            set_error(etTemplateNextAuditedArea);
        }
        if (etTemplateNextNotAuditedArea.getText().toString().equals("")) {
            passed = false;
            set_error(etTemplateNextNotAuditedArea);
        }
        if (etTemplateNextDateOfWrapUp.getText().toString().equals("")) {
            passed = false;
            set_error(etTemplateNextDateOfWrapUp);
        }
//        if (etTemplateNextSummaryRecommendationOtherIssuesAudit.getText().toString().equals("")) {
//            passed = false;
//            set_error(etTemplateNextSummaryRecommendationOtherIssuesAudit);
//        }
//        if (etTemplateNextSummaryRecommendationOtherIssuesExecutive.getText().toString().equals("")) {
//            passed = false;
//            set_error(etTemplateNextSummaryRecommendationOtherIssuesExecutive);
//        }


        if (!adapterScopeAudit.check()) {
            passed = false;
            message += "\nYou have entered duplicate scope.";
        }
        if (!adapterReference.check()) {
            passed = false;
        }
        if (!adapterPreAuditDoc.check()) {
            passed = false;
        }
        if (!adapterPresentDuringMeeting.check()) {
            passed = false;
        }
        if (!adapterPersonelMetDuring.check()) {
            passed = false;
        }
        if (!adapterCompanyBackgroundMajorChanges.check()) {
            passed = false;
        }
        if (!adapterTranslator.check()) {
            passed = false;
        }
        if (!adapterOthersIssueAudit.check()) {
            passed = false;
        }
        if (!adapterOthersIssueExecutive.check()) {
            passed = false;
        }

        if (!adapterAuditors.check()) {
            passed = false;
            Log.e("validate", "12");
            message += "\nYou have entered duplicate co-auditor.";
        }

        return passed;

//        if (templateModelScopeAudits.get(0).getScope_name().isEmpty() || templateModelScopeAudits.get(0).getScope_detail().isEmpty()) {
////            templateModelScopeAudits.get(0).getEtremarks().setError("Field Required!");
//            Log.e("validate", "templateModelScopeAudits");
//            return false;
//        }
////        if (templateModelScopeAuditInterests.size() > 0) {
////            if (templateModelScopeAuditInterests.get(0).getProduct_name().isEmpty()) {
////                Log.e("validate", "templateModelScopeAuditInterests");
////                return false;
////            }
////        }
//        if (templateModelReferences.get(0).getCertification().isEmpty() || templateModelReferences.get(0).getBody().isEmpty() ||
//                templateModelReferences.get(0).getNumber().isEmpty() || templateModelReferences.get(0).getValidity().isEmpty()) {
//            Log.e("validate", "templateModelReferences");
//            return false;
//        }
//        if (templateModelPreAuditDocs.get(0).getPreaudit().isEmpty()) {
//            Log.e("validate", "templateModelPreAuditDocs");
//            return false;
//        }
//        if (templateModelPresentDuringMeetings.get(0).getName().isEmpty() || templateModelPresentDuringMeetings.get(0).getPosition().isEmpty()) {
//            Log.e("validate", "templateModelPresentDuringMeetings");
//            return false;
//        }
//        if (templateModelPersonelMetDurings.get(0).getName().isEmpty() || templateModelPersonelMetDurings.get(0).getPosition().isEmpty()) {
//            Log.e("validate", "templateModelPersonelMetDurings");
//            return false;
//        }
//        if (templateModelDistributionLists.get(0).getDistribution().isEmpty()) {
//            Log.e("validate", "templateModelDistributionLists");
//            return false;
//        }
//        if (templateModelSummaryRecommendations.get(0).getElement().isEmpty()) {
//            Log.e("validate", "templateModelSummaryRecommendations");
//            return false;
//        }
//        if (templateModelCompanyBackgroundNames.get(0).getBgname().isEmpty()) {
//            Log.e("validate", "templateModelCompanyBackgroundNames");
//            return false;
//        }
//        if (templateModelCompanyBackgroundMajorChanges.get(0).getMajorchanges().isEmpty()) {
//            Log.e("validate", "templateModelCompanyBackgroundMajorChanges");
//            return false;
//        }
//        if (templateModelAuditorses.get(0).getName().isEmpty() || templateModelAuditorses.get(0).getPosition().isEmpty() ||
//                templateModelAuditorses.get(0).getDepartment().isEmpty()) {
//            Log.e("validate", "templateModelAuditorses");
//            return false;
//        }
//        if (templateModelTranslators.get(0).getTranslator().isEmpty()) {
//            Log.e("validate", "templateModelTranslators");
//            return false;
//        }
//
//        return true;
    }

    public boolean postData() {

        String co_auditor_id = "";
        List<TemplateModelAuditors> ltma = TemplateModelAuditors.find(TemplateModelAuditors.class, "reportid = ?", report.getReport_id());
        int counter = 0;
        for (TemplateModelAuditors tma : ltma) {
            if (++counter != ltma.size()) {
                co_auditor_id += "{\"auditor_id\": " + tma.getAuditor_id() + "},";
            } else {
                co_auditor_id += "{\"auditor_id\": " + tma.getAuditor_id() + "}";
            }
        }

        // need to discuss this shit
//        String scope = "";
//        String disposition = "";
//        counter = 0;
//        List<TemplateModelScopeAudit> tmsa = TemplateModelScopeAudit.find(TemplateModelScopeAudit.class, "reportid = ?", report.getReport_id());
//        for (TemplateModelScopeAudit t : tmsa) {
//            List<TemplateModelScopeAuditInterest> mm = TemplateModelScopeAuditInterest.find(TemplateModelScopeAuditInterest.class, "reportid = ? AND id = ?", report.getReport_id(), t.getId() + "");
//            String scope_product = "";
//            int m_counter = 0;
//            for (TemplateModelScopeAuditInterest m : mm) {
//                scope_product += "{\"product_id\":" + m.getProduct_id() + "}";
//                if (++m_counter != mm.size()) {
//                    scope_product += ",";
//                }
//            }
//            scope += "{\"scope_id\":" + t.getScope_id() + ",\"scope_detail\":\"" + t.getScope_detail() + "\",\"scope_product\":[" + scope_product + "]}";
//            //disposition += "{\"disposition_id\":" + t.getDisposition_id() + ",\"scope_product\":[{\"scope_id\":" + t.getScope_id() + ",\"scope_detail\":\"" + t.getScope_detail() + "\"}]}";
//            disposition += "{\"disposition_id\": 1,\"scope_product\":[{\"product_id\": 1,\"remarks\":\"" + t.getScope_detail() + "\"}]}";
//            if (++counter != tmsa.size()) {
//                scope += ",";
//                disposition += ",";
//            }
//        }

        String new_scope = "";
        counter = 0;
        List<TemplateModelScopeAudit> tmsa2 = TemplateModelScopeAudit.find(TemplateModelScopeAudit.class,
                "reportid = ?", report.getReport_id());
        for (TemplateModelScopeAudit t : tmsa2) {
            List<TemplateModelScopeAuditInterest> mm = TemplateModelScopeAuditInterest.find(TemplateModelScopeAuditInterest.class,
                    "reportid = ? AND auditid = ?", report.getReport_id(), counter + "");
            String scope_product = "";
            int m_counter = 0;
            for (TemplateModelScopeAuditInterest m : mm) {
                scope_product += "{\"product_id\":" + m.getProduct_id() + ",\"disposition_id\":" + m.getDisposition_id() + "}";
                if (++m_counter != mm.size()) {
                    scope_product += ",";
                }
            }

            new_scope += "{\"scope_id\":" + t.getScope_id() + ",\"scope_remarks\":\"" + t.getScope_detail() + "\",\"scope_product\":[" + scope_product + "]}";
            if (++counter != tmsa2.size()) {
                new_scope += ",";
            }
        }

        counter = 0;
        String pre_audit_documents = "";
        List<TemplateModelPreAuditDoc> tmpd = TemplateModelPreAuditDoc.find(TemplateModelPreAuditDoc.class, "reportid = ?", report.getReport_id());
        for (TemplateModelPreAuditDoc t : tmpd) {
            pre_audit_documents += "{\"document_name\":\"" + t.getPreaudit() + "\"}";
            if (++counter != tmpd.size()) {
                pre_audit_documents += ",";
            }
        }
        counter = 0;
        String references = "";
        List<TemplateModelReference> tmr = TemplateModelReference.find(TemplateModelReference.class, "reportid = ?", report.getReport_id());
        for (TemplateModelReference t : tmr) {
            references += "{\"reference_name\":\"" + t.getCertification() + "\",\"issuer\":\"" + t.getBody()
                    + "\",\"reference_no\":\"" + t.getNumber() + "\",\"validity\":\"" + t.getValidity()
                    + "\",\"issued\":\"" + t.getIssue_date() + "\"}";
            if (++counter != tmr.size()) {
                references += ",";
            }
        }

        counter = 0;
        String inspection = "";
        List<TemplateModelCompanyBackgroundMajorChanges> tmc = TemplateModelCompanyBackgroundMajorChanges.find(
                TemplateModelCompanyBackgroundMajorChanges.class, "reportid = ?", report.getReport_id());
        for (TemplateModelCompanyBackgroundMajorChanges t : tmc) {
            inspection += "{\"changes\":\"" + t.getMajorchanges() + "\"}";
            if (++counter != tmc.size()) {
                inspection += ",";
            }
        }
        counter = 0;
        String inspector = "";
        List<TemplateModelCompanyBackgroundName> tmn = TemplateModelCompanyBackgroundName.find(TemplateModelCompanyBackgroundName.class, "reportid = ?", report.getReport_id());
        for (TemplateModelCompanyBackgroundName t : tmn) {
            inspector += "{\"name\":\"" + t.getBgname() + "\"}";
            if (++counter != tmn.size()) {
                inspector += ",";
            }
        }
        counter = 0;
        String personnel = "";
        List<TemplateModelPersonelMetDuring> tmp = TemplateModelPersonelMetDuring.find(TemplateModelPersonelMetDuring.class, "reportid = ?", report.getReport_id());
        for (TemplateModelPersonelMetDuring t : tmp) {
            personnel += "{\"name\":\"" + t.getName() + "\",\"designation\":\"" + t.getPosition() + "\"}";
            if (++counter != tmp.size()) {
                personnel += ",";
            }
        }
        counter = 0;
        String activities = "";
        List<ModelReportActivities> mra = ModelReportActivities.find(ModelReportActivities.class, "reportid = ?", report.getReport_id());
        for (ModelReportActivities t : mra) {
            if (t.isCheck()) {
                String sub_activities = "";
                if (counter++ > 0) {
                    activities += ",";
                }
                int m_counter = 0;
                List<ModelReportSubActivities> mm = ModelReportSubActivities.find(ModelReportSubActivities.class, "reportid = ? AND activityid = ?", report.getReport_id(), t.getActivity_id());
                for (ModelReportSubActivities m : mm) {
                    if (m.isCheck()) {
                        if (m_counter++ > 0) {
                            sub_activities += ",";
                        }
                        sub_activities += "{\"sub_item_id\":" + m.getSub_item_id() + "}";
                    }
                }
                activities += "{\"activity_id\":\"" + t.getActivity_id() + "\",\"sub_activities\":[" + sub_activities + "]}";
            }
        }

        counter = 0;
        String question = "";
        List<ModelReportQuestion> mrq = ModelReportQuestion.find(ModelReportQuestion.class, "reportid = ? AND answerid > '0'", report.getReport_id());
        for (ModelReportQuestion t : mrq) {
            //question += "{\"question_id\":" + t.getQuestion_id() + ",\"answer_id\":" + t.getAnswer_id() + ",\"naoption_id\":\"" + t.getNaoption_id() + "\",\"category_id\":" + (t.getCategory_id().isEmpty() ? null : t.getCategory_id()) + ",\"answer_details\":\"" + t.getAnswer_details() + "\"}";
            question += "{\"question_id\":" + t.getQuestion_id() + ",\"answer_id\":" + (t.getAnswer_id().isEmpty() ? "0" : t.getAnswer_id())
                    + ",\"category_id\":" + (t.getCategory_id().isEmpty() ? null : t.getCategory_id())
                    + ",\"answer_details\":\"" + t.getAnswer_details() + "\",\"na_option\":\"" + t.getNaoption_id() + "\"}";
            if (++counter != mrq.size()) {
                question += ",";
            }
        }
        counter = 0;
        String recommendation = "";
        List<TemplateModelSummaryRecommendation> tmsr = TemplateModelSummaryRecommendation.find(TemplateModelSummaryRecommendation.class, "reportid = ? AND elementid > 0", report.getReport_id());
        for (TemplateModelSummaryRecommendation t : tmsr) {
            recommendation += "{\"element_id\":" + t.getElement_id() + ",\"recommendation\":\"" + t.getRemarks() + "\"}";
            if (++counter != tmsr.size()) {
                recommendation += ",";
            }
        }

        counter = 0;
        String otherdistribution = "";
        List<TemplateModelDistributionOthers> tmdo = TemplateModelDistributionOthers.find(TemplateModelDistributionOthers.class, "reportid = ?", report.getReport_id());
        for (TemplateModelDistributionOthers t : tmdo) {
            otherdistribution += "{\"others\":\"" + t.getDistribution_other() + "\"}";
            if (++counter != tmdo.size()) {
                otherdistribution += ",";
            }
        }

        counter = 0;
        String distribution = "";
        List<TemplateModelDistributionList> tmdl = TemplateModelDistributionList.find(TemplateModelDistributionList.class, "reportid = ?", report.getReport_id());
        for (TemplateModelDistributionList t : tmdl) {
            distribution += "{\"distribution_id\":" + t.getDistribution_id() + "}";
            if (++counter != tmdl.size()) {
                distribution += ",";
            }
        }

        counter = 0;
        String present_during_meeting = "";
        List<TemplateModelPresentDuringMeeting> tmpdm = TemplateModelPresentDuringMeeting.find(TemplateModelPresentDuringMeeting.class, "reportid = ?", report.getReport_id());
        for (TemplateModelPresentDuringMeeting t : tmpdm) {
            present_during_meeting += "{\"name\":\"" + t.getName() + "\",\"position\":\"" + t.getPosition() + "\"}";
            if (++counter != tmpdm.size()) {
                present_during_meeting += ",";
            }
        }
        counter = 0;
        String auditdate = ""; //"[{\"" + report.getAudit_date_1() + "\" ,\"" + report.getAudit_date_2() + "\"}]";
        List<ModelDateOfAudit> date = ModelDateOfAudit.find(ModelDateOfAudit.class, "reportid = ?", report.getReport_id());
        for (ModelDateOfAudit t : date) {
            auditdate += "{\"" + t.getDateOfAudit() + "\"}";
            if (++counter != date.size()) {
                auditdate += ",";
            }
        }

        counter = 0;
        String translators = ""; //"[{\"" + report.getAudit_date_1() + "\" ,\"" + report.getAudit_date_2() + "\"}]";
        List<TemplateModelTranslator> translatorList = TemplateModelTranslator.find(TemplateModelTranslator.class, "reportid = ?", report.getReport_id());
        for (TemplateModelTranslator t : translatorList) {
            translators += "{\"translator\":\"" + t.getTranslator() + "\"}";
            if (++counter != translatorList.size()) {
                translators += ",";
            }
        }
        counter = 0;
        String issue = "";
        List<TemplateModelOtherIssuesAudit> issueList = TemplateModelOtherIssuesAudit.find(TemplateModelOtherIssuesAudit.class, "reportid = ?", report.getReport_id());
        for (TemplateModelOtherIssuesAudit t : issueList) {
            issue += "{\"" + t.getOther_issues_audit() + "\"}";
            if (++counter != issueList.size()) {
                issue += ",";
            }
        }

        counter = 0;
        String issuex = "";
        List<TemplateModelOtherIssuesExecutive> issuexList = TemplateModelOtherIssuesExecutive.find(TemplateModelOtherIssuesExecutive.class, "reportid = ?", report.getReport_id());
        for (TemplateModelOtherIssuesExecutive t : issuexList) {
            issuex += "{\"" + t.getOther_issues_executive() + "\"}";
            if (++counter != issuexList.size()) {
                issuex += ",";
            }
        }

        Log.e("Bulk Edit", "token:35ced0a2f0ad35bdc9ae075ee213ea4b8e6c2839\n" +
                "cmdEvent:postInput\n" +
                "report_id:\n" +
                "report_no:\n" +
                "company_id:" + report.getCompany_id() + "\n" +
                "other_activities:" + report.getOther_activities() + "\n" +
                "audit_date:[" + auditdate + "]\n" +
                "template_id:" + report.getTemplate_id() + "\n" +
                "auditor_id:" + report.getAuditor_id() + "\n" +
                "other_issues_audit:[" + issue + "]\n" +
                "other_issues_executive:[" + issuex + "]\n" +
                "audited_areas:" + report.getAudited_areas() + "\n" +
                "areas_to_consider:" + report.getAreas_to_consider() + "\n" +
                "wrap_up_date:" + report.getWrap_date() + "\n" +
                "translator:[" + translators + "]\n" +
                "co_auditor_id:[" + co_auditor_id + "]\n" +
                "reviewer_id:" + report.getReviewer_id() + "\n" +
                "approver_id:" + report.getApprover_id() + "\n" +
                "scope:[" + new_scope + "]\n" +
                //"disposition:[" + disposition + "]\n" +
                "pre_audit_documents:[" + pre_audit_documents + "]\n" +
                "references:[" + references + "]\n" +
                "inspection:[" + inspection + "]\n" +
                "inspector:[" + inspector + "]\n" +
                "personnel:[" + personnel + "]\n" +
                "activities:[" + activities + "]\n" +
                "question:[" + question + "]\n" +
                "recommendation:[" + recommendation + "]\n" +
                "distribution:[" + distribution + "]\n" +
                "present_during_meeting:[" + present_during_meeting + "]\n" +
                //"status:0\n" +
                "version:0\n" +
                "other_distribution:[" + otherdistribution + "]\n" +
                "head_lead:" + report.getHead_lead() + "");

        apiInterface = ApiClient.getApiClientPostAuditReport().create(ApiInterface.class);
        Call<ModelAuditReportReply> modelAuditReportReplyCall = apiInterface.sendAuditReports(
                "35ced0a2f0ad35bdc9ae075ee213ea4b8e6c2839",
                "postInput",
                "",//report_id
                "",//report_no
                report.getCompany_id(),
                report.getOther_activities(),
                "[" + auditdate + "]",
                report.getTemplate_id(),
                report.getAuditor_id(),
                "[" + issue + "]",
                "[" + issuex + "]",
                report.getAudited_areas(),
                report.getAreas_to_consider(),
                report.getWrap_date(),
                "[" + translators + "]",
                "[" + co_auditor_id + "]",
                report.getReviewer_id(),
                report.getApprover_id(),
                "[" + new_scope + "]",
                //"[" + disposition + "]",
                "[" + pre_audit_documents + "]",
                "[" + references + "]",
                "[" + inspection + "]",
                "[" + inspector + "]",
                "[" + personnel + "]",
                "[" + activities + "]",
                "[" + question + "]",
                "[" + recommendation + "]",
                "[" + distribution + "]",
                "[" + present_during_meeting + "]",
                // "0",//"status",
                "0",//"version"
                "[" + otherdistribution + "]",
                report.getHead_lead()
        );
        modelAuditReportReplyCall.enqueue(new Callback<ModelAuditReportReply>() {
            @Override
            public void onResponse(Call<ModelAuditReportReply> call, Response<ModelAuditReportReply> response) {
                ModelAuditReportReply modelAuditReportReply;
                modelAuditReportReply = response.body();
                //Log.e("Result post", modelApproverInfo.getMessage());
                Log.e("Result post", modelAuditReportReply.toString() + "");
                updateDate(modelAuditReportReply.getReport_id());
                report.setReport_id(modelAuditReportReply.getReport_id());
                report.setReport_no(modelAuditReportReply.getReport_no());
                report.setStatus(modelAuditReportReply.getStatus());
                report.setModified_date(getDate());
                report.save();

                // send to co_auditors
//                apiInterface = ApiClient.getApiClientPostAuditReport().create(ApiInterface.class);
//                List<TemplateModelAuditors> ltma = TemplateModelAuditors.find(TemplateModelAuditors.class,
//                        "reportid = ?", report.getReport_id());
//                for (TemplateModelAuditors tma : ltma) {
//
//                    List<AuditorsModel> coEmail = AuditorsModel.find(AuditorsModel.class, "auditorid = ?", tma.getAuditor_id());
//                    Log.e("Email", coEmail.get(0).getEmail());
//                    Call<ModelAuditReportReply> emailsending = apiInterface.sendToCoAuditors(
//                            "35ced0a2f0ad35bdc9ae075ee213ea4b8e6c2839",
//                            "EmailtoLead",
//                            report.getReport_id(),
//                            coEmail.get(0).getEmail(),
//                            tma.getName());
//
//                    emailsending.enqueue(new Callback<ModelAuditReportReply>() {
//
//                        @Override
//                        public void onResponse(Call<ModelAuditReportReply> call, Response<ModelAuditReportReply> response) {
//                            Log.e("EmailSending", "Sent");
//                        }
//
//                        @Override
//                        public void onFailure(Call<ModelAuditReportReply> call, Throwable throwable) {
//                            Log.e("EmailSending", "Failed");
//                        }
//                    });
//
//                }

                dialogSuccess("Successfully submitted.");

            }

            @Override
            public void onFailure(Call<ModelAuditReportReply> call, Throwable t) {
                Log.e("TemplateFragment ", "OnFailure " + t.getMessage());
                Toast.makeText(context, "FAIL", Toast.LENGTH_SHORT);
            }
        });
        return true;
    }

    public void updateDate(String report_id) {
        List<TemplateModelAuditors> ltma = TemplateModelAuditors.find(TemplateModelAuditors.class, "templateid = ? AND reportid = ?", modelTemplates.getTemplateID(), report.getReport_id());
        for (TemplateModelAuditors t : ltma) {
            t.setReport_id(report_id);
            t.save();
        }

        List<TemplateModelScopeAudit> tmsa = TemplateModelScopeAudit.find(TemplateModelScopeAudit.class, "reportid = ?", report.getReport_id());
        for (TemplateModelScopeAudit t : tmsa) {
            List<TemplateModelScopeAuditInterest> mm = TemplateModelScopeAuditInterest.find(TemplateModelScopeAuditInterest.class, "reportid = ? AND id = ?", report.getReport_id(), t.getId() + "");
            for (TemplateModelScopeAuditInterest m : mm) {
                m.setReport_id(report_id);
                m.save();
            }
            t.setReport_id(report_id);
            t.save();
        }

        List<TemplateModelPreAuditDoc> tmpd = TemplateModelPreAuditDoc.find(TemplateModelPreAuditDoc.class, "reportid = ?", report.getReport_id());
        for (TemplateModelPreAuditDoc t : tmpd) {
            t.setReport_id(report_id);
            t.save();
        }

        List<TemplateModelReference> tmr = TemplateModelReference.find(TemplateModelReference.class, "reportid = ?", report.getReport_id());
        for (TemplateModelReference t : tmr) {
            t.setReport_id(report_id);
            t.save();
        }

        List<TemplateModelCompanyBackgroundMajorChanges> tmc = TemplateModelCompanyBackgroundMajorChanges.find(TemplateModelCompanyBackgroundMajorChanges.class, "reportid = ?", report.getReport_id());
        for (TemplateModelCompanyBackgroundMajorChanges t : tmc) {
            t.setReport_id(report_id);
            t.save();
        }

        List<TemplateModelCompanyBackgroundName> tmn = TemplateModelCompanyBackgroundName.find(TemplateModelCompanyBackgroundName.class, "reportid = ?", report.getReport_id());
        for (TemplateModelCompanyBackgroundName t : tmn) {
            t.setReport_id(report_id);
            t.save();
        }

        List<TemplateModelPersonelMetDuring> tmp = TemplateModelPersonelMetDuring.find(TemplateModelPersonelMetDuring.class, "reportid = ?", report.getReport_id());
        for (TemplateModelPersonelMetDuring t : tmp) {
            t.setReport_id(report_id);
            t.save();
        }

        List<ModelReportActivities> mra = ModelReportActivities.find(ModelReportActivities.class, "reportid = ?", report.getReport_id());
        for (ModelReportActivities t : mra) {
            List<ModelReportSubActivities> mm = ModelReportSubActivities.find(ModelReportSubActivities.class, "reportid = ? AND activityid = ?", report.getReport_id(), t.getActivity_id());
            for (ModelReportSubActivities m : mm) {
                m.setReport_id(report_id);
                m.save();
            }
            t.setReport_id(report_id);
            t.save();
        }

        List<ModelReportQuestion> mrq = ModelReportQuestion.find(ModelReportQuestion.class, "reportid = ?", report.getReport_id());
        for (ModelReportQuestion t : mrq) {
            t.setReport_id(report_id);
            t.save();
        }

        List<TemplateModelSummaryRecommendation> tmsr = TemplateModelSummaryRecommendation.find(TemplateModelSummaryRecommendation.class, "reportid = ?", report.getReport_id());
        for (TemplateModelSummaryRecommendation t : tmsr) {
            t.setReport_id(report_id);
            t.save();
        }

        List<TemplateModelDistributionOthers> tmdo = TemplateModelDistributionOthers.find(TemplateModelDistributionOthers.class, "reportid = ?", report.getReport_id());
        for (TemplateModelDistributionOthers t : tmdo) {
            t.setReport_id(report_id);
            t.save();
        }

        List<TemplateModelDistributionList> tmdl = TemplateModelDistributionList.find(TemplateModelDistributionList.class, "reportid = ?", report.getReport_id());
        for (TemplateModelDistributionList t : tmdl) {
            t.setReport_id(report_id);
            t.save();
        }

        List<TemplateModelPresentDuringMeeting> tmpdm = TemplateModelPresentDuringMeeting.find(TemplateModelPresentDuringMeeting.class, "reportid = ?", report.getReport_id());
        for (TemplateModelPresentDuringMeeting t : tmpdm) {
            t.setReport_id(report_id);
            t.save();
        }

        List<TemplateModelTranslator> tmt = TemplateModelTranslator.find(TemplateModelTranslator.class, "reportid = ?", report.getReport_id());
        for (TemplateModelTranslator t : tmt) {
            t.setReport_id(report_id);
            t.save();
        }

        List<ModelDateOfAudit> mdoa = ModelDateOfAudit.find(ModelDateOfAudit.class, "reportid = ?", report.getReport_id());
        for (ModelDateOfAudit t : mdoa) {
            t.setReport_id(report_id);
            t.save();
        }

        List<TemplateModelOtherIssuesAudit> issue = TemplateModelOtherIssuesAudit.find(TemplateModelOtherIssuesAudit.class, "reportid = ?", report.getReport_id());
        for (TemplateModelOtherIssuesAudit t : issue) {
            t.setReport_id(report_id);
            t.save();
        }

        List<TemplateModelOtherIssuesExecutive> issuex = TemplateModelOtherIssuesExecutive.find(TemplateModelOtherIssuesExecutive.class, "reportid = ?", report.getReport_id());
        for (TemplateModelOtherIssuesExecutive t : issuex) {
            t.setReport_id(report_id);
            t.save();
        }

    }

    public void dialogSaveDraft(String mess) {
        dialogSaveDraft = new Dialog(context);
        dialogSaveDraft.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialogSaveDraft.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogSaveDraft.setCancelable(false);
        dialogSaveDraft.setContentView(R.layout.dialog_cancel_template_confirmation);
        dialogSaveDraft.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        TextView msg = (TextView) dialogSaveDraft.findViewById(R.id.tv_message);
        Button yes = (Button) dialogSaveDraft.findViewById(R.id.btn_yes);
        Button no = (Button) dialogSaveDraft.findViewById(R.id.btn_no);

        msg.setText(mess);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //save as draft
                saveReport();
                dialogSaveDraft.dismiss();
                dialogSucSaveDraft("Successfully saved as draft");
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogSaveDraft.dismiss();
            }
        });

        dialogSaveDraft.show();
    }

    public void dialogSucSaveDraft(String mess) {
        dialogSucSaveDraft = new Dialog(context);
        dialogSucSaveDraft.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialogSucSaveDraft.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogSucSaveDraft.setCancelable(false);
        dialogSucSaveDraft.setContentView(R.layout.dialog_error_login);
        dialogSucSaveDraft.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        TextView msg = (TextView) dialogSucSaveDraft.findViewById(R.id.tv_message);
        Button ok = (Button) dialogSucSaveDraft.findViewById(R.id.btn_ok);

        msg.setText(mess);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                fragmentManager.beginTransaction()
                        .replace(R.id.fl_content, templateFragment).addToBackStack(null).commit();
                dialogSucSaveDraft.dismiss();
            }
        });

        dialogSucSaveDraft.show();
    }

    public void dialogSubmit(String mess) {
        dialogSubmit = new Dialog(context);
        dialogSubmit.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialogSubmit.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogSubmit.setCancelable(false);
        dialogSubmit.setContentView(R.layout.dialog_cancel_template_confirmation);
        dialogSubmit.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        TextView msg = (TextView) dialogSubmit.findViewById(R.id.tv_message);
        Button yes = (Button) dialogSubmit.findViewById(R.id.btn_yes);
        Button no = (Button) dialogSubmit.findViewById(R.id.btn_no);

        msg.setText(mess);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogSubmit.dismiss();
                analyzeInputs();
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogSubmit.dismiss();
            }
        });

        dialogSubmit.show();
    }

    public void dialogSubmitFailed(String mess) {
        dialogSubmitFailed = new Dialog(context);
        dialogSubmitFailed.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialogSubmitFailed.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogSubmitFailed.setCancelable(false);
        dialogSubmitFailed.setContentView(R.layout.dialog_error_login);
        dialogSubmitFailed.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        TextView msg = (TextView) dialogSubmitFailed.findViewById(R.id.tv_message);
        Button ok = (Button) dialogSubmitFailed.findViewById(R.id.btn_ok);

        msg.setText(mess);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogSubmitFailed.dismiss();
            }
        });

        dialogSubmitFailed.show();
    }


    public void dialogDeleteFromListConfirmation(String mess, final int list) {
        if (!dialogDeleteIsShowing) {
            dialogDeleteDateOfAudit = new Dialog(context);
            dialogDeleteDateOfAudit.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            dialogDeleteDateOfAudit.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialogDeleteDateOfAudit.setCancelable(false);
            dialogDeleteDateOfAudit.setContentView(R.layout.dialog_exit_confirmation);
            dialogDeleteDateOfAudit.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

            TextView msg = (TextView) dialogDeleteDateOfAudit.findViewById(R.id.tv_message);
            Button yes = (Button) dialogDeleteDateOfAudit.findViewById(R.id.btn_yes);
            Button no = (Button) dialogDeleteDateOfAudit.findViewById(R.id.btn_no);

            msg.setText(mess);

            if (list == simpleMessageDialog) {
                yes.setVisibility(View.GONE);
                no.setText("Close");
            }

            yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (list == distributionDelete) {
                        templateModelDistributionLists.remove(templateModelDistributionLists.size() - 1);
                        adapterDistributionList.notifyItemRemoved(templateModelDistributionLists.size());
                    }
                    if (list == translatorDelete) {
                        if (templateModelTranslators.size() > 1) {
                            templateModelTranslators.remove(templateModelTranslators.size() - 1);
                            adapterTranslator.notifyItemRemoved(templateModelTranslators.size());
                        } else {
                            templateModelTranslators.get(0).setTranslator("");
                            adapterTranslator.notifyItemChanged(0);
                        }
                    }

                    if (list == preAuditDocDelete) {
                        if (templateModelPreAuditDocs.size() > 1) {
                            templateModelPreAuditDocs.remove(templateModelPreAuditDocs.size() - 1);
                            adapterPreAuditDoc.notifyItemRemoved(templateModelPreAuditDocs.size());
                        } else {
                            templateModelPreAuditDocs.get(0).setPreaudit("");
                            adapterPreAuditDoc.notifyItemChanged(0);
                        }

                    }

                    if (list == distributionOthersDelete) {
                        if (templateModelDistributionOthers.size() > 1) {
                            templateModelDistributionOthers.remove(templateModelDistributionOthers.size() - 1);
                            adapterDistributionOthers.notifyItemRemoved(templateModelDistributionOthers.size());
                        } else {
                            templateModelDistributionOthers.get(0).setDistribution_other("");
                            adapterDistributionOthers.notifyItemChanged(0);
                        }
                    }

                    if (list == typeOfAuditDelete) {
                        templateModelScopeAudits.remove(templateModelScopeAudits.size() - 1);
                        adapterScopeAudit.notifyItemRemoved(templateModelScopeAudits.size());
                    }

                    if (list == personnelMetDelete) {
                        if (templateModelPersonelMetDurings.size() > 1) {
                            templateModelPersonelMetDurings.remove(templateModelPersonelMetDurings.size() - 1);
                            adapterPersonelMetDuring.notifyItemRemoved(templateModelPersonelMetDurings.size());
                        } else {
                            templateModelPersonelMetDurings.get(0).setName("");
                            templateModelPersonelMetDurings.get(0).setPosition("");
                            adapterPersonelMetDuring.notifyItemChanged(0);
                        }
                    }

                    if (list == elementsRequiringDelete) {
                        templateModelSummaryRecommendations.remove(templateModelSummaryRecommendations.size() - 1);
                        adapterSummaryRecommendation.notifyItemRemoved(templateModelSummaryRecommendations.size());
                    }

                    if (list == otherIssuesExecutiveDelete) {
                        if (templateModelOtherIssuesExecutives.size() > 1) {
                            templateModelOtherIssuesExecutives.remove(templateModelOtherIssuesExecutives.size() - 1);
                            adapterOthersIssueExecutive.notifyItemRemoved(templateModelOtherIssuesExecutives.size());
                        } else {
                            templateModelOtherIssuesExecutives.get(0).setOther_issues_executive("");
                            adapterOthersIssueExecutive.notifyItemChanged(0);
                        }
                    }

                    if (list == otherIssuesAuditDelete) {
                        if (templateModelOtherIssuesAudits.size() > 1) {
                            templateModelOtherIssuesAudits.remove(templateModelOtherIssuesAudits.size() - 1);
                            adapterOthersIssueAudit.notifyItemRemoved(templateModelOtherIssuesAudits.size());
                        } else {
                            templateModelOtherIssuesAudits.get(0).setOther_issues_audit("");
                            adapterOthersIssueAudit.notifyItemChanged(0);
                        }
                    }
                    if (list == auditorDelete) {
                        templateModelAuditorses.remove(templateModelAuditorses.size() - 1);
                        adapterAuditors.notifyItemRemoved(templateModelAuditorses.size());
                    }
                    if (list == presentDuringDelete) {
                        if (templateModelPresentDuringMeetings.size() > 1) {
                            templateModelPresentDuringMeetings.remove(templateModelPresentDuringMeetings.size() - 1);
                            adapterPresentDuringMeeting.notifyItemRemoved(templateModelPresentDuringMeetings.size());
                        } else {
                            templateModelPresentDuringMeetings.get(0).setName("");
                            templateModelPresentDuringMeetings.get(0).setPosition("");
                            adapterPresentDuringMeeting.notifyItemChanged(0);
                        }
                    }
                    if (list == majorChangesDelete) {
                        if (templateModelCompanyBackgroundMajorChanges.size() > sitemajorchangescount) {
                            if (templateModelCompanyBackgroundMajorChanges.size() > 1) {
                                templateModelCompanyBackgroundMajorChanges.remove(templateModelCompanyBackgroundMajorChanges.size() - 1);
                                adapterCompanyBackgroundMajorChanges.notifyItemRemoved(templateModelCompanyBackgroundMajorChanges.size());
                            } else {
                                templateModelCompanyBackgroundMajorChanges.get(0).setMajorchanges("");
                                adapterCompanyBackgroundMajorChanges.notifyItemChanged(0);
                            }
                        }
                    }
                    dialogDeleteIsShowing = false;
                    dialogDeleteDateOfAudit.dismiss();
                }
            });

            no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogDeleteIsShowing = false;
                    dialogDeleteDateOfAudit.dismiss();
                }
            });

            dialogDeleteIsShowing = true;
            dialogDeleteDateOfAudit.show();
        }
    }

    public String getDate() {
        String dateStr = "";
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        dateStr = dateFormat.format(date);
        return dateStr;
    }

    Dialog dialogSuccess;

    public void dialogSuccess(String mess) {
        dialogSuccess = new Dialog(context);
        dialogSuccess.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialogSuccess.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogSuccess.setCancelable(false);
        dialogSuccess.setContentView(R.layout.dialog_error_login);
        dialogSuccess.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        TextView msg = (TextView) dialogSuccess.findViewById(R.id.tv_message);
        Button ok = (Button) dialogSuccess.findViewById(R.id.btn_ok);

        msg.setText(mess);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();

                fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                fragmentManager.beginTransaction()
                        .replace(R.id.fl_content, templateFragment).addToBackStack(null).commit();

                dialogSuccess.dismiss();
            }
        });

        dialogSuccess.show();
    }

}