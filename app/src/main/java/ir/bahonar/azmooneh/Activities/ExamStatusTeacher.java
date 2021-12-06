package ir.bahonar.azmooneh.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import ir.bahonar.azmooneh.DA.ExamDA;
import ir.bahonar.azmooneh.DA.QuestionDA;
import ir.bahonar.azmooneh.DA.relatedObjects.ActivityHolder;
import ir.bahonar.azmooneh.R;
import ir.bahonar.azmooneh.domain.exam.Exam;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class ExamStatusTeacher extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_info_page_for_tecaher);
        Objects.requireNonNull(getSupportActionBar()).hide();

        //initializing
        FloatingActionButton questionList = findViewById(R.id.floatingActionButton2);
        FloatingActionButton studentList = findViewById(R.id.floatingActionButton);
        EditText date = findViewById(R.id.editTextDate);
        EditText time = findViewById(R.id.editTextTime);
        RadioButton text = findViewById(R.id.radioButton6);
        RadioButton test = findViewById(R.id.radioButton7);
        EditText duration = findViewById(R.id.editTextNumber);
        Button save = findViewById(R.id.button7);

        if(ActivityHolder.exam==null || ActivityHolder.exam.getId() == null){
            ActivityHolder.exam = new Exam("*",ActivityHolder.user,false,"","",20f,"new Exam");
        }else{
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm'&'yyyy/MM/dd", Locale.ENGLISH);
            try {
                Date start = formatter.parse(ActivityHolder.exam.getStartingTime());
                Date finish = formatter.parse(ActivityHolder.exam.getFinishingTime());
                date.setText(start.getYear()+"/"+start.getMonth()+"/"+start.getDay());
                time.setText(start.getHours()+":"+start.getMinutes()+":"+start.getSeconds());
                long diffInMillies = start.getTime() - finish.getTime();
                Date dur=new Date(diffInMillies);
                duration.setText((dur.getTime()/60000)+" "+getResources().getString(R.string.minute));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            test.setChecked(ActivityHolder.exam.isMultiQuestion());
            text.setChecked(!ActivityHolder.exam.isMultiQuestion());
        }


        //on click listeners
        questionList.setOnClickListener(v->{startActivity(new Intent(this,QuestionList.class));});
        studentList.setOnClickListener(v->{startActivity(new Intent(this,StudentList.class));});
        save.setOnClickListener(v->{
            //TODO : TEMP! must some changes to new exam
            try {
                SimpleDateFormat formatter = new SimpleDateFormat("HH:mm'&'yyyy/MM/dd", Locale.ENGLISH);
                Date start = formatter.parse(ActivityHolder.exam.getStartingTime());
                long finishingMSeconds = start.getTime() + (Long.parseLong(duration.getText().toString())*60000);
                Date finishing = new Date(finishingMSeconds);
                Exam exam = new Exam("*", ActivityHolder.user, test.isChecked(), time.getText().toString() + "&" + date.getText().toString(),
                        formatter.format(finishing), 20, "exam");
                exam.addQuestions(ActivityHolder.exam.getQuestions());
                exam.addStudents(ActivityHolder.exam.getStudents());
                ExamDA eda = new ExamDA();
                QuestionDA qda = new QuestionDA();
                qda.add(exam.getQuestions());
                eda.add(exam);
                Toast.makeText(getApplicationContext(), R.string.Exam_added, Toast.LENGTH_LONG).show();
                startActivity(new Intent(this, MainPage.class));
                finish();
            }catch (Exception e){
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setTitle(getResources().getString(R.string.warning));
                alert.setMessage(getResources().getString(R.string.not_formatted));
                alert.setNegativeButton(getResources().getString(R.string.OK), (d, w) -> d.dismiss());
                alert.show();
            }
        });
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(this,MainPage.class));
        super.onBackPressed();
    }
}