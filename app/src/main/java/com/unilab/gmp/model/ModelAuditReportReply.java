package com.unilab.gmp.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by c_rcmiguel on 9/4/2017.
 */


@Setter
@Getter

public class ModelAuditReportReply {
    //        @SerializedName("status")
    String status;
    //    @SerializedName("report_id")
    String report_id;
    //    @SerializedName("report_no")
    String report_no;
    //    @SerializedName("message")
    String message;
    String report_status;
    String key;

    @Override
    public String toString() {
        return "ModelAuditReportReply{" +
                "status='" + status + '\'' +
                ", report_id='" + report_id + '\'' +
                ", report_no='" + report_no + '\'' +
                ", message='" + message + '\'' +
                ", report_status='" + report_status + '\'' +
//                ", key='" + key + '\'' +
                '}';
    }
}
