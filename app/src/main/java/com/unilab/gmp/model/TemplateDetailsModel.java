package com.unilab.gmp.model;

import com.orm.SugarRecord;

import androidx.room.Entity;import lombok.Getter;
import lombok.Setter;

/**
 * Created by c_jhcanuto on 7/31/2017.
 */

@Getter
@Setter
@Entity
public class TemplateDetailsModel  {
    String template_id;
    String standard_id;
    String classification_id;
    String status;
    String modified_date;
}
