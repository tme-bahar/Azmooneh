package ir.bahonar.azmooneh.domain;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.List;

import ir.bahonar.azmooneh.DA.UserDA;
import ir.bahonar.azmooneh.domain.exam.Exam;

public class User implements Serializable{

    //fields
    private final String id;
    private final String number;
    private final String firstName;
    private final String lastName;
    private final String username;
    private final String password;
    private final userType type;
    private final String profile;
    public enum userType{TEACHER,STUDENT}

    //constructors
    public User(String id,String number,String firstName,String lastName,String username,String password,userType type,String profile){
        this.id = id;
        this.number = number;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.type = type;
        this.profile = profile;
    }

    //getters
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getNumber() {
        return number;
    }

    public userType getType() {
        return type;
    }

    public String getProfile() {
        return profile;
    }

    //checking problems
    public boolean isComplete(){
        return !id.isEmpty() && !firstName.isEmpty() && !lastName.isEmpty() && !username.isEmpty() && !password.isEmpty() && type != null;
    }

    //get exams
    public List<Exam> getExams(){
        UserDA uda = new UserDA();
        return uda.getExam(id);
    }
}
