package com.unilab.gmp.model;

import androidx.room.Entity;

import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by c_rcmiguel on 10/19/2017.
 */

@Setter
@Getter
@Entity
public class ModelSiteDate extends SugarRecord {
    String inspection_date;

    String company_id;
}
