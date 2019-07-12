 package com.unilab.gmp.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import io.reactivex.Completable;
import io.reactivex.Flowable;

import java.util.List;


 /**
  * Created by c_jhcanuto on 8/24/2017.
  */

 @Dao
 public interface TemplateModelCompanyBackgroundMajorChangesDAO {
     @Query("select * from TemplateModelCompanyBackgroundMajorChanges")
     Flowable<List<TemplateModelCompanyBackgroundMajorChangesDAO>> getItemList();

     @Insert
     Completable insert(final TemplateModelCompanyBackgroundMajorChangesDAO templateModelCompanyBackgroundMajorChanges);

     @Query("DELETE FROM TemplateModelCompanyBackgroundMajorChanges")
     void delete();
 }
