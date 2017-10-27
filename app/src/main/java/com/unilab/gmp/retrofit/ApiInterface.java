package com.unilab.gmp.retrofit;

import com.unilab.gmp.model.ConfigModel;
import com.unilab.gmp.model.ModelApproverInfo;
import com.unilab.gmp.model.ModelAuditReportReply;
import com.unilab.gmp.model.ModelAuditReports;
import com.unilab.gmp.model.ModelAuditReportsList;
import com.unilab.gmp.model.ModelAuditorInfo;
import com.unilab.gmp.model.ModelCategoryInfo;
import com.unilab.gmp.model.ModelClassificationInfo;
import com.unilab.gmp.model.ModelCompanyInfo;
import com.unilab.gmp.model.ModelDispositionInfo;
import com.unilab.gmp.model.ModelDistributionInfo;
import com.unilab.gmp.model.ModelProductInfo;
import com.unilab.gmp.model.ModelReferenceInfo;
import com.unilab.gmp.model.ModelReviewerInfo;
import com.unilab.gmp.model.ModelTemplates;
import com.unilab.gmp.model.ModelTypeAuditInfo;
import com.unilab.gmp.model.TemplateListModel;
import com.unilab.gmp.retrofit.user.ResultUser;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * Created by c_jsbustamante on 7/22/2016.
 */
public interface ApiInterface {

    String baseURL = "http://ojl.ecomqa.com/api";
    String pdbaURL = "http://abig.unilab.ph/WebAPI_BiomedisOJL/api/md/search";

    //    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @FormUrlEncoded
    @POST("api")
    Call<ModelAuditReportReply> sendAuditReports(
            @Field("token") String token,
            @Field("cmdEvent") String cmdEvent,
            @Field("report_id") String report_id,
            @Field("report_no") String report_no,
            @Field("company_id") String company_id,
            @Field("other_activities") String other_activities,
            @Field("audit_date") String audit_date,
            @Field("template_id") String template_id,
            @Field("auditor_id") String auditor_id,
            @Field("other_issues_audit") String other_issues_audit,
            @Field("other_issues_executive") String other_issues_executive,
            @Field("audited_areas") String audited_areas,
            @Field("areas_to_consider") String areas_to_consider,
            @Field("wrap_up_dates") String wrap_up_date,
            @Field("translator") String translator,
            @Field("co_auditor_id") String co_auditor_id,
            @Field("reviewer_id") String reviewer_id,
            @Field("approver_id") String approver_id,
            @Field("scope") String scope,
            @Field("disposition") String disposition,
            @Field("pre_audit_documents") String pre_audit_documents,
            @Field("references") String references,
            @Field("inspection") String inspection,
            @Field("inspector") String inspector,
            @Field("personnel") String personnel,
            @Field("activities") String activities,
            @Field("question") String question,
            @Field("recommendation") String recommendation,
            @Field("distribution") String distribution,
            @Field("present_during_meeting") String present_during_meeting,
            @Field("create_date") String create_date,
            @Field("modified_date") String modified_date,
            @Field("status") String status,
            @Field("version") String version,
            @Field("other_distribution") String other_distribution,
            @Field("head_lead") String head_lead);

    @FormUrlEncoded
    @POST("api")
    Call<ArrayList<ModelAuditReportReply>> sendAuditReports2(
            @Field("token") String token,
            @Field("cmdEvent") String cmdEvent,
            @Field("report_id") String report_id,
            @Field("report_no") String report_no,
            @Field("company_id") String company_id,
            @Field("other_activities") String other_activities,
            @Field("audit_date") String audit_date,
            @Field("p_inspection_date_1") String p_inspection_date_1,
            @Field("p_inspection_date_2") String p_inspection_date_2,
            @Field("template_id") String template_id,
            @Field("auditor_id") String auditor_id,
            @Field("closure_date") String closure_date,
            @Field("other_issues_audit") String other_issues_audit,
            @Field("other_issues_executive") String other_issues_executive,
            @Field("audited_areas") String audited_areas,
            @Field("areas_to_consider") String areas_to_consider,
            @Field("wrap_up_date") String wrap_up_date,
            @Field("translator") String translator,
            @Field("co_auditor_id") String co_auditor_id,
            @Field("reviewer_id") String reviewer_id,
            @Field("approver_id") String approver_id,
            @Field("scope") String scope,
            @Field("disposition") String disposition,
            @Field("pre_audit_documents") String pre_audit_documents,
            @Field("references") String references,
            @Field("inspection") String inspection,
            @Field("inspector") String inspector,
            @Field("personnel") String personnel,
            @Field("activities") String activities,
            @Field("question") String question,
            @Field("recommendation") String recommendation,
            @Field("distribution") String distribution,
            @Field("present_during_meeting") String present_during_meeting,
            @Field("create_date") String create_date,
            @Field("modified_date") String modified_date,
            @Field("status") String status,
            @Field("version") String version,
            @Field("other_distribution") String other_distribution,
            @Field("head_lead") String head_lead);

    @FormUrlEncoded
    @POST("ojl/validate_user.php/")
    Call<ResultUser> getUser(@Field("email") String email);

    @GET("template_list.json")
    Call<TemplateListModel> getDataList();

    @GET("config.json")
    Call<ConfigModel> getConfig();


    @GET("audit_report_list.json")
    Call<ModelAuditReportsList> getAuditReportDataList();

    @GET
    Call<ModelAuditReports> getAuditReport(@Url String url);

    //    @GET(".json")
//    Call<ModelTemplates> getData();
    @GET
    public Call<ModelTemplates> getData(@Url String reportID);

    @GET("approver.json")
    Call<ModelApproverInfo> getApprover();


    @GET("auditor.json")
    Call<ModelAuditorInfo> getAuditors();

    @GET("reviewer.json")
    Call<ModelReviewerInfo> getReviewer();

    @GET("category.json")
    Call<ModelCategoryInfo> getCategory();

    @GET("product.json")
    Call<ModelProductInfo> getProducts();

    @GET("classification.json")
    Call<ModelClassificationInfo> getClassifications();

    @GET("site.json")
    Call<ModelCompanyInfo> getCompanies();

    @GET("standard_reference.json")
    Call<ModelReferenceInfo> getReferences();

    @GET("type_audit.json ")
    Call<ModelTypeAuditInfo> getTypeAudit();

    @GET("disposition.json")
    Call<ModelDispositionInfo> getDisposition();

    @GET("distribution.json")
    Call<ModelDistributionInfo> getDistribution();
}
