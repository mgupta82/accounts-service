package com.anz.accounts;

import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.tomcat.util.codec.binary.Base64;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.UUID;

public class JwtUtil {

    public static String generateJwtToken() throws Exception {
        RSAPrivateKey rsaPrivateKey = JwtUtil.readPrivateKey(new File(com.anz.accounts.JwtUtilTest.class.getClassLoader().getResource("privatekey.pem").getFile()));
        Calendar date = new GregorianCalendar();
        date.add(Calendar.DAY_OF_MONTH, 1);

        String token = Jwts.builder().setSubject("test")
                .setExpiration(date.getTime())
                .setIssuer("http://localhost:8089/auth/realms/test")
                .setIssuedAt(new Date())
                .setAudience("postcode")
                .setHeaderParam("kid","c/e6pHOPY+cmnSHXsJ5QtQctyqM=")
                .setHeaderParam("typ","JWT")
                .setId(UUID.randomUUID().toString())
                .claim("groups", new String[] { "user", "admin" })
                .claim("typ","Bearer")
                .claim("azp","postcode-client")
                .claim("scope","profile")
                .signWith(SignatureAlgorithm.RS256, rsaPrivateKey).compact();
        return token;
    }

    public static void decodeToken(String token) throws Exception{
        RSAPublicKey rsaPublicKey = JwtUtil.readPublicKey(new File(com.anz.accounts.JwtUtilTest.class.getClassLoader().getResource("publickey.pem").getFile()));
        Jws

                parseClaimsJws = Jwts.parser().setSigningKey(rsaPublicKey)
                .parseClaimsJws(token);

        System.out.println("Header     : " + parseClaimsJws.getHeader());
        System.out.println("Body       : " + parseClaimsJws.getBody());
        System.out.println("Signature  : " + parseClaimsJws.getSignature());
    }

    private static RSAPublicKey readPublicKey(File file) throws Exception {
        String key = new String(Files.readAllBytes(file.toPath()), Charset.defaultCharset());

        String publicKeyPEM = key
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replaceAll(System.lineSeparator(), "")
                .replace("-----END PUBLIC KEY-----", "");

        byte[] encoded = Base64.decodeBase64(publicKeyPEM);

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(encoded);
        return (RSAPublicKey) keyFactory.generatePublic(keySpec);
    }

    private static RSAPrivateKey readPrivateKey(File file) throws Exception {
        String key = new String(Files.readAllBytes(file.toPath()), Charset.defaultCharset());

        String privateKeyPEM = key
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replaceAll(System.lineSeparator(), "")
                .replace("-----END PRIVATE KEY-----", "");

        byte[] encoded = Base64.decodeBase64(privateKeyPEM);

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);
        return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
    }
}
