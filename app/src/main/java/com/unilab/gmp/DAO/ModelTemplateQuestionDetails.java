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
 * Created by c_rcmiguel on 8/22/2017.
 */

@Dao
public interface ModelTemplateQuestionDetails {
    @Query("select * from ModelTemplateQuestionDetails")
    Flowable<List<ModelTemplateQuestionDetails>> getItemList();

    @Insert
    Completable insert(final ModelTemplateQuestionDetails modelTemplateQuestionDetails);

    @Query("DELETE FROM ModelTemplateQuestionDetails")
    void delete();
}
