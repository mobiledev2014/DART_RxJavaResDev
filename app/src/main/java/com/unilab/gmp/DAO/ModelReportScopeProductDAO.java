package com.unilab.gmp.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

/**
 * Created by c_jhcanuto on 8/23/2017.
 */

@Dao
public interface ModelReportScopeProductDAO {
    @Query("select * from ModelReportScopeProduct")
    Flowable<List<ModelReportScopeProductDAO>> getItemList();

    @Insert
    Completable insert(final ModelReportScopeProductDAO modelReportScopeProduct);

    @Query("DELETE FROM ModelReportScopeProduct")
    void delete();
}
