package com.ad.app.filter;

import com.ad.app.service.JwtUserDetailsService;
import com.google.common.base.Strings;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class JwtVerificationFilter extends OncePerRequestFilter {

    @Autowired
    JwtUserDetailsService jwtUserDetailsService;

    private final String secretKey = "AbsolutelyUnimaginableConfidentialOutOfTheWorldVeryBestSecretKey";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (!Strings.isNullOrEmpty(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
            String jws = authorizationHeader.replaceFirst("Bearer ", StringUtils.EMPTY);

            Claims claims = null;
            try {
                claims = Jwts.parserBuilder()
                        .setSigningKey(secretKey.getBytes())
                        .build()
                        .parseClaimsJws(jws)
                        .getBody();
            } catch (JwtException e) {
                throw new JwtException("Token invalid or expired");
            }

            String subject = claims.getSubject();
            String role = claims.get("role", String.class);

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    subject,
                    null,
                    List.of(new SimpleGrantedAuthority("ROLE_" + role))
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }
}
