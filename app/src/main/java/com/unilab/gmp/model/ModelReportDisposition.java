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
public class ModelReportDisposition extends SugarRecord {
    String disposition_id;
    List<ModelReportDispositionScopeProduct> scope_product;
}
