package com.unilab.gmp.DAO;

import androidx.room.Dao;

import androidx.room.Insert;
import androidx.room.Query;

import com.unilab.gmp.model.TemplateModelSummaryRecommendation;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

/**
 * Created by c_jhcanuto on 8/24/2017.
 */

@Dao
public interface TemplateModelSummaryRecommendationDAO {
    @Query("select * from TemplateModelSummaryRecommendation")
    Flowable<List<TemplateModelSummaryRecommendation>> getItemList();

    @Insert
    Completable insert(final TemplateModelSummaryRecommendation templateModelSummaryRecommendation);

    @Query("DELETE FROM TemplateModelSummaryRecommendation")
    void delete();

    @Query("DELETE FROM TemplateModelSummaryRecommendation WHERE report_id = :reportId")
    void deleteId(String reportId);
}
