package com.unilab.gmp.fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.paolorotolo.expandableheightlistview.ExpandableHeightListView;
import com.unilab.gmp.R;
import com.unilab.gmp.adapter.ActivityAdapter;
import com.unilab.gmp.adapter.TemplateElementAdapter;
import com.unilab.gmp.adapter.templates.AdapterAuditors;
import com.unilab.gmp.adapter.templates.AdapterCompanyBackgroundMajorChanges;
import com.unilab.gmp.adapter.templates.AdapterCompanyBackgroundName;
import com.unilab.gmp.adapter.templates.AdapterDistributionList;
import com.unilab.gmp.adapter.templates.AdapterDistributionOthers;
import com.unilab.gmp.adapter.templates.AdapterInspectionDate;
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
import com.unilab.gmp.utility.StartDatePicker;
import com.unilab.gmp.utility.Variable;

import java.util.ArrayList;
import java.util.Calendar;
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

public class NextSelectedAuditReportFragment extends Fragment {

    Unbinder unbinder;
    Context context;

    @BindView(R.id.btn_cancel)
    Button btnCancel;
    @BindView(R.id.btn_save_draft)
    Button btnSaveDraft;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.lv_template_next_activities_carried)
    ExpandableHeightListView lvTemplateNextActivitiesCarried;
    @BindView(R.id.et_template_next_activity_carried)
    EditText etTemplateNextActivityCarried;
    @BindView(R.id.btn_template_next_scope_audit_add)
    Button btnTemplateNextScopeAuditAdd;
    @BindView(R.id.btn_template_next_scope_audit_delete)
    Button btnTemplateNextScopeAuditDelete;
    @BindView(R.id.lv_template_next_scope_audit)
    ExpandableHeightListView lvTemplateNextScopeAudit;
    /*@BindView(R.id.btn_template_next_scope_audit_interest_add)
    Button btnTemplateNextScopeAuditInterestAdd;
    @BindView(R.id.btn_template_next_scope_audit_interest_delete)
    Button btnTemplateNextScopeAuditInterestDelete;
    @BindView(R.id.lv_template_next_scope_audit_interest)
    ExpandableHeightListView lvTemplateNextScopeAuditInterest;*/
    @BindView(R.id.btn_template_next_reference_add)
    Button btnTemplateNextReferenceAdd;
    @BindView(R.id.btn_template_next_reference_delete)
    Button btnTemplateNextReferenceDelete;
    @BindView(R.id.lv_template_next_reference)
    ExpandableHeightListView lvTemplateNextReference;
    @BindView(R.id.lv_template_next_pre_audit_doc)
    ExpandableHeightListView lvTemplateNextPreAuditDoc;
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
    ExpandableHeightListView lvTemplateNextPresentDuringMeeting;
    @BindView(R.id.btn_template_next_personnel_inspection_add)
    Button btnTemplateNextPersonnelInspectionAdd;
    @BindView(R.id.btn_template_next_personnel_inspection_delete)
    Button btnTemplateNextPersonnelInspectionDelete;
    @BindView(R.id.lv_template_next_personnel_inspection)
    ExpandableHeightListView lvTemplateNextPersonnelInspection;
    @BindView(R.id.lv_template_next_distribution_list)
    ExpandableHeightListView lvTemplateNextDistributionList;
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
    ExpandableHeightListView lvTemplateNextSummaryRecommendation;
    @BindView(R.id.et_template_next_summary_recommendation_audit_close_date)
    EditText etTemplateNextSummaryRecommendationAuditCloseDate;
    @BindView(R.id.et_template_next_summary_recommendation_other_issues_audit)
    EditText etTemplateNextSummaryRecommendationOtherIssuesAudit;
    @BindView(R.id.et_template_next_summary_recommendation_other_issues_executive)
    EditText etTemplateNextSummaryRecommendationOtherIssuesExecutive;
    @BindView(R.id.et_template_next_company_background_history)
    EditText etTemplateNextCompanyBackgroundHistory;
    @BindView(R.id.lv_template_next_company_background_name)
    ExpandableHeightListView lvTemplateNextCompanyBackgroundName;
    @BindView(R.id.ll_template_next_company_background_name)
    LinearLayout llTemplateNextCompanyBackgroundName;
    @BindView(R.id.btn_template_next_company_background_inspector_name_add)
    Button btnTemplateNextCompanyBackgroundInspectorNameAdd;
    @BindView(R.id.btn_template_next_company_background_inspector_name_delete)
    Button btnTemplateNextCompanyBackgroundInspectorNameDelete;
    @BindView(R.id.lv_template_next_company_background_major_changes)
    ExpandableHeightListView lvTemplateNextCompanyBackgroundMajorChanges;
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
    ExpandableHeightListView lvTemplateNextAuditors;
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
    ExpandableHeightListView lvTemplateNextTranslator;
    @BindView(R.id.ll_template_next_translator)
    LinearLayout llTemplateNextTranslator;
    @BindView(R.id.btn_template_next_translator_add)
    Button btnTemplateNextTranslatorAdd;
    @BindView(R.id.btn_template_next_translator_delete)
    Button btnTemplateNextTranslatorDelete;
    @BindView(R.id.lv_template_next_other_distribution)
    ExpandableHeightListView lvTemplateNextOtherDistribution;
    @BindView(R.id.cb_template_next_reviewer)
    CheckBox cbTemplateNextReviewer;
    @BindView(R.id.btn_prev)
    Button btnPrev;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.lv_template_next_company_background_inspection_date)
    ExpandableHeightListView lvTemplateNextCompanyBackgroundInspectionDate;

    Dialog dialogCancelTemplate;
    Dialog dialogSaveDraft;
    Dialog dialogSucSaveDraft;
    Dialog dialogSubmit;
    Dialog dialogSubmitFailed;

    TemplateFragment templateFragment;
    AuditReportFragment auditReportFragment;

    ModelTemplates modelTemplates;
    ActivityAdapter activityAdapter;
    TemplateElementAdapter templateElementAdapter;

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

    AdapterScopeAudit adapterScopeAudit;
    //    AdapterScopeAuditInterest adapterScope;
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

    List<AuditorsModel> auditorsModels;
    List<ReviewerModel> reviewerModels;
    List<ApproverModel> approverModels;

    int year, month, day;
    Calendar dateSelected = Calendar.getInstance();
    Calendar currentTime = Calendar.getInstance();
    int useDate;
    ModelAuditReports report;

    ApiInterface apiInterface;
    SharedPreferenceManager sharedPref;
    String reviewer_id = "", approver_id = "";

    ModelAuditReportReply modelAuditReportReply;
    SelectedAuditReportFragment selectedAuditReportFragment;
    View rootView;

    public NextSelectedAuditReportFragment(ModelTemplates modelTemplates, ModelAuditReports report,
                                           TemplateElementAdapter templateElementAdapter, SelectedAuditReportFragment selectedAuditReportFragment) {
        this.modelTemplates = modelTemplates;
        this.report = report;
        this.templateElementAdapter = templateElementAdapter;
        this.selectedAuditReportFragment = selectedAuditReportFragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (this.rootView != null) {
            ButterKnife.bind(this, this.rootView);
            unbinder = ButterKnife.bind(this, this.rootView);
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // Do something after 5s = 5000ms
                    pDialog.dismiss();
                }
            }, 500);
            return this.rootView;
        }
        View rootView = inflater.inflate(R.layout.fragment_template_selected_next, container, false);
        ButterKnife.bind(this, rootView);
        unbinder = ButterKnife.bind(this, rootView);
        context = getActivity();

        Variable.menu = true;
        Variable.onTemplate = true;
        Variable.onAudit = true;
        templateFragment = new TemplateFragment();
        auditReportFragment = new AuditReportFragment();
        tvTitle.setText("AUDIT REPORT");

        year = currentTime.get(Calendar.YEAR);
        month = currentTime.get(Calendar.MONTH);
        day = currentTime.get(Calendar.DAY_OF_MONTH);

        activityAdapter = new ActivityAdapter(context, find(ModelTemplateActivities.class, "templateid = ?", report.getTemplate_id()), report.getReport_id());
        lvTemplateNextActivitiesCarried.setAdapter(activityAdapter);
        lvTemplateNextActivitiesCarried.setExpanded(true);


        etTemplateNextAuditedArea.setText(report.getAudited_areas());
        etTemplateNextNotAuditedArea.setText(report.getAreas_to_consider());
        etTemplateNextDateOfWrapUp.setText(report.getWrap_date());
