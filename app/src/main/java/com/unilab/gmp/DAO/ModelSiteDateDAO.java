 package com.unilab.gmp.DAO;
import androidx.room.Dao;

import androidx.room.Insert;
import androidx.room.Query;

import com.unilab.gmp.model.ModelSiteDate;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

 /**
  * Created by c_rcmiguel on 10/19/2017.
  */

 @Dao
 public interface ModelSiteDateDAO {
     @Query("select * from ModelSiteDate")
     Flowable<List<ModelSiteDate>> getItemList();

     @Insert
     Completable insert(final ModelSiteDate modelSiteDate);

     @Query("DELETE FROM ModelSiteDate")
     void delete();

     @Query("select * from ModelSiteDate WHERE company_id = :companyId")
     List<ModelSiteDate> getByCompanyId(String companyId);

 }
