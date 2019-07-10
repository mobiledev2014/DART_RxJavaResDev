package com.unilab.gmp.model;

import androidx.room.Entity;import lombok.Getter;
import lombok.Setter;

/**
 * Created by c_jhcanuto on 8/23/2017.
 */

@Setter
@Getter
@Entity
public class ModelReportPersonnel {
    String personnel_met_id;
    String fname;
    String mname;
    String lname;
    String designation;
}
