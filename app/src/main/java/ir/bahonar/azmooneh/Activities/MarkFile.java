package ir.bahonar.azmooneh.Activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import ir.bahonar.azmooneh.DA.AnswerDA;
import ir.bahonar.azmooneh.DA.relatedObjects.ActivityHolder;
import ir.bahonar.azmooneh.R;
import ir.bahonar.azmooneh.domain.answer.Answer;
import ir.bahonar.azmooneh.domain.exam.Exam;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;
import java.util.Objects;

public class MarkFile extends AppCompatActivity {

    String path ;
    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_page_type1_for_marking);
        Objects.requireNonNull(getSupportActionBar()).hide();

        Button previous = findViewById(R.id.button10);
        Button next = findViewById(R.id.button8);
        TextView questionText = findViewById(R.id.textView26);
        TextView questionNum = findViewById(R.id.textView27);
        TextView questionGrade = findViewById(R.id.textView33);
        EditText mark = findViewById(R.id.editTextNumberDecimal);

        Exam temp = ActivityHolder.exam;
        Intent i = getIntent();
        int num = i.getIntExtra("question",0);
        //put data
        AnswerDA ada = new AnswerDA();
        List<Answer> answer1 = ada.get(ActivityHolder.student,temp.getQuestions().get(num));
        Answer ans = null;
        for (Answer a:answer1)
            if(a.getQuestion().getId().equals(temp.getQuestions().get(num).getId())){
                ans = a;
                break;
            }

        path = ans == null ? "" : ans.getAnswer();
        if(ans != null)
            mark.setText(String.valueOf(ans.getGrade()));

        questionText.setText(temp.getQuestions().get(num).getText());
        questionNum.setText((num + 1)+" / "+ temp.getQuestions().size());
        questionGrade.setText(temp.getQuestions().get(num).getMaxGrade()+" / "+ temp.getMaxGrade());
        previous.setVisibility(num == 0  ? View.GONE : View.VISIBLE);
        next.setText(getResources().getString(temp.getQuestions().size() == (num+1) ? R.string.Exit : R.string.next));

        Answer finalAns = ans;
        View.OnClickListener Exit = v->{
            if(!save(mark.getText().toString(),
                    temp.getQuestions().get(num).getMaxGrade(), finalAns))
                return;
            startActivity(new Intent(this,StudentList.class));
            finish();};

        View.OnClickListener nextClick = v->{
            if(!save(mark.getText().toString(),
                    temp.getQuestions().get(num).getMaxGrade(), finalAns))
                return;
            int t = ActivityHolder.exam.getQuestions().get(num+1).getChoices();
            Intent n = new Intent(this,t == 0?MarkFile.class:(t == 1 ?MarkTyping.class :
                    Mark4Choice.class));
            n.putExtra("question",num+1);
            startActivity(n);
            finish();};

        View.OnClickListener back = v->{
            if(!save(mark.getText().toString(),
                    temp.getQuestions().get(num).getMaxGrade(), finalAns))
                return;
            int t = ActivityHolder.exam.getQuestions().get(num-1).getChoices();
            Intent n = new Intent(this,t == 0?MarkFile.class:(t == 1 ?MarkTyping.class :
                    Mark4Choice.class));
            n.putExtra("question",num-1);
            startActivity(n);
            finish();};
        next.setOnClickListener(temp.getQuestions().size() == (num+1) ? Exit : nextClick);
        previous.setOnClickListener(back);
    }

    private boolean save(String Grade,float max,Answer answer){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(getResources().getString(R.string.wrong_format));
        alert.setPositiveButton(getResources().getString(R.string.OK), (dialog, which) -> {
            dialog.cancel();
        });
        try {
            float grade = Float.parseFloat(Grade);
            if(grade > max){
                alert.setMessage(getResources().getString(R.string.grade_maxGrage));
                alert.show();
            }
            else
            {
                answer.setGrade(grade);
                AnswerDA ada = new AnswerDA();
                ada.change(answer);
                return true;
            }
            return false;
        }catch (Exception e){
            alert.setMessage(getResources().getString(R.string.wrong_format_message));
            alert.show();
            return false;
        }
    }
}