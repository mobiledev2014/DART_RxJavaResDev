package com.unilab.gmp.model;

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
public class TemplateModelAuditors  {
    String template_id;
    int selected;
    String name = "";
    String position = "";
    String department = "";
    String auditor_id = "";
    String company= "";

    String report_id;
}
