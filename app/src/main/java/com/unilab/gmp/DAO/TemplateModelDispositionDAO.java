package com.unilab.gmp.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.unilab.gmp.model.TemplateModelDisposition;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

/**
 * Created by c_jhcanuto on 8/24/2017.
 */

@Dao
public interface TemplateModelDispositionDAO {
    @Query("select * from TemplateModelDisposition")
    Flowable<List<TemplateModelDisposition>> getItemList();

    @Insert
    Completable insert(final TemplateModelDisposition templateModelDisposition);

    @Query("DELETE FROM TemplateModelDisposition")
    void delete();
}
