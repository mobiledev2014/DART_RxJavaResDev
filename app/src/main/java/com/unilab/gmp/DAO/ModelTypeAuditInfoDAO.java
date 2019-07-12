package com.unilab.gmp.DAO;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import io.reactivex.Completable;
import io.reactivex.Flowable;

/**
 * Created by c_jhcanuto on 8/18/2017.
 */
@Dao
public interface ModelTypeAuditInfoDAO {
    @Query("select * from ModelTypeAuditInfo")
    Flowable<List<ModelTypeAuditInfoDAO>> getItemList();

    @Insert
    Completable insert(final ModelTypeAuditInfoDAO modelTypeAuditInfo);

    @Query("DELETE FROM ModelTypeAuditInfo")
    void delete();
}
