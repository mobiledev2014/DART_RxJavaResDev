package com.unilab.gmp.DAO;

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
 * Created by c_rcmiguel on 9/19/2017.
 */


@Dao
public interface TemplateModelDistributionOthers  {
    @Query("select * from TemplateModelDistributionOthers")
    Flowable<List<TemplateModelDistributionOthers>> getItemList();

    @Insert
    Completable insert(final TemplateModelDistributionOthers templateModelDistributionOthers);

    @Query("DELETE FROM TemplateModelDistributionOthers")
    void delete();
}
