package com.unilab.gmp.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.unilab.gmp.model.TemplateModelPersonelMetDuring;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

/**
 * Created by c_jhcanuto on 8/24/2017.
 */

@Dao
public interface TemplateModelPersonelMetDuringDAO {
    @Query("select * from TemplateModelPersonelMetDuring")
    Flowable<List<TemplateModelPersonelMetDuring>> getItemList();

    @Insert
    Completable insert(final TemplateModelPersonelMetDuring templateModelPersonelMetDuring);

    @Query("DELETE FROM TemplateModelPersonelMetDuring")
    void delete();

    @Query("DELETE FROM TemplateModelPersonelMetDuring WHERE report_id = :reportId")
    void deleteId(String reportId);
}
