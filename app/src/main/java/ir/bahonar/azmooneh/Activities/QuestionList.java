package ir.bahonar.azmooneh.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ir.bahonar.azmooneh.DA.relatedObjects.ActivityHolder;
import ir.bahonar.azmooneh.QuestionAdapter;
import ir.bahonar.azmooneh.R;
import ir.bahonar.azmooneh.UserAdapter;
import ir.bahonar.azmooneh.domain.Question;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.Objects;

public class QuestionList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_list);
        Objects.requireNonNull(getSupportActionBar()).hide();

        //initializing
        FloatingActionButton fbt = findViewById(R.id.floatingActionButton4);
        RecyclerView rv = findViewById(R.id.questionList);


        List<Question> all = ActivityHolder.exam.getQuestions();
        if (ActivityHolder.exam!= null && all != null && !all.isEmpty()) {
            rv.setLayoutManager(new LinearLayoutManager(this));
            QuestionAdapter a = new QuestionAdapter(all);
            a.setOnItemClickListener((question, position) -> {
                Intent i = new Intent(this,question.getChoices() == 0?
                        FileQuestionEdit.class:(question.getChoices() == 1?TextQuestionEdit.class:TestQuestionEdit.class));
                i.putExtra("question",position);
                startActivity(i);
            });
            a.setOnItemLongClickListener((question, position) -> {
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setTitle(getResources().getString(R.string.are_you_sure));
                alert.setMessage(getResources().getString(R.string.are_you_sure_message));
                alert.setPositiveButton(getResources().getString(R.string.yes), (dialog, which) -> {
                    ActivityHolder.exam.getStudents().remove(question);
                    a.notifyDataSetChanged();
                    Toast.makeText(getApplicationContext(), "question Removed", Toast.LENGTH_LONG);
                    dialog.dismiss();
                });
                alert.setNegativeButton(getResources().getString(R.string.No), (d, w) -> d.dismiss());
                alert.show();

            });
            rv.setAdapter(a);
        }
        //add
        fbt.setOnClickListener(v->{

            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle(getResources().getString(R.string.which));
            alert.setMessage(getResources().getString(R.string.which_message));
            alert.setPositiveButton(getResources().getString(R.string.File), (dialog, which) -> {
                Intent i = new Intent(this,FileQuestionEdit.class);
                i.putExtra("question",ActivityHolder.exam.getQuestions().size());
                startActivity(i);
                dialog.dismiss();
                finish();
            });
            alert.setNegativeButton(getResources().getString(R.string.text),(d,w)-> {
                Intent i = new Intent(this,TextQuestionEdit.class);
                i.putExtra("question",ActivityHolder.exam.getQuestions().size());
                startActivity(i);
                d.dismiss();
                finish();
            });
            alert.setNeutralButton(getResources().getString(R.string._4_choice),(d,w)->{
                Intent i = new Intent(this,TestQuestionEdit.class);
                i.putExtra("question",ActivityHolder.exam.getQuestions().size());
                startActivity(i);
                d.dismiss();
                finish();
            });
            alert.show();
        });

    }
}