//        Log.i("DATE FORMAT", report.getDate_of_wrap() + " ");
//        if (report.getDate_of_wrap() != null) {
//            etTemplateNextDateOfWrapUp.setText("");
//        }
        if (report.getAudit_close_date() != null) {
            etTemplateNextSummaryRecommendationAuditCloseDate.setText
                    (DateTimeUtils.parseDateMonthToWord(report.getAudit_close_date()));
        }
        etTemplateNextSummaryRecommendationOtherIssuesAudit.setText(report.getOther_issues());
        etTemplateNextSummaryRecommendationOtherIssuesExecutive.setText(report.getOther_issues_executive());
        //if (report.getP_inspection_date_1() != null || report.getP_inspection_date_1() != "") {
//            etTemplateNextCompanyBackgroundDateFrom.setText(DateTimeUtils.parseDateMonthToWord(report.getP_inspection_date_1()));
        //
        //}
        //if (report.getP_inspection_date_2() != null || report.getP_inspection_date_2() != "") {
//            etTemplateNextCompanyBackgroundDateTo.setText(DateTimeUtils.parseDateMonthToWord(report.getP_inspection_date_2()));
        //}
        etTemplateNextActivityCarried.setText(report.getOther_activities());

        //--- Lead Auditor setting start
        auditorsModels = AuditorsModel.listAll(AuditorsModel.class);
        List<String> scopeAuditList = new ArrayList<>();
        int scopeAuditListselected = 0;

        for (int x = 0; x < auditorsModels.size(); x++) {
            scopeAuditList.add(auditorsModels.get(x).getFname() + " " + auditorsModels.get(x).getMname()
                    + " " + auditorsModels.get(x).getLname());
            if (report.getAuditor_id().equals(auditorsModels.get(x).getAuditor_id())) {
                scopeAuditListselected = x;
            }

        }
        ArrayAdapter<String> scopeAuditAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, scopeAuditList);
        sTemplateNextAuditorLeadName.setEnabled(false);
        sTemplateNextAuditorLeadName.setClickable(false);
        sTemplateNextAuditorLeadName.setAdapter(scopeAuditAdapter);
        sTemplateNextAuditorLeadName.setSelection(scopeAuditListselected);
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
        reviewerModels = ReviewerModel.listAll(ReviewerModel.class);
        int rev = 0;
        final List<String> reviewerList = new ArrayList<>();
        reviewer_id = report.getReviewer_id();
        for (int x = 0; x < reviewerModels.size(); x++) {
            Log.e("reviewerList", reviewerModels.get(x).getReviewer_id());
            reviewerList.add(reviewerModels.get(x).getFirstname() + " " + reviewerModels.get(x).getMiddlename()
                    + " " + reviewerModels.get(x).getLastname());
            if (reviewerModels.get(x).getReviewer_id().equals(reviewer_id)) {
                rev = x;
            }
        }
        ArrayAdapter<String> reviewerAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, reviewerList);
        sTemplateNextReviewerName.setAdapter(reviewerAdapter);
        sTemplateNextReviewerName.setSelection(rev);

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

        cbTemplateNextReviewer.setChecked(report.isReviewerChecked());
        sTemplateNextReviewerName.setEnabled(!report.isReviewerChecked());
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
        approverModels = ApproverModel.listAll(ApproverModel.class);
        List<String> approverList = new ArrayList<>();
        int app = 0;
        approver_id = report.getApprover_id();
        for (int x = 0; x < approverModels.size(); x++) {
            approverList.add(approverModels.get(x).getFirstname() + " " + approverModels.get(x).getMiddlename()
                    + " " + approverModels.get(x).getLastname());
            if (approverModels.get(x).getApprover_id().equals(approver_id)) {
                app = x;
            }
        }
        ArrayAdapter<String> approverAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, approverList);
        sTemplateNextApproverName.setAdapter(approverAdapter);
        sTemplateNextApproverName.setSelection(app);

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
        templateModelScopeAudits = TemplateModelScopeAudit.find(TemplateModelScopeAudit.class, "reportid = ?", report.getReport_id());
        adapterScopeAudit = new AdapterScopeAudit(templateModelScopeAudits, context, modelTemplates.getCompany_id(), null, this);
        lvTemplateNextScopeAudit.setAdapter(adapterScopeAudit);
        lvTemplateNextScopeAudit.setExpanded(true);
