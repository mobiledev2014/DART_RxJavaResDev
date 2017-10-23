package com.unilab.gmp.model;

import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by c_jhcanuto on 8/16/2017.
 */
@Setter
@Getter
public class ModelApproverInfo extends SugarRecord {

    @SerializedName("approver_info")
    ArrayList<ApproverModel> approverModels;
}
