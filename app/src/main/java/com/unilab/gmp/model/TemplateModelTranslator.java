package com.unilab.gmp.model;

import com.orm.SugarRecord;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by c_jhcanuto on 8/24/2017.
 */
@Getter
@Setter
public class TemplateModelTranslator extends SugarRecord {
    String template_id;
    String translator = "";

    String report_id;
}
