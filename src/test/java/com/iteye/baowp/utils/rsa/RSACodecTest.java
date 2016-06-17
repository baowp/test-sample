package com.iteye.baowp.utils.rsa;

import org.apache.commons.codec.binary.Base64;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;


/**
 * Created by Administrator on 2016/6/7.
 */
public class RSACodecTest {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Test
    public void testPublicEncryptPrivateDecrypt() throws Exception {
        String originalText = "北京beijing";
        RSAPublicKey publicKey = RSAPublicTool.loadPublicKey();
        byte[] bytes = RSAPublicTool.encrypt(publicKey, originalText.getBytes());
        String encryptString = Base64.encodeBase64String(bytes);
        logger.info("encrypt string is {}", encryptString);

        RSAPrivateKey privateKey = RSAPrivateTool.loadPrivateKey();
        bytes = RSAPrivateTool.decrypt(privateKey, Base64.decodeBase64(encryptString));
        String decryptString = new String(bytes);
        logger.info("decrypt string is {}", decryptString);
    }

    @Test
    public void testPrivateSignPublicCheck() throws Exception {
        String originalText = "北京beijing";
        String signedString = RSAPrivateTool.sign(originalText, RSAPrivateTool.getPrivateKey());
        logger.info("signed string is {}", signedString);

        boolean valid = RSAPublicTool.doCheck(originalText, signedString, RSAPublicTool.getPublicKey());
        logger.info("RSAPublic checked is {}", valid);
    }

    @Test
    public void testPublicEncryptPrivateDecryptUsingJks() throws Exception {
        InputStream ins = getClass().getResourceAsStream("keystore.jks");
        String originalText = "北京beijing";
        RSAPublicKey publicKey = (RSAPublicKey) RSAPublicTool.loadPublicKeyFromJks(ins,"dafy.com", "mykey");
        byte[] bytes = RSAPublicTool.encrypt(publicKey, originalText.getBytes());
        String encryptString = Base64.encodeBase64String(bytes);
        logger.info("encrypt string is {}", encryptString);

        RSAPrivateKey privateKey = (RSAPrivateKey) RSAPrivateTool.loadPrivateKeyFromJks(getClass().getResourceAsStream("keystore.jks"), "dafy.com", "mykey", "dafy.com");
        bytes = RSAPrivateTool.decrypt(privateKey, Base64.decodeBase64(encryptString));
        String decryptString = new String(bytes);
        logger.info("decrypt string is {}", decryptString);
    }

    @Test
    public void testPrivateSignPublicCheckUsingJks() throws Exception {
        String originalText = "北京beijing";
        RSAPrivateKey privateKey = (RSAPrivateKey) RSAPrivateTool.loadPrivateKeyFromJks(getClass().getResourceAsStream("keystore.jks"), "dafy.com", "mykey", "dafy.com");
        String signedString = RSAPrivateTool.sign(originalText,privateKey);
        logger.info("signed string is {}", signedString);

        RSAPublicKey publicKey = (RSAPublicKey) RSAPublicTool.loadPublicKeyFromJks(getClass().getResourceAsStream("keystore.jks"),"dafy.com", "mykey");
        boolean valid = RSAPublicTool.doCheck(originalText, signedString, publicKey);
        logger.info("RSAPublic checked is {}", valid);
    }
}
