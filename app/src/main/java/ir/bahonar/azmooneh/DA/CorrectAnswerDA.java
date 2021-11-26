package ir.bahonar.azmooneh.DA;

import android.content.SharedPreferences;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import ir.bahonar.azmooneh.DA.relatedObjects.ActivityHolder;
import ir.bahonar.azmooneh.DA.relatedObjects.Field;
import ir.bahonar.azmooneh.DA.relatedObjects.FieldMap;
import ir.bahonar.azmooneh.domain.Question;
import ir.bahonar.azmooneh.domain.answer.CorrectAnswer;
import ir.bahonar.azmooneh.domain.exam.CorrectExam;
import ir.bahonar.azmooneh.domain.exam.Exam;

public class CorrectAnswerDA {
    //field
    private final DataBase db;
    private final SharedPreferences sharedPref ;
    private final SharedPreferences.Editor editor;
    {
        db = new DataBase();
        sharedPref = ActivityHolder.activity.getPreferences(0);
        editor = sharedPref.edit();
    }
    public CorrectAnswer add(CorrectAnswer correctAnswer){
        Field question_id = new Field("question_id",correctAnswer.getQuestion().getId());
        Field answer = new Field("answer",String.valueOf(correctAnswer.getAnswer()));
        FieldMap hm = new FieldMap(question_id,answer);
        String id = db.insert("correct_answers",hm);
        correctAnswer = new CorrectAnswer(id,correctAnswer);
        return correctAnswer;
    }
    public CorrectAnswer get(String id){
        Field filter = new Field("id",id);
        Cursor cursor = db.select("correct_answers",null,filter,null);
        if(cursor == null)
            return null;
        cursor.moveToNext();
        String question_id = cursor.getString(cursor.getColumnIndexOrThrow("question_id"));
        String answer = cursor.getString(cursor.getColumnIndexOrThrow("answer"));
        // TODO : exam must be complete
        return new CorrectAnswer(id,new QuestionDA().get(question_id),Integer.parseInt(answer));
    }
}
