package com.unilab.gmp.utility.Database;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

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

//    public abstract KioskDAO kioskDAO();
}
