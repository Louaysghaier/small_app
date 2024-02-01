package com.test.smallapp.Controllers;


import com.test.smallapp.DTO.SignIn;
import com.test.smallapp.Entity.User;
import com.test.smallapp.JWT.JwtProvider;
import com.test.smallapp.JWT.JwtResponse;
import com.test.smallapp.Reposotories.RoleRepository;
import com.test.smallapp.Reposotories.UserRepository;
import com.test.smallapp.ServiceIMP.UserService;
import com.test.smallapp.Services.MailSenderService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthRestAPIs {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    MailSenderService mailSending;

    @PostMapping("/signIn")
    public ResponseEntity<JwtResponse> authenticateUser(@RequestBody SignIn login) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(login.getEmail(), login.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateJwtToken(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername(), userDetails.getAuthorities()));

    }
    @RequestMapping(value = "/signup/employee", method = RequestMethod.POST)
    public ResponseEntity<User> registerUser(@Validated @RequestBody User user1) {
       return userService.registerUser(user1);
    }

        @RequestMapping(value = "/signup/entreprise", method = RequestMethod.POST)
        public ResponseEntity<User> registerEntreprise(@Validated @RequestBody User user1){
          return userService.registerEntreprise(user1);
    }
    @RequestMapping(value = "/signupadmin", method = RequestMethod.POST)
    public ResponseEntity<User> registerAdmin(@Valid @RequestBody User user)  {
        return userService.registerAdmin(user);
    }
}
















