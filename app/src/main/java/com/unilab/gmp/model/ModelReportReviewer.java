package com.unilab.gmp.model;

import com.orm.SugarRecord;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by c_jhcanuto on 8/23/2017.
 */

@Setter
@Getter
public class ModelReportReviewer extends SugarRecord {
    String reviewer_id;
    String report_id;
}
