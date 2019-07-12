package com.unilab.gmp.DAO;

import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.List;

import androidx.room.Dao;
import androidx.room.Entity;
import androidx.room.Insert;
import androidx.room.Query;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by c_jhcanuto on 7/31/2017.
 */

@Dao
public interface TemplateListModel  {
    @Query("select * from TemplateListModel")
    Flowable<List<TemplateListModel>> getItemList();

    @Insert
    Completable insert(final TemplateListModel templateListModel);

    @Query("DELETE FROM TemplateListModel")
    void delete();
}
