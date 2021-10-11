package ir.bahonar.azmooneh.domain.answer;

import java.util.ArrayList;
import java.util.List;

import ir.bahonar.azmooneh.domain.exam.Exam;

public class Answers {
    //field
    public List<AnswerExam> answers;
    private final Exam exam;

    //constructor
    public Answers(Exam exam){
        answers = new ArrayList<>();
        this.exam = exam;
    }

    //getters

    public Exam getExam() {
        return exam;
    }

    public List<AnswerExam> getAnswers() {
        return answers;
    }


    //adder
    public boolean add(AnswerExam ae){
        if (ae.getExam().equals(exam))
            answers.add(ae);
        return ae.getExam().equals(exam);
    }

    //average
    public float getAverage(){
        float sum = 0f;
        int num = 0;
        for (AnswerExam ae:answers) {
            sum += ae.getGrade();
            num++;
        }
        return sum/num;
    }

    //min grade
    public float getMinGrade(){
        float min = 0;
        for (AnswerExam ae:answers)
            min = Math.min(min,ae.getGrade());
        return min;
    }

    //max grade
    public float getMaxGrade(){
        float max = 0;
        for (AnswerExam ae:answers)
            max = Math.max(max,ae.getGrade());
        return max;
    }

    //number of under
    public int numberOfUnder(float num) {
        int result = 0;
        for (AnswerExam ae : answers)
            if (num > ae.getGrade())
                result++;
        return result;
    }
}
