 package com.unilab.gmp.DAO;
import androidx.room.Dao;

import androidx.room.Insert;
import androidx.room.Query;

import com.unilab.gmp.model.TemplateModelPresentDuringMeeting;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

 /**
 * Created by c_jhcanuto on 8/24/2017.
 */
 @Dao
 public interface TemplateModelPresentDuringMeetingDAO {
     @Query("select * from TemplateModelPresentDuringMeeting")
     Flowable<List<TemplateModelPresentDuringMeeting>> getItemList();

     @Insert
     Completable insert(final TemplateModelPresentDuringMeeting templateModelPresentDuringMeeting);

     @Query("DELETE FROM TemplateModelPresentDuringMeeting")
     void delete();

     @Query("DELETE FROM TemplateModelPresentDuringMeeting WHERE report_id = :reportId")
     void deleteId(String reportId);
}
