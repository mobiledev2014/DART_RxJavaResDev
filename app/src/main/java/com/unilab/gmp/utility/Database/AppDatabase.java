package com.unilab.gmp.utility.Database;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.unilab.gmp.DAO.ApproverModelDAO;
import com.unilab.gmp.DAO.AuditorsModelDAO;
import com.unilab.gmp.DAO.ConfigModelDAO;
import com.unilab.gmp.DAO.ModelApproverInfoDAO;
import com.unilab.gmp.DAO.ModelAuditReportDetailsDAO;
import com.unilab.gmp.DAO.ModelAuditReportReplyDAO;
import com.unilab.gmp.DAO.ModelAuditReportWrapUpDateDAO;
import com.unilab.gmp.DAO.ModelAuditReportsDAO;
import com.unilab.gmp.DAO.ModelAuditReportsListDAO;
import com.unilab.gmp.DAO.ModelAuditorInfoDAO;
import com.unilab.gmp.DAO.ModelAuditorsDAO;
import com.unilab.gmp.DAO.ModelCategoryDAO;
import com.unilab.gmp.DAO.ModelCategoryInfoDAO;
import com.unilab.gmp.DAO.ModelClassificationCategoryDAO;
import com.unilab.gmp.DAO.ModelClassificationDAO;
import com.unilab.gmp.DAO.ModelClassificationInfoDAO;
import com.unilab.gmp.DAO.ModelCompanyDAO;
import com.unilab.gmp.DAO.ModelCompanyInfoDAO;
import com.unilab.gmp.DAO.ModelDateOfAuditDAO;
import com.unilab.gmp.DAO.ModelDispositionDAO;
import com.unilab.gmp.DAO.ModelDispositionInfoDAO;
import com.unilab.gmp.DAO.ModelDistributionDAO;
import com.unilab.gmp.DAO.ModelDistributionInfoDAO;
import com.unilab.gmp.DAO.ModelProductDAO;
import com.unilab.gmp.DAO.ModelProductInfoDAO;
import com.unilab.gmp.DAO.ModelReferenceDAO;
import com.unilab.gmp.DAO.ModelReferenceInfoDAO;
import com.unilab.gmp.DAO.ModelReportActivitiesDAO;
import com.unilab.gmp.DAO.ModelReportApproverDAO;
import com.unilab.gmp.DAO.ModelReportCoAuditorIDDAO;
import com.unilab.gmp.DAO.ModelReportDAO;
import com.unilab.gmp.DAO.ModelReportDispositionDAO;
import com.unilab.gmp.DAO.ModelReportDispositionScopeProductDAO;
import com.unilab.gmp.DAO.ModelReportElementsRequiringRecommendationDAO;
import com.unilab.gmp.DAO.ModelReportInspectionDAO;
import com.unilab.gmp.DAO.ModelReportInspectorDAO;
import com.unilab.gmp.DAO.ModelReportPersonnelDAO;
import com.unilab.gmp.DAO.ModelReportPreAuditDocsDAO;
import com.unilab.gmp.DAO.ModelReportQuestionDAO;
import com.unilab.gmp.DAO.ModelReportReferencesDAO;
import com.unilab.gmp.DAO.ModelReportReviewerDAO;
import com.unilab.gmp.DAO.ModelReportScopeDAO;
import com.unilab.gmp.DAO.ModelReportScopeProductDAO;
import com.unilab.gmp.DAO.ModelReportSubActivitiesDAO;
import com.unilab.gmp.DAO.ModelReviewerInfoDAO;
import com.unilab.gmp.DAO.ModelSiteAuditHistoryDAO;
import com.unilab.gmp.DAO.ModelSiteDateDAO;
import com.unilab.gmp.DAO.ModelTemplateActivitiesDAO;
import com.unilab.gmp.DAO.ModelTemplateElementsDAO;
import com.unilab.gmp.DAO.ModelTemplateQuestionDetailsDAO;
import com.unilab.gmp.DAO.ModelTemplateSubActivitiesDAO;
import com.unilab.gmp.DAO.ModelTemplatesDAO;
import com.unilab.gmp.DAO.ModelTypeAuditDAO;
import com.unilab.gmp.DAO.ModelTypeAuditInfoDAO;
import com.unilab.gmp.DAO.ModelUserDAO;
import com.unilab.gmp.DAO.QuestionModelDAO;
import com.unilab.gmp.DAO.ReviewerModelDAO;
import com.unilab.gmp.DAO.SupplierAndCompanyInformationModelDAO;
import com.unilab.gmp.DAO.TemplateDetailsModelDAO;
import com.unilab.gmp.DAO.TemplateListModelDAO;
import com.unilab.gmp.DAO.TemplateModelAuditorsDAO;
import com.unilab.gmp.DAO.TemplateModelCompanyBackgroundMajorChangesDAO;
import com.unilab.gmp.DAO.TemplateModelCompanyBackgroundNameDAO;
import com.unilab.gmp.DAO.TemplateModelDispositionDAO;
import com.unilab.gmp.DAO.TemplateModelDistributionListDAO;
import com.unilab.gmp.DAO.TemplateModelDistributionOthersDAO;
import com.unilab.gmp.DAO.TemplateModelOtherIssuesAuditDAO;
import com.unilab.gmp.DAO.TemplateModelOtherIssuesExecutiveDAO;
import com.unilab.gmp.DAO.TemplateModelPersonelMetDuringDAO;
import com.unilab.gmp.DAO.TemplateModelPreAuditDocDAO;
import com.unilab.gmp.DAO.TemplateModelPresentDuringMeetingDAO;
import com.unilab.gmp.DAO.TemplateModelReferenceDAO;
import com.unilab.gmp.DAO.TemplateModelScopeAuditCopyDAO;
import com.unilab.gmp.DAO.TemplateModelScopeAuditDAO;
import com.unilab.gmp.DAO.TemplateModelScopeAuditInterestDAO;
import com.unilab.gmp.DAO.TemplateModelSummaryRecommendationDAO;
import com.unilab.gmp.DAO.TemplateModelTranslatorDAO;
import com.unilab.gmp.model.*;

