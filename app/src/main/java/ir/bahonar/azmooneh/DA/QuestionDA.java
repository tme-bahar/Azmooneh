package ir.bahonar.azmooneh.DA;

import android.content.SharedPreferences;

import ir.bahonar.azmooneh.DA.relatedObjects.ActivityHolder;
import ir.bahonar.azmooneh.DA.relatedObjects.Field;
import ir.bahonar.azmooneh.domain.Question;

public class QuestionDA {
    //field
    private final DataBase db;
    private final SharedPreferences sharedPref ;
    private final SharedPreferences.Editor editor;
    {
        db = new DataBase();
        sharedPref = ActivityHolder.activity.getPreferences(0);
        editor = sharedPref.edit();
    }

    public void addQuestion(Question question){
        Field number = new Field("number",String.valueOf(question.getNumber()));
        Field text = new Field("text",question.getText());
        Field picture = new Field("picture",question.getPicture());
        Field exam = new Field("exam_id",question.getExam().getId());
        Field maxGrade = new Field("max_grade",String.valueOf(question.getMaxGrade()));
        Field choices = new Field("choices",String.valueOf(question.getChoices()));
    }
}
