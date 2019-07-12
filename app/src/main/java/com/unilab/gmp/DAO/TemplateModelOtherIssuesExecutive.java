package com.unilab.gmp.DAO;

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
 * Created by c_jsbustamante on 08/11/2017.
 */

@Dao
public interface TemplateModelOtherIssuesExecutive  {
    @Query("select * from TemplateModelOtherIssuesExecutive")
    Flowable<List<TemplateModelOtherIssuesExecutive>> getItemList();

    @Insert
    Completable insert(final TemplateModelOtherIssuesExecutive templateModelOtherIssuesExecutive);

    @Query("DELETE FROM TemplateModelOtherIssuesExecutive")
    void delete();
}
