package com.unilab.gmp.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

/**
 * Created by c_jhcanuto on 8/11/2017.
 */
@Dao
public interface ModelTemplateSubActivitiesDAO {
    @Query("select * from ModelTemplateSubActivities")
    Flowable<List<ModelTemplateSubActivitiesDAO>> getItemList();

    @Insert
    Completable insert(final ModelTemplateSubActivitiesDAO modelTemplateSubActivities);

    @Query("DELETE FROM ModelTemplateSubActivities")
    void delete();
}
