package com.unilab.gmp.DAO;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.unilab.gmp.model.ModelTemplateActivities;

import io.reactivex.Completable;
import io.reactivex.Flowable;

/**
 * Created by c_jhcanuto on 8/11/2017.
 */

@Dao
public interface ModelTemplateActivitiesDAO {
    @Query("select * from ModelTemplateActivities")
    Flowable<List<ModelTemplateActivities>> getItemList();

    @Insert
    Completable insert(final ModelTemplateActivities modelTemplateActivities);

    @Query("DELETE FROM ModelTemplateActivities")
    void delete();

    @Query("select * from ModelTemplateActivities WHERE activityID = :activityId AND template_id = :templateId")
    List<ModelTemplateActivities> searchActIdAndTempId(String activityId, String templateId);

}
