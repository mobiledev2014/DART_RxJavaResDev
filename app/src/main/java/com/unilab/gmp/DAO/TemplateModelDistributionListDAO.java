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
public interface TemplateModelDistributionListDAO {
    @Query("select * from TemplateModelDistributionList")
    Flowable<List<TemplateModelDistributionListDAO>> getItemList();

    @Insert
    Completable insert(final TemplateModelDistributionListDAO templateModelDistributionList);

    @Query("DELETE FROM TemplateModelDistributionList")
    void delete();
}
