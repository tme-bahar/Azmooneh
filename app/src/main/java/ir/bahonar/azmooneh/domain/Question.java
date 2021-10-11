package ir.bahonar.azmooneh.domain;

import android.graphics.Bitmap;

import ir.bahonar.azmooneh.domain.exam.Exam;

public class Question {

    //fields
    private final String id;
    private final int number;
    private final String text;
    private final String picture;
    private final Exam exam;
    private final float maxGrade;
    /**
     * if this is a question that its answer is text (typing), the 'choices' integer must be 1
     * if this is a question that its answer is a file, the 'choices' integer must be 0
     * if this is a multi choice question, the 'choices' integer must be number of choices**/
    private final int choices;
    private final String[] choiceText;
    //constructors
    public Question(String id,int number,String text,String picture,int choices,Exam exam,float maxGrade){
        this.id = id;
        this.number = number;
        this.picture = picture;
        this.text = text;
        this.exam = exam;
        this.maxGrade = maxGrade;
        this.choices = choices;
        this.choiceText = new String[choices];
    }

    //getters

    public String getId() {
        return id;
    }

    public String getPicture() {
        return picture;
    }

    public int getChoices() {
        return choices;
    }

    public int getNumber() {
        return number;
    }

    public String getText() {
        return text;
    }

    public Exam getExam() {
        return exam;
    }

    public float getMaxGrade() {
        return maxGrade;
    }

    public String getChoiceText(int index) {
        return choiceText[index];
    }
    public void setChoiceText(int index,String value) {
        choiceText[index]=value;
    }
    //is any problem ?
    public boolean isComplete(){
        return !id.isEmpty() && choices>=0;
    }

    public boolean equals(Question q){return q.getId().equals(id);}


}
