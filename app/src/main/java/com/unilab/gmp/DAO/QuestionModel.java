package com.unilab.gmp.DAO;

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
 * Created by c_rcmiguel on 2/20/2017.
 */

@Dao
public interface QuestionModel {
    @Query("select * from QuestionModel")
    Flowable<List<QuestionModel>> getItemList();

    @Insert
    Completable insert(final QuestionModel questionModel);

    @Query("DELETE FROM QuestionModel")
    void delete();
}
