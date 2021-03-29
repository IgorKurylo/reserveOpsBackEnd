package utils;


import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    public static java.sql.Date toSQLDateType(String date) {
        return java.sql.Date.valueOf(date);
    }

    public static java.sql.Time toSQLTimeType(String time) {
        return java.sql.Time.valueOf(time);
    }
}