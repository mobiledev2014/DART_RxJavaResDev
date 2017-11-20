package com.unilab.gmp.model;

import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by c_jhcanuto on 8/11/2017.
 */

@Getter
@Setter
public class ModelTemplates extends SugarRecord{
    @SerializedName("template_id")
    String templateID;

    @SerializedName("standard_name")
    String templateName;

    @SerializedName("product_type")
    String productType;

    @SerializedName("create_date")
    String dateCreated;

    @SerializedName("update_date")
    String dateUpdated;

    @SerializedName("activities")
    List<ModelTemplateActivities> modelTemplateActivities ;

    @SerializedName("elements")
    List<ModelTemplateElements> modelTemplateElements;

    String report_id;
    String report_no;
    String company_id = "";
    String other_activities = "";
    String audit_date_1 = "";
    String audit_date_2 = "";
    String p_inspection_date_1 = "";
    String p_inspection_date_2 = "";
    String auditor_id = "";
    String closure_date = "";
    String other_issues = "";
    String audited_areas = "";
    String areas_to_consider = "";
    String wrap_up_date = "";
    String translator = "";
    boolean isReviewerChecked;

    String status = "";

    List<ModelDateOfAudit> modelDateOfAudits;

}

