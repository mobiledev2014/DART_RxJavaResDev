package com.unilab.gmp.utility;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.unilab.gmp.R;
import com.unilab.gmp.activity.HomeActivity;
import com.unilab.gmp.fragment.HomeFragment;
import com.unilab.gmp.model.ApproverModel;
import com.unilab.gmp.model.AuditorsModel;
import com.unilab.gmp.model.ConfigModel;
import com.unilab.gmp.model.ModelApproverInfo;
import com.unilab.gmp.model.ModelAuditReportDetails;
import com.unilab.gmp.model.ModelAuditReports;
import com.unilab.gmp.model.ModelAuditReportsList;
import com.unilab.gmp.model.ModelAuditorInfo;
import com.unilab.gmp.model.ModelCategory;
import com.unilab.gmp.model.ModelCategoryInfo;
import com.unilab.gmp.model.ModelClassification;
import com.unilab.gmp.model.ModelClassificationCategory;
import com.unilab.gmp.model.ModelClassificationInfo;
import com.unilab.gmp.model.ModelCompany;
import com.unilab.gmp.model.ModelCompanyInfo;
import com.unilab.gmp.model.ModelDateOfAudit;
import com.unilab.gmp.model.ModelDisposition;
import com.unilab.gmp.model.ModelDispositionInfo;
import com.unilab.gmp.model.ModelDistribution;
import com.unilab.gmp.model.ModelDistributionInfo;
import com.unilab.gmp.model.ModelProduct;
import com.unilab.gmp.model.ModelProductInfo;
import com.unilab.gmp.model.ModelReport;
import com.unilab.gmp.model.ModelReportActivities;
import com.unilab.gmp.model.ModelReportQuestion;
import com.unilab.gmp.model.ModelReportSubActivities;
import com.unilab.gmp.model.ModelReviewerInfo;
import com.unilab.gmp.model.ModelSiteAuditHistory;
import com.unilab.gmp.model.ModelSiteDate;
import com.unilab.gmp.model.ModelTemplateActivities;
import com.unilab.gmp.model.ModelTemplateElements;
import com.unilab.gmp.model.ModelTemplateQuestionDetails;
import com.unilab.gmp.model.ModelTemplateSubActivities;
import com.unilab.gmp.model.ModelTemplates;
import com.unilab.gmp.model.ModelTypeAudit;
import com.unilab.gmp.model.ModelTypeAuditInfo;
import com.unilab.gmp.model.ReviewerModel;
import com.unilab.gmp.model.TemplateDetailsModel;
import com.unilab.gmp.model.TemplateListModel;
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
import com.unilab.gmp.model.TemplateModelScopeAuditCopy;
import com.unilab.gmp.model.TemplateModelScopeAuditInterest;
import com.unilab.gmp.model.TemplateModelSummaryRecommendation;
import com.unilab.gmp.model.TemplateModelTranslator;
import com.unilab.gmp.retrofit.ApiClient;
import com.unilab.gmp.retrofit.ApiInterface;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by c_rcmiguel on 8/23/2017.
 */

public class APICalls extends AsyncTask<String, String, Boolean> {

    private static final String TAG = "APICalls";

    int changes = 0;
    int numberoftemplates, templatesdownloaded;
    boolean isdone = false;

//    int numberoftemplates2, templatesdownloaded2;
//    boolean isdone2 = false;

    ApiClient apiClient;
    ApiInterface apiInterface;
    AlertDialog loginAlert;

    AlertDialog delayLoginAlert;
    Context context;
    String dialogMessage = "";
    String statusMessage = "";
    SharedPreferenceManager sharedPref;
    AlertDialog.Builder builder2;

    ConfigModel configModel;
    ModelApproverInfo modelApproverInfo;
    ModelAuditorInfo modelAuditorInfo;
    ModelReviewerInfo modelReviewerInfo;
    ModelCompanyInfo modelCompanyInfo;
    ModelTemplates modelTemplates;
    ModelCategoryInfo modelCategoryInfo;
    ModelProductInfo modelProductInfo;
    ModelTypeAuditInfo modelTypeAuditInfo;
    ModelDispositionInfo modelDispositionInfo;
    ModelDistributionInfo modelDistributionInfo;
    ModelAuditReports modelAuditReports;
    ModelClassificationInfo modelClassificationInfo;
    ModelReport modelReport;

    Dialog dialogSyncSuccess;
    Boolean manualSync;
    HomeActivity homeActivity;
//    ModelClassificationInfo modelClassificationInfo;
//    ModelReferenceInfo modelReferenceInfo;
//

    public APICalls(Context context, String message, Boolean sync, HomeActivity homeActivity, String status) {
        this.context = context;
        this.dialogMessage = message;
        sharedPref = new SharedPreferenceManager(context);
        this.manualSync = sync;
        this.homeActivity = homeActivity;
        this.statusMessage = status;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setCancelable(false);
        builder1.setTitle("Downloading data");
        builder1.setMessage("Please wait...");

        loginAlert = builder1.create();
        loginAlert.show();

        builder2 = new AlertDialog.Builder(context);
        builder2.setCancelable(false);
        builder2.setTitle("Downloading data");
        builder2.setMessage("Please wait...");
    }

    @Override
    protected Boolean doInBackground(String... strings) {
        Boolean result = false;

        try {
            apiInterface = ApiClient.getConfig().create(ApiInterface.class);
            apiConfig();
            while (!isdone || templatesdownloaded != numberoftemplates)
                //  while (!isdone2 || templatesdownloaded2 != numberoftemplates2) ;
                //Log.e("changes", "changes : " + changes);
                result = false;
        } catch (Exception e) {
            Log.i("CATCH", "RESULT_VALUE : " + e.toString() + "");
            result = true;
        }

        return result;
    }

