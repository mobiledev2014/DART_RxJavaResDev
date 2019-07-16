package com.unilab.gmp.model;

import androidx.room.Entity;import lombok.Getter;
import lombok.Setter;

/**
 * Created by c_jhcanuto on 8/23/2017.
 */
@Setter
@Getter
@Entity
public class ModelReportQuestion  {
    String question_id;
    String answer_id = "0";
    String naoption_id = "";
    String category_id = "";
    String answer_details;
    String report_id;

    @Override
    public String toString() {
        return "ModelReportQuestion{" +
                "question_id='" + question_id + '\'' +
                ", answer_id='" + answer_id + '\'' +
                ", naoption_id='" + naoption_id + '\'' +
                ", category_id='" + category_id + '\'' +
                ", answer_details='" + answer_details + '\'' +
                ", report_id='" + report_id + '\'' +
                '}';
    }
}
