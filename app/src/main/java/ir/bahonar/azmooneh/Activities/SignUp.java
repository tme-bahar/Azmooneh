package ir.bahonar.azmooneh.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

import ir.bahonar.azmooneh.DA.UserDA;
import ir.bahonar.azmooneh.R;
import ir.bahonar.azmooneh.domain.User;

public class SignUp extends AppCompatActivity {
    User.userType type;
    @Override
    protected void onCreate(Bundle saveInstance) {
        super.onCreate(saveInstance);
        setContentView(R.layout.activity_signup_page);
        Objects.requireNonNull(getSupportActionBar()).hide();
        Intent i = getIntent();
        Button singIn = findViewById(R.id.button2);
        Button singUp = findViewById(R.id.button);
        EditText username = findViewById(R.id.editTextTextPersonName3);
        EditText firstName = findViewById(R.id.editTextTextPersonName4);
        EditText lastName = findViewById(R.id.editTextTextPersonName5);
        EditText password = findViewById(R.id.editTextTextPassword2);
        Button teacher = findViewById(R.id.button4);
        Button student = findViewById(R.id.button5);
        username.setText(i.getStringExtra("username"));
        password.setText(i.getStringExtra("password"));

        type = User.userType.STUDENT;
        teacher.setOnClickListener(v->{type = User.userType.TEACHER;
            teacher.setBackgroundResource(R.drawable.student_btn);
            student.setBackgroundResource(R.drawable.teacher_btn);});
        student.setOnClickListener(v->{type = User.userType.STUDENT;
            student.setBackgroundResource(R.drawable.student_btn);
            teacher.setBackgroundResource(R.drawable.teacher_btn);});
        singIn.setOnClickListener(v->{finish();});
        singUp.setOnClickListener(v->{
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            if(new UserDA().isValidUser(username.getText().toString()))
            {
                dialog.setTitle(R.string.valid);
                dialog.setMessage(R.string.validText);
                dialog.setCancelable(true);
                dialog.setPositiveButton(R.string.sign_in, (dialog1, which) -> {
                    singIn.performClick();
                });
                dialog.setNegativeButton(R.string.cancel,(a,b)->{
                    a.dismiss();
                });
                dialog.show();
            }else if(isEmpty(username)||isEmpty(firstName)||isEmpty(lastName)||isEmpty(password)){
                dialog.setTitle(R.string.Empty);
                dialog.setMessage(R.string.EmtyText);
                dialog.setCancelable(true);
                dialog.setNegativeButton(R.string.OK,(a,b)->{
                    a.dismiss();
                });
                dialog.show();
            }else{
                //TODO : Type and prof must be complete
                User user = new User("*","0000",firstName.getText().toString(),lastName.getText().toString(),username.getText().toString(),password.getText().toString(), type,"");
                new UserDA().singUp(user);
                dialog.setTitle(R.string.sign_up);
                dialog.setMessage(R.string.sign_up_complete);
                dialog.setCancelable(true);
                dialog.setPositiveButton(R.string.sign_in, (dialog1, which) -> {
                    singIn.performClick();
                });
                dialog.setNegativeButton(R.string.cancel,(a,b)->{
                    a.dismiss();
                });
                dialog.show();
            }
        });
    }
    private boolean isEmpty(EditText et){
        return et.getText().length() ==0;
    }
}
