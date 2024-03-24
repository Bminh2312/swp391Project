package com.swp391.project.security;

import com.swp391.project.entity.RoleEntity;
import com.swp391.project.entity.UserEntity;
import com.swp391.project.repository.RoleRepository;
import com.swp391.project.service.impl.LoginServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class CustomAuthenProvider implements AuthenticationProvider {

    @Autowired
    private LoginServiceImp loginServiceImp;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = (String) authentication.getPrincipal();
        System.out.println("Email " + email);
        String password = (String) authentication.getCredentials();
        System.out.println("Pass " + password);
        UserEntity userEntity = null;
        UserEntity userEntityMail = null;
        if (password != null) {
            // Đăng nhập bằng tài khoản thông thường
            userEntity = loginServiceImp.checkLogin(email, password);
        } else {
            // Đăng nhập bằng email mới
            userEntityMail = loginServiceImp.checkLoginGmail(email);
        }

        if (userEntity != null) {
            if (userEntity.getStatus().equals("INACTIVE")) {
                throw new BadCredentialsException("User is baned");
            }
            List<GrantedAuthority> listRoles = new ArrayList<>();
            SimpleGrantedAuthority role = new SimpleGrantedAuthority(userEntity.getRole().getName());
            listRoles.add(role);

            return new UsernamePasswordAuthenticationToken("", "", listRoles);
        }

        if (userEntityMail != null) {
            if (userEntityMail.getStatus().equals("INACTIVE")) {
                throw new BadCredentialsException("User is baned");
            }
            List<GrantedAuthority> listRoles = new ArrayList<>();
            SimpleGrantedAuthority role = new SimpleGrantedAuthority(userEntityMail.getRole().getName());
            listRoles.add(role);

            return new UsernamePasswordAuthenticationToken("", "", listRoles);
        }

        // Nếu không tìm thấy người dùng trong cơ sở dữ liệu khi đăng nhập bằng tài khoản thông thường, ném ra BadCredentialsException
        if (password != null) {
            throw new UsernameNotFoundException("Invalid username or password");
        } else {
            // Nếu không tìm thấy người dùng trong cơ sở dữ liệu khi đăng nhập bằng email mới, trả về vai trò mặc định ROLE_USER
            RoleEntity roleEntity = roleRepository.findByName("ROLE_USER");
            List<GrantedAuthority> listRoles = new ArrayList<>();
            SimpleGrantedAuthority role = new SimpleGrantedAuthority(roleEntity.getName());
            listRoles.add(role);

            return new UsernamePasswordAuthenticationToken("", "", listRoles);
        }



    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
