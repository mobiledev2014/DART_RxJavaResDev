package com.unilab.gmp.DAO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

import androidx.room.Dao;
import androidx.room.Entity;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by c_jsbustamante on 7/29/2016.
 */
@Dao
public interface ModelUser {
    @Query("select * from ModelUser")
    Flowable<List<ModelUser>> getItemList();

    @Insert
    Completable insert(final ModelUser modelUser);

    @Query("DELETE FROM ModelUser")
    void delete();
}
