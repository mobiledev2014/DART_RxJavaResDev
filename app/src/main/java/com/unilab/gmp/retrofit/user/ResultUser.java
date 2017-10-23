package com.unilab.gmp.retrofit.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.unilab.gmp.model.ModelUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by c_jsbustamante on 7/29/2016.
 */
public class ResultUser {
    @SerializedName("result")
    @Expose
    private List<ModelUser> result = new ArrayList<ModelUser>();

    /**
     * @return The result
     */
    public List<ModelUser> getResult() {
        return result;
    }

    /**
     * @param result The result
     */
    public void setResult(List<ModelUser> result) {
        this.result = result;
    }
}
