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
public class TemplateModelScopeAuditInterest  {
    String template_id;
    int selected;
    String product_name = "";
    String product_id = "";
    String report_id;

    String disposition = "";
    String disposition_id = "";
    int selected2;
    String audit_id = "";
}
