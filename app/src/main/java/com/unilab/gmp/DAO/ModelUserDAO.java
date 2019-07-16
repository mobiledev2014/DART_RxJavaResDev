package com.unilab.gmp.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.unilab.gmp.model.ModelUser;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

/**
 * Created by c_jsbustamante on 7/29/2016.
 */
@Dao
public interface ModelUserDAO {
    @Query("select * from ModelUser")
    Flowable<List<ModelUser>> getItemList();

    @Insert
    Completable insert(final ModelUser modelUser);

    @Query("DELETE FROM ModelUser")
    void delete();
}
