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
}
