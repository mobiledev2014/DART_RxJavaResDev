package com.unilab.gmp.model;

import androidx.room.Entity;

import com.orm.SugarRecord;

import androidx.room.Entity;import lombok.Getter;
import lombok.Setter;

/**
 * Created by c_jhcanuto on 8/24/2017.
 */
@Getter
@Setter
@Entity
public class TemplateModelTranslator  {
    String template_id;
    String translator = "";

    String report_id;
}
