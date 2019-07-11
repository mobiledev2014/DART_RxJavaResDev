package com.unilab.gmp.DAO;

import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

import androidx.room.Entity;import lombok.Getter;
import lombok.Setter;

/**
 * Created by c_rcmiguel on 9/19/2017.
 */


@Setter
@Getter
@Entity
public class TemplateModelDistributionOthers  {
    @SerializedName("others")
    String distribution_other;

    String report_id;
    String template_id;

    @Override
    public String toString() {
        return "TemplateModelDistributionOthers{" +
                "distribution_other='" + distribution_other + '\'' +
                ", report_id='" + report_id + '\'' +
                ", template_id='" + template_id + '\'' +
                '}';
    }
}
