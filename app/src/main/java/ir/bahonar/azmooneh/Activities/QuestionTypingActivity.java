package ir.bahonar.azmooneh.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import java.util.Objects;

import ir.bahonar.azmooneh.R;

public class QuestionTypingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_page_type2);
        Objects.requireNonNull(getSupportActionBar()).hide();
        Button previous = findViewById(R.id.button10);
        Button next = findViewById(R.id.button8);
        previous.setOnClickListener(v->finish());
        next.setText(getResources().getString(R.string.Exit));
        next.setOnClickListener(v->startActivity(new Intent(this,MainPage.class)));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}