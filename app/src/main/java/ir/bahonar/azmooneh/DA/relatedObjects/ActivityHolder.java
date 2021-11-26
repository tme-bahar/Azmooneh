package ir.bahonar.azmooneh.DA.relatedObjects;

import android.annotation.SuppressLint;
import android.app.Activity;

import java.util.List;

import ir.bahonar.azmooneh.domain.Question;
import ir.bahonar.azmooneh.domain.User;
import ir.bahonar.azmooneh.domain.exam.Exam;

public class ActivityHolder {
    @SuppressLint("StaticFieldLeak")
    public static Activity activity;
    public static Exam exam;
    public static User user;
}
