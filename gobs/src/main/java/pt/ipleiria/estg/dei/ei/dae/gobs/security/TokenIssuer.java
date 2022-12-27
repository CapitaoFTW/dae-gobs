package pt.ipleiria.estg.dei.ei.dae.gobs.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Calendar;

public class TokenIssuer {
    public static final int EXPIRY_MINUTES = 60;
    protected static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public String issue(AuthInfo authInfo) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, EXPIRY_MINUTES);

        return Jwts
                .builder()
                .setSubject(authInfo.getUsername())
                .setIssuer(authInfo.getToken())
                .signWith(key, SignatureAlgorithm.HS256)
                .setExpiration(calendar.getTime())
                .compact();
    }

    public AuthInfo revertIssue(String jwToken) {
        Claims body = Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jwToken)
                .getBody();
        return new AuthInfo(body.getSubject(), body.getIssuer());
    }
}
