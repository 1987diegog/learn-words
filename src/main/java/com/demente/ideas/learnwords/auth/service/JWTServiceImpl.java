package com.demente.ideas.learnwords.auth.service;

import com.demente.ideas.learnwords.auth.SimpleGrantedAuthorityMixIn;
import com.demente.ideas.learnwords.model.services.JpaUserDetailsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

/**
 * @author 1987diegog
 */
@Component
public class JWTServiceImpl implements JWTService {

    private Logger logger = LoggerFactory.getLogger(JWTServiceImpl.class);

    // Secret key generada con http://keygen.io/
    public static final String SECRET = Base64Utils
			.encodeToString("878b75b1a38838163ee71f80725fb42fd091887e0bb5b370a313e2de91a99f4736403d7fbca8f3ee796578a3b2692cf1f6d2e6d0537d8e238ef2340ab2e5ef7b"
					.getBytes());

    public static final long EXPIRATION_DATE = 3600000L;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";

    @Override
    public String create(Authentication auth) throws IOException {

//        SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
//        // String base64Key = Encoders.BASE64.encode(secretKey.getEncoded());
//
//        // data user
        String username = ((User) (auth.getPrincipal())).getUsername();
        logger.info("[AUTHENTICATION] - Se procede a crear el JWT para el usuario: " + username);
        Collection<? extends GrantedAuthority> roles = auth.getAuthorities();

        Claims claims = Jwts.claims();
        claims.put("authorities", new ObjectMapper().writeValueAsString(roles));
//
//        // se crea el token que se retornara al cliente
//        String token = Jwts.builder()
//                .setClaims(claims)
//                .setSubject(username)
//                .signWith(secretKey, SignatureAlgorithm.HS512)
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_DATE)) // --> 1hs
//                .compact();


        String token = Jwts.builder().setClaims(claims).setSubject(username)
                .signWith(SignatureAlgorithm.HS512, SECRET.getBytes()).setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_DATE)).compact();

        logger.info("[AUTHENTICATION] - JWT creado con exito para el usuario: " + username);

        return token;
    }

    @Override
    public boolean validate(String token) {
        try {
            getClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public Claims getClaims(String token) {
        Claims claims = Jwts.parser().setSigningKey(SECRET.getBytes())
                .parseClaimsJws(resolve(token)).getBody();
        return claims;
    }

    @Override
    public String getUsername(String token) {
        return getClaims(token).getSubject();
    }

    @Override
    public Collection<? extends GrantedAuthority> getRoles(String token) throws IOException {
        Object roles = getClaims(token).get("authorities");

        Collection<? extends GrantedAuthority> authorities = Arrays
                .asList(new ObjectMapper().addMixIn(SimpleGrantedAuthority.class, SimpleGrantedAuthorityMixIn.class)
                        .readValue(roles.toString().getBytes(), SimpleGrantedAuthority[].class));

        return authorities;
    }

    @Override
    public String resolve(String token) {
        if (token != null && token.startsWith(TOKEN_PREFIX)) {
            return token.replace(TOKEN_PREFIX, "");
        }
        return null;
    }
}
