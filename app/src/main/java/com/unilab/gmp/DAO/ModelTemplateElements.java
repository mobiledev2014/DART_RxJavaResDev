package com.unilab.gmp.DAO;

import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;
import com.unilab.gmp.model.ModelTemplateQuestionDetails;

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
 * Created by c_rcmiguel on 8/22/2017.
 */

@Dao
public interface ModelTemplateElements  {
    @Query("select * from ModelTemplateElements")
    Flowable<List<ModelTemplateElements>> getItemList();

    @Insert
    Completable insert(final ModelTemplateElements modelTemplateElements);

    @Query("DELETE FROM ModelTemplateElements")
    void delete();
}