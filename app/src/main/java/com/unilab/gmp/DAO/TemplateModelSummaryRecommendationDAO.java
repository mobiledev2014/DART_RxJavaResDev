package com.unilab.gmp.DAO;

import androidx.room.Dao;

import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

/**
 * Created by c_jhcanuto on 8/24/2017.
 */

@Dao
public interface TemplateModelSummaryRecommendationDAO {
    @Query("select * from TemplateModelSummaryRecommendation")
    Flowable<List<TemplateModelSummaryRecommendationDAO>> getItemList();

    @Insert
    Completable insert(final TemplateModelSummaryRecommendationDAO templateModelSummaryRecommendation);

    @Query("DELETE FROM TemplateModelSummaryRecommendation")
    void delete();
}
