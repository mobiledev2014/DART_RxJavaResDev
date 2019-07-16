package com.unilab.gmp.model;

import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.List;

import androidx.room.Entity;import lombok.Getter;
import lombok.Setter;

/**
 * Created by c_jhcanuto on 8/23/2017.
 */

@Setter
@Getter
@Entity
public class TemplateModelScopeAuditCopy  {
    String scope_id = "";
    String scope_detail;
    @SerializedName("scope_product")
    List<TemplateModelScopeAuditInterest> templateModelScopeAuditInterests = new ArrayList<>();
    String scope_name = "";
    String template_id;
    int selected;
    String report_id = "";
//    AdapterScopeAuditInterest adapterScope;

}
