package com.unilab.gmp.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.unilab.gmp.model.ApproverModel;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;


@Dao
public interface ApproverModelDAO  {

    @Query("select * from ApproverModel")
    Flowable<List<ApproverModel>> getApproverList();


    @Query("select * from ApproverModel WHERE approver_id = :approver_id")
    Flowable<List<ApproverModel>> getListItem(String approver_id);

    @Insert
    Completable insert(final ApproverModel approverModel);


    @Query("DELETE FROM ApproverModel")
    void delete();


}
