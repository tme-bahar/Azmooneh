package ir.bahonar.azmooneh.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Objects;

import ir.bahonar.azmooneh.DA.relatedObjects.ActivityHolder;
import ir.bahonar.azmooneh.R;
import ir.bahonar.azmooneh.domain.Question;
import ir.bahonar.azmooneh.domain.exam.Exam;

public class TestQuestionEdit extends AppCompatActivity {

    private static final int SELECT_PICTURE = 200;
    private String filePath = "";
    ImageView prof;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_page_type3_for_teacher);
        Objects.requireNonNull(getSupportActionBar()).hide();
        //initialize
        EditText text = findViewById(R.id.editTextTextPersonName10);
        Button save = findViewById(R.id.button15);
        prof = findViewById(R.id.imageView3);
        EditText grade = findViewById(R.id.editTextTextPersonName14);
        TextView number = findViewById(R.id.textView27);
        EditText[] answers = {findViewById(R.id.editTextTextPersonName24),
                findViewById(R.id.editTextTextPersonName22),
                findViewById(R.id.editTextTextPersonName21),
                findViewById(R.id.editTextTextPersonName12)};

        //put date
        number.setText(String.valueOf(ActivityHolder.exam.getQuestions().size()+1));

        prof.setOnClickListener(v->{
            Intent i = new Intent();
            i.setType("image/*");
            i.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
        });
        save.setOnClickListener(v->{
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle(getResources().getString(R.string.warning));
            alert.setNegativeButton(getResources().getString(R.string.OK), (d, w) -> d.dismiss());
            if(grade.getText().toString().isEmpty()){
                alert.setMessage(getResources().getString(R.string.grade_empty));
                alert.show();
                return;
            }
            if(grade.getText().toString().contains("&")){
                alert.setMessage(getResources().getString(R.string.deny_text));
                alert.show();
                return;
            }
            for (EditText r:answers)
                if(r.getText().toString().contains("&")){
                    alert.setMessage(getResources().getString(R.string.deny_text));
                    alert.show();
                    return;
                }
            if(text.getText().toString().isEmpty()){
                alert.setMessage(getResources().getString(R.string.text_empty));
                alert.show();
                return;
            }
            float gradeNum = 0f;
            try {
                gradeNum = Float.parseFloat(grade.getText().toString());
            }catch (Exception e){
                alert.setMessage(getResources().getString(R.string.grade_invalid));
                alert.show();
                return;
            }
            StringBuilder tex = new StringBuilder(text.getText().toString());
            for (EditText r:answers)
                tex.append("&").append(r.getText().toString());
            Exam temp = ActivityHolder.exam;
            Question q = new Question("*", temp.getQuestions().size(),
                    tex.toString(),"",4,temp,gradeNum);
            for(int i = 0 ; i < 4 ; i ++)
                q.setChoiceText(i,answers[i].getText().toString());
            temp.getQuestions().add(q);
            ActivityHolder.exam = temp;
            startActivity(new Intent(this,QuestionList.class));
            finish();
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    filePath = selectedImageUri.toString();
                    prof.setImageURI(selectedImageUri);
                }
            }
        }
    }
}