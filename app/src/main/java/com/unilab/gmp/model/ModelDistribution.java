package com.unilab.gmp.model;

import com.orm.SugarRecord;

import androidx.room.Entity;import lombok.Getter;
import lombok.Setter;

/**
 * Created by c_jhcanuto on 9/15/2017.
 */

@Setter
@Getter
@Entity
public class ModelDistribution  {
    String distribution_id	;
    String distribution_name;
    String create_date;
    String update_date;
    String status;
}
