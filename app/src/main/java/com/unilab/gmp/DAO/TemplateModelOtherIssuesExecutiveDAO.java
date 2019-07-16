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
    @Query("select * from TemplateModelOtherIssuesExecutive")
    Flowable<List<TemplateModelOtherIssuesExecutive>> getItemList();

    @Insert
    Completable insert(final TemplateModelOtherIssuesExecutive templateModelOtherIssuesExecutive);

    @Query("DELETE FROM TemplateModelOtherIssuesExecutive")
    void delete();

    @Query("DELETE FROM TemplateModelOtherIssuesExecutive WHERE report_id = :reportId")
    void deleteId(String reportId);
}
