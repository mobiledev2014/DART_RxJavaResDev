package com.unilab.gmp.model;

import com.orm.SugarRecord;

import androidx.room.Entity;import lombok.Getter;
import lombok.Setter;

/**
 * Created by c_jsbustamante on 08/11/2017.
 */

@Setter
@Getter
@Entity
public class TemplateModelOtherIssuesAudit  {
    String report_id;
    String other_issues_audit;
}
