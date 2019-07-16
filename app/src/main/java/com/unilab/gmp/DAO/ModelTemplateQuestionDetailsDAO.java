package com.unilab.gmp.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.unilab.gmp.model.ModelTemplateQuestionDetails;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

/**
 * Created by c_rcmiguel on 8/22/2017.
 */

@Dao
public interface ModelTemplateQuestionDetailsDAO {
    @Query("select * from ModelTemplateQuestionDetails")
    Flowable<List<ModelTemplateQuestionDetails>> getItemList();

    @Insert
    Completable insert(final ModelTemplateQuestionDetails modelTemplateQuestionDetails);

    @Query("DELETE FROM ModelTemplateQuestionDetails")
    void delete();

    @Query("DELETE FROM ModelTemplateQuestionDetails WHERE template_id = :template_id")
    void deleteTemplateId(String template_id);

    @Query("SELECT * FROM ModelTemplateQuestionDetails WHERE element_id = :elementId AND template_id = :templateId AND question_id = :questionId")
    Flowable<ModelTemplateQuestionDetails> findElementTemplateQuestionId(String elementId, String templateId, String questionId);

    @Query("SELECT * FROM ModelTemplateQuestionDetails WHERE element_id = :elementId AND template_id = :templateId AND question_id = :questionId")
    Flowable<List<ModelTemplateQuestionDetails>> findElementTemplateQuestionIdList(String elementId, String templateId, String questionId);
}
