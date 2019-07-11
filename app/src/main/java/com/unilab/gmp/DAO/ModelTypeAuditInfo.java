package com.unilab.gmp.DAO;

import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;
import com.unilab.gmp.model.ModelTypeAudit;

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
 * Created by c_jhcanuto on 8/18/2017.
 */
@Dao
public interface ModelTypeAuditInfo  {
    @Query("select * from ModelTypeAuditInfo")
    Flowable<List<ModelTypeAuditInfo>> getItemList();

    @Insert
    Completable insert(final ModelTypeAuditInfo modelTypeAuditInfo);

    @Query("DELETE FROM ModelTypeAuditInfo")
    void delete();
}
