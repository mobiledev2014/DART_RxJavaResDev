package com.unilab.gmp.DAO;

import androidx.room.Entity;

import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

import androidx.room.Entity;import lombok.Getter;
import lombok.Setter;

/**
 * Created by c_jhcanuto on 8/24/2017.
 */

@Getter
@Setter
@Entity
public class TemplateModelSummaryRecommendation  {
    String template_id;
    int selected;
    String element = "";
    String element_id = "";
    @SerializedName("recommendation")
    String remarks;

    String report_id;

    @Override
    public String toString() {
        return "TemplateModelSummaryRecommendation{" +
                "template_id='" + template_id + '\'' +
                ", selected=" + selected +
                ", element='" + element + '\'' +
                ", element_id='" + element_id + '\'' +
                ", scope_detail='" + remarks + '\'' +
                ", report_id='" + report_id + '\'' +
                '}';
    }
}
