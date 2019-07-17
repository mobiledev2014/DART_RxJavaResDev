 package com.unilab.gmp.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.unilab.gmp.model.TemplateModelCompanyBackgroundMajorChanges;

import io.reactivex.Completable;
import io.reactivex.Flowable;

import java.util.List;


 /**
  * Created by c_jhcanuto on 8/24/2017.
  */

 @Dao
 public interface TemplateModelCompanyBackgroundMajorChangesDAO {
     @Query("select * from TemplateModelCompanyBackgroundMajorChanges")
     Flowable<List<TemplateModelCompanyBackgroundMajorChanges>> getItemList();

     @Insert
     Completable insert(final TemplateModelCompanyBackgroundMajorChanges templateModelCompanyBackgroundMajorChanges);

     @Query("DELETE FROM TemplateModelCompanyBackgroundMajorChanges")
     void delete();

     @Query("DELETE FROM TemplateModelCompanyBackgroundMajorChanges WHERE report_id = :reportId")
     void deleteId(String reportId);

     @Query("select * from TemplateModelCompanyBackgroundMajorChanges WHERE company_id = :companyId AND report_id = '0'")
     List<TemplateModelCompanyBackgroundMajorChanges> getByCompanyAndReportId(String companyId);

     @Query("select * from TemplateModelCompanyBackgroundMajorChanges WHERE report_id = report_id")
     List<TemplateModelCompanyBackgroundMajorChanges> getByReportId(String reportId);

     @Query("UPDATE TemplateModelCompanyBackgroundMajorChanges WHERE report_id = :reportId")
     void updateReportId(String reportId);


 }
