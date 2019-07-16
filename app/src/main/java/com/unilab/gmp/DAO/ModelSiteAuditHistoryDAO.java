 package com.unilab.gmp.DAO;
import androidx.room.Dao;

import java.util.List;

import androidx.room.Insert;
import androidx.room.Query;

import com.unilab.gmp.model.ModelSiteAuditHistory;

import io.reactivex.Completable;
import io.reactivex.Flowable;

 /**
  * Created by c_jhcanuto on 10/19/2017.
  */

 @Dao
 public interface ModelSiteAuditHistoryDAO {
     @Query("select * from ModelSiteAuditHistory")
     Flowable<List<ModelSiteAuditHistory>> getItemList();

     @Insert
     Completable insert(final ModelSiteAuditHistory modelSiteAuditHistory);

     @Query("DELETE FROM ModelSiteAuditHistory")
     void delete();
 }
