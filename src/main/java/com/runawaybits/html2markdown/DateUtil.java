package com.runawaybits.html2markdown;

import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

/**
 * DateUtil
 *
 * @author robin
 * @author refactor by Sevan Joe
 */
public class DateUtil {

    public DateUtil() {

    }

    /**
     * return current date value in format: yyyy-MM-dd
     *
     * @return String value
     */
    public static String getNowDate() {
        return dateToStringWithPattern(new Date(), "yyyy-MM-dd");
    }

    /**
     * return current time value in format: yyyy-MM-dd HH:mm:ss:sss
     *
     * @return String value
     */
    public static String getNowTime() {
        return dateToStringWithPattern(new Date(), "yyyy-MM-dd HH:mm:ss:sss");
    }

    /**
     * return time value of specified date in format: yyyy-MM-dd HH:mm
     *
     * @param date the specified date to convert
     * @return String value
     */
    public static String dateToString(Date date) {
        return dateToStringWithPattern(date, "yyyy-MM-dd HH:mm");
    }

    /**
     * return date value only of specified date in format: yyyy-MM-dd
     *
     * @param date the specified date to convert
     * @return String value
     */
    public static String dateToShortString(Date date) {
        return dateToStringWithPattern(date, "yyyy-MM-dd");
    }

    /**
     * return time value of specified date in format: yyyy-MM-dd HH:mm:ss
     *
     * @param date the specified date to convert
     * @return String value
     */
    public static String dateToLongString(Date date) {
        return dateToStringWithPattern(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * return time value only of specified date in format: HH:mm:ss
     *
     * @param date the specified date to convert
     * @return String value
     */
    public static String dateToTimeString(Date date) {
        return dateToStringWithPattern(date, "HH:mm:ss");
    }

    /**
     * return time value of specified date
     *
     * @param date    the specified date to convert
     * @param pattern time format
     * @return String value
     */
    public static String dateToStringWithPattern(Date date, String pattern) {
        try {
            SimpleDateFormat simpledateformat = new SimpleDateFormat(pattern);
            return simpledateformat.format(date);
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * split date value of specified date by '-'
     *
     * @param date the specified date to convert
     * @return String[] value
     */
    public static String[] SplitDate(Date date) {
        String s = dateToShortString(date);
        String[] temp = new String[3];
        StringTokenizer st = new StringTokenizer(s, "-");
        int i = 0;
        while (st.hasMoreTokens()) {
            temp[i] = st.nextToken();
            i++;
        }
        return temp;
    }

    /**
     * return string value of specified date in format: yyyy-MM-ddTHH:mm:ss
     *
     * @param date the specified date to convert
     * @return String value
     */
    public static String dateToBOMCStringDate(Date date) {
        try {
            SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String string = simpledateformat.format(date);
            string = StringToBOMCStringDate(string);
            return string;
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * return handled string value of date
     *
     * @param date string value to convert
     * @return String value
     */
    public static String StringToBOMCStringDate(String date) {
        return date.replace(" ", "T");
    }

    /**
     * return date value of specified string value in format: yyyy-MM-dd HH:mm:ss
     *
     * @param string string value to convert
     * @return Date value
     */
    public static Date stringToDate(String string) {
        try {
            SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return simpledateformat.parse(string);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * return date value of specified string value in format: HH:mm:ss
     *
     * @param string string value to convert
     * @return Date value
     */
    public static Date timeStringToDate(String string) {
        try {
            SimpleDateFormat simpledateformat = new SimpleDateFormat("HH:mm:ss");
            return simpledateformat.parse(string);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * return date value of specified string value in format: yyyy-MM-dd
     *
     * @param string string value to convert
     * @return Date value
     */
    public static Date stringToShortDate(String string) {
        try {
            SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy-MM-dd");
            ParsePosition parseposition = new ParsePosition(0);
            return simpledateformat.parse(string, parseposition);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * return date value of specified string value in format: yyyyMMdd
     *
     * @param string string value to convert
     * @return Date value
     */
    public static Date stringToShortNoDate(String string) {
        try {
            SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyyMMdd");
            ParsePosition parseposition = new ParsePosition(0);
            return simpledateformat.parse(string, parseposition);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * return date value of now
     *
     * @return Date value
     */
    public static Date getNow() {
        return new Date();
    }

    /**
     * return unix timestamp of now
     *
     * @return long value
     */
    public static long getCurrentTimestamp() {
        return (new Date()).getTime();
    }

    /**
     * return unix timestamp of specified string value in format: yyyy-MM-dd
     *
     * @param string string value to convert
     * @return long value
     */
    public static long getTimestamp(String string) {
        try {
            SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy-MM-dd");
            ParsePosition parseposition = new ParsePosition(0);
            Date date = simpledateformat.parse(string, parseposition);
            return date.getTime();
        } catch (Exception e) {
            return -1;
        }
    }

    /**
     * return unix timestamp of specified string value in format: yyyy-MM-dd HH:mm:ss
     *
     * @param string string value to convert
     * @return long value
     */
    public static long getStringToTimestamp(String string) {
        try {
            SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            ParsePosition parseposition = new ParsePosition(0);
            Date date = simpledateformat.parse(string, parseposition);
            return date.getTime();
        } catch (Exception e) {
            return -1;
        }
    }

    /**
     * return the time difference from a specified time to now in minutes
     *
     * @param timestamp unix timestamp of a specified time
     * @return long value
     */
    public static long getOffMinutes(long timestamp) {
        return getOffMinutes(timestamp, System.currentTimeMillis());
    }

    /**
     * return the time difference from two specified time
     *
     * @param left  unix timestamp of the first specified time
     * @param right unix timestamp of the second specified time
     * @return long value
     */
    public static long getOffMinutes(long left, long right) {
        return (left - right) / 60000L;
    }

    /**
     * return string value of specified unix timestamp
     *
     * @param timestamp unix timestamp
     * @return String value
     */
    public static String LongToDateString(long timestamp) {
        DateFormat mediumDateFormat;
        Date sDate;
        try {
            mediumDateFormat = DateFormat.getDateTimeInstance();
            String date = String.valueOf(timestamp);
            sDate = new Date(Long.parseLong(date));
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        return mediumDateFormat.format(sDate);
    }

    /**
     * return date's weekday value of specified string value in format: yyyy-MM-dd
     * Date first = DateUtil.getMonday(today,Calendar.SUNDAY);
     * Date last = DateUtil.getMonday(today,Calendar.SATURDAY);
     *
     * @param date    String value of date
     * @param weekDay index of weekday to get, first Calendar.SUNDAY, last Calendar.SATURDAY
     * @return Date value
     */
    public static Date getWeekDay(String date, int weekDay) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date d = null;
        try {
            d = format.parse(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        if (d != null) {
            cal.setTime(d);
        }
        // DAY_OF_WEEK
        // Field number for get and set indicating the day of the week. This field takes values
        // SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, and SATURDAY
        cal.set(Calendar.DAY_OF_WEEK, weekDay);
        cal.add(Calendar.DATE, 1);
        return cal.getTime();
    }

    /**
     * return the first day of the date's month of specified string value in format: yyyy-MM
     *
     * @param date String value of date
     * @return Date value
     */
    public static Date getMonthFirstDay(String date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        Date d = null;
        try {
            d = format.parse(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        if (d != null) {
            cal.setTime(d);
        }
        cal.add(Calendar.DAY_OF_MONTH, 0);
        return cal.getTime();
    }

    /**
     * return the last day of the date's month of specified string value in format: yyyy-MM
     *
     * @param date String value of date
     * @return Date value
     */
    public static Date getMonthLastDay(String date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        Date d = null;
        try {
            d = format.parse(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        if (d != null) {
            cal.setTime(d);
        }
        cal.add(Calendar.MONTH, 1);
        cal.add(Calendar.DATE, -1);
        return cal.getTime();
    }

    /**
     * return the first day of the date's year of specified string value in format: yyyy
     *
     * @param date String value of date
     * @return Date value
     */
    public static Date getYearFirstDay(String date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy");
        Date d = null;
        try {
            d = format.parse(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        if (d != null) {
            cal.setTime(d);
        }
        cal.add(Calendar.DAY_OF_YEAR, 0);
        return cal.getTime();
    }

    /**
     * return the last day of the date's year of specified string value in format: yyyy
     *
     * @param date String value
     * @return Date value
     */
    public static Date getYearLastDay(String date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy");
        Date d = null;
        try {
            d = format.parse(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        if (d != null) {
            cal.setTime(d);
        }
        cal.add(Calendar.YEAR, 1);
        cal.add(Calendar.DATE, -1);
        return cal.getTime();
    }

    /**
     * return date value with specified field value
     *
     * @param date   Date value
     * @param field  int Date filed, such as Calendar.DAY_OF_MONTH
     * @param amount int the value of the field to set
     * @return Date value
     */
    public static Date getDate(Date date, int field, int amount) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(field, amount);
        return cal.getTime();
    }
}