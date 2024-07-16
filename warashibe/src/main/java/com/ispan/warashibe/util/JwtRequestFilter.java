package com.ispan.warashibe.util;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ispan.warashibe.service.TokenBlacklistService;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtRequestFilter extends OncePerRequestFilter{

    @Autowired
    private JsonWebTokenUtility jsonWebTokenUtility;

    @Autowired
    private TokenBlacklistService tokenBlacklistService;
	
	
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
        final String requestTokenHeader = request.getHeader("Authorization");

        String subject = null;
        String jwtToken = null;
        
        if(requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
        	jwtToken = requestTokenHeader.substring(7);
        	try {
        		jsonWebTokenUtility.validateEncryptedToken(jwtToken);
        	} catch(IllegalArgumentException e) {
        		System.out.println("無法取得 JWT Token");
        	}  catch (ExpiredJwtException e) {
                System.out.println("JWT Token 已經過期");
            }
        } else {
        	logger.warn("JWT Token does not begin with Bearer String");	
        }
        
        subject = jsonWebTokenUtility.validateEncryptedToken(jwtToken);
        if(subject != null) {
            if (tokenBlacklistService.isTokenBlacklisted(jwtToken)) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token is blacklisted");
                return;
            }
//            SecurityContextHolder.getContext().setAuthentication(subject);
            System.out.println("認證成功");
            
            
            
        }
        filterChain.doFilter(request, response);
        
        
        
	}

}
