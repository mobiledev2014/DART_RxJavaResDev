package com.unilab.gmp.model;

import com.orm.SugarRecord;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by c_jhcanuto on 8/24/2017.
 */
@Getter
@Setter

public class TemplateModelPresentDuringMeeting  extends SugarRecord {
    String template_id;
    String name = "";
    String position = "";

    String report_id;

    @Override
    public String toString() {
        return "TemplateModelPresentDuringMeeting{" +
                "template_id='" + template_id + '\'' +
                ", name='" + name + '\'' +
                ", position='" + position + '\'' +
                ", report_id='" + report_id + '\'' +
                '}';
    }
}
