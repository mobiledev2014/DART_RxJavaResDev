 package com.unilab.gmp.model;
import com.orm.SugarRecord;

import androidx.room.Entity;import lombok.Getter;
import lombok.Setter;

/**
 * Created by c_rcmiguel on 7/31/2017.
 */

@Setter
@Getter
@Entity
public class SupplierAndCompanyInformationModel  {
    String company_id, name, address1, address2, address3, address4, country, zip_code,
            background, create_date, update_date;
}
