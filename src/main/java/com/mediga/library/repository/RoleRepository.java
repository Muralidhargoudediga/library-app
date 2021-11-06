package com.mediga.library.repository;

import com.mediga.library.entity.Role;
import com.mediga.library.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(UserRole name);
}
