package com.unilab.gmp.model;

import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by c_jhcanuto on 8/24/2017.
 */

@Getter
@Setter
public class TemplateModelReference extends SugarRecord {
    String template_id;
    @SerializedName("reference_name")
    String certification = "";
    @SerializedName("issuer")
    String body = "";
    @SerializedName("reference_no")
    String number = "";
    String validity = "";
    String issue_date = "";

    String report_id;

}
