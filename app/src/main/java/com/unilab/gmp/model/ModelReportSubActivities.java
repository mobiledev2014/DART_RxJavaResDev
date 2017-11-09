package com.unilab.gmp.model;

import com.orm.SugarRecord;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by c_jhcanuto on 8/23/2017.
 */

@Setter
@Getter
public class ModelReportSubActivities extends SugarRecord {
    String report_id;
    String sub_item_id;
    String activity_id;
    boolean isCheck;

    @Override
    public String toString() {
        return "ModelReportSubActivities{" +
                "report_id='" + report_id + '\'' +
                ", sub_item_id='" + sub_item_id + '\'' +
                ", activity_id='" + activity_id + '\'' +
                ", isCheck=" + isCheck +
                '}';
    }
}
