package com.unilab.gmp.model;

import com.orm.SugarRecord;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by c_jhcanuto on 7/31/2017.
 */

@Getter
@Setter
public class TemplateListModel extends SugarRecord {
    ArrayList<TemplateDetailsModel> template_list;
}
