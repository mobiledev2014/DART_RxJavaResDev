package com.unilab.gmp.DAO;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import io.reactivex.Completable;
import io.reactivex.Flowable;

/**
 * Created by c_jhcanuto on 8/11/2017.
 */

@Dao
public interface ModelTemplateActivitiesDAO {
    @Query("select * from ModelTemplateActivities")
    Flowable<List<ModelTemplateActivitiesDAO>> getItemList();

    @Insert
    Completable insert(final ModelTemplateActivitiesDAO modelTemplateActivities);

    @Query("DELETE FROM ModelTemplateActivities")
    void delete();
}
