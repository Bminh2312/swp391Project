package com.swp391.project.security;

import com.swp391.project.entity.UserEntity;
import com.swp391.project.service.impl.LoginServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomAuthenProvider implements AuthenticationProvider {

    @Autowired
    private LoginServiceImp loginServiceImp;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = (String) authentication.getPrincipal();
        System.out.println("Email " + email);
        String password = (String) authentication.getCredentials();
        System.out.println("Pass " + password);
        UserEntity userEntity = null;
        if(password!= null){
            userEntity = loginServiceImp.checkLogin(email,password);

        }else{
             userEntity = loginServiceImp.checkLoginGmail(email);

        }

        if( userEntity != null){
            //Tạo một list nhận vào danh sách quyền theo chuẩn của Security
            List<GrantedAuthority> listRoles = new ArrayList<>();
            //Tạo ra một quyền và gán tên quyền truy vấn được từ database để add vào list role ở trên
            SimpleGrantedAuthority role = new SimpleGrantedAuthority(userEntity.getRole().getName());
            listRoles.add(role);

            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken("","",listRoles);

            return authenticationToken;
        }

        throw new BadCredentialsException("Invalid username or password");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