import io.reactivex.annotations.NonNull;

@Database(entities = {
        ApproverModel.class, AuditorsModel.class, ConfigModel.class,
        ModelApproverInfo.class, ModelAuditorInfo.class, ModelAuditors.class,
        ModelAuditReportDetails.class, ModelAuditReportReply.class, ModelAuditReportsList.class,
        ModelAuditReports.class, ModelAuditReportWrapUpDate.class, ModelCategory.class,
        ModelCategoryInfo.class, ModelClassification.class, ModelClassificationCategory.class,
        ModelClassificationInfo.class, ModelCompany.class, ModelCompanyInfo.class,
        ModelDateOfAudit.class, ModelDisposition.class, ModelDispositionInfo.class,
        ModelDistribution.class, ModelDistributionInfo.class, ModelProduct.class,
        ModelProductInfo.class, ModelReference.class, ModelReferenceInfo.class,
        ModelReport.class, ModelReportActivities.class, ModelReportApprover.class,
        ModelReportCoAuditorID.class, ModelReportDisposition.class, ModelReportDispositionScopeProduct.class,
        ModelReportElementsRequiringRecommendation.class, ModelReportInspection.class, ModelReportInspector.class,
        ModelReportPersonnel.class, ModelReportPreAuditDocs.class, ModelReportQuestion.class,
        ModelReportReferences.class, ModelReportReviewer.class, ModelReportScope.class,
        ModelReviewerInfo.class, ModelSiteAuditHistory.class, ModelSiteDate.class,
        ModelTemplateActivities.class, ModelTemplateElements.class, ModelTemplateQuestionDetails.class,
        ModelTemplates.class, ModelTemplateSubActivities.class, ModelTypeAudit.class,
        ModelTypeAuditInfo.class, ModelUser.class, QuestionModel.class,
        ReviewerModel.class, SupplierAndCompanyInformationModel.class, TemplateDetailsModel.class,
        TemplateListModel.class, TemplateModelAuditors.class, TemplateModelCompanyBackgroundMajorChanges.class,
        TemplateModelCompanyBackgroundName.class, TemplateModelDisposition.class, TemplateModelDistributionList.class,
        TemplateModelDistributionOthers.class, TemplateModelOtherIssuesAudit.class, TemplateModelOtherIssuesExecutive.class,
        TemplateModelPersonelMetDuring.class, TemplateModelPreAuditDoc.class, TemplateModelPresentDuringMeeting.class,
        TemplateModelReference.class, TemplateModelScopeAudit.class, TemplateModelScopeAuditCopy.class,
        TemplateModelScopeAuditCopy.class, TemplateModelScopeAuditInterest.class, TemplateModelSummaryRecommendation.class,
        TemplateModelTranslator.class

//        Kiosk.class
}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public static AppDatabase appDatabase;
    public static AppDatabase getInstance(final Context context)
    {
        if(appDatabase == null){

            synchronized (AppDatabase.class){
                if (appDatabase == null) {
                    appDatabase = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "dart_db")
                            .addCallback(new Callback() {
                                @Override
                                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                    super.onCreate(db);
                                    // On Database Create
                                    //TODO: create async task database population
                                    //new PopulateDatabaseAsync(appDatabase, context).execute();
                                }
                            })
                            .addCallback(new Callback() {
                                @Override
                                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                                    super.onOpen(db);
                                    // On Database open
                                    // TODO: checker if data is not tampered
                                }
                            })
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }

        return appDatabase;
    }

    public abstract ApproverModelDAO approverModelDAO();
    public abstract AuditorsModelDAO auditorsModelDAO();
    public abstract ConfigModelDAO configModelDAO();
    public abstract ModelApproverInfoDAO modelApproverInfoDAO();
    public abstract ModelAuditorInfoDAO modelAuditorInfoDAO();
    public abstract ModelAuditorsDAO modelAuditorsDAO();
    public abstract ModelAuditReportDetailsDAO modelAuditReportDetailsDAO();
    public abstract ModelAuditReportReplyDAO modelAuditReportReplyDAO();
    public abstract ModelAuditReportsListDAO modelAuditReportsListDAO();
    public abstract ModelAuditReportsDAO modelAuditReportsDAO();
    public abstract ModelCategoryDAO modelCategoryDAO();
    public abstract ModelCategoryInfoDAO modelCategoryInfoDAO();
    public abstract ModelClassificationDAO modelClassificationDAO();
    public abstract ModelClassificationCategoryDAO modelClassificationCategoryDAO();
    public abstract ModelClassificationInfoDAO modelClassificationInfoDAO();
    public abstract ModelCompanyDAO modelCompanyDAO();
    public abstract ModelCompanyInfoDAO modelCompanyInfoDAO();
    public abstract ModelDateOfAuditDAO modelDateOfAuditDAO();
    public abstract ModelDispositionDAO modelDispositionDAO();
    public abstract ModelDispositionInfoDAO modelDispositionInfoDAO();
    public abstract ModelDistributionDAO modelDistributionDAO();
    public abstract ModelDistributionInfoDAO modelDistributionInfoDAO();
    public abstract ModelProductDAO modelProductDAO();
    public abstract ModelProductInfoDAO modelProductInfoDAO();
    public abstract ModelReferenceDAO modelReferenceDAO();
    public abstract ModelReferenceInfoDAO modelReferenceInfoDAO();
    public abstract ModelReportActivitiesDAO modelReportActivitiesDAO();
    public abstract ModelReportApproverDAO modelReportApproverDAO();
    public abstract ModelReportCoAuditorIDDAO modelReportCoAuditorIDDAO();
    public abstract ModelReportDAO modelReportDAO();
    public abstract ModelReportDispositionDAO modelReportDispositionDAO();
    public abstract ModelReportDispositionScopeProductDAO modelReportDispositionScopeProductDAO();
    public abstract ModelReportElementsRequiringRecommendationDAO modelReportElementsRequiringRecommendationDAO();
    public abstract ModelReportInspectionDAO modelReportInspectionDAO();
    public abstract ModelReportInspectorDAO modelReportInspectorDAO();
    public abstract ModelReportPersonnelDAO modelReportPersonnelDAO();
    public abstract ModelReportPreAuditDocsDAO modelReportPreAuditDocsDAO();

    public abstract ModelReportQuestionDAO modelReportQuestionDAO();
    public abstract ModelReportReferencesDAO modelReportReferencesDAO();
    public abstract ModelReportReviewerDAO modelReportReviewerDAO();
    public abstract ModelReportScopeDAO modelReportScopeDAO();
    public abstract ModelReportScopeProductDAO modelReportScopeProductDAO();
    public abstract ModelReportSubActivitiesDAO modelReportSubActivitiesDAO();
    public abstract ModelReviewerInfoDAO modelReviewerInfoDAO();
    public abstract ModelSiteAuditHistoryDAO modelSiteAuditHistoryDAO();
    public abstract ModelSiteDateDAO modelSiteDateDAO();
    public abstract ModelTemplateActivitiesDAO modelTemplateActivitiesDAO();
    public abstract ModelTemplateElementsDAO modelTemplateElementsDAO();
    public abstract ModelTemplateQuestionDetailsDAO modelTemplateQuestionDetailsDAO();
    public abstract ModelTemplatesDAO modelTemplatesDAO();
    public abstract ModelTemplateSubActivitiesDAO modelTemplateSubActivitiesDAO();
    public abstract ModelTypeAuditDAO modelTypeAuditDAO();
    public abstract ModelUserDAO modelUserDAO();
    public abstract QuestionModelDAO questionModelDAO();
    public abstract ReviewerModelDAO reviewerModelDAO();
    public abstract SupplierAndCompanyInformationModelDAO supplierAndCompanyInformationModelDAO();
    public abstract TemplateDetailsModelDAO templateDetailsModelDAO();
    public abstract TemplateListModelDAO templateListModelDAO();
    public abstract TemplateModelAuditorsDAO templateModelAuditorsDAO();
    public abstract TemplateModelCompanyBackgroundMajorChangesDAO templateModelCompanyBackgroundMajorChangesDAO();
    public abstract TemplateModelCompanyBackgroundNameDAO templateModelCompanyBackgroundNameDAO();
    public abstract TemplateModelDispositionDAO templateModelDispositionDAO();
    public abstract TemplateModelDistributionListDAO templateModelDistributionListDAO();
    public abstract TemplateModelDistributionOthersDAO templateModelDistributionOthersDAO();
    public abstract TemplateModelOtherIssuesAuditDAO templateModelOtherIssuesAuditDAO();
    public abstract TemplateModelOtherIssuesExecutiveDAO templateModelOtherIssuesExecutiveDAO();
    public abstract TemplateModelPersonelMetDuringDAO templateModelPersonelMetDuringDAO();
    public abstract TemplateModelPreAuditDocDAO templateModelPreAuditDocDAO();
    public abstract TemplateModelPresentDuringMeetingDAO templateModelPresentDuringMeetingDAO();
    public abstract TemplateModelReferenceDAO templateModelReferenceDAO();
    public abstract TemplateModelScopeAuditCopyDAO templateModelScopeAuditCopyDAO();
    public abstract TemplateModelScopeAuditDAO templateModelScopeAuditDAO();
    public abstract TemplateModelScopeAuditInterestDAO templateModelScopeAuditInterestDAO();
    public abstract TemplateModelSummaryRecommendationDAO templateModelSummaryRecommendationDAO();
    public abstract TemplateModelTranslatorDAO templateModelTranslatorDAO();

}
