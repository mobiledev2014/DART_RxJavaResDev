package com.unilab.gmp.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

/**
 * Created by c_jhcanuto on 8/18/2017.
 */
@Dao
public interface ModelTypeAuditDAO {
    @Query("select * from ModelTypeAudit")
    Flowable<List<ModelTypeAuditDAO>> getItemList();

    @Insert
    Completable insert(final ModelTypeAuditDAO modelTypeAudit);

    @Query("DELETE FROM ModelTypeAudit")
    void delete();
}
