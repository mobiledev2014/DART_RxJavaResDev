package com.unilab.gmp.DAO;

import androidx.room.Dao;

import androidx.room.Insert;
import androidx.room.Query;

import com.unilab.gmp.model.TemplateModelTranslator;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

/**
 * Created by c_jhcanuto on 8/24/2017.
 */
@Dao
public interface TemplateModelTranslatorDAO {
    @Query("select * from TemplateModelTranslator")
    Flowable<List<TemplateModelTranslator>> getItemList();

    @Insert
    Completable insert(final TemplateModelTranslator templateModelTranslator);

    @Query("DELETE FROM TemplateModelTranslator")
    void delete();

    @Query("DELETE FROM TemplateModelTranslator WHERE report_id = :reportId")
    void deleteId(String reportId);

    @Query("select * from TemplateModelTranslator WHERE report_id = :reportId")
    List<TemplateModelTranslator> getByReportId(String reportId);

    @Query("UPDATE TemplateModelTranslator SET report_id = :reportId WHERE report_id = :reportId")
    void updateReportId(String reportId);

}
