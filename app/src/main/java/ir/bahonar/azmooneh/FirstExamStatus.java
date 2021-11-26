package ir.bahonar.azmooneh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

public class FirstExamStatus extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_info_page);
        Button start = findViewById(R.id.button7);
        CheckBox cb = findViewById(R.id.checkBox);
        View hider = findViewById(R.id.view);
        cb.setOnCheckedChangeListener((b,c)->{
            hider.setVisibility(c ? View.GONE : View.VISIBLE);
            start.setClickable(c);
            if(c)
            start.setOnClickListener(v->{startActivity(new Intent(this,Question4ChoiceActivity.class));
                finish();});
        });

    }
}