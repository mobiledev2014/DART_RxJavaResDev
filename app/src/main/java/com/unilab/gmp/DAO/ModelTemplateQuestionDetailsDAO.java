package com.unilab.gmp.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

/**
 * Created by c_rcmiguel on 8/22/2017.
 */

@Dao
public interface ModelTemplateQuestionDetailsDAO {
    @Query("select * from ModelTemplateQuestionDetails")
    Flowable<List<ModelTemplateQuestionDetailsDAO>> getItemList();

    @Insert
    Completable insert(final ModelTemplateQuestionDetailsDAO modelTemplateQuestionDetails);

    @Query("DELETE FROM ModelTemplateQuestionDetails")
    void delete();
}
