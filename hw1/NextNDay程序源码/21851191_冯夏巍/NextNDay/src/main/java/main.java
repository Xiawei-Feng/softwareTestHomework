import java.text.SimpleDateFormat;
import java.util.Calendar;

public class main {
    public static void main(String[] args) throws Exception {
        String dt = "1582-10-4";  // Start date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(sdf.parse(dt));
        c.add(Calendar.DATE, 365);  // number of days to add
        dt = sdf.format(c.getTime());  // dt is now the new date
        System.out.println(dt);
    }
}
