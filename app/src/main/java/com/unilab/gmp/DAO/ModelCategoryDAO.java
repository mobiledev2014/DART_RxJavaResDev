package com.unilab.gmp.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.unilab.gmp.model.ApproverModel;
import com.unilab.gmp.model.ModelCategory;
import com.unilab.gmp.model.ModelCompany;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;


@Dao
public interface ModelCategoryDAO {
    @Query("select * from ModelCategory")
    Flowable<List<ModelCategory>> getList();


    @Query("select * from ModelCategory WHERE category_id = :categoryid")
    List<ModelCategory> getListItem(String categoryid);


    @Query("UPDATE ModelCategory SET category_id=:category_id, category_name =:category_name, " +
            "create_date =:category_createdate, update_date =:category_updatedate, " +
            "status =:category_status WHERE category_id=:rowid")
    Completable update(String category_id, String category_name,
                                         String category_createdate, String category_updatedate,
                                         String category_status, String rowid);

    @Insert
    Completable insert(final ModelCategory modelCategories);


    @Query("DELETE FROM ModelCategory")
    void delete();
}
