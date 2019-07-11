package com.unilab.gmp.DAO;

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
public class TemplateModelPersonelMetDuring  {
    String template_id;
    String name = "";
    @SerializedName("designation")
    String position = "";

    String report_id;

    @Override
    public String toString() {
        return "TemplateModelPersonelMetDuring{" +
                "template_id='" + template_id + '\'' +
                ", name='" + name + '\'' +
                ", position='" + position + '\'' +
                ", report_id='" + report_id + '\'' +
                '}';
    }
}
