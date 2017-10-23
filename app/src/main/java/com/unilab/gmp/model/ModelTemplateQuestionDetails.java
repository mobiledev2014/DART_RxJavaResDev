package com.unilab.gmp.model;

import com.orm.SugarRecord;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by c_rcmiguel on 8/22/2017.
 */

@Setter
@Getter
public class ModelTemplateQuestionDetails extends SugarRecord{
    String question_id;
    String question;
    String default_yes;
    String required_remarks;
    String create_date;
    String update_date;
    String element_id;
    String template_id;

    String answer_id = "";
    String naoption_id = "";
    String category_id = "";
    String answer_details = "";

}
