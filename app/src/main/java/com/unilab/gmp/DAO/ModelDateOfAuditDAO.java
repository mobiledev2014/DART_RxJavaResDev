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


//    @Query("select * from ModelCompany WHERE approver_id = :approver_id")
//    Flowable<List<ApproverModel>> getListItem(String approver_id);

//    @Query("UPDATE ModelCompany SET approver_id=:approver_id, firstname =:firstname, " +
//            "middlename =:middlename, lastname =:lastname, designation =:designation, " +
//            "company =:company, department =:department, create_date =:create_date, " +
//            "update_date =:update_date, email =:email, status =:status WHERE approver_id=:rowid")
//    Flowable<List<ModelCompany>> updateApprover(String approver_id, String firstname,String middlename,String lastname,
//                                                 String company,String department,String designation,
//                                                 String update_date,String email,String status,
//                                                 String create_date,String rowid);

    @Insert
    Completable insert(final ModelDateOfAudit modelDateOfAudit);


    @Query("DELETE FROM ModelDateOfAudit")
    void delete();

    @Query("DELETE FROM ModelDateOfAudit WHERE report_id = :report_id")
    void deleteId(String report_id);

}
