package pro.pie.me.datatimes;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Author : liuby.
 * Description :
 * Date : Created in 2017/11/29 14:07
 */
@Slf4j
public class DateUtil {

    /**
     * 年-月-日
     */
    public static final String PATTERN = "yyyy-MM-dd";
    /**
     * XXXX年XX月XX日
     */
    public static final String PATTERN_1 = "yyyy年MM月dd日";
    /**
     * XXXXXXXX
     */
    public static final String PATTERN_2 = "yyyyMMdd";

    /**
     * Author : liuby
     * Description : 判断给定日期是否为月末的一天
     * Date : Created in 2017/11/29 14:07
     */
    public static boolean isLastDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DATE, (calendar.get(Calendar.DATE) + 1));
        if (calendar.get(Calendar.DAY_OF_MONTH) == 1) {
            return true;
        }
        return false;
    }

    /**
     * Author : liuby
     * Description : 判断给定日期是否为月末的一天
     * Date : Created in 2017/11/29 14:07
     */
    public static Date getToday() {
        return format(new Date(), PATTERN);
    }

    /**
     * Author : liuby
     * Description : 获取昨天
     * Date : Created in 2017/11/29 14:07
     */
    public static Date getYesterday() {
        return format(getLastDate(new Date()), PATTERN);
    }

    /**
     * Author : liuby
     * Description : 获取日期下个月的最后一天
     * Date : Created in 2017/11/29 14:07
     */
    public static Date getLastDayOfNextMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 2);
        calendar.set(Calendar.DATE, 0);
        return calendar.getTime();
    }

    /**
     * Author : liuby
     * Description : 判断两个时间段是否有交叉
     * Date : Created in 2017/11/29 14:07
     */
    public static Boolean isOverlap(Date startDate1, Date endDate1, Date startDate2, Date endDate2) {
        return ((startDate1.getTime() >= startDate2.getTime()) && startDate1.getTime() < endDate2.getTime())
                ||
                ((startDate1.getTime() > startDate2.getTime()) && startDate1.getTime() <= endDate2.getTime())
                ||
                ((startDate2.getTime() >= startDate1.getTime()) && startDate2.getTime() < endDate1.getTime())
                ||
                ((startDate2.getTime() > startDate1.getTime()) && startDate2.getTime() <= endDate1.getTime());
    }

    /**
     * Author : liuby
     * Description : 获取下个月的第一天
     * Date : Created in 2017/11/30 14:15
     */
    public static Date getNextMonthFirstDate() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.MONTH, c.get(Calendar.MONTH) + 1);
        c.set(Calendar.DAY_OF_MONTH, 1);
        return c.getTime();
    }

    /**
     * Author : liuby
     * Description : 获取下个月的最后一天
     * Date : Created in 2017/11/30 14:15
     */
    public static Date getNextMonthLastDate() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.MONTH, c.get(Calendar.MONTH) + 2);
        c.set(Calendar.DAY_OF_MONTH, 0);
        return c.getTime();
    }

    /**
     * Author : liuby
     * Description : 获取当前年
     * Date : Created in 2017/11/30 14:15
     */
    public static int getYear() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.YEAR);
    }

    /**
     * Author : liuby
     * Description : 获取当前月
     * Date : Created in 2017/11/30 14:15
     */
    public static int getMonth() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.MONTH) + 1;
    }

    /**
     * Author : liuby
     * Description : 获取当前月
     * Date : Created in 2017/11/30 14:15
     */
    public static String getFullPattern(int day) {
        StringBuilder sb = new StringBuilder();
        sb.append(getYear()).append("-").append(getMonth()).append("-").append(day);
        return sb.toString();
    }

    /**
     * Author : liuby
     * Description : 格式化日期
     * Date : Created in 2017/11/30 14:15
     */
    public static Date format(Date date, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        String dd = format.format(date);
        try {
            return format.parse(dd);
        } catch (ParseException e) {
            log.error("DateUtil.format ---->", e);
        }
        return date;
    }

    /**
     * Author : liuby
     * Description : 格式化日期
     * Date : Created in 2017/11/30 14:15
     */
    public static Date format(String date, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        try {
            return format.parse(date);
        } catch (ParseException e) {
            log.error("DateUtil.format ---->", e);
        }
        return null;
    }

    /**
     * Author : liuby
     * Description : 格式化日期
     * Date : Created in 2017/11/30 14:15
     */
    public static String formatToStr(Date date, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }

    /**
     * Author : liuby
     * Description : 计算两个日期之间相差的天数
     * Date : Created in 2017/12/3 16:52
     */
    public static int daysBetween(Date smdate, Date bdate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        smdate = sdf.parse(sdf.format(smdate));
        bdate = sdf.parse(sdf.format(bdate));
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * Author : liuby
     * Description : 获取下一天
     * Date : Created in 2017/12/5 16:19
     */
    public static Date getNextDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date == null ? new Date() : date);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        return new Date(calendar.getTimeInMillis());
    }

    /**
     * Author : liuby
     * Description : 获取前一天
     * Date : Created in 2017/12/5 16:19
     */
    public static Date getLastDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date == null ? new Date() : date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        return new Date(calendar.getTimeInMillis());
    }

    public static void main(String[] args) {
        System.out.println(getLastDate(new Date()));

    }

}
