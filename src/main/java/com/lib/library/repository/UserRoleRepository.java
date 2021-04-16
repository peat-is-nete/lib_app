package com.lib.library.repository;

import com.lib.library.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    UserRole getUserRoleByType(int roleType);

}
