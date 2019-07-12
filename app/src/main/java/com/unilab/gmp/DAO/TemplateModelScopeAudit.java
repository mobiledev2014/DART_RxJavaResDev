package com.unilab.gmp.DAO;

import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;
import com.unilab.gmp.adapter.templates.AdapterScopeAuditInterest;
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
public interface TemplateModelScopeAudit  {
    @Query("select * from TemplateModelScopeAudit")
    Flowable<List<TemplateModelScopeAudit>> getItemList();

    @Insert
    Completable insert(final TemplateModelScopeAudit templateModelScopeAudit);

    @Query("DELETE FROM TemplateModelScopeAudit")
    void delete();

}
