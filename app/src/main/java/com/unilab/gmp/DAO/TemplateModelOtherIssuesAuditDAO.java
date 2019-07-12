package com.unilab.gmp.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

/**
 * Created by c_jsbustamante on 08/11/2017.
 */

@Dao
public interface TemplateModelOtherIssuesAuditDAO {
    @Query("select * from TemplateModelOtherIssuesAudit")
    Flowable<List<TemplateModelOtherIssuesAuditDAO>> getItemList();

    @Insert
    Completable insert(final TemplateModelOtherIssuesAuditDAO templateModelOtherIssuesAudit);

    @Query("DELETE FROM TemplateModelOtherIssuesAudit")
    void delete();
}
