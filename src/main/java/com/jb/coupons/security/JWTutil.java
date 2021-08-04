package com.jb.coupons.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.jb.coupons.beans.ClientType;
import com.jb.coupons.beans.UserDetails;
import io.jsonwebtoken.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class JWTutil {

    //encryption type
    private static String signatureAlgorithm = SignatureAlgorithm.HS256.getJcaName();

    //PK secret -> something to give as input to a function that creates a PK from it
    private static String encodedSecretKey = "this+is+a+private+key+secret+at+least+256+bits+long";

    //turn the secret into a private key
    private static Key decodedSecretKey = new SecretKeySpec(Base64.getDecoder().decode(encodedSecretKey), signatureAlgorithm);

    //generate a key
    public static String generateToken(UserDetails userDetails){
        Map<String, Object> claims = new HashMap<>();
//        claims.put("userId", userDetails.id);
        claims.put("clientType", userDetails.clientType);
        String createdToken = createToken(claims, userDetails.userEmail);
        System.out.println("Created token:" + createdToken);
        return createdToken;
    }

    //email helps us determine which client type this is, as we can check it in the database
    static String createToken(Map<String, Object> claims, String subject/*email*/) {

        //the timestamp of this operation. we want to limit the lifetime of a token
        Instant now = Instant.now();

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(ZonedDateTime.now().plusMinutes(30).toInstant()))
                .signWith(decodedSecretKey)
                .compact();
    }

    //extract all our data
    private static Claims extractAllClaims(String token) throws ExpiredJwtException{
        JwtParser jwtParser = Jwts.parserBuilder().setSigningKey(decodedSecretKey).build();
        return jwtParser.parseClaimsJws(token).getBody();
        //jws=json web string, jwt=json web token.
        // we use string in this case and not Token object
    }

    //extracts the user's email
    public static String extractEmail(String token){
        return extractAllClaims(token).getSubject();
    }

    //get the token's expiration date
    public static Date extractExpirationDate(String token){
        return extractAllClaims(token).getExpiration();
    }


    //check if the token is expired. if it is expired, then extractAllClaims will throw an exception
    public static boolean isTokenExpired(String token){
        try{
            extractAllClaims(token);
            return false;
        }catch (ExpiredJwtException e){
            return true;
        }
    }

    //validate the token. see if the username matches and the token is not expired.
    public static boolean validateToken(String token){
        return !isTokenExpired(token);
    }

    public static boolean validateToken3(String token, UserDetails userDetails){
        final String email = extractEmail(token);
        return (email.equals(userDetails.userEmail) && !isTokenExpired(token));
    }

//    public static boolean validateToken2(String token/*, UserDetails userDetails*/){
//        final DecodedJWT jwt = JWT.decode(token);
//        boolean verified = true;
//        System.out.println("jwt2: "+jwt.toString());
//        try{
////            SignatureAlgorithm.HS256.assertValidSigningKey(decodedSecretKey);
////            JWTVerifier.BaseVerification verification = (JWTVerifier.BaseVerification) JWT.require(Algorithm.HMAC256(encodedSecretKey));
////            DecodedJWT jawt = verification.build().verify(token);
//            Algorithm.HMAC256(encodedSecretKey).verify(jwt);
//        }catch (Exception e) {
//            System.out.println("Failed verification of token.");
//            System.out.println(e.getMessage());
//            verified = false;
//        }
//
//        return verified;
//    }

    public static String trimToken(String token){
        String[] splitBearer = token.split(" ");
        if(splitBearer.length>1){
            return splitBearer[1];
        }
        return token;
    }

    public static ClientType extractClientType(String token){
        return ClientType.valueOf(JWT.decode(token).getClaim("clientType").asString());
    }


}
