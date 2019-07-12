package com.unilab.gmp.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

/**
 * Created by c_jsbustamante on 7/29/2016.
 */
@Dao
public interface ModelUserDAO {
    @Query("select * from ModelUser")
    Flowable<List<ModelUserDAO>> getItemList();

    @Insert
    Completable insert(final ModelUserDAO modelUser);

    @Query("DELETE FROM ModelUser")
    void delete();
}
