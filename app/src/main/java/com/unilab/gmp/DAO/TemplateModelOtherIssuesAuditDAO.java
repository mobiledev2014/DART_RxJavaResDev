package com.unilab.gmp.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.unilab.gmp.model.TemplateModelOtherIssuesAudit;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

/**
 * Created by c_jsbustamante on 08/11/2017.
 */

@Dao
public interface TemplateModelOtherIssuesAuditDAO {
    @Query("select * from TemplateModelOtherIssuesAudit")
    Flowable<List<TemplateModelOtherIssuesAudit>> getItemList();

    @Insert
    Completable insert(final TemplateModelOtherIssuesAudit templateModelOtherIssuesAudit);

    @Query("DELETE FROM TemplateModelOtherIssuesAudit")
    void delete();

    @Query("DELETE FROM TemplateModelOtherIssuesAudit WHERE report_id = :reportId")
    void deleteId(String reportId);
}
