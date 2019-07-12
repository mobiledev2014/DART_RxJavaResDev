 package com.unilab.gmp.DAO;

import androidx.room.Dao;
import androidx.room.Entity;
import androidx.room.Insert;
import androidx.room.Query;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import lombok.Getter;
import lombok.Setter;

import com.google.gson.annotations.SerializedName;

import java.util.List;


 /**
  * Created by c_jhcanuto on 8/24/2017.
  */

 @Dao
 public interface TemplateModelCompanyBackgroundMajorChanges {
     @Query("select * from TemplateModelCompanyBackgroundMajorChanges")
     Flowable<List<TemplateModelCompanyBackgroundMajorChanges>> getItemList();

     @Insert
     Completable insert(final TemplateModelCompanyBackgroundMajorChanges templateModelCompanyBackgroundMajorChanges);

     @Query("DELETE FROM TemplateModelCompanyBackgroundMajorChanges")
     void delete();
 }
