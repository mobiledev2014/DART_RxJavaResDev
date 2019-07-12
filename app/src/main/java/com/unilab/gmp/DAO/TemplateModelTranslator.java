package com.unilab.gmp.DAO;

import androidx.room.Dao;
import androidx.room.Entity;

import com.orm.SugarRecord;

import androidx.room.Entity;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by c_jhcanuto on 8/24/2017.
 */
@Dao
public interface TemplateModelTranslator  {
    @Query("select * from TemplateModelTranslator")
    Flowable<List<TemplateModelTranslator>> getItemList();

    @Insert
    Completable insert(final TemplateModelTranslator templateModelTranslator);

    @Query("DELETE FROM TemplateModelTranslator")
    void delete();
}
