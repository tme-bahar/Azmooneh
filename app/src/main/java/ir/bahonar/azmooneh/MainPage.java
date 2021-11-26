package ir.bahonar.azmooneh;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import ir.bahonar.azmooneh.DA.UserDA;
import ir.bahonar.azmooneh.DA.relatedObjects.ActivityHolder;
import ir.bahonar.azmooneh.domain.Question;
import ir.bahonar.azmooneh.domain.User;
import ir.bahonar.azmooneh.domain.exam.Exam;
import ir.bahonar.azmooneh.DA.relatedObjects.TimeHandler;

public class MainPage extends AppCompatActivity {

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
        TextView finishedActivities = findViewById(R.id.textView10);
        TextView notFinishedActivities = findViewById(R.id.textView9);
        RecyclerView rv = findViewById(R.id.list_item);
        FloatingActionButton fbt = findViewById(R.id.floatingActionButton);

        //putting date
        List<Exam> exams = new UserDA().getExam(ActivityHolder.user.getId());
        Exam exam = new Exam("id",new User("0","153","ali","hassani","","", User.userType.TEACHER,""),true,"14:30","15:30",2,"testing exam");
        Question question1 = new Question("1",1,"2 × 2 = ?","",4,exam,1);
        Question question2 = new Question("2",2,"4 × 2 = ?","",1,exam,1);
        exam.addQuestion(question1,question2);
        exams.add(exam);
        name.setText(user.getFirstName()+" "+user.getLastName());
        type.setText(getResources().getString(user.getType()== User.userType.TEACHER ? R.string.teacher : R.string.student));
        fbt.setVisibility(user.getType()== User.userType.TEACHER ? View.VISIBLE : View.GONE);

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
        if (exam != null){
            rv.setLayoutManager(new LinearLayoutManager(this));
            Adapter a = new Adapter(exams);
            a.setOnItemClickListener((exam1, position) -> {
                ActivityHolder.exam = exam1;
                startActivity(new Intent(this,FirstExamStatus.class));
            });
            rv.setAdapter(a);

        }
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
        System.exit(0);
    }
}