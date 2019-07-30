package com.unilab.gmp.DAO;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.unilab.gmp.model.TemplateModelScopeAudit;

import io.reactivex.Completable;
import io.reactivex.Flowable;

/**
 * Created by c_jhcanuto on 8/23/2017.
 */

@Dao
public interface TemplateModelScopeAuditDAO {
    @Query("select * from TemplateModelScopeAudit")
    Flowable<List<TemplateModelScopeAudit>> getItemList();

    @Insert
    Completable insert(final TemplateModelScopeAudit templateModelScopeAudit);

    @Query("DELETE FROM TemplateModelScopeAudit")
    void delete();

    @Query("DELETE FROM TemplateModelScopeAudit WHERE report_id = :reportId")
    void deleteId(String reportId);

    @Query("select * from TemplateModelScopeAudit WHERE report_id = :reportId")
    List<TemplateModelScopeAudit> getByReportId(String reportId);

    @Query("UPDATE TemplateModelScopeAudit SET report_id = :reportId WHERE report_id = :reportId")
    void updateReportId(String reportId);

}
