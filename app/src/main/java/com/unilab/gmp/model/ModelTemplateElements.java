package com.unilab.gmp.model;

import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by c_rcmiguel on 8/22/2017.
 */

@Setter
@Getter
public class ModelTemplateElements extends SugarRecord {

    String template_id;
    String element_id;
    String element_name;
    String create_date;
    String update_date;
    @SerializedName("questions")
    List<ModelTemplateQuestionDetails> modelTemplateQuestionDetails;
}
