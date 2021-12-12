package ir.bahonar.azmooneh.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Objects;

import ir.bahonar.azmooneh.DA.relatedObjects.ActivityHolder;
import ir.bahonar.azmooneh.R;
import ir.bahonar.azmooneh.domain.Question;
import ir.bahonar.azmooneh.domain.exam.Exam;

public class FileQuestionEdit extends AppCompatActivity {

    private static final int SELECT_PICTURE = 200;
    private String filePath = "";
    ImageView prof;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_page_type1_for_teacher);
        Objects.requireNonNull(getSupportActionBar()).hide();
        //initialize
        EditText text = findViewById(R.id.editTextTextMultiLine4);
        Button save = findViewById(R.id.button12);
        prof = findViewById(R.id.imageView3);
        EditText grade = findViewById(R.id.editTextTextPersonName14);
        TextView number = findViewById(R.id.textView27);

        //put date
        Intent intent = getIntent();
        int num = intent.getIntExtra("question",ActivityHolder.exam.getQuestions().size());
        number.setText(String.valueOf(num)+1);
        if(num != ActivityHolder.exam.getQuestions().size())
        {
            text.setText(ActivityHolder.exam.getQuestions().get(num).getText());
        }

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
            if(text.getText().toString().isEmpty()){
                alert.setMessage(getResources().getString(R.string.text_empty));
                alert.show();
                return;
            }
            float gradeNum ;
            try {
                gradeNum = Float.parseFloat(grade.getText().toString());
            }catch (Exception e){
                alert.setMessage(getResources().getString(R.string.grade_invalid));
                alert.show();
                return;
            }
            Exam temp = ActivityHolder.exam;
            Question q = new Question("*", temp.getQuestions().size(),text.getText().toString(),filePath,0,temp,gradeNum);
            ActivityHolder.exam.getQuestions().add(q);
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