package com.test.smallapp.Controllers;



import com.test.smallapp.DTO.RoleName;
import com.test.smallapp.Entity.Role;
import com.test.smallapp.Entity.User;
import com.test.smallapp.Reposotories.RoleRepository;
import com.test.smallapp.Reposotories.UserRepository;
import com.test.smallapp.ServiceIMP.UserServiceIMP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Null;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/user")
public class UserController {

@Autowired
UserServiceIMP userServiceIMP;
@Autowired
    UserRepository userRepository;
@Autowired
    RoleRepository roleRepository;

    @GetMapping("/list-user")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<User> ListUser() {
        return userServiceIMP.getAllUser();
    }

    @PutMapping("/validate-user/{idUser}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void validInscription(@PathVariable("idUser") Long idUser) {
        userServiceIMP.validInscription(idUser);
    }

    @PutMapping("/bloque-user/{idUser}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void bloqueUser(@PathVariable("idUser") Long idUser) {
        userServiceIMP.bloqueUser(idUser);
    }
    @DeleteMapping("/delete-user/{idUser}")
    public void deleteAccount(@PathVariable("idUser") Long idUser) {
        userServiceIMP.deleteUser(idUser);
    }

    @GetMapping("/list-RolesName/{RolesName}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<User> ListUserByRoles(@PathVariable("RolesName") RoleName roleName) {
        return userServiceIMP.getUserByRoles(roleName);
    }
    @PutMapping("/addRole/{RolesName}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public  void addRole(@PathVariable("RolesName") RoleName roleName){
        Role role=new Role();
        role.setName(roleName);
        roleRepository.save(role);

    }
    /*
   @PutMapping("/deleteRole/{roleName}")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public void deleteRole(@PathVariable("roleName") RoleName roleName) {
    Role role = roleRepository.findByName(roleName)
            .orElseThrow(() -> new RuntimeException("Role not found"));

    // Remove the role from all users who are currently assigned that role
    List<User> usersWithRole = userRepository.findByRolesContaining(role);
    for (User user : usersWithRole) {
        user.getRoles().remove(role);
        userRepository.save(user);
    }

    // Delete the role
    roleRepository.delete(role);
}

    }*/

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
