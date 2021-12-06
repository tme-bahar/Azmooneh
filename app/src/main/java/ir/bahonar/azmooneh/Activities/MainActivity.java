package ir.bahonar.azmooneh.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Objects;

import ir.bahonar.azmooneh.DA.UserDA;
import ir.bahonar.azmooneh.DA.relatedObjects.ActivityHolder;
import ir.bahonar.azmooneh.R;
import ir.bahonar.azmooneh.domain.User;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).hide();
        ActivityHolder.activity = this;
        new UserDA().isFirstIn();
        //initialize
        EditText username = findViewById(R.id.editTextTextPersonName);
        EditText password = findViewById(R.id.editTextTextPassword);
        TextView signUp = findViewById(R.id.textView3);
        CheckBox reminder = findViewById(R.id.radioButton);
        signUp.setOnClickListener(v->{
            Intent i = new Intent(MainActivity.this,SignUp.class);
            i.putExtra("username",username.getText().toString());
            i.putExtra("password",password.getText().toString());
            startActivity(i);});
        Button logIn = findViewById(R.id.button3);
        logIn.setOnClickListener(v->{
            User user =new UserDA().isValidUser(username.getText().toString(),password.getText().toString());
            if(user != null){
                Intent i = new Intent(this,MainPage.class);
                ActivityHolder.user = user;
                startActivity(i);
                if(reminder.isChecked())
                    new UserDA().keepSignedIn(username.getText().toString(),password.getText().toString());
            }
            else
            {
                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                dialog.setTitle(R.string.notValid);
                dialog.setMessage(R.string.notValidText);
                dialog.setCancelable(true);
                dialog.setPositiveButton(R.string.sign_up, (dialog1, which) -> {
                    signUp.performClick();
                });
                dialog.setNegativeButton(R.string.cancel,(a,b)->{
                    a.dismiss();
                });
                dialog.show();
            }
        });
        UserDA uda = new UserDA();
        if(uda.isSignedIn())
        {
            username.setText(uda.getUserName());
            password.setText(uda.getPassword());
            logIn.performClick();
        }
    }
}