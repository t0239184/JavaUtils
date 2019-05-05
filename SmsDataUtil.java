package tra.irts4.sms.service.pri.util;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.MonthDay;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 資料處理工具
 *
 * @author Alan
 */
public class SmsDataUtil {
    /**
     * logging
     */
    private static final Logger LOG = LoggerFactory.getLogger(SmsDataUtil.class);

    /** default pattern yyyyMMdd */
    public static final String DEFAULT_PATTERN = "yyyy/MM/dd";
    public static final String YYYY_MM_DD_PATTERN = "yyyyMMdd";
    public static final String YYYY_S_MM_S_DD_PATTERN = "yyyy/MM/dd";

    /** year pattern */
    public static final String YEAR_PATTERN = "yyyy";

    public static final String YYYY_D_MM_D_DD_PATTERN = "yyyy.MM.dd";
    public static final String HH_MM_PATTERN = "HH:mm";
    public static final String YYYY_MM_DD_HH_MM_SS_PATTERN = "yyyyMMddHHmmss";

    private SmsDataUtil() {
        throw new IllegalStateException("Utility class");
    }

    /** 如果t1 為null 則回傳t2 */
    public static <T> T nvl(T t1, T t2) {
        if (t1 instanceof String && isBlank((String) t1)) {// 字串如果為空
            return t2;
        }
        return t1 == null ? t2 : t1;
    }

    /** 將物件toString 回傳，如物件是null 則回傳null */
    public static String toStr(Object obj) {
        return obj != null ? obj.toString() : null;
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] datas(T... t) {
        return t;
    }

    /** 傳入字串是否為空(null 或 空字串含空白) */
    public static boolean isBlank(String s) {
        return StringUtils.isBlank(s);
    }

    /** 傳入字串是否不為空(null 或 空字串含空白) */
    public static boolean isNotBlank(String s) {
        return StringUtils.isNotBlank(s);
    }

    /** 是否過期 201126 Jean */
    public static boolean isOverDue(Date tranDate) {
        boolean flag = true;
        SimpleDateFormat dFormat = new SimpleDateFormat(DEFAULT_PATTERN);
        Date currentDate = java.sql.Date.valueOf(dFormat.format(new Date()));
        Date tranDateNew = java.sql.Date.valueOf(dFormat.format(tranDate));
        if (currentDate.before(tranDateNew)) {
            flag = false;// ==未進行
        } else if (currentDate.after(tranDateNew)) {
            flag = true;// ==逾期
        }
        return flag;
    }

    /**
     * 設定為時分秒23:59:59:999
     */
    public static Date truncEndDate(Date srcDate) {
        return customDateTime(srcDate, 23, 59, 59, 999);
    }

    /**
     * 設定為時分秒00:00:00:000
     */
    public static Date truncDate(Date srcDate) {
        return customDateTime(srcDate, 0, 0, 0, 0);
    }

    public static Date customDateTime(Date srcDate, int h, int m, int s, int x) {
        Calendar cal = Calendar.getInstance();
        Date rtnDate = null;
        if (srcDate != null) {
            cal.setTime(srcDate);
            cal.set(Calendar.HOUR_OF_DAY, h);
            cal.set(Calendar.MINUTE, m);
            cal.set(Calendar.SECOND, s);
            cal.set(Calendar.MILLISECOND, x);
            rtnDate = cal.getTime();
        }
        return rtnDate;
    }

    public static Date customDate(Date srcDate, int y, int m, int d) {
        Calendar cal = Calendar.getInstance();
        Date rtnDate = null;
        if (srcDate != null) {
            cal.setTime(srcDate);
            cal.set(Calendar.YEAR, y);
            cal.set(Calendar.MONTH, m);
            cal.set(Calendar.DAY_OF_MONTH, d);
            rtnDate = cal.getTime();
        }
        return rtnDate;
    }


