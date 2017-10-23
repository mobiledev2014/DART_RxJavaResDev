package com.unilab.gmp.model;

import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by c_rcmiguel on 9/29/2017.
 */

@Setter
@Getter
public class ModelDateOfAudit extends SugarRecord {
    @SerializedName("audit_date")
    String dateOfAudit = "";
    String report_id = "";

}
