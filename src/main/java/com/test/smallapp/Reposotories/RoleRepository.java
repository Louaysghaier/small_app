package com.test.smallapp.Reposotories;



import com.test.smallapp.DTO.RoleName;
import com.test.smallapp.Entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByName(RoleName roleName );

}
