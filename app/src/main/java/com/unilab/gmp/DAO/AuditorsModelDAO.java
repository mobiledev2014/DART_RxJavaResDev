package com.unilab.gmp.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.unilab.gmp.model.ApproverModel;
import com.unilab.gmp.model.AuditorsModel;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;


@Dao
public interface AuditorsModelDAO {
    @Query("select * from AuditorsModel WHERE auditor_id = :auditor_id")
    Flowable<List<AuditorsModel>> getItemList(String auditor_id);

    @Query("UPDATE AuditorsModel SET auditor_id=:auditor_id, fname =:fname, " +
            "mname =:mname, lname =:lname, designation =:designation, " +
            "company =:company, department =:department, create_date =:create_date, " +
            "update_date =:update_date, actions =:actions, email =:email, status =:status WHERE auditor_id=:rowid")
    Flowable<List<ApproverModel>> updateApprover(String auditor_id, String fname, String mname, String lname,
                                                 String company, String department, String designation,
                                                 String update_date,String actions, String email, String status,
                                                 String create_date, String rowid);

    @Insert
    Completable insert(final List<AuditorsModel> auditorsModels); // currently, we must put final before user variable or you will get error when compile

    @Query("DELETE FROM AuditorsModel")
    void delete();
}
