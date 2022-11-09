package com.example.instazoo.security;

import java.security.SecureRandom;
import java.util.Base64;

public class SecurityConstant {
    public static final String SIGN_UP_URLS = "/api/auth/**";
    public static final String SECRET = generateSecretWord();
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String CONTENT_TYPE = "application/json";
    public static final long EXPIRATION_TIME= 600_000; //10 minutes

    /**
     * Generated random secret word
     * @return random secret word
     */
    private static String generateSecretWord() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[36];

        random.nextBytes(bytes);
        var encoder = Base64.getEncoder().withoutPadding();
        return encoder.encodeToString(bytes);
    }

}
