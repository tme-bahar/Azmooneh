package ir.bahonar.azmooneh.DA;

import android.content.SharedPreferences;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import ir.bahonar.azmooneh.DA.relatedObjects.ActivityHolder;
import ir.bahonar.azmooneh.DA.relatedObjects.Field;
import ir.bahonar.azmooneh.DA.relatedObjects.FieldMap;
import ir.bahonar.azmooneh.domain.Question;
import ir.bahonar.azmooneh.domain.User;
import ir.bahonar.azmooneh.domain.answer.Answer;

public class AnswerDA {

    //field
    private final DataBase db;
    private final SharedPreferences sharedPref ;
    private final SharedPreferences.Editor editor;
    {
        db = new DataBase();
        sharedPref = ActivityHolder.activity.getPreferences(0);
        editor = sharedPref.edit();
    }

    public Answer add(Answer answer){
        Field answerText = new Field("answer",answer.getAnswer());
        Field exam = new Field("exam_id",answer.getExam().getId());
        Field user = new Field("user_id",answer.getStudent().getId());
        Field question = new Field("question_id",answer.getQuestion().getId());
        Field grade = new Field("grade",String.valueOf(answer.getGrade()));
        FieldMap hm = new FieldMap(answerText,exam,user,question,grade);
        String id = db.insert("answers",hm);
        Answer ans = new Answer(id,answer);
        return ans;
    }

    public List<Answer> get(User student, Question question){
        Field filter = new Field("user_id",student.getId());
        Cursor cursor = db.select("answers",null,filter,null);
        List<Answer> result = new ArrayList<>();
        if(cursor == null)
            return result;
        if(cursor.getCount() == 0)
            return result;
        while (cursor.moveToNext()){
            String question_id = cursor.getString(cursor.getColumnIndexOrThrow("question_id"));
            if(!question_id.equals(question.getId()))
                continue;
            String id = cursor.getString(cursor.getColumnIndexOrThrow("id"));
            String answer = cursor.getString(cursor.getColumnIndexOrThrow("answer"));
            String grade = cursor.getString(cursor.getColumnIndexOrThrow("grade"));
            Answer ans = new Answer(id,answer,question,student,null);
            ans.setGrade(Float.parseFloat(grade));
            result.add(ans);
        }
        return result;
    }

    //change
    public void change(Answer answer){
        Field ans = new Field("answer", answer.getAnswer());
        Field exam = new Field("exam_id", answer.getExam().getId());
        Field user = new Field("user_id", answer.getStudent().getId());
        Field question = new Field("question_id", answer.getQuestion().getId());
        Field grade = new Field("grade", String.valueOf(answer.getGrade()));
        FieldMap hm = new FieldMap(ans,exam,user,question,grade);
        Field filter = new Field("id",answer.getId());
        db.update("answers",filter,hm);
    }
}
