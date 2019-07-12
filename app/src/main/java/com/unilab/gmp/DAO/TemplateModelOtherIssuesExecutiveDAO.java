package com.unilab.gmp.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

/**
 * Created by c_jsbustamante on 08/11/2017.
 */

@Dao
public interface TemplateModelOtherIssuesExecutiveDAO {
    @Query("select * from TemplateModelOtherIssuesExecutive")
    Flowable<List<TemplateModelOtherIssuesExecutiveDAO>> getItemList();

    @Insert
    Completable insert(final TemplateModelOtherIssuesExecutiveDAO templateModelOtherIssuesExecutive);

    @Query("DELETE FROM TemplateModelOtherIssuesExecutive")
    void delete();
}
