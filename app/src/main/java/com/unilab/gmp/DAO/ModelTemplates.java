package com.unilab.gmp.DAO;

import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;
import com.unilab.gmp.model.ModelDateOfAudit;
import com.unilab.gmp.model.ModelTemplateActivities;
import com.unilab.gmp.model.ModelTemplateElements;

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
 * Created by c_jhcanuto on 8/11/2017.
 */

@Dao
public interface ModelTemplates {
    @Query("select * from ModelTemplates")
    Flowable<List<ModelTemplates>> getItemList();

    @Insert
    Completable insert(final ModelTemplates modelTemplates);

    @Query("DELETE FROM ModelTemplates")
    void delete();
}

