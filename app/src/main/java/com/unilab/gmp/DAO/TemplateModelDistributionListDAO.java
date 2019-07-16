package com.unilab.gmp.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.unilab.gmp.model.TemplateModelDistributionList;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

/**
 * Created by c_jhcanuto on 8/24/2017.
 */
@Dao
public interface TemplateModelDistributionListDAO {
    @Query("select * from TemplateModelDistributionList")
    Flowable<List<TemplateModelDistributionList>> getItemList();

    @Insert
    Completable insert(final TemplateModelDistributionList templateModelDistributionList);

    @Query("DELETE FROM TemplateModelDistributionList")
    void delete();

    @Query("DELETE FROM TemplateModelDistributionList WHERE report_id = :reportId")
    void deleteId(String reportId);
}