    /**
     * 開始日與結束日的相隔天數
     *
     * @throws ParseException
     */
    public static long lessDay(Date startDay, Date endDay) throws ParseException {
        SimpleDateFormat dFormat = new SimpleDateFormat(DEFAULT_PATTERN);
        String sDateToStr = dFormat.format(startDay);
        String eDateToStr = dFormat.format(endDay);
        Date sDate = dFormat.parse(sDateToStr);
        Date eDate = dFormat.parse(eDateToStr);
        long betweenDate = (eDate.getTime() - sDate.getTime()) / (1000 * 60 * 60 * 24);
        return betweenDate + 1;// 包含開始日需加1
    }

    public static long lessLocalDay(Date startDate, Date endDate){
        LocalDate startTime = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate endTime = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return startTime.until(endTime, ChronoUnit.DAYS);
    }

    /** 字串轉日期localDate */
    public static LocalDate convStrToLocalDate(String dateStr, String format) {
        if (StringUtils.isBlank(dateStr) || StringUtils.isBlank(format)) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);

        return LocalDate.parse(dateStr, formatter);
    }

    /** 字串轉日期 */
    public static Date convStrToDate(String dateStr, String format) {
        Date day = null;
        if (!"".equals(dateStr)) {
            SimpleDateFormat df = new SimpleDateFormat(format);
            try {
                day = df.parse(dateStr);
            } catch (Exception e) {
                LOG.error(e.toString());
            }
        }
        return day;
    }

    /** 日期轉字串 */
    public static String convDateToStr(Date srcDate, String format) {
        String dateStr = null;
        if (srcDate != null) {
            SimpleDateFormat df = new SimpleDateFormat(format);
            try {
                dateStr = df.format(srcDate);
            } catch (Exception e) {
                LOG.error(e.toString());
            }
        }
        return dateStr;
    }

    /**
     * date to yyyy/MM/dd format
     *
     * @param date
     *         Date
     * @param separator
     *         char
     * @return yyyy/MM/dd
     */
    public static String convDateToStrWith(Date date, char separator) {
        String dateFormat = null;
        if (date != null) {
            String datePattern = null;
            if (separator != '\0') {
                datePattern = DEFAULT_PATTERN.replace('-', separator);
            } else {
                datePattern = DEFAULT_PATTERN.replace("-", "");
            }
            dateFormat = new SimpleDateFormat(datePattern).format(date);
        }
        return dateFormat;
    }

    /** 日期轉 時:分 */
    public static String convDateToHHMM(Date date) {
        if (date != null) {
            try {
                return new SimpleDateFormat(HH_MM_PATTERN).format(date);
            } catch (Exception e) {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * date to yyyy/MM/dd format
     *
     * @param date
     * @return yyyy/MM/dd
     */
    public static String convDateToStrWithHyphen(Date date) {
        String dateFormat = null;
        if (date != null) {
            try {
                dateFormat = new SimpleDateFormat(DEFAULT_PATTERN).format(date);
            } catch (Exception e) {
                LOG.error(e.toString());
            }
        }
        return dateFormat;
    }

    /**
     * date to yyyyMMdd format
     *
     * @param date
     * @return yyyyMMdd
     */
    public static String convDateToStrWithEmpty(Date date) {
        String dateFormat = null;
        if (date != null) {
            try {
                dateFormat = new SimpleDateFormat(YYYY_MM_DD_PATTERN).format(date);
            } catch (Exception e) {
                LOG.error(e.toString());
            }
        }
        return dateFormat;
    }

    /**
     * date to yyyy/MM/dd format
     *
     * @param date
     * @return yyyy/MM/dd
     */
    public static String convDateToStrWithSlash(Date date) {
        String dateFormat = null;
        if (date != null) {
            try {
                dateFormat = new SimpleDateFormat(YYYY_S_MM_S_DD_PATTERN).format(date);
            } catch (Exception e) {
                LOG.error(e.toString());
            }
        }
        return dateFormat;
    }

    /**
     * date to yyyy.MM.dd format
     *
     * @param date
     * @return yyyy.MM.dd
     */
    public static String convDateToStrWithDot(Date date) {
        String dateFormat = null;
        if (date != null) {
            try {
                dateFormat = new SimpleDateFormat(YYYY_D_MM_D_DD_PATTERN).format(date);
            } catch (Exception e) {
                LOG.error(e.toString());
            }
        }
        return dateFormat;
    }

    public static String numberFormat(Number obj, String format) {
        NumberFormat nf = new DecimalFormat(format);
        nf.setRoundingMode(RoundingMode.HALF_UP);
        if (obj != null) {
            return nf.format(obj);
        }
        return null;
    }

    /** add Day */
    public static Date addDay(Date d, int addDay) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.add(Calendar.DATE, addDay);
        return c.getTime();
    }

    /** add Month */
    public static Date addMonth(Date d, int addMonth) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.add(Calendar.MONTH, addMonth);
        return c.getTime();
    }

    /** 時分秒相加 */
    public static Date dateAdd(Date srcDate, int h, int m, int s) {
        Calendar cal = Calendar.getInstance();
        Date rtnDate = null;
        if (srcDate != null) {
            cal.setTime(srcDate);
            if (h != 0)
                cal.add(Calendar.HOUR_OF_DAY, h);
            if (m != 0)
                cal.add(Calendar.MINUTE, m);
            // if(s!=0)
            // cal.set(Calendar.SECOND,s);
            // cal.set(Calendar.MILLISECOND, 999);
            rtnDate = cal.getTime();
        }
        return rtnDate;
    }

    /** 時分秒相減 */
    public static Date dateLess(Date srcDate, int h, int m, int s) {
        Calendar cal = Calendar.getInstance();

        Date rtnDate = null;
        if (srcDate != null) {
            cal.setTime(srcDate);
            if (h != 0)
                cal.add(Calendar.HOUR_OF_DAY, -h);
            if (m != 0)
                cal.add(Calendar.MINUTE, -m);
            // if(s!=0)
            // cal.set(Calendar.SECOND,-s);
            // cal.set(Calendar.MILLISECOND, 999);
            rtnDate = cal.getTime();
        }
        return rtnDate;
    }

    /**
     * 檢查參數是否為空值,以及比對null等值字串
     * <p>
     * nil : null,"",...
     */
    public static boolean isNil(Object... args) {
        int len = args.length;
        if (args[0] != null && args[0].toString().length() > 0)
            for (int i = 1; i < len; i++) {
                // 對照組不為空 樣本與對照相符 所以是空
                if (args[i] != null && args[0].equals(args[i]))
                    return true;
            }
        return false;
    }

    /**
     * 如果物件1為空值則回傳物件2
     *
     * @param obj1
     * @param obj2
     *         不得為空值
     * @return
     */
    public static Object nilReturn(Object obj1, Object obj2) {
        try {
            return obj1 == null ? obj2 : obj1;
        } catch (NullPointerException e) {
            LOG.error("NullException!! Obj2 is Null!!");
            return null;
        }
    }

    /** String To Boolean */
    public static Boolean parseBoolean(String str) throws Exception {
        if (str == null) {
            return false;
        }
        if (str.equals("Y") || str.equals("y") || str.equals("Yes") || str.equals("yes") || str.equals("1"))
            return true;
        if (str.equals("N") || str.equals("n") || str.equals("No") || str.equals("no") || str.equals("0"))
            return false;
        throw new Exception("傳入參數不可轉布林值");
    }

    /** String To Boolean */
    public static Boolean parseBoolean(Character str) throws Exception {
        if (str == null) {
            return false;
        }
        if (str.equals('Y') || str.equals('y'))
            return true;
        if (str.equals('N') || str.equals('n'))
            return false;
        throw new Exception("傳入參數不可轉布林值");
    }

    /**
     * 快速判斷 Y y Yes YES N n No NO 成布林值
     *
     * @param s
     * @return
     */
    public static boolean transStrToBln(String s) {
        if (null == s) {
            throw new java.lang.NullPointerException("傳入的值為空");
        } else {
            char chr = s.charAt(0);
            if (chr == 'Y' || chr == 'y') {
                return true;
            } else if (chr == 'N' || chr == 'n') {
                return false;
            } else {
                throw new java.lang.IllegalArgumentException("傳入的值無法解析 可接受的第一個字元為Y/y/N/n");
            }
        }
    }

    /** String To Boolean */
    public static Boolean parse2Boolean(String str) {
        if (StringUtils.isBlank(str)) {
            return false;
        }
        String firstChar = str.toUpperCase().substring(0, 1);
        if (firstChar.equals("Y") || firstChar.equals("1")) {
            return true;
        } else {
            return false;
        }
    }

    /** strFormat */
    public static String strFormat(String args, String format) {
        return String.format(format, args);
    }

    /** LocalDate to Date */
    public static Date parseDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    /** LocalDateTime to Date */
    public static Date parseDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /** Date to LocalDate */
    public static LocalDate parseLocalDate(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    /** Date to LocalDateTime */
    public static LocalDateTime parseLocalDateTime(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    /** LocalDate to MonthDay */
    public static MonthDay parseMonthDay(LocalDate localDate) {
        return MonthDay.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    /** Date to MonthDay */
    public static MonthDay parseMonthDay(Date date) {
        return MonthDay.from(Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()));
    }

    /** List Clone */
    public static List<String> cloneList(List<String> oriList) {
        List<String> cloneList = new ArrayList<>(oriList.size());
        for (int i = 0; i < oriList.size(); i++) {
            cloneList.add(oriList.get(i));
        }
        return cloneList;
    }

    /**
     * 12:34 to (720 + 34) minutes
     *
     * @param time1
     * @return
     */
    public static int parseMinutes(String time1) {
        final String[] ary = StringUtils.isBlank(time1) ? null : time1.split(":");
        if (null == ary || ary.length != 2) {
            return 0;
        }
        final int[] num = {NumberUtils.toInt(ary[0]), NumberUtils.toInt(ary[1])};
        return (num[0] * 60) + num[1];
    }

    /**
     * @param date
     * @param year
     * @param month
     * @param day
     * @param hour
     * @param minute
     * @param second
     * @return
     */
    public static Calendar addYmdhms(final Date date, final int year, int month, int day, int hour, int minute,
                                     int second) {
        final Calendar cal = DateUtils.toCalendar(date);
        cal.set(Calendar.MILLISECOND, 0);
        if (year != 0) {
            cal.add(Calendar.YEAR, year);
        }
        if (month != 0) {
            cal.add(Calendar.MONTH, month);
        }
        if (day != 0) {
            cal.add(Calendar.DATE, day);
        }
        if (hour != 0) {
            cal.add(Calendar.HOUR_OF_DAY, hour);
        }
        if (minute != 0) {
            cal.add(Calendar.MINUTE, minute);
        }
        if (second != 0) {
            cal.add(Calendar.SECOND, second);
        }
        return cal;
    }

    /**
     * 空值判斷，若為空給DEFAULT 值
     *
     * @param i
     * @param defaultValue
     * @return
     */
    public static Integer nvl(Integer i, Integer defaultValue) {
        return null != i ? i : defaultValue;
    }

    /**
     * 空值判斷,若為空給-1
     *
     * @param i
     * @return
     */
    public static Integer nvl(Integer i) {
        return nvl(i, -1);
    }

    /**
     * 空值判斷，若為空給DEFAULT 值
     *
     * @param s
     * @param defaultValue
     * @return
     */
    public static String nvl(String s, String defaultValue) {
        return null != s ? s : defaultValue;
    }

    /**
     * 空值判斷,若為空給 ""
     *
     * @param s
     *         String
     * @return
     */
    public static String nvl(String s) {
        return nvl(s, "");
    }

    public static <T> T valueOf(final Map<String, Object> map, final String key, final Class<T> type) {
        if (null == map || map.size() == 0 || StringUtils.isBlank(key) || null == type) {
            return null;
        }
        final Object val = map.get(key);
        if (null != val && type.isInstance(val)) {
            return (T) val;
        }
        return null;
    }

    /**
     * Date轉LocalDate
     */
    public static LocalDate date2LocalDate(Date date) {
        if (date == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return LocalDate.of(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH));
    }

    /**
     * 取得系統日期(Date)
     */
    public static Date getCurrentDate() {
        return new Date();
    }

    /**
     * 取得系統日期(cal)
     */
    public static Calendar getCurrentDt() {
        return Calendar.getInstance();
    }

    /** 設定為時分秒00:00:00:000 */
    public static Date getMinDate(Date srcDate) {
        return truncDate(srcDate);
    }

    /** 設定為時分秒23:59:59:999 */
    public static Date getMaxDate(Date srcDate) {
        return truncEndDate(srcDate);
    }

    /** 日期轉字串 */
    public static String date2Str(Date srcDate, String format) {
        return convDateToStr(srcDate, format);
    }

    /** add Day */
    public static Date calcDate(Date d, int addDay) {
        return addDay(d, addDay);
    }

    /** clone bean */
    public static Object cloneBean(Object source) {
        Object target = null;

        try {
            if (source instanceof Serializable) {
                // deep clone
                target = SerializationUtils.clone((Serializable) source);
            } else {
                // shadow clone
                target = BeanUtils.cloneBean(source);
            }
        } catch (Exception e) {
            LOG.error(e.toString());
            target = source;
        }
        return target;
    }

    /** copy properties */
    public static void copyProperties(final Object dest, final Object orig)
            throws IllegalAccessException, InvocationTargetException {
        BeanUtils.copyProperties(dest, orig);
    }

    /** pad n char in left */
    public static String leftPad(String str, int size, char padChar) {
        return StringUtils.leftPad(str, size, padChar);
    }

    /** pad n char in right */
    public static String rightPad(String str, int size, char padChar) {
        return StringUtils.rightPad(str, size, padChar);
    }

    /**
     * 遮蔽字串只留前 n 個字元及後 n 個字元。
     *
     * @param str
     *         String
     * @param first
     *         int
     * @param last
     *         int
     * @return String
     */
    public static String maskString(String str, int first, int last) {
        if (str != null && str.trim().length() > 0) {
            char[] charArray = str.trim().toCharArray();
            int len = charArray.length;
            int begin = (len > first) ? first : len;
            int end = (len > first + last) ? len - last : begin;
            for (int i = begin; i < end; i++) {
                charArray[i] = '*';
            }
            return new String(charArray);
        } else {
            return str;
        }
    }

    public static int getAge(LocalDate birthDate) {
        if (birthDate != null) {
            return Period.between(birthDate, LocalDate.now()).getYears();
        } else {
            return 0;
        }
    }

    public static boolean isDifferent(LocalDateTime ldt1, LocalDateTime ldt2, ChronoUnit... chronoUnits) {
        for (ChronoUnit chronoUnit : chronoUnits) {
            if (chronoUnit.between(ldt1, ldt2) != 0) {
                return true;
            }
        }
        return false;
    }

    public static Date calcTimes(final Date date, final int year, int month, int day, int hour, int minute,
                                 int second) {
        final Calendar cal = DateUtils.toCalendar(date);
        cal.set(Calendar.MILLISECOND, 0);
        if (year != 0) {
            cal.add(Calendar.YEAR, year);
        }
        if (month != 0) {
            cal.add(Calendar.MONTH, month);
        }
        if (day != 0) {
            cal.add(Calendar.DATE, day);
        }
        if (hour != 0) {
            cal.add(Calendar.HOUR_OF_DAY, hour);
        }
        if (minute != 0) {
            cal.add(Calendar.MINUTE, minute);
        }
        if (second != 0) {
            cal.add(Calendar.SECOND, second);
        }
        return cal.getTime();
    }

    /** 計算百分比 */
    public static Double calPercent(Double a, Double b){
        if(a == null || b == null || a <= 0 || b <= 0){
            return 0D;
        }
        return (b / a) * 100;
    }

    public static void printProcessDuration(String message,LocalDateTime startTime){
        LOG.info("{} : Cost {} ms",message,calProcessDuration(startTime));
    }

    public static long calProcessDuration(LocalDateTime startTime){
        return Duration.between(startTime,LocalDateTime.now()).toMillis();
    }

    public static long calProcessDuration(LocalDateTime startTime, LocalDateTime endTime){
        return Duration.between(startTime,endTime).toMillis();
    }
}