//        templateModelScopeAudits.addAll(TemplateModelScopeAudit.find(TemplateModelScopeAudit.class, "templateid = ? AND reportid = ?", report.getTemplate_id(), report.getReport_id()));
        if (templateModelScopeAudits.size() > 0) {
            adapterScopeAudit.notifyDataSetChanged();
        } else {
            addScopeAuditType();
        }

        // --- Audit Scope Interest
        /*templateModelScopeAuditInterests = new ArrayList<>();
        adapterScope = new AdapterScopeAuditInterest(templateModelScopeAuditInterests, context, modelTemplates.getCompany_id());
        lvTemplateNextScopeAuditInterest.setAdapter(adapterScope);
        lvTemplateNextScopeAuditInterest.setExpanded(true);
        templateModelScopeAuditInterests.addAll(TemplateModelScopeAuditInterest.find(TemplateModelScopeAuditInterest.class, "templateid = ? AND reportid = ?", report.getTemplate_id(), report.getReport_id()));
        if (templateModelScopeAuditInterests.size() > 0) {
            adapterScope.notifyDataSetChanged();
        } else {
            addScopeAuditTypeInterest();
        }*/

        // --- Reference
        templateModelReferences = new ArrayList<>();
        adapterReference = new AdapterReference(templateModelReferences, context);
        lvTemplateNextReference.setAdapter(adapterReference);
        lvTemplateNextReference.setExpanded(true);
        templateModelReferences.addAll(TemplateModelReference.find(TemplateModelReference.class, "reportid = ?", report.getReport_id()));
        if (templateModelReferences.size() > 0) {
            adapterReference.notifyDataSetChanged();
        } else {
            addReference();
        }

        // --- Pre Audit Doc
        templateModelPreAuditDocs = new ArrayList<>();
        adapterPreAuditDoc = new AdapterPreAuditDoc(templateModelPreAuditDocs, context);
        lvTemplateNextPreAuditDoc.setAdapter(adapterPreAuditDoc);
        lvTemplateNextPreAuditDoc.setExpanded(true);
        templateModelPreAuditDocs.addAll(TemplateModelPreAuditDoc.find(TemplateModelPreAuditDoc.class, " reportid = ?", report.getReport_id()));
        if (templateModelPreAuditDocs.size() > 0) {
            adapterPreAuditDoc.notifyDataSetChanged();
        } else {
            addPreAuditDoc();
        }
        // --- Present during meeting
        templateModelPresentDuringMeetings = new ArrayList<>();
        adapterPresentDuringMeeting = new AdapterPresentDuringMeeting(templateModelPresentDuringMeetings, context);
        lvTemplateNextPresentDuringMeeting.setAdapter(adapterPresentDuringMeeting);
        lvTemplateNextPresentDuringMeeting.setExpanded(true);
        templateModelPresentDuringMeetings.addAll(TemplateModelPresentDuringMeeting.find(TemplateModelPresentDuringMeeting.class, "reportid = ?", report.getReport_id()));
        if (templateModelPresentDuringMeetings.size() > 0) {
            adapterPresentDuringMeeting.notifyDataSetChanged();
        } else {
            addPresentDuringMeeting();
        }
        // --- Personel Met During
        templateModelPersonelMetDurings = new ArrayList<>();
        adapterPersonelMetDuring = new AdapterPersonelMetDuring(templateModelPersonelMetDurings, context);
        lvTemplateNextPersonnelInspection.setAdapter(adapterPersonelMetDuring);
        lvTemplateNextPersonnelInspection.setExpanded(true);
        templateModelPersonelMetDurings.addAll(TemplateModelPersonelMetDuring.find(TemplateModelPersonelMetDuring.class, "reportid = ?", report.getReport_id()));
        if (templateModelPersonelMetDurings.size() > 0) {
            adapterPersonelMetDuring.notifyDataSetChanged();
        } else {
            addPersonelMet();
        }
        // --- Distribution List
        templateModelDistributionLists = new ArrayList<>();
        adapterDistributionList = new AdapterDistributionList(templateModelDistributionLists, context);
        lvTemplateNextDistributionList.setAdapter(adapterDistributionList);
        lvTemplateNextDistributionList.setExpanded(true);
        templateModelDistributionLists.addAll(TemplateModelDistributionList.find(TemplateModelDistributionList.class, "reportid = ?", report.getReport_id()));
        if (templateModelDistributionLists.size() > 0) {
            adapterDistributionList.notifyDataSetChanged();
        } else {
            addDistribution();
        }

        //--- Distribution others

        templateModelDistributionOthers = new ArrayList<>();
        adapterDistributionOthers = new AdapterDistributionOthers(templateModelDistributionOthers, context);
        lvTemplateNextOtherDistribution.setAdapter(adapterDistributionOthers);
        lvTemplateNextOtherDistribution.setExpanded(true);
        templateModelDistributionOthers.addAll(TemplateModelDistributionOthers.find(TemplateModelDistributionOthers.class, "reportid = ?", report.getReport_id()));
        if (templateModelDistributionOthers.size() > 0) {
            adapterDistributionOthers.notifyDataSetChanged();
        } else {
            addDistributionOthers();
        }

        // --- Reocommendation
        templateModelSummaryRecommendations = new ArrayList<>();
        adapterSummaryRecommendation = new AdapterSummaryRecommendation(templateModelSummaryRecommendations, context, report.getTemplate_id());
        lvTemplateNextSummaryRecommendation.setAdapter(adapterSummaryRecommendation);
        lvTemplateNextSummaryRecommendation.setExpanded(true);
        templateModelSummaryRecommendations.addAll(TemplateModelSummaryRecommendation.find(TemplateModelSummaryRecommendation.class, "reportid = ?", report.getReport_id()));
        if (templateModelSummaryRecommendations.size() > 0) {
            adapterSummaryRecommendation.notifyDataSetChanged();
        } else {
            addRecommendation();
        }
        // ---
        templateModelCompanyBackgroundNames = new ArrayList<>();
        templateModelCompanyBackgroundNames.addAll(TemplateModelCompanyBackgroundName.find(TemplateModelCompanyBackgroundName.class, "companyid = ?", report.getCompany_id()));
        adapterCompanyBackgroundName = new AdapterCompanyBackgroundName(templateModelCompanyBackgroundNames, context, templateModelCompanyBackgroundNames.size());
        templateModelCompanyBackgroundNames.addAll(TemplateModelCompanyBackgroundName.find(TemplateModelCompanyBackgroundName.class, "reportid = ?", report.getReport_id()));
        lvTemplateNextCompanyBackgroundName.setAdapter(adapterCompanyBackgroundName);
        lvTemplateNextCompanyBackgroundName.setExpanded(true);
        if (templateModelCompanyBackgroundNames.size() > 0) {
            adapterCompanyBackgroundName.notifyDataSetChanged();
        } else {
            addBackgroundName();
        }
        // ---
        templateModelCompanyBackgroundMajorChanges = new ArrayList<>();
        templateModelCompanyBackgroundMajorChanges.addAll(TemplateModelCompanyBackgroundMajorChanges.find(TemplateModelCompanyBackgroundMajorChanges.class, "companyid = ?", report.getCompany_id()));
        adapterCompanyBackgroundMajorChanges = new AdapterCompanyBackgroundMajorChanges(templateModelCompanyBackgroundMajorChanges, context,templateModelCompanyBackgroundMajorChanges.size());
        templateModelCompanyBackgroundMajorChanges.addAll(TemplateModelCompanyBackgroundMajorChanges.find(TemplateModelCompanyBackgroundMajorChanges.class, "reportid = ?", report.getReport_id()));
        lvTemplateNextCompanyBackgroundMajorChanges.setAdapter(adapterCompanyBackgroundMajorChanges);
        lvTemplateNextCompanyBackgroundMajorChanges.setExpanded(true);
        if (templateModelCompanyBackgroundMajorChanges.size() > 0) {
            adapterCompanyBackgroundMajorChanges.notifyDataSetChanged();
        } else {
            addMajorChanges();
        }
        // ---
        templateModelAuditorses = new ArrayList<>();
        adapterAuditors = new AdapterAuditors(templateModelAuditorses, context);
        lvTemplateNextAuditors.setAdapter(adapterAuditors);
        lvTemplateNextAuditors.setExpanded(true);
        templateModelAuditorses.addAll(TemplateModelAuditors.find(TemplateModelAuditors.class, "reportid = ?", report.getReport_id()));
        if (templateModelAuditorses.size() > 0) {
            adapterAuditors.notifyDataSetChanged();
        } else {
            addAuditors();
        }
        // ---
        templateModelTranslators = new ArrayList<>();
        adapterTranslator = new AdapterTranslator(templateModelTranslators, context);
        lvTemplateNextTranslator.setAdapter(adapterTranslator);
        lvTemplateNextTranslator.setExpanded(true);
        templateModelTranslators.addAll(TemplateModelTranslator.find(TemplateModelTranslator.class, "reportid = ?", report.getReport_id()));
        if (templateModelTranslators.size() > 0) {
            adapterTranslator.notifyDataSetChanged();
        } else {
            addTranslator();
        }
        // ---
