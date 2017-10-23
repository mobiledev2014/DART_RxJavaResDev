package com.unilab.gmp.model;

import com.orm.SugarRecord;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by c_rcmiguel on 8/24/2017.
 */

@Setter
@Getter

public class ModelDispositionInfo extends SugarRecord {

    ArrayList<ModelDisposition> disposition;
}
