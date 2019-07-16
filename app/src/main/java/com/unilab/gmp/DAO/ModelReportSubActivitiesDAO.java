package com.unilab.gmp.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.unilab.gmp.model.ModelReportSubActivities;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

/**
 * Created by c_jhcanuto on 8/23/2017.
 */

@Dao
public interface ModelReportSubActivitiesDAO {
    @Query("select * from ModelReportSubActivities")
    Flowable<List<ModelReportSubActivities>> getItemList();

    @Insert
    Completable insert(final ModelReportSubActivities modelReportSubActivities);

    @Query("DELETE FROM ModelReportSubActivities")
    void delete();

    @Query("DELETE FROM ModelReportSubActivities WHERE report_id = :id")
    void deleteId(String id);

}
