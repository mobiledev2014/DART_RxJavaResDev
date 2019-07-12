package com.unilab.gmp.DAO;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import io.reactivex.Completable;
import io.reactivex.Flowable;

/**
 * Created by c_jhcanuto on 8/18/2017.
 */
@Dao
public interface ModelReviewerInfoDAO {
    @Query("select * from ModelReviewerInfo")
    Flowable<List<ModelReviewerInfoDAO>> getItemList();

    @Insert
    Completable insert(final ModelReviewerInfoDAO modelReviewerInfo);

    @Query("DELETE FROM ModelReviewerInfo")
    void delete();
}