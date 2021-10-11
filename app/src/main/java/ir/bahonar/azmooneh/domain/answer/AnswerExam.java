package ir.bahonar.azmooneh.domain.answer;

import java.util.ArrayList;
import java.util.List;

import ir.bahonar.azmooneh.domain.User;
import ir.bahonar.azmooneh.domain.exam.Exam;

public class AnswerExam {
    //fields
    private final String id;
    private final Exam exam;
    private final List<Answer> answers;
    private final User student;
    private float grade;
    //constructor
    public AnswerExam(String id,Exam exam,User student){
        this.id = id;
        this.exam = exam;
        this.student = student;
        answers = new ArrayList<>();
        grade = 0;
    }

    //getters
    public String getId() {
        return id;
    }

    public Exam getExam() {
        return exam;
    }

    public User getStudent() {
        return student;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public float getGrade() {
        return grade;
    }

    //adder
    public void addAnswer(Answer answer){
        answers.add(answer);
    }

    //finder
    public Answer find(String id) {
        for (Answer a : answers)
            if (a.getId().equals(id))
                return a;
        return null;
    }
    public float check(){
        for (Answer a:answers)
            grade += a.getGrade();
        return grade;
    }
}