    @Override
    protected void onPostExecute(Boolean s) {
        super.onPostExecute(s);
        loginAlert.dismiss();

        final AlertDialog loginAlert2 = builder2.create();
        loginAlert2.show();

        if (statusMessage.equals("auditReport")){
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    loginAlert2.dismiss();
                    Log.i("lag_audit_report", statusMessage + " : " + "if");
                }
            }, 10000);
        } else {
            loginAlert2.dismiss();
            Log.i("lag_audit_report", statusMessage + " : " + "else");
        }

        try {
            Log.i("RESULT", "RESULT_VALUE : " + s + "");
            Log.i("DIALOG_SHOWING", "1" + loginAlert.isShowing() + " : " + s.toString());

            Log.i("save_date", getDate());
            sharedPref.saveData("DATE", getDate());

            if (changes > 0) {
                NotificationCreator notificationCreator = new NotificationCreator(context);
                notificationCreator.createNotification(changes);
            }

            if (manualSync) {
                Log.i("DIALOG_SHOWING", "2" + loginAlert.isShowing());
                if (loginAlert.isShowing()) {
                    loginAlert.dismiss();
                }

                Log.i("New_Template", changes + "");

                if (Variable.showDialog) {
                    dialogSyncSuccess("Data has been successfully synced.");
                }
                //homeActivity.initializeHome();
            } else {
                Intent intent = new Intent(context, HomeActivity.class);
                intent.putExtra("NEWTEMPLATE", changes + "");
                context.startActivity(intent);
                ((Activity) context).finish();
                Log.e("DATA", "FORCE SYNC");
            }
        } catch (Exception e) {
            Log.i("RESULT", "RESULT_VALUE : " + s + "");
            loginAlert.dismiss();
        }
    }

    public void setFragment() {
        FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        fragmentManager.beginTransaction()
                .replace(R.id.fl_content, new HomeFragment()).addToBackStack(null).commit();
    }

    public Date date(String date) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (date == null)
            return null;
        try {
            return df.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean apiConfig() {
        final Call<ConfigModel> config = apiInterface.getConfig();

        config.enqueue(new Callback<ConfigModel>() {
            @Override
            public void onResponse(Call<ConfigModel> call, Response<ConfigModel> response) {
                configModel = response.body();
                List<ConfigModel> cm = ConfigModel.listAll(ConfigModel.class);
                apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
                if (cm.size() > 0) {
                    if (configModel != null) {
                        if (configModel.getApprover() != null) {
                            Log.i("config_date_debug", "approver" + configModel.getApprover());
                            if (date(cm.get(0).getApprover()).before(date(configModel.getApprover()))) {
                                cm.get(0).setApprover(configModel.getApprover());
                                apiApprover();
                            }
                        }

                        if (configModel.getAuditor() != null) {
                            Log.i("config_date_debug", "auditor" + configModel.getApprover());
                            if (date(cm.get(0).getAuditor()).before(date(configModel.getAuditor()))) {
                                cm.get(0).setAuditor(configModel.getAuditor());
                                apiAuditors();
                            }
                        }

                        if (configModel.getReviewer() != null) {
                            Log.i("config_date_debug", "reviewer" + configModel.getApprover());
                            if (date(cm.get(0).getReviewer()).before(date(configModel.getReviewer()))) {
                                Log.e(TAG, "onResponse: Enter get reviewer");
                                cm.get(0).setReviewer(configModel.getReviewer());
                                apiReviewer();
                            }
                        }

                        if (configModel.getSite() != null) {
                            Log.i("config_date_debug", "site" + configModel.getApprover());
                            if (date(cm.get(0).getSite()).before(date(configModel.getSite()))) {
                                cm.get(0).setSite(configModel.getSite());
                                apiSupplier();
                            }
                        }

                        if (configModel.getCategory() != null) {
                            Log.i("config_date_debug", "category" + configModel.getApprover());
                            if (date(cm.get(0).getCategory()).before(date(configModel.getCategory()))) {
                                cm.get(0).setCategory(configModel.getCategory());
                                apiCategory();
                            }
                        }

                        if (configModel.getProduct() != null) {
                            Log.i("config_date_debug", "product" + configModel.getApprover());
                            if (date(cm.get(0).getProduct()).before(date(configModel.getProduct()))) {
                                cm.get(0).setProduct(configModel.getProduct());
                                apiProduct();
                            }
                        }
                        if (configModel.getType_audit() != null) {
                            Log.i("config_date_debug", "type audit" + configModel.getApprover());
                            if (date(cm.get(0).getType_audit()).before(date(configModel.getType_audit()))) {
                                cm.get(0).setType_audit(configModel.getType_audit());
                                apiTypeAudit();
                            }
                        }
                        if (configModel.getDisposition() != null) {
                            Log.i("config_date_debug", "disposition" + configModel.getApprover());
                            if (date(cm.get(0).getDisposition()).before(date(configModel.getDisposition()))) {
                                cm.get(0).setDisposition(configModel.getDisposition());
                                apiDisposition();
                            }
                        }
                        if (configModel.getDistribution() != null) {
                            Log.i("config_date_debug", "distribution" + configModel.getApprover());
                            if (date(cm.get(0).getDistribution()).before(date(configModel.getDistribution()))) {
                                cm.get(0).setDistribution(configModel.getDistribution());
                                apiDistribution();
                            }
                        }
                        if (configModel.getClassification() != null) {
                            Log.i("config_date_debug", "classification" + configModel.getApprover());
                            if (date(cm.get(0).getClassification()).before(date(configModel.getClassification()))) {
                                cm.get(0).setDistribution(configModel.getClassification());
                                apiClassification();
                            }
                        }

                        //for testing only
                        if (!statusMessage.equals("auditReport")) {
                            apiTemplateList();
                        } else {
                            isdone = true;
                        }
                        apiAuditReports();

                        cm.get(0).save();
                    }
                } else {
                    if (configModel != null) {
                        configModel.save();
                    }
                    apiApprover();
                    apiAuditors();
                    apiReviewer();
                    apiSupplier();
                    apiCategory();
                    apiProduct();
                    apiTypeAudit();
                    apiDisposition();
                    apiDistribution();
                    apiClassification();

                    apiTemplateList();
                    apiAuditReports();
                }
            }

            @Override
            public void onFailure(Call<ConfigModel> call, Throwable t) {
                Log.e("Approver ", "OnFailure " + t.getMessage());
            }
        });

        return true;
    }

    public void apiApprover() {
        //api call for auditors information
        Call<ModelApproverInfo> listApprover = apiInterface.getApprover();
        listApprover.enqueue(new Callback<ModelApproverInfo>() {
            @Override
            public void onResponse(Call<ModelApproverInfo> call, Response<ModelApproverInfo> response) {
                modelApproverInfo = response.body();


                ApproverModel.deleteAll(ApproverModel.class);
                if (modelApproverInfo != null) {
                    for (int x = 0; x < modelApproverInfo.getApproverModels().size(); x++) {
                        ApproverModel approversModel = new ApproverModel();
                        approversModel.setApprover_id(modelApproverInfo.getApproverModels().get(x).getApprover_id());
                        approversModel.setFirstname(modelApproverInfo.getApproverModels().get(x).getFirstname());
                        approversModel.setMiddlename(modelApproverInfo.getApproverModels().get(x).getMiddlename());
                        approversModel.setLastname(modelApproverInfo.getApproverModels().get(x).getLastname());
                        approversModel.setDesignation(modelApproverInfo.getApproverModels().get(x).getDesignation());
                        approversModel.setCompany(modelApproverInfo.getApproverModels().get(x).getCompany());
                        approversModel.setDepartment(modelApproverInfo.getApproverModels().get(x).getDepartment());
                        approversModel.setCreate_date(modelApproverInfo.getApproverModels().get(x).getCreate_date());
                        approversModel.setUpdate_date(modelApproverInfo.getApproverModels().get(x).getUpdate_date());
                        approversModel.setEmail(modelApproverInfo.getApproverModels().get(x).getEmail());
                        approversModel.setStatus(modelApproverInfo.getApproverModels().get(x).getStatus());
                        isAppoverExisting(approversModel);
                        Log.e("APICalls ", "Approver " + modelApproverInfo.getApproverModels().get(x).toString());
                    }


                }else{

                }
            }

            @Override
            public void onFailure(Call<ModelApproverInfo> call, Throwable t) {
                Log.e("Approver ", "OnFailure " + t.getMessage());
            }



        });
    }

    public void apiAuditors() {
        final Call<ModelAuditorInfo> listAuditors = apiInterface.getAuditors();
        listAuditors.enqueue(new Callback<ModelAuditorInfo>() {
            @Override
            public void onResponse(Call<ModelAuditorInfo> call, Response<ModelAuditorInfo> response) {
                modelAuditorInfo = response.body();
                AuditorsModel.deleteAll(AuditorsModel.class);

                for (int x = 0; x < modelAuditorInfo.getModelAuditors().size(); x++) {
                    AuditorsModel auditorsModel = new AuditorsModel();
                    auditorsModel.setAuditor_id(modelAuditorInfo.getModelAuditors().get(x).getAuditor_id());
                    auditorsModel.setFname(modelAuditorInfo.getModelAuditors().get(x).getFirstname());
                    auditorsModel.setMname(modelAuditorInfo.getModelAuditors().get(x).getMiddlename());
                    auditorsModel.setLname(modelAuditorInfo.getModelAuditors().get(x).getLastname());
                    auditorsModel.setDesignation(modelAuditorInfo.getModelAuditors().get(x).getDesignation());
                    auditorsModel.setCompany(modelAuditorInfo.getModelAuditors().get(x).getCompany());
                    auditorsModel.setDepartment(modelAuditorInfo.getModelAuditors().get(x).getDepartment());
                    auditorsModel.setCreate_date(modelAuditorInfo.getModelAuditors().get(x).getCreate_date());
                    auditorsModel.setUpdate_date(modelAuditorInfo.getModelAuditors().get(x).getUpdate_date());
                    auditorsModel.setEmail(modelAuditorInfo.getModelAuditors().get(x).getEmail());
                    auditorsModel.setStatus(modelAuditorInfo.getModelAuditors().get(x).getStatus());

                    //tick for reviewer and approver
                    /*
                    * auditorsModel.setReviewer(modelAuditorInfo.getModelAuditors().get(x).getReviewer());
                    * auditorsModel.setApprover(modelAuditorInfo.getModelAuditors().get(x).getApprover());
                    * */
                    isAuditorExisting(auditorsModel);
                }

            }

            @Override
            public void onFailure(Call<ModelAuditorInfo> call, Throwable t) {
                Log.e("testing", t.getMessage());
            }
        });
    }

    public void apiReviewer() {
        Call<ModelReviewerInfo> modelReviewerCall = apiInterface.getReviewer();
        modelReviewerCall.enqueue(new Callback<ModelReviewerInfo>() {
            @Override
            public void onResponse(Call<ModelReviewerInfo> call, Response<ModelReviewerInfo> response) {
                modelReviewerInfo = response.body();
                ReviewerModel.deleteAll(ReviewerModel.class);

                if (modelReviewerInfo != null)
                    if (modelReviewerInfo.getModelReviewers() != null)
                        for (int x = 0; x < modelReviewerInfo.getModelReviewers().size(); x++) {
                            ReviewerModel reviewersModel = new ReviewerModel();
                            reviewersModel.setReviewer_id(modelReviewerInfo.getModelReviewers().get(x).getReviewer_id());
                            reviewersModel.setFirstname(modelReviewerInfo.getModelReviewers().get(x).getFirstname());
                            reviewersModel.setMiddlename(modelReviewerInfo.getModelReviewers().get(x).getMiddlename());
                            reviewersModel.setLastname(modelReviewerInfo.getModelReviewers().get(x).getLastname());
                            reviewersModel.setDesignation(modelReviewerInfo.getModelReviewers().get(x).getDesignation());
                            reviewersModel.setCompany(modelReviewerInfo.getModelReviewers().get(x).getCompany());
                            reviewersModel.setDepartment(modelReviewerInfo.getModelReviewers().get(x).getDepartment());
                            reviewersModel.setCreate_date(modelReviewerInfo.getModelReviewers().get(x).getCreate_date());
                            reviewersModel.setUpdate_date(modelReviewerInfo.getModelReviewers().get(x).getUpdate_date());
                            reviewersModel.setEmail(modelReviewerInfo.getModelReviewers().get(x).getEmail());
                            reviewersModel.setStatus(modelReviewerInfo.getModelReviewers().get(x).getStatus());
                            isReviewerExisting(reviewersModel);
                        }

                //Log.e("testing", response.toString() + " Reviewer: " + modelReviewerInfo.getModelReviewers().get(0).getFirstname());
            }

            @Override
            public void onFailure(Call<ModelReviewerInfo> call, Throwable t) {
                Log.e("testing", t.getMessage());
            }
        });
    }

    public void apiSupplier() {
        Call<ModelCompanyInfo> modelCompanyInfoCall = apiInterface.getCompanies();
        modelCompanyInfoCall.enqueue(new Callback<ModelCompanyInfo>() {
            @Override
            public void onResponse(Call<ModelCompanyInfo> call, Response<ModelCompanyInfo> response) {
                modelCompanyInfo = response.body();

                ModelCompany.deleteAll(ModelCompany.class);
                ModelSiteAuditHistory.deleteAll(ModelSiteAuditHistory.class);
                TemplateModelCompanyBackgroundMajorChanges.deleteAll(TemplateModelCompanyBackgroundMajorChanges.class);
                TemplateModelCompanyBackgroundName.deleteAll(TemplateModelCompanyBackgroundName.class);
                ModelSiteDate.deleteAll(ModelSiteDate.class);
                for (int x = 0; x < modelCompanyInfo.getModelCompanies().size(); x++) {
                    ModelCompany modelCompany = new ModelCompany();
                    String company_id = modelCompanyInfo.getModelCompanies().get(x).getCompany_id();
                    modelCompany.setCompany_id(company_id);
                    modelCompany.setCompany_name(modelCompanyInfo.getModelCompanies().get(x).getCompany_name());
                    modelCompany.setAddress1(modelCompanyInfo.getModelCompanies().get(x).getAddress1());
                    modelCompany.setAddress2(modelCompanyInfo.getModelCompanies().get(x).getAddress2());
                    modelCompany.setAddress3(modelCompanyInfo.getModelCompanies().get(x).getAddress3());
                    modelCompany.setCountry(modelCompanyInfo.getModelCompanies().get(x).getCountry());
                    modelCompany.setType(modelCompanyInfo.getModelCompanies().get(x).getType());
                    modelCompany.setBackground(modelCompanyInfo.getModelCompanies().get(x).getBackground());
                    modelCompany.setCreate_date(modelCompanyInfo.getModelCompanies().get(x).getCreate_date());
                    modelCompany.setUpdate_date(modelCompanyInfo.getModelCompanies().get(x).getUpdate_date());
                    modelCompany.setStatus(modelCompanyInfo.getModelCompanies().get(x).getStatus());

                    if (modelCompanyInfo.getModelCompanies().get(x).getAudit_history() != null) {
                        for (ModelSiteAuditHistory mc : modelCompanyInfo.getModelCompanies().get(x).getAudit_history()) {
                            ModelSiteAuditHistory modelSiteAuditHistory = new ModelSiteAuditHistory();
                            modelSiteAuditHistory.setCompany_id(company_id);
                            modelSiteAuditHistory.save();
                            Log.e("APICalls", "CompanySite:company name : " + modelCompany.getCompany_name());
                            //+ modelSiteAuditHistory.getModelSiteDates().size());
                            if (mc.getMajor_changes() != null) {
                                for (TemplateModelCompanyBackgroundMajorChanges mmc : mc.getMajor_changes()) {
                                    TemplateModelCompanyBackgroundMajorChanges majorChanges = new TemplateModelCompanyBackgroundMajorChanges();
                                    majorChanges.setCompany_id(company_id);
                                    majorChanges.setMajorchanges(mmc.getMajorchanges());
                                    majorChanges.setReport_id("0");
                                    majorChanges.save();
                                    Log.e("APICalls", " CompanySite:changes " + mmc.getMajorchanges());

                                }
                            }
                            if (mc.getModelSiteDates() != null) {
                                for (ModelSiteDate msd : mc.getModelSiteDates()) {
                                    ModelSiteDate modelSiteDate = new ModelSiteDate();
                                    modelSiteDate.setCompany_id(company_id);
                                    modelSiteDate.setInspection_date(msd.getInspection_date());
                                    modelSiteDate.save();
                                    Log.e("APICalls", " CompanySite:date" + msd.getInspection_date());

                                }
                            }
                            if (mc.getInspectors() != null)
                                for (TemplateModelCompanyBackgroundName msi : mc.getInspectors()) {
                                    TemplateModelCompanyBackgroundName modelSiteInspectors = new TemplateModelCompanyBackgroundName();
                                    modelSiteInspectors.setBgname(msi.getBgname());
                                    modelSiteInspectors.setCompany_id(company_id);
                                    modelSiteInspectors.save();
                                    Log.e("APICalls", " CompanySite:inspector" + msi.getBgname());
                                }
                        }
                    }
                    Log.e("APICalls", response.toString() + " CompanySite: " + modelCompanyInfo.getModelCompanies().
                            get(x).getCompany_name());

                    isSupplierExisting(modelCompany);
                }
                //modelSamples.getDateCreated()
            }

            @Override
            public void onFailure(Call<ModelCompanyInfo> call, Throwable t) {
                Log.e("CompanySite", "onFailure: " + t.getMessage());
            }
        });
    }

    public void apiTemplateList() {
        apiInterface = ApiClient.getApiClientTemplate().create(ApiInterface.class);
        Call<TemplateListModel> listCall = apiInterface.getDataList();
        listCall.enqueue(new Callback<TemplateListModel>() {
            @Override
            public void onResponse(Call<TemplateListModel> call, Response<TemplateListModel> response) {
                TemplateListModel templateListModel = response.body();
                ModelTemplates.executeQuery("UPDATE MODEL_TEMPLATES SET status='2' where status = 1", new String[]{});
                numberoftemplates = templateListModel.getTemplate_list().size();
                isdone = true;
                Log.e("numberoftemplates", "numberoftemplates : " + numberoftemplates);
                for (TemplateDetailsModel tdm : templateListModel.getTemplate_list()) {
                    final String templateid = tdm.getTemplate_id();
                    final String templateStatus = tdm.getStatus();

                    Call<ModelTemplates> listCall = ApiClient.getApiClientTemplate().
                            create(ApiInterface.class).getData(tdm.getTemplate_id() + ".json");
                    listCall.enqueue(new Callback<ModelTemplates>() {
                        @Override
                        public void onResponse(Call<ModelTemplates> call, Response<ModelTemplates> response) {
                            modelTemplates = response.body();
                            ModelTemplates modelTemplate = new ModelTemplates();

                                if (modelTemplates != null) {


                                    if (modelTemplates.getProductType() != null) {

                                        //get all template id in local db
                                        List<ModelTemplates> templateList = ModelTemplates.find
                                                (ModelTemplates.class, "status = '1' OR status = '2'",
                                                        new String[]{}, null, "", "");

                                        //if local db not match with api modified date
                                        for (ModelTemplates qid : templateList) {
                                            Log.i("TEMPLATE_LIST", "ID : " + qid.getTemplateID() + " Modified Date offline : " + qid.getDateUpdated()
                                                    + "ID : " + modelTemplates.getTemplateID() + " Modified Date API : " + modelTemplates.getDateUpdated());

                                            if (qid.getTemplateID().equals(modelTemplates.getTemplateID())) {
                                            /*Log.i("modified_date_template1", "local: " +
                                                    qid.getDateUpdated() + " api: " + modelTemplates.getDateUpdated());*/
                                                if (!qid.getDateUpdated().equals(modelTemplates.getDateUpdated())) {
                                                    Log.i("TEMPLATE_LIST", "WHEN HERE");

                                                    //delete template
                                                    ModelTemplates.executeQuery("DELETE FROM MODEL_TEMPLATES " +
                                                            "WHERE template_id = '" + qid.getTemplateID() + "'");
                                                    //test delete all question from element
                                                    ModelTemplateQuestionDetails.executeQuery("DELETE FROM MODEL_TEMPLATE_QUESTION_DETAILS "
                                                            + "WHERE templateid = '" + qid.getTemplateID() + "'");
                                                } else {
                                                    Log.i("modified_date_template2", "local: " +
                                                            qid.getDateUpdated() + " api: " + modelTemplates.getDateUpdated());
                                                }
                                            }
                                        }

                                        //modelTemplate.setTemplateID(modelTemplates.getTemplateID() + "");
                                        modelTemplate.setTemplateID(templateid);
                                        modelTemplate.setProductType(modelTemplates.getProductType() + "");
                                        modelTemplate.setTemplateName(modelTemplates.getTemplateName() + "");
                                        modelTemplate.setDateCreated(modelTemplates.getDateCreated() + "");
                                        modelTemplate.setDateUpdated(modelTemplates.getDateUpdated());
                                        modelTemplate.setModelTemplateElements(modelTemplates.getModelTemplateElements());
                                        modelTemplate.setModelTemplateActivities(modelTemplates.getModelTemplateActivities());
                                        modelTemplate.setStatus(templateStatus);


                                        Log.i("S T A T U S", "value : " + templateStatus + " --- " + modelTemplates.getTemplateName());
                                        ModelTemplateElements.deleteAll(ModelTemplateElements.class, "templateid = ?", new String[]{templateid});
                                        for (ModelTemplateElements mte : modelTemplates.getModelTemplateElements()) {
                                            mte.setTemplate_id(modelTemplates.getTemplateID() + "");
                                            if (isElementIDExisting(mte))
                                                mte.save();
                                            for (ModelTemplateQuestionDetails mteq : mte.getModelTemplateQuestionDetails()) {
                                                mteq.setElement_id(mte.getElement_id());
                                                mteq.setTemplate_id(mte.getTemplate_id() + "");
                                                mteq.setRequired_remarks(mteq.getRequired_remarks());
                                                Log.i("REMARKS-I", "REQUIRED : " + mteq.getRequired_remarks() +
                                                        " ID : " + templateid + " EXIST: " + isQuestionIDExisting(mteq));

                                                if (isQuestionIDExisting(mteq)) {
                                                    Log.i("REMARKS-II", "REQUIRED : " + mteq.getQuestion_id() +
                                                            " ID : " + templateid);
                                                    mteq.save();
                                                } else {
                                                    Log.i("REMARKS-III", "REQUIRED : " + mteq.getQuestion_id() + " ID : " + templateid);
                                                    updateQuestion(mteq);
                                                }
                                            }
                                        }

                                        for (ModelTemplateActivities mta : modelTemplates.getModelTemplateActivities()) {
                                            mta.setTemplate_id(modelTemplates.getTemplateID() + "");
                                            if (isActivityIDExisting(mta)) {
                                                mta.save();
                                            }
                                            for (ModelTemplateSubActivities mtsa : mta.getModelTemplateSubActivities()) {
                                                mtsa.setTemplate_id(mta.getTemplate_id());
                                                if (isSubActivityIDExisting(mtsa)) {
                                                    mtsa.save();
                                                }
                                            }
                                        }

                                        isTemplateExisting(modelTemplate);
                                    }
                                }
                                Log.e("templatesdownloaded", "templatesdownloaded : " + ++templatesdownloaded);

                        }

                        @Override
                        public void onFailure(Call<ModelTemplates> call, Throwable t) {
//                            Log.e("testing", t.getMessage());
                            Log.e("templatesdownloaded", "templatesdownloaded : " + ++templatesdownloaded);
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<TemplateListModel> call, Throwable t) {
                isdone = true;
                Log.e("testing", t.getMessage());
            }
        });

    }

    private void updateQuestion(ModelTemplateQuestionDetails mteq) {
        String id = mteq.getElement_id();
        Log.i("ARGU-TEMPLATE", "CHECKER E " + id);
        Log.i("ARGU-TEMPLATE", "CHECKER T " + mteq.getTemplate_id());
        Log.i("ARGU-TEMPLATE", "CHECKER Q " + mteq.getQuestion_id());

        //get question list via template id
        List<ModelTemplateQuestionDetails> questionList = ModelTemplateQuestionDetails.find
                (ModelTemplateQuestionDetails.class, "templateid = ?", mteq.getTemplate_id());
        Log.i("ARGU-TEMPLATE", "QUESTION COUNT DB " + questionList.size());

        //get template question details on local db via element id, template id and question id
        ModelTemplateQuestionDetails template = (ModelTemplateQuestionDetails.find
                (ModelTemplateQuestionDetails.class, "elementid = ? AND templateid = ? AND questionid = ?",
                        id, mteq.getTemplate_id(), mteq.getQuestion_id())).get(0);

        template.setQuestion_id(mteq.getQuestion_id());
        template.setQuestion(mteq.getQuestion());
        template.setDefault_yes(mteq.getDefault_yes());
        template.setRequired_remarks(mteq.getRequired_remarks());
        template.setElement_id(mteq.getElement_id());
        template.setTemplate_id(mteq.getTemplate_id());

        template.save();
    }

    public void apiCategory() {
        //Category
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<ModelCategoryInfo> modelCategoryInfoCall = apiInterface.getCategory();
        modelCategoryInfoCall.enqueue(new Callback<ModelCategoryInfo>() {
            @Override
            public void onResponse(Call<ModelCategoryInfo> call, Response<ModelCategoryInfo> response) {
                modelCategoryInfo = response.body();
                ModelCategory.deleteAll(ModelCategory.class);
                for (int x = 0; x < modelCategoryInfo.getModelCategories().size(); x++) {
                    ModelCategory modelCategory = new ModelCategory();
                    modelCategory.setCategory_id(modelCategoryInfo.getModelCategories().get(x).getCategory_id());
                    modelCategory.setCategory_name(modelCategoryInfo.getModelCategories().get(x).getCategory_name());
                    modelCategory.setCreate_date(modelCategoryInfo.getModelCategories().get(x).getCreate_date());
                    modelCategory.setUpdate_date(modelCategoryInfo.getModelCategories().get(x).getUpdate_date());
                    modelCategory.setStatus(modelCategoryInfo.getModelCategories().get(x).getStatus());
                    isCategoryExisting(modelCategory);
                }

                Log.e("testing", response.toString() + " Category: " + modelCategoryInfo.getModelCategories().get(0).getCategory_name());
            }

            @Override
            public void onFailure(Call<ModelCategoryInfo> call, Throwable t) {
                Log.e("testing", t.getMessage());
            }
        });
    }

    public void apiProduct() {
        //Product
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<ModelProductInfo> modelProductInfoCall = apiInterface.getProducts();
        modelProductInfoCall.enqueue(new Callback<ModelProductInfo>() {
            @Override
            public void onResponse(Call<ModelProductInfo> call, Response<ModelProductInfo> response) {
                modelProductInfo = response.body();
                ModelProduct.deleteAll(ModelProduct.class);
                for (int x = 0; x < modelProductInfo.getModelProducts().size(); x++) {
                    ModelProduct product = new ModelProduct();
                    product.setProduct_id(modelProductInfo.getModelProducts().get(x).getProduct_id());
                    product.setCompany_id(modelProductInfo.getModelProducts().get(x).getCompany_id());
                    product.setType(modelProductInfo.getModelProducts().get(x).getType());
                    product.setProduct_name(modelProductInfo.getModelProducts().get(x).getProduct_name());
                    product.setCreate_date(modelProductInfo.getModelProducts().get(x).getCreate_date());
                    product.setUpdate_date(modelProductInfo.getModelProducts().get(x).getUpdate_date());
                    product.setStatus(modelProductInfo.getModelProducts().get(x).getStatus());

                    Log.e("testing", response.toString() + " Product: " + product.getProduct_name());
                    isProductExisting(product);
                }
            }

            @Override
            public void onFailure(Call<ModelProductInfo> call, Throwable t) {
                Log.e("testing", t.getMessage());
            }
        });
    }

    public void apiTypeAudit() {
        Call<ModelTypeAuditInfo> modelTypeAuditInfoCall = apiInterface.getTypeAudit();
        modelTypeAuditInfoCall.enqueue(new Callback<ModelTypeAuditInfo>() {
            @Override
            public void onResponse(Call<ModelTypeAuditInfo> call, Response<ModelTypeAuditInfo> response) {
                modelTypeAuditInfo = response.body();
                ModelTypeAudit.deleteAll(ModelTypeAudit.class);
                for (int x = 0; x < modelTypeAuditInfo.getModelTypeAudits().size(); x++) {
                    ModelTypeAudit typeAuditModel = new ModelTypeAudit();
                    typeAuditModel.setScope_id(modelTypeAuditInfo.getModelTypeAudits().get(x).getScope_id());
                    typeAuditModel.setScope_name(modelTypeAuditInfo.getModelTypeAudits().get(x).getScope_name());
                    typeAuditModel.setCreate_date(modelTypeAuditInfo.getModelTypeAudits().get(x).getCreate_date());
                    typeAuditModel.setUpdate_date(modelTypeAuditInfo.getModelTypeAudits().get(x).getUpdate_date());
                    typeAuditModel.setStatus(modelTypeAuditInfo.getModelTypeAudits().get(x).getStatus());
                    Log.e("testing", response.toString() + " Company: " + typeAuditModel.getScope_name());
                    isTypeAuditExisting(typeAuditModel);
                }
            }

            @Override
            public void onFailure(Call<ModelTypeAuditInfo> call, Throwable t) {
                Log.e("testing", t.getMessage());
            }
        });
    }

    public void apiDisposition() {
        Call<ModelDispositionInfo> modelDispositionInfoCall = apiInterface.getDisposition();
        modelDispositionInfoCall.enqueue(new Callback<ModelDispositionInfo>() {
            @Override
            public void onResponse(Call<ModelDispositionInfo> call, Response<ModelDispositionInfo> response) {
                modelDispositionInfo = response.body();
                ModelDisposition.deleteAll(ModelDisposition.class);
                for (int x = 0; x < modelDispositionInfo.getDisposition().size(); x++) {
                    ModelDisposition modelDisposition = new ModelDisposition();
                    modelDisposition.setDisposition_id(modelDispositionInfo.getDisposition().get(x).getDisposition_id());
                    modelDisposition.setDisposition_name(modelDispositionInfo.getDisposition().get(x).getDisposition_name());
                    modelDisposition.setCreate_date(modelDispositionInfo.getDisposition().get(x).getCreate_date());
                    modelDisposition.setUpdate_date(modelDispositionInfo.getDisposition().get(x).getUpdate_date());
                    modelDisposition.setStatus(modelDispositionInfo.getDisposition().get(x).getStatus());
                    Log.e("testing", response.toString() + " Disposition: " + modelDisposition.getDisposition_name());
                    isDispositionExisting(modelDisposition);
                }

            }

            @Override
            public void onFailure(Call<ModelDispositionInfo> call, Throwable t) {
                Log.e("testing", "asdasd" + t.getMessage());
            }
        });
    }

    public void apiClassification() {
        Call<ModelClassificationInfo> modelClassificationInfoCall = apiInterface.getClassifications();
        modelClassificationInfoCall.enqueue(new Callback<ModelClassificationInfo>() {
            @Override
            public void onResponse(Call<ModelClassificationInfo> call, Response<ModelClassificationInfo> response) {
                modelClassificationInfo = response.body();
                ModelClassification.deleteAll(ModelClassification.class);
                for (int x = 0; x < modelClassificationInfo.getModelClassifications().size(); x++) {
                    ModelClassification modelClassification = new ModelClassification();
                    modelClassification.setClassification_id(modelClassificationInfo.getModelClassifications().get(x).getClassification_id());
                    modelClassification.setClassification_name(modelClassificationInfo.getModelClassifications().get(x).getClassification_name());
                    modelClassification.setCreate_date(modelClassificationInfo.getModelClassifications().get(x).getCreate_date());
                    modelClassification.setUpdate_date(modelClassificationInfo.getModelClassifications().get(x).getUpdate_date());
                    modelClassification.setStatus(modelClassificationInfo.getModelClassifications().get(x).getStatus());
                    modelClassification.save();
                    Log.e("testing", response.toString() + " Classification: " + modelClassification.toString());
                    if (modelClassificationInfo.getModelClassifications().get(x).getCategory() != null) {
                        for (ModelClassificationCategory mcc : modelClassificationInfo.getModelClassifications().get(x).getCategory()) {
                            Log.e("testing", "ClassificationCategory: " + mcc.getCategory_id());
                            mcc.setClassification_id(modelClassification.getClassification_id());
                            mcc.setClassification_name(modelClassification.getClassification_name());
                            mcc.setCategory_id(mcc.getCategory_id());
                            mcc.save();
                        }
                    }
//                    isDispositionExisting(modelClassification);
                }
            }

            @Override
            public void onFailure(Call<ModelClassificationInfo> call, Throwable t) {
                Log.e("testing", t.getMessage());
            }
        });
    }


    //Distribution
    public void apiDistribution() {
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<ModelDistributionInfo> modelDistributionInfoCall = apiInterface.getDistribution();
        modelDistributionInfoCall.enqueue(new Callback<ModelDistributionInfo>() {
            @Override
            public void onResponse(Call<ModelDistributionInfo> call, Response<ModelDistributionInfo> response) {
                modelDistributionInfo = response.body();
                ModelDistribution.deleteAll(ModelDistribution.class);
                for (int x = 0; x < modelDistributionInfo.getModelDistributions().size(); x++) {
                    ModelDistribution modelDistribution = new ModelDistribution();
                    modelDistribution.setDistribution_id(modelDistributionInfo.getModelDistributions().get(x).getDistribution_id());
                    modelDistribution.setDistribution_name(modelDistributionInfo.getModelDistributions().get(x).getDistribution_name());
                    modelDistribution.setCreate_date(modelDistributionInfo.getModelDistributions().get(x).getCreate_date());
                    modelDistribution.setUpdate_date(modelDistributionInfo.getModelDistributions().get(x).getUpdate_date());
                    modelDistribution.setStatus(modelDistributionInfo.getModelDistributions().get(x).getStatus());
                    Log.e("testing", response.toString() + " distribution report: " + modelDistribution.getDistribution_name());
                    isDistributionExisting(modelDistribution);
                }
            }

            @Override
            public void onFailure(Call<ModelDistributionInfo> call, Throwable t) {
                Log.e("testing", t.getMessage());
            }
        });
    }

    public void apiAuditReports() {
        apiInterface = ApiClient.getApiClientAuditReport().create(ApiInterface.class);

        Call<ModelAuditReportsList> templateListModelCall = apiInterface.getAuditReportDataList();
        templateListModelCall.enqueue(new Callback<ModelAuditReportsList>() {
            @Override
            public void onResponse(Call<ModelAuditReportsList> call, Response<ModelAuditReportsList> response) {
                ModelAuditReportsList modelAuditReportsList = response.body();
//                numberoftemplates2 = modelAuditReportsList.getAudit_report_list().size();
//                isdone2 = true;
                for (ModelAuditReportDetails mar : modelAuditReportsList.getAudit_report_list()) {
                    String reportId = mar.getReport_id();
                    final String modifiedDate = mar.getModified_date();
                    final String status = mar.getStatus();

                    List<ModelAuditReports> auditReportsList = ModelAuditReports.find(
                            ModelAuditReports.class, "reportid = ? AND modifieddate = ?", reportId, modifiedDate);
                    if (auditReportsList.size() > 0) {
                        Log.i("API_AUDIT_REPORT", "Existing :" + mar.toString());
                        continue;
                    }

                    Log.i("API_AUDIT_REPORT", "START 1 :" + mar.toString() + " size : " + auditReportsList.size());
                    Call<ModelAuditReports> report = ApiClient.getApiClientAuditReport()
                            .create(ApiInterface.class).getAuditReport(reportId + ".json");
//                    final Call<ModelAuditReports> report = apiInterface.getAuditReport(reportId);
                    report.enqueue(new Callback<ModelAuditReports>() {
                        @Override
                        public void onResponse(Call<ModelAuditReports> call, Response<ModelAuditReports> response) {
                            modelAuditReports = response.body();
                            Log.i("AUDIT REPORT", "START 2");
                            if (modelAuditReports != null) {
                                Log.i("AUDIT REPORT", "START 3");

                                Log.e("APICalls", "Model Audit Reports : " + modelAuditReports.getReport_id());

                                ModelAuditReports reports = new ModelAuditReports();

                                String report_id = modelAuditReports.getReport_id();
                                String version = modelAuditReports.getVersion();
                                String modified_date = modelAuditReports.getModified_date();
                                Log.i("API_AUDIT_REPORT", "version shit : " + version + " date : " + modified_date);

                                reports.setReport_id(report_id);
                                reports.setReport_no(modelAuditReports.getReport_no());
                                reports.setCompany_id(modelAuditReports.getCompany_id());
                                reports.setOther_activities(modelAuditReports.getOther_activities());
//                                reports.setP_inspection_date_1(modelAuditReports.getP_inspection_date_1());
//                                reports.setP_inspection_date_2(modelAuditReports.getP_inspection_date_2());
                                reports.setTemplate_id(modelAuditReports.getTemplate_id());
                                reports.setAuditor_id(modelAuditReports.getAuditor_id());
                                reports.setAudit_close_date(modelAuditReports.getAudit_close_date());
                                reports.setOther_issues(modelAuditReports.getOther_issues());
                                reports.setOther_issues_executive(modelAuditReports.getOther_issues_executive());
                                reports.setAudited_areas(modelAuditReports.getAudited_areas());
                                reports.setAreas_to_consider(modelAuditReports.getAreas_to_consider());
                                reports.setWrap_date(modelAuditReports.getWrap_date());
//                                reports.setTranslator(modelAuditReports.getTranslator());
                                reports.setReviewer_id(modelAuditReports.getReviewer_id());
                                reports.setApprover_id(modelAuditReports.getApprover_id());
                                Log.i("STATUS-!!!!", status);
                                reports.setStatus(status);
                                reports.setVersion(version);
                                reports.setHead_lead(modelAuditReports.getHead_lead());
                                reports.setModified_date(modified_date);

                                if (version != null) {
                                    Log.i("AUDIT-REPORT", "START 4 ID: " + report_id);
//                                    if (checkAuditReport(report_id, version)) {
//                                        Log.i("AUDIT REPORT", "START 5");
                                    ModelDateOfAudit.deleteAll(ModelDateOfAudit.class, "reportid = ?", report_id);
                                    TemplateModelAuditors.deleteAll(TemplateModelAuditors.class, "reportid = ?", report_id);
                                    TemplateModelScopeAudit.deleteAll(TemplateModelScopeAudit.class, "reportid = ?", report_id);
                                    TemplateModelScopeAuditInterest.deleteAll(TemplateModelScopeAuditInterest.class, "reportid = ?", report_id);
                                    TemplateModelPreAuditDoc.deleteAll(TemplateModelPreAuditDoc.class, "reportid = ?", report_id);
                                    TemplateModelReference.deleteAll(TemplateModelReference.class, "reportid = ?", report_id);
                                    TemplateModelTranslator.deleteAll(TemplateModelTranslator.class, "reportid = ?", report_id);
                                    TemplateModelCompanyBackgroundMajorChanges.deleteAll(TemplateModelCompanyBackgroundMajorChanges.class, "reportid = ?", report_id);
//                                        TemplateModelCompanyBackgroundName.deleteAll(TemplateModelCompanyBackgroundName.class, "reportid = ?", report_id);
                                    TemplateModelPersonelMetDuring.deleteAll(TemplateModelPersonelMetDuring.class, "reportid = ?", report_id);
                                    TemplateModelSummaryRecommendation.deleteAll(TemplateModelSummaryRecommendation.class, "reportid = ?", report_id);
                                    TemplateModelDistributionList.deleteAll(TemplateModelDistributionList.class, "reportid = ?", report_id);
                                    TemplateModelPresentDuringMeeting.deleteAll(TemplateModelPresentDuringMeeting.class, "reportid = ?", report_id);
                                    TemplateModelDistributionOthers.deleteAll(TemplateModelDistributionOthers.class, "reportid = ?", report_id);
                                    ModelReportQuestion.deleteAll(ModelReportQuestion.class, "reportid = ?", report_id);
                                    ModelReportActivities.deleteAll(ModelReportActivities.class, "reportid = ?", report_id);
                                    ModelReportSubActivities.deleteAll(ModelReportSubActivities.class, "reportid = ?", report_id);
                                    TemplateModelOtherIssuesAudit.deleteAll(TemplateModelOtherIssuesAudit.class, "reportid = ?", report_id);
                                    TemplateModelOtherIssuesExecutive.deleteAll(TemplateModelOtherIssuesExecutive.class, "reportid = ?", report_id);

                                    saveListsOfAuditReport(report_id);

//                                    } else {
//                                        saveListsOfAuditReport(report_id);
//                                    }
                                }

                                Log.e("ARGUNEW-NEW", response.toString() + " AuditReports: " + modelAuditReports.getReport_id());
                                isAuditReportExisting(reports, modifiedDate);

                            }
//                            Log.e("templatesdownloaded", "templatesdownloaded2 : " + ++templatesdownloaded2);
                        }

                        @Override
                        public void onFailure(Call<ModelAuditReports> call, Throwable t) {
                            Log.e("ModelAuditReportsasd", "asdasd" + t.getMessage());
//                            Log.e("templatesdownloaded", "templatesdownloaded2 : " + ++templatesdownloaded2);
                        }
                    });
                }


            }

            private void saveListsOfAuditReport(String report_id) {


                for (TemplateModelOtherIssuesAudit tmt : modelAuditReports.getOther_issues()) {
                    tmt.setReport_id(report_id);
                    tmt.save();
                    Log.e("APICalls", "issuesAudit : " + tmt.getOther_issues_audit());
                }

                for (TemplateModelOtherIssuesExecutive tmt : modelAuditReports.getOther_issues_executive()) {
                    tmt.setReport_id(report_id);
                    tmt.save();
                    Log.e("APICalls", "issuesExecutive : " + tmt.getOther_issues_executive());
                }

                for (TemplateModelTranslator tmt : modelAuditReports.getTranslators()) {
                    tmt.setReport_id(report_id);
                    tmt.save();
                    Log.e("APICalls", "translators : " + tmt.getTranslator());
                }
                for (ModelDateOfAudit mda : modelAuditReports.getDate_of_audit()) {
                    mda.setReport_id(report_id);
//                    mda.setDateOfAudit(mda.getDateOfAudit());
                    mda.save();
                    Log.e("APICalls", "Audit dates : " + mda.getDateOfAudit());
                }
                for (TemplateModelAuditors mrca : modelAuditReports.getCo_auditor_id()) {
                    mrca.setReport_id(report_id);
//                    mrca.setAuditor_id(mrca.getAuditor_id());
                    mrca.save();
                    Log.e("APICalls", "Auditors : " + mrca.getAuditor_id());
                }
                //-
                int counter = 0;
                for (TemplateModelScopeAuditCopy msa : modelAuditReports.getScope()) {
                    TemplateModelScopeAudit tmsa = new TemplateModelScopeAudit();
                    tmsa.setReport_id(report_id);
                    tmsa.setScope_id(msa.getScope_id());
                    tmsa.setScope_detail(msa.getScope_detail());
                    Log.e("API-SCOPE", "Scope Audit : " + msa.getScope_detail() + " ID: " + report_id);
                    for (TemplateModelScopeAuditInterest tmsai : msa.getTemplateModelScopeAuditInterests()) {
                        tmsai.setReport_id(report_id);
                        tmsai.setProduct_id(tmsai.getProduct_id());
                        tmsai.setDisposition_id(tmsai.getDisposition_id());
                        tmsai.setAudit_id(counter + "");
                        tmsai.save();
                        Log.e("API-SCOPE-PRODUCT", "Scope Audit interest : " + tmsai.getProduct_id() + " ID: " + report_id);
                    }
                    counter++;
                    tmsa.save();
                }

//                for (TemplateModelScopeAudit msa : modelAuditReports.getScope()) {
//                  msa.setReport_id(report_id);
//                  msa.setScope_id(msa.getScope_id());
//                  msa.setScope_detail(msa.getScope_detail());
//                  Log.e("APICalls", "Scope Audit : " + msa.getScope_detail());
//                  for (TemplateModelScopeAuditInterest tmsai : msa.getTemplateModelScopeAuditInterests()) {
//                      tmsai.setReport_id(report_id);
//                      tmsai.setProduct_id(tmsai.getProduct_id());
//                      tmsai.save();
//                      Log.e("APICalls", "Scope Audit interest : " + tmsai.getProduct_id());
//                  }
//                  msa.save();
//              }
                //disposition
//-
                for (TemplateModelPreAuditDoc mpad : modelAuditReports.getPre_audit_documents()) {
                    mpad.setReport_id(report_id);
                    mpad.setPreaudit(mpad.getPreaudit());
                    mpad.save();

                    Log.e("APICalls", "Pre Audit : " + mpad.getPreaudit());
                }

                //-
                for (TemplateModelReference mr : modelAuditReports.getReferencess()) {
                    mr.setReport_id(report_id);
                    mr.setCertification(mr.getCertification());
                    mr.setBody(mr.getBody());
                    mr.setNumber(mr.getNumber());
                    mr.setValidity(mr.getValidity());
                    mr.setIssue_date(mr.getIssue_date());
                    mr.save();


                    Log.e("APICalls", "References : " + mr.getBody());
                }

                //-
                for (TemplateModelCompanyBackgroundMajorChanges mmc : modelAuditReports.getInspection()) {
                    mmc.setReport_id(report_id);
                    mmc.setMajorchanges(mmc.getMajorchanges());
                    mmc.save();

                    Log.e("APICalls", "company major changes : " + mmc.getMajorchanges());
                }

                //-
//                for (TemplateModelCompanyBackgroundName mmn : modelAuditReports.getInspector()) {
//                    mmn.setReport_id(report_id);
//                    mmn.setBgname(mmn.getBgname());
//                    mmn.save();
//
//                    Log.e("APICalls", "Company bgname : " + mmn.getBgname());
//                }

                //-
                for (TemplateModelPersonelMetDuring mpmd : modelAuditReports.getPersonnel()) {
                    mpmd.setReport_id(report_id);
                    mpmd.setName(mpmd.getName());
                    mpmd.setPosition(mpmd.getPosition());
                    mpmd.save();

                    Log.e("APICalls", "Personnel met : " + mpmd.toString());
                }

                //-
                for (TemplateModelSummaryRecommendation msr : modelAuditReports.getRecommendation()) {
                    msr.setReport_id(report_id);
                    msr.setElement_id(msr.getElement_id());
                    msr.setRemarks(msr.getRemarks());
                    msr.save();

                    Log.e("APICalls", "Summary Recommendation : " + msr.toString());
                }

                for (TemplateModelDistributionList mdl : modelAuditReports.getDistribution()) {
                    mdl.setReport_id(report_id);
                    mdl.setDistribution_id(mdl.getDistribution_id());
                    mdl.save();
                    Log.e("APICalls", "Distri list : " + mdl.toString());
                }

                for (TemplateModelPresentDuringMeeting mpdm : modelAuditReports.getPresent_during_meeting()) {
                    mpdm.setReport_id(report_id);
                    mpdm.setName(mpdm.getName());
                    mpdm.setPosition(mpdm.getPosition());
                    mpdm.save();
                    Log.e("APICalls", "Present During meeting : " + mpdm.toString());
                }

                //-
                for (TemplateModelDistributionOthers mdl : modelAuditReports.getOther_distribution()) {
                    mdl.setReport_id(report_id);
                    if (mdl.getDistribution_other().equals("null")) {
                        mdl.setDistribution_other("");
                    }
                    mdl.setDistribution_other(mdl.getDistribution_other());
                    mdl.save();
                    Log.e("APICalls", "Distri others : " + mdl.toString());
                }

                for (ModelReportQuestion mrq : modelAuditReports.getQuestion()) {
                    mrq.setReport_id(report_id);

                    mrq.setQuestion_id(mrq.getQuestion_id());
                    mrq.setAnswer_id(mrq.getAnswer_id());
                    mrq.setCategory_id(mrq.getCategory_id());
                    mrq.setAnswer_details(mrq.getAnswer_details());
                    mrq.save();

                    Log.e("APICalls", "Report question : " + mrq.toString());
                }


//                            List<ModelReportActivities> activities;
                if (modelAuditReports.getActivities() != null) {
                    for (ModelReportActivities mra : modelAuditReports.getActivities()) {
                        mra.setReport_id(report_id);
                        mra.setActivity_id(mra.getActivity_id());
//                        if (mra.getSub_activities().size() < 1)
                        mra.setCheck(true);
                        mra.save();
                        for (ModelReportSubActivities mrsa : mra.getSub_activities()) {
                            mrsa.setReport_id(report_id);
                            mrsa.setActivity_id(mra.getActivity_id());
                            mrsa.setCheck(true);
                            mrsa.save();
                            Log.e("APICalls", "Report sub activities : " + mrsa.toString());
                        }
                        Log.e("APICalls", "Report activities : " + mra.toString());
                    }
                }
            }

            @Override
            public void onFailure(Call<ModelAuditReportsList> call, Throwable throwable) {
//                isdone2 = true;
                Log.e("ModelAuditReportsList", throwable.getMessage());
            }
        });


    }

//  public void apiAuditReports() {
//        apiInterface = ApiClient.getApiClientAuditReport().create(ApiInterface.class);
//        String reportId = "147";
//        final Call<ModelReport> report = apiInterface.getAuditReport(reportId);
//        report.enqueue(new Callback<ModelReport>() {
//            @Override
//            public void onResponse(Call<ModelReport> call, Response<ModelReport> response) {
//                modelReport = response.body();
//
//                ModelAuditReports reports = new ModelAuditReports();
//                reports.setReport_id(modelReport.getReport_id());
//                reports.setReport_no(modelReport.getReport_no());
//                reports.setTemplate_id("485");//modelAuditReports.getTemplate_id());
//                reports.setCompany_id(modelReport.getCompany_id());
//                reports.setOther_activities(modelReport.getOther_activities());
//                reports.setAudit_date_1(modelReport.getAudit_date_1());
//                reports.setAudit_date_2(modelReport.getAudit_date_2());
//                reports.setP_inspection_date_1(modelReport.getP_inspection_date_1());
//                reports.setP_inspection_date_2(modelReport.getP_inspection_date_2());
//                reports.setAuditor_id(modelReport.getAuditor_id());
//
////                for (int x = 0; x < modelAuditReports.getQuestion().size(); x++) {
////
////                }
//                Log.e("testing", response.toString() + " AuditReports: " + modelAuditReports.getQuestion().
//                        get(0).getAnswer_details());
//                isAuditReportExisting(reports);
//
//
//            }
//
//            @Override
//            public void onFailure(Call<ModelReport> call, Throwable t) {
//                Log.e("testing", t.getMessage());
//            }
//        });
//    }
    // checker

    //Approver
    public void isAppoverExisting(ApproverModel approverModel) {
        String id = approverModel.getApprover_id();
        List<ApproverModel> approverList = ApproverModel.find(ApproverModel.class, "approverid = ?", id);
        int size = approverList.size();

        if (size > 0) {
            boolean found = false;
            String date = "";
            for (int count = 0; count < size; count++) {
                if (approverList.get(count).getApprover_id().equals(id)) {
                    found = true;
                    date = approverList.get(count).getUpdate_date();
                }
            }

            if (found) {
                if (!date.equals(approverModel.getUpdate_date()))
                    updateDataApprover(approverModel);
            } else {
                approverModel.save();
                approverList.add(approverModel);
            }

        } else {
            approverModel.save();
            approverList.add(approverModel);
        }
    }

    public void updateDataApprover(ApproverModel approverModel) {
        String rowId = approverModel.getApprover_id();
        ApproverModel approverModelUpdate = (ApproverModel.find(ApproverModel.class, "approverid = ?", String.valueOf(rowId))).get(0);
        approverModelUpdate.setApprover_id(approverModel.getApprover_id());
        approverModelUpdate.setFirstname(approverModel.getFirstname());
        approverModelUpdate.setMiddlename(approverModel.getMiddlename());
        approverModelUpdate.setLastname(approverModel.getLastname());
        approverModelUpdate.setDesignation(approverModel.getDesignation());
        approverModelUpdate.setCompany(approverModel.getCompany());
        approverModelUpdate.setDepartment(approverModel.getDepartment());
        approverModelUpdate.setCreate_date(approverModel.getCreate_date());
        approverModelUpdate.setUpdate_date(approverModel.getUpdate_date());
        approverModelUpdate.setEmail(approverModel.getEmail());
        approverModelUpdate.setStatus(approverModel.getStatus());
        approverModelUpdate.save();
    }

    //Auditors
    public void updateDataAuditors(AuditorsModel auditorsModel) {
        String rowId = auditorsModel.getAuditor_id();
        Log.i("ARGU", "CHECKER " + rowId);
        AuditorsModel auditorsModelUpdate = (AuditorsModel.find(AuditorsModel.class, "auditorid = ?", String.valueOf(rowId))).get(0);
        auditorsModelUpdate.setAuditor_id(auditorsModel.getAuditor_id());
        auditorsModelUpdate.setFname(auditorsModel.getFname());
        auditorsModelUpdate.setMname(auditorsModel.getMname());
        auditorsModelUpdate.setLname(auditorsModel.getLname());
        auditorsModelUpdate.setDesignation(auditorsModel.getDesignation());
        auditorsModelUpdate.setCompany(auditorsModel.getCompany());
        auditorsModelUpdate.setDepartment(auditorsModel.getDepartment());
        auditorsModelUpdate.setCreate_date(auditorsModel.getCreate_date());
        auditorsModelUpdate.setUpdate_date(auditorsModel.getUpdate_date());
        auditorsModelUpdate.setEmail(auditorsModel.getEmail());
        auditorsModelUpdate.setStatus(auditorsModel.getStatus());
        auditorsModelUpdate.save();
    }

    public void isAuditorExisting(AuditorsModel auditorsModel) {
        String id = auditorsModel.getAuditor_id();
        List<AuditorsModel> auditorsList = AuditorsModel.find(AuditorsModel.class, "auditorid = ?", id);
        int size = auditorsList.size();
        Log.i("ARGU", "size " + size);

        if (size > 0) {
            boolean found = false;
            String date = "";
            for (int count = 0; count < size; count++) {
                if (auditorsList.get(count).getAuditor_id().equals(id)) {
                    date = auditorsList.get(count).getUpdate_date();
                    found = true;
                }
            }

            if (found) {
                Log.i("ARGULOOP", "AUDITOR UPDATE");
                if (!date.equals(auditorsModel.getUpdate_date()))
                    updateDataAuditors(auditorsModel);
            } else {
                Log.i("ARGULOOP", "SAVE");
                auditorsModel.save();
                auditorsList.add(auditorsModel);
            }

        } else {
            Log.i("ARGU", "SAVE - SIZE 0");
            auditorsModel.save();
            auditorsList.add(auditorsModel);
        }
    }

    //Reviewer
    public void updateDataReviewer(ReviewerModel reviewerModel) {
        String rowId = reviewerModel.getReviewer_id();
        Log.i("ARGU", "CHECKER " + rowId);
        ReviewerModel reviewerModelUpdate = (ReviewerModel.find(ReviewerModel.class, "reviewerid = ?", String.valueOf(rowId))).get(0);
        reviewerModelUpdate.setReviewer_id(reviewerModel.getReviewer_id());
        reviewerModelUpdate.setFirstname(reviewerModel.getFirstname());
        reviewerModelUpdate.setMiddlename(reviewerModel.getMiddlename());
        reviewerModelUpdate.setLastname(reviewerModel.getLastname());
        reviewerModelUpdate.setDesignation(reviewerModel.getDesignation());
        reviewerModelUpdate.setCompany(reviewerModel.getCompany());
        reviewerModelUpdate.setDepartment(reviewerModel.getDepartment());
        reviewerModelUpdate.setCreate_date(reviewerModel.getCreate_date());
        reviewerModelUpdate.setUpdate_date(reviewerModel.getUpdate_date());
        reviewerModelUpdate.setEmail(reviewerModel.getEmail());
        reviewerModelUpdate.setStatus(reviewerModel.getStatus());
        reviewerModelUpdate.save();
    }

    public void isReviewerExisting(ReviewerModel reviewerModel) {
        String id = reviewerModel.getReviewer_id();
        Log.i("ARGU", "CHECKER " + id);
        List<ReviewerModel> reviewerList = ReviewerModel.find(ReviewerModel.class, "reviewerid = ?", id);
        int size = reviewerList.size();

        if (size > 0) {
            boolean found = false;
            String date = "";
            for (int count = 0; count < size; count++) {
                if (reviewerList.get(count).getReviewer_id().equals(id)) {
                    date = reviewerList.get(count).getUpdate_date();
                    found = true;
                }
            }
            if (found) {
                Log.i("ARGULOOP", "REVIEWER UPDATE");
                if (!date.equals(reviewerModel.getUpdate_date()))
                    updateDataReviewer(reviewerModel);
            } else {
                Log.i("ARGULOOP", "SAVE");
                reviewerModel.save();
                reviewerList.add(reviewerModel);
            }
        } else {
            Log.i("ARGU", "SAVE - SIZE 0");
            reviewerModel.save();
            reviewerList.add(reviewerModel);
        }
    }

    //Supplier
    public void updateDataSupplier(ModelCompany modelCompany) {
        String rowId = modelCompany.getCompany_id();
        Log.i("ARGU", "CHECKER " + rowId);
        ModelCompany company = (ModelCompany.find(ModelCompany.class, "companyid = ?", String.valueOf(rowId))).get(0);
        company.setCompany_id(modelCompany.getCompany_id());
        company.setCompany_name(modelCompany.getCompany_name());
        company.setAddress1(modelCompany.getAddress1());
        //supplierModelUpdate.setAddress2(supplierModel.getAddress2());
        company.setAddress2(modelCompany.getAddress2());
        company.setAddress3(modelCompany.getAddress3());
        company.setCountry(modelCompany.getCountry());
        company.setBackground(modelCompany.getBackground());
        company.setCreate_date(modelCompany.getCreate_date());
        company.setUpdate_date(modelCompany.getUpdate_date());
        company.setStatus(modelCompany.getStatus());
        company.save();
    }

    public void isSupplierExisting(ModelCompany modelCompany) {
        String id = modelCompany.getCompany_id();
        Log.i("ARGU", "CHECKER " + id);
        List<ModelCompany> supplierList = ModelCompany.find(ModelCompany.class, "companyid = ?", id);
        int size = supplierList.size();

        if (size > 0) {
            boolean found = false;
            String date = "";
            for (int count = 0; count < size; count++) {
                if (supplierList.get(count).getCompany_id().equals(id)) {
                    date = supplierList.get(count).getUpdate_date();
                    found = true;
                }
            }

            if (found) {
                Log.i("ARGULOOP", "SITE UPDATE");
                if (!date.equals(modelCompany.getUpdate_date()))
                    updateDataSupplier(modelCompany);
//                    auditorsAdapter.notifyDataSetChanged();
            } else {
                Log.i("ARGULOOP", "SAVE");
                modelCompany.save();
                supplierList.add(modelCompany);
            }

        } else {
            Log.i("ARGU", "SAVE - SIZE 0");
            modelCompany.save();
            supplierList.add(modelCompany);
        }
    }

    //Category
    public void updateDataCategory(ModelCategory modelCategory) {
        String rowId = modelCategory.getCategory_id();
        Log.i("ARGU", "CHECKER " + rowId);
        ModelCategory category = (ModelCategory.find(ModelCategory.class, "categoryid = ?", String.valueOf(rowId))).get(0);
        category.setCategory_id(modelCategory.getCategory_id());
        category.setCategory_name(modelCategory.getCategory_name());
        category.setCreate_date(modelCategory.getCreate_date());
        category.setUpdate_date(modelCategory.getUpdate_date());
        category.setStatus(modelCategory.getStatus());
        category.save();
    }

    public void isCategoryExisting(ModelCategory modelCategory) {
        String id = modelCategory.getCategory_id();
        Log.i("ARGU", "CHECKER " + id);
        List<ModelCategory> categoryList = ModelCategory.find(ModelCategory.class, "categoryid = ?", id);
        int size = categoryList.size();

        if (size > 0) {
            boolean found = false;
            String date = "";
            for (int count = 0; count < size; count++) {
                if (categoryList.get(count).getCategory_id().equals(id)) {
                    date = categoryList.get(count).getUpdate_date();
                    found = true;
                }
            }

            if (found) {
                Log.i("ARGULOOP", "CATEGORY UPDATE");
                if (!date.equals(modelCategory.getUpdate_date()))
                    updateDataCategory(modelCategory);
//                    auditorsAdapter.notifyDataSetChanged();
            } else {
                Log.i("ARGULOOP", "SAVE");
                modelCategory.save();
                categoryList.add(modelCategory);
            }

        } else {
            Log.i("ARGU", "SAVE - SIZE 0");
            modelCategory.save();
            categoryList.add(modelCategory);
        }
    }

    //Product
    public void updateDataProduct(ModelProduct modelProduct) {
        String rowId = modelProduct.getProduct_id();
        Log.i("ARGU", "CHECKER " + rowId);
        ModelProduct category = (ModelProduct.find(ModelProduct.class, "productid = ?", String.valueOf(rowId))).get(0);
        category.setProduct_id(modelProduct.getProduct_id());
        category.setCompany_id(modelProduct.getCompany_id());
        category.setProduct_name(modelProduct.getProduct_name());
        category.setType(modelProduct.getType());
        category.setCreate_date(modelProduct.getCreate_date());
        category.setUpdate_date(modelProduct.getUpdate_date());
        category.setStatus(modelProduct.getStatus());
        category.save();
    }

    public void isProductExisting(ModelProduct modelProduct) {
        String id = modelProduct.getProduct_id();
        Log.i("ARGU", "CHECKER " + id);
        List<ModelProduct> productList = ModelProduct.find(ModelProduct.class, "productid = ?", id);
        int size = productList.size();

        if (size > 0) {
            boolean found = false;
            String date = "";
            for (int count = 0; count < size; count++) {
                if (productList.get(count).getProduct_id().equals(id)) {
                    date = productList.get(count).getUpdate_date();
                    found = true;
                }
            }

            if (found) {
                Log.i("ARGULOOP", "PRODUCT UPDATE");
                if (!date.equals(modelProduct.getUpdate_date()))
                    updateDataProduct(modelProduct);
//                    auditorsAdapter.notifyDataSetChanged();
            } else {
                Log.i("ARGULOOP", "SAVE");
                modelProduct.save();
                productList.add(modelProduct);
            }

        } else {
            Log.i("ARGU", "SAVE - SIZE 0");
            modelProduct.save();
            productList.add(modelProduct);
        }
    }

    //Type Audit
    public void isTypeAuditExisting(ModelTypeAudit modelTypeAudit) {
        String id = modelTypeAudit.getScope_id();
        Log.i("ARGU", "CHECKER " + id);
        List<ModelTypeAudit> typeAuditList = ModelTypeAudit.find(ModelTypeAudit.class, "scopeid = ?", id);
        int size = typeAuditList.size();

        if (size > 0) {
            boolean found = false;
            String date = "";
            for (int count = 0; count < size; count++) {
                if (typeAuditList.get(count).getScope_id().equals(id)) {
                    date = typeAuditList.get(count).getUpdate_date();
                    found = true;
                }
            }

            if (found) {
                Log.i("ARGULOOP", "TYPE AUDIT UPDATE");
                if (!date.equals(modelTypeAudit.getUpdate_date()))
                    updateTypeAudit(modelTypeAudit);
//                    auditorsAdapter.notifyDataSetChanged();
            } else {
                Log.i("ARGULOOP", "SAVE");
                modelTypeAudit.save();
                typeAuditList.add(modelTypeAudit);
            }

        } else {
            Log.i("isTypeAuditExistingARGU", "SAVE - SIZE 0");
            modelTypeAudit.save();
            typeAuditList.add(modelTypeAudit);
        }
    }

    public void updateTypeAudit(ModelTypeAudit modelTypeAudit) {
        String rowId = modelTypeAudit.getScope_id();
        Log.i("ARGU", "CHECKER " + rowId);
        ModelTypeAudit typeAudit = (ModelTypeAudit.find(ModelTypeAudit.class, "scopeid = ?", String.valueOf(rowId))).get(0);
        typeAudit.setScope_id(modelTypeAudit.getScope_id());
        typeAudit.setScope_name(modelTypeAudit.getScope_name());
        typeAudit.setCreate_date(modelTypeAudit.getCreate_date());
        typeAudit.setStatus(modelTypeAudit.getStatus());
        typeAudit.save();
    }

    // Audit Report
    public boolean checkAuditReport(String report_id, String version) {
        List<ModelAuditReports> report = ModelAuditReports.find(ModelAuditReports.class, "reportid = ?", report_id);
        int size = report.size();
        Log.i("APICalls: ", "SIZE - " + size + " Report id: " + report_id);
        Log.i("APICalls: ", " Audit report version - " + version);

        boolean different = false;

        if (size > 0) {
            Log.i("APICalls: ", " Audit report version - " + report.get(0).getVersion());
//            for (int count = 0; count < size; count++) {
            if (report.get(0).getVersion().equals(version)) {
                different = true;
            }
//            }
        }
        return different;
    }

    public void isAuditReportExisting(ModelAuditReports modelAuditReports, String modDate) {
        String id = modelAuditReports.getReport_id();
        Log.i("ARGU", "Audit Report id " + id);
        List<ModelAuditReports> report = ModelAuditReports.find(ModelAuditReports.class, "reportid = ?", id);
        // List<ModelAuditReports> report = ModelAuditReports.findWithQuery(ModelAuditReports.class, "SELECT * FROM MODEL_AUDIT_REPORTS WHERE reportid = '" + id + "'");
        int size = report.size();
        Log.i("R-E-P-O-R-T : ", "SIZE - " + size);
        Log.i("R-E-P-O-R-T : ", "SIZE - " + report.toString());

        if (size > 0) {
            boolean found = false;
            String date = "";
            String status = "";
            String RepId = "";
            for (int count = 0; count < size; count++) {
                if (report.get(count).getReport_id().equals(id)) {
                    date = report.get(count).getModified_date();
                    status = report.get(count).getStatus();
                    RepId = report.get(count).getReport_id();
                    found = true;
                }
            }

            if (found) {
                Log.i("ARGULOOP", "UPDATE : " + date);
                Log.i("ARGULOOP-DATE",
                        "DATE DB : " + date +
                                " DATE API: " + modelAuditReports.getModified_date() +
                                " REP ID: " + RepId);
                //if (!date.equals(modelAuditReports.getModified_date())) {
                if (!date.equals(modDate)) {
                    updateAuditReport(modelAuditReports);
                    Log.i("ARGULOOP", "UPDATED");
                }
//                    auditorsAdapter.notifyDataSetChanged();
            } else {
                Log.i("ARGULOOP", "SAVE");
                modelAuditReports.save();
                report.add(modelAuditReports);
            }

        } else {
            Log.i("ARGUNEW-NEW", "SAVE - SIZE 0 ID: " + id);
            //query ung existing na report scope (TemplateModelScopeAuditInterest)
            //update ung laman via modelAuditReports.getScope
            //check inner model para sa adapater within adapter
            //then save
            modelAuditReports.save();
            report.add(modelAuditReports);
        }
    }

    public void updateAuditReport(ModelAuditReports modelAuditReports) {
        String report_id = modelAuditReports.getReport_id();
        Log.i("ARGU", "CHECKER " + report_id);
        Log.i("ARGULOOP-DATE API", "CHECKER " + modelAuditReports.toString());

        ModelAuditReports auditReports = (ModelAuditReports.find(ModelAuditReports.class,
                "reportid = ?", String.valueOf(report_id))).get(0);
        Log.i("ARGULOOP-DATE DB", "CHECKER " + auditReports.toString());
//        auditReports.setReport_id(modelAuditReports.getReport_id());
//        auditReports.setReport_no(modelAuditReports.getReport_no());
//        auditReports.setCompany_id(modelAuditReports.getCompany_id());
//        auditReports.setOther_activities(modelAuditReports.getOther_activities());
//        auditReports.setAudit_date_1(modelAuditReports.getAudit_date_1());
//        auditReports.setAudit_date_2(modelAuditReports.getAudit_date_2());
//        auditReports.setP_inspection_date_1(modelAuditReports.getP_inspection_date_1());
//        auditReports.setP_inspection_date_2(modelAuditReports.getP_inspection_date_2());
//
        auditReports.setReport_id(report_id);
        auditReports.setReport_no(modelAuditReports.getReport_no());
        auditReports.setCompany_id(modelAuditReports.getCompany_id());
        auditReports.setOther_activities(modelAuditReports.getOther_activities());
//        auditReports.setP_inspection_date_1(modelAuditReports.getP_inspection_date_1());
//        auditReports.setP_inspection_date_2(modelAuditReports.getP_inspection_date_2());
        auditReports.setTemplate_id(modelAuditReports.getTemplate_id());
        auditReports.setAuditor_id(modelAuditReports.getAuditor_id());
        auditReports.setAudit_close_date(modelAuditReports.getAudit_close_date());
        auditReports.setOther_issues(modelAuditReports.getOther_issues());
        auditReports.setOther_issues_executive(modelAuditReports.getOther_issues_executive());
        auditReports.setAudited_areas(modelAuditReports.getAudited_areas());
        auditReports.setAreas_to_consider(modelAuditReports.getAreas_to_consider());
//        auditReports.setDate_of_wrap(modelAuditReports.getDate_of_wrap());
//        auditReports.setTranslator(modelAuditReports.getTranslator());
        auditReports.setReviewer_id(modelAuditReports.getReviewer_id());
        auditReports.setApprover_id(modelAuditReports.getApprover_id());
        auditReports.setStatus(modelAuditReports.getStatus());
        auditReports.setVersion(modelAuditReports.getVersion());
        auditReports.setHead_lead(modelAuditReports.getHead_lead());
        auditReports.save();

        Log.i("DISPO-SIZE", "NEW SIZE: " + modelAuditReports.getDisposition());
    }

    //Disposition
    public void isDispositionExisting(ModelDisposition modelDisposition) {
        String id = modelDisposition.getDisposition_id();
        Log.i("ARGU", "CHECKER " + id);
        List<ModelDisposition> dispositionList = ModelDisposition.find(ModelDisposition.class, "dispositionid = ?", id);
        int size = dispositionList.size();

        if (size > 0) {
            boolean found = false;
            String date = "";
            for (int count = 0; count < size; count++) {
                if (dispositionList.get(count).getDisposition_id().equals(id)) {
                    date = dispositionList.get(count).getUpdate_date();
                    found = true;
                }
            }
            if (found) {
                Log.i("ARGULOOP", "DISPOSITION UPDATE");
                if (!date.equals(modelDisposition.getUpdate_date()))
                    updateDisposition(modelDisposition);
//                    auditorsAdapter.notifyDataSetChanged();
            } else {
                Log.i("ARGULOOP", "SAVE");
                modelDisposition.save();
                dispositionList.add(modelDisposition);
            }

        } else {
            Log.i("ARGU", "SAVE - SIZE 0");
            modelDisposition.save();
            dispositionList.add(modelDisposition);
        }
    }

    public void updateDisposition(ModelDisposition modelDisposition) {
        String rowId = modelDisposition.getDisposition_id();
        Log.i("ARGU", "CHECKER " + rowId);
        ModelDisposition disposition = (ModelDisposition.find(ModelDisposition.class, "dispositionid = ?", String.valueOf(rowId))).get(0);
        disposition.setDisposition_id(modelDisposition.getDisposition_id());
        disposition.setDisposition_name(modelDisposition.getDisposition_name());
        disposition.setCreate_date(modelDisposition.getCreate_date());
        disposition.setUpdate_date(modelDisposition.getUpdate_date());
        disposition.setStatus(modelDisposition.getStatus());
        disposition.save();
    }

    //Distribution
    public void isDistributionExisting(ModelDistribution modelDistribution) {
        String id = modelDistribution.getDistribution_id();
        Log.i("ARGU", "CHECKER " + id);
        List<ModelDistribution> modelDistributions = ModelDistribution.find(ModelDistribution.class, "distributionid = ?", id);
        int size = modelDistributions.size();

        if (size > 0) {
            boolean found = false;
            String date = "";
            for (int count = 0; count < size; count++) {
                if (modelDistributions.get(count).getDistribution_id().equals(id)) {
                    date = modelDistributions.get(count).getUpdate_date();
                    found = true;
                }
            }
            if (found) {
                Log.i("ARGULOOP", "DISTRIBUTION UPDATE");
                if (!date.equals(modelDistribution.getUpdate_date()))
                    updateDistribution(modelDistribution);
//                    auditorsAdapter.notifyDataSetChanged();
            } else {
                Log.i("ARGULOOP", "SAVE");
                modelDistribution.save();
                modelDistributions.add(modelDistribution);
            }

        } else {
            Log.i("ARGU", "SAVE - SIZE 0");
            modelDistribution.save();
            modelDistributions.add(modelDistribution);
        }
    }

    public void updateDistribution(ModelDistribution modelDistribution) {
        String rowId = modelDistribution.getDistribution_id();
        Log.i("ARGU", "CHECKER " + rowId);
        ModelDistribution modelDistribution1 = (ModelDistribution.find(ModelDistribution.class, "distributionid = ?", String.valueOf(rowId))).get(0);
        modelDistribution1.setDistribution_id(modelDistribution.getDistribution_id());
        modelDistribution1.setDistribution_name(modelDistribution.getDistribution_name());
        modelDistribution1.setCreate_date(modelDistribution.getCreate_date());
        modelDistribution1.setUpdate_date(modelDistribution.getUpdate_date());
        modelDistribution1.setStatus(modelDistribution.getStatus());
        modelDistribution1.save();
    }

    //ModelDateOfAudit
    public void isDateOfAuditExisting(ModelDateOfAudit modelDateOfAudit) {
        String id = modelDateOfAudit.getReport_id();
        Log.i("ARGU", "CHECKER " + id);
        List<ModelDateOfAudit> modelDateOfAudits = ModelDateOfAudit.find(ModelDateOfAudit.class, "reportid = ?", id);
        int size = modelDateOfAudits.size();

        if (size > 0) {
            boolean found = false;
            String date = "";
            for (int count = 0; count < size; count++) {
                if (modelDateOfAudits.get(count).getReport_id().equals(id)) {
                    found = true;
                }
            }
            if (found) {
                modelDateOfAudit.save();
            } else {
                Log.i("ARGULOOP", "SAVE modelDateOfAudit");
                modelDateOfAudit.save();
                modelDateOfAudits.add(modelDateOfAudit);
            }

        } else {
            Log.i("ARGU", "SAVE - SIZE 0 modelDateOfAudit");
            modelDateOfAudit.save();
            modelDateOfAudits.add(modelDateOfAudit);
        }
    }

    //Template
    public void updateDataTemplate(ModelTemplates modelTemplates) {
        String rowId = modelTemplates.getTemplateID();
        Log.i("ARGU", "CHECKER " + rowId);
        ModelTemplates template = (ModelTemplates.find(ModelTemplates.class, "template_id = ?", String.valueOf(rowId))).get(0);
        template.setTemplateID(modelTemplates.getTemplateID());
        template.setProductType(modelTemplates.getProductType());
        template.setTemplateName(modelTemplates.getTemplateName());

        if (!template.getDateUpdated().equals(modelTemplates.getDateUpdated())) {
            if (modelTemplates.getStatus().equals("1")) {
                changes++;
                template.setStatus("1");
            } else {
                template.setStatus(modelTemplates.getStatus());
            }
        } else if (modelTemplates.getStatus().equals("1")) {
            template.setStatus("2");
        }

        template.setDateUpdated(modelTemplates.getDateUpdated());

        template.save();
    }

    public void isTemplateExisting(ModelTemplates modelTemplates) {
        String id = modelTemplates.getTemplateID();
        Log.i("ARGU", "CHECKER " + id);
        List<ModelTemplates> templateList = ModelTemplates.find(ModelTemplates.class, "template_id = ?", id);
        int size = templateList.size();

        if (size > 0) {
            boolean found = false;
            String date = "";
            for (int count = 0; count < size; count++) {
                if (templateList.get(count).getTemplateID().equals(id)) {
                    date = templateList.get(count).getDateUpdated();
                    found = true;
                }
            }

            if (found) {
                Log.i("ARGULOOP", "TEMPLATES UPDATE");
                if (!date.equals(modelTemplates.getDateUpdated()))
                    updateDataTemplate(modelTemplates);
            } else {
                Log.i("ARGULOOP", "SAVE");
                modelTemplates.save();
                templateList.add(modelTemplates);
                if (modelTemplates.getStatus().equals("1"))
                    changes++;
            }

        } else {
            Log.i("ARGU", "SAVE - SIZE 0");
            modelTemplates.save();
            templateList.add(modelTemplates);
            if (modelTemplates.getStatus().equals("1"))
                changes++;
        }
    }

    public boolean isTemplateIDExisting(ModelTemplates modelTemplates) {
        boolean exists = true;
        String id = modelTemplates.getTemplateID();
        Log.i("ARGU", "CHECKER " + id);
        List<ModelTemplates> templateList = ModelTemplates.listAll(ModelTemplates.class);
        int size = templateList.size();

        if (size > 0) {
            for (int count = 0; count < size; count++) {
                if (templateList.get(count).getTemplateID().equals(id)) {
                    exists = false;
                }
            }

        }
        return exists;
    }

    public boolean isElementIDExisting(ModelTemplateElements modelTemplateElements) {
        boolean exists = true;
        String id = modelTemplateElements.getElement_id();
        Log.i("ARGU", "CHECKER " + id);
        List<ModelTemplateElements> templateList = ModelTemplateElements.find(
                ModelTemplateElements.class, "elementid = ? AND templateid = ?", id,
                modelTemplateElements.getTemplate_id());
        int size = templateList.size();

        if (size > 0) {
            for (int count = 0; count < size; count++) {
                if (templateList.get(count).getElement_id().equals(id)) {
                    exists = false;
                }
            }

        }
        return exists;
    }

    public boolean isQuestionIDExisting(ModelTemplateQuestionDetails modelTemplateQuestionDetails) {
        boolean exists = true;
        String id = modelTemplateQuestionDetails.getElement_id();
        Log.i("ARGU-TEMPLATE", "CHECKER E " + id);
        Log.i("ARGU-TEMPLATE", "CHECKER T " + modelTemplateQuestionDetails.getTemplate_id());
        Log.i("ARGU-TEMPLATE", "CHECKER Q " + modelTemplateQuestionDetails.getQuestion_id());

        List<ModelTemplateQuestionDetails> templateList = ModelTemplateQuestionDetails.find
                (ModelTemplateQuestionDetails.class, "elementid = ? AND templateid = ? AND questionid = ?",
                        id, modelTemplateQuestionDetails.getTemplate_id(), modelTemplateQuestionDetails.getQuestion_id());
        int size = templateList.size();
        Log.i("ARGU-TEMPLATE-SIZE", "SIZE: " + size + " ID: " + modelTemplateQuestionDetails.getTemplate_id());

        if (size > 0) {
            for (int count = 0; count < size; count++) {
                Log.i("ARGU-TEMPLATE-SIZE", "DB: " + templateList.get(count).getElement_id() +
                        " API: " + id);
                if (templateList.get(count).getElement_id().equals(id)) {
                    exists = false;
                }
            }

        }
        return exists;
    }

    public boolean isActivityIDExisting(ModelTemplateActivities modelTemplateActivities) {
        boolean exists = true;
        String id = modelTemplateActivities.getActivityID();
        Log.i("ARGU", "CHECKER " + id);
        List<ModelTemplateActivities> templateList = ModelTemplateActivities.find(ModelTemplateActivities.class, "activity_id = ? AND templateid = ?", id, modelTemplateActivities.getTemplate_id());
        int size = templateList.size();

        if (size > 0) {
            for (int count = 0; count < size; count++) {
                if (templateList.get(count).getActivityID().equals(id)) {
                    exists = false;
                }
            }

        }
        return exists;
    }

    public boolean isSubActivityIDExisting(ModelTemplateSubActivities modelTemplateSubActivities) {
        boolean exists = true;
        String id = modelTemplateSubActivities.getActivity_id();
        Log.i("ARGU", "CHECKER " + id);
        List<ModelTemplateSubActivities> templateList = ModelTemplateSubActivities.find(ModelTemplateSubActivities.class, "activityid = ? AND templateid = ? AND sub_item_id = ?", id, modelTemplateSubActivities.getTemplate_id(), modelTemplateSubActivities.getSubItemID());
        int size = templateList.size();

        if (size > 0) {
            for (int count = 0; count < size; count++) {
                if (templateList.get(count).getActivity_id().equals(id)) {
                    exists = false;
                }
            }

        }
        return exists;
    }

    public String getDate() {
        String dateStr = "";

        DateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");
        Date date = new Date();
        dateStr = dateFormat.format(date);

        return dateStr;
    }

    public void dialogSyncSuccess(String mess) {
        dialogSyncSuccess = new Dialog(context);
        dialogSyncSuccess.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialogSyncSuccess.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogSyncSuccess.setCancelable(false);
        dialogSyncSuccess.setContentView(R.layout.dialog_error_login);
        dialogSyncSuccess.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        TextView msg = (TextView) dialogSyncSuccess.findViewById(R.id.tv_message);
        Button ok = (Button) dialogSyncSuccess.findViewById(R.id.btn_ok);

        msg.setText(mess);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("NEWTEMPLATE-COUNT", changes + "");
                if (changes > 0) {
                    HomeActivity.tvSyncNotifCount.setText(String.valueOf(changes));
                }
                setFragment();
                dialogSyncSuccess.dismiss();
            }
        });

        dialogSyncSuccess.show();
    }
}
