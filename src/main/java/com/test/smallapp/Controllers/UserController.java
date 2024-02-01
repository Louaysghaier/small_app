package com.test.smallapp.Controllers;



import com.test.smallapp.DTO.RoleName;
import com.test.smallapp.Entity.User;
import com.test.smallapp.ServiceIMP.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/user")
public class UserController {

@Autowired
UserService userService;

    @GetMapping("/list-user")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<User> ListUser() {
        return userService.getAllUser();
    }

    @PutMapping("/validate-user/{idUser}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void validInscription(@PathVariable("idUser") Long idUser) {
        userService.validInscription(idUser);
    }

    @PutMapping("/bloque-user/{idUser}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void bloqueUser(@PathVariable("idUser") Long idUser) {
        userService.bloqueUser(idUser);
    }
    @DeleteMapping("/delete-user/{idUser}")
    public void deleteAccount(@PathVariable("idUser") Long idUser) {
        userService.deleteUser(idUser);
    }

    @GetMapping("/list-RolesName/{RolesName}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<User> ListUserByRoles(@PathVariable("RolesName") RoleName roleName) {
        return userService.getUserByRoles(roleName);
    }
    /*
    @GetMapping("/list-User/ASC")
    public List<User> getUsersOrderBySum_totalAsc() {
        return userService.getUsersOrderBySum_totalAsc();
    }

    @GetMapping("/sorted-by-role/{role}")
    public List<User> getUsersSortedByRole(@PathVariable("role") String role) {
       RoleName role1= RoleName.valueOf(role);
        return userService.getAllUserByRoleOrderSum_total(role1);
    }
    */

}
