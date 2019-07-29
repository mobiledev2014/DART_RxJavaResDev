package com.unilab.gmp.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.unilab.gmp.model.ModelDateOfAudit;
import com.unilab.gmp.model.ModelDistribution;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;


@Dao
public interface ModelDateOfAuditDAO  {
    @Query("select * from ModelDateOfAudit")
    Flowable<List<ModelDateOfAudit>> getList();


    @Query("select * from ModelDateOfAudit WHERE report_id = :report_id")
    List<ModelDateOfAudit> getReportItemList(String report_id);


//    @Query("select * from ModelCompany WHERE approver_id = :approver_id")
//    Flowable<List<ApproverModel>> getListItem(String approver_id);

    @Query("UPDATE ModelDateOfAudit SET report_id=:report_id WHERE report_id=:report_id")
    Flowable<List<ModelDateOfAudit>> updateReportId(String report_id);

    @Insert
    Completable insert(final ModelDateOfAudit modelDateOfAudit);


    @Query("DELETE FROM ModelDateOfAudit")
    void delete();

    @Query("DELETE FROM ModelDateOfAudit WHERE report_id = :report_id")
    void deleteId(String report_id);

}
