package com.demente.ideas.learnwords.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author 1987diegog
 */
public class DateUtil {

    /**
     * Returns a given date by parameters with the hour set in 00:00:00.
     *
     * @param date
     * @return
     */
    public static Date getDateFrom(Date date) {
		return setHourToDate(date, 0,0,0,0);
    }

    /**
     * Returns a given date by parameters with the hour set in 23:59:59.
     *
     * @param date
     * @return
     */
    public static Date getDateTo(Date date) {
		return setHourToDate(date, 23,59,59,999);
    }

	/**
	 * @param date
	 * @param hour
	 * @param minute
	 * @param second
	 * @param millisecond
	 * @return
	 */
    private static Date setHourToDate(Date date, int hour, int minute, int second, int millisecond) {

        Calendar c = new GregorianCalendar();
		c.setTime(date);

        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, second);
        c.set(Calendar.MILLISECOND, millisecond);

        return c.getTime();
    }
}
