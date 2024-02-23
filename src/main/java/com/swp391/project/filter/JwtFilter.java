package com.swp391.project.filter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.swp391.project.jwt.JwtHelper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtHelper jwtHelper;

    private final Gson gson = new Gson();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Optional<String> bearerToken = Optional.ofNullable(request.getHeader("Authorization"));

        if (bearerToken.isPresent() && bearerToken.get().startsWith("Bearer ")) {
            String token = bearerToken.get().substring(7);
            System.out.println(token);

            if (!token.isEmpty()) {
                try {
                    String data = jwtHelper.decodeToken(token);
                    System.out.println(data);
                    Type listType = new TypeToken<ArrayList<SimpleGrantedAuthority>>() {}.getType();

                    List<GrantedAuthority> listRoles = gson.fromJson(data, listType);

                    if (!listRoles.isEmpty()) {
                        // Tạo context holder
                        UsernamePasswordAuthenticationToken authenticationToken =
                                new UsernamePasswordAuthenticationToken(null, null, listRoles);
                        SecurityContext securityContext = SecurityContextHolder.getContext();
                        securityContext.setAuthentication(authenticationToken);
                    }
                } catch (Exception e) {
                    System.out.println("Kiem tra loi");
                    // Xử lý exception
                    System.out.println(e.getMessage());

                }
            }
        }

        filterChain.doFilter(request, response);
    }
    }

