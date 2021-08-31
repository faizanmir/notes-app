package com.notes.notesapp.user.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
@Component
public class JWTUtils {
    public static final String KEY = "HXR0OgK$+h1j0Hyd-0vr5Gs@8?S2[Dm]_Zw>X<<#;}p(yMP{~vgKrwuZp:XXM:";
    <T> T extractClaim(String token, Function<Claims,T> claimsResolver){
        final Claims claims =  extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token){
        return Jwts.parser().setSigningKey(KEY).parseClaimsJws(token).getBody();
    }
    public Date getExpiration(String token){
        return extractClaim(token,Claims::getExpiration);
    }

    public String getUserName(String token){
        return extractClaim(token,Claims::getSubject);
    }
    public String getId(String token){
        return extractClaim(token,Claims::getId);
    }

    public boolean isTokenExpired(String token){
        return getExpiration(token).before(new Date());
    }

    public  String generateToken(UserDetails userDetails){
        Map<String,Object> claims =  new HashMap<>();
        return createJwtToken(claims,userDetails.getUsername());
    }


    public String createJwtToken(Map<String,Object> claims,String subject){
        return Jwts.builder().setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+ 1000L*60*60*24*365))
                .signWith(SignatureAlgorithm.HS256,KEY)
                .compact();
    }


    public Boolean validate(String token,UserDetails userDetails){
        String userName  =  getUserName(token);
        return (userName.equals(userDetails.getUsername())&&!isTokenExpired(token));
    }
}
