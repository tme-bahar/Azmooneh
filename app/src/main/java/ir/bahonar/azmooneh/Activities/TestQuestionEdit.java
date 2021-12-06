package ir.bahonar.azmooneh.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.Objects;

import ir.bahonar.azmooneh.DA.relatedObjects.ActivityHolder;
import ir.bahonar.azmooneh.R;
import ir.bahonar.azmooneh.domain.Question;

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

        prof.setOnClickListener(v->{
            Intent i = new Intent();
            i.setType("image/*");
            i.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
        });
        save.setOnClickListener(v->{
            Question q = new Question("*", ActivityHolder.exam.getQuestions().size(),text.getText().toString(),"",4,ActivityHolder.exam,0);
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