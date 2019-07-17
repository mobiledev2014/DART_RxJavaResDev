package com.unilab.gmp.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.unilab.gmp.model.TemplateModelPreAuditDoc;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

/**
 * Created by c_jhcanuto on 8/24/2017.
 */

@Dao
public interface TemplateModelPreAuditDocDAO {
    @Query("select * from TemplateModelPreAuditDoc")
    Flowable<List<TemplateModelPreAuditDoc>> getItemList();

    @Insert
    Completable insert(final TemplateModelPreAuditDoc templateModelPreAuditDoc);

    @Query("DELETE FROM TemplateModelPreAuditDoc")
    void delete();

    @Query("DELETE FROM TemplateModelPreAuditDoc WHERE report_id = :reportId")
    void deleteId(String reportId);

    @Query("SELECT * FROM TemplateModelPreAuditDoc WHERE report_id = :reportId")
    List<TemplateModelPreAuditDoc> getReportById(String reportId);

    @Query("UPDATE TemplateModelPreAuditDoc SET report_id = :reportId WHERE report_id = :reportId")
    void updateReportId(String reportId);

}
