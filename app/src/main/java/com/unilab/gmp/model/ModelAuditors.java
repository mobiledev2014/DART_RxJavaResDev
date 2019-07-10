package com.unilab.gmp.model;

import androidx.room.Entity;

import androidx.room.Entity;import lombok.Getter;
import lombok.Setter;

/**
 * Created by c_jhcanuto on 8/18/2017.
 */
@Setter
@Getter
@Entity

//This model is just used for getting data from api
//AuditorsModel is used in the database
public class ModelAuditors{
    String auditor_id;
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

    /*
    * String reviewer;
    * String approver;
    * */
}
