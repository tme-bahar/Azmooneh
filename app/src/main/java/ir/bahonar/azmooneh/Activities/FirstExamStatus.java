package ir.bahonar.azmooneh.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import ir.bahonar.azmooneh.DA.relatedObjects.ActivityHolder;
import ir.bahonar.azmooneh.R;
import ir.bahonar.azmooneh.domain.Question;
import ir.bahonar.azmooneh.domain.exam.Exam;

public class FirstExamStatus extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_info_page);
        Objects.requireNonNull(getSupportActionBar()).hide();
        //initialize
        Button start = findViewById(R.id.button7);
        CheckBox cb = findViewById(R.id.checkBox);
        View hider = findViewById(R.id.view);
        TextView date = findViewById(R.id.textView6);
        TextView time = findViewById(R.id.textView13);
        TextView duration = findViewById(R.id.textView12);
        TextView type = findViewById(R.id.textView9);
        TextView questions = findViewById(R.id.textView8);

        //date
        Exam temp = ActivityHolder.exam;

        for (Question q:temp.getQuestions()
        ) {
            Log.e(q.getId(),q.getChoices()+"");
        }

        Date startDate;
        Date finishDate;
        String durationMin = "";
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm'&'yyyy/MM/dd", Locale.ENGLISH);
        try {
            startDate = formatter.parse(temp.getStartingTime());
            finishDate = formatter.parse(temp.getFinishingTime());
            assert startDate != null;
            assert finishDate != null;
            durationMin = String.valueOf((finishDate.getTime() - startDate.getTime())/60000);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //putting data
        date.setText(temp.getStartingTime().split("&")[1]);
        time.setText(temp.getStartingTime().split("&")[0]);
        duration.setText(durationMin + "  " +getResources().getString(R.string.minute));
        type.setText(getResources().getString(temp.isMultiQuestion() ? R.string._4_choice : R.string.text));
        questions.setText(temp.getQuestions().size() + " " + getResources().getString(R.string.questions));
        cb.setVisibility(ActivityHolder.exam.getStatus() == Exam.Status.running ? View.VISIBLE : View.GONE);
        start.setClickable(false);

        cb.setOnCheckedChangeListener((b,c)->{
            hider.setVisibility(c ? View.GONE : View.VISIBLE);
            start.setClickable(c);
            start.setAlpha(c?1f:0.5f);
            if(c)
            start.setOnClickListener(v->{
                int t = ActivityHolder.exam.getQuestions().get(0).getChoices();
                Intent i = new Intent(this,t == 0?QuestionFileActivity.class:(t == 1 ?QuestionTypingActivity.class :
                        Question4ChoiceActivity.class));
                i.putExtra("question",0);
                startActivity(i);
                finish();});
        });

    }
}