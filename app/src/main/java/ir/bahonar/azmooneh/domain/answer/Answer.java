package ir.bahonar.azmooneh.domain.answer;

import ir.bahonar.azmooneh.domain.exam.Exam;
import ir.bahonar.azmooneh.domain.Question;
import ir.bahonar.azmooneh.domain.User;

public abstract class Answer {

    //fields
    private final String id;
    private final Question question;
    private final Exam exam;
    private final User student;
    private float grade;
    public enum AnswerType{file,text,multiChoice}
    //constructor
    public Answer(String id, Question question, User student) {
        this.id = id;
        this.question = question;
        this.exam = question.getExam();
        this.student = student;
        this.grade = -1;
    }

    //getters

    public String getId() {
        return id;
    }

    public Question getQuestion() {
        return question;
    }

    public Exam getExam() {
        return exam;
    }

    public User getStudent() {
        return student;
    }

    public float getGrade() {
        return grade;
    }

    public boolean setGrade(float grade) {
        if (grade > question.getMaxGrade() || grade < 0)
            return false;
        this.grade = grade;
        return true;
    }

    public void removeGrade() {
        grade = -1;
    }

    public abstract AnswerType getType();
}
