package ir.bahonar.azmooneh.DA;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ir.bahonar.azmooneh.DA.relatedObjects.ActivityHolder;
import ir.bahonar.azmooneh.DA.relatedObjects.Field;
import ir.bahonar.azmooneh.DA.relatedObjects.FieldMap;
import ir.bahonar.azmooneh.domain.Question;
import ir.bahonar.azmooneh.domain.User;
import ir.bahonar.azmooneh.domain.exam.Exam;

public class UserDA {

    //field
    private final DataBase db;
    private final SharedPreferences sharedPref ;
    private final SharedPreferences.Editor editor;
    {
        db = new DataBase();
        sharedPref = ActivityHolder.activity.getPreferences(0);
        editor = sharedPref.edit();
    }

    public User isValidUser(String username, String password){
        Field filter = new Field("username",username);
        String id ;
        String number ;
        String firstName ;
        String lastName ;
        User.userType type ;
        String profile ;
        Cursor cursor = db.select("users",null,filter,null);
        if(cursor == null )
            return null;
        if(cursor.getCount() < 1)
            return null;
        cursor.moveToNext();
        if(!cursor.getString(cursor.getColumnIndexOrThrow("password")).equals(password))
            return null;
        id = cursor.getString(cursor.getColumnIndexOrThrow("id"));
        number = cursor.getString(cursor.getColumnIndexOrThrow("number"));
        firstName = cursor.getString(cursor.getColumnIndexOrThrow("firstName"));
        lastName = cursor.getString(cursor.getColumnIndexOrThrow("lastName"));
        type = cursor.getString(cursor.getColumnIndexOrThrow("type")).equals(User.userType.STUDENT.toString())? User.userType.STUDENT: User.userType.TEACHER;
        profile = cursor.getString(cursor.getColumnIndexOrThrow("profile"));
        return new User(id,number,firstName,lastName,username,password,type,profile);
    }
    public User get(String id){
        Field filter = new Field("id",id);
        String number ;
        String firstName ;
        String lastName ;
        String username ;
        String password ;
        User.userType type ;
        String profile ;
        Cursor cursor = db.select("users",null,filter,null);
        if(cursor == null)
            return null;
        cursor.moveToNext();
        number = cursor.getString(cursor.getColumnIndexOrThrow("number"));
        username = cursor.getString(cursor.getColumnIndexOrThrow("username"));
        password = cursor.getString(cursor.getColumnIndexOrThrow("password"));
        firstName = cursor.getString(cursor.getColumnIndexOrThrow("firstName"));
        lastName = cursor.getString(cursor.getColumnIndexOrThrow("lastName"));
        type = cursor.getString(cursor.getColumnIndexOrThrow("type")).equals("student")? User.userType.STUDENT: User.userType.TEACHER;
        profile = cursor.getString(cursor.getColumnIndexOrThrow("profile"));
        return new User(id,number,firstName,lastName,username,password,type,profile);
    }
    public List<User> getAllStudents(){
        List<User> result = new ArrayList<>();
        Field filter = new Field("type",User.userType.STUDENT.toString());
        Cursor cursor = db.select("users",null,filter,null);
        if(cursor == null)
            return null;
        while (cursor.moveToNext()){
            String id = cursor.getString(cursor.getColumnIndexOrThrow("id"));
            String number = cursor.getString(cursor.getColumnIndexOrThrow("number"));
            String username = cursor.getString(cursor.getColumnIndexOrThrow("username"));
            String password = cursor.getString(cursor.getColumnIndexOrThrow("password"));
            String firstName = cursor.getString(cursor.getColumnIndexOrThrow("firstName"));
            String lastName = cursor.getString(cursor.getColumnIndexOrThrow("lastName"));
            User.userType type = cursor.getString(cursor.getColumnIndexOrThrow("type")).equals("student")? User.userType.STUDENT: User.userType.TEACHER;
            String profile = cursor.getString(cursor.getColumnIndexOrThrow("profile"));
            result.add(new User(id,number,firstName,lastName,username,password,type,profile));
        }
        return result;
    }

