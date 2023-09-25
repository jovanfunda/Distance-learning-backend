package com.learning.model.courses.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionDAO {

    public String question;
    public String rightAnswer;
    public String wrongAnswer1;
    public String wrongAnswer2;
    public String wrongAnswer3;
}
