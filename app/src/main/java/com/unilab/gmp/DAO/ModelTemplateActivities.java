package com.unilab.gmp.DAO;

import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;
import com.unilab.gmp.model.ModelTemplateSubActivities;

import java.util.ArrayList;
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
 * Created by c_jhcanuto on 8/11/2017.
 */

@Dao
public interface ModelTemplateActivities  {
    @Query("select * from ModelTemplateActivities")
    Flowable<List<ModelTemplateActivities>> getItemList();

    @Insert
    Completable insert(final ModelTemplateActivities modelTemplateActivities);

    @Query("DELETE FROM ModelTemplateActivities")
    void delete();
}
