package com.example.demo;

//import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.access.prepost.PreAuthorize;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class HelloController {

    Logger logger = LoggerFactory.getLogger(HelloController.class);

    @GetMapping("admin")
    @ResponseBody
    @PreAuthorize("hasAuthority('APPROLE_Admin')")
    public String admin() {
        return "Admin message. hurrrraaaaayyyyyy !!!!!!!!!!!!!!!!!!!";
    }

    @GetMapping("/user")
    @ResponseBody
    public Authentication getLoggedUserDetail(Authentication authentication) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        //get username
        String username = authentication.getName();
        logger.info("username : " + username);

        // concat list of authorities to single string seperated by comma
        String authorityString = authentication
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        String role = "role_A";
        boolean isCurrentUserInRole = authentication
                .getAuthorities()
                .stream()
                .anyMatch(role::equals);
        return authentication;
    }

}
