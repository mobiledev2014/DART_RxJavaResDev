package com.unilab.gmp.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.unilab.gmp.model.ModelClassificationCategory;
import com.unilab.gmp.model.ModelDistribution;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;


@Dao
public interface ModelDistributionDAO  {
    @Query("select * from ModelDistribution")
    Flowable<List<ModelDistribution>> getList();


    @Query("select * from ModelDistribution WHERE distribution_id = :distribution_id")
    List<ModelDistribution> getListItem(String distribution_id);



    @Query("UPDATE ModelDistribution SET distribution_id=:distribution_id, distribution_name =:distribution_name, " +
            "create_date =:create_date, update_date =:update_date, " +
            "status =:status WHERE distribution_id=:rowid")
    Completable update(String distribution_id, String distribution_name,
                       String create_date, String update_date,
                       String status, String rowid);

    @Insert
    Completable insert(final ModelDistribution modelDistribution);


    @Query("DELETE FROM ModelDistribution")
    void delete();
}
