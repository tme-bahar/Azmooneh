package ir.bahonar.azmooneh.Activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;

import ir.bahonar.azmooneh.DA.UserDA;
import ir.bahonar.azmooneh.DA.relatedObjects.ActivityHolder;
import ir.bahonar.azmooneh.R;
import ir.bahonar.azmooneh.domain.User;

public class ProfileActivity extends AppCompatActivity {
    private static final int SELECT_PICTURE = 200;
    private String profilePath = "";
    ImageView prof;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        //initializing
        TextView firstLastNameTV =findViewById(R.id.textView14);
        TextView typeTV = findViewById(R.id.textView20);
        TextView usernameTV = findViewById(R.id.textView16);
        TextView numberTV = findViewById(R.id.textView17);
        TextView firstNameTV = findViewById(R.id.textView24);
        TextView lastNameTV = findViewById(R.id.textView21);

        EditText usernameET = findViewById(R.id.editTextTextPersonName7);
        EditText numberET = findViewById(R.id.editTextTextPersonName6);
        EditText firstNameET = findViewById(R.id.editTextTextPersonName8);
        EditText lastNameET = findViewById(R.id.editTextTextPersonName11);
        EditText passwordET = findViewById(R.id.editTextTextPassword4);

        Button save = findViewById(R.id.button11);
        Button picImage = findViewById(R.id.button9);
        try {
        prof = findViewById(R.id.imageView4);
        }finally {}

        //putting data
        firstLastNameTV.setText(ActivityHolder.user.getFirstName()+" "+ActivityHolder.user.getLastName());
        typeTV.setText(ActivityHolder.user.getType() == User.userType.TEACHER ? getResources().getString(R.string.teacher) :
                getResources().getString(R.string.student));
        usernameTV.setText(ActivityHolder.user.getUsername());
        numberTV.setText(ActivityHolder.user.getNumber());
        firstNameTV.setText(ActivityHolder.user.getFirstName());
        lastNameTV.setText(ActivityHolder.user.getLastName());
        prof.setImageURI(Uri.parse(ActivityHolder.user.getProfile()));

        //pickup image
        picImage.setOnClickListener(v->{
            Intent i = new Intent();
            i.setType("image/*");
            i.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
        });

        //saving
        save.setOnClickListener(v->{

            ProgressDialog dialog = ProgressDialog.show(this, "",
                    "Loading. Please wait...", true);
            String number = numberET.getText().toString().isEmpty() ?numberTV.getText().toString() : numberET.getText().toString();
            String firstName = firstNameET.getText().toString().isEmpty() ?firstNameTV.getText().toString() : firstNameET.getText().toString();
            String lastName = lastNameET.getText().toString().isEmpty() ? lastNameTV.getText().toString() : lastNameET.getText().toString();
            String userName = usernameET.getText().toString().isEmpty() ? usernameTV.getText().toString() : usernameET.getText().toString();
            String password = passwordET.getText().toString().isEmpty() ?ActivityHolder.user.getPassword() : passwordET.getText().toString();


            User newUser = new User(ActivityHolder.user.getId(),number,firstName,lastName,userName,password,
                    ActivityHolder.user.getType(),profilePath);
            UserDA uda = new UserDA();
            uda.change(newUser);
            ActivityHolder.user = uda.signIn(userName,password);
            startActivity(new Intent(this,MainPage.class));
            //dialog.cancel();
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
                    profilePath = selectedImageUri.toString();
                    prof.setImageURI(selectedImageUri);
                }
            }
        }
    }
}