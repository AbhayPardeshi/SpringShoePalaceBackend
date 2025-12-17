package com.example.shoepalace.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

// generate, decode, and validate JWT
@Service
public class JwtService {

    private String secretKey;

    private long jwtTokenExpiry;


    // claims is returned
    // Claims::getSubject -> method reference : call this getSubject method once we get claims
    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractTokenExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }


    // generate a jwt token
    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(), userDetails);
    }

    // sending data to build token
    private String generateToken(Map<String,Object> extraClaims, UserDetails userDetails){
        return buildToken(extraClaims, userDetails, jwtTokenExpiry);
    }

    // building the token
    // adding email, issuedAt time, setting expiration time, signingAlgo HS256
    private String buildToken(Map<String,Object> extraClaims, UserDetails userDetails, long jwtTokenExpiry){
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtTokenExpiry))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails){
        String claimUsername = extractUsername(token);

        return (claimUsername.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }


    // return if the token expiry date is before the current date
    // if yes return true else false
    private boolean isTokenExpired(String token) {
        return extractTokenExpiration(token).before(new Date());
    }

    // <T> T - can return any type of data based on what you want
    // Function<Claims,T> - a function that takes in claims as input and return whatever we want
    // but why use this? - so you don't have to write a separate function for all different types of claims
    // return the extracted claim
    private <T> T extractClaim(String token, Function<Claims,T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // this is where to token is unwrapped, and we can extract info inside it
    // we use a factory utility class provide by JWT library, can be used to build or parse the token
    // parseClaimsJws(token) - main man; does verification, split token into header,payload, signature
    // converts payload into claims
    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

    }

    // jwt needs a secret key to verify the signature of the token sent by user
    // our secret key is a base64 string, but jwt library does not understand this type of key
    // we need to convert this to a binary string
    // this is done here
    private Key getSignInKey() {

        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);

    }






}
