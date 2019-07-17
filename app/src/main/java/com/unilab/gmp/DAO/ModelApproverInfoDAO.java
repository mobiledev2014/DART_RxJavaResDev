package com.unilab.gmp.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.unilab.gmp.model.ConfigModel;
import com.unilab.gmp.model.ModelApproverInfo;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;


@Dao
public interface ModelApproverInfoDAO {
    @Query("select * from ModelApproverInfo")
    Flowable<List<ModelApproverInfo>> getItemList();

//    @Query("UPDATE AuditorsModel SET auditor_id=:auditor_id, fname =:fname, " +
//            "mname =:mname, lname =:lname, designation =:designation, " +
//            "company =:company, department =:department, create_date =:create_date, " +
//            "update_date =:update_date, actions =:actions, email =:email, status =:status WHERE auditor_id=:rowid")
//    Flowable<List<ApproverModel>> updateApprover(String auditor_id, String fname, String mname, String lname,
//                                                 String company, String department, String designation,
//                                                 String update_date, String actions, String email, String status,
//                                                 String create_date, String rowid);

    @Insert
    Completable insert(final List<ModelApproverInfo> modelApproverInfos);

    @Query("DELETE FROM ModelApproverInfo")
    void delete();

}
