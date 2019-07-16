package com.unilab.gmp.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.unilab.gmp.model.ModelTemplateSubActivities;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

/**
 * Created by c_jhcanuto on 8/11/2017.
 */
@Dao
public interface ModelTemplateSubActivitiesDAO {
    @Query("select * from ModelTemplateSubActivities")
    Flowable<List<ModelTemplateSubActivities>> getItemList();

    @Insert
    Completable insert(final ModelTemplateSubActivities modelTemplateSubActivities);

    @Query("DELETE FROM ModelTemplateSubActivities")
    void delete();

    @Query("SELECT * from ModelTemplateSubActivities WHERE activity_id = :activityId AND template_id = :templateId AND subItemID = :subItemId")
    Flowable<List<ModelTemplateSubActivities>> getActvitiyTemplateSubItemId(String activityId, String templateId,String subItemId);
}
