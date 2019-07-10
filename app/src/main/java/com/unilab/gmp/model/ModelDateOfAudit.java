package com.unilab.gmp.model;

import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

import androidx.room.Entity;import lombok.Getter;
import lombok.Setter;

/**
 * Created by c_rcmiguel on 9/29/2017.
 */

@Setter
@Getter
@Entity
public class ModelDateOfAudit  {
    @SerializedName("audit_date")
    String dateOfAudit = "";
    String report_id = "";

}
