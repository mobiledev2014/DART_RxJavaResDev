package com.unilab.gmp.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

/**
 * Created by c_jhcanuto on 7/31/2017.
 */

@Dao
public interface ReviewerModelDAO {
    @Query("select * from ReviewerModel")
    Flowable<List<ReviewerModelDAO>> getItemList();

    @Insert
    Completable insert(final ReviewerModelDAO reviewerModel);

    @Query("DELETE FROM ReviewerModel")
    void delete();
}
