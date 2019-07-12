 package com.unilab.gmp.DAO;
import androidx.room.Dao;
import androidx.room.Entity;

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
 * Created by c_jhcanuto on 8/24/2017.
 */
 @Dao
 public interface TemplateModelPresentDuringMeeting   {
     @Query("select * from TemplateModelPresentDuringMeeting")
     Flowable<List<TemplateModelPresentDuringMeeting>> getItemList();

     @Insert
     Completable insert(final TemplateModelPresentDuringMeeting templateModelPresentDuringMeeting);

     @Query("DELETE FROM TemplateModelPresentDuringMeeting")
     void delete();
}
