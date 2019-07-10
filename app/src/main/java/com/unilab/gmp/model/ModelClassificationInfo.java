package com.unilab.gmp.model;

import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

import java.util.ArrayList;

import androidx.room.Entity;import lombok.Getter;
import lombok.Setter;

/**
 * Created by c_jhcanuto on 8/18/2017.
 */
@Setter
@Getter
@Entity
public class ModelClassificationInfo  {
    @SerializedName("classification_list")
    ArrayList<ModelClassification> modelClassifications ;
}
