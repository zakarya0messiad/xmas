package fr.univ.rouen.xmas.services;

import fr.univ.rouen.xmas.entities.User;
import fr.univ.rouen.xmas.exceptions.BadRequestException;
import fr.univ.rouen.xmas.exceptions.NotFoundException;
import fr.univ.rouen.xmas.exceptions.UserAlreadyExistsException;
import fr.univ.rouen.xmas.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.*;

@Service
@Transactional
public class UserServiceImp implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Objects.requireNonNull(email);
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRole())
                .build();
    }



    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    public List<User> getAllLutins() {
        return userRepository.findByRole("USER");
    }

    public User getUserById(UUID id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("Aucun utilisateur trouvé avec l'identifiant " + id));
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("Aucun utilisateur trouvé ayant un compte avec l'adresse " + email));
    }

    public User createUser(User user) {
        Optional<User> optionalUser = userRepository.findByEmail(user.getEmail());

        if (optionalUser.isPresent()) {
            throw new UserAlreadyExistsException("Un compte existe dèja avec l'adresse e-mail suivante : " + user.getEmail());
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (user.getRole() != "ADMIN") {
            user.setRole("USER");
        }
        return userRepository.save(user);
    }

    public User updateUser(User user) {
        Optional<User> userDb = this.userRepository.findById(user.getId());

        if(userDb.isPresent()) {
            User userUpdate = userDb.get();
            userUpdate.setId(user.getId());
            userUpdate.setName(user.getName());
            userUpdate.setEmail(user.getEmail());
            userUpdate.setRole(user.getRole());
            userUpdate.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(userUpdate);
            return userUpdate;
        }else {
            throw new NotFoundException("Record not found with id : " + user.getId());
        }
    }

    public User updateName(UUID id, User user) {
        Optional<User> optionalUser = userRepository.findById(id);

        if (!optionalUser.isPresent()) {
            throw new NotFoundException("Aucun utilisateur trouvé avec l'identifiant " + id);
        }

        User u = optionalUser.get();
        u.setName(user.getName());

        return userRepository.save(u);
    }

    public void updateEmail(UUID id, String email) {
        Optional<User> optionalUser1 = userRepository.findById(id);
        Optional<User> optionalUser2 = userRepository.findByEmail(email);

        if (!optionalUser1.isPresent()) {
            throw new NotFoundException("Aucun utilisateur trouvé avec l'identifiant " + id);
        }

        if (optionalUser2.isPresent()) {
            throw new BadRequestException("L'adresse mail " + email + " correspond déjà à un compte");
        }

        User u = optionalUser1.get();
        u.setEmail(email);

        userRepository.save(u);
    }

    public void updatePassword(UUID id, String password) {
        Optional<User> optionalUser = userRepository.findById(id);

        if (!optionalUser.isPresent()) {
            throw new NotFoundException("Aucun utilisateur trouvé avec l'identifiant " + id);
        }

        User u = optionalUser.get();
        u.setPassword(passwordEncoder.encode(password));

        userRepository.save(u);
    }


    public void delete(UUID id) {
        Optional<User> optionalUser = userRepository.findById(id);

        if (!optionalUser.isPresent()) {
            throw new NotFoundException("Aucun utilisateur trouvé avec l'identifiant " + id);
        }
        User user = optionalUser.get();
        userRepository.delete(user);
    }

}
