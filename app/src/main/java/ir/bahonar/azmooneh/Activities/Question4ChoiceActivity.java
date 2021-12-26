package ir.bahonar.azmooneh.Activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.Objects;

import ir.bahonar.azmooneh.DA.AnswerDA;
import ir.bahonar.azmooneh.DA.relatedObjects.ActivityHolder;
import ir.bahonar.azmooneh.R;
import ir.bahonar.azmooneh.domain.Question;
import ir.bahonar.azmooneh.domain.answer.Answer;
import ir.bahonar.azmooneh.domain.exam.Exam;

public class Question4ChoiceActivity extends AppCompatActivity {
    int answer = -1;
    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_page_type3);
        Objects.requireNonNull(getSupportActionBar()).hide();

        RadioGroup rg = findViewById(R.id.radioGrop);
        RadioButton[] radios = {findViewById(R.id.radioButton2),findViewById(R.id.radioButton3),findViewById(R.id.radioButton4),findViewById(R.id.radioButton5)};
        Button previous = findViewById(R.id.button10);
        Button next = findViewById(R.id.button8);
        TextView questionText = findViewById(R.id.textView26);
        TextView questionNum = findViewById(R.id.textView27);
        TextView questionGrade = findViewById(R.id.textView33);

        for (int i = 0 ; i < 4 ; i++) {
            int finalI1 = i;
            radios[i].setOnClickListener(v -> {
                if(answer == finalI1)
                {
                    rg.clearCheck();
                    answer = -1;
                }else answer = finalI1;

            });
        }

        //put data
        Exam temp = ActivityHolder.exam;
        Intent i = getIntent();
        int num = i.getIntExtra("question",0);
        String tex = temp.getQuestions().get(num).getText();
        String[] splitText = tex.split("&");
        questionText.setText(splitText[0]);
        questionNum.setText((num + 1)+" / "+ temp.getQuestions().size());
        questionGrade.setText(temp.getQuestions().get(num).getMaxGrade()+" / "+ temp.getMaxGrade());
        previous.setVisibility(num == 0  ? View.GONE : View.VISIBLE);
        next.setText(getResources().getString(temp.getQuestions().size() == (num+1) ? R.string.Exit : R.string.next));
        for (int j = 0 ; j < 4 ; j++)
            radios[j].setText(splitText[j+1]);


        View.OnClickListener Exit = v->{
            save(String.valueOf(answer),temp.getQuestions().get(num));
            startActivity(new Intent(this,MainPage.class));
            finish();};

        View.OnClickListener nextClick = v->{
            int t = ActivityHolder.exam.getQuestions().get(num+1).getChoices();
            Intent n = new Intent(this,t == 0?QuestionFileActivity.class:(t == 1 ?QuestionTypingActivity.class :
                    Question4ChoiceActivity.class));
            n.putExtra("question",num+1);
            save(String.valueOf(answer),temp.getQuestions().get(num));
            startActivity(n);
            finish();};
        View.OnClickListener back = v->{
            int t = ActivityHolder.exam.getQuestions().get(num-1).getChoices();
            Intent n = new Intent(this,t == 0?QuestionFileActivity.class:(t == 1 ?QuestionTypingActivity.class :
                    Question4ChoiceActivity.class));
            n.putExtra("question",num-1);
            save(String.valueOf(answer),temp.getQuestions().get(num));
            startActivity(n);
            finish();};
        next.setOnClickListener(temp.getQuestions().size() == (num+1) ? Exit : nextClick);
        previous.setOnClickListener(back);
    }
    private void save(String text, Question question){
        Answer answer = new Answer("*",text,question,ActivityHolder.user,null);
        AnswerDA ada = new AnswerDA();
        ada.add(answer);
    }
    @Override
    public void onBackPressed() {

    }
}