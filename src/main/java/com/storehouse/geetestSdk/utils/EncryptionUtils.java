package com.storehouse.geetestSdk.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;


public class EncryptionUtils {

    public static String md5_encode(String value) {
        return DigestUtils.md5Hex(value);
    }

    public static String sha256_encode(String value) {
        return DigestUtils.sha256Hex(value);
    }

    public static String hmac_sha256_encode(String value, String key) {
        return new HmacUtils(HmacAlgorithms.HMAC_SHA_256, key).hmacHex(value);
    }
}
