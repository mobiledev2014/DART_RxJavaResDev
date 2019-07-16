package com.unilab.gmp.DAO;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.unilab.gmp.model.ModelReportScope;

import io.reactivex.Completable;
import io.reactivex.Flowable;

/**
 * Created by c_jhcanuto on 8/23/2017.
 */
@Dao
public interface ModelReportScopeDAO {
    @Query("select * from ModelReportScope")
    Flowable<List<ModelReportScope>> getItemList();

    @Insert
    Completable insert(final ModelReportScope modelReportReviewer);

    @Query("DELETE FROM ModelReportScope")
    void delete();
}
