package ir.bahonar.azmooneh.domain.answer;

import ir.bahonar.azmooneh.domain.Question;

public class CorrectAnswer {
    //fields
    private final String id;
    private final int answer;
    private final Question question;


    //constructors
    public CorrectAnswer(String id,Question question,int answer){
        this.id = id;
        this.question = question;
        this.answer = answer;
    }

    //getters

    public String getId() {
        return id;
    }

    public int getAnswer() {
        return answer;
    }

    public Question getQuestion() {
        return question;
    }
}
