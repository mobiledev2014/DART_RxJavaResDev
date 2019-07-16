package com.unilab.gmp.DAO;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.unilab.gmp.model.ModelTypeAuditInfo;

import io.reactivex.Completable;
import io.reactivex.Flowable;

/**
 * Created by c_jhcanuto on 8/18/2017.
 */
@Dao
public interface ModelTypeAuditInfoDAO {
    @Query("select * from ModelTypeAuditInfo")
    Flowable<List<ModelTypeAuditInfo>> getItemList();

    @Insert
    Completable insert(final ModelTypeAuditInfo modelTypeAuditInfo);

    @Query("DELETE FROM ModelTypeAuditInfo")
    void delete();
}
