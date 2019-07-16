package com.unilab.gmp.DAO;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.unilab.gmp.model.TemplateModelScopeAuditCopy;

import io.reactivex.Completable;
import io.reactivex.Flowable;

/**
 * Created by c_jhcanuto on 8/23/2017.
 */

@Dao
public interface TemplateModelScopeAuditCopyDAO {
    @Query("select * from TemplateModelScopeAuditCopy")
    Flowable<List<TemplateModelScopeAuditCopy>> getItemList();

    @Insert
    Completable insert(final TemplateModelScopeAuditCopy templateModelScopeAuditCopy);

    @Query("DELETE FROM TemplateModelScopeAuditCopy")
    void delete();
}
