package fincode.utils;

import java.time.LocalDate;

/**
 * @author zlj
 * @date 2023/4/7
 */
public class TimeUtils {
    // 从形如20201010的时间Int获取localDate对象
    public static LocalDate convertTimeFromInt(int fromTime) {
        int day = fromTime % 100;
        int month = ((fromTime - day) % 10000) / 100;
        int year = (fromTime -day - 100 * month) / 10000;

        return LocalDate.of(year, month, day);
    }

    // 返回两个日期间的天数
    public static int getPeriod(int startDate, int endDate) {
        return (int) (Math.abs(TimeUtils.convertTimeFromInt(endDate).toEpochDay() - TimeUtils.convertTimeFromInt(startDate).toEpochDay()) + 1);
    }
}
