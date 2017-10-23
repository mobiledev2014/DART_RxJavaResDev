package com.unilab.gmp.model;

import com.orm.SugarRecord;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by c_jhcanuto on 8/24/2017.
 */

@Getter
@Setter
public class TemplateModelScopeAuditInterest extends SugarRecord {
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
