package com.iteye.baowp.domain;

import com.iteye.baowp.domain.entity.BookEntity;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: baowp
 * Date: 12/3/13
 * Time: 8:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class CommonTest {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Test
    public void testCalendar() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, -4);
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String s = f.format(calendar.getTime());
        logger.info(s);
        logger.info(calendar.get(Calendar.MILLISECOND) + "");
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        s = f.format(calendar.getTime());
        logger.info(s);

        calendar.set(2013, 11, 5, 13, 0);
        s = f.format(calendar.getTime());
        logger.info(s);
    }

    @Test
    public void testHashCode() {
        Object obj1 = new BookEntity();
        Object obj2 = new BookEntity();
        System.out.println(obj1.hashCode());
        System.out.println(obj2.hashCode());
        System.out.println(obj1.hashCode());
        System.out.println(obj2.hashCode());

        for (int i = 37; i < 127; i++) {
            String str = new String(new char[]{(char) i, (char) (i + 1)});
            logger.info("{} hashCode:{}", str, str.hashCode());
        }

    }

    @Test
    public void test() {
        Map map = new HashMap(0);
        map.put("a", "a");
    }
}
