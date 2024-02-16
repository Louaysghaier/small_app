package com.test.smallapp.ServiceIMP;


import com.test.smallapp.DTO.RoleName;
import com.test.smallapp.Entity.Role;
import com.test.smallapp.Entity.User;
import com.test.smallapp.Interfaces.UserServiceInterface;
import com.test.smallapp.Reposotories.RoleRepository;
import com.test.smallapp.Reposotories.UserRepository;
import com.test.smallapp.Services.MailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.*;

@Service
public class
UserServiceIMP implements UserServiceInterface {

    @Autowired
    UserRepository userRepository;
    @Autowired
    MailSenderService mailSending;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    RoleRepository roleRepository;


    public List<User> getAllUser() {
        return userRepository.findAll();
    }


    public User getUserById(Long idUser) {
        return userRepository.findById(idUser).orElseThrow(() -> new IllegalArgumentException("Provider ID not Found"));
    }

    public List<User> getUserByRoles(RoleName roleName){
        Role role= roleRepository.findByName(roleName).get();
        return userRepository.findByRolesContains(role);
    }

    public User deleteUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
        } else {
            return null;
        }
    }


    public void bloqueUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        User user1 = user.get();
        String Newligne = System.getProperty("line.separator");
        String url = "http://localhost:4200/auth/verification/" + user1.getToken();
        String body = "compte bloque\n  use this link to verify your account is :" + Newligne + url;
        if (user.isPresent()) {

            user1.setValid(false);
            this.userRepository.save(user1);
            try {
                mailSending.send(user1.getEmail(), "bloque ", body);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

/*
    public List<User> getUsersOrderBySum_totalAsc(){
        return userRepository.findUsersOrderByBilanSum_totalAsc();
    }
    public List<User> getAllUserByRoleOrderSum_total(RoleName role) {
        return userRepository.findAllUserByRoleOrderSum_total(role);
    }
*/

    public void validInscription(Long id) {
        Optional<User> user = userRepository.findById(id);
        User user1 = user.get();
        String Newligne = System.getProperty("line.separator");
        String url = "http://localhost:4200/auth/verification/" + user1.getToken();
        String body = "Soyez le bienvenue dans notre platforme ECOtalan  \n  veuillez utuliser ce lien là pour s'authentifier :" + Newligne + url + Newligne + "verification" +
                "Voici votre code de verfication  TN1122" ;
        if (user.isPresent()) {

            user1.setValid(true);
            this.userRepository.save(user1);
            try {
                mailSending.send(user1.getEmail(), "Welcome Provaider", body);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public ResponseEntity<User> registerUser(User user1, String roleName) {
        if (userRepository.existsByUsername(user1.getUsername())) {
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
        if (userRepository.existsByEmail(user1.getEmail())) {
            return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
        }
        User user = new User(user1.getName(), user1.getUsername(), user1.getEmail(), passwordEncoder.encode(user1.getPassword()), false, user1.getAddress(), false);
        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName(RoleName.valueOf(roleName.trim()))
                .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
        roles.add(userRole);
        user.setRoles(roles);
        user.setValid(true);
        User suser = userRepository.save(user);
        if (suser != null) {
            //String Newligne = System.getProperty("line.separator");
            String url = "http://localhost:4200/#/verification" ;
            String verificationCode = "TN1122"; // Replace with your actual verification code
            String newLine = "<br/>"; // HTML line break
            String htmlMessage = "<div style='border: 1px solid #ccc; padding: 10px; margin-bottom: 10px;'>"
                    + "Soyez le bienvenue dans notre plateforme" + newLine
                    + "Veuillez utiliser ce lien pour vous authentifier : " + newLine
                    + "<a href='" + url + "'>" + url + "</a>" + newLine
                    + "<strong>Verification Code:</strong> " + verificationCode + newLine
                    + "</div>";
            try {
                mailSending.send(user.getEmail(), "Welcome"+ user.getName() , htmlMessage);
                return new ResponseEntity<User>(user, HttpStatus.OK);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

    }

    public ResponseEntity<User> registerEntreprise(User user1) {
        if (userRepository.existsByUsername(user1.getUsername())) {
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
        if (userRepository.existsByEmail(user1.getEmail())) {
            return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
        }
        User user = new User(user1.getName(), user1.getUsername(), user1.getEmail(), passwordEncoder.encode(user1.getPassword()), false, user1.getAddress(), false);
        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName(RoleName.ROLE_Entreprise)
                .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
        roles.add(userRole);
        user.setRoles(roles);
        user.setValid(true);
        User suser = userRepository.save(user);
        if (suser != null) {
            String Newligne = System.getProperty("line.separator");
            String url = "http://localhost:4200/auth/verification/" + suser.getToken();
            String body = "Soyez le bienvenue dans notre platforme ECOtalan  \n  veuillez utuliser ce lien là pour s'authentifier :" + Newligne + url + Newligne + "verification" +
                    "Voici votre code de verfication  TN1122" ;
            try {
                mailSending.send(user.getEmail(), "Welcome", body);
                return new ResponseEntity<User>(user, HttpStatus.OK);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }


    }

    public ResponseEntity<User> registerAdmin(@Valid @RequestBody User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
        String token = UUID.randomUUID().toString().replace("-", "");
        User user1 = new User(user.getName(), user.getUsername(), user.getEmail(), passwordEncoder.encode(user.getPassword()), false, user.getAddress(), false);
        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
                .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
        roles.add(userRole);
        user1.setRoles(roles);
        userRepository.save(user1);
        return new ResponseEntity<User>(user1, HttpStatus.OK);
    }

    public Optional<User> getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        return userRepository.findByUsername(username);
    }

}



