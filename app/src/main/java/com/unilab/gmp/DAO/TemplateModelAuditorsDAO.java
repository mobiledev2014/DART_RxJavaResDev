package com.unilab.gmp.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.unilab.gmp.model.TemplateModelAuditors;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

/**
 * Created by c_jhcanuto on 8/24/2017.
 */
@Dao
public interface TemplateModelAuditorsDAO {
    @Query("select * from TemplateModelAuditors")
    Flowable<List<TemplateModelAuditors>> getItemList();

    @Insert
    Completable insert(final TemplateModelAuditors templateModelAuditors);

    @Query("DELETE FROM TemplateModelAuditors")
    void delete();

    @Query("DELETE FROM TemplateModelAuditors WHERE report_id = :reportId")
    void deleteId(String reportId);

}
