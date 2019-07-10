 package com.unilab.gmp.model;
import androidx.room.Entity;

import com.orm.SugarRecord;

import androidx.room.Entity;

import lombok.Getter;
import lombok.Setter;

 /**
 * Created by c_jhcanuto on 8/24/2017.
 */
@Getter
@Setter
@Entity
public class TemplateModelPresentDuringMeeting   {
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
