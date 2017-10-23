package com.unilab.gmp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by c_jsbustamante on 7/29/2016.
 */
@Setter
@Getter
public class ModelUser extends SugarRecord{

    @SerializedName("emp_id")
    @Expose
    private int emp_id;
    private String email, password;

}
