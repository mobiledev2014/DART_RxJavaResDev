package com.unilab.gmp.model;

import com.orm.SugarRecord;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by c_jhcanuto on 7/31/2017.
 */

@Getter
@Setter
public class AuditorsModel extends SugarRecord {
    String auditor_id;
    String fname = "";
    String mname;
    String lname;
    String designation;
    String company;
    String department;
    String create_date;
    String update_date;
    String email = "";
    String actions;
    String status;
}
