package com.rafaelrocha.backend.repositories;

import com.rafaelrocha.backend.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
