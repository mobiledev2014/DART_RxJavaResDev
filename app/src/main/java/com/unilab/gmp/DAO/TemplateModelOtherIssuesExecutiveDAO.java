package com.unilab.gmp.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.unilab.gmp.model.TemplateModelOtherIssuesExecutive;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

/**
 * Created by c_jsbustamante on 08/11/2017.
 */

@Dao
public interface TemplateModelOtherIssuesExecutiveDAO {
    @Query("SELECT * from TemplateModelOtherIssuesExecutive")
    Flowable<List<TemplateModelOtherIssuesExecutive>> getItemList();

    @Insert
    Completable insert(final TemplateModelOtherIssuesExecutive templateModelOtherIssuesExecutive);

    @Query("DELETE FROM TemplateModelOtherIssuesExecutive")
    void delete();

    @Query("DELETE FROM TemplateModelOtherIssuesExecutive WHERE report_id = :reportId")
    void deleteId(String reportId);

    @Query("SELECT * FROM TemplateModelOtherIssuesExecutive WHERE report_id = :reportId")
    List<TemplateModelOtherIssuesExecutive> getByReportId(String reportId);

    @Query("UPDATE TemplateModelOtherIssuesExecutive SET report_id = :reportId WHERE report_id = :reportId")
    void updateReportId(String reportId);



}
