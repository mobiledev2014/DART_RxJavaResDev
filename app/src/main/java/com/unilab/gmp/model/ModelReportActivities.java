package com.unilab.gmp.model;

import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

import java.util.List;

import androidx.room.Entity;import lombok.Getter;
import lombok.Setter;

/**
 * Created by c_jhcanuto on 8/23/2017.
 */
@Setter
@Getter
@Entity
public class ModelReportActivities  {
    String report_id;
    String activity_id;
    @SerializedName("scope_product")
    List<ModelReportSubActivities> sub_activities;
    boolean isCheck;

    @Override
    public String toString() {
        return "ModelReportActivities{" +
                "report_id='" + report_id + '\'' +
                ", activity_id='" + activity_id + '\'' +
                ", sub_activities=" + sub_activities +
                ", isCheck=" + isCheck +
                '}';
    }
}
