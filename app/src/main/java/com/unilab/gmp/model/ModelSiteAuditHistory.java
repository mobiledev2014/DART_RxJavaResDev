 package com.unilab.gmp.model;
import androidx.room.Entity;

import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

import java.util.ArrayList;

import androidx.room.Entity;import lombok.Getter;
import lombok.Setter;

/**
 * Created by c_jhcanuto on 10/19/2017.
 */

@Setter
@Getter
@Entity
public class ModelSiteAuditHistory  {
    ArrayList<TemplateModelCompanyBackgroundMajorChanges> major_changes;
    @SerializedName("audit_dates")
    ArrayList<ModelSiteDate> modelSiteDates;
    ArrayList<TemplateModelCompanyBackgroundName> inspectors;

    String company_id;
}
