package com.foodie.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.List;

public class JwtTokenValidator extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    @NotNull HttpServletResponse response,
                                    @NotNull FilterChain filterChain) throws ServletException, IOException {
        String jwt = request.getHeader(JwtCostant.JWT_HEADER);

        //Bearer token

        if(jwt!=null){ //if jwt notnull it means jwt is provided in header we need to validate jwt token

            jwt = jwt.substring(7); // we need to collect the jwt using substring

            try{ // there should be 2 claims one for email and role of the user/authority.
                SecretKey key  = Keys.hmacShaKeyFor(JwtCostant.SECRET_KEY.getBytes());
                Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();

                String email = String.valueOf(claims.get("email"));
                String authorities = String.valueOf(claims.get("authorities"));

                //ROLE_CUSTOMER,ROLE_OWNER,ADMIN

                List<GrantedAuthority> auth = AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);
                //String will be converted into granted authority

                Authentication authentication = new UsernamePasswordAuthenticationToken(email,null, auth);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            catch (Exception e){
                throw new BadCredentialsException("Invalid token......");
            }
        }

        filterChain.doFilter(request, response); // after validating it will move to next part
    } // this is to validate JWT token
}
