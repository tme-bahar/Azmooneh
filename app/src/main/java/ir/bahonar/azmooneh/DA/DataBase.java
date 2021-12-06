package ir.bahonar.azmooneh.DA;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import ir.bahonar.azmooneh.DA.relatedObjects.ActivityHolder;
import ir.bahonar.azmooneh.DA.relatedObjects.Field;
import ir.bahonar.azmooneh.DA.relatedObjects.FieldMap;

public class DataBase {

    //base obj
    private final SQLiteDatabase myDataBase;
    SharedPreferences sh;

    //constructor
    public DataBase(){
        myDataBase = ActivityHolder.activity.openOrCreateDatabase("database.db", Context.MODE_PRIVATE,null);
        sh = ActivityHolder.activity.getPreferences(0);
    }

    //is signed in
    public boolean isSignedIn(){
        return sh.getBoolean("isSignedIn",false);
    }
    public boolean isFirstIn(){
        return sh.getBoolean("isFirstIn",false);
    }

    //create all tables
    public void createTables(){
        //Users
        {
            Field number = new Field("number", "VARCHAR(20)");
            Field username = new Field("username", "VARCHAR(20)");
            Field password = new Field("password", "VARCHAR(20)");
            Field firstName = new Field("firstName", "VARCHAR(20)");
            Field lastName = new Field("lastName", "VARCHAR(20)");
            Field type = new Field("type", "VARCHAR(8)");
            Field profile = new Field("profile", "TEXT");
            FieldMap hm = new FieldMap(number, username, password, firstName, lastName, type,profile);
            createTable("users", hm, true);
        }

        //Questions
        {
            Field number = new Field("number", "VARCHAR(5)");
            Field text = new Field("text", "TEXT");
            Field picture = new Field("picture", "TEXT");
            Field exam = new Field("exam_id", "VARCHAR(5)");
            Field maxGrade = new Field("max_grade", "VARCHAR(10)");
            Field choices = new Field("choices", "VARCHAR(2)");
            FieldMap hm = new FieldMap(number, text, picture, exam, maxGrade, choices);
            createTable("questions", hm, true);
        }

        //exam to user
        {
            Field number = new Field("exam_id", "VARCHAR(5)");
            Field text = new Field("user_id", "VARCHAR(5)");
            FieldMap hm = new FieldMap(number, text);
            createTable("exam_to_user", hm, true);
        }

        //exam
        {
            Field number = new Field("teacher_id", "VARCHAR(5)");
            Field is_multi_question = new Field("is_multi_question", "VARCHAR(1)");
            Field text = new Field("name", "TEXT");
            Field maxGrade = new Field("max_grade", "VARCHAR(5)");
            Field starting_time = new Field("starting_time", "VARCHAR(10)");
            Field finishing_time = new Field("finishing_time", "VARCHAR(10)");
            Field teacher_id = new Field("teacher_id", "VARCHAR(5)");
            FieldMap hm = new FieldMap(number, text,maxGrade,starting_time,finishing_time,teacher_id,is_multi_question);
            createTable("exams", hm, true);
        }
    }

    //create
    public void createTable(String tableName, FieldMap columns) {
        createTable(tableName,columns,true);
    }
    public void createTable(String tableName, FieldMap columns,boolean isIdUnique) {
        StringBuilder sb = new StringBuilder("CREATE TABLE IF NOT EXISTS ");
        sb.append(tableName).append(" ( ");
        if(isIdUnique)
            sb.append(" id INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE,");
        else
            sb.append(" id INTEGER,");
        Set<String> keys = columns.keySet();
        for (String key:keys)
            sb.append(key).append(" ").append(columns.get(key)).append(",");
        sb.deleteCharAt(sb.length()-1);
        sb.append(")");
        myDataBase.execSQL(sb.toString());
    }

    //insert
    public String insert(String tableName,FieldMap data){
        StringBuilder sb = new StringBuilder("INSERT INTO " + tableName+ " ( ");
        Set<String> keys = data.keySet();
        for (String key:keys){
            sb.append(key).append(", ");
        }
        sb.deleteCharAt(sb.length()-2);
        sb.append(" ) VALUES ( ");
        Collection<String> values = data.values();
        for (String val:values){
            sb.append("'").append(val).append("', ");
        }
        sb.deleteCharAt(sb.length()-2);
        sb.append(")");
        myDataBase.execSQL(sb.toString());
        @SuppressLint("Recycle")
        Cursor c = myDataBase.rawQuery("select last_insert_rowid()",null);
        c.moveToNext();
        return c.getString(c.getColumnIndexOrThrow("last_insert_rowid()"));
    }

    //update
    public void update(String tableName, Field filter, Field data){

        String sb = "UPDATE " + tableName + " SET " + data.getKey() + " = '" + data.getValue() + "'" +
                " WHERE " + filter.getKey() + " = '" + filter.getValue() + "'";
        myDataBase.execSQL(sb);
    }
    public void update(String tableName, Field filter, FieldMap data){
        if (data.isEmpty())
            return;
        StringBuilder sb = new StringBuilder("UPDATE " + tableName + " SET ");
        for (Field f:data.getAll())
            sb.append(f.getKey()).append(" = '").append(f.getValue()).append("',");
        sb.replace(sb.length()-1,sb.length(),"");
        sb.append(" WHERE ").append(filter.getKey()).append(" = '").append(filter.getValue()).append("'");
        myDataBase.execSQL(sb.toString());
    }

    //select
    public Cursor select(String tableName, List<String> projection, Field filter, String order, String limit){
        StringBuilder sb = new StringBuilder();
        if (projection!=null){
            for (String a :
                    projection) {
                sb.append(a).append(", ");
            }
            sb.deleteCharAt(sb.length()-2);
        }
        else {
            sb.append("*");
        }
        sb = new StringBuilder("SELECT " + sb.toString() + " FROM " + tableName);
        if (filter != null)
            sb.append(" WHERE ").append(filter.getKey()).append(" LIKE '").append(filter.getValue()).append("' ");

        if (order!=null)
            sb.append(" ORDER BY ").append(order);

        if (limit!=null)
            sb.append(" LIMIT ").append(limit);

        Cursor cursor;
        try {
            cursor = myDataBase.rawQuery(sb.toString(),null);
        }catch (Exception e){
            Log.e("Error",e.getMessage());
            return null;
        }
        return cursor ;
    }
    public Cursor select(String tableName, List<String> projection, Field filter, String order) {
        return select(tableName,projection,filter,order,null);
    }

    public String select(String tableName, String projection, Field filter){
        List<String> projs = new ArrayList<>();
        projs.add(projection);
        Cursor cursor = select(tableName,projs,filter,projection+" ASC","1");
        String result = "";
        if (cursor != null)
            while (cursor.moveToNext())
                result=cursor.getString(cursor.getColumnIndexOrThrow(projection));
        return result ;
    }

    //delete
    public void delete(String tableName,Field filter){

        myDataBase.execSQL("DELETE FROM " + tableName + " WHERE " + filter.getKey() + "= '" + filter.getValue() + "'");
    }
}
