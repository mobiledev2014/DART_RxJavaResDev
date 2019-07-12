package com.unilab.gmp.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

/**
 * Created by c_rcmiguel on 9/19/2017.
 */


@Dao
public interface TemplateModelDistributionOthersDAO {
    @Query("select * from TemplateModelDistributionOthers")
    Flowable<List<TemplateModelDistributionOthersDAO>> getItemList();

    @Insert
    Completable insert(final TemplateModelDistributionOthersDAO templateModelDistributionOthers);

    @Query("DELETE FROM TemplateModelDistributionOthers")
    void delete();
}
