package com.unilab.gmp.model;

import com.orm.SugarRecord;

import androidx.room.Entity;import lombok.Getter;
import lombok.Setter;

/**
 * Created by c_jhcanuto on 7/31/2017.
 */

@Getter
@Setter
@Entity
public class ApproverModel  {
    String approver_id;
    String firstname;
    String middlename;
    String lastname;
    String designation;
    String company;
    String department;
    String create_date;
    String update_date;
    String email;
    String status;

    @Override
    public String toString() {
        return "ApproverModel{" +
                "approver_id='" + approver_id + '\'' +
                ", firstname='" + firstname + '\'' +
                ", middlename='" + middlename + '\'' +
                ", lastname='" + lastname + '\'' +
                ", designation='" + designation + '\'' +
                ", company='" + company + '\'' +
                ", department='" + department + '\'' +
                ", create_date='" + create_date + '\'' +
                ", update_date='" + update_date + '\'' +
                ", email='" + email + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
