package com.unilab.gmp.model;

import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.List;

import androidx.room.Entity;import lombok.Getter;
import lombok.Setter;

/**
 * Created by c_jhcanuto on 8/11/2017.
 */

@Getter
@Setter
@Entity
public class ModelTemplateActivities  {

    @SerializedName("activity_id")
    String activityID;
    @SerializedName("activity_name")
    String activityName;

    String create_date;
    String update_date;

    @SerializedName("sub_activities")
    List<ModelTemplateSubActivities> modelTemplateSubActivities;

    boolean isCheck;

    String template_id;
}
