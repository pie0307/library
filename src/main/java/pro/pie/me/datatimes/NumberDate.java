package pro.pie.me.datatimes;

import com.google.common.base.Preconditions;

import java.util.Calendar;
import java.util.Date;

/**
 * 数字可视化日期时间类型包装类
 * 提供基于数字可视化时间的基本运算如比较大小、转换等操作
 */
public class NumberDate {

    /**
     * 转换和比较时使用的枚举类型
     *
     * @author homelink
     */
    public enum TransType {
        MILLISECOND, SECOND, MINUTE, HOUR, DAY, MONTH, YEAR
    }

    /**
     * 支持格式：
     * yyyyMMddHHmmssSSS - 17位
     * yyyyMMddHHmmss - 14位
     * yyyyMMddHHmm - 12位
     * yyyyMMddHH - 10位
     * yyyyMMdd - 8位
     * yyyyMM - 6位
     * yyyy - 4位
     */
    private long numberTimestamp;

    public long getTimestamp() {
        return numberTimestamp;
    }

    private NumberDate(long numberTimestamp) {
        Preconditions.checkArgument(String.valueOf(numberTimestamp).length() >= 4, "必须至少是4位数字：如2016");
        this.numberTimestamp = numberTimestamp;
    }

    public static NumberDate newNumberDate(long numberTimestamp) {
        return new NumberDate(numberTimestamp);
    }

//    public static NumberDate newNumberDate(String strDate, String format) {
//        Preconditions.checkNotNull(strDate);
//        Preconditions.checkNotNull(format);
//        Date date = null;
//        SimpleDateFormat sdf = new SimpleDateFormat(format);
//        try {
//            date = sdf.parse(strDate);
//        } catch (ParseException e) {
//            throw new RuntimeException("无法转换的日期:" + strDate);
//        }
//        return newNumberDate(date);
//    }

//    public static NumberDate newNumberDate(Date date) {
//        Preconditions.checkNotNull(date);
//        return new NumberDate(DateUtils.format2Long(date, false));
//    }

//    public static NumberDate newNumberDateHasMillisecond(Date date) {
//        Preconditions.checkNotNull(date);
//        return new NumberDate(DateUtils.format2Long(date, true));
//    }

    /**
     * 比较是否在另一个NumberDate之前
     *
     * @param another   - another
     * @param transType - 按照指定类型比较
     * @return
     */
    public boolean before(NumberDate another, TransType transType) {
        Preconditions.checkNotNull(another);

        int n = subLength(transType);
        long me = Long.parseLong(String.valueOf(this.numberTimestamp).substring(0, n));
        long her = Long.parseLong(String.valueOf(another.getTimestamp()).substring(0, n));
        return me < her;
    }

    /**
     * 比较是否在另一个NumberDate之后
     *
     * @param another   - another
     * @param transType - 按照指定类型比较
     * @return
     */
    public boolean after(NumberDate another, TransType transType) {
        Preconditions.checkNotNull(another);

        int n = subLength(transType);
        long me = Long.parseLong(String.valueOf(this.numberTimestamp).substring(0, n));
        long her = Long.parseLong(String.valueOf(another.getTimestamp()).substring(0, n));
        return me > her;
    }

    /**
     * 根据类型返回日期截取长度
     *
     * @param transType 转换类型
     * @return int
     */
    private int subLength(TransType transType) {
        int n = 0;
        if (TransType.YEAR == transType) {
            n = 4;
        } else if (TransType.MONTH == transType) {
            n = 6;
        } else if (TransType.DAY == transType) {
            n = 8;
        } else if (TransType.HOUR == transType) {
            n = 10;
        } else if (TransType.MINUTE == transType) {
            n = 12;
        } else if (TransType.SECOND == transType) {
            n = 14;
        } else if (TransType.MILLISECOND == transType) {
            n = 17;
        }
        return n;
    }

    /**
     * 根据指定类型做时间减法
     *
     * @param n         - 要减的时间数
     * @param transType - 按照指定类型
     * @return
     */
//    public NumberDate minus(int n, TransType transType) {
//        Preconditions.checkArgument(n > 0, "参数n必须大于0");
//        return addOrMinus(-n, transType);
//    }

    /**
     * 根据指定类型做时间加法
     *
     * @param n         - 要减的时间数
     * @param transType - 按照指定类型
     * @return
     */
//    public NumberDate add(int n, TransType transType) {
//        Preconditions.checkArgument(n > 0, "参数n必须大于0");
//        return addOrMinus(n, transType);
//    }

//    private NumberDate addOrMinus(int n, TransType transType) {
//        Calendar me = Calendar.getInstance();
//        me.setTime(this.toDate());
//
//        if (TransType.YEAR == transType) {
//            me.add(Calendar.YEAR, n);
//        } else if (TransType.MONTH == transType) {
//            me.add(Calendar.MONTH, n);
//        } else if (TransType.DAY == transType) {
//            me.add(Calendar.DATE, n);
//        } else if (TransType.HOUR == transType) {
//            me.add(Calendar.HOUR, n);
//        } else if (TransType.MINUTE == transType) {
//            me.add(Calendar.MINUTE, n);
//        } else if (TransType.SECOND == transType) {
//            me.add(Calendar.SECOND, n);
//        } else if (TransType.MILLISECOND == transType) {
//            me.add(Calendar.MILLISECOND, n);
//        }
//
//        return NumberDate.newNumberDate(me.getTime());
//    }

    /**
     * 转换为java.util.Date类型
     *
     * @return
     */
    public Date toDate() {
        String str = String.valueOf(this.numberTimestamp);
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        if (str.length() >= 4) {
            calendar.set(Calendar.YEAR, Integer.parseInt(str.substring(0, 4)));
        }
        if (str.length() >= 6) {
            calendar.set(Calendar.MONTH, Integer.parseInt(str.substring(4, 6)) - 1);
        }
        if (str.length() >= 8) {
            calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(str.substring(6, 8)));
        }
        if (str.length() >= 14) {
            calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(str.substring(8, 10)));
            calendar.set(Calendar.MINUTE, Integer.parseInt(str.substring(10, 12)));
            calendar.set(Calendar.SECOND, Integer.parseInt(str.substring(12, 14)));
        }
        if (str.length() == 17) {
            calendar.set(Calendar.MILLISECOND, Integer.parseInt(str.substring(14, 17)));
        }
        return calendar.getTime();
    }

    @Override
    public String toString() {
        return String.valueOf(this.numberTimestamp);
    }

    public String toString(TransType transType) {
        if (TransType.YEAR == transType) {
            return String.valueOf(this.numberTimestamp).substring(0, 4);
        } else if (TransType.MONTH == transType) {
            return String.valueOf(this.numberTimestamp).substring(0, 6);
        } else if (TransType.DAY == transType) {
            return String.valueOf(this.numberTimestamp).substring(0, 8);
        } else if (TransType.HOUR == transType) {
            return String.valueOf(this.numberTimestamp).substring(0, 10);
        } else if (TransType.MINUTE == transType) {
            return String.valueOf(this.numberTimestamp).substring(0, 12);
        } else if (TransType.SECOND == transType) {
            return String.valueOf(this.numberTimestamp).substring(0, 14);
        } else if (TransType.MILLISECOND == transType) {
            String str = String.valueOf(this.numberTimestamp);
            if (str.length() == 17) {
                return String.valueOf(this.numberTimestamp).substring(0, 17);
            } else {
                return String.valueOf(this.numberTimestamp) + "000";
            }
        }
        return String.valueOf(this.numberTimestamp);
    }
}
