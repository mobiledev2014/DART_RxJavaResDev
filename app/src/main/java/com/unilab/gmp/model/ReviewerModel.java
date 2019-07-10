package com.unilab.gmp.model;

import com.orm.SugarRecord;

import androidx.room.Entity;import lombok.Getter;
import lombok.Setter;

/**
 * Created by c_jhcanuto on 7/31/2017.
 */

@Getter
@Setter
@Entity
public class ReviewerModel  {
    String reviewer_id;
    String firstname;
    String middlename;
    String lastname;
    String designation;
    String company;
    String department;
    String create_date;
    String update_date;
    String email;
    String status;
}
