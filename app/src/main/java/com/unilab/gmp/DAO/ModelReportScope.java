package com.unilab.gmp.DAO;

import com.orm.SugarRecord;
import com.unilab.gmp.model.ModelReportScopeProduct;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Entity;
import androidx.room.Insert;
import androidx.room.Query;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by c_jhcanuto on 8/23/2017.
 */
@Dao
public interface ModelReportScope  {
    @Query("select * from ModelReportScope")
    Flowable<List<ModelReportScope>> getItemList();

    @Insert
    Completable insert(final ModelReportScope modelReportReviewer);

    @Query("DELETE FROM ModelReportScope")
    void delete();
}
