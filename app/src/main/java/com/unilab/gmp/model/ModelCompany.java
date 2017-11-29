package com.unilab.gmp.model;

import com.orm.SugarRecord;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by c_jhcanuto on 8/18/2017.
 */
@Setter
@Getter
public class ModelCompany extends SugarRecord {
    String company_id;


    String company_name;
    String address1;
    String address2;
    String address3;
    String country;
    String type;
    String background;
    String create_date;
    String update_date;
    String status;

    ArrayList<ModelSiteAuditHistory> audit_history;


    @Override
    public String toString() {
        return "ModelCompany{" +
                "company_id='" + company_id + '\'' +
                ", company_name='" + company_name + '\'' +
                ", address1='" + address1 + '\'' +
                ", address2='" + address2 + '\'' +
                ", address3='" + address3 + '\'' +
                ", country='" + country + '\'' +
                ", type='" + type + '\'' +
                ", background='" + background + '\'' +
                ", create_date='" + create_date + '\'' +
                ", update_date='" + update_date + '\'' +
                ", status='" + status + '\'' +
                ", audit_history=" + audit_history +
                '}';
    }
}
