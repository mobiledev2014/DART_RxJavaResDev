package com.unilab.gmp.model;

import androidx.room.Entity;

import androidx.room.Entity;import lombok.Getter;
import lombok.Setter;

/**
 * Created by c_jhcanuto on 8/18/2017.
 */
@Setter
@Getter
@Entity
public class ModelReference {
    String standard_id;
    String standard_name;
    String classification_id;
    String create_date;
    String update_date;
}
