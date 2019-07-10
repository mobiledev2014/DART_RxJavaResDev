package com.unilab.gmp.model;

import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

import java.util.List;

import androidx.room.Entity;import lombok.Getter;
import lombok.Setter;

/**
 * Created by c_rcmiguel on 10/3/2017.
 */

@Getter
@Setter
@Entity
public class ModelAuditReportsList  {
    @SerializedName("audit_report")
    List<ModelAuditReportDetails> audit_report_list;
}
