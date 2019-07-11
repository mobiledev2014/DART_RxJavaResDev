package com.unilab.gmp.DAO;

import com.orm.SugarRecord;

import androidx.room.Dao;
import androidx.room.Entity;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by c_jhcanuto on 7/31/2017.
 */

@Dao
public interface ReviewerModel  {
    @Query("select * from ReviewerModel")
    Flowable<List<ReviewerModel>> getItemList();

    @Insert
    Completable insert(final ReviewerModel reviewerModel);

    @Query("DELETE FROM ReviewerModel")
    void delete();
}
