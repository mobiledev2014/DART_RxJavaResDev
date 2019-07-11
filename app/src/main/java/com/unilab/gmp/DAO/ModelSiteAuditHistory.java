 package com.unilab.gmp.DAO;
import androidx.room.Dao;
import androidx.room.Entity;

import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;
import com.unilab.gmp.model.ModelSiteDate;
import com.unilab.gmp.model.TemplateModelCompanyBackgroundMajorChanges;
import com.unilab.gmp.model.TemplateModelCompanyBackgroundName;

import java.util.ArrayList;
import java.util.List;

import androidx.room.Entity;
import androidx.room.Insert;
import androidx.room.Query;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import lombok.Getter;
import lombok.Setter;

 /**
  * Created by c_jhcanuto on 10/19/2017.
  */

 @Dao
 public interface ModelSiteAuditHistory  {
     @Query("select * from ModelSiteAuditHistory")
     Flowable<List<ModelSiteAuditHistory>> getItemList();

     @Insert
     Completable insert(final ModelSiteAuditHistory modelSiteAuditHistory);

     @Query("DELETE FROM ModelSiteAuditHistory")
     void delete();
 }
