package ir.bahonar.azmooneh.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Objects;

import ir.bahonar.azmooneh.DA.relatedObjects.ActivityHolder;
import ir.bahonar.azmooneh.R;
import ir.bahonar.azmooneh.domain.exam.Exam;

public class QuestionFileActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_page_type1);
        Objects.requireNonNull(getSupportActionBar()).hide();

        //initialize
        Button previous = findViewById(R.id.button10);
        Button next = findViewById(R.id.button8);
        TextView questionText = findViewById(R.id.textView26);
        TextView questionNum = findViewById(R.id.textView27);
        TextView questionGrade = findViewById(R.id.textView33);

        //put data
        Exam temp = ActivityHolder.exam;
        Intent i = getIntent();
        int num = i.getIntExtra("question",0);
        questionText.setText(temp.getQuestions().get(num).getText());
        questionNum.setText((num + 1)+" / "+ temp.getQuestions().size());
        questionGrade.setText(temp.getQuestions().get(num).getMaxGrade()+" / "+ temp.getMaxGrade());
        //previous.setVisibility(num == 0  ? View.GONE : View.VISIBLE);
        next.setText(getResources().getString(temp.getQuestions().size() == (num+1) ? R.string.Exit : R.string.next));

        View.OnClickListener Exit = v->startActivity(new Intent(this,MainPage.class));
        View.OnClickListener nextClick = v->{
            int t = ActivityHolder.exam.getQuestions().get(num+1).getChoices();
            Intent n = new Intent(this,t == 0?QuestionFileActivity.class:(t == 1 ?QuestionTypingActivity.class :
                    Question4ChoiceActivity.class));
            n.putExtra("question",num+1);
            startActivity(n);
            finish();};
        next.setOnClickListener(temp.getQuestions().size() == (num+1) ? Exit : nextClick);
    }

    @Override
    public void onBackPressed() {

    }
}