package com.unilab.gmp.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.unilab.gmp.model.ModelReportQuestion;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

/**
 * Created by c_jhcanuto on 8/23/2017.
 */
@Dao
public interface ModelReportQuestionDAO {
    @Query("select * from ModelReportQuestion")
    Flowable<List<ModelReportQuestion>> getItemList();

    @Insert
    Completable insert(final ModelReportQuestion modelReportQuestion);

    @Query("DELETE FROM ModelReportQuestion")
    void delete();

    @Query("DELETE FROM ModelReportQuestion where report_id = :id")
    void deleteId(String id);

    @Query("select * from ModelReportQuestion WHERE report_id = 'TEMPData' AND answer_id > '0'")
    List<ModelReportQuestion> getByAnswerAndReportIdTempData();

    @Query("select * from ModelReportQuestion WHERE report_id = :reportId AND answer_id > '0'")
    List<ModelReportQuestion> getByAnswerAndReportId(String reportId);

    @Query("select * from ModelReportQuestion WHERE report_id = :reportId AND question_id = :questionId")
    List<ModelReportQuestion> getByReportAndQuestionId(String reportId, String questionId);

    @Query("select * from ModelReportQuestion WHERE report_id = :reportId")
    List<ModelReportQuestion> getByReportId(String reportId);

    @Query("select * from ModelReportQuestion WHERE report_id = 'TEMPData'")
    List<ModelReportQuestion> getTempData();

    @Query("DELETE FROM ModelReportQuestion WHERE report_id = 'TEMPData'")
    void deleteTempData();

    @Query("UPDATE ModelReportQuestion SET report_id = :reportId, question_id = :questionId," +
            " answer_id = :answerId, category_id = :categoryId, answer_details = :answerDetails" +
            "  WHERE report_id = :reportId AND question_id = :questionId")
    void updateByReportAndQuestionId(String reportId, String questionId,String answerId, String categoryId, String answerDetails);

    @Query("UPDATE ModelReportQuestion SET report_id = :reportId " +
            "  WHERE report_id = :reportId")
    void updateByReportId(String reportId, String questionId,String answerId, String categoryId, String answerDetails);


}
