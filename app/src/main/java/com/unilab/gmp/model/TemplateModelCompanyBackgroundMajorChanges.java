 package com.unilab.gmp.model;

import androidx.room.Entity;

import lombok.Getter;
import lombok.Setter;

import com.google.gson.annotations.SerializedName;


/**
 * Created by c_jhcanuto on 8/24/2017.
 */

@Getter
@Setter
@Entity
public class TemplateModelCompanyBackgroundMajorChanges {
    String template_id;
    @SerializedName("changes")
    String majorchanges = "";

    String report_id;

    String company_id;
}
