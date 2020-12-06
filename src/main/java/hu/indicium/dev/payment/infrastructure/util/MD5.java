package hu.indicium.dev.payment.infrastructure.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {

    private final Logger logger = LoggerFactory.getLogger(MD5.class);

    public String hash(String string) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] digest = messageDigest.digest(string.getBytes());
            return DatatypeConverter.printHexBinary(digest);
        } catch (NoSuchAlgorithmException e) {
            logger.error(e.getMessage());
            return "";
        }
    }
}
