package com.iteye.baowp.utils;

import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: baowp
 * Date: 1/2/14
 * Time: 11:45 AM
 */
public class DESedeSecretUtilsTest {

    @Test
    public void test() {
        String msg = "3DES加密解密案例";
        System.out.println("【加密前】：" + msg);

        //加密
        byte[] secretArr = DESedeSecretUtils.encryptMode(msg.getBytes());
        System.out.println("【加密后】：" + new String(secretArr));

        //解密
        byte[] myMsgArr = DESedeSecretUtils.decryptMode(secretArr);
        System.out.println("【解密后】：" + new String(myMsgArr));
    }

    @Test
    public void testDecode() {
        DESedeSecretUtils.PASSWORD_CRYPT_KEY = "fx8NzklezWeb4/fCKTGe6SZDaHwLZ573";
        String encodeStr = "0D+MWGnqTrZhYZeW7J7tca+WeVcF1Yme0wKmNIxuF3iXRgTbIgWG5Wn1HiaoO7o9Pglqcr7iB+Hh5vvQW+hANpU19dclAQBYg74sTn7RjzwgyC1r4k8wJGICDD1FMC861dgATYCi8r6ml/KyZTSMZWICDD1FMC86Cz+vsV2Cz1inHNmHP/qf1bzeRviQbMSSPaV52XTnqCE6kWExy01IgohoV0awzIJ1YgIMPUUwLzplegwo0fCy61GE4VujCe63zsVUUSS/WnGk9zdkEa5REAMTBsa0ioaCyVGAcjQy46X5OB2mxaEIjUjaZKtndQdDppfysmU0jGViAgw9RTAvOiAi11i65rWWDd/dgOW82MJ//bqdRJzDLbfXE+6weC4WRB86RrTzlqPQW2zfZs0Li0+jJQl0GsM9jblQwHMQBiGRvvVbTi5gsooFMdCinY0qgVl+MwntAYPcddWH9DLtm0QfOka085ajtTbfT/il6uJ99OkQAf+v10QfOka085aj5q3FUKm0cPhkhfyL+1nY/pONI2h+kKERRB86RrTzlqP5tXwvpq8jrJOAkXzxt2bWFLYB7LA8xEp/HDDpmtA++bQjcPi6iUgEJ8H2bCVoAVbttM2fXIgoeW5B5hMjfaSsmV9UenxrCAzP3k/L85yWaQ==";
        byte[] myMsgArr = DESedeSecretUtils.decryptMode(encodeStr.getBytes());
        System.out.println("【解密后】：\n" + new String(myMsgArr));
    }

    @Test
    public void testOther() {
        String str = "asdklljdllfldl";
        int i = str.indexOf("d");
        System.out.println(i);
        i = str.lastIndexOf("d");
        System.out.println(i);
    }
}
