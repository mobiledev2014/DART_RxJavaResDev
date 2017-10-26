package com.unilab.gmp.model;

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

public class ModelAuditReports extends SugarRecord {
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
    String other_issues;

    String other_issues_executive;
    String audited_areas;
    String areas_to_consider;

    @SerializedName("wrap_up_date")
    ArrayList<ModelAuditReportWrapUpDate> date_of_wrap;

    ArrayList<TemplateModelTranslator> translators;
    String create_date;
    String modified_date;

    String status = "0";
    String version = "0";
    String head_lead;
    String reviewer_id;//waley
    String approver_id;//waley
    boolean isReviewerChecked;

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
                "report_id='" + report_id + '\'' +
                ", report_no='" + report_no + '\'' +
                ", company_id='" + company_id + '\'' +
                ", other_activities='" + other_activities + '\'' +
                ", template_id='" + template_id + '\'' +
                ", auditor_id='" + auditor_id + '\'' +
                ", audit_close_date='" + audit_close_date + '\'' +
                ", other_issues='" + other_issues + '\'' +
                ", other_issues_executive='" + other_issues_executive + '\'' +
                ", audited_areas='" + audited_areas + '\'' +
                ", areas_to_consider='" + areas_to_consider + '\'' +
                ", date_of_wrap=" + date_of_wrap +
                ", translators=" + translators +
                ", create_date='" + create_date + '\'' +
                ", modified_date='" + modified_date + '\'' +
                ", status='" + status + '\'' +
                ", version='" + version + '\'' +
                ", head_lead='" + head_lead + '\'' +
                ", reviewer_id='" + reviewer_id + '\'' +
                ", approver_id='" + approver_id + '\'' +
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
                ", audit_date_1='" + audit_date_1 + '\'' +
                ", audit_date_2='" + audit_date_2 + '\'' +
                '}';
    }

    //    List<ModelReportElementsRequiringRecommendation> elements_requiring_recommendation;


}
