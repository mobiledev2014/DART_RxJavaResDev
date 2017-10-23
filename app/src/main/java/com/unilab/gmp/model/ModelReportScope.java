package com.unilab.gmp.model;

import com.orm.SugarRecord;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by c_jhcanuto on 8/23/2017.
 */
@Setter
@Getter
public class ModelReportScope extends SugarRecord {
    String scope_id = "";
    String scope_detail;
    List<ModelReportScopeProduct> scope_product;

    String remarks;
    String scope_name = "";
    String template_id;
    String disposition = "";
    int selected;
    int selected2;
    String report_id;
}
