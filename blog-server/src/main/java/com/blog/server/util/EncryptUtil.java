package com.blog.server.util;


import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class EncryptUtil {

    public static String encryptPassword(String password) {
        return Arrays.toString(DigestUtils.md5Digest(password.getBytes(StandardCharsets.UTF_8)));
    }
}