    public boolean isValidUser(String username){
        Field filter = new Field("username",username);
        Cursor cursor = db.select("users",null,filter,null);
        if(cursor == null)
            return false;
        return cursor.getCount() >= 1;
    }

    //is signed in
    public boolean isSignedIn(){
        return sharedPref.getBoolean("isSignedIn",false);
    }

    //is first in
    public boolean isFirstIn(){
        final boolean result = sharedPref.getBoolean("isFirstIn",true);
        if (result)
        {
            new DataBase().createTables();
            editor.putBoolean("isFirstIn",false);
            editor.apply();
        }
        return result;
    }

    public User signIn(String username,String password){
        return isValidUser(username,password);
    }

    public void keepSignedIn(String username,String password){
        editor.putString("username",username);
        editor.putString("password",password);
        editor.putBoolean("isSignedIn",true);
        editor.apply();
    }
    public String getUserName(){
        return sharedPref.getString("username","");
    }
    public String getPassword(){
        return sharedPref.getString("password","");
    }

    public void singUp(User user){
        Field number = new Field("number",user.getNumber());
        Field firstName = new Field("firstName",user.getFirstName());
        Field lastName = new Field("lastName",user.getLastName());
        Field username = new Field("username",user.getUsername());
        Field password = new Field("password",user.getPassword());
        Field type = new Field("type",user.getType().toString());
        Field profile = new Field("profile",user.getProfile());
        FieldMap fm = new FieldMap(number,firstName,lastName,username,
                password,type,profile);
        db.insert("users",fm);
    }

    public void singOut(){
        editor.putString("username","");
        editor.putString("password","");
        editor.putBoolean("isSignedIn",false);
        editor.apply();
    }

    //get exams
    public List<Exam> getExam(String id){
        Log.e("getExam",id);
        Field filter = new Field("user_id",id);
        List<Exam> result = new ArrayList<>();
        List<String> projection = new ArrayList<>();
        projection.add("exam_id");
        Cursor cursor =db.select("exam_to_user",projection,null,null);
        Log.e("cursor",String.valueOf(cursor.getCount()));
        if(cursor == null)
            return result;
        if(cursor.getCount() < 1)
            return result;
        List<String> ids = new ArrayList<>();
        while (cursor.moveToNext()){
            String newId = cursor.getString(cursor.getColumnIndexOrThrow("exam_id"));
            ids.add(newId);
        }
        ExamDA eda = new ExamDA();
        for (String i:ids)
            result.add(eda.get(i));
        return result;
    }
    public List<Exam> getExamTeacher(String teacher_id){
        Field filter = new Field("teacher_id",teacher_id);
        List<Exam> result = new ArrayList<>();
        Cursor cursor =db.select("exams",null,filter,null);
        if(cursor == null)
            return result;
        if(cursor.getCount() < 1)
            return result;
        while (cursor.moveToNext()){
            String id = cursor.getString(cursor.getColumnIndexOrThrow("id"));
            String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            String is_multi_question = cursor.getString(cursor.getColumnIndexOrThrow("is_multi_question"));
            String starting_time = cursor.getString(cursor.getColumnIndexOrThrow("starting_time"));
            String finishing_time = cursor.getString(cursor.getColumnIndexOrThrow("finishing_time"));
            String max_grade = cursor.getString(cursor.getColumnIndexOrThrow("max_grade"));
            Exam exam = new Exam(id,(new UserDA()).get(teacher_id),is_multi_question.equals("1"),starting_time,finishing_time,Float.parseFloat(max_grade),name);
            result.add(exam);
        }
        return result;
    }

    //change
    public void change(User user){
        Field number = new Field("number",user.getNumber());
        Field firstName = new Field("firstName",user.getFirstName());
        Field lastName = new Field("lastName",user.getLastName());
        Field username = new Field("username",user.getUsername());
        Field password = new Field("password",user.getPassword());
        Field type = new Field("type",user.getType().toString());
        Field profile = new Field("profile",user.getProfile());
        FieldMap fm = new FieldMap(number,firstName,lastName,username,
                password,type,profile);
        Field filter = new Field("id",user.getId());
        db.update("users",filter,fm);
    }
}
