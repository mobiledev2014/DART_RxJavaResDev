 package com.unilab.gmp.model;
import androidx.room.Entity;

import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

import androidx.room.Entity;import lombok.Getter;
import lombok.Setter;

/**
 * Created by c_jhcanuto on 8/24/2017.
 */

@Getter
@Setter
@Entity
public class TemplateModelCompanyBackgroundName  {
    String template_id;
    @SerializedName("inspector")
    String bgname = "";

    String report_id;
    String company_id;
}
