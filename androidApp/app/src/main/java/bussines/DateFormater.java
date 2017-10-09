package bussines;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Hp on 10/7/2017.
 */

public class DateFormater {





    public static String getDayFromDateString(String stringDate)
    {
        String[] daysArray = new String[] {"7","1","2","3","4","5","6"};
        String day = "";

        int dayOfWeek =0;
        //dateTimeFormat =
        SimpleDateFormat formatter = new SimpleDateFormat("dd-mm-yyyy");
        Date date;
        try {
            date = formatter.parse(stringDate);
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            dayOfWeek = c.get(Calendar.DAY_OF_WEEK)-1;
            if (dayOfWeek < 0) {
                dayOfWeek += 7;
            }
            day = daysArray[dayOfWeek];
        } catch (Exception e) {
            e.printStackTrace();
        }

        return day;
    }
    public String getDateDayID(String date){




        return "";
    }


    public String getDateDay(String date){

        return "";
    }


    public static String getIdFromDay(String day){

        HashMap<String,String> dayIdMap = new HashMap<>();
        dayIdMap.put("Mon","1");
        dayIdMap.put("Tue","2");
        dayIdMap.put("Wed","3");
        dayIdMap.put("Thu","4");
        dayIdMap.put("Fri","5");
        dayIdMap.put("Sat","6");
        dayIdMap.put("Sun","7");

        return dayIdMap.get(day);
    }




}
