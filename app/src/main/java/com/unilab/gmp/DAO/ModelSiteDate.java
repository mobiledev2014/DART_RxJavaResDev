 package com.unilab.gmp.DAO;
import androidx.room.Dao;
import androidx.room.Entity;

import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

import androidx.room.Entity;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import lombok.Getter;
import lombok.Setter;

 /**
  * Created by c_rcmiguel on 10/19/2017.
  */

 @Dao
 public interface ModelSiteDate  {
     @Query("select * from ModelSiteDate")
     Flowable<List<ModelSiteDate>> getItemList();

     @Insert
     Completable insert(final ModelSiteDate modelSiteDate);

     @Query("DELETE FROM ModelSiteDate")
     void delete();
 }
