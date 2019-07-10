package com.unilab.gmp.model;

import androidx.room.Entity;

import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by c_jhcanuto on 8/23/2017.
 */

@Setter
@Getter
@Entity

public class ModelAuditReports  {
    String report_id;
    String report_no;
    String company_id;
    String other_activities = "";
    //    String p_inspection_date_1 = "";
//    String p_inspection_date_2 = "";
    String template_id;
    String auditor_id = "";

    @SerializedName("closure_date")
    String audit_close_date;

    @SerializedName("other_issues_audit")
    List<TemplateModelOtherIssuesAudit> other_issues;

    List<TemplateModelOtherIssuesExecutive> other_issues_executive;
    String audited_areas;
    String areas_to_consider;

    ArrayList<ModelAuditReportWrapUpDate> date_of_wrap;

    ArrayList<TemplateModelTranslator> translators;
    String create_date;
    String modified_date = "";

    /**
     * Audit Report statuses
     *  0 - Draft
     *  1 - Co-Auditors Review
     *  2 - Reviewed by Co-Auditor
     *  3 - Submitted to Department Head (Editing is Disabled)
     *  4 - Submitted to Division Head (Editing is Disabled)
     *  5 - Approved by Division Head (Viewing is PDF)
     *  6 - Returned by Department Head
     *  7 - Returned by Division Head
     *  -1 - Archived
     */
    String status = "0";
    String version = "0";
    String head_lead;
    String reviewer_id;//waley
    String approver_id;//waley
    boolean isReviewerChecked;
    @SerializedName("wrap_up_date")
    String wrap_date = "";

    @SerializedName("audit_dates")
    List<ModelDateOfAudit> date_of_audit;

    List<TemplateModelAuditors> co_auditor_id;
    List<TemplateModelScopeAuditCopy> scope;
    List<ModelReportDisposition> disposition;
    List<TemplateModelPreAuditDoc> pre_audit_documents;
    @SerializedName("references")
    List<TemplateModelReference> referencess;
    List<TemplateModelCompanyBackgroundMajorChanges> inspection;
    //    List<TemplateModelCompanyBackgroundName> inspector;
    @SerializedName("personel")
    List<TemplateModelPersonelMetDuring> personnel;
    List<ModelReportActivities> activities;
    List<ModelReportQuestion> question;
    List<TemplateModelSummaryRecommendation> recommendation;
    List<TemplateModelDistributionList> distribution;
    List<TemplateModelPresentDuringMeeting> present_during_meeting;
    List<TemplateModelDistributionOthers> other_distribution;

    String audit_date_1;
    String audit_date_2;

    @Override
    public String toString() {
        return "ModelAuditReports{" +
                "report_id='" + report_id + '\n' +
                ", report_no='" + report_no + '\n' +
                ", company_id='" + company_id + '\n' +
                ", other_activities='" + other_activities + '\n' +
                ", template_id='" + template_id + '\n' +
                ", auditor_id='" + auditor_id + '\n' +
                ", audit_close_date='" + audit_close_date + '\n' +
                ", other_issues='" + other_issues + '\n' +
                ", other_issues_executive='" + other_issues_executive + '\n' +
                ", audited_areas='" + audited_areas + '\n' +
                ", areas_to_consider='" + areas_to_consider + '\n' +
//                ", date_of_wrap=" + date_of_wrap +
                ", translators=" + translators +
                ", create_date='" + create_date + '\n' +
                ", modified_date='" + modified_date + '\n' +
                ", status='" + status + '\n' +
                ", version='" + version + '\n' +
                ", head_lead='" + head_lead + '\n' +
                ", reviewer_id='" + reviewer_id + '\n' +
                ", approver_id='" + approver_id + '\n' +
                ", isReviewerChecked=" + isReviewerChecked +
                ", date_of_audit=" + date_of_audit +
                ", co_auditor_id=" + co_auditor_id +
                ", scope=" + scope +
                ", disposition=" + disposition +
                ", pre_audit_documents=" + pre_audit_documents +
                ", referencess=" + referencess +
                ", inspection=" + inspection +
//                ", inspector=" + inspector +
                ", personnel=" + personnel +
                ", activities=" + activities +
                ", question=" + question +
                ", recommendation=" + recommendation +
                ", distribution=" + distribution +
                ", present_during_meeting=" + present_during_meeting +
                ", other_distribution=" + other_distribution +
                ", audit_date_1='" + audit_date_1 + '\n' +
                ", audit_date_2='" + audit_date_2 + '\n' +
                '}';
    }

    //    List<ModelReportElementsRequiringRecommendation> elements_requiring_recommendation;


}
