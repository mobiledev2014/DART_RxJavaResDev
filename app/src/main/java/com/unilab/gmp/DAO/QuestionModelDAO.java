package com.unilab.gmp.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.unilab.gmp.model.QuestionModel;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

/**
 * Created by c_rcmiguel on 2/20/2017.
 */

@Dao
public interface QuestionModelDAO {
    @Query("select * from QuestionModel")
    Flowable<List<QuestionModel>> getItemList();

    @Insert
    Completable insert(final QuestionModel questionModel);

    @Query("DELETE FROM QuestionModel")
    void delete();
}
