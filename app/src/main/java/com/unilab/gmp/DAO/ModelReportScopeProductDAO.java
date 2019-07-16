package com.unilab.gmp.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.unilab.gmp.model.ModelReportScopeProduct;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

/**
 * Created by c_jhcanuto on 8/23/2017.
 */

@Dao
public interface ModelReportScopeProductDAO {
    @Query("select * from ModelReportScopeProduct")
    Flowable<List<ModelReportScopeProduct>> getItemList();

    @Insert
    Completable insert(final ModelReportScopeProduct modelReportScopeProduct);

    @Query("DELETE FROM ModelReportScopeProduct")
    void delete();
}
