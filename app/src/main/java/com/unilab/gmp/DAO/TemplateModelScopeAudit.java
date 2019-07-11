package com.unilab.gmp.DAO;

import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;
import com.unilab.gmp.adapter.templates.AdapterScopeAuditInterest;
import com.unilab.gmp.model.TemplateModelScopeAuditInterest;

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
public class TemplateModelScopeAudit  {
    String audit_id = "";
    String scope_id = "";
    @SerializedName("scope_detail")
    String scope_detail = "";
    String scope_name = "";
    String template_id;
    int selected;

    String report_id = "";
    @SerializedName("adapterScope")
    AdapterScopeAuditInterest adapterScope;
    @SerializedName("scope_product")
    List<TemplateModelScopeAuditInterest> templateModelScopeAuditInterests = new ArrayList<>();

}
