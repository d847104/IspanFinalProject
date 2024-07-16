package com.ispan.warashibe.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

@Service
public class TokenBlacklistService {
    private Set<String> tokenBlacklist = new HashSet<>();

    public void addToBlacklist(String token) {
        tokenBlacklist.add(token);
    }

    public boolean isTokenBlacklisted(String token) {
        return tokenBlacklist.contains(token);
    }
}
