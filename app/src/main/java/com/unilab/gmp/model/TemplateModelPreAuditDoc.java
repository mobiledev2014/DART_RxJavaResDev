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
public class TemplateModelPreAuditDoc extends SugarRecord {
    String template_id;
    @SerializedName("document_name")
    String preaudit = "";

    String report_id;
}
