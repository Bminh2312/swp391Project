package com.swp391.project.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.gson.Gson;
import com.swp391.project.dto.UserDetailDTO;
import com.swp391.project.entity.RoleEntity;
import com.swp391.project.entity.UserEntity;
import com.swp391.project.jwt.JwtHelper;
import com.swp391.project.payload.response.BaseResponse;
import com.swp391.project.repository.RoleRepository;
import com.swp391.project.repository.UserRepository;
import com.swp391.project.service.impl.LoginServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService implements LoginServiceImp {
    private final Gson gson = new Gson();

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

//    @Autowired
//    private PasswordEncoder passwordEncoder;

    public ResponseEntity<?> loginWithGmail(String accessToken) {
        try{
            System.out.println("Token: " + accessToken);
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(accessToken);
            System.out.println("Email: " + decodedToken.getEmail());
            System.out.println("Decode: " + decodedToken);
            String email = decodedToken.getEmail();
            UserEntity userEntity = checkLoginGmail(email);
            UserDetailDTO userDetailDTO = new UserDetailDTO();
//            if (email.endsWith("@fpt.edu.vn")) {
//                return new ResponseEntity<>(decodedToken, HttpStatus.OK);
//            }
            String avt = decodedToken.getPicture();
            String fullName = decodedToken.getName();
            if(userEntity != null){
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(email,null);
                Authentication authentication = authenticationManager.authenticate(token);
                ObjectMapper mapper = new ObjectMapper();
                String json = mapper.writeValueAsString(authentication.getAuthorities());
                String jwtToken = jwtHelper.generateToken(json);
                userDetailDTO.setAccessToken(userEntity.getAccessToken());
                userDetailDTO.setEmail(userEntity.getEmail());
                userDetailDTO.setFullName(userEntity.getFullName());
                userDetailDTO.setAvt(userEntity.getAvt());
                userDetailDTO.setRole(userEntity.getRole().getName());
                BaseResponse baseResponse = new BaseResponse();
                baseResponse.setMesssage("LoginSucessFull");
                baseResponse.setData(userDetailDTO);
                return new ResponseEntity<>(baseResponse, HttpStatus.OK);
            }else{
                RoleEntity roleEntity = roleRepository.findByName("ROLE_USER");
                if (roleEntity == null) {
                    roleEntity = new RoleEntity();
                    roleEntity.setName("ROLE_USER");
                    roleRepository.save(roleEntity);
                }

                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(email,null);
                Authentication authentication = authenticationManager.authenticate(token);
                ObjectMapper mapper = new ObjectMapper();
                String json = mapper.writeValueAsString(authentication.getAuthorities());
                String jwtToken = jwtHelper.generateToken(json);
                userEntity = new UserEntity();
                userEntity.setEmail(email);
                userEntity.setRole(roleEntity);
                userEntity.setFullName(fullName);
                userEntity.setAvt(avt);
                userEntity.setAccessToken(jwtToken);
                userRepository.save(userEntity);
                userDetailDTO.setAccessToken(userEntity.getAccessToken());
                userDetailDTO.setEmail(userEntity.getEmail());
                userDetailDTO.setFullName(userEntity.getFullName());
                userDetailDTO.setAvt(userEntity.getAvt());
                userDetailDTO.setRole(userEntity.getRole().getName());
                BaseResponse baseResponse = new BaseResponse();
                baseResponse.setMesssage("LoginSucessFull");
                baseResponse.setData(userDetailDTO);

                return new ResponseEntity<>(baseResponse, HttpStatus.OK);
            }
//            ObjectMapper mapper = new ObjectMapper();
//            return new ResponseEntity<>(decodedToken, HttpStatus.OK);

        } catch (FirebaseAuthException e) {
            System.out.println("Firebase Authentication Error: " + e.getMessage());
            return new ResponseEntity<>(e.getMessage(), null, HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    public ResponseEntity<?> loginWithUserNameAndPassword(String username, String password) throws JsonProcessingException {
        try {
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
            Authentication authentication = authenticationManager.authenticate(token);
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(authentication.getAuthorities());
            String jwtToken = jwtHelper.generateToken(json);
            UserEntity userEntity = checkLogin(username,password);
            UserDetailDTO userDetailDTO = new UserDetailDTO();
            userEntity.setAccessToken(jwtToken);
            userRepository.save(userEntity);
            userDetailDTO.setAccessToken(userEntity.getAccessToken());
            userDetailDTO.setEmail(userEntity.getEmail());
            userDetailDTO.setFullName(userEntity.getFullName());
            userDetailDTO.setAvt(userEntity.getAvt());
            userDetailDTO.setRole(userEntity.getRole().getName());
            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setMesssage("OKOK");
            baseResponse.setData(userDetailDTO);
            return new ResponseEntity<>(baseResponse, HttpStatus.OK);
        } catch (UsernameNotFoundException | BadCredentialsException e) {
            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setMesssage("Login Fail");
            baseResponse.setData("Invalid username or password");
            return new ResponseEntity<>(baseResponse, HttpStatus.UNAUTHORIZED);
        }
    }



    @Override
    public UserEntity checkLoginGmail(String email) {
        BaseResponse baseResponse = new BaseResponse();
        UserEntity userEntity = userRepository.findByEmail(email);
        if(userEntity != null) {

            return userEntity;
        }

        return null;
    }

    @Override
    public UserEntity checkLogin(String username, String password) {
        UserEntity userEntity = userRepository.findByUsername(username);
        if(userEntity != null) {

            //kiểm tra password trong database có match với password user truyền lên hay không
            if (passwordEncoder.matches(password, userEntity.getPassword())) {
                System.out.println(userEntity.getFullName());
                return userEntity;
            }
        }
        return null;
    }
}