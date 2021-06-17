package fr.univ.rouen.xmas.controllers;

import fr.univ.rouen.xmas.dto.LoginDTO;
import fr.univ.rouen.xmas.entities.User;
import fr.univ.rouen.xmas.dto.UserDTO;
import fr.univ.rouen.xmas.services.UserServiceImp;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
@CrossOrigin(origins = "http://localhost:3000")
public class LoginController {
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserServiceImp userService;

    @Autowired
    private ModelMapper modelMapper;

    @ApiOperation(value = "Authentification.")
    @PostMapping
    public ResponseEntity<Object> authenticate(@RequestBody LoginDTO loginDTO) {
        User user = userService.getUserByEmail(loginDTO.getEmail());

        if (user == null) {
            return new ResponseEntity<Object>("L'adresse mail ne correspond à aucun compte", HttpStatus.BAD_REQUEST);
        } else if (passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            return new ResponseEntity<Object>(modelMapper.map(user, UserDTO.class), HttpStatus.OK);
        }

        return new ResponseEntity<Object>("Mot de passe incorrect", HttpStatus.BAD_REQUEST);
    }
}
