package ir.bahonar.azmooneh.domain.answer;

import ir.bahonar.azmooneh.domain.exam.Exam;
import ir.bahonar.azmooneh.domain.Question;
import ir.bahonar.azmooneh.domain.User;

public class MultiChoiceAnswer extends Answer{

    //fields
    private final int answer;

    //constructor
    public MultiChoiceAnswer(String id, Question question, User student,int answer,AnswerExam answerExam) {
        super(id, question, student,answerExam);
        if(answer >1)
            this.answer = answer;
        else
            this.answer = -1;
    }

    //getter
    public int getAnswer() {
        return answer;
    }

    @Override
    public AnswerType getType() {
        return AnswerType.multiChoice;
    }
}
