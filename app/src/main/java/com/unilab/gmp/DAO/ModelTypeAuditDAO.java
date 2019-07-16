package com.unilab.gmp.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.unilab.gmp.model.ModelTypeAudit;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

/**
 * Created by c_jhcanuto on 8/18/2017.
 */
@Dao
public interface ModelTypeAuditDAO {
    @Query("select * from ModelTypeAudit")
    Flowable<List<ModelTypeAudit>> getItemList();

    @Insert
    Completable insert(final ModelTypeAudit modelTypeAudit);

    @Query("DELETE FROM ModelTypeAudit")
    void delete();

    @Query("SELECT * from ModelTypeAudit WHERE scope_id = :scopeId")
    Flowable<List<ModelTypeAudit>> getScopeIdList(String scopeId);

    @Query("SELECT * from ModelTypeAudit WHERE scope_id = :scopeId")
    Flowable<ModelTypeAudit> getScopeId(String scopeId);

    @Query("UPDATE ModelTypeAudit SET scope_id = :scopeId, scope_name = :scope_name, create_date = :create_date, status = :status WHERE scope_id = :scopeId")
    Completable update(String scopeId, String scope_name, String create_date, String status);


}
