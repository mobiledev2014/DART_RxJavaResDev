package com.unilab.gmp.model;

import com.orm.SugarRecord;

import androidx.room.Entity;import lombok.Getter;
import lombok.Setter;

/**
 * Created by c_jhcanuto on 8/18/2017.
 */
@Setter
@Getter
@Entity
public class ModelTypeAudit {
    String scope_id ;
    String scope_name;
    String create_date;
    String update_date;
    String status;
    String selected = "0";

    @Override
    public String toString() {
        return "ModelTypeAudit{" +
                "scope_id='" + scope_id + '\'' +
                ", scope_name='" + scope_name + '\'' +
                ", create_date='" + create_date + '\'' +
                ", update_date='" + update_date + '\'' +
                ", status='" + status + '\'' +
                ", selected='" + selected + '\'' +
                '}';
    }
}
