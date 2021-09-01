package com.notes.notesapp.user.user_service;


import com.notes.notesapp.models.notes_models.Note;
import com.notes.notesapp.models.user_models.NotesUser;
import com.notes.notesapp.user.user_repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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


    public NotesUser findUserByToken(String jwt){
        return userRepo.findUserByJwt(jwt);
    }

    public List<Note> getNotesForUser(String jwt){
        NotesUser notesUser  =  findUserByToken(jwt);
        System.out.println(jwt);
        return notesUser.getNotes();
    }

    public Note getNote(String jwt,String noteId){
        return findUserByToken(jwt)
                .getNotes()
                .stream()
                .filter(note -> note.getId().equalsIgnoreCase(noteId))
                .findFirst()
                .orElseThrow();
    }


    public NotesUser addNoteForUser(String jwt,Note note){
        NotesUser notesUser =  findUserByToken(jwt);
        note.setId(String.valueOf(System.currentTimeMillis()));
        notesUser.getNotes().add(note);
        return  saveUser(notesUser);
    }

    public NotesUser deleteNoteForUser(String jwt,String noteId){
        NotesUser notesUser   =  findUserByToken(jwt);
        notesUser.getNotes().removeIf(note1 -> note1.getId().equalsIgnoreCase(noteId));
        return  saveUser(notesUser);
    }

    public NotesUser updateNoteForUser(String jwt,Note note){
        NotesUser notesUser  =  findUserByToken(jwt);
        Optional<Note> note1 = notesUser.getNotes().stream().filter(e->e.getId().equalsIgnoreCase(note.getId())).findFirst();
        if(note1.isPresent()){
           int noteIndex=  notesUser.getNotes().indexOf(note1.get());
           notesUser.getNotes().remove(noteIndex);
           notesUser.getNotes().add(noteIndex,note);
        }
        return saveUser(notesUser);
    }





}
