package com.unilab.gmp.DAO;

import com.orm.SugarRecord;

import java.util.ArrayList;

import androidx.room.Entity;import lombok.Getter;
import lombok.Setter;

/**
 * Created by c_jhcanuto on 7/31/2017.
 */

@Getter
@Setter
@Entity
public class TemplateListModel  {
    ArrayList<TemplateDetailsModel> template_list;
}
