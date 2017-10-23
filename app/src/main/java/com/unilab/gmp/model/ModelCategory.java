package com.unilab.gmp.model;

import com.orm.SugarRecord;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by c_jhcanuto on 8/18/2017.
 */
@Setter
@Getter
public class ModelCategory extends SugarRecord{
    String category_id;
    String category_name;
    String create_date;
    String update_date;
}
