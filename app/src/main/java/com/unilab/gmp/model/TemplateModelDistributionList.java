package com.unilab.gmp.model;

import com.orm.SugarRecord;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by c_jhcanuto on 8/24/2017.
 */
@Getter
@Setter

public class TemplateModelDistributionList extends SugarRecord {
    String template_id;
    String distribution = "";
    String distribution_id = "";
    int selected;

    String report_id;

    @Override
    public String toString() {
        return "TemplateModelDistributionList{" +
                "template_id='" + template_id + '\'' +
                ", distribution='" + distribution + '\'' +
                ", distribution_id='" + distribution_id + '\'' +
                ", selected=" + selected +
                ", report_id='" + report_id + '\'' +
                '}';
    }
}
