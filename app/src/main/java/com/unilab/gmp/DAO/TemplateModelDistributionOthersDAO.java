package com.unilab.gmp.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.unilab.gmp.model.TemplateModelDistributionOthers;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

/**
 * Created by c_rcmiguel on 9/19/2017.
 */


@Dao
public interface TemplateModelDistributionOthersDAO {
    @Query("select * from TemplateModelDistributionOthers")
    Flowable<List<TemplateModelDistributionOthers>> getItemList();

    @Insert
    Completable insert(final TemplateModelDistributionOthers templateModelDistributionOthers);

    @Query("DELETE FROM TemplateModelDistributionOthers")
    void delete();

    @Query("DELETE FROM TemplateModelDistributionOthers WHERE report_id = :reportId")
    void deleteId(String reportId);

    @Query("select * from TemplateModelDistributionOthers WHERE report_id = :reportId")
    List<TemplateModelDistributionOthers> getByReportId(String reportId);

    @Query("UPDATE TemplateModelDistributionOthers SET report_id = :reportId WHERE report_id = :reportId")
    void updateReportId(String reportId);

}
