package com.unilab.gmp.retrofit.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by c_jsbustamante on 1/13/2017.
 */

public class ResultAuth {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("dataresult")
    @Expose
    private ModelAuth dataresult;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ModelAuth getDataresult() {
        return dataresult;
    }

    public void setDataresult(ModelAuth dataresult) {
        this.dataresult = dataresult;
    }
}