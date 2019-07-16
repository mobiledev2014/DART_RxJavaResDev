package com.unilab.gmp.DAO;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.unilab.gmp.model.ModelTemplateElements;

import io.reactivex.Completable;
import io.reactivex.Flowable;

/**
 * Created by c_rcmiguel on 8/22/2017.
 */

@Dao
public interface ModelTemplateElementsDAO {
    @Query("select * from ModelTemplateElements")
    Flowable<List<ModelTemplateElements>> getItemList();

    @Insert
    Completable insert(final ModelTemplateElements modelTemplateElements);

    @Query("DELETE FROM ModelTemplateElements")
    void delete();

    @Query("DELETE FROM ModelTemplateElements WHERE template_id = :templateId")
    void deleteId(String templateId);

    @Query("SELECT * FROM ModelTemplateElements WHERE element_id = :elementId AND template_id = :templateId")
    Flowable<List<ModelTemplateElements>> findByElementAndTemplateId(String elementId, String templateId);
}
