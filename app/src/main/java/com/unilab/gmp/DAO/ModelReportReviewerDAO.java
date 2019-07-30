package com.unilab.gmp.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.unilab.gmp.model.ModelReportReviewer;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

/**
 * Created by c_jhcanuto on 8/23/2017.
 */

@Dao
public interface ModelReportReviewerDAO {
    @Query("select * from ModelReportReviewer")
    Flowable<List<ModelReportReviewer>> getItemList();

    @Insert
    Completable insert(final ModelReportReviewer modelReportReviewer);

    @Query("DELETE FROM ModelReportReviewer")
    void delete();

    @Query("DELETE FROM ModelReportReviewer WHERE report_id = :reportId")
    void deleteById(String reportId);
}
