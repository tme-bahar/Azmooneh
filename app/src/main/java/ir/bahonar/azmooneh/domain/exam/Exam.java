package ir.bahonar.azmooneh.domain.exam;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicReference;

import ir.bahonar.azmooneh.domain.Question;
import ir.bahonar.azmooneh.domain.User;
import ir.bahonar.azmooneh.domain.answer.Answers;

public class Exam {

    //fields
    private final String id;
    private final User teacher;
    private final String name;
    private  List<User> students;
    private  List<Question> questions;
    private final boolean isMultiQuestion;
    private final String startingTime;
    private final String finishingTime;
    private final float maxGrade;
    private  Answers studentAnswers;
    public enum Status {notStarted, running , finished}

    //constructor
    public Exam(String id,User teacher,boolean isMultiQuestion,String startingTime,String finishingTime,float maxGrade,String name){
        this.id = id;
        this.teacher = teacher;
        this.isMultiQuestion = isMultiQuestion;
        this.startingTime = startingTime;
        this.finishingTime = finishingTime;
        this.maxGrade = maxGrade;
        this.name = name;
        students = new ArrayList<>();
        questions = new ArrayList<>();
        studentAnswers = new Answers(this);
    }
    public Exam(String id,Exam exam){
        this.id = id;
        this.teacher = exam.teacher;
        this.name = exam.name;
        this.students = exam.students;
        this.questions = exam.questions;
        this.isMultiQuestion = exam.isMultiQuestion;
        this.startingTime = exam.startingTime;
        this.finishingTime = exam.finishingTime;
        this.maxGrade = exam.maxGrade;
        this.studentAnswers = exam.studentAnswers;
        students = new ArrayList<>();
        questions = new ArrayList<>();
        studentAnswers = new Answers(this);
        this.studentAnswers = exam.getStudentAnswers();
        this.students.addAll(exam.getStudents());
        this.questions.addAll(exam.getQuestions());
    }
    //getters

    public String getId() {
        return id;
    }

    public boolean isMultiQuestion() {
        return isMultiQuestion;
    }

    public User getTeacher() {
        return teacher;
    }

    public List<User> getStudents() {
        return students;
    }

    public String getStartingTime() {
        return startingTime;
    }

    public String getFinishingTime() {
        return finishingTime;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public Answers getStudentAnswers() {
        return studentAnswers;
    }

    public String getName() {
        return name;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public float getMaxGrade() {
        float max = 0f;
        for (Question q:questions) max+=q.getMaxGrade();
        return max;
    }

    public User getStudentById(String id) {
        for (User u : students)
            if (u.getId().equals(id))
                return u;
        return null;
    }

    public User getStudentAt(int index){
        return students.get(index);
    }

    public Question getQuestionById(String id) {
        for (Question q : questions)
            if (q.getId().equals(id))
                return q;
        return null;
    }

    public Question getQuestionAt(int index){
        return questions.get(index);
    }

    //adders
    private boolean addStudent(User student) {
        if (student.getType() != User.userType.STUDENT && !student.isComplete())
            return false;
        students.add(student);
        return true;
    }

    public boolean addStudents(User... students) {
        boolean result = true;
        for (User u : students)
            result &= addStudent(u);
        return result;
    }
    public boolean addStudents(List<User> users) {
        boolean result = true;
        for (User u : users)
            result &= addStudent(u);
        Log.e("result",String.valueOf(result));
        return result;
    }

    private boolean addQuestion(Question question) {
        if (!question.isComplete())
            return false;
        if (isMultiQuestion && question.getChoices() < 2)
            return false;
        questions.add(question);
        return true;
    }

    public boolean addQuestion(Question... questions) {
        boolean result = true;
        for (Question q : questions)
            result &= addQuestion(q);
        return result;
    }

    public boolean addQuestions(List<Question> questions){
        boolean result = true;
        for (Question q : questions)
            result &= addQuestion(q);
        return result;
    }
    public boolean isFull() {
        int sum = 0;
        for (Question q : questions)
            if (q.getMaxGrade() < 0)
                return false;
            else
                sum += q.getMaxGrade();
        return sum == maxGrade;
    }
    public boolean equals(Exam exam){
        return exam.getId().equals(id);
    }

    //get status
    public Status getStatus(){
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm'&'yyyy/MM/dd", Locale.ENGLISH);
        try {
            Date start = formatter.parse(startingTime);
            Date finish = formatter.parse(finishingTime);
            Date now = new Date();
            if(start.getTime() > now.getTime())
                return Status.notStarted;
            if(finish.getTime() > now.getTime())
                return Status.running;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return Status.finished;
    }
}
