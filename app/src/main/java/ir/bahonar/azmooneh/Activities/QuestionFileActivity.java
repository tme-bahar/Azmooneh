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

import ir.bahonar.azmooneh.DA.AnswerDA;
import ir.bahonar.azmooneh.DA.relatedObjects.ActivityHolder;
import ir.bahonar.azmooneh.R;
import ir.bahonar.azmooneh.domain.Question;
import ir.bahonar.azmooneh.domain.answer.Answer;
import ir.bahonar.azmooneh.domain.exam.Exam;

public class QuestionFileActivity extends AppCompatActivity {
    String path = "";
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
        previous.setVisibility(num == 0  ? View.GONE : View.VISIBLE);
        next.setText(getResources().getString(temp.getQuestions().size() == (num+1) ? R.string.Exit : R.string.next));

        View.OnClickListener Exit = v->{
            save(String.valueOf(path),temp.getQuestions().get(num));
            startActivity(new Intent(this,MainPage.class));
            finish();};
        View.OnClickListener nextClick = v->{
            int t = ActivityHolder.exam.getQuestions().get(num+1).getChoices();
            Intent n = new Intent(this,t == 0?QuestionFileActivity.class:(t == 1 ?QuestionTypingActivity.class :
                    Question4ChoiceActivity.class));
            n.putExtra("question",num+1);
            save(String.valueOf(path),temp.getQuestions().get(num));
            startActivity(n);
            finish();};
        View.OnClickListener back = v->{
            int t = ActivityHolder.exam.getQuestions().get(num-1).getChoices();
            Intent n = new Intent(this,t == 0?QuestionFileActivity.class:(t == 1 ?QuestionTypingActivity.class :
                    Question4ChoiceActivity.class));
            n.putExtra("question",num-1);
            save(String.valueOf(path),temp.getQuestions().get(num));
            startActivity(n);
            finish();};
        next.setOnClickListener(temp.getQuestions().size() == (num+1) ? Exit : nextClick);
        previous.setOnClickListener(back);
    }

    @Override
    public void onBackPressed() {

    }
    private void save(String text, Question question){
        Answer answer = new Answer("*",text,question,ActivityHolder.user,null);
        AnswerDA ada = new AnswerDA();
        ada.add(answer);
    }
}