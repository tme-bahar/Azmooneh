package ir.bahonar.azmooneh.DA;

import android.content.SharedPreferences;
import android.database.Cursor;

import ir.bahonar.azmooneh.DA.relatedObjects.ActivityHolder;
import ir.bahonar.azmooneh.DA.relatedObjects.Field;
import ir.bahonar.azmooneh.DA.relatedObjects.FieldMap;
import ir.bahonar.azmooneh.domain.User;

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
        if(cursor == null)
            return null;
        cursor.moveToNext();
        if(!cursor.getString(cursor.getColumnIndexOrThrow("password")).equals(password))
            return null;
        id = cursor.getString(cursor.getColumnIndexOrThrow("id"));
        number = cursor.getString(cursor.getColumnIndexOrThrow("number"));
        firstName = cursor.getString(cursor.getColumnIndexOrThrow("firstName"));
        lastName = cursor.getString(cursor.getColumnIndexOrThrow("lastName"));
        type = cursor.getString(cursor.getColumnIndexOrThrow("type")).equals("student")? User.userType.STUDENT: User.userType.TEACHER;
        profile = cursor.getString(cursor.getColumnIndexOrThrow("profile"));
        return new User(id,number,firstName,lastName,username,password,type,profile);
    }

    public boolean isValidUser(String username){
        Field filter = new Field("username",username);
        Cursor cursor = db.select("users",null,filter,null);
        return cursor != null;
    }

    public boolean isSignedIn(){
        return sharedPref.getBoolean("isSignedIn",false);
    }

    public User signIn(){
        return isValidUser(sharedPref.getString("username",""),sharedPref.getString("password",""));
    }

    public void keepSignedIn(String username,String password){
        editor.putString("username",username);
        editor.putString("password",password);
        editor.putBoolean("isSignedIn",true);
        editor.apply();
    }

    public void singUp(User user){
        Field number = new Field("number",user.getNumber());
        Field firstName = new Field("first_name",user.getFirstName());
        Field lastName = new Field("last_name",user.getLastName());
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
}
