package com.unilab.gmp.model;

import com.orm.SugarRecord;

import java.util.List;

import androidx.room.Entity;import lombok.Getter;
import lombok.Setter;

/**
 * Created by c_jhcanuto on 8/18/2017.
 */
@Setter
@Getter
@Entity
public class ModelClassification {
    String classification_id;
    String classification_name;
    String create_date;
    String update_date;
    String status;

    List<ModelClassificationCategory> category;

    @Override
    public String toString() {
        return "ModelClassification{" +
                "classification_id='" + classification_id + '\'' +
                ", classification_name='" + classification_name + '\'' +
                ", create_date='" + create_date + '\'' +
                ", update_date='" + update_date + '\'' +
                ", status='" + status + '\'' +
                ", category=" + category +
                '}';
    }

}
