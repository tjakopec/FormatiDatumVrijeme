package jakopec.android;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by tjakopec on 25.04.2017..
 */

public class Datum {

    private Date datum;

    public Datum(){
        datum = new Date();

        System.out.println(datum);

        System.out.println(datum.getTime());


        SimpleDateFormat df = new SimpleDateFormat("dd. MMMM yyyy. HH:mm:ss");

        System.out.println(df.format(datum));


        Calendar c = Calendar.getInstance();

        c.setTime(datum);

        c.add(Calendar.DAY_OF_YEAR, 48);


        System.out.println(df.format(c.getTime()));

        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();

        gc.setTime(c.getTime());

        gc.add(Calendar.DAY_OF_YEAR, -48);

        System.out.println(df.format(gc.getTime()));
    }

}
