package ir.bahonar.azmooneh.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import ir.bahonar.azmooneh.Adapter;
import ir.bahonar.azmooneh.DA.ExamDA;
import ir.bahonar.azmooneh.DA.UserDA;
import ir.bahonar.azmooneh.DA.relatedObjects.ActivityHolder;
import ir.bahonar.azmooneh.R;
import ir.bahonar.azmooneh.domain.Question;
import ir.bahonar.azmooneh.domain.User;
import ir.bahonar.azmooneh.domain.exam.Exam;
import ir.bahonar.azmooneh.DA.relatedObjects.TimeHandler;

public class MainPage extends AppCompatActivity {
    Adapter a;
    TextView finishedActivities;
    TextView notFinishedActivities;
    RecyclerView rv;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        Objects.requireNonNull(getSupportActionBar()).hide();

        //initializing
        User user = ActivityHolder.user;
        ImageView prof = findViewById(R.id.imageView4);
        TextView name = findViewById(R.id.textView13);
        TextView type = findViewById(R.id.textView8);
        finishedActivities = findViewById(R.id.textView10);
        notFinishedActivities = findViewById(R.id.textView9);
        rv = findViewById(R.id.list_item);
        FloatingActionButton fbt = findViewById(R.id.floatingActionButton);
        FloatingActionButton LogOut = findViewById(R.id.floatingActionButton4);

        //putting date
        getExams();
        /*Exam exam = new Exam("id",new User("0","153","ali","hassani","","", User.userType.TEACHER,""),true,"14:30","15:30",2,"testing exam");
        Question question1 = new Question("1",1,"2 × 2 = ?","",4,exam,1);
        Question question2 = new Question("2",2,"4 × 2 = ?","",1,exam,1);
        exam.addQuestion(question1,question2);
        exams.add(exam);*/
        name.setText(user.getFirstName()+" "+user.getLastName());
        type.setText(getResources().getString(user.getType()== User.userType.TEACHER ? R.string.teacher : R.string.student));
        fbt.setVisibility(user.getType()== User.userType.TEACHER ? View.VISIBLE : View.GONE);
        try {
            prof.setImageURI(Uri.parse(user.getProfile()));
        }finally {}


        //new Exam
        fbt.setOnClickListener(v->{
            ActivityHolder.exam = null;
            startActivity(new Intent(this,ExamStatusTeacher.class));
        finish();});

        //log out
        LogOut.setOnClickListener(v->{
            (new UserDA()).singOut();
            startActivity(new Intent(this,MainActivity.class));
            finish();
        });

        //float
        prof.setOnClickListener(v->{
            ActivityHolder.exam = new Exam(null,null,false,null,null,0f,null);
            startActivity(new Intent(this,ProfileActivity.class));});
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
        System.exit(0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getExams();
    }

    private void getExams(){
        List<Exam> temp = ActivityHolder.user.getType()== User.userType.TEACHER ?new UserDA().getExamTeacher(ActivityHolder.user.getId()):new UserDA().getExam(ActivityHolder.user.getId());
        List<Exam> exams =new ArrayList<>();
        for (Exam exam:temp)
            if(exam != null)
                exams.add(exam);


        int NotFinishedActivities = 0;

        for (Exam e:exams) {

            try {
                if(Calendar.getInstance().before(TimeHandler.parse(e.getStartingTime())))
                    NotFinishedActivities++;
            } catch (Exception exception) {
                exception.printStackTrace();
            }

        }
        //TODO : TEMP
        finishedActivities.setText("0");
        notFinishedActivities.setText("1");
        if (exams.size() == 0){
            rv.setVisibility(View.GONE);
        }else{
            rv.setVisibility(View.VISIBLE);
            rv.setLayoutManager(new LinearLayoutManager(this));
            a = new Adapter(exams);
            a.setOnItemClickListener((exam1, position) -> {
                ActivityHolder.exam = exam1;
                startActivity(new Intent(this,FirstExamStatus.class));
            });

            if(ActivityHolder.user.getType() == User.userType.TEACHER)
                a.setOnItemLongClickListener((exam, position) -> {
                    AlertDialog.Builder alert = new AlertDialog.Builder(this);
                    alert.setTitle(getResources().getString(R.string.are_you_sure));
                    alert.setMessage(getResources().getString(R.string.are_you_sure_exam));
                    alert.setPositiveButton(getResources().getString(R.string.yes), (dialog, which) -> {
                        new ExamDA().delete(exam);
                        getExams();
                        Toast.makeText(getApplicationContext(), "question Removed", Toast.LENGTH_LONG);
                        dialog.dismiss();
                    });
                    alert.setNegativeButton(getResources().getString(R.string.No), (d, w) -> d.dismiss());
                    alert.show();
                });
            rv.setAdapter(a);
        }
    }
}