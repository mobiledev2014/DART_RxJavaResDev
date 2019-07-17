package com.unilab.gmp.DAO;

import androidx.room.Dao;

import androidx.room.Insert;
import androidx.room.Query;

import com.unilab.gmp.model.TemplateModelScopeAuditInterest;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

/**
 * Created by c_jhcanuto on 8/24/2017.
 */

@Dao
public interface TemplateModelScopeAuditInterestDAO {
    @Query("select * from TemplateModelScopeAuditInterest")
    Flowable<List<TemplateModelScopeAuditInterest>> getItemList();

    @Insert
    Completable insert(final TemplateModelScopeAuditInterest templateModelScopeAuditInterest);

    @Query("DELETE FROM TemplateModelScopeAuditInterest")
    void delete();

    @Query("DELETE FROM TemplateModelScopeAuditInterest WHERE report_id = :reportId")
    void deleteId(String reportId);

    @Query("select * from TemplateModelScopeAuditInterest WHERE report_id = :reportId")
    List<TemplateModelScopeAuditInterest> getByReportId(String reportId);

    @Query("select * from TemplateModelScopeAuditInterest WHERE report_id = :reportId AND audit_id = :auditId")
    List<TemplateModelScopeAuditInterest> getByReportAndAuditId(String reportId, String auditId);

    @Query("select * from TemplateModelScopeAuditInterest WHERE report_id = :reportId AND id = :id")
    List<TemplateModelScopeAuditInterest> getByReportAndPrimaryId(String reportId, String id);


    @Query("UPDATE TemplateModelScopeAuditInterest SET report_id = :reportId WHERE report_id = :reportId AND id = :id")
    void updateReportId(String reportId, String id);

}
