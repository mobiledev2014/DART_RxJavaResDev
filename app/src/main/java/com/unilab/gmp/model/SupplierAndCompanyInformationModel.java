package com.unilab.gmp.model;

import com.orm.SugarRecord;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by c_rcmiguel on 7/31/2017.
 */

@Setter
@Getter
public class SupplierAndCompanyInformationModel extends SugarRecord {
    String company_id, name, address1, address2, address3, address4, country, zip_code,
            background, create_date, update_date;
}
