package com.notes.notesapp.user.user_service;


import com.notes.notesapp.models.user_models.NotesUser;
import com.notes.notesapp.user.user_repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private UserRepo userRepo;

    @Autowired
    public void setUserRepo(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public boolean checkIfUserExistsByName(String name){
        return userRepo.existsByUsername(name);
    }

    public boolean checkIfUserExistsByEmail(String email){
        return userRepo.existsByEmail(email);
    }

    public NotesUser provideUser(String username){
        return userRepo.findUserByUsername(username);
    }

    public List<NotesUser> getAllUsers(){
        return userRepo.findAll();
    }

    public NotesUser saveUser(NotesUser notesUser){
        return userRepo.save(notesUser);
    }

    public void deleteUser(String id){
        userRepo.deleteById(id);
    }


    public NotesUser updateJwt(String jwt,String username){
        NotesUser notesUser = userRepo.findUserByUsername(username);
        notesUser.setJwt(jwt);
        return userRepo.save(notesUser);
    }



}
