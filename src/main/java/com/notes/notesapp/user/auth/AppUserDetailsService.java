package com.notes.notesapp.user.auth;

import com.notes.notesapp.models.user_models.NotesUser;
import com.notes.notesapp.models.user_models.NotesUserPrincipal;
import com.notes.notesapp.user.user_repo.UserRepo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AppUserDetailsService implements UserDetailsService {
    private UserRepo userRepo;

    private static final Log logger = LogFactory.getLog(AppUserDetailsService.class);


    @Autowired
    public void setUserRepo(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
         return new NotesUserPrincipal(userRepo.findById(s).orElseThrow());
    }

}
