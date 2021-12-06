package ir.bahonar.azmooneh.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.Objects;

import ir.bahonar.azmooneh.R;

public class Question4ChoiceActivity extends AppCompatActivity {
    int answer = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_page_type3);
        Objects.requireNonNull(getSupportActionBar()).hide();
        RadioGroup rg = findViewById(R.id.radioGrop);
        RadioButton[] radios = {findViewById(R.id.radioButton2),findViewById(R.id.radioButton3),findViewById(R.id.radioButton4),findViewById(R.id.radioButton5)};
        Button previous = findViewById(R.id.button10);
        Button next = findViewById(R.id.button8);
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
        previous.setVisibility(View.GONE);
        next.setOnClickListener(v->startActivity(new Intent(this,QuestionTypingActivity.class)));
    }

    @Override
    public void onBackPressed() {

    }
}