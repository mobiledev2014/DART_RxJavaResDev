package com.unilab.gmp.model;

import com.orm.SugarRecord;

import java.util.List;

import androidx.room.Entity;import lombok.Getter;
import lombok.Setter;

/**
 * Created by c_jhcanuto on 8/23/2017.
 */

@Setter
@Getter
@Entity
public class ModelReport  {
    String token =  "35ced0a2f0ad35bdc9ae075ee213ea4b8e6c2839";
    String cmdEvent = "postInput";
    String report_id;
    String template_id;
    String report_no;
    String company_id;
    String other_activities;
    String audit_date_1;
    String audit_date_2;
    String p_inspection_date_1;
    String p_inspection_date_2;
    String auditor_id;
    List<ModelReportCoAuditorID> co_auditor_id;
    List<ModelReportReviewer> reviewer;
    List<ModelReportApprover> approver;
    String closure_date;
    String other_issues;
    String audited_areas;
    String areas_to_consider;
    String wrap_up_date;
    String translator;
    List<ModelReportScope> scope;
    List<ModelReportDisposition> disposition;
    List<ModelReportPreAuditDocs> pre_audit_documents;
    List<ModelReportReferences> references;
    List<ModelReportInspection> inspection;
    List<ModelReportInspector> inspector;
    List<ModelReportPersonnel> personnel;
    List<ModelReportActivities> activities;
    List<ModelReportQuestion> question;
    List<ModelReportElementsRequiringRecommendation> elements_requiring_recommendation;

}
