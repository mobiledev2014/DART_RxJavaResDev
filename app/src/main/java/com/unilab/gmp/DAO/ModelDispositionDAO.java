package com.unilab.gmp.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.unilab.gmp.model.ModelDisposition;
import com.unilab.gmp.model.ModelProduct;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;


@Dao
public interface ModelDispositionDAO  {
    @Query("select * from ModelDisposition")
    Flowable<List<ModelDisposition>> getList();


    @Query("select * from ModelDisposition WHERE disposition_id = :disposition_id")
    List<ModelDisposition> getListItem(String disposition_id);

     @Query("UPDATE ModelDisposition SET disposition_id=:disposition_id, disposition_name =:disposition_name, " +
            "create_date =:create_date, update_date =:update_date, " +
            "status =:status WHERE disposition_id=:rowid")
    Completable update(String disposition_id, String disposition_name,
                       String create_date, String update_date,
                       String status, String rowid);

    @Insert
    Completable insert(final ModelDisposition modelDispositions);


    @Query("DELETE FROM ModelDisposition")
    void delete();
}
