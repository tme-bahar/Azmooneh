package ir.bahonar.azmooneh.domain.answer;

import ir.bahonar.azmooneh.domain.exam.Exam;
import ir.bahonar.azmooneh.domain.Question;
import ir.bahonar.azmooneh.domain.User;

public class TextAnswer extends Answer{

    //field
    private String answer;

    public TextAnswer(String id, Question question, User student, String answer,AnswerExam answerExam) {
        super(id, question, student,answerExam);
        this.answer = answer;
    }

    //getter
    public String getAnswer() {
        return answer;
    }

    @Override
    public AnswerType getType() {
        return AnswerType.text;
    }
}
