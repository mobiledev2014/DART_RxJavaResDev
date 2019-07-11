package com.unilab.gmp.DAO;

import com.orm.SugarRecord;

import androidx.room.Dao;
import androidx.room.Entity;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by c_jhcanuto on 8/23/2017.
 */
@Dao
public interface ModelReportQuestion  {
    @Query("select * from ModelReportQuestion")
    Flowable<List<ModelReportQuestion>> getItemList();

    @Insert
    Completable insert(final ModelReportQuestion modelReportQuestion);

    @Query("DELETE FROM ModelReportQuestion")
    void delete();
}
