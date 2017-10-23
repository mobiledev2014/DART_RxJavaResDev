package com.unilab.gmp.model;

import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by c_jhcanuto on 8/11/2017.
 */
@Setter
@Getter
public class ModelTemplateSubActivities extends SugarRecord {
    @SerializedName("sub_item_id")
    String subItemID;

    @SerializedName("sub_item_name")
    String subItemName;
    String create_date;
    String update_date;
    String activity_id;
    String template_id;
    boolean isCheck;
}
