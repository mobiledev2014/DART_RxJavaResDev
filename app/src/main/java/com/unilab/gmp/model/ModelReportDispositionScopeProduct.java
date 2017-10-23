package com.unilab.gmp.model;

import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by c_jhcanuto on 8/23/2017.
 */

@Setter
@Getter
public class ModelReportDispositionScopeProduct extends SugarRecord {
    @SerializedName("product_id")
    String scope_id;
    String disposition_id;
    @SerializedName("scope_detail")
    String disposition_description;
    String report_id;
}
