 package com.unilab.gmp.DAO;
import com.orm.SugarRecord;

import androidx.room.Dao;
import androidx.room.Entity;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import lombok.Getter;
import lombok.Setter;

 /**
  * Created by c_rcmiguel on 7/31/2017.
  */

 @Dao
 public interface SupplierAndCompanyInformationModel  {
  @Query("select * from SupplierAndCompanyInformationModel")
  Flowable<List<SupplierAndCompanyInformationModel>> getItemList();

  @Insert
  Completable insert(final SupplierAndCompanyInformationModel supplierAndCompanyInformationModel);

  @Query("DELETE FROM SupplierAndCompanyInformationModel")
  void delete();
 }