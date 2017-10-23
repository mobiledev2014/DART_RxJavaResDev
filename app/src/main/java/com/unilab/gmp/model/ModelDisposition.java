package com.unilab.gmp.model;

import com.orm.SugarRecord;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by c_rcmiguel on 8/24/2017.
 */
@Setter
@Getter
public class ModelDisposition extends SugarRecord {
    String disposition_id;
    String disposition_name;
    String create_date;
    String update_date;
    String report_id;
}
