package com.unilab.gmp.DAO;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.unilab.gmp.model.ModelCategory;
import com.unilab.gmp.model.ModelProduct;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;


@Dao
public interface ModelProductDAO {
    @Query("select * from ModelProduct")
    Flowable<List<ModelProduct>> getList();


    @Query("select * from ModelProduct WHERE product_id = :product_id")
    List<ModelProduct> getListItem(String product_id);


    @Query("UPDATE ModelProduct SET product_id=:product_id,company_id =:company_id, product_name =:category_name, " +
            "type = :type,create_date =:create_date, update_date =:update_date, " +
            "status =:status WHERE product_id=:rowid")
    Completable update(String product_id,String company_id, String product_name,
                       String type, String create_date,
                       String update_date,
                       String status, String rowid);
    @Insert
    Completable insert(final ModelProduct modelProducts);


    @Query("DELETE FROM ModelProduct")
    void delete();
}
