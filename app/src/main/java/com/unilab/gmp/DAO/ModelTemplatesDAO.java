package com.unilab.gmp.DAO;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.unilab.gmp.model.ModelTemplates;

import io.reactivex.Completable;
import io.reactivex.Flowable;

/**
 * Created by c_jhcanuto on 8/11/2017.
 */

@Dao
public interface ModelTemplatesDAO {
    @Query("select * from ModelTemplates")
    Flowable<List<ModelTemplates>> getItemList();

    @Insert
    Completable insert(final ModelTemplates modelTemplates);

    @Query("DELETE FROM ModelTemplates")
    void delete();

    @Query("UPDATE ModelTemplates SET status='2' where status = 1")
    Completable updateStatus1();

    @Query("UPDATE ModelTemplates SET templateID = :templateId, productType = :productType, " +
            "templateName = :templateName, status = :status, dateUpdated = :dateUpdated   " +
            "where templateID = :templateId")
    Completable updateStatusTemplateId(String templateId, String productType, String templateName, String status,
                                       String dateUpdated);

    @Query("SELECT * from ModelTemplates WHERE status = '1' OR status = '2'")
    Flowable<List<ModelTemplates>> getStatus1OR2();

    @Query("SELECT * from ModelTemplates WHERE templateID = :templateId")
    Flowable<ModelTemplates> getTemplateId(String templateId);

    @Query("SELECT * from ModelTemplates WHERE templateID = :templateId")
    Flowable<List<ModelTemplates>> getTemplateIdList(String templateId);

    @Query("DELETE FROM ModelTemplates WHERE templateID = :templateId")
    void deleteTemplateId(String templateId);

}

