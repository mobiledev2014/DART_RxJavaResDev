package com.unilab.gmp.model;

import com.orm.SugarRecord;

import java.util.List;

import androidx.room.Entity;import lombok.Getter;
import lombok.Setter;

/**
 * Created by c_jhcanuto on 8/23/2017.
 */

@Setter
@Getter
@Entity
public class ModelReportDisposition  {
    String disposition_id;
    List<ModelReportDispositionScopeProduct> scope_product;
}
