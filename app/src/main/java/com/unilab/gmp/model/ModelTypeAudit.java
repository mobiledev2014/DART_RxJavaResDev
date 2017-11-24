package com.unilab.gmp.model;

import com.orm.SugarRecord;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by c_jhcanuto on 8/18/2017.
 */
@Setter
@Getter
public class ModelTypeAudit extends SugarRecord{
    String scope_id ;
    String scope_name;
    String create_date;
    String update_date;
    String status;
    String selected = "0";
}
