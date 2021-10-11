package ir.bahonar.azmooneh;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import ir.bahonar.azmooneh.DA.relatedObjects.ActivityHolder;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityHolder.activity = this;
    }
}