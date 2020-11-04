package com.anz.accounts;

import org.junit.jupiter.api.Test;

import java.security.*;

public class JwtUtilTest {

    @Test
    public void testGenerateJwtWithRsa() throws Exception {
        String token = JwtUtil.generateJwtToken();
        System.out.println("TOKEN:" + token);
        JwtUtil.decodeToken(token);
    }

    @Test
    public void testGenerateKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyGenerator = KeyPairGenerator.getInstance("RSA");
        keyGenerator.initialize(1024);

        KeyPair kp = keyGenerator.genKeyPair();
        PublicKey publicKey = (PublicKey) kp.getPublic();
        PrivateKey privateKey = (PrivateKey) kp.getPrivate();

        String encodedPublicKey = java.util.Base64.getEncoder().encodeToString(publicKey.getEncoded());
        String encodedPrivateKey = java.util.Base64.getEncoder().encodeToString(privateKey.getEncoded());
        System.out.println("Private Key : " + encodedPrivateKey);
        System.out.println("Public Key : " + encodedPublicKey);
    }

}
