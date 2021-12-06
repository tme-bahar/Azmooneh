package ir.bahonar.azmooneh.DA;

import android.content.SharedPreferences;
import android.database.Cursor;

import java.util.List;

import ir.bahonar.azmooneh.DA.relatedObjects.ActivityHolder;
import ir.bahonar.azmooneh.DA.relatedObjects.Field;
import ir.bahonar.azmooneh.DA.relatedObjects.FieldMap;
import ir.bahonar.azmooneh.domain.Question;
import ir.bahonar.azmooneh.domain.User;
import ir.bahonar.azmooneh.domain.exam.Exam;

public class ExamDA {

    //field
    private final DataBase db;
    private final SharedPreferences sharedPref ;
    private final SharedPreferences.Editor editor;
    {
        db = new DataBase();
        sharedPref = ActivityHolder.activity.getPreferences(0);
        editor = sharedPref.edit();
    }

    public Exam add(Exam exam){
        Field teacher_id = new Field("teacher_id",exam.getTeacher().getId());
        Field name = new Field("name",exam.getName());
        Field is_multi_question = new Field("is_multi_question",exam.isMultiQuestion()?"1":"0");
        Field starting_time = new Field("starting_time",exam.getStartingTime());
        Field finishing_time = new Field("finishing_time",exam.getFinishingTime());
        Field max_grade = new Field("max_grade",String.valueOf(exam.getMaxGrade()));
        FieldMap hm = new FieldMap(teacher_id,name,is_multi_question,starting_time,finishing_time,max_grade);
        String id = db.insert("exams",hm);
        exam = new Exam(id,exam);
        for (User student:exam.getStudents()) {
            Field number = new Field("exam_id",exam.getId());
            Field text = new Field("user_id",student.getId());
            FieldMap hm1 = new FieldMap(number, text);
            String i = db.insert("exam_to_user",hm1);
        }
        return exam;
    }
    public void delete(String id){
        Field filter = new Field("id",id);
        db.delete("exams",filter);
    }
    public void delete(Exam exam){
        delete(exam.getId());
    }
    public Exam get(String id){
        Field filter = new Field("id",id);
        Cursor cursor = db.select("exams",null,filter,null);
        if(cursor == null)
            return null;
        cursor.moveToNext();
        String teacher_id = cursor.getString(cursor.getColumnIndexOrThrow("teacher_id"));
        String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
        String is_multi_question = cursor.getString(cursor.getColumnIndexOrThrow("is_multi_question"));
        String starting_time = cursor.getString(cursor.getColumnIndexOrThrow("starting_time"));
        String finishing_time = cursor.getString(cursor.getColumnIndexOrThrow("finishing_time"));
        String max_grade = cursor.getString(cursor.getColumnIndexOrThrow("max_grade"));
        return new Exam(id,(new UserDA()).get(teacher_id),is_multi_question.equals("1"),starting_time,finishing_time,Float.parseFloat(max_grade),name);
    }
    public Exam change(Exam exam){
        Field teacher_id = new Field("teacher_id",exam.getTeacher().getId());
        Field name = new Field("name",exam.getName());
        Field is_multi_question = new Field("is_multi_question",exam.isMultiQuestion()?"1":"0");
        Field max_grade = new Field("max_grade",String.valueOf(exam.getMaxGrade()));
        Field starting_time = new Field("starting_time",exam.getStartingTime());
        Field finishing_time = new Field("finishing_time",exam.getFinishingTime());
        FieldMap fm = new FieldMap(teacher_id,name,is_multi_question,max_grade,starting_time,finishing_time);
        Field filter = new Field("id",exam.getId());
        db.update("exams",filter,fm);
        return exam;
    }

}
