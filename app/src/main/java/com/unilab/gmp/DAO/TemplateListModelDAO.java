package com.unilab.gmp.DAO;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.unilab.gmp.model.TemplateListModel;

import io.reactivex.Completable;
import io.reactivex.Flowable;

/**
 * Created by c_jhcanuto on 7/31/2017.
 */

@Dao
public interface TemplateListModelDAO {
    @Query("select * from TemplateListModel")
    Flowable<List<TemplateListModel>> getItemList();

    @Insert
    Completable insert(final TemplateListModel templateListModel);

    @Query("DELETE FROM TemplateListModel")
    void delete();
}
