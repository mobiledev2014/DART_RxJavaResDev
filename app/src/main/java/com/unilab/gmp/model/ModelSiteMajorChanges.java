package com.unilab.gmp.model;

import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by c_jhcanuto on 25/10/2017.
 */

@Setter
@Getter
public class ModelSiteMajorChanges extends SugarRecord {
    @SerializedName("changes")
    String major_change;
    String company_id;
}
