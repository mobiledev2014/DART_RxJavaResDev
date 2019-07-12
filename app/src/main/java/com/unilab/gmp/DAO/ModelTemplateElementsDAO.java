package com.unilab.gmp.DAO;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import io.reactivex.Completable;
import io.reactivex.Flowable;

/**
 * Created by c_rcmiguel on 8/22/2017.
 */

@Dao
public interface ModelTemplateElementsDAO {
    @Query("select * from ModelTemplateElements")
    Flowable<List<ModelTemplateElementsDAO>> getItemList();

    @Insert
    Completable insert(final ModelTemplateElementsDAO modelTemplateElements);

    @Query("DELETE FROM ModelTemplateElements")
    void delete();
}
