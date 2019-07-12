 package com.unilab.gmp.DAO;

 import androidx.room.Dao;
 import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

 /**
  * Created by c_rcmiguel on 7/31/2017.
  */

 @Dao
 public interface SupplierAndCompanyInformationModelDAO {
  @Query("select * from SupplierAndCompanyInformationModel")
  Flowable<List<SupplierAndCompanyInformationModelDAO>> getItemList();

  @Insert
  Completable insert(final SupplierAndCompanyInformationModelDAO supplierAndCompanyInformationModel);

  @Query("DELETE FROM SupplierAndCompanyInformationModel")
  void delete();
 }
