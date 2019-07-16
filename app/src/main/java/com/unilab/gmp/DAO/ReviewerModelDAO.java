package com.unilab.gmp.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.unilab.gmp.model.ReviewerModel;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

/**
 * Created by c_jhcanuto on 7/31/2017.
 */

@Dao
public interface ReviewerModelDAO {
    @Query("select * from ReviewerModel")
    Flowable<List<ReviewerModel>> getItemList();

    @Insert
    Completable insert(final ReviewerModel reviewerModel);

    @Query("DELETE FROM ReviewerModel")
    void delete();

    @Query("select * from ReviewerModel WHERE reviewer_id = :reviewerId")
    Flowable<List<ReviewerModel>> getByReviewerId(String reviewerId);

    @Query("UPDATE ReviewerModel SET reviewer_id = :reviewer_id, firstname = :firstname,middlename = :middlename," +
            "lastname = :lastname, designation = :designation, company = :company, department = :department," +
            "create_date = :createdate, update_date = :updatedate, email = :email, status = :status")
    Completable update(String reviewer_id, String firstname, String middlename, String lastname,
                String designation, String company, String department, String createdate,
                String updatedate, String email, String status);
}
