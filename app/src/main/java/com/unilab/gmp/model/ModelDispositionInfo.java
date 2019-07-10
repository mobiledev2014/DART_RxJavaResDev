package com.unilab.gmp.model;

import com.orm.SugarRecord;

import java.util.ArrayList;

import androidx.room.Entity;import lombok.Getter;
import lombok.Setter;

/**
 * Created by c_rcmiguel on 8/24/2017.
 */

@Setter
@Getter
@Entity

public class ModelDispositionInfo  {

    ArrayList<ModelDisposition> disposition;
}
