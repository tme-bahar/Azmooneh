package ir.bahonar.azmooneh.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.Objects;

import ir.bahonar.azmooneh.Adapter;
import ir.bahonar.azmooneh.DA.UserDA;
import ir.bahonar.azmooneh.DA.relatedObjects.ActivityHolder;
import ir.bahonar.azmooneh.R;
import ir.bahonar.azmooneh.UserAdapter;
import ir.bahonar.azmooneh.domain.User;

public class StudentList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);
        Objects.requireNonNull(getSupportActionBar()).hide();

        //initialize
        RecyclerView rv = findViewById(R.id.studentList);
        FloatingActionButton fbt = findViewById(R.id.floatingActionButton3);

        //put data
        Intent intent = getIntent();
        boolean isAll = intent.getBooleanExtra("isAll",false);
        fbt.setVisibility(isAll? View.GONE : View.VISIBLE);

        if(isAll){
            UserDA uda = new UserDA();
            List<User> all = uda.getAllStudents();
            if (ActivityHolder.exam!= null && all != null && !all.isEmpty()){
                rv.setLayoutManager(new LinearLayoutManager(this));
                UserAdapter a = new UserAdapter(all);
                a.setOnItemClickListener((student, position) -> {
                    ActivityHolder.exam.getStudents().add(student);
                    startActivity(new Intent(this,StudentList.class));
                    finish();
                });
                rv.setAdapter(a);
            }
        }else{
            if (ActivityHolder.exam!= null &&ActivityHolder.exam.getStudents() != null ){
                rv.setLayoutManager(new LinearLayoutManager(this));
                UserAdapter a = new UserAdapter(ActivityHolder.exam.getStudents());
                a.setOnItemLongClickListener((student, position) -> {
                    AlertDialog.Builder alert = new AlertDialog.Builder(this);
                    alert.setTitle(getResources().getString(R.string.are_you_sure));
                    alert.setMessage(getResources().getString(R.string.are_you_sure_messege_student));
                    alert.setPositiveButton(getResources().getString(R.string.yes), (dialog, which) -> {
                        ActivityHolder.exam.getStudents().remove(student);
                        a.notifyDataSetChanged();
                        Toast.makeText(getApplicationContext(),"student Removed",Toast.LENGTH_LONG);
                        dialog.dismiss();
                    });
                    alert.setNegativeButton(getResources().getString(R.string.No),(d,w)->d.dismiss());
                    alert.show();

                });
                a.setOnItemClickListener((student, position) ->{
                    int t = ActivityHolder.exam.getQuestions().get(0).getChoices();
                    ActivityHolder.student = student;
                    Intent i = new Intent(this,t == 0?MarkFile.class:(t == 1 ?MarkTyping.class :
                            Mark4Choice.class));
                    i.putExtra("question",0);
                    startActivity(i);
                    finish();
                });
                rv.setAdapter(a);

            }
        }

        //floating action button
        fbt.setOnClickListener(v->{
            Intent i = new Intent(this,StudentList.class);
            i.putExtra("isAll",true);
            startActivity(i);
            finish();
        });
    }
}