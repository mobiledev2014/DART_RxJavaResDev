package com.unilab.gmp.model;

import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by c_rcmiguel on 10/3/2017.
 */

@Getter
@Setter
public class ModelAuditReportsList extends SugarRecord {
    @SerializedName("audit_report")
    List<ModelAuditReportDetails> audit_report_list;
}
