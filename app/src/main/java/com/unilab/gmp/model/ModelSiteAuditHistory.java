package com.unilab.gmp.model;

import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by c_jhcanuto on 10/19/2017.
 */

@Setter
@Getter

public class ModelSiteAuditHistory extends SugarRecord {
    ArrayList<ModelSiteMajorChanges> major_changes;
    @SerializedName("audit_dates")
    ArrayList<ModelSiteDate> modelSiteDates;
    ArrayList<ModelSiteInspectors> inspectors;

    String company_id;
}
