package ir.bahonar.azmooneh.DA.relatedObjects;

import android.annotation.SuppressLint;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class TimeHandler {

    //fields
    private final Date dateTime;

    //constructor
    public TimeHandler(String dateTime) throws Exception{
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm'&'yyyy/MM/dd", Locale.ENGLISH);
        this.dateTime = formatter.parse(dateTime);
    }
    public static Calendar parse(String date) throws Exception{
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm'&'yyyy/MM/dd", Locale.ENGLISH);
        Date d = formatter.parse(date);
        return toCalendar(d);
    }
    public static Calendar toCalendar(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }
    public Date getDateTime() {
        return dateTime;
    }

    //get date
    public String getDate(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);
        String date = formatter.format(dateTime);
        Date today = Calendar.getInstance().getTime();
        String todayDateStr = formatter.format(today);
        Date firstDate ;
        Date secondDate;
        try {
            firstDate = formatter.parse(date);
            secondDate = formatter.parse(todayDateStr);
            int daysAgo = ((int)((secondDate.getTime() - firstDate.getTime())/86400000));
            if(daysAgo == 0)
                return "";
            if(daysAgo == 1)
                return "yesterday";
            if(today.getYear() == firstDate.getYear()) {
                @SuppressLint("SimpleDateFormat")
                DateFormat df = new SimpleDateFormat("dd MMM");
                return df.format(firstDate);
            }else {
                @SuppressLint("SimpleDateFormat")
                DateFormat df = new SimpleDateFormat("yyyy / MM / dd");
                return df.format(firstDate);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    //get time
    public String getTime(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);
        String date = formatter.format(dateTime);
        Date today = Calendar.getInstance().getTime();
        String todayDateStr = formatter.format(today);
        Date firstDate ;
        Date secondDate;
        try {
            firstDate = formatter.parse(date);
            secondDate = formatter.parse(todayDateStr);
            int daysAgo = ((int)((secondDate.getTime() - firstDate.getTime())/86400000));
            @SuppressLint("SimpleDateFormat")
            DateFormat tdf = new SimpleDateFormat("HH:mm");
            if(daysAgo > 0){return tdf.format(today);}
            else {
                String firstDayTimeStr = tdf.format(dateTime);
                String todayTimeStr = tdf.format(today);
                Date firstDayTime = formatter.parse(firstDayTimeStr);
                Date secDayTime = formatter.parse(todayTimeStr);
                int minutesAgo = ((int)((firstDayTime.getTime() - secDayTime.getTime())/60000));
                if(minutesAgo <= 1)
                    return "just now";
                if(minutesAgo <=10)
                    return minutesAgo+" min ago";
                else
                    return tdf.format(today);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
