package com.ty.wq.utils;

import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * @program: ty-qdd
 * @description: 日期工具类
 * @author: qiao
 * @create: 2020/10/18 13:09
 */
public class DateUtil {
  /**
   * 获取当前时间的0点毫秒值
   *
   * @return
   */
  public static long getTodayZero() {
    long current = System.currentTimeMillis();
    long zero = current - (current + TimeZone.getDefault().getRawOffset()) % (1000 * 3600 * 24);
    return zero;
  }

  /**
   * 根据日期字符串转换成calendar对象
   *
   * @param date
   * @param format
   * @return
   * @throws ParseException
   */
  public static Calendar parseDateToCalendar(String date, String format) throws ParseException {
    SimpleDateFormat sdf = new SimpleDateFormat(format);
    Date d = sdf.parse(date);
    return new Calendar.Builder().setInstant(d).build();
  }

  /**
   * 日期格式的字符串（yyyy-MM-dd or yyyy-MM-dd HH:mm:ss）转化成时间戳
   *
   * @param date
   * @return
   * @throws ParseException
   */
  public static long parseDateToLong(String date) throws ParseException {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date d = null;
    if (date.contains(":")) {
      d = sdf1.parse(date);
    } else {
      d = sdf.parse(date);
    }
    return d.getTime();
  }

  /**
   * 时间格式的字符串（HH:mm:ss）转化成时间戳
   *
   * @param time
   * @return
   * @throws ParseException
   */
  public static long parseTimeToLong(String time) throws ParseException {
    String date = "1970-01-01 " + time;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date d = sdf.parse(date);
    int offset = Calendar.getInstance().get(Calendar.ZONE_OFFSET);
    return d.getTime() + offset;
  }

  /**
   * 根据时间戳转换成日期格式
   *
   * @param timestamp
   * @param format
   * @return
   */
  public static String parseLongToDate(long timestamp, String format) {
    if (StringUtils.isEmpty(format)) {
      format = "yyyy-MM-dd HH:mm:ss";
    }
    int offset = TimeZone.getDefault().getRawOffset();
    System.out.println(offset);
    SimpleDateFormat sdf = new SimpleDateFormat(format);
    return sdf.format(new Date(timestamp - offset));
  }

  /**
   * Description: 本地时间转化为UTC时间
   *
   * @param localTime
   * @return
   */
  public static Date localToUTC(String localTime) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date localDate = null;
    try {
      localDate = sdf.parse(localTime);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return localToUTC(localDate.getTime());
  }

  /**
   * Description: 本地时间转化为UTC时间
   *
   * @param localTimeInMillis
   * @return
   */
  public static Date localToUTC(long localTimeInMillis) {
    /** long时间转换成Calendar */
    Calendar calendar = Calendar.getInstance();
    calendar.setTimeInMillis(localTimeInMillis);
    /** 取得时间偏移量 */
    int zoneOffset = calendar.get(Calendar.ZONE_OFFSET);
    /** 取得夏令时差 */
    int dstOffset = calendar.get(Calendar.DST_OFFSET);
    /** 从本地时间里扣除这些差量，即可以取得UTC时间 */
    calendar.add(Calendar.MILLISECOND, -(zoneOffset + dstOffset));
    /** 取得的时间就是UTC标准时间 */
    Date utcDate = new Date(calendar.getTimeInMillis());
    return utcDate;
  }

  /**
   * Description:UTC时间转化为本地时间
   *
   * @param utcTime
   * @return
   */
  public static Date utcToLocal(String utcTime) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
    Date utcDate = null;
    try {
      utcDate = sdf.parse(utcTime);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    sdf.setTimeZone(TimeZone.getDefault());
    Date locatlDate = null;
    String localTime = sdf.format(utcDate.getTime());
    try {
      locatlDate = sdf.parse(localTime);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return locatlDate;
  }

  /**
   * Description:UTC时间转化为本地时间
   *
   * @param utcTime
   * @return
   */
  public static Date utcToLocal(long utcTime) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
    sdf.setTimeZone(TimeZone.getDefault());
    Date locatlDate = null;
    String localTime = sdf.format(utcTime);
    try {
      locatlDate = sdf.parse(localTime);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return locatlDate;
  }

  /**
   * utc时间转化成北京时间
   *
   * @param utcStr
   * @return
   */
  public static long utcToCST(long utcStr) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTimeInMillis(utcStr);
    calendar.set(Calendar.HOUR, calendar.get(Calendar.HOUR) + 8);
    return calendar.getTimeInMillis();
  }

  public static int getTimeZone() {
    Calendar calendar = Calendar.getInstance();
    int offset = calendar.get(Calendar.ZONE_OFFSET);
    calendar.add(Calendar.MILLISECOND, -offset);
    Long timeStampUTC = calendar.getTimeInMillis();
    Long timeStamp = new Date().getTime();
    Long timeZone = (timeStamp - timeStampUTC) / (1000 * 3600);
    return timeZone.intValue();
  }

  /**
   * java8(经测试别的版本获取2月有bug) 获取某月第一天的00:00:00
   *
   * @return
   */
  public static String getFirstDayOfMonth(String dateStr) {
    if (StringUtils.isEmpty(dateStr)) return null;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date date = strToDateNotDD(dateStr);
    LocalDateTime localDateTime =
        LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault());
    ;
    LocalDateTime endOfDay =
        localDateTime.with(TemporalAdjusters.firstDayOfMonth()).with(LocalTime.MIN);
    Date dates = Date.from(endOfDay.atZone(ZoneId.systemDefault()).toInstant());
    return sdf.format(dates);
  }

  /**
   * java8(别的版本获取2月有bug) 获取某月最后一天的23:59:59
   *
   * @return
   */
  public static String getLastDayOfMonth(String dateStr) {
    if (StringUtils.isEmpty(dateStr)) return null;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date date = strToDateNotDD(dateStr);
    LocalDateTime localDateTime =
        LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault());
    ;
    LocalDateTime endOfDay =
        localDateTime.with(TemporalAdjusters.lastDayOfMonth()).with(LocalTime.MAX);
    Date dates = Date.from(endOfDay.atZone(ZoneId.systemDefault()).toInstant());
    return sdf.format(dates);
  }

  /**
   * 将短时间格式字符串转换为时间 yyyy-MM( 2020-02)
   *
   * @param strDate
   * @return
   */
  public static Date strToDateNotDD(String strDate) {
    if (StringUtils.isEmpty(strDate)) return null;
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
    ParsePosition pos = new ParsePosition(0);
    Date strtodate = formatter.parse(strDate, pos);
    return strtodate;
  }

  public static void main(String[] args) {
    // String time = "2019-03-15 10:17:00";
    Date utcTime = DateUtil.localToUTC(System.currentTimeMillis());
    long cstTime = DateUtil.utcToCST(utcTime.getTime());
    Timestamp timestamp = new Timestamp(cstTime);
    System.out.println(timestamp.toString());
  }
}
