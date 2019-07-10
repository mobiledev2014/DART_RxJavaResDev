package com.unilab.gmp.model;

import androidx.room.Entity;import lombok.Getter;
import lombok.Setter;

/**
 * Created by c_rcmiguel on 2/20/2017.
 */

@Setter
@Getter
@Entity
public class QuestionModel {
    String elementNumber, questionNumber, question, answer, remarks;
}
