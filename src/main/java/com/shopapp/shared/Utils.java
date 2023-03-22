package com.shopapp.shared;

import com.shopapp.security.SecurityConstants;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.time.StopWatch;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Date;
import java.util.Random;

import static java.lang.Thread.sleep;

@Service
public class Utils {

    public static StopWatch stopWatch = new StopWatch();
    private final Random RANDOM = new SecureRandom();
    private final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private final int defaultIdLength = 8;

    public static void delay(long delayMilliSeconds) {
        try {
            sleep(delayMilliSeconds);
        } catch (Exception e) {
            System.out.println("Exception is :" + e.getMessage());
        }

    }

    public static boolean hasTokenExpired(String token) {
        var returnValue = false;

        try {
            var claims = Jwts.parserBuilder()
                    .setSigningKey(SecurityConstants.getTokenSecret().getBytes())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            var tokenExpirationDate = claims.getExpiration();
            var todayDate = new Date();

            returnValue = tokenExpirationDate.before(todayDate);
        } catch (ExpiredJwtException ex) {
            returnValue = true;
        }

        return returnValue;
    }

    public static int noOfCores() {
        return Runtime.getRuntime().availableProcessors();
    }

    public static void startTimer() {
        stopWatchReset();
        stopWatch.start();
    }

    public static void stopWatchReset() {
        stopWatch.reset();
    }

    public static void timeTaken() {
        stopWatch.stop();
        System.out.println("Total Time Taken : " + stopWatch.getTime());
    }

    public static String transForm(String s) {
        Utils.delay(500);
        return s.toUpperCase();
    }

    public String generateEmailVerificationToken(String userEmail) {
        var token = Jwts.builder()
                .setSubject(userEmail)
                .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.getTokenSecret().getBytes())
                .compact();
        return token;
    }

    public String generateId(int length) {
        return generateRandomString(length);
    }

    public String generateId() {
        return generateRandomString(defaultIdLength);
    }

    public String generatePasswordResetToken(String userId) {
        var token = Jwts.builder()
                .setSubject(userId)
                .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.getTokenSecret().getBytes())
                .compact();
        return token;
    }

    private String generateRandomString(int length) {
        StringBuilder returnValue = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            returnValue.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }

        return new String(returnValue);
    }

    public String generateUserId(int length) {
        return generateRandomString(length);
    }
}
