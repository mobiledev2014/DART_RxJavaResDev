package com.unilab.gmp.model;

import com.orm.SugarRecord;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by c_jsbustamante on 10/11/2017.
 */

@Setter
@Getter
public class ModelClassificationCategory extends SugarRecord {
    String category_id;
    String classification_id;
    String classification_name;

    @Override
    public String toString() {
        return "ModelClassificationCategory{" +
                "category_id='" + category_id + '\'' +
                ", classification_id='" + classification_id + '\'' +
                ", classification_name='" + classification_name + '\'' +
                '}';
    }
}
