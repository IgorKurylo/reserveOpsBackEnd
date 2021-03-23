package utils;

import java.sql.Time;
import java.text.SimpleDateFormat;

public class Converters {

    public static String ConvertTimeToString(Time time) {
        String t = "";
        SimpleDateFormat timeFormatter = new SimpleDateFormat(Const.TIME_FORMAT);
        t = timeFormatter.format(time);
        return t;
    }
}