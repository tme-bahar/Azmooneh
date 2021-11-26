package ir.bahonar.azmooneh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import java.util.Objects;

public class QuestionFileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_page_type1);
        Objects.requireNonNull(getSupportActionBar()).hide();
        Button previous = findViewById(R.id.button10);
        Button next = findViewById(R.id.button8);
    }
}