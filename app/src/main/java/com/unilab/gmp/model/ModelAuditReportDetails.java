package com.unilab.gmp.model;

import androidx.room.Entity;

import com.orm.SugarRecord;

import androidx.room.Entity;import lombok.Getter;
import lombok.Setter;

/**
 * Created by c_rcmiguel on 10/3/2017.
 */

@Getter
@Setter
@Entity
public class ModelAuditReportDetails  {
    String report_id;
    String report_no;
    String status;
    String modified_date;

    @Override
    public String toString() {
        return "ModelAuditReportDetails{" +
                "report_id='" + report_id + '\'' +
                ", report_no='" + report_no + '\'' +
                ", status='" + status + '\'' +
                ", modified_date='" + modified_date + '\'' +
                '}';
    }
}
