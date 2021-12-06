package ir.bahonar.azmooneh.DA;

import android.content.SharedPreferences;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ir.bahonar.azmooneh.DA.relatedObjects.ActivityHolder;
import ir.bahonar.azmooneh.DA.relatedObjects.Field;
import ir.bahonar.azmooneh.DA.relatedObjects.FieldMap;
import ir.bahonar.azmooneh.domain.Question;
import ir.bahonar.azmooneh.domain.exam.Exam;

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

    public Question add(Question question){
        Field number = new Field("number",String.valueOf(question.getNumber()));
        Field text = new Field("text",question.getText());
        Field picture = new Field("picture",question.getPicture());
        Field exam = new Field("exam_id",question.getExam().getId());
        Field maxGrade = new Field("max_grade",String.valueOf(question.getMaxGrade()));
        Field choices = new Field("choices",String.valueOf(question.getChoices()));
        FieldMap hm = new FieldMap(number,text,picture,exam,maxGrade,choices);
        String id = db.insert("questions",hm);
        question = new Question(id,question);
        return question;
    }
    public void add(List<Question> questions){
        for (Question q:questions)
            add(q);

    }
    public Question get(String id){
        Field filter = new Field("id",id);
        Cursor cursor = db.select("questions",null,filter,null);
        if(cursor == null)
            return null;
        cursor.moveToNext();
        String number = cursor.getString(cursor.getColumnIndexOrThrow("number"));
        String text = cursor.getString(cursor.getColumnIndexOrThrow("text"));
        String picture = cursor.getString(cursor.getColumnIndexOrThrow("picture"));
        String exam_id = cursor.getString(cursor.getColumnIndexOrThrow("exam_id"));
        String max_grade = cursor.getString(cursor.getColumnIndexOrThrow("max_grade"));
        String choices = cursor.getString(cursor.getColumnIndexOrThrow("choices"));
        // TODO : exam must be complete
        return new Question(id,Integer.parseInt(number),text,picture,Integer.parseInt(choices),(new ExamDA()).get(exam_id),Float.parseFloat(max_grade));
    }
    public List<Question> get(Exam exam){
        Field filter = new Field("exam_id",exam.getId());
        Cursor cursor = db.select("questions",null,filter,"number ASC");
        if(cursor == null)
            return null;
        List<Question> result = new ArrayList<>();
        while (cursor.moveToNext()){
            String id = cursor.getString(cursor.getColumnIndexOrThrow("id"));
            String number = cursor.getString(cursor.getColumnIndexOrThrow("number"));
            String text = cursor.getString(cursor.getColumnIndexOrThrow("text"));
            String picture = cursor.getString(cursor.getColumnIndexOrThrow("picture"));
            String max_grade = cursor.getString(cursor.getColumnIndexOrThrow("max_grade"));
            String choices = cursor.getString(cursor.getColumnIndexOrThrow("choices"));
            result.add(new Question(id,Integer.parseInt(number),text,picture,Integer.parseInt(choices),exam,Float.parseFloat(max_grade)));
        }
        return result;
    }
    public void setQuestions(Exam exam){exam.addQuestions(get(exam));}
    public void change(Question question){
        Field number = new Field("number",String.valueOf(question.getNumber()));
        Field text = new Field("text",String.valueOf(question.getText()));
        Field picture = new Field("picture",String.valueOf(question.getPicture()));
        Field examId = new Field("exam_id",String.valueOf(question.getExam().getId()));
        Field max_grade = new Field("max_grade",String.valueOf(question.getMaxGrade()));
        Field choices = new Field("choices",String.valueOf(question.getChoices()));
        FieldMap fm = new FieldMap(number,text,picture,max_grade,choices,examId);
        Field filter = new Field("id",question.getId());
        db.update("questions",filter,fm);
    }
}
