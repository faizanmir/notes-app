package com.notes.notesapp.user.user_api_controller;
import com.notes.notesapp.models.notes_models.Note;
import com.notes.notesapp.models.user_models.AuthRequest;
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
import java.util.List;
import java.util.Map;

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


    @GetMapping("/get-all-users")
    public List<NotesUser> getAllUsers(){
        return userService.getAllUsers();
    }


    @PostMapping("/login")
    public ResponseEntity<?> signUp(@RequestBody NotesUser notesUser) {
        if (!userService.checkIfUserExistsByName(notesUser.getUsername())) {
            notesUser.setId(sequenceGenerator.getSequenceNumber(NotesUser.SEQUENCE_NAME));
            String jwt = jwtUtils.createJwtToken(new HashMap<>(), notesUser.getUsername());
            notesUser.setJwt(jwt);
            return ResponseEntity.ok(userService.saveUser(notesUser));
        }else {
           return signIn(new AuthRequest(notesUser.getUsername(), notesUser.getPassword()));
        }
    }

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
        return ResponseEntity.ok(userService.updateJwt(jwt,authRequest.getUsername()));
    }


    @PostMapping("/user/get-notes/")
    public List<Note>  getNotes(@RequestHeader Map<String, String> headers) throws Exception {
        String bearerWithJwt = headers.get("authorization");
        if(bearerWithJwt!=null) {
            String jwt = bearerWithJwt.substring(7);
            return userService.getNotesForUser(jwt);
        }else {
            throw new Exception("Header doesn't have auth token");
        }
    }

    @PostMapping("/user/create-note")
    public NotesUser addNote(@RequestHeader Map<String, String> headers,@RequestBody Note note) throws Exception {
        String bearerWithJwt = headers.get("authorization");
        if(bearerWithJwt!=null) {
            String jwt = bearerWithJwt.substring(7);
            return userService.addNoteForUser(jwt, note);
        }else {
            throw new Exception("Header doesn't have auth token");
        }
    }

    @GetMapping("/user/get-note/{noteId}")
    public Note getNote(@RequestHeader Map<String, String> headers, @PathVariable String noteId) throws Exception {
        String bearerWithJwt = headers.get("authorization");
        if(bearerWithJwt!=null) {
            String jwt = bearerWithJwt.substring(7);
            return userService.getNote(jwt, noteId);
        }else {
            throw new Exception("Header doesn't have auth token");
        }
    }


    @PostMapping ("/user/update-note")
    public NotesUser updateNote(@RequestHeader Map<String, String> headers,@RequestBody Note note) throws Exception {
        String bearerWithJwt = headers.get("authorization");
        if(bearerWithJwt!=null) {
            return userService.updateNoteForUser(bearerWithJwt.substring(7), note);
        }else{
            throw new Exception("Header doesn't have auth token");
        }
    }

    @GetMapping("/user/delete-note/{noteId}")
    public NotesUser deleteNote(@RequestHeader Map<String, String> headers,@PathVariable String noteId) throws Exception {
        String bearerWithJwt = headers.get("authorization");
        if(bearerWithJwt!=null) {
            return userService.deleteNoteForUser(bearerWithJwt.substring(7),noteId);
        }else{
            throw new Exception("Header doesn't have auth token");
        }
    }
}
