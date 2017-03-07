package com.iteye.baowp.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by baowp on 2016/9/8.
 */
public class DigestUtilsTest {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Test
    public void testMd5Hex(){
        String encryption= DigestUtils.md5Hex("dfb-deploy");
        logger.info(encryption);
    }
}
