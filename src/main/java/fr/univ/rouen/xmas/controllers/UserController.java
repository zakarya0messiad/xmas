package fr.univ.rouen.xmas.controllers;

import fr.univ.rouen.xmas.dto.UserDTO;
import fr.univ.rouen.xmas.dto.UserEmailDTO;
import fr.univ.rouen.xmas.dto.UserNameDTO;
import fr.univ.rouen.xmas.dto.UserPasswordDTO;
import fr.univ.rouen.xmas.entities.User;
import fr.univ.rouen.xmas.services.UserServiceImp;
import fr.univ.rouen.xmas.utils.PdfExport;
import org.dom4j.DocumentException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
    @Autowired
    UserServiceImp userService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers(){
        return ResponseEntity.ok().body(userService.getAllUsers());
    }

    @GetMapping("/users/lutins")
    public ResponseEntity<List<User>> getAllLutins(){
        return ResponseEntity.ok().body(userService.getAllLutins());
    }


    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user){
        return ResponseEntity.ok().body(this.userService.createUser(user));
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable UUID id, @RequestBody User user){
        user.setId(id);
        return ResponseEntity.ok().body(this.userService.updateUser(user));
    }

    @DeleteMapping("/users/{id}")
    public HttpStatus deleteUser(@PathVariable UUID id){
        this.userService.delete(id);
        return HttpStatus.OK;
    }


    //Permet de modifier le nom et le nom d'un utilisateur.
    @PutMapping(value="/users/{id}/name")
    public UserDTO updateUserName(@PathVariable UUID id, @RequestBody UserNameDTO userNameDTO) {
        return modelMapper.map((userService.updateName(id, modelMapper.map(userNameDTO, User.class))), UserDTO.class);
    }

    //Permet de modifier l'adresse mail d'un utilisateur
    @PutMapping(path="/users/{id}/email")
    public void updateUserEmail(@PathVariable UUID id, @Valid @RequestBody UserEmailDTO userEmailDTO) {
        userService.updateEmail(id, userEmailDTO.getEmail());
    }

    //Permet de modifier le mot de passe d'un utilisateur.
    @PutMapping(path="/users/{id}/password")
    public void updateUserPassword(@PathVariable UUID id, @Valid @RequestBody UserPasswordDTO userPasswordDTO) {
        userService.updatePassword(id, userPasswordDTO.getPassword());
    }

    //Permet d'exporter la liste des utilisateurs au format PDF"
    @GetMapping(path = "/users/exportpdf")
    public ResponseEntity<InputStreamResource> exportPDF() throws DocumentException, com.itextpdf.text.DocumentException {
        List<UserDTO> usersDTO = new ArrayList<>();
        for (User user : userService.getAllUsers()) {
            UserDTO uDTO = modelMapper.map(user, UserDTO.class);
            usersDTO.add(uDTO);
        }

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.valueOf("application/pdf"));
        httpHeaders.add("Content-Disposition", "attachment; filename=liste_des_utilisateurs" + ".pdf");

        return ResponseEntity
                .ok()
                .headers(httpHeaders)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(PdfExport.usersExport(usersDTO)));
    }


}
