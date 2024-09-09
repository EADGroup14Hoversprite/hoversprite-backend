package shared.types;

import com.ibm.icu.util.ChineseCalendar;
import lombok.Data;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Data
public class LunarDate {
    private final int day;
    private final int month;
    private final int year;

    public LunarDate(LocalDate date) {
        Date gregorianDate = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());

        ChineseCalendar chineseCalendar = new ChineseCalendar();
        chineseCalendar.setTime(gregorianDate);

        this.year = chineseCalendar.get(ChineseCalendar.EXTENDED_YEAR) - 2637;
        this.month = chineseCalendar.get(ChineseCalendar.MONTH) + 1;
        this.day = chineseCalendar.get(ChineseCalendar.DAY_OF_MONTH);
    }

    @Override
    public String toString() {
        return year + "-" + month + "-" + day;
    }
}
