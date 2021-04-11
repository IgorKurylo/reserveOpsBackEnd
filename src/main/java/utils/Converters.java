package utils;


import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Converters {

    public static String ConvertTimeToString(Time time) {
        String t = "";
        SimpleDateFormat timeFormatter = new SimpleDateFormat(Const.TIME_FORMAT);
        t = timeFormatter.format(time);
        return t;
    }

    public static Date convertTimeFromString(String time) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(Const.TIME_FORMAT, Locale.getDefault());
        return formatter.parse(time);
    }

    public static Date convertDateFromString(String date) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(Const.DATE_FORMAT, Locale.getDefault());
        return formatter.parse(date);
    }

    public static java.sql.Date toSQLDateType(String date) {
        return java.sql.Date.valueOf(date);
    }

    public static java.sql.Time toSQLTimeType(String time) {
        return java.sql.Time.valueOf(time);
    }

    public static String getDayOfWeekName(Date date) {
        SimpleDateFormat simpleDateformat = new SimpleDateFormat("E"); // the day of the week abbreviated
        return simpleDateformat.format(date);

    }

    public static int getDayOfWeekNumber(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }
}