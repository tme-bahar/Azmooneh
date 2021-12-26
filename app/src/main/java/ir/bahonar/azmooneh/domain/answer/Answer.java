package ir.bahonar.azmooneh.domain.answer;

import ir.bahonar.azmooneh.domain.exam.Exam;
import ir.bahonar.azmooneh.domain.Question;
import ir.bahonar.azmooneh.domain.User;

public class Answer {

    //fields
    private final String id;
    private final Question question;
    private final Exam exam;
    private final User student;
    private final AnswerExam answerExam;
    private float grade;
    private String answer;
    public enum AnswerType{file,text,multiChoice}
    //constructor
    public Answer(String id,String answer, Question question, User student,AnswerExam answerExam) {
        this.id = id;
        this.question = question;
        this.answerExam = answerExam;
        this.exam = question.getExam();
        this.student = student;
        this.grade = 0;
        this.answer = answer;
    }
    public Answer(String id,Answer answer) {
        this.id = id;
        this.question = answer.getQuestion();
        this.answerExam = answer.answerExam;
        this.exam = answer.getExam();
        this.student = answer.student;
        this.grade = answer.grade;
        this.answer = answer.answer;
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

    public AnswerExam getAnswerExam() {
        return answerExam;
    }

    public String getAnswer() {
        return answer;
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

    public  AnswerType getType(){
        return question.getChoices() == 0 ? AnswerType.file : (question.getChoices() == 1 ? AnswerType.text : AnswerType.multiChoice);
    }
}
