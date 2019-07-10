package com.unilab.gmp.model;

import com.orm.SugarRecord;

import androidx.room.Entity;import lombok.Getter;
import lombok.Setter;

/**
 * Created by c_jhcanuto on 8/18/2017.
 */
@Getter
@Setter
@Entity
public class ModelProduct {
    String product_id;
    String company_id;
    String product_name;
    String type;
    String create_date;
    String update_date;
    String status;
}
