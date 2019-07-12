package com.unilab.gmp.DAO;

import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;
import com.unilab.gmp.model.TemplateModelScopeAuditInterest;

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
 * Created by c_jhcanuto on 8/23/2017.
 */

@Dao
public interface TemplateModelScopeAuditCopy  {
    @Query("select * from TemplateModelScopeAuditCopy")
    Flowable<List<TemplateModelScopeAuditCopy>> getItemList();

    @Insert
    Completable insert(final TemplateModelScopeAuditCopy templateModelScopeAuditCopy);

    @Query("DELETE FROM TemplateModelScopeAuditCopy")
    void delete();
}
