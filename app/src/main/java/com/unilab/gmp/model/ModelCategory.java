package com.unilab.gmp.model;

import com.orm.SugarRecord;

import androidx.room.Entity;import lombok.Getter;
import lombok.Setter;

/**
 * Created by c_jhcanuto on 8/18/2017.
 */
@Setter
@Getter
@Entity
public class ModelCategory {
    String category_id;
    String category_name;
    String create_date;
    String update_date;
    String status;
}
