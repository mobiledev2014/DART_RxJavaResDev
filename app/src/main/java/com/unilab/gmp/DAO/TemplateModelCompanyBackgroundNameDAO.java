 package com.unilab.gmp.DAO;
import androidx.room.Dao;

import androidx.room.Insert;
import androidx.room.Query;

import com.unilab.gmp.model.TemplateModelCompanyBackgroundName;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

 /**
  * Created by c_jhcanuto on 8/24/2017.
  */

 @Dao
 public interface TemplateModelCompanyBackgroundNameDAO {
     @Query("select * from TemplateModelCompanyBackgroundName")
     Flowable<List<TemplateModelCompanyBackgroundName>> getItemList();

     @Insert
     Completable insert(final TemplateModelCompanyBackgroundName templateModelCompanyBackgroundName);

     @Query("DELETE FROM TemplateModelCompanyBackgroundName")
     void delete();

     @Query("DELETE FROM TemplateModelCompanyBackgroundName WHERE report_id = :reportId")
     void deleteId(String reportId);

     @Query("select * from TemplateModelCompanyBackgroundName WHERE company_id = :companyId")
     List<TemplateModelCompanyBackgroundName> getByCompanyId(String companyId);

     @Query("select * from TemplateModelCompanyBackgroundName WHERE report_id = :reportId")
     List<TemplateModelCompanyBackgroundName> getByReportId(String reportId);


     @Query("UPDATE TemplateModelCompanyBackgroundName SET report_id = :reportId WHERE report_id = :reportId")
     void updateReportId(String reportId);

 }
