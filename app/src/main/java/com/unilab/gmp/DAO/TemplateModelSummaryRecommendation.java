package com.unilab.gmp.DAO;

import androidx.room.Dao;
import androidx.room.Entity;

import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

import androidx.room.Entity;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by c_jhcanuto on 8/24/2017.
 */

@Dao
public interface TemplateModelSummaryRecommendation  {
    @Query("select * from TemplateModelSummaryRecommendation")
    Flowable<List<TemplateModelSummaryRecommendation>> getItemList();

    @Insert
    Completable insert(final TemplateModelSummaryRecommendation templateModelSummaryRecommendation);

    @Query("DELETE FROM TemplateModelSummaryRecommendation")
    void delete();
}
