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
public class TemplateModelCompanyBackgroundName extends SugarRecord {
    String template_id;
    @SerializedName("name")
    String bgname = "";

    String report_id;
}
