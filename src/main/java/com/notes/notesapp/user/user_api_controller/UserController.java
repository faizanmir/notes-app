package com.notes.notesapp.user.user_api_controller;


import com.notes.notesapp.models.user_models.AuthRequest;
import com.notes.notesapp.models.user_models.AuthResponse;
import com.notes.notesapp.models.user_models.NotesUser;
import com.notes.notesapp.user.auth.AppUserDetailsService;
import com.notes.notesapp.user.user_service.UserService;
import com.notes.notesapp.user.util.JWTUtils;
import com.notes.notesapp.user.util.SequenceGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController()

public class UserController {
    private AuthenticationManager authenticationManager;
    private AppUserDetailsService appUserDetailsService;
    private JWTUtils jwtUtils;
    private UserService userService;
    private SequenceGenerator sequenceGenerator;

    @Autowired
    public void setSequenceGenerator(SequenceGenerator sequenceGenerator) {
        this.sequenceGenerator = sequenceGenerator;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setAppUserDetailsService(AppUserDetailsService appUserDetailsService) {
        this.appUserDetailsService = appUserDetailsService;
    }

    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Autowired
    public void setJwtUtils(JWTUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }




    @GetMapping("/hello")
    public String sayHello(){
        return  "Hello World";
    }


    @PostMapping("/register")
    public ResponseEntity<?> signUp(@RequestBody NotesUser notesUser) throws Exception {
        System.out.println("Note User"+notesUser.toString());
        notesUser.setId(sequenceGenerator.getSequenceNumber(NotesUser.SEQUENCE_NAME));
        String jwt  = jwtUtils.createJwtToken(new HashMap<>(),notesUser.getUsername());
        System.out.println(jwt);
        notesUser.setJwt(jwt);
       return ResponseEntity.ok(userService.saveUser(notesUser));
    }


    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@RequestBody AuthRequest authRequest){
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(),
                            authRequest.getPassword()));
        }catch (BadCredentialsException e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body("User doesn't exist");
        }
        UserDetails userDetails =  appUserDetailsService.loadUserByUsername(authRequest.getUsername());
        final String jwt  = jwtUtils.generateToken(userDetails);
        return ResponseEntity.ok(new AuthResponse(userService.updateJwt(jwt,authRequest.getUsername()).getJwt()));
    }





}