//        List<ModelTemplates> l = ModelTemplates.find(ModelTemplates.class, "template_id = ?", report.getTemplate_id());
//        etTemplateNextActivityCarried.setText(l.get(0).getOther_activities());
//        etTemplateNextActivityCarried.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                modelTemplates.setOther_activities(etTemplateNextActivityCarried.getText().toString());
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });

        etTemplateNextActivityCarried.setText(report.getOther_activities());

        List<ModelCompany> mc = ModelCompany.find(ModelCompany.class, "companyid = ?", modelTemplates.getCompany_id());
        if (mc.size() > 0) {
            etTemplateNextCompanyBackgroundHistory.setText(mc.get(0).getBackground());
        }

        //adapter inspection date call and set
        List<ModelSiteDate> modelSiteDates = ModelSiteDate.find(ModelSiteDate.class, "companyid = ?", modelTemplates.getCompany_id());
        AdapterInspectionDate adapterInspectionDate = new AdapterInspectionDate(context, modelSiteDates);
        lvTemplateNextCompanyBackgroundInspectionDate.setAdapter(adapterInspectionDate);
        lvTemplateNextCompanyBackgroundInspectionDate.setExpanded(true);



        /*progress dialog dismiss*/
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

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
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
            R.id.btn_template_next_other_distribution_add, R.id.btn_template_next_other_distribution_delete,
            R.id.et_template_next_summary_recommendation_audit_close_date, R.id.btn_prev})
    public void onViewClicked(View view) {
        Log.e("clicked", "button : " + view.getId());
        FragmentManager fragmentManager = getFragmentManager();
        switch (view.getId()) {
            case R.id.btn_prev:
                /*FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.fl_content, selectedAuditReportFragment, "TemplateSelect");
                ft.commit();*/

                fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                fragmentManager.beginTransaction()
                        .replace(R.id.fl_content, selectedAuditReportFragment).addToBackStack(null).commit();
                break;
            case R.id.btn_cancel:
                dialogCancelTemplate();
                break;
            case R.id.btn_save_draft:
                dialogSaveDraft("Are you sure you want to save as draft?");
                //save();
                break;
            case R.id.btn_submit:
                dialogSubmit("Are you sure you want to submit?");
                break;
            case R.id.btn_template_next_scope_audit_add:
                addScopeAuditType();
                break;
            case R.id.btn_template_next_scope_audit_delete:
                if (templateModelScopeAudits.size() > 1) {
                    templateModelScopeAudits.remove(templateModelScopeAudits.size() - 1);
                    adapterScopeAudit.notifyDataSetChanged();
                }
                break;
            /*case R.id.btn_template_next_scope_audit_interest_add:
                addScopeAuditTypeInterest();
                break;
            case R.id.btn_template_next_scope_audit_interest_delete:
                if (templateModelScopeAuditInterests.size() > 1) {
                    templateModelScopeAuditInterests.remove(templateModelScopeAuditInterests.size() - 1);
                    adapterScope.notifyDataSetChanged();
                }
                break;*/
            case R.id.btn_template_next_reference_add:
                addReference();
                break;
            case R.id.btn_template_next_reference_delete:
                if (templateModelReferences.size() > 1) {
                    templateModelReferences.remove(templateModelReferences.size() - 1);
                    adapterReference.notifyDataSetChanged();
                }
                break;
            case R.id.btn_template_next_pre_audit_doc_add:
                addPreAuditDoc();
                break;
            case R.id.btn_template_next_pre_audit_doc_delete:
                if (templateModelPreAuditDocs.size() > 1) {
                    templateModelPreAuditDocs.remove(templateModelPreAuditDocs.size() - 1);
                    adapterPreAuditDoc.notifyDataSetChanged();
                }
                break;
            case R.id.btn_template_next_present_close_up_add:
                addPresentDuringMeeting();
                break;
            case R.id.btn_template_next_present_close_up_delete:
                if (templateModelPresentDuringMeetings.size() > 1) {
                    templateModelPresentDuringMeetings.remove(templateModelPresentDuringMeetings.size() - 1);
                    adapterPresentDuringMeeting.notifyDataSetChanged();
                }
                break;
            case R.id.btn_template_next_personnel_inspection_add:
                addPersonelMet();
                break;
            case R.id.btn_template_next_personnel_inspection_delete:
                if (templateModelPersonelMetDurings.size() > 1) {
                    templateModelPersonelMetDurings.remove(templateModelPersonelMetDurings.size() - 1);
                    adapterPersonelMetDuring.notifyDataSetChanged();
                }
                break;
            case R.id.btn_template_next_distribution_add:
                addDistribution();
                break;
            case R.id.btn_template_next_distribution_delete:
                if (templateModelDistributionLists.size() > 1) {
                    templateModelDistributionLists.remove(templateModelDistributionLists.size() - 1);
                    adapterDistributionList.notifyDataSetChanged();
                }
                break;
            case R.id.btn_template_next_other_distribution_add:
                addDistributionOthers();
                break;
            case R.id.btn_template_next_other_distribution_delete:
                if (templateModelDistributionOthers.size() > 1) {
                    templateModelDistributionOthers.remove(templateModelDistributionOthers.size() - 1);
                    adapterDistributionOthers.notifyDataSetChanged();
                }
                break;
            case R.id.btn_template_next_summary_recommendation_add:
                addRecommendation();
                break;
            case R.id.btn_template_next_summary_recommendation_delete:
                if (templateModelSummaryRecommendations.size() > 1) {
                    templateModelSummaryRecommendations.remove(templateModelSummaryRecommendations.size() - 1);
                    adapterSummaryRecommendation.notifyDataSetChanged();
                }
                break;
            case R.id.btn_template_next_company_background_inspector_name_add:
                addBackgroundName();
                break;
            case R.id.btn_template_next_company_background_inspector_name_delete:
                if (templateModelCompanyBackgroundNames.size() > 1) {
                    templateModelCompanyBackgroundNames.remove(templateModelCompanyBackgroundNames.size() - 1);
                    adapterCompanyBackgroundName.notifyDataSetChanged();
                }
                break;
            case R.id.btn_template_next_company_background_major_changes_add:
                addMajorChanges();
                break;
            case R.id.btn_template_next_company_background_major_changes_delete:
                if (templateModelCompanyBackgroundMajorChanges.size() > 1) {
                    templateModelCompanyBackgroundMajorChanges.remove(templateModelCompanyBackgroundMajorChanges.size() - 1);
                    adapterCompanyBackgroundMajorChanges.notifyDataSetChanged();
                }
                break;
            case R.id.btn_template_next_auditor_add:
                addAuditors();
                break;
            case R.id.btn_template_next_auditor_delete:
                if (templateModelAuditorses.size() > 1) {
                    templateModelAuditorses.remove(templateModelAuditorses.size() - 1);
                    adapterAuditors.notifyDataSetChanged();
                }
                break;
            case R.id.btn_template_next_translator_add:
                addTranslator();
                break;
            case R.id.btn_template_next_translator_delete:
                if (templateModelTranslators.size() > 1) {
                    templateModelTranslators.remove(templateModelTranslators.size() - 1);
                    adapterTranslator.notifyDataSetChanged();
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
        }
    }

    private void callDatePicker(EditText editText) {
        StartDatePicker datePicker = new StartDatePicker(editText);
        datePicker.show(((AppCompatActivity) context).getSupportFragmentManager(), "datePicker");
    }

    private void analyzeInputs() {
        ProgressDialog loginDialog = new ProgressDialog(context);
        loginDialog.setCanceledOnTouchOutside(false);
        loginDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        loginDialog.setMax(2);

        if (validate()) {
            if (isNetworkConnected()) {
                save();
                sharedPref = new SharedPreferenceManager(context);
                String email = sharedPref.getStringData("EMAIL");
                String password = sharedPref.getStringData("PASSWORD");
                Log.i("ERROR", "POSTASYNC 2");
                new PostAsync(context, loginDialog, email, password, Glovar.POST_AUDIT, NextSelectedAuditReportFragment.this, null).execute();
            } else {
                //dialog if save as draft or self destruct
                dialogSaveDraft("No internet connection. Would you like to save as draft?");
            }
        } else {
            dialogSubmitFailed("Please fill up all the required fields.");
        }
    }

    private void save() {
        ModelAuditReports mar = ModelAuditReports.find(ModelAuditReports.class, "reportid = ?", report.getReport_id()).get(0);

        mar.setCompany_id(modelTemplates.getCompany_id());
        mar.setAudit_date_1(modelTemplates.getAudit_date_1());
        mar.setAudit_date_2(modelTemplates.getAudit_date_2());
//        mar.setP_inspection_date_1(DateTimeUtils.parseDateMonthToDigit(etTemplateNextCompanyBackgroundDateFrom.getText().toString()));
//        mar.setP_inspection_date_2(DateTimeUtils.parseDateMonthToDigit(etTemplateNextCompanyBackgroundDateTo.getText().toString()));
        mar.setAuditor_id(auditorsModels.get(sTemplateNextAuditorLeadName.getSelectedItemPosition()).getAuditor_id());
        mar.setReviewer_id(reviewer_id);
        mar.setApprover_id(approver_id);
        mar.setWrap_date(etTemplateNextDateOfWrapUp.getText().toString());
        mar.setReviewerChecked(modelTemplates.isReviewerChecked());

        adapterAuditors.save(mar.getReport_id());

        ModelReportReviewer mrr = new ModelReportReviewer();
        mrr.setReport_id(mar.getReport_id());
        mrr.setReviewer_id(reviewerModels.get(sTemplateNextApproverName.getSelectedItemPosition()).getReviewer_id());
        mrr.save();

        ModelReportApprover mra = new ModelReportApprover();
        mra.setReport_id(mar.getReport_id());
        mra.setApprover_id(approverModels.get(sTemplateNextApproverName.getSelectedItemPosition()).getApprover_id());
        mra.save();

        mar.setAudit_close_date(DateTimeUtils.parseDateMonthToDigit(
                etTemplateNextSummaryRecommendationAuditCloseDate.getText().toString()));
        mar.setOther_issues(etTemplateNextSummaryRecommendationOtherIssuesAudit.getText().toString());
        mar.setAudited_areas(etTemplateNextAuditedArea.getText().toString());
        mar.setAreas_to_consider(etTemplateNextNotAuditedArea.getText().toString());
//        mar.setDate_of_wrap(DateTimeUtils.parseDateMonthToDigit(etTemplateNextDateOfWrapUp.getText().toString()));
//        mar.setTranslator(adapterTranslator.save(mar.getReport_id()));//translator
        adapterTranslator.save(mar.getReport_id());//translator


        adapterScopeAudit.save(mar.getReport_id());
        adapterPreAuditDoc.save(mar.getReport_id());
        adapterReference.save(mar.getReport_id());
        adapterCompanyBackgroundMajorChanges.save(mar.getReport_id());//inspection
        adapterCompanyBackgroundName.save(mar.getReport_id());//inspector
        adapterPersonelMetDuring.save(mar.getReport_id());
        activityAdapter.save(mar.getReport_id());
        templateElementAdapter.save(mar.getReport_id());

        ModelDateOfAudit.deleteAll(ModelDateOfAudit.class, "reportid = ?", mar.getReport_id());
        for (ModelDateOfAudit t : modelTemplates.getModelDateOfAudits()) {
            t.setReport_id(mar.getReport_id());
            t.save();
        }

        mar.setOther_activities(etTemplateNextActivityCarried.getText().toString());
        mar.setOther_issues_executive(etTemplateNextSummaryRecommendationOtherIssuesExecutive.getText().toString());

        // adapterScope.save(report_id);//w
        adapterPresentDuringMeeting.save(mar.getReport_id());//w
        adapterDistributionList.save(mar.getReport_id());//w
        adapterDistributionOthers.save(mar.getReport_id());

        adapterSummaryRecommendation.save(mar.getReport_id());//(y)

        mar.save();
        report = mar;
    }

    private void addScopeAuditType() {
        if (adapterScopeAudit.getTypeAuditSize() > templateModelScopeAudits.size()) {
            TemplateModelScopeAudit t = new TemplateModelScopeAudit();
            t.setScope_detail("");
            templateModelScopeAudits.add(t);
            adapterScopeAudit.notifyDataSetChanged();
        }
    }

//    private void addScopeAuditTypeInterest() {
//        if (adapterScope.getTypeAuditSize() > templateModelScopeAuditInterests.size()) {
//            TemplateModelScopeAuditInterest t = new TemplateModelScopeAuditInterest();
//            templateModelScopeAuditInterests.add(t);
//            adapterScope.notifyDataSetChanged();
//        }
//    }

    private void addReference() {
        if (20 > templateModelReferences.size()) {
            TemplateModelReference t = new TemplateModelReference();
            templateModelReferences.add(t);
            adapterReference.notifyDataSetChanged();
        }
    }

    private void addPreAuditDoc() {
        if (20 > templateModelPreAuditDocs.size()) {
            TemplateModelPreAuditDoc t = new TemplateModelPreAuditDoc();
            templateModelPreAuditDocs.add(t);
            adapterPreAuditDoc.notifyDataSetChanged();
        }
    }

    private void addPresentDuringMeeting() {
        if (30 > templateModelPresentDuringMeetings.size()) {
            TemplateModelPresentDuringMeeting t = new TemplateModelPresentDuringMeeting();
            templateModelPresentDuringMeetings.add(t);
            adapterPresentDuringMeeting.notifyDataSetChanged();
        }
    }

    private void addPersonelMet() {
        if (30 > templateModelPersonelMetDurings.size()) {
            TemplateModelPersonelMetDuring t = new TemplateModelPersonelMetDuring();
            templateModelPersonelMetDurings.add(t);
            adapterPersonelMetDuring.notifyDataSetChanged();
        }
    }

    private void addDistribution() {
        if (4 > templateModelDistributionLists.size()) {
            TemplateModelDistributionList t = new TemplateModelDistributionList();
            templateModelDistributionLists.add(t);
            adapterDistributionList.notifyDataSetChanged();
        }
    }

    private void addDistributionOthers() {
        if (4 > templateModelDistributionOthers.size()) {
            TemplateModelDistributionOthers t = new TemplateModelDistributionOthers();
            t.setTemplate_id(report.getTemplate_id());
            templateModelDistributionOthers.add(t);
            adapterDistributionOthers.notifyDataSetChanged();
        }
    }

    private void addRecommendation() {
        if (adapterSummaryRecommendation.getRSize() > templateModelSummaryRecommendations.size()) {
            TemplateModelSummaryRecommendation t = new TemplateModelSummaryRecommendation();
            templateModelSummaryRecommendations.add(t);
            adapterSummaryRecommendation.notifyDataSetChanged();
        }
    }

    private void addBackgroundName() {
        if (4 > templateModelCompanyBackgroundNames.size()) {
            TemplateModelCompanyBackgroundName t = new TemplateModelCompanyBackgroundName();
            templateModelCompanyBackgroundNames.add(t);
            adapterCompanyBackgroundName.notifyDataSetChanged();
        }
    }

    private void addMajorChanges() {
        if (4 > templateModelCompanyBackgroundMajorChanges.size()) {
            TemplateModelCompanyBackgroundMajorChanges t = new TemplateModelCompanyBackgroundMajorChanges();
            templateModelCompanyBackgroundMajorChanges.add(t);
            adapterCompanyBackgroundMajorChanges.notifyDataSetChanged();
        }
    }

    private void addAuditors() {
        if (adapterAuditors.getAuditorSize() > templateModelAuditorses.size()) {
            TemplateModelAuditors t = new TemplateModelAuditors();
            templateModelAuditorses.add(t);
            adapterAuditors.notifyDataSetChanged();
        }
    }

    private void addTranslator() {
        if (4 > templateModelTranslators.size()) {
            TemplateModelTranslator t = new TemplateModelTranslator();
            templateModelTranslators.add(t);
            adapterTranslator.notifyDataSetChanged();
        }
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
                        .replace(R.id.fl_content, auditReportFragment).addToBackStack(null).commit();
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
                save();
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
                        .replace(R.id.fl_content, auditReportFragment).addToBackStack(null).commit();
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

    public boolean validate() {
        if (templateModelScopeAudits.get(0).getScope_name().isEmpty() || templateModelScopeAudits.get(0).getScope_detail().isEmpty()) {
            Log.e("validate", "templateModelScopeAudits");
            return false;
        }
//        if (templateModelScopeAuditInterests.size() > 0) {
//            if (templateModelScopeAuditInterests.get(0).getProduct_name().isEmpty()) {
//                Log.e("validate", "templateModelScopeAuditInterests");
//                return false;
//            }
//        }
        if (templateModelReferences.get(0).getCertification().isEmpty() || templateModelReferences.get(0).getBody().isEmpty() ||
                templateModelReferences.get(0).getNumber().isEmpty() || templateModelReferences.get(0).getValidity().isEmpty()) {
            Log.e("validate", "templateModelReferences");
            return false;
        }
        if (templateModelPreAuditDocs.get(0).getPreaudit().isEmpty()) {
            Log.e("validate", "templateModelPreAuditDocs");
            return false;
        }
        if (templateModelPresentDuringMeetings.get(0).getName().isEmpty() || templateModelPresentDuringMeetings.get(0).getPosition().isEmpty()) {
            Log.e("validate", "templateModelPresentDuringMeetings");
            return false;
        }
        if (templateModelPersonelMetDurings.get(0).getName().isEmpty() || templateModelPersonelMetDurings.get(0).getPosition().isEmpty()) {
            Log.e("validate", "templateModelPersonelMetDurings");
            return false;
        }
        if (templateModelDistributionLists.get(0).getDistribution().isEmpty()) {
            Log.e("validate", "templateModelDistributionLists");
            return false;
        }
        if (templateModelSummaryRecommendations.get(0).getElement().isEmpty()) {
            Log.e("validate", "templateModelSummaryRecommendations");
            return false;
        }
        if (templateModelCompanyBackgroundNames.get(0).getBgname().isEmpty()) {
            Log.e("validate", "templateModelCompanyBackgroundNames");
            return false;
        }
        if (templateModelCompanyBackgroundMajorChanges.get(0).getMajorchanges().isEmpty()) {
            Log.e("validate", "templateModelCompanyBackgroundMajorChanges");
            return false;
        }
        if (templateModelAuditorses.get(0).getName().isEmpty() || templateModelAuditorses.get(0).getPosition().isEmpty() ||
                templateModelAuditorses.get(0).getDepartment().isEmpty()) {
            Log.e("validate", "templateModelAuditorses");
            return false;
        }
        if (templateModelTranslators.get(0).getTranslator().isEmpty()) {
            Log.e("validate", "templateModelTranslators");
            return false;
        }

        if (etTemplateNextAuditedArea.getText().toString().length() <= 0) {
            Log.e("validate", "etTemplateNextAuditedArea");
            return false;
        }
        if (etTemplateNextNotAuditedArea.getText().toString().length() <= 0) {
            Log.e("validate", "etTemplateNextNotAuditedArea");
            return false;
        }
        if (etTemplateNextDateOfWrapUp.getText().toString().length() <= 0) {
            Log.e("validate", "etTemplateNextDateOfWrapUp");
            return false;
        }
//        if (etTemplateNextSummaryRecommendationAuditCloseDate.getText().toString().length() <= 0) {
//            Log.e("validate", "etTemplateNextSummaryRecommendationAuditCloseDate");
//            return false;
//        }
        if (etTemplateNextSummaryRecommendationOtherIssuesAudit.getText().toString().length() <= 0) {
            Log.e("validate", "etTemplateNextSummaryRecommendationOtherIssuesAudit");
            return false;
        }
        if (etTemplateNextSummaryRecommendationOtherIssuesExecutive.getText().toString().length() <= 0) {
            Log.e("validate", "etTemplateNextSummaryRecommendationOtherIssuesExecutive");
            return false;
        }
//        if (etTemplateNextCompanyBackgroundDateFrom.getText().toString().length() <= 0) {
//            Log.e("validate", "etTemplateNextCompanyBackgroundDateFrom");
//            return false;
//        }
//        if (etTemplateNextCompanyBackgroundDateTo.getText().toString().length() <= 0) {
//            Log.e("validate", "etTemplateNextCompanyBackgroundDateTo");
//            return false;
//        }

        return true;
    }

    public boolean postData() {

        String co_auditor_id = "";
        List<TemplateModelAuditors> ltma = TemplateModelAuditors.find(TemplateModelAuditors.class,
                "templateid = ? AND reportid = ?", report.getTemplate_id(), report.getReport_id());
        int counter = 0;
        for (TemplateModelAuditors tma : ltma) {
            if (++counter == ltma.size()) {
                co_auditor_id += "{\"auditor_id\": " + tma.getAuditor_id() + "}";
            } else {
                co_auditor_id += "{\"auditor_id\": " + tma.getAuditor_id() + "},";
            }
        }

        // need to discuss this shit
        String scope = "";
        String disposition = "";
        counter = 0;
        List<TemplateModelScopeAudit> tmsa = TemplateModelScopeAudit.find(TemplateModelScopeAudit.class,
                "reportid = ?", report.getReport_id());
        for (TemplateModelScopeAudit t : tmsa) {
            List<TemplateModelScopeAuditInterest> mm = TemplateModelScopeAuditInterest.find(TemplateModelScopeAuditInterest.class,
                    "reportid = ? AND id = ?", report.getReport_id(), t.getId() + "");
            String scope_product = "";
            int m_counter = 0;
            for (TemplateModelScopeAuditInterest m : mm) {
                scope_product += "{\"product_id\":" + m.getProduct_id() + "}";
                if (++m_counter != mm.size()) {
                    scope_product += ",";
                }
            }
            scope += "{\"scope_id\":" + t.getScope_id() + ",\"scope_detail\":\"" + t.getScope_detail() + "\",\"scope_product\":[" + scope_product + "]}";
            //disposition += "{\"disposition_id\":" + t.getDisposition_id() + ",\"scope_product\":[{\"scope_id\":" + t.getScope_id() + ",\"scope_detail\":\"" + t.getScope_detail() + "\"}]}";
            disposition += "{\"disposition_id\": 1,\"scope_product\":[{\"product_id\": 1,\"remarks\":\"" + t.getScope_detail() + "\"}]}";
            if (++counter != tmsa.size()) {
                scope += ",";
                disposition += ",";
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
        List<TemplateModelCompanyBackgroundMajorChanges> tmc = TemplateModelCompanyBackgroundMajorChanges.find(TemplateModelCompanyBackgroundMajorChanges.class, "reportid = ?", report.getReport_id());
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
            String sub_activities = "";
            int m_counter = 0;
            List<ModelReportSubActivities> mm = ModelReportSubActivities.find(ModelReportSubActivities.class, "reportid = ? AND activityid = ?", report.getReport_id(), t.getActivity_id());
            for (ModelReportSubActivities m : mm) {
                sub_activities += "{\"sub_item_id\":" + m.getSub_item_id() + "}";
                if (++m_counter != mm.size()) {
                    sub_activities += ",";
                }
            }
            activities += "{\"activity_id\":\"" + t.getActivity_id() + "\",\"sub_activities\":[" + sub_activities + "]}";
            if (++counter != mra.size()) {
                activities += ",";
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

//        String auditdate = "[{\"" + report.getAudit_date_1() + "\" ,\"" + report.getAudit_date_2() + "\"}]";


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

        String id = "", no = "", version = "0";
        if (!report.getReport_no().contains("GMP-00-")) {
            id = report.getReport_id();
            no = report.getReport_no();
            version = Integer.parseInt(report.getVersion()) + 1 + "";
        }

        Log.e("Bulk Edit", "token:35ced0a2f0ad35bdc9ae075ee213ea4b8e6c2839\n" +
                "cmdEvent:postInput\n" +
                "report_id:" + id + "\n" +
                "report_no:" + no + "\n" +
                "company_id:" + report.getCompany_id() + "\n" +
                "other_activities:" + report.getOther_activities() + "\n" +
                "audit_date:[" + auditdate + "]\n" +
                "template_id:" + report.getTemplate_id() + "\n" +
                "auditor_id:" + report.getAuditor_id() + "\n" +
                "other_issues_audit:" + report.getOther_issues() + "\n" +
                "other_issues_executive:" + report.getOther_issues_executive() + "\n" +
                "audited_areas:" + report.getAudited_areas() + "\n" +
                "areas_to_consider:" + report.getAreas_to_consider() + "\n" +
                "wrap_up_date:" + report.getWrap_date() + "\n" +
                "translator:[" + translators + "]\n" +
                "co_auditor_id:[" + co_auditor_id + "]\n" +
                "reviewer_id:" + report.getReviewer_id() + "\n" +
                "approver_id:" + report.getApprover_id() + "\n" +
                "scope:[" + scope + "]\n" +
                "disposition:[" + disposition + "]\n" +
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
                "status:" + report.getStatus() + "\n" +
                "version:" + version + "\n" +
                "other_distribution:[" + otherdistribution + "]\n" +
                "head_lead:" + report.getHead_lead() + "");

        apiInterface = ApiClient.getApiClientPostAuditReport().create(ApiInterface.class);
        Call<ModelAuditReportReply> modelAuditReportReplyCall = apiInterface.sendAuditReports(
                "35ced0a2f0ad35bdc9ae075ee213ea4b8e6c2839",
                "postInput",
                id,//report_id
                no,//report_no
                report.getCompany_id(),
                report.getOther_activities(),
                "[" + auditdate + "]",
                report.getTemplate_id(),
                report.getAuditor_id(),
                report.getOther_issues(),
                report.getOther_issues_executive(),
                report.getAudited_areas(),
                report.getAreas_to_consider(),
                report.getWrap_date(),
                "[" + translators + "]",
                "[" + co_auditor_id + "]",
                report.getReviewer_id(),
                report.getApprover_id(),
                "[" + scope + "]",
                "[" + disposition + "]",
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
                report.getStatus(),//"status",
                version,//"version"
                "[" + otherdistribution + "]",
                report.getHead_lead()
        );

        modelAuditReportReplyCall.enqueue(new Callback<ModelAuditReportReply>() {
            @Override
            public void onResponse(Call<ModelAuditReportReply> call, Response<ModelAuditReportReply> response) {
                modelAuditReportReply = response.body();
                //Log.e("Result post", modelApproverInfo.getMessage());
                Log.e("Result post", "hey " + modelAuditReportReply.toString());
                try {
                    Log.e("ResultTry", modelAuditReportReply.getMessage() + "");
                    Log.e("ResultTry", modelAuditReportReply.getKey() + "");
                    Log.e("ResultTry", modelAuditReportReply.getReport_id() + "");
                    Log.e("ResultTry2", modelAuditReportReply.toString() + "");
                } catch (Exception e) {
                    Log.e("ResultCatch", e.toString() + "");
                }
                updateDate(modelAuditReportReply.getReport_id());
                report.setReport_id(modelAuditReportReply.getReport_id());
                report.setReport_no(modelAuditReportReply.getReport_no());
                report.setStatus(modelAuditReportReply.getStatus());
                report.save();
            }

            @Override
            public void onFailure(Call<ModelAuditReportReply> call, Throwable t) {
                Log.e("AuditReport ", "OnFailure " + t.getMessage());
            }
        });
        return true;
    }

    public void updateDate(String report_id) {
        List<TemplateModelAuditors> ltma = TemplateModelAuditors.find(TemplateModelAuditors.class, "templateid = ? AND reportid = ?", report.getTemplate_id(), report.getReport_id());
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

        List<ModelDateOfAudit> mdoa = ModelDateOfAudit.find(ModelDateOfAudit.class, "reportid = ?", report.getReport_id());
        for (ModelDateOfAudit t : mdoa) {
            t.setReport_id(report_id);
            t.save();
        }
    }

}