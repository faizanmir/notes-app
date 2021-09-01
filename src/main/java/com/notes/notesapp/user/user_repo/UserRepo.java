package com.notes.notesapp.user.user_repo;

import com.notes.notesapp.models.user_models.NotesUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository("userRepo")
public interface UserRepo extends MongoRepository<NotesUser, String> {
    NotesUser findUserByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByJwt(String jwt);
    NotesUser findUserByJwt(String jwt);

}
